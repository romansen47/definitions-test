package definitions.generictest;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import definitions.prototypes.GenericSpaceTest;
import definitions.prototypes.GenericTest;
import exceptions.DevisionByZeroException;
import exceptions.ExtendingFailedException;

public class TrigonometricSpaceTest extends GenericSpaceTest {

	public static final Logger logger = LogManager.getLogger(TrigonometricSpaceTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	@Before
	public void setUp() throws DevisionByZeroException, IOException, ExtendingFailedException {
		eps = 1e1;
		setDegree(3);
		setSobolevDegree(0);
		setField(GenericTest.getRealLine());
		setSpace(
				GenericTest.getSpaceGenerator().getTrigonometricSpace(GenericTest.getRealLine(), getDegree(), Math.PI));
		super.setUp();
	}
}
