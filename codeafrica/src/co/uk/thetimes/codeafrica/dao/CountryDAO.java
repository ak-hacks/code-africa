package co.uk.thetimes.codeafrica.dao;

import co.uk.thetimes.codeafrica.datastore.DSInterface;
import co.uk.thetimes.codeafrica.model.Country;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * @author anuragkapur
 *
 */
public class CountryDAO {
	
	public static void addOrUpdateCountry(Country country) {
		Entity countryEntity = getCountryEntity(country.getName());
		
		if(countryEntity == null) {
			countryEntity = new Entity("Country", country.getName());
		}
		
		countryEntity.setProperty("area", country.getArea());
		countryEntity.setProperty("population", country.getPopulation());
		countryEntity.setProperty("population_density",country.getPopulationDensity());
		countryEntity.setProperty("literacy_rate",country.getLiteracyRate());
		countryEntity.setProperty("urbanization",country.getUrbanization());
		countryEntity.setProperty("divorce_rate",country.getDivorceRate());
		countryEntity.setProperty("country_age",country.getCountryAge());
		countryEntity.setProperty("no_of_macdonalds",country.getNoOfMacdonalds());
		countryEntity.setProperty("coca_cola_consumption",country.getCocaColaConsumtion());
		countryEntity.setProperty("women_in_parliament",country.getWomenInParliament());
		countryEntity.setProperty("alcohol_consumption",country.getAlcoholConsumption());
		countryEntity.setProperty("aid_percent_gov_spending",country.getAidPercentGovSpending());
		countryEntity.setProperty("average_broadband_speed",country.getAverageBroadbandSpeed());
		
		// Save data
		DSInterface.persistEntity(countryEntity);
	}
	
	private static Entity getCountryEntity(String name) {
	  	Key key = KeyFactory.createKey("Country",name);
	  	return DSInterface.findEntity(key);
	}
}