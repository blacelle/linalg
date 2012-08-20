package bla.linearalgebra.polynomial.impl;

import bla.linearalgebra.IOperation;
import bla.linearalgebra.IRing;
import bla.linearalgebra.polynomial.IPolynomialFunction;
import bla.linearalgebra.polynomial.monomial.impl.MonomialFunction;

public class PolynomialMultiplication_FFT<T> implements IOperation<IPolynomialFunction<T>> {
	protected final IPolynomialFunction<T> neutralElement;

	public PolynomialMultiplication_FFT(IPolynomialFunction<T> neutralElement) {
		this.neutralElement = neutralElement;
	}

	@Override
	public IPolynomialFunction<T> add(IPolynomialFunction<T> left, IPolynomialFunction<T> right) {
		IRing<T> ring = left.getCoeffiRing();

		if (!ring.equals(right.getCoeffiRing())) {
			throw new RuntimeException("Incompatible CoeffRings");
		}

		NaivePolynomialFunction<T> output = new NaivePolynomialFunction<T>(ring);
		

//		a = algo.fft(ring, a, omega);
		// Calcul de la FFT inverse
//		System.out.println(toString(ring, a));
//		a = algo.fft(ring, a, omegainv);

		int maxPower = left.getMaxPower() + right.getMaxPower();

		// TODO: consider only the relevant coeffs
		for (int i = 0; i <= maxPower; i++) {
			T coeff = ring.zero();

			for (int j = 0; j <= i; j++) {
				T leftCoeff = left.getCoedd(j);
				T rightCoeff = right.getCoedd(i - j);

				if (leftCoeff != null && rightCoeff != null)
					coeff = ring.add(coeff, ring.mul(leftCoeff, rightCoeff));
				else {
					// do nothing
				}
			}

			output.setCoeff(i, coeff);
		}

		return output;
	}

	@Override
	public IPolynomialFunction<T> getNeutralElement() {
		return neutralElement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((neutralElement == null) ? 0 : neutralElement.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PolynomialMultiplication_FFT<?> other = (PolynomialMultiplication_FFT<?>) obj;
		if (neutralElement == null) {
			if (other.neutralElement != null)
				return false;
		} else if (!neutralElement.equals(other.neutralElement))
			return false;
		return true;
	}

	@Override
	public IPolynomialFunction<T> makeFromint(int i) {
		return new MonomialFunction<T>(neutralElement.getCoeffiRing(), neutralElement.getCoeffiRing().makeFromInt(i), 0);
	}

}
