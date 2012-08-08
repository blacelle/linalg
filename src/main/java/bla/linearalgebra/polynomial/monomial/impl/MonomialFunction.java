package bla.linearalgebra.polynomial.monomial.impl;

import java.util.Collections;
import java.util.Map;

import bla.linearalgebra.IReducedRing;
import bla.linearalgebra.IRing;
import bla.linearalgebra.impl.RingUtil;
import bla.linearalgebra.polynomial.IPolynomialFunction;
import bla.linearalgebra.polynomial.impl.APolynomialFunction;
import bla.linearalgebra.polynomial.monomial.IMonomialFunction;

public class MonomialFunction<T> extends APolynomialFunction<T> implements IMonomialFunction<T> {
	protected final T coeff;
	protected final int power;

	public MonomialFunction(IRing<T> coeffRing, T coeff, int power) {
		super(coeffRing);

		this.coeff = (coeffRing instanceof IReducedRing<?>) ? ((IReducedRing<T>) coeffRing).reduce(coeff) : coeff;
		this.power = power;
	}

	@Override
	public T evaluate(T element) {
		// 1
		T output = RingUtil.power(getCoeffiRing(), element, power);

		// coeff * element^power
		return coeffRing.mul(coeff, output);
	}

	@Override
	public T getCoedd(int power) {
		if (power == this.power) {
			return coeff;
		} else {
			return coeffRing.zero();
		}
	}

	@Override
	public T getCoeff() {
		return coeff;
	}

	@Override
	public int getMaxPower() {
		return power;
	}

	@Override
	public void setCoeff(int power, T coeff) {
		// if (power == this.power) {
		// this.coeff = coeff;
		// } else {
		// throw new RuntimeException("Incompatbile power");
		// }
		throw new RuntimeException("Monomial can not be mutated");
	}

	@Override
	public Map<Integer, IMonomialFunction<T>> getMonomials() {
		return Collections.<Integer, IMonomialFunction<T>> singletonMap(power, this);
	}

	@Override
	public IPolynomialFunction<T> shift(int shift) {
		if (power + shift >= 0) {
			return new MonomialFunction<T>(coeffRing, coeff, power + shift);
		} else {
			return new ZeroMonomialFunction<T>(getCoeffiRing());
		}
	}
}
