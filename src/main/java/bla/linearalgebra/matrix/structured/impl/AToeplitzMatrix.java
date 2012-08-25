package bla.linearalgebra.matrix.structured.impl;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.impl.AMatrix;
import bla.linearalgebra.matrix.structured.IToeplitzMatrix;

public abstract class AToeplitzMatrix<T> extends AMatrix<T> implements IToeplitzMatrix<T> {

	public AToeplitzMatrix(IRing<T> coeffRing, int nbRows, int nbColumns) {
		super(coeffRing, nbRows, nbColumns);
	}

	public AToeplitzMatrix(IRing<T> coeffRing) {
		super(coeffRing, -1, -1);
	}

}
