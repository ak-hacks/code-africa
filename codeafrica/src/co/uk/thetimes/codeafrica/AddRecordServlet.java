/**
 * 
 */
package co.uk.thetimes.codeafrica;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.uk.thetimes.codeafrica.dao.CountryDAO;
import co.uk.thetimes.codeafrica.model.Country;

/**
 * @author anuragkapur
 * 
 */

@SuppressWarnings("serial")
public class AddRecordServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Country country = new Country();
		country.setName(req.getParameter("name"));
		country.setArea(req.getParameter("area"));
		country.setPopulation(req.getParameter("population"));
		country.setLiteracyRate(req.getParameter("literacyrate"));
		// Add or update country records
		CountryDAO.addOrUpdateCountry(country);
	}
}
