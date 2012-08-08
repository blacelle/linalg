package bla.linearalgebra.impl;

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
}
