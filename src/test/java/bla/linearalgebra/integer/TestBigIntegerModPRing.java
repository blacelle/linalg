package bla.linearalgebra.integer;

import java.math.BigInteger;

import bla.linearalgebra.IRing;
import bla.linearalgebra.integer.big.impl.BigIntegerRing;
import bla.linearalgebra.integer.impl.IntegerModPRing;
import bla.linearalgebra.integer.reducer.impl.ModPBigIntegerRingReducer;

public class TestBigIntegerModPRing extends TestBigIntegerRing {

	@Override
	public IRing<BigInteger> makeRing() {
		return new IntegerModPRing<BigInteger>(new BigIntegerRing(7), new ModPBigIntegerRingReducer(7));
	}

}
