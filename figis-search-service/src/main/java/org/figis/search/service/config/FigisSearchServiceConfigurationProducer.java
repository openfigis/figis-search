/**
 * 
 */
package org.figis.search.service.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.fao.fi.services.factsheet.client.FactsheetWebServiceClient;

/**
 * 
 * @author Erik van Ingen
 *
 */
@ApplicationScoped
public class FigisSearchServiceConfigurationProducer {

	@Inject
	FigisSearchServiceConfiguration c;

	@Produces
	@FigisSearchServiceQualifier
	public FactsheetWebServiceClient factsheetWebServiceClient() {
		return new FactsheetWebServiceClient(c.getFactsheetWebservcieEndPoint());
	}

	@Produces
	public SolrClient SolrClient() {
		return new HttpSolrClient(c.getHttpSolrEndPoint());

	}

}
