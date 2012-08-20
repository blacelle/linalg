package bla.linearalgebra.matrix.impl;

import bla.linearalgebra.matrix.IFourSquaresMatrix;
import bla.linearalgebra.matrix.IMatrix;

public class FourSquareMatrix<T> extends AMatrix<T> implements IFourSquaresMatrix<T> {
	protected final IMatrix<T> northWest;
	protected final IMatrix<T> northEast;
	protected final IMatrix<T> southWest;
	protected final IMatrix<T> southEast;

	public FourSquareMatrix(IMatrix<T> northWest, IMatrix<T> northEast, IMatrix<T> southWest, IMatrix<T> southEast) {
		super(northWest.getCoeffRing(), northWest.nbRows() + southWest.nbRows(), northWest.nbColumns() + northEast.nbColumns());

		if (!northWest.getCoeffRing().equals(northEast.getCoeffRing()) || !northWest.getCoeffRing().equals(northEast.getCoeffRing())
				|| !northWest.getCoeffRing().equals(northEast.getCoeffRing()) || !northWest.getCoeffRing().equals(northEast.getCoeffRing())) {
			throw new RuntimeException("Incompatible coeff rings");
		}

		this.northWest = northWest;
		this.northEast = northEast;
		this.southWest = southWest;
		this.southEast = southEast;
	}

	@Override
	public T getValue(int rowIndex, int columnIndex) {
		return getSquare(rowIndex, columnIndex).getValue(cleanRowIndex(rowIndex), cleanColumnIndex(columnIndex));
	}

	protected int cleanRowIndex(int rowIndex) {
		if (rowIndex < northWest.nbRows()) {
			return rowIndex;
		} else {
			return rowIndex - northWest.nbRows();
		}
	}

	protected int cleanColumnIndex(int columnIndex) {
		if (columnIndex < northWest.nbColumns()) {
			return columnIndex;
		} else {
			return columnIndex - northWest.nbColumns();
		}
	}

	protected IMatrix<T> getSquare(int rowIndex, int columnIndex) {
		if (rowIndex < northWest.nbRows()) {
			if (columnIndex < northWest.nbColumns()) {
				return northWest;
			} else {
				return northEast;
			}
		} else {
			if (columnIndex < northWest.nbColumns()) {
				return southWest;
			} else {
				return southEast;
			}
		}
	}

	@Override
	public void setValue(int rowIndex, int columnIndex, T value) {
		getSquare(rowIndex, columnIndex).setValue(cleanRowIndex(rowIndex), cleanColumnIndex(columnIndex), value);
	}

}
