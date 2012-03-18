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
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

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
	
	public static Entity findEntity(Query query) {
		logger.log(Level.INFO, "Searching for an entity that matches :: " + query.toString());
		PreparedQuery pq = datastore.prepare(query);
		for (Entity result : pq.asIterable()) {
			logger.log(Level.INFO, "Match Found :: " + result.toString());
			return result;
		}
		return null;
	}

	public static DatastoreService getDatastoreServiceInstance() {
		return datastore;
	}
}