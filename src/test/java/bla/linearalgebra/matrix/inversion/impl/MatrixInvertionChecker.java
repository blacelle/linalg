package bla.linearalgebra.matrix.inversion.impl;

import org.junit.Assert;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.IMatrix;
import bla.linearalgebra.matrix.MatrixSquareUtil;
import bla.linearalgebra.matrix.impl.SubMatrix;
import bla.linearalgebra.matrix.structured.impl.DiagonalMatrix;

public class MatrixInvertionChecker<T> implements IMatrixInvertion<T> {
	protected IMatrixInvertion<T> mbaInversion;

	public MatrixInvertionChecker(IMatrixInvertion<T> mbaInversion) {
		this.mbaInversion = mbaInversion;
	}

	@Override
	public IMatrix<T> doInverse(IRing<IMatrix<T>> matrixRing, IMatrix<T> iA) {
		IMatrix<T> inverse = mbaInversion.doInverse(matrixRing, iA);

		int inverseSize = MatrixSquareUtil.getSize(inverse);

		IMatrix<T> expected = new DiagonalMatrix<T>(iA.getCoeffRing(), inverseSize, inverseSize, iA.getCoeffRing().one());

		IMatrix<T> toCheck = matrixRing.mul(new SubMatrix<T>(inverse, 0, inverseSize, 0, inverseSize), inverse);

		if (!expected.equals(toCheck)) {
			// Redo inversion for debug purposes
			mbaInversion.doInverse(matrixRing, iA);
			matrixRing.mul(new SubMatrix<T>(inverse, 0, inverseSize, 0, inverseSize), inverse);

			Assert.assertEquals(expected, toCheck);
		}

		return inverse;
	}
}
