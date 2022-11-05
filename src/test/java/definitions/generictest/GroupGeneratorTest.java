package definitions.generictest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.GenericTest;
import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.groups.DiscreetGroup;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.euclidean.Generator;

public class GroupGeneratorTest extends GenericTest {

	public static final Logger logger = LogManager.getLogger(GroupGeneratorTest.class);

	@BeforeClass
	public static void prepare() {
		GenericTest.prepare();
	}

	private Group group;
	private Element one;
	private Element minusOne;
	private Element zero;
	private Element two;
	private Element four1;
	private Element four2;
	private Element minusFour;

	@Before
	public void createStructures() {
		one = GenericTest.getIntegers().getOne();
		minusOne = GenericTest.getIntegers().getInverseElement(GenericTest.getIntegers().getOne());
		zero = GenericTest.getIntegers().getNeutralElement();
		two = GenericTest.getIntegers().addition(one, one);
		four1 = GenericTest.getIntegers().multiplication(two, two);
		four2 = GenericTest.getIntegers().addition(two, two);
		minusFour = GenericTest.getIntegers().multiplication(four2, minusOne);
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(final Group group) {
		this.group = group;
	}

	/**
	 * We test if we can create the
	 */
	@Test
	public void completionTest() {
		final DiscreetGroup rationals = Generator.getInstance().getGroupGenerator()
				.completeToGroup(Generator.getInstance().getGroupGenerator().getNaturals());
		final Element neutralElement = rationals.getNeutralElement();
		for (double i = 1; i < 10; i++) {
			final Element tmp = rationals.get(i);
			final Element inverse = rationals.getInverseElement(tmp);
			final Element addition = rationals.operation(tmp, inverse);
			final boolean ans = neutralElement.equals(addition);
			Assert.assertTrue(ans);
			logger.info("rational: {}, inverse: {}, addition: {}, check: {}", tmp, inverse, addition, ans);
		}
		final Element a = rationals.get(-5);
		final Element b = rationals.get(15);
		Element ans = rationals.operation(a, b);
		logger.info("{}+{}={}", a, b, ans);
	}

	@Test
	public void integersTest() {
		final boolean addSameAsMult = four1.equals(four2);
		final boolean sumIsZero = GenericTest.getIntegers().addition(four1, minusFour).equals(zero);
		Assert.assertTrue(addSameAsMult);
		Assert.assertTrue(sumIsZero);
	}

	@Test
	public void fieldsTest() {
		final FieldElement newZero = GenericTest.getRationals().getNeutralElement();
		final FieldElement newOne = (FieldElement) GenericTest.getRationals().getMuliplicativeMonoid()
				.getNeutralElement();
		final FieldElement newTwo = (FieldElement) GenericTest.getRationals().addition(newOne, newOne);
		final FieldElement half = (FieldElement) GenericTest.getRationals().getMuliplicativeMonoid()
				.getInverseElement(newTwo);
		FieldElement var = (FieldElement) GenericTest.getRationals().multiplication(newOne, newOne);
		FieldElement tmp;
		FieldElement debugTmp;
		for (int i = 1; i < 5; i++) {
			debugTmp = (FieldElement) GenericTest.getRationals().multiplication(var, half);
			tmp = GenericTest.getRationals().getInverseElement(debugTmp);
			var = (FieldElement) GenericTest.getRationals().addition(var, tmp);
			logger.info(i + ": " + var.toString());
		}
		logger.info("Inverse of new zero is "
				+ GenericTest.getRationals().getMuliplicativeMonoid().getInverseElement(newZero));
		logger.info("for i==5 we get an error. to be fixed, e.g. by replacing (224,208) by (16,0) somewhere...");
	}

	@Test
	public void testBinField() {

		final PrimeField field = Generator.getInstance().getGroupGenerator().getConstructedBinaries();
		logger.info(field);

		final Element zero = field.getZero();
		final Element one = field.getOne();
		logger.info("zero " + zero.toString() + ", one " + one.toString());

		logger.info("zero + zero: " + field.addition(zero, zero));
		logger.info("zero + one: " + field.addition(zero, one));
		logger.info("one + zero: " + field.addition(one, zero));
		logger.info("one + one: " + field.addition(one, one) + "\r");

		logger.info("zero * zero: " + field.multiplication(zero, zero));
		logger.info("zero * one: " + field.multiplication(zero, one));
		logger.info("one * zero: " + field.multiplication(one, zero));
		logger.info("one * one: " + field.multiplication(one, one));

	}

}
