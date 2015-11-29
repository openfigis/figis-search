package org.figis.search.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class IndexStatusTest {

	@Test
	public void testGetMessageList() {
		IndexResponse is = new IndexResponse();
		assertNull(is.getMessageList());

	}

	@Test
	public void testIndexStatus() {
		assertEquals("FAILED", IndexResponse.OperationStatus.FAILED.toString());
	}

}
