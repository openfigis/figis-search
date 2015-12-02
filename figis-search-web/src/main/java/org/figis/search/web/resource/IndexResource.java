/**
 * 
 */
package org.figis.search.web.resource;

import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.fao.fi.factsheetwebservice.domain.FactsheetDomain;
import org.figis.search.config.elements.Index;
import org.figis.search.service.Action;
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
	public IndexResponse actionOnDomain(
			@PathParam("index") @ApiParam(value = "the name of the index", required = true) Index index,
			@PathParam("action") @ApiParam(value = "action", required = true) Action action,
			@PathParam("domain") @ApiParam(value = "the factsheet domain", required = true) FactsheetDomain domain) {

		if (index == null || action == null || domain == null) {
			return provideFailed();
		} else {
			return service.actionOnDomain(action, index, domain);
		}
	}

	/**
	 * 
	 * @param index
	 * @param action
	 * @param domain
	 * @param factsheet
	 * @return
	 */

	@GET
	@Path("/{index}/action/{action}/domain/{domain}/factsheet/{factsheet}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update the index of a specific factsheet from a certain domain", response = IndexResponse.class)
	public IndexResponse actionOnFactsheet(
			@PathParam("index") @ApiParam(value = "the name of the index", required = true) Index index,
			@PathParam("action") @ApiParam(value = "action", required = true) Action action,
			@PathParam("domain") @ApiParam(value = "the factsheet domain", required = true) FactsheetDomain domain,
			@PathParam("factsheet") @ApiParam(value = "the factsheet", required = true) String factsheet) {
		if (index == null || action == null || domain == null || StringUtils.isBlank(factsheet)) {
			return provideFailed();
		} else {
			IndexResponse r;
			switch (action) {
			case update:
				r = service.updateFactsheet(index, domain, factsheet);
				break;
			case delete:
				r = service.deleteFactsheet(index, domain, factsheet);
				break;
			case unknown:
			default:
				r = provideFailed();
				break;
			}
			return r;
		}
	}

	private IndexResponse provideFailed() {
		IndexResponse r = new IndexResponse();
		r.setOperationStatus(OperationStatus.FAILED);
		r.setMessageList(new ArrayList<String>());
		r.getMessageList().add("No valid action specified");
		return r;
	}

}
