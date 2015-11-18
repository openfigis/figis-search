package org.figis.search.xmlsearchenginecontrol.jaxb;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.figis.search.xmlsearchenginecontrol.Element;
import org.figis.search.xmlsearchenginecontrol.KeyWord;
import org.figis.search.xmlsearchenginecontrol.ObjectType;
import org.figis.search.xmlsearchenginecontrol.XmlSearchEngineControl;
import org.junit.Test;

public class FigisSearchJaxbTest {

	FigisSearchJaxb m = new FigisSearchJaxb();

	@Test
	public void test() {

		File file = new File("src/main/resources/SearchTerms.xml");

		XmlSearchEngineControl c = m.unmarshall(file);
		testC(c);
		c = m.unmarshall();
		testC(c);

	}

	private void testC(XmlSearchEngineControl c) {
		assertEquals(22, c.getObjectTypeList().size());
		assertEquals("staticxml.eaftool", c.getObjectTypeList().get(0).getName());
		assertEquals(8, c.getObjectTypeList().get(0).getKeyWordList().size());
		assertEquals(7, c.getObjectTypeList().get(0).getKeyWordList().get(0).getElementList().size());
		for (ObjectType o : c.getObjectTypeList()) {
			for (KeyWord k : o.getKeyWordList()) {
				if (k.getElementList() == null) {
					for (Element e : k.getConcat().getElementList()) {
						assertNotNull(e);
					}
				} else {
					for (Element e : k.getElementList()) {
						assertNotNull(e);
					}
				}
			}
		}
	}

	private void assertNotNull(Element e) {
		// TODO Auto-generated method stub

	}

}
