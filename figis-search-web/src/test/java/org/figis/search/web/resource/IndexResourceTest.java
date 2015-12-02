package org.figis.search.web.resource;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.fao.fi.factsheetwebservice.domain.FactsheetDomain;
import org.figis.search.config.elements.Index;
import org.figis.search.service.IndexResponse;
import org.figis.search.service.IndexResponse.OperationStatus;
import org.figis.search.service.IndexService;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.ProducesAlternative;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(CdiRunner.class)
public class IndexResourceTest {

	@Inject
	IndexResource indexResource;

	Action update = Action.update;
	Action delete = Action.delete;
	Index index = Index.factsheet;
	FactsheetDomain domain = FactsheetDomain.resource;
	String factsheet = "10529";

	// @Test
	public void testActionsOnDomain() {
		assertEquals(OperationStatus.SUCCEEDED, indexResource.actionOnDomain(index, update, domain).getOperationStatus());
		assertEquals(OperationStatus.SUCCEEDED, indexResource.actionOnDomain(index, delete, domain).getOperationStatus());
		assertEquals(OperationStatus.FAILED, indexResource.actionOnDomain(index, null, domain).getOperationStatus());
	}

	@Test
	public void testActionsOnFactsheet() {
		assertEquals(OperationStatus.SUCCEEDED,
				indexResource.actionOnFactsheet(index, update, domain, factsheet).getOperationStatus());
		assertEquals(OperationStatus.SUCCEEDED,
				indexResource.actionOnFactsheet(index, delete, domain, factsheet).getOperationStatus());
		assertEquals(OperationStatus.FAILED,
				indexResource.actionOnFactsheet(null, update, domain, factsheet).getOperationStatus());
		assertEquals(OperationStatus.FAILED,
				indexResource.actionOnFactsheet(index, null, domain, factsheet).getOperationStatus());
		assertEquals(OperationStatus.FAILED,
				indexResource.actionOnFactsheet(index, delete, null, factsheet).getOperationStatus());
		assertEquals(OperationStatus.FAILED,
				indexResource.actionOnFactsheet(index, delete, domain, null).getOperationStatus());
	}

	@Produces
	@ProducesAlternative
	public IndexService indexService() {
		IndexService s = Mockito.mock(IndexService.class);
		IndexResponse r = new IndexResponse();
		r.setOperationStatus(OperationStatus.SUCCEEDED);
		when(s.update(index, domain, factsheet)).thenReturn(r);
		when(s.update(index, domain)).thenReturn(r);
		when(s.delete(index, domain, factsheet)).thenReturn(r);
		when(s.delete(index, domain)).thenReturn(r);
		return s;
	}

}
