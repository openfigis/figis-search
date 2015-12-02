package org.figis.search.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.fao.fi.factsheetwebservice.domain.FactsheetDomain;
import org.junit.Test;

public class FactsheetIdTest {

	@Test
	public void testComposeFactsheetId() {
		FactsheetId u = new FactsheetId();
		assertEquals("equipment-456-es", u.domain(FactsheetDomain.equipment).factsheet("456").lang("es").compose());
		try {
			u.domain(null).compose();
			fail();
		} catch (Exception e) {
		}
	}

}
