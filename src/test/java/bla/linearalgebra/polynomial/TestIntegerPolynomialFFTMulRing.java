package bla.linearalgebra.polynomial;

import java.math.BigInteger;

import bla.linearalgebra.IRing;
import bla.linearalgebra.polynomial.impl.PolynomialAdditionOperation;
import bla.linearalgebra.polynomial.impl.PolynomialMultiplication_FFT;
import bla.linearalgebra.polynomial.impl.PolynomialRing;

public class TestIntegerPolynomialFFTMulRing extends TestIntegerPolynomialModPRing {

	@Override
	public IRing<IPolynomialFunction<BigInteger>> makeRing() {
		return new PolynomialRing<BigInteger>(new PolynomialAdditionOperation<BigInteger>(makeCoeffRing()), new PolynomialMultiplication_FFT<BigInteger>(
				makeCoeffRing()));
	}
}
