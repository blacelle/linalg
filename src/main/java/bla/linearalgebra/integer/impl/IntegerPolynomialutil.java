package bla.linearalgebra.integer.impl;

import bla.linearalgebra.IRing;
import bla.linearalgebra.polynomial.monomial.IMonomialFunction;
import bla.linearalgebra.polynomial.monomial.impl.MonomialFunction;
import bla.linearalgebra.polynomial.monomial.impl.ZeroMonomialFunction;

public class IntegerPolynomialutil {
//	public static final IMonomialFunction<Integer> ZERO = new ZeroMonomialFunction<Integer>(new IntegerRing());
//	public static final IMonomialFunction<Integer> ONE = new MonomialFunction<Integer>(new IntegerRing(), 1, 0);

	public static <T> IMonomialFunction<T> makeZero(IRing<T> coeffRing) {
		return new ZeroMonomialFunction<T>(coeffRing);
	}

	public static <T> IMonomialFunction<T> makeOne(IRing<T> coeffRing) {
		return new MonomialFunction<T>(coeffRing, coeffRing.one(), 0);
	}
}
