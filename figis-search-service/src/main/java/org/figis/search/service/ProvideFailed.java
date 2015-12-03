package org.figis.search.service;

import java.util.ArrayList;

import org.figis.search.service.IndexResponse.OperationStatus;

public class ProvideFailed {

	public static IndexResponse provide() {
		IndexResponse r = new IndexResponse();
		r.setOperationStatus(OperationStatus.FAILED);
		r.setMessageList(new ArrayList<String>());
		r.getMessageList().add("No valid action specified");
		return r;
	}

}
