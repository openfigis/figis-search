package org.figis.search.web;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.ws.rs.core.Application;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Erik van Ingen
 *
 */

@RunWith(CdiRunner.class)
// @AdditionalClasses({ ConfigurationImpl.class, FigisSearchAplication.class })
public class FigisSearchAplicationTest {

	// static Weld weld;
	// static WeldContainer weldContainer;

	@Inject
	Application figisSearchAplication;

	@Test
	@Ignore
	public void test() {
		assertNotNull(figisSearchAplication);
	}

	// @BeforeClass
	// public static void setUpClass() {
	// weld = new Weld();
	// weldContainer = weld.initialize();
	// System.out.println("weld init");
	// }
	//
	// @AfterClass
	// public static void teardownClass() {
	// weld.shutdown();
	// System.out.println("weld teardown");
	// }

	// @Override
	// @Before
	// public void init() {
	// // manager = weldContainer.instance().select(CategoryManager.class).get();
	// }

}