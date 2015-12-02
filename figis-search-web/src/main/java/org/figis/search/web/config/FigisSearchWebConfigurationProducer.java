/**
 * 
 */
package org.figis.search.web.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.figis.search.service.config.FigisSearchServiceConfiguration;

/**
 * 
 * @author Erik van Ingen
 *
 */
@ApplicationScoped
public class FigisSearchWebConfigurationProducer {

	@Context
	private ServletContext context;

	public final static String FIGIS_SEARCH_REST_URL = "figis-search-rest-url";
	public final static String FACTSHEET_WS_ENDPOINT = "factsheet-ws-endpoint";
	public final static String SOLR_ENDPOINT = "solr-endpoint";

	@Produces
	@Singleton
	public FigisSearchWebConfiguration buildConfiguration() {
		FigisSearchWebConfiguration c = new FigisSearchWebConfiguration();
		c.setFigisRestUrl(context.getInitParameter(FIGIS_SEARCH_REST_URL));
		return c;
	}

	@Produces
	@Singleton
	public FigisSearchServiceConfiguration figisSearchServiceConfiguration() {
		FigisSearchServiceConfiguration c = new FigisSearchServiceConfiguration();
		c.setFactsheetWebservcieEndPoint(context.getInitParameter(FACTSHEET_WS_ENDPOINT));
		c.setHttpSolrEndPoint(context.getInitParameter(SOLR_ENDPOINT));
		return c;
	}

}
