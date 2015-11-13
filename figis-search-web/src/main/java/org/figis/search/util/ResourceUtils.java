/**
 * 
 */
package org.figis.search.util;

import javax.ws.rs.WebApplicationException;

/**
 * @author Erik van Ingen
 *
 */
public class ResourceUtils {

	public static <T> T checkNotFound(T item) {
		if (item == null)
			throw new WebApplicationException(404);
		return item;
	}

}
