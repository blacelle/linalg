package bla.linearalgebra;

public abstract class ATestRingWithUnderlyingRing<S, T> extends ATestRing<T> {
	public abstract IRingWithUnderlyingRing<S, T> makeRing();

}
