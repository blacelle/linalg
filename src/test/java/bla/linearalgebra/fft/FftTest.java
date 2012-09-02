package bla.linearalgebra.fft;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import bla.linearalgebra.IRing;
import bla.linearalgebra.algorithm.fft.FFT;
import bla.linearalgebra.algorithm.fft.FftAlgorithm;
import bla.linearalgebra.integer.big.impl.BigIntegerRing;
import bla.linearalgebra.integer.impl.IntegerModPRing;
import bla.linearalgebra.integer.reducer.impl.ModPBigIntegerRingReducer;

/**
 * Test de l'Algorithme de FFT
 * 
 * @author O. Carton
 * @version 1.0
 */
public class FftTest {
	/**
	 * Affichage d'un tableau comme un polynôme
	 */
	public static <T> String toString(IRing<T> ring, T[] a) {
		int i; // Variable de boucle
		StringBuffer sb = new StringBuffer();

		for (i = 0; i < a.length; i++) {
			sb.append(a[i]);
			sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * Multiplication de chacun des éléments d'un tableau par un scalaire
	 */
	public static <T> void scalMult(IRing<T> ring, List<T> a, T scal) {
		for (int i = 0; i < a.size(); i++)
			a.set(i, ring.mul(a.get(i), scal));
	}

	public static void main(String[] args) {
		main2(new IntegerModPRing<BigInteger>(new BigIntegerRing(257), new ModPBigIntegerRingReducer(257)));
	}

	public static <T> void main2(IRing<T> ring) {
		int i, j; // Variables de boucle

		// Algorithme utilisé (FftAlgoIter() éventuellement)
		FftAlgorithm algo = new FFT();

		// Paramètres pour faire de la FFT dans Z/257Z
		// avec 4 comme racine 8-eme de l'unité.
		// final int modulo = 257;
		// Racine 8-ème de l'unité : 4*193 = 1 mod 257
		final T omega = ring.makeFromInt(4);
		final T omegainv = ring.makeFromInt(193);
		final int n = 8; // Taille du tableau
		// 8*225 = 1 mod 257
		final T ninv = ring.makeFromInt(225);
		// Racine 256-ieme de l'unite : 3
		// Autres nombre premiers
		// 7.2^26+1 5.2^26+1 45.2^24+1
		// Allocation du tableau

		// Essais avec les éléments de la base : 1, x, x^2, ..., x^{n-1}
		for (i = 0; i < n; i++) {
			List<T> a = new ArrayList<T>();
			// x^i
			for (j = 0; j < n; j++)
				a.add(ring.makeFromInt(i == j ? 1 : 0));
			System.out.println("P = x^" + i);
			// System.out.println(toString(ring, a));
			a = algo.fft(ring, a, omega);
			// System.out.println(toString(ring, a));
			// Calcul de la FFT inverse
			a = algo.fft(ring, a, omegainv);
			scalMult(ring, a, ninv); // Normalisation
			// System.out.println(toString(ring, a));
		}
		{
			List<T> a = new ArrayList<T>();

			// Exemple du poly
			a.add(ring.makeFromInt(255));
			a.add(ring.makeFromInt(8));
			a.add(ring.makeFromInt(0));
			a.add(ring.makeFromInt(226));
			a.add(ring.makeFromInt(37));
			a.add(ring.makeFromInt(240));
			a.add(ring.makeFromInt(3));
			a.add(ring.makeFromInt(0));
			// System.out.println(toString(ring, a));
			a = algo.fft(ring, a, omega);
			// Calcul de la FFT inverse
			// System.out.println(toString(ring, a));
			a = algo.fft(ring, a, omegainv);
			scalMult(ring, a, ninv); // Normalisation
			// System.out.println(toString(ring, a));
		}
	}
}
