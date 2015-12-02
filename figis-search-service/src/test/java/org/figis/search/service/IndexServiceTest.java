package org.figis.search.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.fao.fi.factsheetwebservice.domain.FactsheetDomain;
import org.figis.search.config.elements.Index;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
public class IndexServiceTest {

	@Inject
	IndexService indexService;

	Index indexName = Index.factsheet;
	FactsheetDomain r = FactsheetDomain.resource;
	FactsheetDomain f = FactsheetDomain.fishery;
	String factsheetID = "10529";

	@Test
	public void testUpdateFactsheet() {

		assertNotNull(indexService);
		IndexResponse s = indexService.updateFactsheet(indexName, r, factsheetID);

		printStatus(s);
		assertEquals(IndexResponse.OperationStatus.SUCCEEDED, s.getOperationStatus());
		assertEquals(0, s.getMessageList().size());
	}

	@Test
	public void testDeleteFactsheet() {
		assertNotNull(indexService);
		IndexResponse s = indexService.deleteFactsheet(indexName, r, factsheetID);

		assertEquals(IndexResponse.OperationStatus.SUCCEEDED, s.getOperationStatus());
		assertEquals(0, s.getMessageList().size());
	}

	// @Test
	public void testActionOnDomainResource() {
		IndexResponse s = indexService.actionOnDomain(Action.update, indexName, r);
		printStatus(s);
	}

	// @Test
	public void testActionOnDomainFishery() {
		assertNotNull(indexService);
		IndexResponse s = indexService.actionOnDomain(Action.update, indexName, f);
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
