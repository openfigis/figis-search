package org.figis.search.xmlsearchenginecontrol.jaxb;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.figis.search.config.ref.FigisSearchException;
import org.figis.search.xmlsearchenginecontrol.XmlSearchEngineControl;

public class FigisSearchJaxb {

	public XmlSearchEngineControl unmarshall(File file) {

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(XmlSearchEngineControl.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (XmlSearchEngineControl) unmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			throw new FigisSearchException(e);
		}

	}
}
