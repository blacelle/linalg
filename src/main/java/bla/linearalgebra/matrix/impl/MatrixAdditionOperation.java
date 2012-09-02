package bla.linearalgebra.matrix.impl;

import bla.linearalgebra.IInversableOperation;
import bla.linearalgebra.matrix.IMatrix;
import bla.linearalgebra.matrix.IMatrixVisitor;
import bla.linearalgebra.polynomial.impl.AInversableOperation;

public class MatrixAdditionOperation<T> extends AInversableOperation<IMatrix<T>> implements IInversableOperation<IMatrix<T>> {
	protected final IMatrix<T> neutralElement;

	public MatrixAdditionOperation(IMatrix<T> neutralElement) {
		this.neutralElement = neutralElement;
	}

	@Override
	public IMatrix<T> add(final IMatrix<T> left, final IMatrix<T> right) {
		if (left.nbRows() != right.nbRows() || left.nbColumns() != right.nbColumns()) {
			throw new RuntimeException("Incompatible sizes: (" + left.nbRows() + "," + left.nbColumns() + ")+(" + right.nbRows() + "," + right.nbColumns()
					+ ")");
		}

		if (!left.getCoeffRing().equals(right.getCoeffRing())) {
			throw new RuntimeException("Incompatible coeff rings");
		}

		final IMatrix<T> output = new DenseMatrix<T>(left.getCoeffRing(), left.nbRows(), left.nbColumns());

		output.accept(new IMatrixVisitor() {
			@Override
			public boolean visitCell(int rowIndex, int columnIndex) {
				output.setValue(rowIndex, columnIndex, left.getCoeffRing().add(left.getValue(rowIndex, columnIndex), right.getValue(rowIndex, columnIndex)));

				return true;
			}
		});

		return output;
	}

	@Override
	public IMatrix<T> getNeutralElement() {
		return neutralElement;
	}

	@Override
	public IMatrix<T> opposite(final IMatrix<T> element) {
		final IMatrix<T> output = new DenseMatrix<T>(element.getCoeffRing(), element.nbRows(), element.nbColumns());

		output.accept(new IMatrixVisitor() {
			@Override
			public boolean visitCell(int rowIndex, int columnIndex) {
				output.setValue(rowIndex, columnIndex, element.getCoeffRing().neg(element.getValue(rowIndex, columnIndex)));

				return true;
			}
		});

		return output;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((neutralElement == null) ? 0 : neutralElement.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatrixAdditionOperation<?> other = (MatrixAdditionOperation<?>) obj;
		if (neutralElement == null) {
			if (other.neutralElement != null)
				return false;
		} else if (!neutralElement.equals(other.neutralElement))
			return false;
		return true;
	}

	@Override
	public IMatrix<T> makeFromint(int i) {
		throw new RuntimeException("Can not make a IMatrix from an int");
	}

}
