/**
 * 
 */
package org.figis.search.web;

import javax.ws.rs.ApplicationPath;

import org.figis.search.util.CorsFilter;
import org.figis.search.util.GenericExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Erik van Ingen
 *
 */
@ApplicationPath("rest")
@Slf4j
public class FigisSearchAplication extends ResourceConfig {

	// @Inject
	// private Configuration configuration;

	public FigisSearchAplication() {

		packages("org.figis.search.web.resource");
		packages("com.wordnik.swagger.jaxrs.listing");

		register(new CorsFilter());
		register(new GenericExceptionMapper());
	}

	// @PostConstruct
	// private void buildBeanConfig() {
	// URL restUrl = configuration.getFigisSearchUrl();
	//
	// if (restUrl == null) {
	// log.warn("Missing figis.search url in configuration, swagger will be not available");
	// return;
	// }
	//
	// BeanConfig beanConfig = new BeanConfig();
	// beanConfig.setTitle("figis.search rest API documentation");
	// beanConfig.setVersion("1.0.0");
	//
	// beanConfig.setSchemes(new String[] { restUrl.getProtocol() });
	// String host = restUrl.getHost();
	// if (restUrl.getPort() > 0)
	// host = host + ":" + restUrl.getPort();
	// beanConfig.setHost(host);
	// String path = restUrl.getPath();
	// if (path.endsWith("/"))
	// path = path.substring(0, path.length() - 1);
	// beanConfig.setBasePath(path);
	//
	// beanConfig.setResourcePackage("org.figis.search.web.resource");
	// beanConfig.setScan(true);
	// }

}