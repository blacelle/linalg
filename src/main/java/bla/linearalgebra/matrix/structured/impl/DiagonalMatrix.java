package bla.linearalgebra.matrix.structured.impl;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.IMatrixVisitor;
import bla.linearalgebra.matrix.structured.IDiagonalMatrix;

public class DiagonalMatrix<T> extends AToeplitzMatrix<T> implements IDiagonalMatrix<T> {
	protected final T valueOnDiagonal;

	public DiagonalMatrix(IRing<T> coeffRing, int nbRows, int nbColumns, T valueOnDiagonal) {
		super(coeffRing, nbRows, nbColumns);

		this.valueOnDiagonal = valueOnDiagonal;

		if (this.valueOnDiagonal == null) {
			throw new RuntimeException("Diagonal can nopt be null");
		}
	}

	public DiagonalMatrix(IRing<T> coeffRing, T valueOnDiagonal) {
		super(coeffRing);

		this.valueOnDiagonal = valueOnDiagonal;

		if (this.valueOnDiagonal == null) {
			throw new RuntimeException("Diagonal can nopt be null");
		}
	}

	@Override
	public T getValue(int rowIndex, int columnIndex) {
		if (rowIndex == columnIndex) {
			return valueOnDiagonal;
		} else {
			return getCoeffRing().zero();
		}
	}

	@Override
	public void setValue(int rowIndex, int columnIndex, T value) {
		throw new RuntimeException("Can not mutate a diagonal matrix");
	}

	@Override
	public void accept(IMatrixVisitor iMatrixVisitor) {
		for (int i = 0; i < Math.min(nbRows(), nbColumns()); i++)
			iMatrixVisitor.visitCell(i, i);
	}

	@Override
	public String toString() {
		return "IDiagonal(" + nbRows() + "," + nbColumns() + ") with " + valueOnDiagonal + " on diagonal";
	}
}
