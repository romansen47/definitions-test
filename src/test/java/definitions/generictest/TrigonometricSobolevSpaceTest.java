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
 * Sobolev tests make sense only on continuous functions.
 * 
 * @author roman
 *
 */
public class TrigonometricSobolevSpaceTest extends GenericSpaceTest {

	public static final Logger logger = LogManager.getLogger(TrigonometricSobolevSpaceTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	@Before
	public void setUp() throws DevisionByZeroException, IOException, ExtendingFailedException {
		eps = 1e1;
		setDegree(3);
		setSobolevDegree(1);
		eps = 1d;
		super.setUp();
		setSpace(GenericTest.getSpaceGenerator().getTrigonometricSobolevSpace(getField(), getDegree(),
				getSobolevDegree()));
	}

}
