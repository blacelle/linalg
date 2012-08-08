package bla.linearalgebra.polynomial.string;

import junit.framework.Assert;

import org.junit.Test;

import bla.linearalgebra.polynomial.IPolynomialFunction;
import bla.linearalgebra.polynomial.PolynomialUtil;
import bla.linearalgebra.polynomial.TestIntegerPolynomialRing;
import bla.linearalgebra.polynomial.monomial.impl.MonomialFunction;

public class TestIntegerPolynomialToString {
	protected TestIntegerPolynomialRing parentTestClass = new TestIntegerPolynomialRing();

	@Test
	public void testToString() {
		IPolynomialFunction<Integer> threePowerTwo = new MonomialFunction<Integer>(parentTestClass.makeCoeffRing(), 3, 2);
		IPolynomialFunction<Integer> fivePowerThree = new MonomialFunction<Integer>(parentTestClass.makeCoeffRing(), 5, 3);

		Assert.assertEquals("3.2", threePowerTwo.toString());
		Assert.assertEquals("3.2+5.3", parentTestClass.makeRing().add(threePowerTwo, fivePowerThree).toString());
		Assert.assertEquals("3.2+5.3", parentTestClass.makeRing().add(fivePowerThree, threePowerTwo).toString());
	}

	@Test
	public void testFromString() {
		IPolynomialFunction<Integer> threePowerTwo = new MonomialFunction<Integer>(parentTestClass.makeCoeffRing(), 3, 2);
		IPolynomialFunction<Integer> fivePowerThree = new MonomialFunction<Integer>(parentTestClass.makeCoeffRing(), 5, 3);

		Assert.assertEquals(threePowerTwo, PolynomialUtil.fromString(parentTestClass.makeRing(), "3.2"));
		Assert.assertEquals(parentTestClass.makeRing().add(fivePowerThree, threePowerTwo), PolynomialUtil.fromString(parentTestClass.makeRing(), "3.2+5.3"));
	}

	@Test
	public void testToStringZero() {
		Assert.assertEquals("0", parentTestClass.makeRing().zero().toString());
	}

	@Test
	public void testFromStringZero() {
		Assert.assertEquals(parentTestClass.makeRing().zero(), PolynomialUtil.fromString(parentTestClass.makeRing(), "0"));
	}
}
