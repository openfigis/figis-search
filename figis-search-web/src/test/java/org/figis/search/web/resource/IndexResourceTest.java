package org.figis.search.web.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import javax.inject.Inject;

import org.figis.search.service.IndexResponse;
import org.figis.search.service.IndexResponse.OperationStatus;
import org.figis.search.service.IndexService;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(CdiRunner.class)
@AdditionalClasses(IndexService.class)
public class IndexResourceTest {

	@Inject
	IndexResource indexResource;

	Action action = Action.update;
	String index = "factsheet";
	String domain = "resource";
	String factsheet = "10529";

	// @Test
	public void testUpdateDomain() {
		assertNotNull(indexResource);
	}

	@Test
	public void testUpdateFactsheet() {
		assertEquals(OperationStatus.SUCCEEDED,
				indexResource.updateFactsheet(index, action, domain, factsheet).getOperationStatus());
	}

	// @Produces
	protected IndexService indexService() {
		IndexService s = Mockito.mock(IndexService.class);
		IndexResponse r = new IndexResponse();
		r.setOperationStatus(OperationStatus.SUCCEEDED);
		when(s.update(index, domain)).thenReturn(r);
		return s;
	}

}
