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
import org.fao.fi.factsheetwebservice.domain.FactsheetDomain;
import org.fao.fi.factsheetwebservice.domain.FactsheetLanguage;
import org.fao.fi.factsheetwebservice.domain.LanguageList;
import org.fao.fi.services.factsheet.client.FactsheetWebServiceClient;
import org.figis.search.config.ref.FigisSearchException;
import org.figis.search.xmlsearchenginecontrol.ObjectType;
import org.figis.search.xmlsearchenginecontrol.XmlSearchEngineControl;
import org.figis.search.xmlsearchenginecontrol.jaxb.FigisSearchJaxb;
import org.w3c.dom.Document;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IndexService {

	/**
	 * update or delete index of a single factsheet /{action}/index/{indexName}/domain/{factsheet
	 * domain}/factsheet/{factsheetID}
	 */

	public String update(String indexName, String domain, String factsheet) {
		SolrClient client = new HttpSolrClient("http://hqldvfigis2:6101/solr/RefPub");

		SolrInputDocument doc = composeDoc(domain, factsheet);

		String response = IndexServiceResponse.APPLIED_UPDATE;
		try {
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
				StringWriter writer = new StringWriter();
				StreamResult result = new StreamResult(writer);
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer transformer = tf.newTransformer();
				transformer.transform(domSource, result);
				System.out.println("XML IN String format is: \n" + writer.toString());
				sd.addField("full_name_e", writer.toString());
				sd.addField("id", factsheet);
				sd.addField("url", factsheet);

			} catch (TransformerException e) {
				log.error(e.getMessage());
				throw new FigisSearchException(e);
			}
		}
		return sd;
	}

}
