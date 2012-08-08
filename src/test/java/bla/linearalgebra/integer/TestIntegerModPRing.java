package bla.linearalgebra.integer;

import bla.linearalgebra.IRing;
import bla.linearalgebra.integer.impl.IntegerModPRing;
import bla.linearalgebra.integer.impl.IntegerRing;
import bla.linearalgebra.integer.reducer.impl.ModPIntegerRingReducer;

public class TestIntegerModPRing extends TestIntegerRing {
	@Override
	public IRing<Integer> makeRing() {
		return new IntegerModPRing<Integer>(new IntegerRing(), new ModPIntegerRingReducer(7));
	}
}
