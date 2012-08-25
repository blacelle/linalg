package bla.linearalgebra.matrix;

import org.junit.Assert;
import org.junit.Test;

import bla.linearalgebra.matrix.structured.IConstantMatrix;
import bla.linearalgebra.matrix.structured.impl.ConstantMatrix;
import bla.linearalgebra.ring.ATestRingWithUnderlyingRing;

public abstract class ATestMatrixRing<T> extends ATestRingWithUnderlyingRing<T, IMatrix<T>> {
	
	@Test
	public void testMulConstantMatrices() {
		IConstantMatrix<T> left = new ConstantMatrix<T>(makeRing().getCoeffRing(), 3, 7, makeRing().getCoeffRing().one());
		IConstantMatrix<T> right = new ConstantMatrix<T>(makeRing().getCoeffRing(), 7, 5, makeRing().getCoeffRing().one());

		Assert.assertEquals(new ConstantMatrix<T>(makeRing().getCoeffRing(), 3, 5, makeRing().getCoeffRing().makeFromInt(7)), makeRing().mul(left, right));
	}

	@Test
	public void testInverseMatrixOfSizeOne() {
		int threeAsInt = 3;

		IMatrix<T> three = makeRing().makeFromInt(threeAsInt);

		// Inverse the matrix from an int
		IMatrix<T> firstInverse = makeRing().inv(three);
		
		// Matrix of the inverse of a single field element built from an int
		IMatrix<T> secondInverse = makeRing().makeFromField(makeRing().getCoeffRing().inv(makeRing().getCoeffRing().makeFromInt(threeAsInt)));

		Assert.assertEquals(secondInverse, firstInverse);
	}
}
