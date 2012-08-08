package bla.linearalgebra.polynomial.impl;

import bla.linearalgebra.IInversableOperation;
import bla.linearalgebra.polynomial.IPolynomialFunction;
import bla.linearalgebra.polynomial.monomial.impl.MonomialFunction;

public class PolynomialAdditionOperation<T> extends AInversableOperation<IPolynomialFunction<T>> implements IInversableOperation<IPolynomialFunction<T>> {
	protected final IPolynomialFunction<T> neutralElement;

	public PolynomialAdditionOperation(IPolynomialFunction<T> neutralElement) {
		this.neutralElement = neutralElement;
	}

	@Override
	public IPolynomialFunction<T> add(IPolynomialFunction<T> left, IPolynomialFunction<T> right) {
		if (!left.getCoeffiRing().equals(right.getCoeffiRing())) {
			throw new RuntimeException("Incompatible CoeffRings");
		}

		NaivePolynomialFunction<T> output = new NaivePolynomialFunction<T>(left.getCoeffiRing());

		int maxPower = Math.max(left.getMaxPower(), right.getMaxPower());

		// TODO: consider only the relevant coeffs
		for (int i = 0; i <= maxPower; i++) {
			T leftCoeff = left.getCoedd(i);
			T rightCoeff = right.getCoedd(i);

			if (leftCoeff != null && rightCoeff != null)
				output.setCoeff(i, left.getCoeffiRing().add(leftCoeff, rightCoeff));
			else if (leftCoeff != null)
				output.setCoeff(i, leftCoeff);
			else if (rightCoeff != null)
				output.setCoeff(i, rightCoeff);
			else {
				// do nothing
			}
		}

		return output;
	}

	@Override
	public IPolynomialFunction<T> getNeutralElement() {
		return neutralElement;
	}

	@Override
	public IPolynomialFunction<T> opposite(IPolynomialFunction<T> element) {
		NaivePolynomialFunction<T> output = new NaivePolynomialFunction<T>(element.getCoeffiRing());

		int maxPower = element.getMaxPower();

		for (int i = 0; i < maxPower; i++) {
			output.setCoeff(i, element.getCoeffiRing().neg(element.getCoedd(i)));
		}

		return output;
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
		PolynomialAdditionOperation<?> other = (PolynomialAdditionOperation<?>) obj;
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
