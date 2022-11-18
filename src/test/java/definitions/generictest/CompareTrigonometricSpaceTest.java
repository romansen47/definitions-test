package definitions.generictest;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.impl.GenericSpaceTest;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import exceptions.DevisionByZeroException;
import exceptions.ExtendingFailedException;

/**
 * this tests that extending a trigonometric space by linear functions truly
 * produces a higher dimensional space that approximates better.
 * 
 * @author roman
 *
 */
public class CompareTrigonometricSpaceTest extends GenericSpaceTest {

	protected static final Logger logger = LogManager.getLogger(CompareTrigonometricSpaceTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	EuclideanFunctionSpace space;
	EuclideanFunctionSpace extendedSpace;
	EuclideanFunctionSpace sobolevSpace;

	EuclideanFunctionSpace extendedSobolevSpace;

	@Override
	public int getDegree() {
		return 3;
	}

	@Override
	public Integer getSobolevDegree() {
		return 1;
	}

	@Override
	@Before
	public void setUp() throws DevisionByZeroException, IOException, ExtendingFailedException {
		eps = 9e-1;
		space = getSpaceGenerator().getTrigonometricSpace(getRealLine(), getDegree(), Math.PI);
		setSpace(space);
		sobolevSpace = getSpaceGenerator().getTrigonometricSobolevSpace(getRealLine(), getDegree(), getSobolevDegree());
		extendedSpace = (EuclideanFunctionSpace) getSpaceGenerator()
				.getTrigonometricFunctionSpaceWithLinearGrowth(getRealLine(), getDegree(), Math.PI);
		extendedSobolevSpace = (EuclideanFunctionSpace) getSpaceGenerator()
				.getTrigonometricSobolevSpaceWithLinearGrowth(getRealLine(), getSobolevDegree(), Math.PI, getDegree());
		super.setUp();
	}

	@Test
	public void compareStairCaseFunction1Test() throws DevisionByZeroException, ExtendingFailedException {
		this.setStaircaseFunction((GenericFunction) sFunction1);
		Function f = sFunction1.getProjection(space);
		Function extendedf = sFunction1.getProjection(extendedSpace);
		sFunction1.plotCompare(-Math.PI, Math.PI, f);
		sFunction1.plotCompare(-Math.PI, Math.PI, extendedf);
		f.plotCompare(-Math.PI, Math.PI, extendedf);
		double distance = extendedSpace.distance(f, extendedf).getDoubleValue();
		double distance1 = extendedSpace.distance(sFunction1, extendedf).getDoubleValue();
		double distance2 = extendedSpace.distance(f, sFunction1).getDoubleValue();
		double sum = Math.pow(Math.pow(distance, 2) + Math.pow(distance1, 2) + Math.pow(distance2, 2), 0.5);
		logger.debug("{}+{}+{} = {}, relative distance = distance / sum = {}", distance, distance1, distance2, sum,
				distance / sum);
		Assert.assertTrue(distance / sum < eps);
	}

	@Test
	public void compareStairCaseFunction2Test() throws DevisionByZeroException, ExtendingFailedException {
		this.setStaircaseFunction((GenericFunction) sFunction2);
		Function f = sFunction2.getProjection(space);
		Function extendedf = sFunction2.getProjection(extendedSpace);
		sFunction2.plotCompare(-Math.PI, Math.PI, f);
		sFunction2.plotCompare(-Math.PI, Math.PI, extendedf);
		f.plotCompare(-Math.PI, Math.PI, extendedf);
		double distance = extendedSpace.distance(f, extendedf).getDoubleValue();
		double distance1 = extendedSpace.distance(sFunction2, f).getDoubleValue();
		double distance2 = extendedSpace.distance(sFunction2, extendedf).getDoubleValue();
		double sum = Math.pow(Math.pow(distance, 2) + Math.pow(distance1, 2) + Math.pow(distance2, 2), 0.5);
		logger.debug("{}+{}+{} = {}, relative distance = distance / sum = {}", distance, distance1, distance2, sum,
				distance / sum);
		Assert.assertTrue(distance / sum < eps);
	}

	// this one shows problems.
	@Test
	public void compareAgainstSobolev() {
		Function f = sFunction1.getProjection(space);
		Function fSobolev = sFunction1.getProjection(sobolevSpace);
		f.plotCompare(-Math.PI, Math.PI, fSobolev);
		double distance1 = space.distance(f, sFunction1).getDoubleValue();
		double distance2 = space.distance(fSobolev, sFunction1).getDoubleValue();
		logger.debug("distance1={}, distance2={}", distance1, distance2);
		Function f2 = sFunction1.getProjection(extendedSpace);
		Function fSobolev2 = sFunction1.getProjection(extendedSobolevSpace);
		f2.plotCompare(-Math.PI, Math.PI, fSobolev2);
	}

}
