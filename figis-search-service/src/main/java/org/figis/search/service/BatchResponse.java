package org.figis.search.service;

import java.util.List;

import org.apache.solr.common.SolrInputDocument;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class BatchResponse extends SingleResponse {

	private List<SolrInputDocument> solrInputDocumentList;

}
