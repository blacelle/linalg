package bla.linearalgebra;

public interface IOperation<T> {
	T add(T left, T right);

	T getNeutralElement();

	T makeFromint(int i);
}
