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
		
		countryEntity.setProperty("Is African", country.isAfrican());
		countryEntity.setProperty("Area", country.getArea());
		countryEntity.setProperty("Cellular Subscribers", country.getCellularSubs());
		countryEntity.setProperty("Internet Users", country.getInternetUsers());
		countryEntity.setProperty("Population", country.getPopulation());
		countryEntity.setProperty("Alcohol Consumption", country.getAlcoholConsumption());
		countryEntity.setProperty("Facebook Penetration", country.getFacebookPenetration());
		countryEntity.setProperty("GDP Growth", country.getGdpGrowth());
		countryEntity.setProperty("Happy Planet", country.getHappyPlanet());
		countryEntity.setProperty("Broadband Speed", country.getBroadbandSpeed());
		countryEntity.setProperty("Coca Cola Consumption", country.getCocaColaConsumtion());
		countryEntity.setProperty("Urban Population", country.getUrbanPopulation());
		countryEntity.setProperty("Women in Parliament", country.getWomenInParliament());
		countryEntity.setProperty("Corruptoin Index", country.getCorruptionIndex());
		
		// Save data
		DSInterface.persistEntity(countryEntity);
	}
	
	private static Entity getCountryEntity(String name) {
	  	Key key = KeyFactory.createKey("Country",name);
	  	return DSInterface.findEntity(key);
	}
}