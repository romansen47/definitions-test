package definitions.prototypes;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import exceptions.DevisionByZeroException;
import exceptions.ExtendingFailedException;

/**
 * generic abstract test class for trigonometric spaces
 * 
 * @author roman
 *
 */
public abstract class GenericSpaceTest extends GenericTest {

	/**
	 * a tolerance parameter
	 */
	protected double eps = 1d;

	/**
	 * the degree of tested space. in case of trigonometric space this is the
	 * trigonometric degree. this will lead to a space ofdimension 2*degree +1
	 */

	protected int degree;

	/**
	 * the sobolev degree of tested space
	 */
	protected int sobolevDegree = 0;

	/**
	 * the space
	 */
	protected EuclideanSpace space;

	/**
	 * path to prepared values of input function
	 */
	protected String path1 = "src/test/resources/test.csv";

	/**
	 * path to prepared values of input function
	 */
	protected String path2 = "src/test/resources/test2.csv";

	/**
	 * the values
	 */
	protected double[][] testValues1;

	/**
	 * the values
	 */
	protected double[][] testValues2;

	/**
	 * the 'stair case function'.
	 */
	protected GenericFunction staircaseFunction;

	/**
	 * the field
	 */
	protected Field f;

	/**
	 * getter for path of input values
	 * 
	 * @return the path for input values
	 */
	protected String getPath1() {
		return path1;
	}

	/**
	 * getter for path of input values
	 * 
	 * @return the path for input values
	 */
	protected String getPath2() {
		return path2;
	}

	/**
	 * stair case function 1
	 */
	protected Function sFunction1;

	/**
	 * stair case function 2
	 */
	protected Function sFunction2;

	@Before
	public void setUp() throws IOException, DevisionByZeroException, ExtendingFailedException {

		setField(GenericTest.getRealLine());
		testValues1 = definitions.generictest.Reader.readFile(getPath1());
		testValues2 = definitions.generictest.Reader.readFile(getPath2());
		sFunction1 = new GenericFunction() {
			private final int length = (int) testValues1[0][testValues1[0].length - 1];

			@Override
			public Field getField() {
				return GenericSpaceTest.this.getField();
			}

			@Override
			public Scalar value(final Scalar input) {
				final double newInput = ((length / (2 * Math.PI)) * ((Real) input).getDoubleValue()) + (length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
				while (((k + 1) < testValues1[0].length) && (testValues1[0][k] < l)) {
					k++;
				}
				return getField().get(testValues1[1][k]);
			}
		};
		sFunction2 = new GenericFunction() {
			private final int length = (int) testValues2[0][testValues2[0].length - 1];

			@Override
			public Field getField() {
				return GenericSpaceTest.this.getField();
			}

			@Override
			public Scalar value(final Scalar input) {
				final double newInput = ((length / (2 * Math.PI)) * ((Real) input).getDoubleValue()) + (length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
				while (((k + 1) < testValues2[0].length) && (testValues2[0][k] < l)) {
					k++;
				}
				return getField().get(testValues2[1][k]);
			}
		};
	}

	protected GenericFunction getStaircaseFunction() {
		return staircaseFunction;
	}

	protected void testOnFunction(Function f, int degree, Integer sobolevDegree, double eps) {

		org.apache.logging.log4j.Logger logger = getLogger();

		Function fProjection = f.getProjection(getSpace());

		f.plotCompare(-Math.PI, Math.PI, fProjection);

		long time1 = System.nanoTime();
		double distance = getSpace().distance(fProjection, f).getRepresentant();
		time1 = System.nanoTime() - time1;
		logger.debug("time for computing distance = {}", time1);

		long time2 = System.nanoTime();
		double norm_of_function = getSpace().norm(f).getRepresentant();
		time2 = System.nanoTime() - time2;
		logger.debug("time for computing norm of the function  = {}", time2);

		long time3 = System.nanoTime();
		double norm_of_projection = getSpace().norm(fProjection).getRepresentant();
		time3 = System.nanoTime() - time3;
		logger.debug("time for computing norm of projection = {}", time3);

		logger.debug("distance = {}", distance);
		logger.debug("norm of function = {}", norm_of_function);
		logger.debug("norm of its projection = {}", norm_of_projection);
		String s = sobolevDegree == null ? "Trig(" + degree + ")" : "on SobTrig(" + degree + "," + sobolevDegree + ")";
		logger.debug("distance / norm = {} / {} = {}  {}, expected tolerance: {}", distance, norm_of_function,
				distance / norm_of_function, s, getEps());
		Assert.assertTrue(distance / norm_of_function < eps);
	}

	@Test
	public void testOnStairCaseFunction1() {
		this.setStaircaseFunction((GenericFunction) sFunction1);
		testOnFunction(getStaircaseFunction(), getDegree(), getSobolevDegree(), getEps());
	}

	@Test
	public void testOnStairCaseFunction2() {
		this.setStaircaseFunction((GenericFunction) sFunction2);
		testOnFunction(getStaircaseFunction(), getDegree(), getSobolevDegree(), getEps());
	}

	@Test
	public void testOnContinuousFunction() throws Exception {
		testOnFunction(new GenericFunction() {

			@Override
			public Field getField() {
				return GenericTest.getRealLine();
			}

			@Override
			public Scalar value(Scalar input) {
				final Double inputValue = ((Real) input).getDoubleValue();
				final double abs = Math.abs((Math.sin(inputValue) * Math.cos(inputValue)) - 0.25);
				return Generator.getInstance().getFieldGenerator().getRealLine().get(abs);
			}
		}, getDegree(), getSobolevDegree(), getEps());
	}

	@Test
	public void testOnAbsolute() {
		final Function absolute = new GenericFunction() {
			@Override
			public Field getField() {
				return GenericTest.getRealLine();
			}

			@Override
			public Scalar value(Scalar input) {
				return getRealLine().get(Math.abs(((Real) input).getRepresentant()));
			}
		};

		testOnFunction(absolute, getDegree(), getSobolevDegree(), getEps());
	}

	@Test
	public void testOnIdentity() {
		final Function identity = new GenericFunction() {

			private Field f = GenericTest.getRealLine();

			@Override
			public Field getField() {
				return f;
			}

			@Override
			public Scalar value(Scalar input) {
				return input;
			}

		};
		testOnFunction(identity, getDegree(), getSobolevDegree(), getEps());
	}

	protected Field getField() {
		return f;
	}

	protected void setField(Field f) {
		this.f = f;
	}

	protected double getEps() {
		return eps;
	};

	protected int getDegree() {
		return degree;
	}

	protected void setDegree(Integer trigDegree) {
		this.degree = trigDegree;
	}

	protected EuclideanSpace getSpace() {
		return space;
	}

	protected void setSpace(EuclideanSpace space) {
		this.space = space;
	}

	protected void setPath1(final String path) {
		this.path1 = path;
	}

	protected void setPath2(final String path) {
		this.path2 = path;
	}

	protected void setStaircaseFunction(final GenericFunction staircaseFunction) {
		this.staircaseFunction = staircaseFunction;
	}

	protected Integer getSobolevDegree() {
		return this.sobolevDegree;
	}

	protected void setSobolevDegree(Integer sDegree) {
		this.sobolevDegree = sDegree;
	}
}
