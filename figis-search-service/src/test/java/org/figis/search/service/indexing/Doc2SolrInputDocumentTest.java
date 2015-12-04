package org.figis.search.service.indexing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.File;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.StringUtils;
import org.fao.fi.factsheetwebservice.domain.FactsheetDomain;
import org.junit.Test;
import org.w3c.dom.Document;

public class Doc2SolrInputDocumentTest {

	Doc2SolrInputDocument d = new Doc2SolrInputDocument();

	FactsheetDomain domain = FactsheetDomain.resource;

	@Test
	public void testExtract() {
		SolrInputDocument s = d.extract(loadXML()).basedOn(domain);
		assertEquals(17, s.getFieldNames().size(), 2);

		String name = (String) s.getField("title").getValue();
		assertEquals(name, "Antarctic krill - High Latitude, Eastern Indian Ocean");

		for (String fieldName : s.getFieldNames()) {
			// System.out.println(fieldName);
			assertFalse(StringUtils.isEmpty(fieldName));
			assertFalse(fieldName.contains(":"));
		}
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
		// FactsheetWebServiceClient fc = new FactsheetWebServiceClient("http://www.fao.org/figis/ws/factsheets/");
		// Document doc = fc.retrieveFactsheet("10529", FactsheetDomain.resource, FactsheetLanguage.en);
		// return doc;
	}

}
