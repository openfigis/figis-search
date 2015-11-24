package org.figis.search.service.indexing;

import javax.xml.xpath.XPath;

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

	private Fpath fp = new Fpath();
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
		XPath x = fp.getXpath();
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
						expr = o.getBase() + "/" + k.getBase() + "/" + e.getName();
						if (e.getAttrSetting() != null) {
							expr = expr + " [" + e.getAttrSetting() + "]";
						}
					}
					if (e.getAttr() != null) {
						key = e.getAttr();
						expr = o.getBase() + "/" + k.getBase() + "/ @" + e.getAttr();
						if (e.getAttrSetting() != null) {
							expr = expr + " [" + e.getAttrSetting() + "]";
						}
					}

					if (expr.contains("null") || StringUtils.isEmpty(expr)) {
						throw new FigisSearchException(
								"Some null values are not anticipated, key = " + key + "; Expr = " + expr);
					}
					System.out.println(expr);
					String value = fp.xpathSingleValue(x, expr, document);
					// post condition
					if (!StringUtils.isEmpty(value)) {
						s.addField(key, value);
					}
				}
			}
		}
		return s;
	}

}
