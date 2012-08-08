package bla.linearalgebra.matrix.vector;

import bla.linearalgebra.matrix.IMatrix;

public interface IVerticalVector<T> extends IMatrix<T> {
	void setValue(int rowIndex, T value);

	T getValue(int rowIndex);
}
