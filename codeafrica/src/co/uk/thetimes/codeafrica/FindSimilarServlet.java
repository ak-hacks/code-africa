/**
 * 
 */
package co.uk.thetimes.codeafrica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
			List<Entity> listOfCountries = new ArrayList<Entity>(2);

			Key keyForCountry1 = KeyFactory.createKey("Country", country1);
			Entity countryEntity1 = DSInterface.findEntity(keyForCountry1);
			Entity similarCountryEntity = findSimilarEntity(countryEntity1);
			
			listOfCountries.add(countryEntity1);
			listOfCountries.add(similarCountryEntity);
			
			// Prepare and write JSON string to response stream.
			Gson jsonConverter = new Gson();
			String jsonResponseString = jsonConverter.toJson(listOfCountries);;
			resp.getWriter().write(jsonResponseString);
		}
	}
	
	private Entity findSimilarEntity(Entity inputCountry) {
		double area = (Double)inputCountry.getProperty("area");
		logger.log(Level.INFO, "Searching for an entity that matches area :: " + area);
		
		Entity similarCountry = null;
		double rangeToCheckIn = 100.00;
		
		while(rangeToCheckIn <= area) {		
			Query query = new Query("Country");
			query.addFilter("area", Query.FilterOperator.GREATER_THAN , area - rangeToCheckIn);
			query.addFilter("area", Query.FilterOperator.LESS_THAN , area + rangeToCheckIn);
			query.addFilter("area", Query.FilterOperator.NOT_EQUAL, area);
			similarCountry = DSInterface.findEntity(query);
			if(similarCountry != null)
				break;
			rangeToCheckIn += 100;
		}

		return similarCountry;
	}
}
