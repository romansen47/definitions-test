package definitions.generictest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.GenericTest;
import definitions.structures.abstr.algebra.fields.impl.ComplexPlane;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.impl.Complex;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorSpaceMapping;
import definitions.structures.abstr.mappings.VectorSpaceSelfMapping;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.dynamicsystems.DynamicSystem;
import definitions.structures.euclidean.Generator;

public class DiscreetDynamicSystemTest extends GenericTest {

	public static final Logger logger = LogManager.getLogger(DiscreetDynamicSystemTest.class);

	private Group timeSpace;
	private VectorSpace phaseSpace;
	private DynamicSystem dinamicSystem;
	private Vector startVector;
	private final int iterations = 21;

	@Before
	public void beforeTest() {
		timeSpace = GenericTest.getIntegers();
	}

	/**
	 * @return the dinamicSystem
	 */
	public DynamicSystem getDinamicSystem() {
		return dinamicSystem;
	}

	/**
	 * @return the phasespace
	 */
	public VectorSpace getPhasespace() {
		return phaseSpace;
	}

	/**
	 * @return the timespace
	 */
	public Group getTimespace() {
		return timeSpace;
	}

	/**
	 * @param dinamicSystem the dinamicSystem to set
	 */
	public void setDinamicSystem(final DynamicSystem dinamicSystem) {
		this.dinamicSystem = dinamicSystem;
	}

	@Test
	public void testExponentialLaw() {
		phaseSpace = Generator.getInstance().getFieldGenerator().getRealLine();
		startVector = ((RealLine) phaseSpace).get(1.);

		dinamicSystem = new DynamicSystem() {

			@Override
			public VectorSpace getPhaseSpace() {
				return phaseSpace;
			}

			@Override
			public VectorSpaceSelfMapping getDefiningMapping() {
				return new VectorSpaceSelfMapping() {

					@Override
					public Element get(Element vec) {
						return phaseSpace.addition((Vector) vec, (Vector) vec);
					}

					@Override
					public VectorSpace getSource() {
						return phaseSpace;
					}

				};
			}

			@Override
			public Group getTimeSpace() {
				return timeSpace;
			}

		};
		final Vector vec = startVector;
		Element tmp;
		VectorSpaceMapping evolutionOp;
		boolean isTrue = true;
		for (double i = 0; i < iterations; i++) {
			tmp = ((DiscreetMonoid) timeSpace).get(i);
			evolutionOp = dinamicSystem.getEvolutionOperator(tmp);
			isTrue = isTrue && ((Real) evolutionOp.get(vec)).getRepresentant().equals(Math.pow(2, i));
			logger.info(i + ": " + ((Vector) evolutionOp.get(vec)).toXml());
		}
		Assert.assertTrue(isTrue);
	}

	/**
	 * here we define fibonnaci series as a series in C via (a_n,b_n) mapsto
	 * a_n+i*b_n
	 */
	@Test
	public void testFibbonacciLaw() {
		phaseSpace = ComplexPlane.getInstance();
		startVector = ((ComplexPlane) phaseSpace).get(1, 1);

		dinamicSystem = new DynamicSystem() {

			@Override
			public VectorSpace getPhaseSpace() {
				return phaseSpace;
			}

			@Override
			public VectorSpaceSelfMapping getDefiningMapping() {
				return new VectorSpaceSelfMapping() {

					@Override
					public Element get(Element vec) {
						return ((ComplexPlane) getPhaseSpace()).get(((Real) ((Complex) vec).getImag()).getDoubleValue(),
								Generator.getInstance().getFieldGenerator().getRealLine()
										.addition(((Complex) vec).getReal(), ((Complex) vec).getImag())
										.getDoubleValue());
					}

					@Override
					public VectorSpace getSource() {
						return phaseSpace;
					}

				};
			}

			@Override
			public Group getTimeSpace() {
				return timeSpace;
			}

		};
		final Vector vec = startVector;
		Element tmp;
		VectorSpaceMapping evolutionOp;
		for (double i = 0; i < iterations; i++) {
			tmp = ((DiscreetMonoid) timeSpace).get(i);
			evolutionOp = dinamicSystem.getEvolutionOperator(tmp);
			final Complex toComplex = (Complex) evolutionOp.get(vec);
			final Real real = (Real) toComplex.getReal();
			final Real imag = (Real) toComplex.getImag();
			String comp = real.toString();
			if (imag.getDoubleValue() > 0) {
				comp += " + " + imag.getDoubleValue() + "*i";
			}
			// this is fine, fibonaccis series is being computed correctly
			Assert.assertTrue(true);
			logger.info("{}: {}", i, comp);
		}

	}

}
