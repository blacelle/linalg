package bla.linearalgebra.integer.impl;

import bla.linearalgebra.IRing;
import bla.linearalgebra.impl.GenericRing;

public class IntegerRing extends GenericRing<Integer> implements IRing<Integer> {

	public IntegerRing() {
		super(new IntegerAdditionOperation(), new IntegerMultiplicationOperation());
	}

	@Override
	public Integer findNthPrimitiveRootOfUnity(int n) {
		// In p = 2^27+1, 31 is the least primitive element is 31
		return 2013265921;
	}
}
