/**
 *
 */
package definitions.generictest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import definitions.prototypes.GenericTest;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.Functional;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author ro
 *
 */
public class FunctionalSpaceTest extends GenericTest {

	public static final Logger logger = LogManager.getLogger(FunctionalSpaceTest.class);

	final int dim = 3;
	final int sobOrder = 2;
	final EuclideanSpace space = getSpaceGenerator().getFiniteDimensionalVectorSpace(dim);
	final EuclideanSpace dualSpace = space.getDualSpace();
	final EuclideanSpace dualDualSpace = dualSpace.getDualSpace();
	final EuclideanSpace functionSpace = Generator.getInstance().getSpaceGenerator()
			.getNormedTrigonometricSpace(Generator.getInstance().getFieldGenerator().getRealLine(), 1);
	final EuclideanSpace dualFunctionSpace = functionSpace.getDualSpace();
	final EuclideanSpace dualDualFunctionSpace = dualFunctionSpace.getDualSpace();
	final EuclideanSpace sobolevSpace = Generator.getInstance().getSpaceGenerator()
			.getTrigonometricSobolevSpace(Generator.getInstance().getFieldGenerator().getRealLine(), dim, sobOrder);
	final EuclideanSpace dualSobolevSpace = sobolevSpace.getDualSpace();

	final EuclideanSpace dualDualSobolevSpace = dualSobolevSpace.getDualSpace();

	@Test
	public void spaceEqualsBidualSpaceTest() {
		Assert.assertEquals(space, dualDualSpace);
	}

	@Test
	public void functionSpaceEqualsBidualFunctionSpaceTest() {
		Assert.assertEquals(functionSpace, dualDualFunctionSpace);
	}

	@Test
	public void sobolevFunctionSpaceEqualsBidualSobolevFunctionSpaceTest() {
		Assert.assertEquals(sobolevSpace, dualDualSobolevSpace);
	}

	@Test
	public void spaceTest() {
		Assert.assertTrue(testEuclideanSpace(space));
	}

	@Test
	public void functionSpaceTest() {
		Assert.assertTrue(testEuclideanSpace(functionSpace));
	}

	@Test
	public void sobolevSpaceTest() {
		Assert.assertTrue(testEuclideanSpace(sobolevSpace));
	}

	@Test
	public void spaceDualDualTest() {
		Assert.assertTrue(testDualSpace(dualFunctionSpace));
	}

	@Test
	public void spaceSobolevDualDualTest() {
		Assert.assertTrue(testDualSpace(dualSobolevSpace));
	}

	public boolean testEuclideanSpace(EuclideanSpace space) {
		boolean ans = true;
		for (Vector a : space.genericBaseToList()) {
			String s = "";
			for (Vector b : space.getDualSpace().genericBaseToList()) {
				final Functional x = (Functional) b;
				final Element c = x.get(a);
				if (!x.getSourceVec().equals(a)) {
					ans = ans && Math.abs(((Real) c).getRepresentant()) < 1e-2;
				} else {
					ans = ans && Math.abs(((Real) c).getRepresentant() - 1d) < 1e-2;
				}
				s += c.toString() + "  ";
			}
			logger.info(s);
		}
		return ans;
	}

	public boolean testDualSpace(EuclideanSpace space) {
		boolean ans = true;
		for (Vector a : space.genericBaseToList()) {
			final Functional x = (Functional) a;
			String s = "";
			for (Vector b : space.getDualSpace().genericBaseToList()) {
				final Element c = x.get(b);
				if (!x.getSourceVec().equals(b)) {
					ans = ans && Math.abs(((Real) c).getRepresentant()) < 1e-2;
				} else {
					ans = ans && Math.abs(((Real) c).getRepresentant() - 1d) < 1e-2;
				}
				s += c.toString() + "  ";
			}
			logger.info(s);
		}
		return ans;
	}

}
