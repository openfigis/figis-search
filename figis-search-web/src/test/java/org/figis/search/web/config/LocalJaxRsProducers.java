package org.figis.search.web.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.enterprise.inject.Produces;
import javax.servlet.ServletContext;

import org.jglue.cdiunit.internal.jaxrs.JaxRsQualifier;

public class LocalJaxRsProducers {

	public static final String URL = "http://localhost:8080/figis-search-web/rest";

	@Produces
	@JaxRsQualifier
	public ServletContext getServletContext() {
		ServletContext servletContext = mock(ServletContext.class);
		when(servletContext.getInitParameter(FigisSearchWebConfigurationProducer.FIGIS_SEARCH_REST_URL)).thenReturn(URL);
		return servletContext;
	}
}
