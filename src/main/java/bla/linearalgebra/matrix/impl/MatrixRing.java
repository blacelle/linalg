package bla.linearalgebra.matrix.impl;

import bla.linearalgebra.impl.GenericRing;
import bla.linearalgebra.matrix.IMatrix;

public class MatrixRing<T> extends GenericRing<IMatrix<T>> {

	public MatrixRing(IMatrix<T> additionNeutral, IMatrix<T> multiplicationNeutral) {
		super(new MatrixAdditionOperation<T>(additionNeutral), new MatrixMultiplicationOperation<T>(multiplicationNeutral));
	}

}
