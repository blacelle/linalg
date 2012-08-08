package bla.linearalgebra.integer.impl;

import bla.linearalgebra.IOperation;

public class IntegerMultiplicationOperation implements IOperation<Integer> {

	@Override
	public Integer add(Integer left, Integer right) {
		return left * right;
	}

	@Override
	public Integer getNeutralElement() {
		return 1;
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
	public Integer makeFromint(int i) {
		return i;
	}
}
