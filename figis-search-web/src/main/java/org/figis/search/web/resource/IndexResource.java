/**
 * 
 */
package org.figis.search.web.resource;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.figis.search.service.SingleResponse;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Erik van Ingen
 *
 */
@Slf4j
@ApplicationScoped
@Path(IndexResource.INDEX_PATH)
@Api(value = "caches", description = "Operations about the index")
public class IndexResource {

	public static final String INDEX_PATH = "/index";

	// @Inject
	// private IndexService service;
	//
	// @Inject
	// private Configuration configuration;

	@GET
	@Path("clear")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Work on the index api", response = SingleResponse.class)
	public SingleResponse clear() {
		return new SingleResponse();
	}

}
