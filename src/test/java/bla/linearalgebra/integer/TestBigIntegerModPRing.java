package bla.linearalgebra.integer;

import java.math.BigInteger;

import bla.linearalgebra.ATestRing;
import bla.linearalgebra.IRing;
import bla.linearalgebra.integer.big.impl.BigIntegerRing;
import bla.linearalgebra.integer.impl.IntegerModPRing;
import bla.linearalgebra.integer.reducer.impl.ModPBigIntegerRingReducer;

public class TestBigIntegerModPRing extends ATestRing<BigInteger> {

	@Override
	public IRing<BigInteger> makeRing() {
		return new IntegerModPRing<BigInteger>(new BigIntegerRing(), new ModPBigIntegerRingReducer(7));
	}

	@Override
	public Iterable<BigInteger> getIteratable() {
		// TODO Auto-generated method stub
		return null;
	}

}
