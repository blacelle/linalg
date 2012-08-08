package bla.linearalgebra.polynomial.mul;

import junit.framework.Assert;

import org.junit.Test;

import bla.linearalgebra.IRing;
import bla.linearalgebra.polynomial.IPolynomialFunction;
import bla.linearalgebra.polynomial.PolynomialUtil;
import bla.linearalgebra.polynomial.TestIntegerPolynomialRing;

public class TestIntegerPolynomialMul {
	protected TestIntegerPolynomialRing parentTestClass = new TestIntegerPolynomialRing();

	@Test
	public void testMul() {
		IRing<IPolynomialFunction<Integer>> ring = parentTestClass.makeRing();

		IPolynomialFunction<Integer> left = PolynomialUtil.fromString(ring, "3.2+5.6");
		IPolynomialFunction<Integer> right = PolynomialUtil.fromString(ring, "5.3+11.7");

		Assert.assertEquals(PolynomialUtil.fromString(ring, "15.5+58.9+55.13"), ring.mul(left, right));
	}
}
