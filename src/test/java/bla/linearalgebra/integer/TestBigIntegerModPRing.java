package bla.linearalgebra.integer;

import java.math.BigInteger;
import java.util.Arrays;

import bla.linearalgebra.IRing;
import bla.linearalgebra.integer.big.impl.BigIntegerRing;
import bla.linearalgebra.integer.impl.IntegerModPRing;
import bla.linearalgebra.integer.reducer.impl.ModPBigIntegerRingReducer;
import bla.linearalgebra.ring.inversible.ATestInversibleRing;

public class TestBigIntegerModPRing extends ATestInversibleRing<BigInteger> {

	@Override
	public IRing<BigInteger> makeRing() {
		return new IntegerModPRing<BigInteger>(new BigIntegerRing(7), new ModPBigIntegerRingReducer(7));
	}

	@Override
	public Iterable<BigInteger> getIteratable() {
		return Arrays.asList(BigInteger.valueOf(17), BigInteger.valueOf(11), BigInteger.valueOf(5), BigInteger.valueOf(4), BigInteger.valueOf(13),
				BigInteger.valueOf(9), BigInteger.valueOf(12));
	}
}
