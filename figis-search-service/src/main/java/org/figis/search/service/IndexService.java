package org.figis.search.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.fao.fi.factsheetwebservice.domain.FactsheetDiscriminator;
import org.fao.fi.factsheetwebservice.domain.FactsheetDomain;
import org.fao.fi.factsheetwebservice.domain.FactsheetLanguage;
import org.fao.fi.factsheetwebservice.domain.FactsheetList;
import org.fao.fi.factsheetwebservice.domain.LanguageList;
import org.fao.fi.services.factsheet.client.FactsheetClientException;
import org.fao.fi.services.factsheet.client.FactsheetWebServiceClient;
import org.fao.fi.services.factsheet.logic.FactsheetUrlComposer;
import org.fao.fi.services.factsheet.logic.FactsheetUrlComposerImpl;
import org.figis.search.service.IndexResponse.OperationStatus;
import org.figis.search.service.indexing.Doc2SolrInputDocument;
import org.figis.search.service.util.FactsheetId;
import org.w3c.dom.Document;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IndexService {

	/**
	 * This dependency is a short cut. The factsheet webservice needs to be extended with this function
	 */

	FactsheetUrlComposer factsheetUrlComposer = new FactsheetUrlComposerImpl();
	FactsheetId u = new FactsheetId();
	Doc2SolrInputDocument prepare = new Doc2SolrInputDocument();
	FactsheetWebServiceClient fc = new FactsheetWebServiceClient("http://www.fao.org/figis/ws/factsheets/");

	/**
	 * update or delete index of a single factsheet /{action}/index/{indexName}/domain/{factsheet
	 * domain}/factsheet/{factsheetID}
	 */
	public IndexResponse update(String indexName, String domain) {
		FactsheetDomain d = FactsheetDomain.parseDomain(domain);
		FactsheetList l = fc.retrieveFactsheetListPerDomain(d);
		IndexResponse domainIndexResponse = new IndexResponse();
		domainIndexResponse.setMessageList(new ArrayList<String>());
		boolean perfect = true;
		for (FactsheetDiscriminator ds : l.getFactsheetList()) {
			IndexResponse singleFactsheetResponse = update(indexName, domain, ds.getFactsheet());
			domainIndexResponse.getMessageList().addAll(singleFactsheetResponse.getMessageList());
			if (singleFactsheetResponse.getOperationStatus().equals(IndexResponse.OperationStatus.PARTLY_SUCCEEDED)
					|| singleFactsheetResponse.getOperationStatus().equals(IndexResponse.OperationStatus.FAILED)) {
				domainIndexResponse.setOperationStatus(IndexResponse.OperationStatus.PARTLY_SUCCEEDED);
				perfect = false;
			}
		}
		if (perfect) {
			domainIndexResponse.setOperationStatus(IndexResponse.OperationStatus.SUCCEEDED);
		}
		return domainIndexResponse;
	}

	/**
	 * update or delete index of a single factsheet /{action}/index/{indexName}/domain/{factsheet
	 * domain}/factsheet/{factsheetID}
	 */
	public IndexResponse update(String indexName, String domain, String factsheet) {
		// List<SolrInputDocument> docs =
		FactsheetIndexResponse r = composeDoc(domain, factsheet);
		SolrClient client = new HttpSolrClient("http://hqldvfigis2:8983/solr/factsheet");
		IndexResponse s = new IndexResponse();
		s.setMessageList(new ArrayList<String>());
		s.setOperationStatus(IndexResponse.OperationStatus.SUCCEEDED);
		boolean perfect = true;
		for (SolrInputDocument solrInputDocument : r.getSolrInputDocumentList()) {
			try {
				client.add(solrInputDocument);
				client.commit();
			} catch (IOException | SolrServerException e) {
				s.getMessageList().add(e.getMessage());
				s.setOperationStatus(IndexResponse.OperationStatus.PARTLY_SUCCEEDED);
				perfect = false;
			}
		}
		try {
			client.close();
		} catch (IOException e) {
			s.getMessageList().add(e.getMessage());
			s.setOperationStatus(IndexResponse.OperationStatus.PARTLY_SUCCEEDED);
			perfect = false;
		}
		if (perfect) {
			s.setOperationStatus(IndexResponse.OperationStatus.SUCCEEDED);
		}
		return s;
	}

	private FactsheetIndexResponse composeDoc(String domain, String factsheet) {
		FactsheetDomain d = FactsheetDomain.parseDomain(domain);
		LanguageList ll = fc.retrieveLanguageListInDomain4ThisFactsheet(d, factsheet);
		List<SolrInputDocument> solrInputDocuments = new ArrayList<SolrInputDocument>();
		FactsheetIndexResponse r = new FactsheetIndexResponse(new ArrayList<SolrInputDocument>());
		r.setMessageList(new ArrayList<String>());
		r.setOperationStatus(OperationStatus.SUCCEEDED);

		for (FactsheetLanguage language : ll.getLanguageList()) {
			try {
				Document doc = fc.retrieveFactsheet(factsheet, d, language);
				// Document doc = loadXML();
				FactsheetDiscriminator disc = new FactsheetDiscriminator(language, d, factsheet);

				SolrInputDocument sd = prepare.extract(doc).basedOn(domain);
				sd.addField("id", u.domain(domain).factsheet(factsheet).lang(language.toString()).compose());
				sd.addField("dataset", "firms");
				sd.addField("language", language.toString());
				sd.addField("url", factsheetUrlComposer.composeFromDomainAndFactsheet(disc).replace("/xml", ""));
				r.getSolrInputDocumentList().add(sd);
				solrInputDocuments.add(sd);
			} catch (FactsheetClientException e) {
				r.setOperationStatus(OperationStatus.PARTLY_SUCCEEDED);
				r.getMessageList().add(e.getMessage());
			}
		}
		return r;

	}

	// public static Document loadXML() {
	//
	// Source source = new StreamSource(new File("src/test/resources/resource-10529-en.xml"));
	// DOMResult result = new DOMResult();
	// try {
	// TransformerFactory.newInstance().newTransformer().transform(source, result);
	// } catch (TransformerException | TransformerFactoryConfigurationError e) {
	// fail();
	// }
	// return (Document) result.getNode();
	// }

}
