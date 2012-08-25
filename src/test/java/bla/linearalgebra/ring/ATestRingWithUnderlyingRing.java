package bla.linearalgebra.ring;

import bla.linearalgebra.IRingWithUnderlyingRing;

public abstract class ATestRingWithUnderlyingRing<S, T> extends ATestRing<T> {
	public abstract IRingWithUnderlyingRing<S, T> makeRing();

}
