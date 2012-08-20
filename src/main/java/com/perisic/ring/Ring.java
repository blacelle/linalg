package com.perisic.ring;

import java.math.*;

/**
 * Title: Ring
 * <p>
 * Description: An abstract Ring class.
 * <p>
 * This class is the base class for all rings and fields of the package. The
 * following methods are abstract and have to be overridden by all child rings
 * (a child ring is a child class of this class which implements a ring in the
 * meaning of a ring in algebra): add(), mult(), zero(), neg() and equalZero().
 * <P>
 * The following methods are semi abstract, that means they throw an error
 * message if they are called. If applicable for the ring, they have to be
 * overridden: one() and inv().
 * <P>
 * Rings which are not a field might override the methods ediv(), mod() and
 * tdiv() if applicable.
 * <P>
 * See the documentation below for details.
 * <P>
 * <UL>
 * <li>Last Change: 13.12.2003: eltToString(), GPL.
 * <li>
 * Copyright: (c) Marc Conrad, 2002, 2003
 * <li>
 * Email: <a href="mailto:ring@perisic.com">ring@perisic.com</a>. Please let me
 * know if you use this software.
 * <li>
 * WWW: www.ring.perisic.com
 * <li>The com.perisic.ring library is distributed under the terms of the <a
 * href="http://www.gnu.org/copyleft/lesser.html" target="_blank">GNU Lesser
 * General Public License (LGPL)</a>.
 * <li>If you require the package under a different licence please contact me.
 * <li>disclaimer: The classes are provided "as is". There is no warranty
 * implied by using the com.perisic.ring package.
 * </UL>
 * 
 * @author Marc Conrad
 * @version 0.2
 **/
abstract public class Ring {

	private static class myComplexField extends ModularRing {
		public boolean isField() {
			return true;
		}

		public String toString() {
			return "C";
		}

		public myComplexField() {
			super((new PolynomialRing(DoubleField.R, "i")).map("i^2 + 1"));
			hideMod();
		}
	}

	/**
	 * The field C of complex numbers. This is constructed as a modular ring
	 * over R[i].
	 */
	public static final ModularRing C = new myComplexField();

	/**
	 * The ring Z of integers. This is a wrapper class for BigInteger of the
	 * java.math package.
	 */
	public static final IntegerRing Z = IntegerRing.Z;
	/**
	 * The field Q of fractions.
	 */
	public static final RationalField Q = RationalField.Q;
	/**
	 * The field R of real numbers. This is a wrapper class for the primitive
	 * data type double.
	 */
	public static final DoubleField R = DoubleField.R;
	/**
	 * The field F2 of integers modulo 2. This is a wrapper class for the
	 * primitive data type boolean
	 */
	public static final F2Field F2 = F2Field.F2;

	/**
	 * The addition a + b of two ring elements a and b. Should be associative.
	 */
	abstract public RingElt add(RingElt a, RingElt b);

	/**
	 * The mutiplicaton a * b of two ring elements a and b. Should be
	 * associative and distributive concerning addition.
	 */
	abstract public RingElt mult(RingElt a, RingElt b);

	/**
	 * Returns the 0 of the ring. The 0 is the unique element for which a + 0 =
	 * 0 + a = a for each element a of the ring.
	 */
	abstract public RingElt zero();

	/**
	 * Returns the additive inverse -a of an ring element a.
	 */
	abstract public RingElt neg(RingElt a);

	/**
	 * Returns true if a == 0.
	 */
	abstract public boolean equalZero(RingElt a);

	/**
	 * Returns the 1 of the ring. This method throws a RingException. Child
	 * rings with one must override this method.
	 */
	public RingElt one() throws RingException {
		throw new RingException("one is not implemented for this ring");
	}

	/**
	 * Returns b^-1. Returns one if b == one() (after mapping b in the ring).
	 * Otherwise throws a RingException.
	 * <P>
	 * Note for implementing a child class of Ring: Must be overridden for
	 * fields. Might be overridden in other child rings if applicable.
	 */
	public RingElt inv(RingElt b) throws RingException {
		if (equal(map(b), one())) {
			return one();
		}
		throw new RingException("inv(" + b + ") is not implemented" + " for the ring " + this);
	}

	/**
	 * Returns a - b. This method is implemented as a + (-b) via the neg()
	 * method.
	 */
	public RingElt sub(RingElt a, RingElt b) {
		return add(a, neg(b));
	}

	/**
	 * True if a == b. This method is implemented as (a - b) == 0 via the sub()
	 * and the equalZero() method.
	 */
	public boolean equal(RingElt a, RingElt b) {
		return (equalZero(sub(a, b)));
	}

	/**
	 * Computes a/b. This method is implemented as a * (b^-1) via the inv() and
	 * the mult() method.
	 */
	public RingElt div(RingElt a, RingElt b) {
		return mult(a, inv(b));
	}

	/**
	 * Computes a/b (true division). An error is thrown. if the division cannot
	 * be performed, i.e. if b divides not a. If the child ring is a field this
	 * method calls the div() method.
	 */
	public RingElt tdiv(RingElt a, RingElt b) {
		if (isField()) {
			return div(a, b);
		} else {
			throw new RingException("tdiv is not implemented for this ring.");
		}
	}

	/**
	 * Returns a div b (euclidian division). If the child ring is a field then
	 * this method calls div(). Otherwise an error is thrown.
	 * <P>
	 * Note for implementing a child class of Ring: Euclidian child rings must
	 * override this method.
	 */
	public RingElt ediv(RingElt a, RingElt b) throws RingException {
		if (isField()) {
			return div(a, b);
		}
		throw new RingException("ediv (euclidian division)" + " is not implemented for this ring");
	}

	/**
	 * Returns a % m (euclidian division, a modulo m). If the child ring is a
	 * field then this method returns zero(). Otherwise an error is thrown.
	 * <P>
	 * Note for implementing a child class of Ring: Euclidian child rings must
	 * override this method.
	 */
	public RingElt mod(RingElt a, RingElt m) throws RingException {
		if (isField()) {
			return zero();
		}
		throw new RingException("mod (modular division" + " is not implemented for this ring");
	}

	/**
	 * Returns gcd(a,b). Returns one() if the ring is a field. For Euclidian
	 * rings the gcd is computed using mod(). Otherwise an error is thrown.
	 */
	public RingElt gcd(RingElt a, RingElt b) throws RingException {
		if (isField()) {
			return one();
		} else if (isEuclidian()) {
			if (equalZero(b)) {
				if (equalZero(a)) {
					throw new RingException("Trying to compute " + " gcd(0,0)");
				} else {
					b = a;
				}
			}
			while (!equalZero(a)) {
				RingElt tmp = a;
				a = mod(b, a);
				b = tmp;
			}
			return b;
		} else {
			throw new RingException("gcd (greatest common divisor" + " is not implemented for this ring");
		}
	}

	/**
	 * Is the ring a field? By default false.
	 * <P>
	 * Note for implementing a child class of Ring: Must be overridden in a
	 * field.
	 */
	public boolean isField() {
		return false;
	}

	/**
	 * Is the ring Euclidian? By default false. True for a field.
	 * <P>
	 * Note for implementing a child class of Ring: Must be overridden in an
	 * Euclidian ring which is not a field.
	 */
	public boolean isEuclidian() {
		if (isField())
			return true;
		return false;
	}

	/**
	 * Is the ring a UFD (unique factorization domain)? By default false. True
	 * if euclidian (or field).
	 * <P>
	 * Note for implementing a child class of Ring: Must be overridden in an UFD
	 * which is not euclidian.
	 */
	public boolean isUFD() {
		if (isEuclidian())
			return true;
		return false;
	}

	/**
	 * Maps a into the Ring. For each ring R with one there is a canonical map Z
	 * -&gt; R, which maps a into 1 + 1 + ... + 1 (a times). Throws an
	 * RingException, if one() is not implemented.
	 */
	public RingElt map(int a) {
		RingElt result = zero();
		RingElt pow2 = one();
		boolean isNegative;
		if (a < 0) {
			isNegative = true;
			a = -a;
		} else {
			isNegative = false;
		}
		while (a != 0) {
			if ((a & 1) != 0) {
				result = add(result, pow2);
			}
			a = a >> 1;
			if (a != 0) {
				pow2 = add(pow2, pow2);
			}
		}
		if (isNegative == false) {
			return result;
		} else {
			return neg(result);
		}

	}

	/**
	 * Maps a into the Ring. For each ring R with one there is a canonical map Z
	 * -&gt; R, which maps a into 1 + 1 + ... + 1 (a times). Throws an
	 * RingException, if one() is not implemented.
	 */
	public RingElt map(BigInteger a) {
		RingElt result = zero();
		RingElt pow2 = one();
		boolean isNegative;
		if (a.signum() == -1) {
			isNegative = true;
			a = a.abs();
		} else {
			isNegative = false;
		}
		int k = a.bitLength();
		for (int i = 0; i < k;) {
			if (a.testBit(i)) {
				result = add(result, pow2);
			}
			i++;
			if (i != k) {
				pow2 = add(pow2, pow2);
			}
		}
		if (isNegative == false) {
			return result;
		} else {
			return neg(result);
		}
	}

	/**
	 * Maps a into the Ring. By default this method maps ring elements in the
	 * ring which are of Z or Q. The first one requires overriding of one(), the
	 * latter in addition overriding of tdiv().
	 * <P>
	 * In addition identical mapping, if the Ring of a is this ring, is
	 * supported.
	 * <P>
	 * Note: For each ring R with one there is a canonical map Z -&gt; R, which
	 * maps a into 1 + 1 + ... + 1 (a times) and a canonical map Q-&gt; R which
	 * maps a = r/s into R if the division is well defined in R for these values
	 * of r and s.
	 * <P>
	 */
	public RingElt map(RingElt a) {
		Ring R = a.getRing();
		if (R == this) {
			return a;
		} else if (a.getRing() instanceof IntegerRing) {
			return map(IntegerRing.toBigInteger(a));
		} else if (a.getRing() instanceof RationalField) {
			BigInteger d = RationalField.denominatorToBigInteger(a);
			BigInteger n = RationalField.numeratorToBigInteger(a);
			return tdiv(map(n), map(d));
		}
		throw new RingException("Cannot map " + a.toString() + " of " + R.toString() + " into " + this.toString());
	}

	/**
	 * Maps a String into the Ring. This is done by first mapping it to Q (via
	 * the map method of RationalField.Q) and then to this Ring.
	 */
	public RingElt map(String str) {
		return map(RationalField.Q.map(str));
	}

	/**
	 * By default, maps a into the Ring using appropriate methods if a is a
	 * RingElt, a BigInteger or a String. Otherwise an error is thrown.
	 */

	public RingElt map(Object a) {

		if (a instanceof RingElt) {
			return map((RingElt) a);
		} else if (a instanceof BigInteger) {
			return map((BigInteger) a);
		} else if (a instanceof String) {
			return map((String) a);
		} else {
			throw new RingException("Cannot map " + a.toString() + " into " + this.toString());
		}
	}

	/**
	 * Returns b^a. Note, if the method one() is not implemented, an error will
	 * be thrown for a == 0. If inv() is not implemented, an error will be
	 * thrown for a < 0.
	 */
	public RingElt pow(RingElt b, int a) {
		RingElt result = null; // Usually: result = one().

		boolean isNegative;
		if (a < 0) {
			isNegative = true;
			a = -a;
		} else {
			isNegative = false;
		}
		while (a != 0) {
			if ((a & 1) != 0) {
				if (result == null) {
					result = b;
				} else {
					result = mult(result, b);
				}
			}
			a = a >> 1;
			if (a != 0) {
				b = mult(b, b);
			}
		}
		if (result == null) {
			return one();
		} else if (isNegative == false) {
			return result;
		} else {
			return inv(result);
		}
	}

	/**
	 * Returns b^a. Note, if the method one() is not overridden, an error will
	 * be thrown for a == 0. If inv() is not overridden, an error will be thrown
	 * for a < 0.
	 */

	public RingElt pow(RingElt b, BigInteger a) {
		RingElt result = null;
		boolean isNegative;
		if (a.signum() == -1) {
			isNegative = true;
			a = a.abs();
		} else {
			isNegative = false;
		}
		int k = a.bitLength();
		for (int i = 0; i < k;) {
			if (a.testBit(i)) {
				if (result == null) {
					result = b;
				} else {
					result = mult(result, b);
				}
			}
			i++;
			if (i != k) {
				b = mult(b, b);
			}
		}
		if (result == null) {
			return one();
		} else if (isNegative == false) {
			return result;
		} else {
			return inv(result);
		}
	}

	/**
	 * Returns the Ring element a as a String. Since version 0.2. If not
	 * overridden returns map(a).toString().
	 */
	public String eltToString(RingElt a) {
		return map(a).toString();
	}

	/**
	 * Evaluates the Polynomial p at b. If p is not a polynomial it is assumed a
	 * constant polynomial and therfore p is mapped to the ring
	 */
	public RingElt evaluatePolynomial(RingElt p, RingElt b) {
		if (p.getRing() instanceof PolynomialRing) {
			return PolynomialRing.evaluate(this, p, b);
		} else {
			return map(p);
		}
	}
}
