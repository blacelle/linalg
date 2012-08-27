package bla.linearalgebra.algorithm.fft;

import java.util.List;

import bla.linearalgebra.IRing;

public interface FftAlgorithm {
	/**
	 * Calcul du papillon
	 */
	<T> void butterfly(IRing<T> ring, List<T> a, int i, int j, T alpha);

	/**
	 * Calcul de la FFT
	 * 
	 * @param a
	 *            tableau dont la taille n doit être une puissance de 2
	 * @param root
	 *            racine primitive n-ième de l'unité
	 */
	<T> List<T> fft(IRing<T> ring, List<T> a, T root);
}