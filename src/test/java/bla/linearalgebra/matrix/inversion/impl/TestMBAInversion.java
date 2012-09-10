package bla.linearalgebra.matrix.inversion.impl;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import junit.framework.Assert;

import org.junit.Test;

import bla.linearalgebra.IRing;
import bla.linearalgebra.IRingWithUnderlyingRing;
import bla.linearalgebra.integer.big.impl.BigIntegerRing;
import bla.linearalgebra.matrix.IMatrix;
import bla.linearalgebra.matrix.MatrixSquareUtil;
import bla.linearalgebra.matrix.MatrixUtil;
import bla.linearalgebra.matrix.impl.MatrixRing;
import bla.linearalgebra.matrix.impl.SubMatrix;
import bla.linearalgebra.matrix.structured.impl.CachedMatrix;
import bla.linearalgebra.matrix.structured.impl.DiagonalMatrix;
import bla.linearalgebra.matrix.structured.impl.VandermondeMatrix;

public class TestMBAInversion {
	public IRing<BigInteger> makeCoeffRing() {
		// return new GenericReducedRing<BigInteger>(new
		// BigIntegerRing(2013265921L), new
		// ModPBigIntegerRingReducer(2013265921));
		return new BigIntegerRing(2013265921L);
	}

	public IRingWithUnderlyingRing<BigInteger, IMatrix<BigInteger>> makeRing() {
		return new MatrixRing<BigInteger>(MatrixUtil.makeZero(makeCoeffRing(), nbRows(), nbColumns()), MatrixUtil.makeOne(makeCoeffRing(), nbRows(),
				nbColumns()));
	}

	private int nbColumns() {
//		return 512;
		return 128;
	}

	private int nbRows() {
//		return 512;
		return 128;
	}

	public Iterable<IMatrix<BigInteger>> getIteratable() {
		int[] coeffs = new int[nbRows()];

		for (int i = 0; i < nbRows(); i++) {
			coeffs[i] = i + 1;
		}

		IMatrix<BigInteger> first = new VandermondeMatrix<BigInteger>(makeRing().getCoeffRing(), coeffs, nbColumns());

		return Arrays.<IMatrix<BigInteger>> asList(new CachedMatrix<BigInteger>(first));
	}

	@Test
	public void testDoInverse() {
		final IMatrix<BigInteger> first = getIteratable().iterator().next();

		ForkJoinPool fjp = new ForkJoinPool();
		fjp.invoke(new RecursiveAction() {
			private static final long serialVersionUID = -1895678139716740264L;

			@Override
			protected void compute() {

				// Override doInverse to check all the recursive returned
				// inverse
				// new MatrixInvertionChecker<BigInteger>(new
				// MBAInversion<BigInteger>()).doInverse(makeRing(), first);

				new MBAInversion<BigInteger>() {
					@Override
					public IMatrix<BigInteger> doInverse(IRing<IMatrix<BigInteger>> matrixRing, IMatrix<BigInteger> iA) {
						IMatrix<BigInteger> inverse = super.doInverse(matrixRing, iA);

						if (inverse == null) {
							return null;
						} else {

							if (false) {
								int inverseSize = MatrixSquareUtil.getSize(inverse);
								
								IMatrix<BigInteger> expected = new DiagonalMatrix<BigInteger>(iA.getCoeffRing(), inverseSize, inverseSize, iA.getCoeffRing().one());
								
								IMatrix<BigInteger> toCheck = matrixRing.mul(new SubMatrix<BigInteger>(iA, 0, inverseSize, 0, inverseSize), inverse);
								
								if (!MatrixUtil.equals(expected, toCheck)) {
									// Redo inversion for debug purposes
									super.doInverse(matrixRing, iA);
									matrixRing.mul(new SubMatrix<BigInteger>(iA, 0, inverseSize, 0, inverseSize), inverse);
									
									Assert.assertTrue(expected + "is not equal to" + toCheck, MatrixUtil.equals(expected, toCheck));
								}
							}

							return inverse;
						}
					}
				}.doInverse(makeRing(), first);

			}
		});

//		System.out.println(fjp.getStealCount());
//		try {
//			Thread.sleep(1000000000L);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
