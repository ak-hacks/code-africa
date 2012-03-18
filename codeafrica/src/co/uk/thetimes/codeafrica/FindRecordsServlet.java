/**
 * 
 */
package co.uk.thetimes.codeafrica;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.uk.thetimes.codeafrica.datastore.DSInterface;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;

/**
 * @author anuragkapur
 * 
 */

@SuppressWarnings("serial")
public class FindRecordsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String country1 = req.getParameter("c1");
		String country2 = req.getParameter("c2");
		
		Key keyForCountry1 = KeyFactory.createKey("Country",country1);
		Key keyForCountry2 = KeyFactory.createKey("Country",country2);
		
		Entity countryEntity1 = DSInterface.findEntity(keyForCountry1);
		countryEntity1.getProperties().keySet();
		
		Gson jsonConverter = new Gson();
		String jsonResponseString = jsonConverter.toJson(countryEntity1);
		
		resp.getWriter().write(jsonResponseString);
	}
}