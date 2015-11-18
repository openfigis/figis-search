package org.figis.search.service;

import java.io.IOException;
import java.io.StringWriter;

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
import org.figis.search.xmlsearchenginecontrol.ObjectType;
import org.figis.search.xmlsearchenginecontrol.XmlSearchEngineControl;
import org.figis.search.xmlsearchenginecontrol.jaxb.FigisSearchJaxb;
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
		SolrClient client = new HttpSolrClient("http://hqldvfigis2:6101/solr/RefPub");

		SolrInputDocument doc = composeDoc(domain, factsheet);

		SolrInputDocument sd2 = new SolrInputDocument();
		sd2.addField("id", factsheet);
		sd2.addField("url", factsheet);

		String response = IndexServiceResponse.APPLIED_UPDATE;
		try {

			// temporary one, delete this
			client.deleteById(factsheet);

			// logic
			client.add(doc);
			client.commit();

		} catch (IOException | SolrServerException e) {
			log.error(e.getMessage());
			throw new FigisSearchException(e);
		}
		return response;
	}

	private SolrInputDocument composeDoc(String domain, String factsheet) {
		XmlSearchEngineControl c = new FigisSearchJaxb().unmarshall();
		ObjectType o = c.getObjectTypeList().stream().filter(w -> w.getName().equals(domain)).findFirst().get();

		FactsheetDomain d = FactsheetDomain.parseDomain(domain);
		FactsheetWebServiceClient fc = new FactsheetWebServiceClient("http://www.fao.org/figis/ws/factsheets/");
		LanguageList ll = fc.retrieveLanguageListInDomain4ThisFactsheet(d, factsheet);
		SolrInputDocument sd = new SolrInputDocument();

		for (FactsheetLanguage language : ll.getLanguageList()) {
			Document doc = fc.retrieveFactsheet(factsheet, d, language);
			try {
				DOMSource domSource = new DOMSource(doc);

				// SolrInputDocument ddd = prepare.extract(document).withHelpFrom(o);

				StringWriter writer = new StringWriter();
				StreamResult result = new StreamResult(writer);
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer transformer = tf.newTransformer();
				transformer.transform(domSource, result);
				// System.out.println("XML IN String format is: \n" + writer.toString());
				sd.addField("full_name_e", writer.toString());
				sd.addField("id", u.domain(domain).factsheet(factsheet).lang(language.toString()).compose());
				FactsheetDiscriminator disc = new FactsheetDiscriminator(language, d, factsheet);
				sd.addField("url", factsheetUrlComposer.composeFromDomainAndFactsheet(disc).replaceAll("/xml", ""));

			} catch (TransformerException e) {
				log.error(e.getMessage());
				throw new FigisSearchException(e);
			}
		}
		return sd;
	}

}
