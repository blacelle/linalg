package bla.linearalgebra.integer;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import bla.linearalgebra.ATestRing;
import bla.linearalgebra.IRing;
import bla.linearalgebra.integer.impl.IntegerRing;

public class TestIntegerRing extends ATestRing<Integer> {

	@Override
	public IRing<Integer> makeRing() {
		return new IntegerRing();
	}

	@Override
	public Iterable<Integer> getIteratable() {
		return Arrays.asList(17, 11, 5, 4, 13, 9, 12);
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
