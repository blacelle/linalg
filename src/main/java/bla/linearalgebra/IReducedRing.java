package bla.linearalgebra;


public interface IReducedRing<T> extends IRing<T> {

	/**
	 * For some rings, we need to clean an instance of T of make it an element
	 * of the ring. For instance, 3 is reduced to 1 by the Ring Z/2Z
	 * 
	 * @param element
	 * @return the equivalent of a+0
	 */
	T reduce(T element);
}
