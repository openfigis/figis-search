package org.figis.search.web.config;

import javax.enterprise.inject.Vetoed;

import lombok.Data;

@Data
@Vetoed
public class FigisSearchWebConfiguration {

	private String figisRestUrl;

}
