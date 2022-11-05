package definitions.generictest;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import definitions.prototypes.GenericSpaceTest;
import definitions.prototypes.GenericTest;
import exceptions.DevisionByZeroException;
import exceptions.ExtendingFailedException;

/**
 * 
 * @author ro
 *
 */
public class TrigonometricSobolevSpaceWithLinearGrowthTest extends GenericSpaceTest {

	public static final Logger logger = LogManager.getLogger(TrigonometricSobolevSpaceWithLinearGrowthTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	@Before
	public void setUp() throws DevisionByZeroException, IOException, ExtendingFailedException {

		eps = 1e2;
		setDegree(3);
		setSobolevDegree(1);
		setSpace(GenericTest.getSpaceGenerator().getTrigonometricSobolevSpaceWithLinearGrowth(GenericTest.getRealLine(),
				getSobolevDegree(), Math.PI, getDegree()));
		super.setUp();

	}

}
