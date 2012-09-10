package bla.linearalgebra.matrix.impl;

import bla.linearalgebra.matrix.IMatrix;

public class SubMatrix<T> extends AProxyMatrix<T> {
	protected final int firstIncludedRowIndex;
	protected final int firstIncludedColumnIndex;

	/**
	 * 
	 * @param coeffRing
	 * @param parent
	 * @param firstIncludedRowIndex
	 * @param lastExcludedRowIndex
	 *            if equals to firstIncludedRowIndex, nbRows will be 0
	 * @param firstIncludedColumnIndex
	 * @param lastExcludedColumnIndex
	 *            if equals to firstIncludedColumnIndex, nbColumns will be 0
	 */
	public SubMatrix(IMatrix<T> parent, int firstIncludedRowIndex, int lastExcludedRowIndex, int firstIncludedColumnIndex, int lastExcludedColumnIndex) {
		super(parent, lastExcludedRowIndex - firstIncludedRowIndex, lastExcludedColumnIndex - firstIncludedColumnIndex);

		this.firstIncludedRowIndex = firstIncludedRowIndex;
		this.firstIncludedColumnIndex = firstIncludedColumnIndex;
	}

	@Override
	public T getValue(int rowIndex, int columnIndex) {
		return getParent().getValue(rowIndex + firstIncludedRowIndex, columnIndex + firstIncludedColumnIndex);
	}

	@Override
	public void setValue(int rowIndex, int columnIndex, T value) {
		getParent().setValue(rowIndex + firstIncludedRowIndex, columnIndex + firstIncludedColumnIndex, value);
	}

}
