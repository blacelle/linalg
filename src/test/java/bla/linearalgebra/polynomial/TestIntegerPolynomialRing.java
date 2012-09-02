package bla.linearalgebra.polynomial;

import java.math.BigInteger;

import bla.linearalgebra.IRing;
import bla.linearalgebra.integer.big.impl.BigIntegerRing;
import bla.linearalgebra.polynomial.impl.PolynomialRing;

public class TestIntegerPolynomialRing extends ATestIntegerPolynomialRing<BigInteger> {

	@Override
	public IRing<IPolynomialFunction<BigInteger>> makeRing() {
		return new PolynomialRing<BigInteger>(makeCoeffRing());
	}

	@Override
	public IRing<BigInteger> makeCoeffRing() {
		return new BigIntegerRing();
	}
}
