package bla.linearalgebra.integer.reducer.impl;

import java.math.BigInteger;

import bla.linearalgebra.IRingReducer;

public class ModPBigIntegerRingReducer implements IRingReducer<BigInteger> {
	protected BigInteger modp;

	public ModPBigIntegerRingReducer(BigInteger modP) {
		this.modp = modP;
	}

	public ModPBigIntegerRingReducer(int i) {
		this(BigInteger.valueOf(i));
	}

	public BigInteger getModP() {
		return modp;
	}

	@Override
	public BigInteger reduce(BigInteger element) {
		return element.remainder(modp);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modp == null) ? 0 : modp.hashCode());
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
		ModPBigIntegerRingReducer other = (ModPBigIntegerRingReducer) obj;
		if (modp == null) {
			if (other.modp != null)
				return false;
		} else if (!modp.equals(other.modp))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ModPIntegerRingReducer [modp=" + modp + "]";
	}

}
