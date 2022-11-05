package definitions.generictest;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import definitions.prototypes.GenericPolynomeRegressionTest;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import exceptions.DevisionByZeroException;
import exceptions.ExtendingFailedException;

public class PolynomeRegressionSobolevTest extends GenericPolynomeRegressionTest {

	public static final Logger logger = LogManager.getLogger(PolynomeRegressionSobolevTest.class);

	@Override
	public Logger getLogger() {
		return PolynomeRegressionSobolevTest.logger;
	}

	@Override
	public void setUp() throws IOException, DevisionByZeroException, ExtendingFailedException {
		eps = 1e1;
		super.setUp();
		setSpace((EuclideanFunctionSpace) Generator.getInstance().getSpaceGenerator()
				.getPolynomialSobolevSpace(realLine, getDegree(), right, getSobolevDegree()));
	}

	@Override
	public int getDegree() {
		return 5;
	}

	@Override
	public Integer getSobolevDegree() {
		return 1;
	}
}
