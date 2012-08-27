package bla.linearalgebra;

public interface IRingReducer<T> {
	T reduce(T element);

	T findNthPrimitiveRootOfUnity(IRing<T> ring, int n);
}
