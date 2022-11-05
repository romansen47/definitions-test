package definitions.prototypes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContextAware;

import definitions.SpringConfiguration;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.fields.impl.ComplexPlane;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.groups.GroupGenerator;
import definitions.structures.abstr.algebra.rings.DiscreetDomain;
import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

public abstract class GenericTest {

	public static final Logger logger = LogManager.getLogger(GenericTest.class);

	protected static SpringConfiguration springConfiguration;

	protected static Generator generator;
	protected static GroupGenerator groupGenerator;
	protected static SpaceGenerator spaceGenerator;
	protected static DiscreetSemiRing naturals;
	protected static DiscreetDomain integers;
	protected static PrimeField rationals;
	protected static RealLine realLine;
	protected static ComplexPlane complexPlane;

	public static ComplexPlane getComplexPlane() {
		return complexPlane;
	}

	public static Generator getGenerator() {
		return generator;
	}

	public Logger getLogger() {
		return logger;
	}

	public static RealLine getRealLine() {
		return realLine;
	}

	public static SpaceGenerator getSpaceGenerator() {
		return spaceGenerator;
	}

	@BeforeClass
	public static void prepare() {
		if (springConfiguration == null) {
			setSpringConfiguration(getSpringConfiguration());
		}
	}

	public static ApplicationContextAware getSpringConfiguration() {
		if (springConfiguration == null) {
			logger.debug("Initializing Spring configuration\r");
			setGeneratos();
		}
		return springConfiguration;
	}

	private static void setGeneratos() {
		setSpringConfiguration(SpringConfiguration.getSpringConfiguration());
		setGenerator(Generator.getInstance());
		GroupGenerator groupGen = Generator.getInstance().getGroupGenerator();
		setGroupGenerator(groupGen);
		setSpaceGenerator(generator.getSpaceGenerator());
		setNaturals(groupGen.getNaturals());
		setIntegers(groupGen.getIntegers());
		setRationals(groupGen.getRationals());
		setRealLine(Generator.getInstance().getFieldGenerator().getRealLine());
		ComplexPlane.setRealLine(realLine);
		setComplexPlane(new ComplexPlane());
	}

	@Before
	public void logNameOfTest() {
		getLogger().info("test class: {}", getClass());
	}

	public static void setComplexPlane(final ComplexPlane complexPlane) {
		GenericTest.complexPlane = complexPlane;
	}

	public static void setGenerator(final Generator generator) {
		GenericTest.generator = generator;
	}

	public static void setRealLine(final RealLine realLine) {
		GenericTest.realLine = realLine;
	}

	public static void setSpaceGenerator(final SpaceGenerator spaceGenerator) {
		GenericTest.spaceGenerator = spaceGenerator;
	}

	public static void setSpringConfiguration(final ApplicationContextAware springConfiguration) {
		GenericTest.springConfiguration = (SpringConfiguration) springConfiguration;
	}

	/**
	 * @param naturals the naturals to set
	 */
	public static void setNaturals(DiscreetSemiRing naturals) {
		GenericTest.naturals = naturals;
	}

	/**
	 * @param integers the integers to set
	 */
	public static void setIntegers(DiscreetDomain integers) {
		GenericTest.integers = integers;
	}

	/**
	 * @param rationals the rationals to set
	 */
	public static void setRationals(PrimeField rationals) {
		GenericTest.rationals = rationals;
	}

	/**
	 * @return the integers
	 */
	public static DiscreetDomain getIntegers() {
		return integers;
	}

	/**
	 * @return the rationals
	 */
	public static PrimeField getRationals() {
		return rationals;
	}

	/**
	 * @return the naturals
	 */
	public static DiscreetSemiRing getNaturals() {
		return naturals;
	}

	public static GroupGenerator getGroupGenerator() {
		return groupGenerator;
	}

	public static void setGroupGenerator(GroupGenerator groupGenerator) {
		GenericTest.groupGenerator = groupGenerator;
	}

}
