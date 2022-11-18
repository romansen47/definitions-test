package definitions.generictest;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import definitions.prototypes.impl.GenericSpaceTest;
import exceptions.DevisionByZeroException;
import exceptions.ExtendingFailedException;

public class TrigonometricSpaceWithLinearGrowthTest extends GenericSpaceTest {

	public static final Logger logger = LogManager.getLogger(TrigonometricSpaceWithLinearGrowthTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	@Before
	public void setUp() throws DevisionByZeroException, IOException, ExtendingFailedException {
		eps = 1d;
		setDegree(3);
		setSobolevDegree(0);
		setField(getRealLine());
		setSpace(
				getSpaceGenerator().getTrigonometricFunctionSpaceWithLinearGrowth(getRealLine(), getDegree(), Math.PI));
		super.setUp();
	}
}
