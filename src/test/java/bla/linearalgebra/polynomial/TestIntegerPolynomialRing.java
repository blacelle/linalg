package bla.linearalgebra.polynomial;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import bla.linearalgebra.ATestRing;
import bla.linearalgebra.IRing;
import bla.linearalgebra.integer.impl.IntegerPolynomialutil;
import bla.linearalgebra.integer.impl.IntegerRing;
import bla.linearalgebra.polynomial.impl.PolynomialRing;
import bla.linearalgebra.polynomial.monomial.impl.MonomialFunction;

public class TestIntegerPolynomialRing extends ATestRing<IPolynomialFunction<Integer>> {

	@Override
	public IRing<IPolynomialFunction<Integer>> makeRing() {
		return new PolynomialRing<Integer>(IntegerPolynomialutil.makeZero(makeCoeffRing()), IntegerPolynomialutil.makeOne(makeCoeffRing()));
	}

	// @Override
	public IRing<Integer> makeCoeffRing() {
		return new IntegerRing();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<IPolynomialFunction<Integer>> getIteratable() {
		IPolynomialFunction<Integer> first = new MonomialFunction<Integer>(makeCoeffRing(), 3, 2);
		IPolynomialFunction<Integer> second = new MonomialFunction<Integer>(makeCoeffRing(), 7, 5);
		IPolynomialFunction<Integer> third = new MonomialFunction<Integer>(makeCoeffRing(), 13, 11);

		return Arrays.asList(first, second, third);
	}

	@Test
	public void testZeroValue() {
		Assert.assertEquals(IntegerPolynomialutil.makeZero(makeCoeffRing()), makeRing().zero());
	}

	@Test
	public void testOneValue() {
		Assert.assertEquals(IntegerPolynomialutil.makeOne(makeCoeffRing()), makeRing().one());
	}
}
