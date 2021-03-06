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
import com.google.appengine.api.datastore.Text;
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
		//country1 = country1.replaceAll("+", " ");

		if (country1 == null) {
			resp.setContentType("text/html");
			// Respond with a HTTP 400 bad request code
			resp.setStatus(400);
			resp.getWriter()
					.write("<b>Invalid arguments passed to the service.</b><br/>Please ensure you supply a country name.<br/>Example: TODO");
		} else {
			List<Entity> listOfCountries = new ArrayList<Entity>();

			Key keyForCountry1 = KeyFactory.createKey("Country", country1);
			Entity countryEntity1 = DSInterface.findEntity(keyForCountry1);
			Entity similarCountryEntity = findSimilarEntity(countryEntity1);
			
			listOfCountries.add(countryEntity1);
			listOfCountries.add(similarCountryEntity);
			
			Entity similarSearchCriteria = new Entity("search criteria");
			similarSearchCriteria.setProperty("prameter", chosenParam);
			listOfCountries.add(similarSearchCriteria);
			
			List<String> countryCoordinates1 = getCountryCoordinates(country1);
			List<String> countryCoordinates2 = getCountryCoordinates(similarCountryEntity.getKey().getName());
			
			Entity countryMetadata1 = new Entity("Cordinate Data Input Country");
			
			try {
				countryMetadata1.setProperty("name", countryCoordinates1.get(0));
				Text countryCoordTxt1 = new Text(countryCoordinates1.get(1));
				Text countryCoordTxt2 = new Text(countryCoordinates1.get(2));
				countryMetadata1.setProperty("centreCoordinates", countryCoordTxt1);
				countryMetadata1.setProperty("boundaryCoordinates", countryCoordTxt2);
				listOfCountries.add(countryMetadata1);
			}catch(Exception e) {
				e.printStackTrace();
			}

			Entity countryMetadata2 = new Entity("Cordinate Data Similar Country");
			
			try {
				countryMetadata2.setProperty("name", countryCoordinates2.get(0));
				Text countryCoordTxt3 = new Text(countryCoordinates2.get(1));
				Text countryCoordTxt4 = new Text(countryCoordinates2.get(2));
				countryMetadata2.setProperty("centreCoordinates", countryCoordTxt3);
				countryMetadata2.setProperty("boundaryCoordinates", countryCoordTxt4);
				listOfCountries.add(countryMetadata2);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
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
		
		while((chosenParamValue - (rangeToCheckIn/2)) > 0) {		
			Query query = new Query("Country");
			query.addFilter("Is African", Query.FilterOperator.EQUAL, 1);
			query.addFilter(chosenParam, Query.FilterOperator.GREATER_THAN , chosenParamValue - rangeToCheckIn);
			query.addFilter(chosenParam, Query.FilterOperator.LESS_THAN , chosenParamValue + rangeToCheckIn);
			query.addFilter(chosenParam, Query.FilterOperator.NOT_EQUAL, chosenParamValue);
			similarCountry = DSInterface.findEntity(query);
			if(similarCountry != null)
				break;
			rangeToCheckIn *= 2;
			logger.log(Level.INFO, "range to check in " + rangeToCheckIn);
		}

		return similarCountry;
	}
	
	private void generateRandomSimilaCriteria() {
		
		String[] comparisonParams = {"Area","Population"};
		Random randomGenerator = new Random();
		int random = randomGenerator.nextInt(comparisonParams.length);
		
		chosenParam = comparisonParams[random];
		rangeToCheckIn = 0.00;
		
		if(chosenParam.equals("Area")) {
			rangeToCheckIn = 2000;
		}else if(chosenParam.equals("Population")) {
			rangeToCheckIn = 50000;
		}
	}
	
	private List<String> getCountryCoordinates(String countryName) {
		
		int i=0;
		
		for(i=0; i<CountryMetadataServlet.countryNames.length; i++) {
			if(countryName.equalsIgnoreCase(CountryMetadataServlet.countryNames[i])) {
				break;
			}
		}
		
		List<String> countryCoordinates = new ArrayList<String>(3);
		
		try {
			countryCoordinates.add(countryName);
			countryCoordinates.add(CountryMetadataServlet.centreCoordinates[i]);
			countryCoordinates.add(CountryMetadataServlet.boundaryCoordinates[i]);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return countryCoordinates;
	}
}
