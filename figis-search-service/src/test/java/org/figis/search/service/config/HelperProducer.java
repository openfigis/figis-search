/**
 * 
 */
package org.figis.search.service.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 * 
 * @author Erik van Ingen
 *
 */
@ApplicationScoped
public class HelperProducer {

	@Produces
	public FigisSearchServiceConfiguration buildConfiguration() {
		FigisSearchServiceConfiguration c = new FigisSearchServiceConfiguration();
		c.setFactsheetWebservcieEndPoint("http://figisapps.fao.org/figis/ws/factsheets/");
		c.setHttpSolrEndPoint("http://hqldvfigis2:8983/solr/factsheet");
		return c;
	}

}
