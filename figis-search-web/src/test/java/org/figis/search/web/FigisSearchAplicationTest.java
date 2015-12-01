package org.figis.search.web;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.figis.search.web.config.ConfigurationProducer;
import org.figis.search.web.config.LocalSupportJaxRs;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Erik van Ingen
 *
 */

@RunWith(CdiRunner.class)
@LocalSupportJaxRs
@AdditionalClasses(ConfigurationProducer.class)
public class FigisSearchAplicationTest {

	@Inject
	FigisSearchAplication figisSearchAplication;

	@Test
	public void test() {
		assertNotNull(figisSearchAplication);
	}

}