package bla.linearalgebra.integer.reducer.impl;

import java.math.BigInteger;

import bla.linearalgebra.IRing;
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

	@Override
	public Integer findNthPrimitiveRootOfUnity(IRing<Integer> ring, int n) {
		if (modp == 2013265921) {
			// e == 27, a==31
			return BigInteger.valueOf(31).modPow(BigInteger.valueOf(2).pow(27 - n).multiply(BigInteger.valueOf(15)), BigInteger.valueOf(modp)).intValue();
		} else {
			return null;
		}
	}

}
