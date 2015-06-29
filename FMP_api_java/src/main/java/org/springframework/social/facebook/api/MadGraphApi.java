package org.springframework.social.facebook.api;

import org.springframework.util.MultiValueMap;

public interface MadGraphApi extends GraphApi{
	/**
	 * Updates data of an object.
	 * Requires appropriate permission to update to the object connection.
	 * @param objectId the object ID to update.
	 * @param data the data to update in the object.
	 * @return true if update was successful
	 */
	boolean update(String objectId, MultiValueMap<String, Object> data);
}
