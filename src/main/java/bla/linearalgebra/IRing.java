package bla.linearalgebra;

public interface IRing<T> { // extends IRingElement> {
	// a + 0 = a
	T zero();

	// a * 1 = a
	T one();

	/**
	 * 
	 * @param element
	 * @return
	 * @throws NullPointerException
	 *             if element is null
	 */
	// a -> -a
	T neg(T element);

	T add(T left, T right);

	T sub(T left, T right);

	T mul(T left, T right);
//	T mulByFactor(T element, int factor);

	T makeFromInt(int i);

	// For zz_p
	// boolean OneIsPrime
}
