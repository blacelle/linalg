package bla.linearalgebra.matrix.impl;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.IMatrix;
import bla.linearalgebra.matrix.IMatrixVisitor;

public abstract class AMatrix<T> implements IMatrix<T> {

	protected final IRing<T> coeffRing;

	protected int nbRows;
	protected int nbColumns;

	public AMatrix(IRing<T> coeffRing, int nbRows, int nbColumns) {
		this.coeffRing = coeffRing;
		this.nbRows = nbRows;
		this.nbColumns = nbColumns;
	}

	@Override
	public IRing<T> getCoeffRing() {
		return coeffRing;
	}

	@Override
	public int nbRows() {
		return nbRows;
	}

	@Override
	public int nbColumns() {
		return nbColumns;
	}

	@Override
	public void accept(IMatrixVisitor iMatrixVisitor) {
		for (int i = 0; i < nbRows(); i++)
			for (int j = 0; j < nbColumns(); j++)
				iMatrixVisitor.visitCell(i, j);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coeffRing == null) ? 0 : coeffRing.hashCode());

		final int[] resultAsArray = new int[] { result };
		this.accept(new IMatrixVisitor() {

			@Override
			public boolean visitCell(int rowIndex, int columnIndex) {
				T coeff = getValue(rowIndex, columnIndex);
				resultAsArray[0] = prime * resultAsArray[0] + (coeff == null ? 0 : coeff.hashCode());

				return true;
			}
		});

		return resultAsArray[0];
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		// if (getClass() != obj.getClass())
		// return false;
		final AMatrix<?> other = (AMatrix<?>) obj;
		if (coeffRing == null) {
			if (other.coeffRing != null)
				return false;
		} else if (!coeffRing.equals(other.coeffRing))
			return false;

		final boolean[] resultAsArray = new boolean[] { true };
		this.accept(new IMatrixVisitor() {

			@Override
			public boolean visitCell(int rowIndex, int columnIndex) {
				T coeff = getValue(rowIndex, columnIndex);

				if (coeff == null) {
					if (other.getValue(rowIndex, columnIndex) != null) {
						resultAsArray[0] = false;
						return false;
					}
				} else if (!coeff.equals(other.getValue(rowIndex, columnIndex))) {
					resultAsArray[0] = false;
					return false;
				}

				return true;
			}
		});

		return resultAsArray[0];
	}

}
