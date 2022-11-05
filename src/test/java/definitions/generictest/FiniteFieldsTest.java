package definitions.generictest;

import org.junit.Test;

import definitions.prototypes.GenericTest;
import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.euclidean.Generator;

public class FiniteFieldsTest extends GenericTest {

	@Test
	public void testFiniteField() {

		DiscreetSemiRing naturals = Generator.getInstance().getGroupGenerator().getNaturals();

	}

}
