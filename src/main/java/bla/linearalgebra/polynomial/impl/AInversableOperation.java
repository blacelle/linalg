package bla.linearalgebra.polynomial.impl;

import bla.linearalgebra.IInversableOperation;

public abstract class AInversableOperation<T> implements IInversableOperation<T> {

	@Override
	public T minus(T left, T right) {
		return add(left, opposite(right));
	}
}
