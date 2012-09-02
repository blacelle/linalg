package bla.linearalgebra.matrix.inversion.impl;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import bla.linearalgebra.IRing;
import bla.linearalgebra.IRingWithUnderlyingRing;
import bla.linearalgebra.integer.big.impl.BigIntegerRing;
import bla.linearalgebra.integer.impl.IntegerModPRing;
import bla.linearalgebra.integer.reducer.impl.ModPBigIntegerRingReducer;
import bla.linearalgebra.matrix.IMatrix;
import bla.linearalgebra.matrix.MatrixUtil;
import bla.linearalgebra.matrix.impl.MatrixRing;
import bla.linearalgebra.matrix.structured.impl.DiagonalMatrix;
import bla.linearalgebra.matrix.structured.impl.VandermondeMatrix;

public class TestMBAInversion {
	public IRing<BigInteger> makeCoeffRing() {
		return new IntegerModPRing<BigInteger>(new BigIntegerRing(7), new ModPBigIntegerRingReducer(7));
	}

	public IRingWithUnderlyingRing<BigInteger, IMatrix<BigInteger>> makeRing() {
		return new MatrixRing<BigInteger>(MatrixUtil.makeZero(makeCoeffRing(), nbRows(), nbColumns()), MatrixUtil.makeOne(makeCoeffRing(), nbRows(),
				nbColumns()));
	}

	private int nbColumns() {
		return 3;
	}

	private int nbRows() {
		return 3;
	}

	@SuppressWarnings("unchecked")
	public Iterable<IMatrix<BigInteger>> getIteratable() {
		int[] coeffs = new int[nbRows()];

		for (int i = 0; i < nbRows(); i++) {
			coeffs[i] = i + 1;
		}

		IMatrix<BigInteger> first = new VandermondeMatrix<BigInteger>(makeRing().getCoeffRing(), coeffs, nbColumns());

		return Arrays.asList(first);
	}

	@Test
	public void testDoInverse() {
		IMatrix<BigInteger> first = getIteratable().iterator().next();

		Assert.assertEquals(new DiagonalMatrix<BigInteger>(makeCoeffRing(), nbRows(), nbColumns(), makeCoeffRing().one()),
				makeRing().mul(first, MBAInversion.doInverse(makeRing(), first)));
	}
}
