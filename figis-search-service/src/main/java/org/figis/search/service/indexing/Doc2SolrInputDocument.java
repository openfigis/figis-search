package org.figis.search.service.indexing;

import javax.xml.xpath.XPath;

import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.StringUtils;
import org.fao.fi.factsheetwebservice.domain.FactsheetDomain;
import org.figis.search.config.ref.FigisSearchException;
import org.figis.search.xmlsearchenginecontrol.Element;
import org.figis.search.xmlsearchenginecontrol.KeyWord;
import org.figis.search.xmlsearchenginecontrol.ObjectType;
import org.figis.search.xmlsearchenginecontrol.XmlSearchEngineControl;
import org.figis.search.xmlsearchenginecontrol.jaxb.FigisSearchJaxb;
import org.w3c.dom.Document;

/**
 * Generate the document for Solr,
 * 
 * 
 * @author Erik van Ingen
 *
 */
public class Doc2SolrInputDocument {

	private Fpath fp = new Fpath();
	private Document document;

	/**
	 * extract the info from the document
	 * 
	 * @param document
	 * @return
	 */
	public Doc2SolrInputDocument extract(Document document) {
		this.document = document;
		return this;
	}

	/**
	 * specify the domain to which the factsheet belongs.
	 * 
	 * @param domain
	 * @return
	 */
	public SolrInputDocument basedOn(FactsheetDomain domain) {
		if (document == null) {
			new FigisSearchException("First use the extract method");
		}
		XmlSearchEngineControl c = new FigisSearchJaxb().unmarshall();
		ObjectType o = c.getObjectTypeList().stream().filter(w -> w.getName().equals(domain.toString())).findFirst()
				.get();
		XPath x = fp.getXpath();
		SolrInputDocument s = new SolrInputDocument();
		s.setField("domain", o.getName());
		s.setField("meta", o.getMeta());

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

					String value = fp.xpathSingleValue(x, expr, document);

					// System.out.println(expr + " - " + value);

					// post condition
					if (!StringUtils.isEmpty(value)) {

						System.out.println(expr + " - " + k.getName() + " - " + value);

						s.addField(k.getName(), value);
					}
				}
			}
		}
		return s;
	}

}
