package definitions.generictest;

import org.junit.Assert;
import org.junit.Test;

import definitions.prototypes.GenericTest;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author ro
 *
 */
public class MultiDimensionalSpaceOverBinaryFieldTest extends GenericTest {

	final int dim = 5;
	PrimeField f = GenericTest.getGenerator().getGroupGenerator().getBinaries();

	@Test
	public void testShowBase() {

		boolean ans = true;
		final EuclideanSpace modulo2Space = GenericTest.getSpaceGenerator().getFiniteDimensionalVectorSpace(f, dim);
		modulo2Space.show();

		for (int i = 1; i < dim; i++) {
			final Vector x = modulo2Space.genericBaseToList().get(i);
			final Vector h = modulo2Space.addition(x, x);
			if (!h.equals(modulo2Space.nullVec())) {
				ans = false;
				continue;
			}
		}

		Assert.assertTrue(ans);
	}

	PrimeField cf = GenericTest.getGenerator().getGroupGenerator().getConstructedBinaries();

	@Test
	public void testWithConstructedBinaries() {

		boolean ans = true;
		final EuclideanSpace modulo2Space = GenericTest.getSpaceGenerator().getFiniteDimensionalVectorSpace(cf, dim);
		modulo2Space.show();

		for (int i = 1; i < dim; i++) {
			final Vector x = modulo2Space.genericBaseToList().get(i);
			final Vector h = modulo2Space.addition(x, x);
			if (!h.equals(modulo2Space.nullVec())) {
				ans = false;
				continue;
			}
		}

		Assert.assertTrue(ans);
	}

}
