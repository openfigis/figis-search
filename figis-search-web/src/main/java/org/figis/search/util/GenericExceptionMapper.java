/**
 * 
 */
package org.figis.search.util;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Erik van Ingen
 *
 */
@Slf4j
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

	public Response toResponse(Exception ex) {
		if (ex instanceof WebApplicationException) {
			return ((WebApplicationException) ex).getResponse();
		}
		log.error("Exception", ex);
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).build();
	}
}
