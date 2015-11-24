package org.figis.search.service.indexing;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.fao.fi.services.factsheet.FisheryResourceNamespace;
import org.figis.search.config.ref.FigisSearchException;
import org.w3c.dom.Document;

/**
 * 
 * FIGIS search Xpath
 * 
 * 
 * @author Erik van Ingen
 *
 */
public class Fpath {

	public String xpathSingleValue(XPath xPath, String expr, Document document) {
		try {
			XPathExpression exprCompiled = xPath.compile(expr);
			return exprCompiled.evaluate(document);

		} catch (XPathExpressionException e) {
			throw new FigisSearchException(e);
		}
	}

	public XPath getXpath() {
		// Create XPathFactory object
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		xpath.setNamespaceContext(new FisheryResourceNamespace());
		return xpath;
	}

}
