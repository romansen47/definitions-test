package definitions.structures.abstr.mappings;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import definitions.prototypes.impl.GenericTest;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;

public class FiniteDimensionalEndomorphismMockitoTest extends GenericTest {

	final static public Random random = new Random();

	@Test
	public void detTest() {
		final Scalar[][] matrix = new Scalar[2][2];
		matrix[0][0] = getRealLine().get(0);
		matrix[0][1] = getRealLine().get(1);
		matrix[1][0] = getRealLine().get(-1);
		matrix[1][1] = getRealLine().get(0);
		VectorSpaceMapping x = getGenerator().getFiniteDimensionalLinearMapping(matrix);
		Assert.assertTrue(x instanceof FiniteDimensionalEndomorphism);
		Assert.assertEquals(((FiniteDimensionalEndomorphism) x).det(matrix), getRealLine().get(1));

		final Scalar[][] matrix2 = new Scalar[3][3];
		matrix2[0][0] = getRealLine().get(1);
		matrix2[0][1] = getRealLine().get(2);
		matrix2[0][2] = getRealLine().get(3);
		matrix2[1][0] = getRealLine().get(4);
		matrix2[1][1] = getRealLine().get(5);
		matrix2[1][2] = getRealLine().get(6);
		matrix2[2][0] = getRealLine().get(7);
		matrix2[2][1] = getRealLine().get(8);
		matrix2[2][2] = getRealLine().get(9);
		VectorSpaceMapping y = getGenerator().getFiniteDimensionalLinearMapping(matrix2);
		Assert.assertFalse(y instanceof FiniteDimensionalEndomorphism);
		Assert.assertEquals(((FiniteDimensionalEndomorphism) x).det(matrix2), getRealLine().get(0));
	}
}
