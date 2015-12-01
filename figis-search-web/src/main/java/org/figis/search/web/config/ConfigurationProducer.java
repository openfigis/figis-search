/**
 * 
 */
package org.figis.search.web.config;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

/**
 * 
 * @author Erik van Ingen
 *
 */
// @ApplicationScoped
public class ConfigurationProducer {

	@Context
	// @Inject
	private ServletContext context;

	public final static String FIGIS_SEARCH_REST_URL = "figis-search-rest-url";

	@Produces
	@Singleton
	public FigisSearchWebConfiguration buildConfiguration() {
		FigisSearchWebConfiguration c = new FigisSearchWebConfiguration();
		c.setFigisRestUrl(context.getInitParameter(FIGIS_SEARCH_REST_URL));
		return c;
	}

}
