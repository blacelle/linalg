package bla.linearalgebra.integer;

import java.math.BigInteger;

import bla.linearalgebra.IRing;
import bla.linearalgebra.integer.big.impl.BigIntegerRing;
import bla.linearalgebra.integer.impl.GenericReducedRing;
import bla.linearalgebra.integer.reducer.impl.ModPBigIntegerRingReducer;

public class TestBigIntegerModPRing extends TestBigIntegerRing {

	@Override
	public IRing<BigInteger> makeRing() {
		return new GenericReducedRing<BigInteger>(new BigIntegerRing(7), new ModPBigIntegerRingReducer(7));
	}

}
