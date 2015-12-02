/**
 * 
 */
package org.figis.search.web.resource;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.figis.search.config.ref.FigisSearchException;
import org.figis.search.service.IndexResponse;
import org.figis.search.service.IndexResponse.OperationStatus;
import org.figis.search.service.IndexService;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 
 * The definition of the Index Resource. In other words, the IndexResource REST webservice.
 * 
 * @author Erik van Ingen
 *
 */
@ApplicationScoped
@Path(IndexResource.INDEX_PATH)
@Api(value = "caches", description = "Operations about the index")
public class IndexResource {

	public static final String INDEX_PATH = "/index";

	@Inject
	private IndexService service;

	@GET
	@Path("/{index}/action/{action}/domain/{domain}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update the index of a specific domain", response = IndexResponse.class)
	public IndexResponse updateDomain(
			@PathParam("index") @ApiParam(value = "the name of the index", required = true) String index,
			@PathParam("action") @ApiParam(value = "action", required = true) Action action,
			@PathParam("domain") @ApiParam(value = "the factsheet domain", required = true) String domain) {
		return processAction(action, () -> service.update(index, domain), () -> service.delete(index, domain));
	}

	@GET
	@Path("/{index}/action/{action}/domain/{domain}/factsheet/{factsheet}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update the index of a specific factsheet from a certain domain", response = IndexResponse.class)
	public IndexResponse updateFactsheet(
			@PathParam("index") @ApiParam(value = "the name of the index", required = true) String index,
			@PathParam("action") @ApiParam(value = "action", required = true) Action action,
			@PathParam("domain") @ApiParam(value = "the factsheet domain", required = true) String domain,
			@PathParam("factsheet") @ApiParam(value = "the factsheet", required = true) String factsheet) {
		return processAction(action, () -> service.update(index, domain, factsheet),
				() -> service.delete(index, domain, factsheet));
	}

	private IndexResponse processAction(Action action, Callable<IndexResponse> update, Callable<IndexResponse> delete) {
		try {
			IndexResponse r;
			switch (action) {
			case update:
				r = update.call();
				break;
			case delete:
				r = delete.call();
				break;
			default:
				r = new IndexResponse();
				r.setOperationStatus(OperationStatus.FAILED);
				r.setMessageList(new ArrayList<String>());
				r.getMessageList().add("No valid action specified");
				break;
			}
			return r;
		} catch (Exception e) {
			throw new FigisSearchException(e);
		}
	}

}
