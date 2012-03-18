/**
 * 
 */
package co.uk.thetimes.codeafrica.datastore;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

/**
 * Data store interface for CRUD ops on data store entities
 * 
 * @author anuragkapur
 */
public class DSInterface {

	private static final Logger logger = Logger.getLogger(DSInterface.class
			.getCanonicalName());
	private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public static void persistEntity(Entity entity) {
		logger.log(Level.INFO, "Saving entity");
		datastore.put(entity);
	}

	public static void deleteEntity(Key key) {
		logger.log(Level.INFO, "Deleting entity");
		datastore.delete(key);
	}

	public static Entity findEntity(Key key) {
		logger.log(Level.INFO, "Search the entity");
		try {
			return datastore.get(key);
		} catch (EntityNotFoundException e) {
			logger.log(Level.WARNING, "Cannot find entity");
			return null;
		}
	}

	public static DatastoreService getDatastoreServiceInstance() {
		return datastore;
	}
}