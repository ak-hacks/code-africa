package co.uk.thetimes.codeafrica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.uk.thetimes.codeafrica.datastore.DSInterface;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;

/**
 * @author anuragkapur
 */
@SuppressWarnings("serial")
public class FindSimilarServlet extends HttpServlet {
	
	private String chosenParam;
	double rangeToCheckIn;
	
	private static final Logger logger = Logger.getLogger(FindSimilarServlet.class
			.getCanonicalName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String country1 = req.getParameter("c1");

		if (country1 == null) {
			resp.setContentType("text/html");
			// Respond with a HTTP 400 bad request code
			resp.setStatus(400);
			resp.getWriter()
					.write("<b>Invalid arguments passed to the service.</b><br/>Please ensure you supply a country name.<br/>Example: TODO");
		} else {
			List<Entity> listOfCountries = new ArrayList<Entity>(3);

			Key keyForCountry1 = KeyFactory.createKey("Country", country1);
			Entity countryEntity1 = DSInterface.findEntity(keyForCountry1);
			Entity similarCountryEntity = findSimilarEntity(countryEntity1);
			
			listOfCountries.add(countryEntity1);
			listOfCountries.add(similarCountryEntity);
			
			Entity similarSearchCriteria = new Entity("search criteria");
			similarSearchCriteria.setProperty("prameter", chosenParam);
			listOfCountries.add(similarSearchCriteria);
			
			// Prepare and write JSON string to response stream.
			Gson jsonConverter = new Gson();
			String jsonResponseString = jsonConverter.toJson(listOfCountries);;
			resp.getWriter().write(jsonResponseString);
		}
	}
	
	private Entity findSimilarEntity(Entity inputCountry) {
		
		generateRandomSimilaCriteria();
		
		double chosenParamValue = (Double)inputCountry.getProperty(chosenParam);
		logger.log(Level.INFO, "Searching for an entity that matches " + chosenParam + " :: " + chosenParamValue);
		
		Entity similarCountry = null;
		
		while(rangeToCheckIn <= chosenParamValue) {		
			Query query = new Query("Country");
			query.addFilter(chosenParam, Query.FilterOperator.GREATER_THAN , chosenParamValue - rangeToCheckIn);
			query.addFilter(chosenParam, Query.FilterOperator.LESS_THAN , chosenParamValue + rangeToCheckIn);
			query.addFilter(chosenParam, Query.FilterOperator.NOT_EQUAL, chosenParamValue);
			similarCountry = DSInterface.findEntity(query);
			if(similarCountry != null)
				break;
			rangeToCheckIn *= 2;
		}

		return similarCountry;
	}
	
	private void generateRandomSimilaCriteria() {
		
		String[] comparisonParams = {"area","population"};
		Random randomGenerator = new Random();
		int random = randomGenerator.nextInt(comparisonParams.length);
		
		chosenParam = comparisonParams[random];
		rangeToCheckIn = 0.00;
		
		if(chosenParam.equals("area")) {
			rangeToCheckIn = 100;
		}else if(chosenParam.equals("population")) {
			rangeToCheckIn = 0.2;
		}
	}
}
