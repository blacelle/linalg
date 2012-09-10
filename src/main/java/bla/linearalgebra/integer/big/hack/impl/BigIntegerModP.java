package bla.linearalgebra.integer.big.hack.impl;

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerModP extends BlaBigInteger {
	private static final long serialVersionUID = 2721855364236480851L;

	final static long LONG_MASK = 0xffffffffL;

	public BigIntegerModP(byte[] val) {
		super(val);
	}

	public BigIntegerModP(int signum, byte[] magnitude) {
		super(signum, magnitude);
	}

	public BigIntegerModP(int bitLength, int certainty, Random rnd) {
		super(bitLength, certainty, rnd);
	}

	public BigIntegerModP(int numBits, Random rnd) {
		super(numBits, rnd);
	}

	public BigIntegerModP(String val, int radix) {
		super(val, radix);
	}

	public BigIntegerModP(String val) {
		super(val);
	}

	protected int mod;
	protected BigIntegerModP(long val, int mod) {
		super(val);
		this.mod= mod;
	}

	public BlaBigInteger mulMod(BlaBigInteger val, int mod) {
		if (val.signum == 0 || signum == 0)
			return ZERO;

		int[] result = modMultiplyToLen(mag, mag.length, val.mag, val.mag.length, null, mod);
		result = trustedStripLeadingZeroInts(result);

		return new BlaBigInteger(result, signum == val.signum ? 1 : -1);

		// return left.multiply(right).remainder(mod);
	}

	/**
	 * Multiplies int arrays x and y to the specified lengths and places the
	 * result into z. There will be no leading zeros in the resultant array.
	 */
	// Adapted from BigInteger
	protected int[] modMultiplyToLen(int[] x, int xlen, int[] y, int ylen, int[] z, int mod) {
		int xstart = xlen - 1;
		int ystart = ylen - 1;

		if (z == null || z.length < (xlen + ylen))
			z = new int[xlen + ylen];

		long carry = 0;
		for (int j = ystart, k = ystart + 1 + xstart; j >= 0; j--, k--) {
			long product = (y[j] & LONG_MASK) * (x[xstart] & LONG_MASK) + carry;
			z[k] = (int) product;
			carry = product >>> 32;
		}
		z[xstart] = (int) carry;

		for (int i = xstart - 1; i >= 0; i--) {
			carry = 0;
			for (int j = ystart, k = ystart + 1 + i; j >= 0; j--, k--) {
				long product = (y[j] & LONG_MASK) * (x[i] & LONG_MASK) + (z[k] & LONG_MASK) + carry;
				z[k] = (int) product;
				carry = product >>> 32;
			}
			z[i] = (int) carry;
		}
		return z;
	}
}
