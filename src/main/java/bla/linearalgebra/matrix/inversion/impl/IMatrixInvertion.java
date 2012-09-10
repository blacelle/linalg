package bla.linearalgebra.matrix.inversion.impl;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.IMatrix;

public interface IMatrixInvertion<T> {

	IMatrix<T> doInverse(IRing<IMatrix<T>> matrixRing, IMatrix<T> iA);

}
