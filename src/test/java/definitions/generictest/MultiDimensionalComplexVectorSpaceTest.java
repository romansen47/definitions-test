/**
 *
 */
package definitions.generictest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import definitions.prototypes.GenericTest;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author ro
 *
 */
public class MultiDimensionalComplexVectorSpaceTest extends GenericTest {

	public static final Logger logger = LogManager.getLogger(MultiDimensionalComplexVectorSpaceTest.class);

	@Test
	public void testNorm() {
		final int dim = 10;

		final EuclideanSpace complexSpace = (EuclideanSpace) Generator.getInstance().getSpaceGenerator()
				.getFiniteDimensionalComplexSpace(dim);

		Vector vec = complexSpace.nullVec();
		for (int i = 0; i < dim; i++) {
			final Vector x = complexSpace.genericBaseToList().get(i);
			vec = complexSpace.addition(vec, x);
		}
		final double doubleAns = complexSpace.norm(vec).doubleValue() - Math.sqrt(dim);
		final boolean newAns = Math.abs(doubleAns) < 0.1;

		Assert.assertTrue(newAns);
		logger.debug("norm = {}, square root of dim = {}, difference = {}", complexSpace.norm(vec).doubleValue(),
				Math.sqrt(dim), doubleAns);
	}

}
