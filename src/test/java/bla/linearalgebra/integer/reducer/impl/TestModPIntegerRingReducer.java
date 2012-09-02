package bla.linearalgebra.integer.reducer.impl;

import java.math.BigInteger;

import junit.framework.Assert;

import org.junit.Test;

public class TestModPIntegerRingReducer {
	@Test
	public void test() {
		// 2013265921 = 15 * 2^27 + 1
		int modp = 2013265921;

		ModPBigIntegerRingReducer reducer = new ModPBigIntegerRingReducer(modp);

		int n = 17;

		BigInteger root = reducer.findNthPrimitiveRootOfUnity(null, n);

		for (int i = 1; i < BigInteger.valueOf(2).pow(n).intValue(); i++) {
			Assert.assertFalse(Integer.valueOf(1).equals(root.modPow(BigInteger.valueOf(i), BigInteger.valueOf(modp)).intValue()));
		}
		Assert.assertTrue(Integer.valueOf(1).equals(root.modPow(BigInteger.valueOf(2).pow(n), BigInteger.valueOf(modp)).intValue()));
	}
}
