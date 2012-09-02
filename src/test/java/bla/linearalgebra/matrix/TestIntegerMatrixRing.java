package bla.linearalgebra.matrix;

import java.math.BigInteger;
import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import bla.linearalgebra.IRing;
import bla.linearalgebra.IRingWithUnderlyingRing;
import bla.linearalgebra.integer.big.impl.BigIntegerRing;
import bla.linearalgebra.matrix.impl.MatrixRing;
import bla.linearalgebra.matrix.structured.impl.VandermondeMatrix;

public class TestIntegerMatrixRing extends ATestMatrixRing<BigInteger> {
	/**
	 * Should be used only in makeRing()
	 */
	public IRing<BigInteger> makeCoeffRing() {
		return new BigIntegerRing();
	}

	@Override
	public IRingWithUnderlyingRing<BigInteger, IMatrix<BigInteger>> makeRing() {
		return new MatrixRing<BigInteger>(MatrixUtil.makeZero(makeCoeffRing(), nbRows(), nbColumns()), MatrixUtil.makeOne(makeCoeffRing(), nbRows(),
				nbColumns()));
	}

	public int nbRows() {
		return 3;
	}

	public int nbColumns() {
		return 3;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<IMatrix<BigInteger>> getIteratable() {
		IMatrix<BigInteger> first = new VandermondeMatrix<BigInteger>(makeRing().getCoeffRing(), new int[] { 2, 3, 5 }, nbColumns());
		IMatrix<BigInteger> second = new VandermondeMatrix<BigInteger>(makeRing().getCoeffRing(), new int[] { 3, 5, 7 }, nbColumns());
		IMatrix<BigInteger> third = new VandermondeMatrix<BigInteger>(makeRing().getCoeffRing(), new int[] { 5, 7, 13 }, nbColumns());

		return Arrays.asList(first, second, third);
	}

	@Test
	public void testZeroValue() {
		Assert.assertEquals(MatrixUtil.makeZero(makeRing().getCoeffRing(), nbRows(), nbColumns()), makeRing().zero());
	}

	@Test
	public void testOneValue() {
		Assert.assertEquals(MatrixUtil.makeOne(makeRing().getCoeffRing(), nbRows(), nbColumns()), makeRing().one());
	}
}
