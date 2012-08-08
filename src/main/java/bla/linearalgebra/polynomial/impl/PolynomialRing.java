package bla.linearalgebra.polynomial.impl;

import bla.linearalgebra.impl.GenericRing;
import bla.linearalgebra.polynomial.IPolynomialFunction;

public class PolynomialRing<T> extends GenericRing<IPolynomialFunction<T>> {

	public PolynomialRing(IPolynomialFunction<T> additionNeutral, IPolynomialFunction<T> multiplicationNeutral) {
		super(new PolynomialAdditionOperation<T>(additionNeutral), new PolynomialMultiplicationOperation<T>(multiplicationNeutral));
	}

}
