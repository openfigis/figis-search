package org.figis.search.service.util;

import org.apache.deltaspike.core.util.StringUtils;
import org.figis.search.config.ref.FigisSearchException;

public class FactsheetId {

	private String domain;
	private String factsheet;
	private String lang;

	public FactsheetId domain(String domain) {
		this.domain = domain;
		return this;
	}

	public FactsheetId factsheet(String factsheet) {
		this.factsheet = factsheet;
		return this;
	}

	public FactsheetId lang(String lang) {
		this.lang = lang;
		return this;
	}

	public Object compose() {
		if (StringUtils.isEmpty(domain) || StringUtils.isEmpty(factsheet) || StringUtils.isEmpty(lang)) {
			throw new FigisSearchException("All domain, factsheet and lang must be filled");
		}
		return domain + "-" + factsheet + "-" + lang;
	}

}
