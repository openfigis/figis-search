package org.figis.search.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
	String r = "resource";
	String f = "fishery";

	@Test
	public void testUpdate() {

		// String factsheetID = "10382";
		String factsheetID = "10529";
		assertNotNull(indexService);
		IndexResponse s = indexService.update(indexName, r, factsheetID);

		printStatus(s);
		assertEquals(IndexResponse.OperationStatus.SUCCEEDED, s.getOperationStatus());
		assertEquals(0, s.getMessageList().size());
	}

	@Test
	public void testUpdateDomainResource() {
		IndexResponse s = indexService.update(indexName, r);
		printStatus(s);
	}

	@Test
	public void testUpdateDomainFishery() {
		assertNotNull(indexService);
		IndexResponse s = indexService.update(indexName, f);
		printStatus(s);
	}

	void printStatus(IndexResponse s) {
		System.out.println("status");
		System.out.println(s.getOperationStatus());
		System.out.println("messages");
		for (String message : s.getMessageList()) {
			System.out.println(message);
		}
		System.out.println("end messages");
	}

}
