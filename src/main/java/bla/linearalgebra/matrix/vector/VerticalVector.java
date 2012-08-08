package bla.linearalgebra.matrix.vector;

import java.util.ArrayList;
import java.util.List;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.impl.AMatrix;

public class VerticalVector<T> extends AMatrix<T> implements IVerticalVector<T> {
	protected List<T> underlying = new ArrayList<T>();

	public VerticalVector(IRing<T> coeffRing, int nbRows) {
		super(coeffRing, nbRows, 1);

		for (int i = 0; i < nbRows(); i++) {
			underlying.add(coeffRing.zero());
		}
	}

	@Override
	public void setValue(int rowIndex, int columnIndex, T value) {
		setValue(rowIndex, value);
	}

	@Override
	public T getValue(int rowIndex, int columnIndex) {
		return getValue(rowIndex);
	}

	@Override
	public void setValue(int rowIndex, T value) {
		underlying.set(rowIndex, value);
	}

	@Override
	public T getValue(int rowIndex) {
		return underlying.get(rowIndex);
	}
}
