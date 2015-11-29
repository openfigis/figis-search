/**
 * 
 */
package org.figis.search.service;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author Erik van Ingen
 *
 */
@Data
public class SingleResponse {

	public enum OperationStatus {
		SUCCEEDED, PARTLY_SUCCEEDED, FAILED;
	}

	private List<String> messageList;
	private OperationStatus operationStatus;

}