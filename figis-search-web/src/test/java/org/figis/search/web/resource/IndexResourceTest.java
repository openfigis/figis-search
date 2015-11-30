package org.figis.search.web.resource;

import static org.junit.Assert.assertNotNull;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
// @AdditionalClasses({ IndexService.class, IndexResource.class })
public class IndexResourceTest {

	// @Inject
	IndexResource indexResource;

	String action = "update";
	String index = "factsheet";
	String domain = "fishery";
	String factsheet = "10529";

	@Test
	public void testUpdateDomain() {
		assertNotNull(indexResource);
	}

	@Test
	public void testUpdateFactsheet() {
		indexResource.updateFactsheet(action, index, domain, factsheet);
	}

}