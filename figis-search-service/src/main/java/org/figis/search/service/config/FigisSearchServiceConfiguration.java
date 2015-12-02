package org.figis.search.service.config;

import javax.enterprise.inject.Vetoed;

import lombok.Data;

@Data
@Vetoed
public class FigisSearchServiceConfiguration {

	private String factsheetWebservcieEndPoint;

	private String httpSolrEndPoint;

}
