package bla.linearalgebra.polynomial;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

import bla.linearalgebra.IRing;
import bla.linearalgebra.IRingReducer;
import bla.linearalgebra.integer.impl.IntegerModPRing;
import bla.linearalgebra.integer.reducer.impl.ModPBigIntegerRingReducer;
import bla.linearalgebra.polynomial.monomial.IMonomialFunction;

public class TestIntegerPolynomialModPRing extends TestIntegerPolynomialRing {

	public IRing<BigInteger> makeCoeffRing() {
		return new IntegerModPRing<BigInteger>(super.makeCoeffRing(), makeReducer());
	}

	public IRingReducer<BigInteger> makeReducer() {
		return new ModPBigIntegerRingReducer(7);
	}

	@Test
	public void testCoefficientsReducing() {

		for (IPolynomialFunction<BigInteger> i : getIteratable()) {
			for (IMonomialFunction<BigInteger> monomial : i.getMonomials().values()) {
				Assert.assertEquals(makeReducer().reduce(monomial.getCoeff()), monomial.getCoeff());
			}
		}
	}
}
