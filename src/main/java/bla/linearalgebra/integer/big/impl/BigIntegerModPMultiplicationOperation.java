package bla.linearalgebra.integer.big.impl;

import java.math.BigInteger;

public class BigIntegerModPMultiplicationOperation extends BigIntegerMultiplicationOperation {

	protected BigInteger modP;

	public BigIntegerModPMultiplicationOperation(BigInteger modP) {
		this.modP = modP;
	}

	public BigIntegerModPMultiplicationOperation(long modP) {
		this.modP = BigInteger.valueOf(modP);
	}

	@Override
	public BigInteger opposite(BigInteger element) {
		return element.modInverse(modP);
	}

	@Override
	public BigInteger minus(BigInteger left, BigInteger right) {
		return left.divide(right);
	}
}
