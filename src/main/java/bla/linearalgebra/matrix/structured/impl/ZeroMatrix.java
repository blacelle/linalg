package bla.linearalgebra.matrix.structured.impl;

import bla.linearalgebra.IRing;

public class ZeroMatrix<T> extends ConstantMatrix<T> {

	public ZeroMatrix(IRing<T> coeffRing, int nbRows, int nbColumns) {
		super(coeffRing, nbRows, nbColumns, coeffRing.zero());
	}

}
