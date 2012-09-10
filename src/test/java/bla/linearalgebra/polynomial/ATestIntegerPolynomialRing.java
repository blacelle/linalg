package bla.linearalgebra.polynomial;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import bla.linearalgebra.IRing;
import bla.linearalgebra.integer.impl.IntegerPolynomialutil;
import bla.linearalgebra.polynomial.monomial.impl.MonomialFunction;
import bla.linearalgebra.ring.ATestRing;

public abstract class ATestIntegerPolynomialRing<T> extends ATestRing<IPolynomialFunction<T>> {

	public abstract IRing<T> makeCoeffRing();

	@Test
	public void testZeroValue() {
		Assert.assertEquals(IntegerPolynomialutil.makeZero(makeCoeffRing()), makeRing().zero());
	}

	@Test
	public void testOneValue() {
		Assert.assertEquals(IntegerPolynomialutil.makeOne(makeCoeffRing()), makeRing().one());
	}

	@Test
	public void testMul() {
		IPolynomialFunction<T> left = PolynomialUtil.fromString(makeRing(), "3.2+5.6");
		IPolynomialFunction<T> right = PolynomialUtil.fromString(makeRing(), "5.3+11.7");

		Assert.assertEquals(PolynomialUtil.fromString(makeRing(), "15.5+58.9+55.13"), makeRing().mul(left, right));
	}

	@Override
	public Iterable<IPolynomialFunction<T>> getIteratable() {
		IPolynomialFunction<T> first = new MonomialFunction<T>(makeCoeffRing(), makeCoeffRing().makeFromInt(3), 2);
		IPolynomialFunction<T> second = new MonomialFunction<T>(makeCoeffRing(), makeCoeffRing().makeFromInt(7), 5);
		IPolynomialFunction<T> third = new MonomialFunction<T>(makeCoeffRing(), makeCoeffRing().makeFromInt(13), 11);

		return Arrays.asList(first, second, third);
	}
}
