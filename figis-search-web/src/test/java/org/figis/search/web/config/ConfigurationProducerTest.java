package org.figis.search.web.config;

import static org.junit.Assert.assertEquals;

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
		assertEquals(LocalJaxRsProducers.URL, figisSearchWebConfiguration.getFigisRestUrl());
	}

}
