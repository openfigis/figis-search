package org.figis.search.service;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
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

		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("full_name_e", "testField");
		doc.addField("id", "10529");
		doc.addField("url", "http://firms.fao.org/firms/resource/10529/en");

		String response = IndexServiceResponse.APPLIED_UPDATE;
		try {
			client.add(doc);
			client.commit();
		} catch (IOException | SolrServerException e) {
			throw new FigisSearchException(e);
		}
		return response;
	}

}
