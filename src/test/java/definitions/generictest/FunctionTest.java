/**
 *
 */
package definitions.generictest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.impl.GenericTest;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalSobolevSpace;
import definitions.structures.euclidean.mappings.impl.DerivativeOperator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

/**
 * @author RoManski
 *
 */
public class FunctionTest extends GenericTest {

	static final int trigonometricDegree = 20;
	static final int sobolevDegree = 20;
	static int derivativeDegree = 2;

	static EuclideanFunctionSpace trigSpace;
	static Function sine;
	static Function cosine;
	static Function derivative;

	/**
	 * @throws java.lang.Exception an exception if sth went wrong
	 */
	@Before
	public void setUp() throws Exception {

		final ISpaceGenerator spGen = getSpaceGenerator();
		FunctionTest.trigSpace = spGen.getTrigonometricSobolevSpace(getFieldGenerator().getRealLine(),
				FunctionTest.trigonometricDegree, FunctionTest.sobolevDegree);

		FunctionTest.sine = (Function) FunctionTest.trigSpace.genericBaseToList().get(1);
		FunctionTest.cosine = (Function) FunctionTest.trigSpace.genericBaseToList()
				.get(1 + FunctionTest.trigonometricDegree);

	}

	/**
	 * Test method for
	 * {@link definitions.structures.abstr.vectorspaces.vectors.Function#getDerivative(int)}.
	 */
	@Test
	public final void testGetDerivativeInt() {
		final DerivativeOperator derivativeBuilder = ((FiniteDimensionalSobolevSpace) FunctionTest.trigSpace)
				.getDerivativeBuilder();

		Function highDerivative;
		final EuclideanSpace space = FunctionTest.trigSpace;
		final Function newSine = FunctionTest.sine.getProjection(space);
		double sumOfDistances = 0;
		for (int i = 0; i < FunctionTest.derivativeDegree; i++) {
			highDerivative = ((Function) derivativeBuilder.get(newSine, (4 * i) + 1));
			FunctionTest.cosine.plotCompare(-Math.PI, Math.PI, highDerivative);
			sumOfDistances += space.distance(highDerivative, newSine).getRepresentant();

			highDerivative = ((Function) derivativeBuilder.get(newSine, (4 * i) + 2));
			Function tmp = FunctionTest.trigSpace.stretch(FunctionTest.sine, FunctionTest.trigSpace.getField().get(-1));
			tmp.plotCompare(-Math.PI, Math.PI, highDerivative);
			sumOfDistances += space.distance(highDerivative, tmp).getRepresentant();

			highDerivative = ((Function) derivativeBuilder.get(newSine, (4 * i) + 3));
			tmp = FunctionTest.trigSpace.stretch(FunctionTest.cosine, FunctionTest.trigSpace.getField().get(-1));
			tmp.plotCompare(-Math.PI, Math.PI, highDerivative);
			sumOfDistances += space.distance(highDerivative, tmp).getRepresentant();

			highDerivative = ((Function) derivativeBuilder.get(newSine, (4 * i) + 4));
			FunctionTest.sine.plotCompare(-Math.PI, Math.PI, highDerivative);
			sumOfDistances += space.distance(highDerivative, FunctionTest.sine).getRepresentant();
		}
		Assert.assertEquals(0d, sumOfDistances / (trigonometricDegree * sobolevDegree), 1e-2);

	}

}
