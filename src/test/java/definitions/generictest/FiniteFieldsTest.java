package definitions.generictest;

import org.junit.Test;

import definitions.prototypes.impl.GenericTest;
import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;

public class FiniteFieldsTest extends GenericTest {

	@Test
	public void testFiniteField() {

		DiscreetSemiRing naturals = getGroupGenerator().getNaturals();

	}

}
