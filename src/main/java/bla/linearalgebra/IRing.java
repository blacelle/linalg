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

	// T mulByFactor(T element, int factor);

	T inv(T element);

	T div(T left, T right);

	T makeFromInt(int i);

	// Returns a value a such that a^n is 1 and 1, a^1, a^2, ..., a^n-1 are not
	// 1
	T findNthPrimitiveRootOfUnity(int n);

	// For zz_p
	// boolean OneIsPrime
}
