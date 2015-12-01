package org.figis.search.web.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@LocalSupportJaxRs
@AdditionalClasses(ConfigurationProducer.class)
public class ConfigurationProducerTest {

	@Inject
	FigisSearchWebConfiguration figisSearchWebConfiguration;

	@Test
	public void testBuildConfiguration() {
		assertNotNull(figisSearchWebConfiguration);
		assertEquals(LocalJaxRsProducers.URL, figisSearchWebConfiguration.getFigisRestUrl());

	}

	// @Produces
	// @Singleton
	// private ServletContext produceServletContext() {
	// ServletContext servletContext = mock(ServletContext.class);
	// when(servletContext.getInitParameter(ConfigurationProducer.FIGIS_SEARCH_REST_URL))
	// .thenReturn("http://localhost:8080/figis-search-web/rest");
	//
	// // ServletContext s = new MockServletContextImpl();
	// // s.setInitParameter(FigisSearchAplication.FIGIS_SEARCH_REST_URL,
	// // "http://localhost:8080/figis-search-web/rest");
	// return servletContext;
	// }

}
