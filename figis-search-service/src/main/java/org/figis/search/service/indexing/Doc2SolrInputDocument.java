package org.figis.search.service.indexing;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.solr.common.SolrInputDocument;
import org.figis.search.config.ref.FigisSearchException;
import org.figis.search.xmlsearchenginecontrol.Element;
import org.figis.search.xmlsearchenginecontrol.KeyWord;
import org.figis.search.xmlsearchenginecontrol.ObjectType;
import org.figis.search.xmlsearchenginecontrol.XmlSearchEngineControl;
import org.figis.search.xmlsearchenginecontrol.jaxb.FigisSearchJaxb;
import org.w3c.dom.Document;

public class Doc2SolrInputDocument {

	// SolrInputDocument solrDoc;
	private Document document;

	public Doc2SolrInputDocument extract(Document document) {
		this.document = document;
		return this;
	}

	public SolrInputDocument basedOn(String domain) {
		if (document == null) {
			new FigisSearchException("First use the extract method");
		}
		XmlSearchEngineControl c = new FigisSearchJaxb().unmarshall();
		ObjectType o = c.getObjectTypeList().stream().filter(w -> w.getName().equals(domain)).findFirst().get();
		XPath x = getXpath();
		SolrInputDocument s = new SolrInputDocument();
		for (KeyWord k : o.getKeyWordList()) {
			if (k.getElementList() == null) {
				System.out.println(k.getName());
			}

			for (Element e : k.getElementList()) {
				String value = xpathSingleValue(x, o.getBase() + k.getBase() + e.getAttrSetting());
				s.addField(e.getName(), value);
			}
		}
		return s;
	}

	private String xpathSingleValue(XPath xPath, String note) {
		try {
			XPathExpression expr = xPath.compile(note);
			return (String) expr.evaluate(document, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			throw new FigisSearchException(e);
		}
	}

	private XPath getXpath() {
		// Create XPathFactory object
		XPathFactory xpathFactory = XPathFactory.newInstance();

		// Create XPath object
		return xpathFactory.newXPath();
	}

}
