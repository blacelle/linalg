package bla.linearalgebra.matrix.structured.impl;

import java.util.List;

import bla.linearalgebra.IRing;
import bla.linearalgebra.impl.RingUtil;
import bla.linearalgebra.matrix.impl.AMatrix;

public class VandermondeMatrix<T> extends AMatrix<T> {
	protected final List<T> coeffs;

	public VandermondeMatrix(IRing<T> coeffRing, List<T> coeffs, int nbColumns) {
		super(coeffRing, coeffs.size(), nbColumns);

		this.coeffs = coeffs;
	}

	public VandermondeMatrix(IRing<T> coeffRing, int[] asList, int nbColumns) {
		this(coeffRing, RingUtil.convertToRingElement(coeffRing, asList), nbColumns);
	}

	@Override
	public T getValue(int rowIndex, int columnIndex) {
		return RingUtil.power(coeffRing, coeffs.get(rowIndex), columnIndex);
	}

	@Override
	public void setValue(int rowIndex, int columnIndex, T value) {
		throw new RuntimeException("Can not mutate a VandermondeMatrix");
	}

}
