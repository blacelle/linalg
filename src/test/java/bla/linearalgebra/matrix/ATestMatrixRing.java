package bla.linearalgebra.matrix;

import org.junit.Assert;
import org.junit.Test;

import bla.linearalgebra.ATestRing;
import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.structured.IConstantMatrix;
import bla.linearalgebra.matrix.structured.impl.ConstantMatrix;

public abstract class ATestMatrixRing<T> extends ATestRing<IMatrix<T>> {

	public abstract IRing<T> makeCoeffRing();

	@Test
	public void testMulConstantMatrices() {
		IConstantMatrix<T> left = new ConstantMatrix<T>(makeCoeffRing(), 3, 7, makeCoeffRing().one());
		IConstantMatrix<T> right = new ConstantMatrix<T>(makeCoeffRing(), 7, 5, makeCoeffRing().one());

		Assert.assertEquals(new ConstantMatrix<T>(makeCoeffRing(), 3, 5, makeCoeffRing().makeFromInt(7)), makeRing().mul(left, right));
	}
}
