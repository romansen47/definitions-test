package definitions.prototypes;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectors.specialfunctions.ExponentialFunction;
import definitions.structures.euclidean.vectors.specialfunctions.Sine;
import exceptions.DevisionByZeroException;
import exceptions.ExtendingFailedException;

/**
 * example for polynomial regression. extensions by linear functions must be
 * avoided (since they make no sense)
 * 
 * @author ro
 *
 */
public abstract class GenericPolynomeRegressionTest extends GenericSpaceTest {

	public static final Logger logger = LogManager.getLogger(GenericPolynomeRegressionTest.class);

	@Override
	public Logger getLogger() {
		return GenericPolynomeRegressionTest.logger;
	}

	// for smaller intervalls we do get greater distances
	protected double intervallSize = Math.PI;
	protected double left = -intervallSize;
	protected double right = intervallSize;

	private Function sin;
	private Function exp;

	protected static final Field realLine = Generator.getInstance().getFieldGenerator().getRealLine();

	@Before
	@Override
	public void setUp() throws IOException, DevisionByZeroException, ExtendingFailedException {
		sin = new Sine(1, 0, Math.PI / right) {

			@Override
			public Field getField() {
				return realLine;
			}
		};

		exp = new ExponentialFunction() {

			@Override
			public Field getField() {
				return realLine;
			}
		};

		super.setUp();
	}

	@Test
	public void regressionExpByPolynomialsTest() throws Throwable {
		final Function ans = exp.getProjection(space);
		exp.plotCompare(left, right, ans);
		double distance = space.distance(ans, exp).getRepresentant();
		logger.info("distance to exp is {}", distance);
		double norm = space.norm(ans).getDoubleValue();
		logger.info("relative distance to exp is {}", distance / norm);
		Assert.assertTrue(distance / norm < getEps());
	}

	@Test
	public void regressionSinByPolynomialsTest() throws Throwable {
		final Function ans = sin.getProjection(space);
		ans.plotCompare(left, right, sin);
		double distance = space.distance(ans, sin).getRepresentant();
		logger.info("distance to sin is {}", distance);
		double norm = space.norm(ans).getDoubleValue();
		logger.info("relative distance to sin is {}", distance / norm);
		Assert.assertTrue(distance / norm < getEps());
	}

}
