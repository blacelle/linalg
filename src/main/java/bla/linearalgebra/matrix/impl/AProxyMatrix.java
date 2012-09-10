package bla.linearalgebra.matrix.impl;

import bla.linearalgebra.matrix.IMatrix;

public abstract class AProxyMatrix<T> extends AMatrix<T> {
	protected final IMatrix<T> parent;

	public AProxyMatrix(IMatrix<T> parent) {
		this(parent, parent.nbRows(), parent.nbColumns());
	}

	public AProxyMatrix(IMatrix<T> parent, int nbRows, int nbColumns) {
		super(parent.getCoeffRing(), nbRows, nbColumns);

		this.parent = parent;
	}

	@Override
	public void setValue(int rowIndex, int columnIndex, T value) {
		throw new RuntimeException("Can not setValue on a proxyMatrix: " + this.getClass());
	}

	public IMatrix<T> getParent() {
		return parent;
	}
}
