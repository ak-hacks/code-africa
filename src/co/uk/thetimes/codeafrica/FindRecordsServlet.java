package co.uk.thetimes.codeafrica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.uk.thetimes.codeafrica.datastore.DSInterface;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;

/**
 * Finds records for upto two Country entities based on names passed as HTTP
 * Request parameters to the service
 * 
 * @author anuragkapur
 */

@SuppressWarnings("serial")
public class FindRecordsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String country1 = req.getParameter("c1");
		String country2 = req.getParameter("c2");

		Gson jsonConverter = new Gson();
		
		if (country1 == null) {
			List<Entity> countries =  DSInterface.listEntities("Country");
			resp.setContentType("text/plain");
			
			StringBuffer countriesString = new StringBuffer();
			String countryName;
			
			for (Entity entity : countries) {
				countryName = entity.getKey().getName();
				countriesString.append(""+countryName);
				countriesString.append("\n");
			}
			
			// Respond with a HTTP 200 bad request code
			resp.setStatus(200);
			resp.getWriter().write(countriesString.toString());
		} else {
			List<Entity> listOfCountries = new ArrayList<Entity>(2);

			Key keyForCountry1 = KeyFactory.createKey("Country", country1);
			Entity countryEntity1 = DSInterface.findEntity(keyForCountry1);
			listOfCountries.add(countryEntity1);

			if (country2 != null) {
				Entity countryEntity2;
				Key keyForCountry2 = KeyFactory.createKey("Country", country2);
				countryEntity2 = DSInterface.findEntity(keyForCountry2);
				listOfCountries.add(countryEntity2);
			}

			// Prepare and write JSON string to response stream.
			String jsonResponseString = jsonConverter.toJson(listOfCountries);
			resp.setContentType("text/html");
			resp.setStatus(200);
			resp.getWriter().write(jsonResponseString);
		}
	}
}