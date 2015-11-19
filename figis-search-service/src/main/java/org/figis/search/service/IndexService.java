package org.figis.search.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.fao.fi.factsheetwebservice.domain.FactsheetDiscriminator;
import org.fao.fi.factsheetwebservice.domain.FactsheetDomain;
import org.fao.fi.factsheetwebservice.domain.FactsheetLanguage;
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

	/**
	 * update or delete index of a single factsheet /{action}/index/{indexName}/domain/{factsheet
	 * domain}/factsheet/{factsheetID}
	 */

	public String update(String indexName, String domain, String factsheet) {
		SolrClient client = new HttpSolrClient("http://hqldvfigis2:6101/solr/firmstemp");
		List<SolrInputDocument> docs = composeDoc(domain, factsheet);

		String response = IndexServiceResponse.APPLIED_UPDATE;
		try {

			// temporary one, delete this
			client.deleteById(factsheet);

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
		FactsheetWebServiceClient fc = new FactsheetWebServiceClient("http://www.fao.org/figis/ws/factsheets/");
		LanguageList ll = fc.retrieveLanguageListInDomain4ThisFactsheet(d, factsheet);
		List<SolrInputDocument> solrInputDocuments = new ArrayList<SolrInputDocument>();

		for (FactsheetLanguage language : ll.getLanguageList()) {
			Document doc = fc.retrieveFactsheet(factsheet, d, language);
			try {
				DOMSource domSource = new DOMSource(doc);

				StringWriter writer = new StringWriter();
				StreamResult result = new StreamResult(writer);
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer transformer = tf.newTransformer();
				transformer.transform(domSource, result);
				// System.out.println("XML IN String format is: \n" + writer.toString());
				FactsheetDiscriminator disc = new FactsheetDiscriminator(language, d, factsheet);

				SolrInputDocument sd = prepare.extract(doc).basedOn(domain);
				sd.addField("id", u.domain(domain).factsheet(factsheet).lang(language.toString()).compose());
				sd.addField("dataset", "firms");
				sd.addField("url", factsheetUrlComposer.composeFromDomainAndFactsheet(disc).replaceAll("/xml", ""));
				solrInputDocuments.add(sd);

			} catch (TransformerException e) {
				log.error(e.getMessage());
				throw new FigisSearchException(e);
			}
		}
		return solrInputDocuments;

	}

}
