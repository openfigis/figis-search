package org.figis.search.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class IndexStatusTest {

	@Test
	public void testGetMessageList() {
		SingleResponse is = new SingleResponse();
		assertNull(is.getMessageList());

	}

	@Test
	public void testIndexStatus() {
		assertEquals("FAILED", SingleResponse.OperationStatus.FAILED.toString());
	}

}
