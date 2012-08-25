package bla.linearalgebra.integer.big.impl;

import java.math.BigInteger;

import bla.linearalgebra.IRing;
import bla.linearalgebra.impl.GenericRing;

public class BigIntegerRing extends GenericRing<BigInteger> implements IRing<BigInteger> {

	public BigIntegerRing() {
		super(new BigIntegerAdditionOperation(), new BigIntegerMultiplicationOperation());
	}
	public BigIntegerRing(long modP) {
		super(new BigIntegerAdditionOperation(), new BigIntegerModPMultiplicationOperation(modP));
	}

}
