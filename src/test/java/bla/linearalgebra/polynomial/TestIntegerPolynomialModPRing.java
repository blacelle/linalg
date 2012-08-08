package bla.linearalgebra.polynomial;

import org.junit.Assert;
import org.junit.Test;

import bla.linearalgebra.IRing;
import bla.linearalgebra.IRingReducer;
import bla.linearalgebra.integer.impl.IntegerModPRing;
import bla.linearalgebra.integer.reducer.impl.ModPIntegerRingReducer;
import bla.linearalgebra.polynomial.monomial.IMonomialFunction;

public class TestIntegerPolynomialModPRing extends TestIntegerPolynomialRing {

	public IRing<Integer> makeCoeffRing() {
		return new IntegerModPRing<Integer>(super.makeCoeffRing(), makeReducer());
	}

	public IRingReducer<Integer> makeReducer() {
		return new ModPIntegerRingReducer(7);
	}

	@Test
	public void testCoefficientsReducing() {

		for (IPolynomialFunction<Integer> i : getIteratable()) {
			for (IMonomialFunction<Integer> monomial : i.getMonomials().values()) {
				Assert.assertEquals(makeReducer().reduce(monomial.getCoeff()), monomial.getCoeff());
			}
		}
	}
}
