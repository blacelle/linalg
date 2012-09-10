package bla.linearalgebra.matrix;

import bla.linearalgebra.IRing;

public interface IMatrix<T> {
	int nbRows();

	int nbColumns();

	T getValue(int rowIndex, int columnIndex);

	void setValue(int rowIndex, int columnIndex, T value);

	/**
	 * The {@link IMatrixVisitor} will be evaluated on each non-zero cell of
	 * this
	 * 
	 * @return true if the visitor always returned true. Else false
	 */
	boolean accept(IMatrixVisitor iMatrixVisitor);

	IRing<T> getCoeffRing();
}
