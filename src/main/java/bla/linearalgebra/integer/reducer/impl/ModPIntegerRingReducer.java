package bla.linearalgebra.integer.reducer.impl;

import bla.linearalgebra.IRingReducer;
import bla.linearalgebra.integer.impl.IntegerUtils;

public class ModPIntegerRingReducer implements IRingReducer<Integer> {
	protected int modp;

	public ModPIntegerRingReducer(int modP) {
		this.modp = modP;
	}

	// @Override
	public Integer getModP() {
		return modp;
	}

	@Override
	public Integer reduce(Integer element) {
		return IntegerUtils.reduce(element, modp);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + modp;
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
		ModPIntegerRingReducer other = (ModPIntegerRingReducer) obj;
		if (modp != other.modp)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ModPIntegerRingReducer [modp=" + modp + "]";
	}

}
