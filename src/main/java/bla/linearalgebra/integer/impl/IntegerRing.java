package bla.linearalgebra.integer.impl;

import bla.linearalgebra.IRing;
import bla.linearalgebra.impl.GenericRing;

public class IntegerRing extends GenericRing<Integer> implements IRing<Integer> {

	public IntegerRing() {
		super(new IntegerAdditionOperation(), new IntegerMultiplicationOperation());
	}
	
	
}
