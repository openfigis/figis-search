package org.figis.search.service;

import java.util.List;

import org.apache.solr.common.SolrInputDocument;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The response for having indexed 1 factsheet object in its different languages.
 * 
 * 
 * 
 * @author Erik van Ingen
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class FactsheetIndexResponse extends IndexResponse {

	private List<SolrInputDocument> solrInputDocumentList;

}
