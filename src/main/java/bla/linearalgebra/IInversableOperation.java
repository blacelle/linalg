package bla.linearalgebra;

public interface IInversableOperation<T> extends IOperation<T> {
	T opposite(T element);

	T minus(T left, T right);
}
