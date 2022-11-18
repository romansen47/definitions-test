package definitions.prototypes.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContextAware;

import definitions.SpringConfiguration;
import definitions.prototypes.IGenericTest;

public abstract class GenericTest implements IGenericTest {

	protected static SpringConfiguration springConfiguration;

	public Logger getLogger() {
		return LogManager.getLogger(this);
	};

	@BeforeClass
	public static void prepare() {
		if (springConfiguration == null) {
			setSpringConfiguration(SpringConfiguration.getSpringConfiguration());
		}
	}

	/**
	 * Setter for spring configuration
	 * 
	 * @param springConfiguration the springConfiguration
	 */
	public static void setSpringConfiguration(final ApplicationContextAware springConfiguration) {
		GenericTest.springConfiguration = (SpringConfiguration) springConfiguration;
	}

}
