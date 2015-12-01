/**
 * 
 */
package org.figis.search.web.resource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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

	/**
	 * /{action}/index/{indexName}/domain/{factsheet domain}
	 * 
	 * 
	 * @param index
	 * @param domain
	 * @return
	 */
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateDomain() {
		return "you got me going";
	}

	/**
	 * /{action}/index/{indexName}/domain/{factsheet domain}
	 * 
	 * 
	 * @param index
	 * @param domain
	 * @return
	 */
	@GET
	@Path("/{index}/action/{action}/domain/{domain}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Work on the index api", response = IndexResponse.class)
	public IndexResponse updateDomain(
			@PathParam("index") @ApiParam(value = "the name of the index", required = true) String index,
			@PathParam("action") @ApiParam(value = "action", required = true) String action,
			@PathParam("domain") @ApiParam(value = "the factsheet domain", required = true) String domain) {

		return new IndexResponse();

		// return service.update(index, domain);
	}

	@GET
	@Path("/{index}/action/{action}/domain/{domain}/factsheet/{factsheet}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Work on the index api", response = IndexResponse.class)
	public IndexResponse updateFactsheet(
			@PathParam("index") @ApiParam(value = "the name of the index", required = true) String index,
			@PathParam("action") @ApiParam(value = "action", required = true) String action,
			@PathParam("domain") @ApiParam(value = "the factsheet domain", required = true) String domain,
			@PathParam("factsheet") @ApiParam(value = "the factsheet", required = true) String factsheet) {

		System.out.println("fiets");

		return service.update(index, domain, factsheet);
	}

}
