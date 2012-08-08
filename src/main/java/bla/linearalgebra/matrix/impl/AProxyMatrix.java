package bla.linearalgebra.matrix.impl;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.IMatrix;

public abstract class AProxyMatrix<T> extends AMatrix<T> {
	protected final IMatrix<T> parent;

	public AProxyMatrix(IRing<T> coeffRing, IMatrix<T> parent) {
		this(coeffRing, parent, parent.nbRows(), parent.nbColumns());
	}

	public AProxyMatrix(IRing<T> coeffRing, IMatrix<T> parent, int nbRows, int nbColumns) {
		super(coeffRing, nbRows, nbColumns);

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
