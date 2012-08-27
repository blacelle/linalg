package bla.linearalgebra.algorithm.fft;

import java.util.ArrayList;
import java.util.List;

import bla.linearalgebra.IRing;

public class FFT implements FftAlgorithm {
	/**
	 * Calcul du logarithme h en base 2 de n. Le logarithme h est caractérisé
	 * par 2^h <= n < 2^{h+1}. Le nombre de bits nécessaires pour coder n est
	 * h+1
	 */
	public static int log2(int n) {
		int h = 0;

		while (n > 1) {
			h++;
			n >>= 1;
		}
		return h;
	}

	/**
	 * Calcul de l'entier dont le codage binaire est formé des h bits de poids
	 * faible de n lus à l'envers. L'entier \sum_{i\ge0}b_i2^i est envoyé sur
	 * \sum_{i=0}^h{b_{h-i}2î}. Pour h fixé, cette fonction est une involution :
	 * mirror(h, mirror(h, n)) = n.
	 * 
	 * @param h
	 *            nombre de bits pris en compte
	 * @param n
	 *            entier dont le codage binaire est utilisé
	 */
	private static int mirror(int h, int n) {
		int r; // Résultat

		for (r = 0; h > 0; h--) {
			// A chaque itération le bit de droite de n est
			// est tranféré dans r comme bit de droite
			r <<= 1; // Préparation d'un nouveau bit à droite
			r |= (n & 1); // Positionnement de ce bit
			n >>= 1; // Effacement du bit de droite de n
		}
		return r;
	}

	/**
	 * Permutation en place d'un tableau selon l'involution mirror
	 * 
	 * @param a
	 *            tableau dont la taille doit être une puissance de 2
	 */
	private static <T> List<T> permuteArray(List<T> a) {
		a = new ArrayList<T>(a);

		int h = log2(a.size()); // On suppose a.length = 2^h
		int i, j; // Indexes variables

		for (i = 0; i < a.size(); i++) {
			if ((j = mirror(h, i)) < i) {
				T tmp = a.get(i);
				a.set(i, a.get(j));
				a.set(j, tmp);
			}
		}

		return a;
	}

	/**
	 * Calcul de la FFT
	 * 
	 * @param a
	 *            tableau dont la taille n doit être une puissance de 2
	 * @param root
	 *            racine primitive n-ième de l'unité
	 */
	@Override
	public <T> List<T> fft(IRing<T> ring, List<T> a, T root) {
		int h = log2(a.size());
		int i, j, l; // Variable d'indice
		int step = 1; // Pas

		// Calcul des root^{2^l} pour 0 <= l < h
		List<T> powerRoot = new ArrayList<T>(h);
		powerRoot.add(root);
		for (l = 1; l < h; l++)
			powerRoot.add(ring.mul(powerRoot.get(l - 1), powerRoot.get(l - 1)));

		// Permutation du tableau
		a = permuteArray(a);

		// Itération
		for (l = h - 1, step = 1; l >= 0; l--, step *= 2) {
			T alpha = ring.one(); // Unité
			for (i = 0; i < step; i++) {
				for (j = i; j < a.size(); j += 2 * step) {
					butterfly(ring, a, j, j + step, alpha);
				}
				alpha = ring.mul(alpha, powerRoot.get(l));
			}
		}
		return a;
	}

	@Override
	public <T> void butterfly(IRing<T> ring, List<T> a, int i, int j, T alpha) {

		T u = a.get(i);
		T v = ring.mul(alpha, a.get(j));
		a.set(i, ring.add(u, v));
		a.set(j, ring.sub(u, v));
	}
}