package definitions.structures.abstr.groups.impl;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import definitions.prototypes.impl.GenericTest;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.algebra.rings.SemiDomain;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.impl.Naturals;
import definitions.structures.impl.Naturals.NaturalNumber;

public class PrimesTest extends GenericTest {

	public static final Logger logger = LogManager.getLogger(PrimesTest.class);

	SemiDomain integers = (Naturals) getNaturals();

	private final int maxValue = 100000;

	final Function distribution = new Function() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Map<Vector, Scalar> getCoordinates() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Map<EuclideanSpace, Map<Vector, Scalar>> getCoordinatesMap() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setCoordinates(final Map<Vector, Scalar> coordinates) {

		}

		@Override
		public void setCoordinates(final Map<Vector, Scalar> coordinates, final EuclideanSpace space) {
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Scalar value(final Scalar input) {
			int ans = 0;
			for (Naturals.NaturalNumber i = (NaturalNumber) ((Naturals) integers).get(2); i
					.getRepresentant() < ((Real) input)
							.getRepresentant(); i = (NaturalNumber) integers.addition(i, integers.getOne())) {
				if (integers.isPrimeElement(i)) {
					ans += 1;
				}
			}
			return getRealLine().get(ans);
		}

	};

	@Test
	public void testPrimes() {
		NaturalNumber number99991 = (NaturalNumber) (((Naturals) integers).get(99991));
		boolean isPrime99991 = integers.isPrimeElement(number99991);
		String s = isPrime99991 ? "is a prime" : "is not a prime";
		logger.debug("{} {}", number99991, s);
		Assert.assertTrue(isPrime99991);
		this.distribution.plot(0, maxValue);
	}

}
