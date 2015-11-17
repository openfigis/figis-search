package org.figis.search.xmlsearchenginecontrol.jaxb;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.figis.search.xmlsearchenginecontrol.XmlSearchEngineControl;
import org.junit.Test;

public class FigisSearchJaxbTest {

	FigisSearchJaxb m = new FigisSearchJaxb();

	@Test
	public void test() {

		File file = new File("/src/test/resources/SearchTerms.xml");

		XmlSearchEngineControl c = m.unmarshall(file);
		assertEquals(6, c.getObjectTypeList().size());

	}

}
