package bla.linearalgebra.polynomial;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

import bla.linearalgebra.polynomial.monomial.impl.ZeroMonomialFunction;

public class TestZeroMonomial {
	protected TestIntegerPolynomialRing parentTestClass = new TestIntegerPolynomialRing();

	public IPolynomialFunction<?> makeZero() {
		return new ZeroMonomialFunction<BigInteger>(parentTestClass.makeCoeffRing());
	}

	@Test
	public void testEdgeCases() {
		IPolynomialFunction<?> zero = makeZero();

		assertEquals(-1, zero.getMaxPower());
		assertTrue(zero.getMonomials().isEmpty());
	}
}
