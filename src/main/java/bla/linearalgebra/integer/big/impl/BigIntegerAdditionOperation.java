package bla.linearalgebra.integer.big.impl;

import java.math.BigInteger;

import bla.linearalgebra.IInversableOperation;

public class BigIntegerAdditionOperation implements IInversableOperation<BigInteger> {

	@Override
	public BigInteger add(BigInteger left, BigInteger right) {
		return left.add(right);
	}

	@Override
	public BigInteger getNeutralElement() {
		return BigInteger.ZERO;
	}

	@Override
	public BigInteger opposite(BigInteger element) {
		return element.negate();
	}

	@Override
	public BigInteger minus(BigInteger left, BigInteger right) {
		return left.subtract(right);
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
}
