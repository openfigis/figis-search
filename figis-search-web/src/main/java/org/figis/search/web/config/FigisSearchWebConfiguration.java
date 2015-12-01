package org.figis.search.web.config;

import javax.enterprise.inject.Alternative;

import lombok.Data;

@Data
@Alternative
public class FigisSearchWebConfiguration {

	private String figisRestUrl;
}
