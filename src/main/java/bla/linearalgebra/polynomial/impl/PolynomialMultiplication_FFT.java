package bla.linearalgebra.polynomial.impl;

import java.util.List;

import bla.linearalgebra.IOperation;
import bla.linearalgebra.IRing;
import bla.linearalgebra.algorithm.fft.FFT;
import bla.linearalgebra.polynomial.IPolynomialFunction;
import bla.linearalgebra.polynomial.PolynomialUtil;
import bla.linearalgebra.polynomial.monomial.impl.MonomialFunction;

public class PolynomialMultiplication_FFT<T> implements IOperation<IPolynomialFunction<T>> {
	protected final IPolynomialFunction<T> neutralElement;

	public PolynomialMultiplication_FFT(IRing<T> coeffRing) {
		this.neutralElement = new MonomialFunction<T>(coeffRing, coeffRing.one(), 0);
	}

	@Override
	public IPolynomialFunction<T> add(IPolynomialFunction<T> left, IPolynomialFunction<T> right) {
		IRing<T> ring = left.getCoeffiRing();

		if (!ring.equals(right.getCoeffiRing())) {
			throw new RuntimeException("Incompatible CoeffRings");
		}

		int maxPower = left.getMaxPower() + right.getMaxPower();

		T omega = ring.findNthPrimitiveRootOfUnity(maxPower);
		if (omega == null) {
			throw new RuntimeException("Can not find a primitive nth root for " + maxPower + " in " + ring);
		}

		T omegaInv = ring.inv(omega);
		if (omegaInv == null) {
			throw new RuntimeException("Can not find the inverse of the primitive nth root " + omega);
		}

		List<T> a = PolynomialUtil.toList(left);
		while (a.size() <= maxPower) {
			a.add(ring.zero());
		}
		List<T> b = PolynomialUtil.toList(right);
		while (b.size() <= maxPower) {
			b.add(ring.zero());
		}

		a = new FFT().fft(ring, a, omega);
		b = new FFT().fft(ring, b, omega);

		for (int i = 0; i <= maxPower; i++) {
			a.set(i, ring.add(a.get(i), b.get(i)));
		}

		// Calcul de la FFT inverse
		a = new FFT().fft(ring, a, omegaInv);

		NaivePolynomialFunction<T> output = new NaivePolynomialFunction<T>(ring);
		for (int i = 0; i <= maxPower; i++) {
			output.setCoeff(i, a.get(i));
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
