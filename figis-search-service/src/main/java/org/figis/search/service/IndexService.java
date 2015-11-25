package org.figis.search.service;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.fao.fi.factsheetwebservice.domain.FactsheetDiscriminator;
import org.fao.fi.factsheetwebservice.domain.FactsheetDomain;
import org.fao.fi.factsheetwebservice.domain.FactsheetLanguage;
import org.fao.fi.factsheetwebservice.domain.FactsheetList;
import org.fao.fi.factsheetwebservice.domain.LanguageList;
import org.fao.fi.services.factsheet.client.FactsheetWebServiceClient;
import org.fao.fi.services.factsheet.logic.FactsheetUrlComposer;
import org.fao.fi.services.factsheet.logic.FactsheetUrlComposerImpl;
import org.figis.search.config.ref.FigisSearchException;
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
	public String update(String indexName, String domain) {
		FactsheetDomain d = FactsheetDomain.parseDomain(domain);
		FactsheetList l = fc.retrieveFactsheetListPerDomain(d);
		for (FactsheetDiscriminator ds : l.getFactsheetList()) {
			update(indexName, domain, ds.getFactsheet());
		}
		return IndexServiceResponse.APPLIED_UPDATE;
	}

	/**
	 * update or delete index of a single factsheet /{action}/index/{indexName}/domain/{factsheet
	 * domain}/factsheet/{factsheetID}
	 */
	public String update(String indexName, String domain, String factsheet) {
		SolrClient client = new HttpSolrClient("http://hqldvfigis2:8983/solr/factsheet");
		List<SolrInputDocument> docs = composeDoc(domain, factsheet);

		String response = IndexServiceResponse.APPLIED_UPDATE;
		try {

			for (SolrInputDocument solrInputDocument : docs) {
				client.add(solrInputDocument);
				client.commit();
			}
		} catch (IOException | SolrServerException e) {
			log.error(e.getMessage());
			throw new FigisSearchException(e);
		}
		return response;
	}

	private List<SolrInputDocument> composeDoc(String domain, String factsheet) {
		FactsheetDomain d = FactsheetDomain.parseDomain(domain);
		LanguageList ll = fc.retrieveLanguageListInDomain4ThisFactsheet(d, factsheet);
		List<SolrInputDocument> solrInputDocuments = new ArrayList<SolrInputDocument>();

		for (FactsheetLanguage language : ll.getLanguageList()) {
			Document doc = fc.retrieveFactsheet(factsheet, d, language);

			// Document doc = loadXML();

			FactsheetDiscriminator disc = new FactsheetDiscriminator(language, d, factsheet);

			SolrInputDocument sd = prepare.extract(doc).basedOn(domain);
			sd.addField("id", u.domain(domain).factsheet(factsheet).lang(language.toString()).compose());
			sd.addField("dataset", "firms");
			sd.addField("url", factsheetUrlComposer.composeFromDomainAndFactsheet(disc).replace("/xml", ""));
			solrInputDocuments.add(sd);
		}
		return solrInputDocuments;

	}

	public static Document loadXML() {

		Source source = new StreamSource(new File("src/test/resources/resource-10529-en.xml"));
		DOMResult result = new DOMResult();
		try {
			TransformerFactory.newInstance().newTransformer().transform(source, result);
		} catch (TransformerException | TransformerFactoryConfigurationError e) {
			fail();
		}
		return (Document) result.getNode();
	}

}
