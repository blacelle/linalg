package bla.linearalgebra.impl;

import bla.linearalgebra.IInversableOperation;
import bla.linearalgebra.IOperation;
import bla.linearalgebra.IRing;

public class GenericRing<T> implements IRing<T> {

	protected IInversableOperation<T> addition;
	protected IOperation<T> multiplication;

	public GenericRing(IInversableOperation<T> addition, IOperation<T> multiplication) {
		this.addition = addition;
		this.multiplication = multiplication;
	}

	@Override
	public T zero() {
		return addition.getNeutralElement();
	}

	@Override
	public T one() {
		return multiplication.getNeutralElement();
	}

	@Override
	public T neg(T element) {
		return addition.opposite(element);
	}

	@Override
	public T add(T left, T right) {
		return addition.add(left, right);
	}

	@Override
	public T sub(T left, T right) {
		return addition.minus(left, right);
	}

	@Override
	public T mul(T left, T right) {
		return multiplication.add(left, right);
	}

	@Override
	public T inv(T element) {
		if (multiplication instanceof IInversableOperation<?>) {
			return ((IInversableOperation<T>) multiplication).opposite(element);
		} else {
			return null;
		}
	}

	@Override
	public T div(T left, T right) {
		if (multiplication instanceof IInversableOperation<?>) {
			return ((IInversableOperation<T>) multiplication).minus(left, right);
		} else {
			return null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addition == null) ? 0 : addition.hashCode());
		result = prime * result + ((multiplication == null) ? 0 : multiplication.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericRing<?> other = (GenericRing<?>) obj;
		if (addition == null) {
			if (other.addition != null)
				return false;
		} else if (!addition.equals(other.addition))
			return false;
		if (multiplication == null) {
			if (other.multiplication != null)
				return false;
		} else if (!multiplication.equals(other.multiplication))
			return false;
		return true;
	}

	@Override
	public T makeFromInt(int i) {
		return addition.makeFromint(i);
	}

	// @Override
	// public T mulByFactor(T element, int factor) {
	// if (element instanceof Integer) {
	// return ((Integer) element) * factor;
	// } else {
	// throw new RuntimeException("Can not mulByFactor a " +
	// element.getClass());
	// }
	// }

}
