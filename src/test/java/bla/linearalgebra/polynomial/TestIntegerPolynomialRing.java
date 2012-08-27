package bla.linearalgebra.polynomial;

import bla.linearalgebra.IRing;
import bla.linearalgebra.integer.impl.IntegerRing;
import bla.linearalgebra.polynomial.impl.PolynomialRing;

public class TestIntegerPolynomialRing extends ATestIntegerPolynomialRing<Integer> {

	@Override
	public IRing<IPolynomialFunction<Integer>> makeRing() {
		return new PolynomialRing<Integer>(makeCoeffRing());
	}

	@Override
	public IRing<Integer> makeCoeffRing() {
		return new IntegerRing();
	}
}
