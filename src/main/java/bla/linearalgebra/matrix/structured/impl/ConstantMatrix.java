package bla.linearalgebra.matrix.structured.impl;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.structured.IConstantMatrix;

public class ConstantMatrix<T> extends AToeplitzMatrix<T> implements IConstantMatrix<T> {

	protected final T everywhereValue;

	public ConstantMatrix(IRing<T> coeffRing, int nbRows, int nbColumns, T everywhereValue) {
		super(coeffRing, nbRows, nbColumns);
		this.everywhereValue = everywhereValue;

	}

	@Override
	public T getValue(int rowIndex, int columnIndex) {
		return everywhereValue;
	}

	@Override
	public void setValue(int rowIndex, int columnIndex, T value) {
		throw new RuntimeException("Can not mutate a constant matrix");
	}

}
