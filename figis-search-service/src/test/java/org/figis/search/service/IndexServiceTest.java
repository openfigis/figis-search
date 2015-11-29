package org.figis.search.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import org.figis.search.config.ref.FigisSearchCore;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
public class IndexServiceTest {

	@Inject
	IndexService indexService;

	String indexName = FigisSearchCore.FACTSHEET;
	String domain = "resource";

	@Test
	public void testUpdate() {

		// String factsheetID = "10382";
		String factsheetID = "10529";
		assertNotNull(indexService);
		SingleResponse s = indexService.update(indexName, domain, factsheetID);
		assertEquals(SingleResponse.OperationStatus.SUCCEEDED, s.getOperationStatus());
		assertNull(s.getMessageList());
	}

	@Test
	public void testUpdateDomain() {
		assertNotNull(indexService);
		indexService.update(indexName, domain);
	}

}
