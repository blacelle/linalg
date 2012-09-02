package bla.linearalgebra.integer.reducer.impl;

import java.math.BigInteger;

import bla.linearalgebra.IRing;
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
		if (element == null) {
			return null;
		} else {
			return element.remainder(modp);
		}
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

	@Override
	public BigInteger findNthPrimitiveRootOfUnity(IRing<BigInteger> ring, int n) {
		// int twoPower_e_by_k = modp -1;
		//
		// int e = 0;
		// while (twoPower_e_by_k%2 == 0) {
		// e++;
		// twoPower_e_by_k = twoPower_e_by_k / 2;
		// }

		// modp = 2^e*k
		// BigInteger e

		if (modp.equals(BigInteger.valueOf(2013265921L))) {
			// e == 27, a==31
			return BigInteger.valueOf(31).modPow(BigInteger.valueOf(2).pow(27 - n).multiply(BigInteger.valueOf(15)), modp);
		} else if (modp.equals(BigInteger.valueOf(7L))) {
			return BigInteger.valueOf(3);
		} else {
			return null;
		}
	}
}
