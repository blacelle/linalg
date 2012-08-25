package bla.linearalgebra.matrix.displacement;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.IMatrix;
import bla.linearalgebra.matrix.impl.AProxyMatrix;

public class TransposedMatrix<T> extends AProxyMatrix<T> {

	public TransposedMatrix(IRing<T> coeffRing, IMatrix<T> parent) {
		super(coeffRing, parent, parent.nbColumns(), parent.nbRows());
	}

	@Override
	public T getValue(int rowIndex, int columnIndex) {
		return getParent().getValue(columnIndex, rowIndex);
	}

	@Override
	public void setValue(int rowIndex, int columnIndex, T value) {
		getParent().setValue(columnIndex, rowIndex, value);
	}

}
