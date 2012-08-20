package bla.linearalgebra.integer.impl;

import bla.linearalgebra.IReducedRing;
import bla.linearalgebra.IRing;
import bla.linearalgebra.IRingReducer;

public class IntegerModPRing<T> implements IReducedRing<T> {
	protected IRing<T> underlyingRing;

	protected IRingReducer<T> reducer;

	public IntegerModPRing(IRing<T> underlyingRing, IRingReducer<T> reducer) {
		this.underlyingRing = underlyingRing;
		this.reducer = reducer;
	}

	@Override
	public T zero() {
		return reduce(underlyingRing.zero());
	}

	@Override
	public T one() {
		return reduce(underlyingRing.one());
	}

	@Override
	public T neg(T element) {
		return reduce(underlyingRing.neg(element));
	}

	@Override
	public T add(T left, T right) {
		return reduce(underlyingRing.add(left, right));
	}

	@Override
	public T sub(T left, T right) {
		return reduce(underlyingRing.sub(left, right));
	}

	@Override
	public T mul(T left, T right) {
		return reduce(underlyingRing.mul(left, right));
	}

	@Override
	public T div(T left, T right) {
		return reduce(underlyingRing.div(left, right));
	}

	@Override
	public T reduce(T element) {
		return reducer.reduce(element);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reducer == null) ? 0 : reducer.hashCode());
		result = prime * result + ((underlyingRing == null) ? 0 : underlyingRing.hashCode());
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
		IntegerModPRing<?> other = (IntegerModPRing<?>) obj;
		if (reducer == null) {
			if (other.reducer != null)
				return false;
		} else if (!reducer.equals(other.reducer))
			return false;
		if (underlyingRing == null) {
			if (other.underlyingRing != null)
				return false;
		} else if (!underlyingRing.equals(other.underlyingRing))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IntegerModPRing [underlyingRing=" + underlyingRing + ", reducer=" + reducer + "]";
	}

	@Override
	public T makeFromInt(int i) {
		return reducer.reduce(underlyingRing.makeFromInt(i));
	}

}
