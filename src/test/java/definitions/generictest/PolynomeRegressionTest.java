package definitions.generictest;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import definitions.prototypes.GenericPolynomeRegressionTest;
import definitions.structures.euclidean.Generator;
import exceptions.DevisionByZeroException;
import exceptions.ExtendingFailedException;

public class PolynomeRegressionTest extends GenericPolynomeRegressionTest {

	public static final Logger logger = LogManager.getLogger(PolynomeRegressionTest.class);

	@Override
	public Logger getLogger() {
		return PolynomeRegressionTest.logger;
	}

	@Override
	public void setUp() throws IOException, DevisionByZeroException, ExtendingFailedException {

		super.setUp();
		setSpace(Generator.getInstance().getSpaceGenerator().getPolynomialFunctionSpace(realLine, getDegree(), right,
				true));
	}

	@Override
	public int getDegree() {
		return 5;
	}

	@Override
	public Integer getSobolevDegree() {
		return 0;
	}
}
