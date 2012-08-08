package bla.linearalgebra.polynomial.impl;

import bla.linearalgebra.IRing;
import bla.linearalgebra.polynomial.IPolynomialFunction;
import bla.linearalgebra.polynomial.IPolynomialVisitor;
import bla.linearalgebra.polynomial.PolynomialUtil;

public abstract class APolynomialFunction<T> implements IPolynomialFunction<T> {

	protected final IRing<T> coeffRing;

	public APolynomialFunction(IRing<T> coeffRing) {
		this.coeffRing = coeffRing;
	}

	@Override
	public IRing<T> getCoeffiRing() {
		return coeffRing;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coeffRing == null) ? 0 : coeffRing.hashCode());

		for (int i = 0; i < getMaxPower(); i++) {
			T coeff = getCoedd(i);
			result = prime * result + (coeff == null ? 0 : coeff.hashCode());
		}

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		// if (getClass() != obj.getClass())
		// return false;
		APolynomialFunction<?> other = (APolynomialFunction<?>) obj;
		if (coeffRing == null) {
			if (other.coeffRing != null)
				return false;
		} else if (!coeffRing.equals(other.coeffRing))
			return false;

		for (int i = 0; i < getMaxPower(); i++) {
			T coeff = getCoedd(i);

			if (coeff == null) {
				if (other.getCoedd(i) != null)
					return false;
			} else if (!coeff.equals(other.getCoedd(i)))
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return PolynomialUtil.toString(this);
	}

	@Override
	public void accept(IPolynomialVisitor iPolynomialVisitor) {
		for (Integer monomialDegree : getMonomials().keySet()) {
			iPolynomialVisitor.visitCoeff(monomialDegree);
		}
	}
}
