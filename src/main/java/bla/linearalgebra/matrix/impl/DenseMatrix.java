package bla.linearalgebra.matrix.impl;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.IMatrix;
import bla.linearalgebra.matrix.IMatrixVisitor;

public class DenseMatrix<T> extends AMatrix<T> implements IMatrix<T> {

	protected Object[][] array;

	public DenseMatrix(IRing<T> coeffRing, int nbRows, int nbColumns) {
		super(coeffRing, nbRows, nbColumns);

		this.array = new Object[nbRows][];

		for (int i = 0; i < nbRows; i++) {
			array[i] = new Object[nbColumns];
		}
	}

	public DenseMatrix(final IMatrix<T> source) {
		this(source.getCoeffRing(), source.nbRows(), source.nbColumns());

		source.accept(new IMatrixVisitor() {

			@Override
			public boolean visitCell(int rowIndex, int columnIndex) {
				setValue(rowIndex, columnIndex, source.getValue(rowIndex, columnIndex));

				return true;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getValue(int rowIndex, int columnIndex) {
		return (T) array[rowIndex][columnIndex];
	}

	@Override
	public void setValue(int rowIndex, int columnIndex, T value) {
		array[rowIndex][columnIndex] = value;
	}
}
