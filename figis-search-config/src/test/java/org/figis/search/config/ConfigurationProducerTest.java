/**
 * 
 */
package org.figis.search.config;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
public class ConfigurationProducerTest {

	@Inject
	ConfigurationProducer p;

	@Inject
	Configuration c;

	@Before
	public void before() {
		System.setProperty(ConfigurationProducer.CONFIG_LOCATION_PROPERTY_NAME,
				"..\\..\\figis-search-deploy\\conf\\dev\\figis-search.properties");
	}

	/**
	 * 
	 * This test only works with the eclipse run config configured with this env variable:
	 * 
	 * 
	 */
	@Test
	@Ignore
	public void buildConfiguration() {
		Configuration config = p.buildConfiguration();
		assertNotNull(config);
		assertNotNull(c);
	}

}
