package org.figis.search.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class FactsheetIdTest {

	@Test
	public void testComposeFactsheetId() {
		FactsheetId u = new FactsheetId();
		assertEquals("screws-456-es", u.domain("screws").factsheet("456").lang("es").compose());
		try {
			u.domain(" ").compose();
			fail();
		} catch (Exception e) {
		}
	}

}
