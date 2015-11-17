package org.figis.search.service;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.figis.search.config.ref.FigisSearchException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IndexService {

	/**
	 * update or delete index of a single factsheet /{action}/index/{indexName}/domain/{factsheet
	 * domain}/factsheet/{factsheetID}
	 */

	public String update(String indexName, String domain, String factsheetID) {

		SolrClient client = new HttpSolrClient("http://hqldvfigis2:6101/solr/RefPub");
		String response = IndexServiceResponse.APPLIED_UPDATE;
		try {
			QueryResponse resp = client.query(new SolrQuery("select?q=*%3A*&wt=json&indent=true"));

		} catch (SolrServerException e) {
			throw new FigisSearchException(e);
		}
		return response;
	}

}
