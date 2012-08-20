package bla.linearalgebra.impl;

import java.util.Arrays;
import java.util.List;

import bla.linearalgebra.IRing;

public class RingUtil {
	public static <T> T power(IRing<T> ring, T element, int power) {
		// 1
		T output = ring.one();

		// element^power
		for (int i = 0; i < power; i++) {
			output = ring.mul(output, element);
		}

		return output;
	}

	// http://fr.wikipedia.org/wiki/Algorithme_d%27Euclide_%C3%A9tendu
	public static <T> List<T> XGCD(IRing<T> ring, T a, T b) {

		T u = ring.one();
		T v = ring.zero();

		T s = ring.zero();
		T t = ring.one();

		T q = null;
		T r = null;
		T tmp = null;

		while (!b.equals(ring.zero())) {
			q = ring.div(a, b);
			r = ring.sub(a, ring.mul(q, b));
			a = b;
			b = r;
			tmp = s;
			s = ring.sub(u, ring.mul(q, s));
			u = tmp;
			tmp = t;
			t = ring.sub(v, ring.mul(q, t));
			v = tmp;
		}
		return Arrays.asList(u, v, a);
	}

	// Return w such that w^n = 1 and w^1...w^2 are different
	public static <T> T findPrimitiveRoot(IRing<T> ring, int n) {
		// Start from 2
		T testedValue = ring.add(ring.one(), ring.one());

		// Worst case: we test all values
		while (!ring.zero().equals(testedValue)) {
			
			
			if (!ring.one().equals(power(ring, testedValue, n - 1)) && ring.one().equals(power(ring, testedValue, n))) {
				break;
			} else {
				// Add 1 to the tested value
				ring.add(testedValue, ring.one());
			}

		}

		return testedValue;
	}
}
