/**
 * 
 */
package org.figis.search.service;

import java.util.List;

import lombok.Data;

/**
 * 
 * The response message after having index one factsheet object for the different languages or having indexed all
 * factsheets for a complete domain.
 * 
 * 
 * @author Erik van Ingen
 *
 */
@Data
public class IndexResponse {

	public enum OperationStatus {
		SUCCEEDED, PARTLY_SUCCEEDED, FAILED;
	}

	private List<String> messageList;
	private OperationStatus operationStatus;

}