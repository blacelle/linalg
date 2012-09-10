package bla.linearalgebra.matrix.structured.impl;

import bla.linearalgebra.matrix.IMatrix;
import bla.linearalgebra.matrix.impl.AProxyMatrix;
import bla.linearalgebra.matrix.impl.DenseMatrix;

public class CachedMatrix<T> extends AProxyMatrix<T> {
	protected IMatrix<T> cache;

	public CachedMatrix(IMatrix<T> parent) {
		super(parent);

		cache = new DenseMatrix<>(parent.getCoeffRing(), parent.nbRows(), parent.nbRows());
	}

	@Override
	public T getValue(int rowIndex, int columnIndex) {
		T inCache = cache.getValue(rowIndex, columnIndex);

		if (inCache == null) {
			T justComputed = getParent().getValue(rowIndex, columnIndex);
			cache.setValue(rowIndex, columnIndex, justComputed);
			return justComputed;
		} else {
			return inCache;
		}
	}

}
