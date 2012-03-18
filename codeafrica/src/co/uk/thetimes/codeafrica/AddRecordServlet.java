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

			String isAfricanString = req.getParameter("isAfrican");
			if(isAfricanString != null && !isAfricanString.equals(""))
				country.setAfrican(Integer.parseInt(isAfricanString));
			
			String areaString = req.getParameter("area");
			if(areaString != null && !areaString.equals(""))
				country.setArea(Double.parseDouble(areaString));
			
			String cellularSubs = req.getParameter("cellsubs");
			if(cellularSubs != null && !cellularSubs.equals(""))
				country.setCellularSubs(Double.parseDouble(cellularSubs));
			
			String internetUsers = req.getParameter("internet");
			if(internetUsers != null && !internetUsers.equals(""))
				country.setInternetUsers(Double.parseDouble(internetUsers));

			String population = req.getParameter("population");
			if(population != null && !population.equals(""))
				country.setPopulation(Double.parseDouble(population));

			String alcoholConsumption = req.getParameter("alc");
			if(alcoholConsumption != null && !alcoholConsumption.equals(""))
				country.setAlcoholConsumption(Double.parseDouble(alcoholConsumption));
			
			String facebookPenetration = req.getParameter("fb");
			if(facebookPenetration != null && !facebookPenetration.equals(""))
				country.setFacebookPenetration(Double.parseDouble(facebookPenetration));
			
			String gdpGrowth = req.getParameter("gdp");
			if(gdpGrowth != null && !gdpGrowth.equals(""))
				country.setGdpGrowth(Double.parseDouble(gdpGrowth));

			String happyPlanet = req.getParameter("happyplanet");
			if(happyPlanet != null && !happyPlanet.equals(""))
				country.setHappyPlanet(Double.parseDouble(happyPlanet));

			String broadbandSpeed = req.getParameter("bbspead");
			if(broadbandSpeed != null && !broadbandSpeed.equals(""))
				country.setBroadbandSpeed(Double.parseDouble(broadbandSpeed));
			
			String cocaColaConsumtion = req.getParameter("ccc");
			if(cocaColaConsumtion != null && !cocaColaConsumtion.equals(""))
				country.setCocaColaConsumtion(Double.parseDouble(cocaColaConsumtion));
			
			String urbanPopulation = req.getParameter("urbanpop");
			if(urbanPopulation != null && !urbanPopulation.equals(""))
				country.setUrbanPopulation(Double.parseDouble(urbanPopulation));
			
			String womenInParliament = req.getParameter("wip");
			if(womenInParliament != null && !womenInParliament.equals(""))
				country.setWomenInParliament(Double.parseDouble(womenInParliament));
			
			String corruptionIndex = req.getParameter("ci");
			if(corruptionIndex != null && !corruptionIndex.equals(""))
				country.setCorruptionIndex(Double.parseDouble(corruptionIndex));
			
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
