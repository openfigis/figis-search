package org.figis.search.service.indexing;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.StringUtils;
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

			// the null check is for the concat problem, work in progress, see also
			// /figis-search-xmlsearchenginecontrol/src/main/resources/SearchTerms.xsd
			if (k.getElementList() != null) {
				for (Element e : k.getElementList()) {
					String expr = null;
					String key = null;

					if (e.getName() != null) {
						key = e.getName();
						System.out.println(o.getBase() + k.getBase() + e.getName());
						expr = o.getBase() + "/" + k.getBase() + "/" + e.getName();
						// expr = o.getBase() + k.getBase() + "/" + e.getName();
						if (e.getAttrSetting() != null) {
							expr = expr + e.getAttrSetting();
						}
					}
					if (e.getAttr() != null) {
						key = e.getAttr();
						// fi:AqRes/fi:AqResIdent/fi:WaterAreaList/fi:WaterAreaRef/fi:ForeignID/ @Code
						// [CodeSystem=iccat_smu_yft]
						expr = o.getBase() + "/" + k.getBase() + "/ @" + e.getAttr();
						if (e.getAttrSetting() != null) {
							expr = expr + " [" + e.getAttrSetting() + "]";
						}
					}

					if (expr.contains("null") || StringUtils.isEmpty(expr)) {
						throw new FigisSearchException(
								"Some null values are not anticipated" + key + "; Expr = " + expr);
					}
					String value = xpathSingleValue(x, expr);
					// post condition
					if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
						throw new FigisSearchException(
								"Some null values are not anticipated, key =" + key + "; value = " + value);
					} else {
						s.addField(key, value);
					}

				}
			}
		}
		return s;
	}

	private String xpathSingleValue(XPath xPath, String expr) {
		try {
			XPathExpression exprCompiled = xPath.compile(expr);
			return (String) exprCompiled.evaluate(document, XPathConstants.STRING);
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
