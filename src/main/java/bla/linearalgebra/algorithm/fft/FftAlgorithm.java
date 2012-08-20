package bla.linearalgebra.algorithm.fft;

import bla.linearalgebra.IRing;

public interface FftAlgorithm {
	/**
	 * Calcul du papillon
	 */
	<T> void butterfly(IRing<T> ring, T[] a, int i, int j, T alpha);

	/**
	 * Calcul de la FFT
	 * 
	 * @param a
	 *            tableau dont la taille n doit être une puissance de 2
	 * @param root
	 *            racine primitive n-ième de l'unité
	 */
	<T> T[] fft(IRing<T> ring, T[] a, T root);
}