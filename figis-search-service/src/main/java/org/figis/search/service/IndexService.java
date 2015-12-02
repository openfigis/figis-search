package org.figis.search.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
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
import org.figis.search.config.elements.Index;
import org.figis.search.config.ref.FigisSearchException;
import org.figis.search.service.IndexResponse.OperationStatus;
import org.figis.search.service.config.FigisSearchServiceQualifier;
import org.figis.search.service.indexing.Doc2SolrInputDocument;
import org.figis.search.service.util.FactsheetId;
import org.w3c.dom.Document;

public class IndexService {

	@Inject
	@FigisSearchServiceQualifier
	private FactsheetWebServiceClient fc;

	@Inject
	private SolrClient client;

	/**
	 * This dependency is a short cut. The factsheet webservice needs to be extended with this function
	 */
	private FactsheetUrlComposer factsheetUrlComposer = new FactsheetUrlComposerImpl();
	private FactsheetId u = new FactsheetId();
	private Doc2SolrInputDocument prepare = new Doc2SolrInputDocument();

	/**
	 * update or delete index of a single factsheet /{action}/index/{indexName}/domain/{factsheet
	 * domain}/factsheet/{factsheetID}
	 */
	public IndexResponse actionOnDomain(Action action, Index indexName, FactsheetDomain domain) {
		FactsheetList l = fc.retrieveFactsheetListPerDomain(domain);
		IndexResponse domainIndexResponse = new IndexResponse();
		domainIndexResponse.setMessageList(new ArrayList<String>());
		boolean perfect = true;
		for (FactsheetDiscriminator ds : l.getFactsheetList()) {
			IndexResponse singleFactsheetResponse = null;
			switch (action) {
			case update:
				singleFactsheetResponse = updateFactsheet(indexName, domain, ds.getFactsheet());
				break;
			case delete:
				singleFactsheetResponse = deleteFactsheet(indexName, domain, ds.getFactsheet());
				break;
			case unknown:
			default:
				throw new FigisSearchException(Action.unknown.name());
			}
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
	public IndexResponse updateFactsheet(Index indexName, FactsheetDomain domain, String factsheet) {
		FactsheetIndexResponse r = composeDoc(domain, factsheet);
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
		if (perfect) {
			s.setOperationStatus(IndexResponse.OperationStatus.SUCCEEDED);
		}
		return s;
	}

	private FactsheetIndexResponse composeDoc(FactsheetDomain domain, String factsheet) {

		LanguageList ll = fc.retrieveLanguageListInDomain4ThisFactsheet(domain, factsheet);
		List<SolrInputDocument> solrInputDocuments = new ArrayList<SolrInputDocument>();
		FactsheetIndexResponse r = new FactsheetIndexResponse(new ArrayList<SolrInputDocument>());
		r.setMessageList(new ArrayList<String>());
		r.setOperationStatus(OperationStatus.SUCCEEDED);

		for (FactsheetLanguage language : ll.getLanguageList()) {
			try {
				Document doc = fc.retrieveFactsheet(factsheet, domain, language);
				// Document doc = loadXML();
				FactsheetDiscriminator disc = new FactsheetDiscriminator(language, domain, factsheet);

				SolrInputDocument sd = prepare.extract(doc).basedOn(domain);
				sd.addField("id", u.domain(domain).factsheet(factsheet).lang(language.toString()).compose());
				sd.addField("provenance", "firms");
				// sd.addField("domain", domain);
				sd.addField("language", language.toString());
				sd.addField("figisid", factsheet);
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

	/**
	 * Delete tries to delete all possible language objects for that factsheet object. Possible languages are defined
	 * in: @FactsheetLanguage
	 * 
	 * 
	 * @param action
	 * @param index
	 * @param domain
	 * @param factsheet
	 * @return
	 */
	public IndexResponse deleteFactsheet(Index index, FactsheetDomain domain, String factsheet) {
		IndexResponse s = new IndexResponse();
		s.setMessageList(new ArrayList<String>());
		s.setOperationStatus(IndexResponse.OperationStatus.SUCCEEDED);
		boolean perfect = true;
		for (FactsheetLanguage language : FactsheetLanguage.values()) {
			try {
				client.deleteById(u.domain(domain).factsheet(factsheet).lang(language.toString()).compose());
				client.commit();
			} catch (IOException | SolrServerException e) {
				s.getMessageList().add(e.getMessage());
				s.setOperationStatus(IndexResponse.OperationStatus.PARTLY_SUCCEEDED);
				perfect = false;
			}
		}
		if (perfect) {
			s.setOperationStatus(IndexResponse.OperationStatus.SUCCEEDED);
		}
		return s;

	}

}
