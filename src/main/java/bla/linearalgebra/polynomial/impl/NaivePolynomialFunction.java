package bla.linearalgebra.polynomial.impl;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import bla.linearalgebra.IRing;
import bla.linearalgebra.polynomial.IPolynomialFunction;
import bla.linearalgebra.polynomial.IPolynomialVisitor;
import bla.linearalgebra.polynomial.monomial.IMonomialFunction;
import bla.linearalgebra.polynomial.monomial.impl.MonomialFunction;

public class NaivePolynomialFunction<T> extends APolynomialFunction<T> implements IPolynomialFunction<T> {
	protected final NavigableMap<Integer, IMonomialFunction<T>> coefficients = new TreeMap<Integer, IMonomialFunction<T>>();

	public NaivePolynomialFunction(IRing<T> coeffRing) {
		super(coeffRing);
	}

	@Override
	public T evaluate(T element) {
		T output = coeffRing.zero();

		for (IMonomialFunction<T> monome : getMonomials().values()) {
			coeffRing.add(output, monome.evaluate(element));
		}

		return output;
	}

	@Override
	public IRing<T> getCoeffiRing() {
		return coeffRing;
	}

	@Override
	public T getCoedd(int power) {
		IMonomialFunction<T> monomial = coefficients.get(power);
		if (monomial == null) {
			return coeffRing.zero();
		} else {
			return monomial.getCoeff();
		}
	}

	@Override
	public int getMaxPower() {
		if (coefficients.isEmpty()) {
			return -1;
		} else {
			return coefficients.lastKey();
		}
	}

	@Override
	public void setCoeff(int power, T coeff) {
		// TODO check zeroes
		coefficients.put(power, new MonomialFunction<T>(getCoeffiRing(), coeff, power));
	}

	@Override
	public Map<Integer, IMonomialFunction<T>> getMonomials() {
		return coefficients;
	}

	@Override
	public IPolynomialFunction<T> shift(final int shift) {
		final IPolynomialFunction<T> output = new NaivePolynomialFunction<T>(getCoeffiRing());

		this.accept(new IPolynomialVisitor() {

			@Override
			public boolean visitCoeff(int degree) {
				if (degree + shift >= 0) {
					output.setCoeff(degree + shift, getCoedd(degree));
				}

				return true;
			}
		});

		return output;
	}
}
