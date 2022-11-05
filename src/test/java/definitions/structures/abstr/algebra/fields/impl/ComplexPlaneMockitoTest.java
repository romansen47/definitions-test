package definitions.structures.abstr.algebra.fields.impl;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import definitions.prototypes.GenericTest;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Complex;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;

public class ComplexPlaneMockitoTest extends GenericTest {

	ComplexPlane complexPlane = getComplexPlane();
	RealLine realLine = getRealLine();
	Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix = complexPlane.getMultiplicationMatrix();
	Vector baseVector1 = complexPlane.genericBaseToList().get(0);
	Vector baseVector2 = complexPlane.genericBaseToList().get(1);
	VectorSpaceHomomorphism identity = multiplicationMatrix.get(baseVector1);
	VectorSpaceHomomorphism rotation = multiplicationMatrix.get(baseVector2);

	@Test
	public void complexTest() {
		Assert.assertEquals(complexPlane.complex(), complexPlane.getZero());
	}

	@Test
	public void conjugateTest() {
		Assert.assertEquals(complexPlane.conjugate((Scalar) baseVector1), baseVector1);
		Assert.assertEquals(complexPlane.conjugate((Scalar) baseVector2),
				complexPlane.product(complexPlane.getMinusOne(), baseVector2));
	}

	@Test
	public void getMultiplicationMatrixTest() {
		Assert.assertEquals(identity.get(baseVector1), baseVector1);
		Assert.assertEquals(identity.get(baseVector2), baseVector2);
		Assert.assertEquals(rotation.get(baseVector1), baseVector2);
		Assert.assertEquals(rotation.get(baseVector2),
				complexPlane.multiplication(getComplexPlane().getMinusOne(), baseVector1));
	}

	@Test
	public void getMultiplicativeInverseElementTest() {
		Complex y = complexPlane.getMultiplicativeInverseElement(complexPlane.get(2, 0));
		Assert.assertEquals(y.getReal(), realLine.get(0.5));
		Assert.assertEquals(y.getImag(), realLine.getZero());

		Complex z = complexPlane.getMultiplicativeInverseElement(complexPlane.getI());
		Assert.assertEquals(z.getReal(), realLine.getZero());
		Assert.assertEquals(z.getImag(), realLine.get(-1));

	}

	@Test
	public void getPrimeFieldTest() {
		Assert.assertEquals(complexPlane.getPrimeField(), Generator.getInstance().getGroupGenerator().getRationals());
	}

}
