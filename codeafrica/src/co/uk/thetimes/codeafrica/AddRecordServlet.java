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
			
			String areaString = req.getParameter("area");
			if(areaString != null && !areaString.equals(""))
				country.setArea(Double.parseDouble(areaString));
			
			String populationString = req.getParameter("pop");
			if(populationString != null && !populationString.equals(""))
				country.setPopulation(Double.parseDouble(populationString));
			
			String populationDensityString = req.getParameter("popden");
			if(populationDensityString != null && !populationDensityString.equals(""))
				country.setPopulationDensity(Double.parseDouble(populationDensityString));
			
			String literacyRateString = req.getParameter("litrate");
			if(literacyRateString != null && !literacyRateString.equals(""))
				country.setLiteracyRate(Double.parseDouble(literacyRateString));
			
			String urbanizationString = req.getParameter("urb");
			if(urbanizationString != null && !urbanizationString.equals(""))
				country.setUrbanization(Double.parseDouble(urbanizationString));
			
			String divorceRateString = req.getParameter("divrate");
			if(divorceRateString != null && !divorceRateString.equals(""))
				country.setDivorceRate(Double.parseDouble(divorceRateString));
			
			String countryAgeString = req.getParameter("age");
			if(countryAgeString != null && !countryAgeString.equals(""))
				country.setCountryAge(Double.parseDouble(countryAgeString));
			
			String noOfMacdonaldsString = req.getParameter("nom");
			if(noOfMacdonaldsString != null && !noOfMacdonaldsString.equals(""))
				country.setNoOfMacdonalds(Double.parseDouble(noOfMacdonaldsString));
			
			String cocaColaConsumptionString = req.getParameter("ccc");
			if(cocaColaConsumptionString != null && !cocaColaConsumptionString.equals(""))
				country.setCocaColaConsumtion(Double.parseDouble(cocaColaConsumptionString));
			
			String womenInParliamentString = req.getParameter("wip");
			if(womenInParliamentString != null && !womenInParliamentString.equals(""))
				country.setWomenInParliament(Double.parseDouble(womenInParliamentString));
			
			String alcoholConsumptionString = req.getParameter("alcc");
			if(alcoholConsumptionString != null && !alcoholConsumptionString.equals(""))
				country.setAlcoholConsumption(Double.parseDouble(alcoholConsumptionString));
			
			String aidPercentString = req.getParameter("aid");
			if(aidPercentString != null && !aidPercentString.equals(""))
				country.setAidPercentGovSpending(Double.parseDouble(aidPercentString));
			
			String averageBroadbandString = req.getParameter("bbspeed");
			if(averageBroadbandString != null && !averageBroadbandString.equals(""))
				country.setAverageBroadbandSpeed(Double.parseDouble(averageBroadbandString));

			// Add or update country records
			CountryDAO.addOrUpdateCountry(country);
			
			resp.setContentType("text/html");
			resp.setStatus(200);
			resp.getWriter().write("Data added successfully");
		} else {
			resp.setContentType("text/html");
			// Respond with a HTTP 400 bad request code
			resp.setStatus(400);
			resp.getWriter()
					.write("<b>Invalid arguments.</b><br/>Supplying a country name parameter is mandatory.<br/>Example: http://thetimescodejam.appspot.com/addrecord?name=Central African Republic&area=622984&pop=4.5");
		}
	}
}
