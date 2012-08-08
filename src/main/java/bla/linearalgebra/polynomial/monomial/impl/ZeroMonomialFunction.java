package bla.linearalgebra.polynomial.monomial.impl;

import java.util.Collections;
import java.util.Map;

import bla.linearalgebra.IRing;
import bla.linearalgebra.polynomial.IPolynomialFunction;
import bla.linearalgebra.polynomial.impl.APolynomialFunction;
import bla.linearalgebra.polynomial.monomial.IMonomialFunction;

public class ZeroMonomialFunction<T> extends APolynomialFunction<T> implements IMonomialFunction<T> {

	public ZeroMonomialFunction(IRing<T> coeffRing) {
		super(coeffRing);
	}

	@Override
	public T getCoedd(int power) {
		return coeffRing.zero();
	}

	@Override
	public void setCoeff(int power, T coeff) {
		throw new RuntimeException("Can not mutate zero");
	}

	@Override
	public int getMaxPower() {
		return -1;
	}

	@Override
	public T evaluate(T element) {
		return coeffRing.zero();
	}

	@Override
	public T getCoeff() {
		return coeffRing.zero();
	}

	@Override
	public Map<Integer, IMonomialFunction<T>> getMonomials() {
		return Collections.emptyMap();
	}

	@Override
	public IPolynomialFunction<T> shift(int i) {
		return this;
	}
}
