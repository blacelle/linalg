package bla.linearalgebra;

public interface IRingWithUnderlyingRing<S, T> extends IRing<T> {

	IRing<S> getCoeffRing();

	T makeFromField(S i);

}
