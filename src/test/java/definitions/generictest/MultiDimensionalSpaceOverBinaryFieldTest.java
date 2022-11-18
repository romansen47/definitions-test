package definitions.generictest;

import org.junit.Assert;
import org.junit.Test;

import definitions.prototypes.impl.GenericTest;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author ro
 *
 */
public class MultiDimensionalSpaceOverBinaryFieldTest extends GenericTest {

	final int dim = 5;

	@Test
	public void testShowBase() {

		boolean ans = true;
		final EuclideanSpace modulo2Space = getSpaceGenerator()
				.getFiniteDimensionalVectorSpace(getGroupGenerator().getBinaries(), dim);
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

	@Test
	public void testWithConstructedBinaries() {

		boolean ans = true;
		final EuclideanSpace modulo2Space = getSpaceGenerator()
				.getFiniteDimensionalVectorSpace(getGenerator().getGroupGenerator().getConstructedBinaries(), dim);
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
