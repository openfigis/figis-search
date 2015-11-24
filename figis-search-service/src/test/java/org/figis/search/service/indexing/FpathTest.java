package org.figis.search.service.indexing;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FpathTest extends Fpath {

	Fpath fp = new Fpath();

	String xpath = "/fi:FIGISDoc/fi:AqRes/fi:AqResIdent";

	@Test
	public void testXpathSingleValue() {
		assertTrue(
				fp.xpathSingleValue(fp.getXpath(), xpath, Doc2SolrInputDocumentTest.loadXML()).contains("Antarctic"));
	}

	@Test
	public void testGetXpath() {
		assertNotNull(fp.getXpath());
	}

}
