package bla.linearalgebra.polynomial.impl;

import bla.linearalgebra.IInversableOperation;
import bla.linearalgebra.IOperation;
import bla.linearalgebra.IRing;
import bla.linearalgebra.impl.GenericRing;
import bla.linearalgebra.polynomial.IPolynomialFunction;

public class PolynomialRing<T> extends GenericRing<IPolynomialFunction<T>> {

	public PolynomialRing(IRing<T> coeffRing) {
		super(new PolynomialAdditionOperation<T>(coeffRing), new PolynomialMultiplicationOperation<T>(coeffRing));
	}

	public PolynomialRing(IInversableOperation<IPolynomialFunction<T>> addition, IOperation<IPolynomialFunction<T>> multiplication) {
		super(addition, multiplication);
	}

}
