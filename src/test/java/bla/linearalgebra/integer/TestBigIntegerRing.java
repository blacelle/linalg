package bla.linearalgebra.integer;

import java.math.BigInteger;
import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import bla.linearalgebra.IRing;
import bla.linearalgebra.integer.big.impl.BigIntegerRing;
import bla.linearalgebra.ring.ATestRing;

public class TestBigIntegerRing extends ATestRing<BigInteger> {

	@Override
	public IRing<BigInteger> makeRing() {
		return new BigIntegerRing();
	}

	@Override
	public Iterable<BigInteger> getIteratable() {
		return Arrays.asList(BigInteger.valueOf(17), BigInteger.valueOf(11), BigInteger.valueOf(5), BigInteger.valueOf(4), BigInteger.valueOf(13),
				BigInteger.valueOf(9), BigInteger.valueOf(12));
	}

	@Test
	public void testZeroValue() {
		Assert.assertEquals((Integer) 0, makeRing().zero());
	}

	@Test
	public void testOneValue() {
		Assert.assertEquals((Integer) 1, makeRing().one());
	}
}
