package bla.linearalgebra.integer.impl;

import bla.linearalgebra.IInversableOperation;

public class IntegerAdditionOperation implements IInversableOperation<Integer> {

	@Override
	public Integer add(Integer left, Integer right) {
		return left + right;
	}

	@Override
	public Integer getNeutralElement() {
		return 0;
	}

	@Override
	public Integer opposite(Integer element) {
		return -element;
	}

	@Override
	public Integer minus(Integer left, Integer right) {
		return left - right;
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
