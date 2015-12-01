/**
 * 
 */
package org.figis.search.web.resource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.figis.search.service.IndexResponse;
import org.figis.search.service.IndexService;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

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

	@Inject
	private IndexService service;
	@Inject
	private ServletContext context;

	@GET
	@Path("/{index}/action/{action}/domain/{domain}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update the index of a specific domain", response = IndexResponse.class)
	public IndexResponse updateDomain(
			@PathParam("index") @ApiParam(value = "the name of the index", required = true) String index,
			@PathParam("action") @ApiParam(value = "action", required = true) String action,
			@PathParam("domain") @ApiParam(value = "the factsheet domain", required = true) String domain) {

		return new IndexResponse();

	}

	@GET
	@Path("/{index}/action/{action}/domain/{domain}/factsheet/{factsheet}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update the index of a specific factsheet from a certain domain", response = IndexResponse.class)
	public IndexResponse updateFactsheet(
			@PathParam("index") @ApiParam(value = "the name of the index", required = true) String index,
			@PathParam("action") @ApiParam(value = "action", required = true) String action,
			@PathParam("domain") @ApiParam(value = "the factsheet domain", required = true) String domain,
			@PathParam("factsheet") @ApiParam(value = "the factsheet", required = true) String factsheet) {

		return service.update(index, domain, factsheet);
	}

}
