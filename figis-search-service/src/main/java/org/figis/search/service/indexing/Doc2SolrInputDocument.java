package org.figis.search.service.indexing;

import org.apache.solr.common.SolrInputDocument;
import org.w3c.dom.Document;

public class Doc2SolrInputDocument {

	public SolrInputDocument extract(Document document) {

		return new SolrInputDocument();
	}

}
