package bla.linearalgebra.polynomial;

import bla.linearalgebra.IRing;
import bla.linearalgebra.polynomial.impl.PolynomialAdditionOperation;
import bla.linearalgebra.polynomial.impl.PolynomialMultiplication_FFT;
import bla.linearalgebra.polynomial.impl.PolynomialRing;

public class TestIntegerPolynomialFFTMulRing extends TestIntegerPolynomialModPRing {

	@Override
	public IRing<IPolynomialFunction<Integer>> makeRing() {
		return new PolynomialRing<Integer>(new PolynomialAdditionOperation<Integer>(makeCoeffRing()),
				new PolynomialMultiplication_FFT<Integer>(makeCoeffRing()));
	}
}
