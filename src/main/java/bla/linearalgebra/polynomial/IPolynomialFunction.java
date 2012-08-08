package bla.linearalgebra.polynomial;

import java.util.Map;

import bla.linearalgebra.IFunction;
import bla.linearalgebra.IRing;
import bla.linearalgebra.polynomial.monomial.IMonomialFunction;

public interface IPolynomialFunction<T> extends IFunction<T> {
	IRing<T> getCoeffiRing();

	T getCoedd(int power);

	void setCoeff(int power, T coeff);

	/**
	 * If this is zero, return -1
	 */
	int getMaxPower();

	/**
	 * If this is zero, it should return an empty map
	 */
	Map<Integer, IMonomialFunction<T>> getMonomials();

	/**
	 * Multiply this by X^i
	 * 
	 * @param i
	 *            the shift to apply
	 */
	IPolynomialFunction<T> shift(int i);

	void accept(IPolynomialVisitor iPolynomialVisitor);
}
