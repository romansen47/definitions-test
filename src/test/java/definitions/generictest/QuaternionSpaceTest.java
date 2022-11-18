package definitions.generictest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import definitions.prototypes.impl.GenericTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.QuaternionSpace;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Quaternion;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

public class QuaternionSpaceTest extends GenericTest {

	public static final Logger logger = LogManager.getLogger(QuaternionSpaceTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	private final double eps = 1e-3;

	public static QuaternionSpace quaternionSpace = (QuaternionSpace) QuaternionSpace.getInstance();

	final FunctionSpace genericFunctionSpace = new FunctionSpace() {

		@Override
		public Field getField() {
			return getRealLine();
		}

		@Override
		public double getEpsilon() {
			return eps;
		}

		@Override
		public double[] getInterval() {
			return new double[] { -1, 1 };
		}

	};

	public Function fSquare = new GenericFunction() {

		public Field field = getRealLine();

		@Override
		public Field getField() {
			return field;
		}

		@Override
		public Vector value(Scalar input) {
			double x = ((Real) input).getRepresentant();
			return (getRealLine().get(2 * Math.pow(x, 2)));
		}

	};

	public Function fNegSquare = new GenericFunction() {

		public Field field = getRealLine();

		@Override
		public Field getField() {
			return field;
		}

		@Override
		public Vector value(Scalar input) {
			double x = ((Real) input).getRepresentant();
			return (getRealLine().get(-2 * Math.pow(x, 2)));
		}

	};

	public Function fReal = new GenericFunction() {

		public Field field = getRealLine();

		@Override
		public Field getField() {
			return field;
		}

		@Override
		public Vector value(Scalar input) {
			double x = ((Real) input).getRepresentant();
			Quaternion in = new Quaternion(x, x, x, x);
			return ((Quaternion) quaternionSpace.multiplication(in, in)).getReal();
		}

	};

	public Function fI = new GenericFunction() {

		public Field field = getRealLine();

		@Override
		public Field getField() {
			return field;
		}

		@Override
		public Vector value(Scalar input) {
			double x = ((Real) input).getRepresentant();
			Quaternion in = new Quaternion(x, x, x, x);
			return ((Quaternion) quaternionSpace.multiplication(in, in)).getI();
		}

	};

	public Function fJ = new GenericFunction() {

		public Field field = getRealLine();

		@Override
		public Field getField() {
			return field;
		}

		@Override
		public Vector value(Scalar input) {
			double x = ((Real) input).getRepresentant();
			Quaternion in = new Quaternion(x, x, x, x);
			return ((Quaternion) quaternionSpace.multiplication(in, in)).getJ();
		}

	};
	public Function fK = new GenericFunction() {

		public Field field = getRealLine();

		@Override
		public Field getField() {
			return field;
		}

		@Override
		public Vector value(Scalar input) {
			double x = ((Real) input).getRepresentant();
			Quaternion in = new Quaternion(x, x, x, x);
			return ((Quaternion) quaternionSpace.multiplication(in, in)).getK();
		}

	};

	@Test
	public void test() {
		var x = quaternionSpace.get(0, 0, 2, 0);
		logger.debug("a = {}", x);
		var y = quaternionSpace.get(0, 0, 3, 0);
		logger.debug("b = {}", y);
		var z1 = quaternionSpace.addition(x, y);
		logger.debug("a + b = {}", z1);
		Assert.assertEquals((z1.getJ()), getRealLine().get(5));
		var z2 = quaternionSpace.multiplication(x, y);
		logger.debug("a * b = {}", z2);
		Assert.assertEquals(((Quaternion) z2).getReal(), getRealLine().get(-6));
	}

	@Test
	public void test2() {
		fReal.plotCompare(-1, 1, fNegSquare);
		fI.plotCompare(-1, 1, fSquare);
		fJ.plotCompare(-1, 1, fSquare);
		fK.plotCompare(-1, 1, fSquare);
		Double scalarProduct = ((Real) genericFunctionSpace.getIntegral(fReal, fNegSquare, -1, 1, eps))
				.getRepresentant();
		logger.debug("scalar product <fReal,fNegSquare> = {}", scalarProduct);
		Double normFReal = Math
				.pow(((Real) genericFunctionSpace.getIntegral(fReal, fReal, -1, 1, eps)).getRepresentant(), 0.5);
		logger.debug("norm ||fReal|| = {}", normFReal);
		Double normFNegSquare = Math.pow(
				((Real) genericFunctionSpace.getIntegral(fNegSquare, fNegSquare, -1, 1, eps)).getRepresentant(), 0.5);
		logger.debug("norm ||fNegSquare|| = {}", normFNegSquare);
		logger.debug("scalarProduct / (normFReal * normFNegSquare) = {} / ( {} * {} ) = {}", scalarProduct, normFReal,
				normFNegSquare, scalarProduct / (normFReal * normFNegSquare));
		Assert.assertEquals(1d, scalarProduct / (normFReal * normFNegSquare), eps);

	}

}
