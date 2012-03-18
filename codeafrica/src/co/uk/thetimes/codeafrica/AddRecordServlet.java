package co.uk.thetimes.codeafrica;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.uk.thetimes.codeafrica.dao.CountryDAO;
import co.uk.thetimes.codeafrica.model.Country;

/**
 * Adds a record using data passed to the service via request parameters
 * 
 * @author anuragkapur
 */

@SuppressWarnings("serial")
public class AddRecordServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String countryName = req.getParameter("name");

		if (countryName != null) {
			Country country = new Country();
			country.setName(countryName);
			country.setArea(req.getParameter("area"));
			country.setPopulation(req.getParameter("pop"));
			country.setPopulationDensity(req.getParameter("popden"));
			country.setLiteracyRate(req.getParameter("litrate"));
			country.setUrbanization(req.getParameter("urb"));
			country.setDivorceRate(req.getParameter("divrate"));
			country.setCountryAge(req.getParameter("age"));
			country.setNoOfMacdonalds(req.getParameter("nom"));
			country.setCocaColaConsumtion(req.getParameter("ccc"));
			country.setWomenInParliament(req.getParameter("wip"));
			country.setAlcoholConsumption(req.getParameter("alcc"));
			country.setAidPercentGovSpending(req.getParameter("aid"));
			country.setAverageBroadbandSpeed(req.getParameter("bbspeed"));

			// Add or update country records
			CountryDAO.addOrUpdateCountry(country);
		} else {
			resp.setContentType("text/html");
			// Respond with a HTTP 400 bad request code
			resp.setStatus(400);
			resp.getWriter()
					.write("<b>Invalid arguments.</b><br/>Supplying a country name parameter is mandatory.<br/>Example: http://thetimescodejam.appspot.com/addrecord?name=Central African Republic&area=622984&pop=4.5");
		}
	}
}
