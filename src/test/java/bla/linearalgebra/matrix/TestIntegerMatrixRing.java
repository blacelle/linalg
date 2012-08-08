package bla.linearalgebra.matrix;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import bla.linearalgebra.IRing;
import bla.linearalgebra.integer.impl.IntegerRing;
import bla.linearalgebra.matrix.impl.MatrixRing;
import bla.linearalgebra.matrix.structured.impl.VandermondeMatrix;

public class TestIntegerMatrixRing extends ATestMatrixRing<Integer> {

	@Override
	public IRing<IMatrix<Integer>> makeRing() {
		return new MatrixRing<Integer>(MatrixUtil.makeZero(makeCoeffRing(), nbRows(), nbColumns()), MatrixUtil.makeOne(makeCoeffRing(), nbRows(), nbColumns()));
	}

	@Override
	public IRing<Integer> makeCoeffRing() {
		return new IntegerRing();
	}

	public int nbRows() {
		return 3;
	}

	public int nbColumns() {
		return 3;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<IMatrix<Integer>> getIteratable() {
		IMatrix<Integer> first = new VandermondeMatrix<Integer>(makeCoeffRing(), Arrays.asList(2, 3, 5), nbColumns());
		IMatrix<Integer> second = new VandermondeMatrix<Integer>(makeCoeffRing(), Arrays.asList(3, 5, 7), nbColumns());
		IMatrix<Integer> third = new VandermondeMatrix<Integer>(makeCoeffRing(), Arrays.asList(5, 7, 13), nbColumns());

		return Arrays.asList(first, second, third);
	}

	@Test
	public void testZeroValue() {
		Assert.assertEquals(MatrixUtil.makeZero(makeCoeffRing(), nbRows(), nbColumns()), makeRing().zero());
	}

	@Test
	public void testOneValue() {
		Assert.assertEquals(MatrixUtil.makeOne(makeCoeffRing(), nbRows(), nbColumns()), makeRing().one());
	}
}
