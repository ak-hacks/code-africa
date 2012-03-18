/**
 * 
 */
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

		if (country1 == null) {
			resp.setContentType("text/html");
			// Respond with a HTTP 400 bad request code
			resp.setStatus(400);
			resp.getWriter()
					.write("<b>Invalid arguments passed to the service.</b><br/>Please ensure you supply atleast one country name as a request parameter with name 'c1'.<br/>Example: http://thetimescodejam.appspot.com/findrecords?c1=Ghana&c2=Ethiopia");
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
			Gson jsonConverter = new Gson();
			String jsonResponseString = jsonConverter.toJson(listOfCountries);

			resp.getWriter().write(jsonResponseString);
		}
	}
}