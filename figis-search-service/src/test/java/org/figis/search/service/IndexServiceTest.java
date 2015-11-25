package org.figis.search.service;

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
	String domain = "resource";

	@Test
	public void testUpdate() {
		String factsheetID = "10121";
		assertNotNull(indexService);
		indexService.update(indexName, domain, factsheetID);
	}

	@Test
	public void testUpdateDomain() {
		assertNotNull(indexService);
		indexService.update(indexName, domain);
	}

}
