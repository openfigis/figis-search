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
import org.junit.Test;
import org.w3c.dom.Document;

public class Doc2SolrInputDocumentTest {

	Doc2SolrInputDocument d = new Doc2SolrInputDocument();

	String domain = "resource";

	@Test
	public void testExtract() {
		SolrInputDocument s = d.extract(loadXML()).basedOn(domain);
		assertEquals(9, s.getFieldNames().size());
		s.getFieldValues(domain);
		s.getFieldNames();
		for (String k : s.getFieldNames()) {
			System.out.println(k);

			assertFalse(StringUtils.isEmpty(s.get(k).getValue().toString()));
		}

	}

	public Document loadXML() {

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
