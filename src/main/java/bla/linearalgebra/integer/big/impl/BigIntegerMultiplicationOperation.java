package bla.linearalgebra.integer.big.impl;

import java.math.BigInteger;

import bla.linearalgebra.IInversableOperation;

public class BigIntegerMultiplicationOperation implements IInversableOperation<BigInteger> {

	@Override
	public BigInteger add(BigInteger left, BigInteger right) {
		return left.multiply(right);
	}

	@Override
	public BigInteger getNeutralElement() {
		return BigInteger.ONE;
	}

	@Override
	public int hashCode() {
		return this.getClass().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		return getClass().equals(obj.getClass());
	}

	@Override
	public BigInteger makeFromint(int i) {
		return BigInteger.valueOf(i);
	}

	@Override
	public BigInteger opposite(BigInteger element) {
		if (getNeutralElement().equals(element)) {
			return element;
		} else {
			return null;
		}
	}

	@Override
	public BigInteger minus(BigInteger left, BigInteger right) {
		return left.divide(right);
	}
}
