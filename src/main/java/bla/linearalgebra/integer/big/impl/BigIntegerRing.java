package bla.linearalgebra.integer.big.impl;

import java.math.BigInteger;

import bla.linearalgebra.IRing;
import bla.linearalgebra.IRingReducer;
import bla.linearalgebra.impl.GenericRing;

public class BigIntegerRing extends GenericRing<BigInteger> implements IRingReducer<BigInteger> {
	protected IRingReducer<BigInteger> ringReducer;

	public BigIntegerRing() {
		super(new BigIntegerAdditionOperation(), new BigIntegerMultiplicationOperation());
	}

	@SuppressWarnings("unchecked")
	public BigIntegerRing(long modP) {
		super(new BigIntegerAdditionOperation(), new BigIntegerModPMultiplicationOperation(modP));
		ringReducer = (IRingReducer<BigInteger>) multiplication;
	}

	@Override
	public boolean equals(BigInteger left, BigInteger right) {
		return super.equals(ringReducer.reduce(left.subtract(right)), zero());
	}

	@Override
	public BigInteger reduce(BigInteger element) {
		if (ringReducer == null) {
			return element;
		} else {
			return ringReducer.reduce(element);
		}
	}

	@Override
	public BigInteger findNthPrimitiveRootOfUnity(IRing<BigInteger> ring, int n) {
		if (ringReducer == null) {
			return null;
		} else {
			return ringReducer.findNthPrimitiveRootOfUnity(ring, n);
		}
	}
}
