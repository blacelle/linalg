package bla.linearalgebra.matrix.generators;

import java.util.List;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.structured.impl.AToeplitzMatrix;

public class LowerToeplitzMatrix<T> extends AToeplitzMatrix<T> {
	protected List<T> valuesOnFirstRow;

	public LowerToeplitzMatrix(IRing<T> coeffRing, List<T> valuesOnFirstRow, int nbColumns) {
		super(coeffRing, valuesOnFirstRow.size(), nbColumns);

		this.valuesOnFirstRow = valuesOnFirstRow;
	}

	public LowerToeplitzMatrix(IRing<T> coeffRing, List<T> valuesOnFirstRow) {
		super(coeffRing);

		this.valuesOnFirstRow = valuesOnFirstRow;
	}

	@Override
	protected T getValue(int i) {
		if (i >= 0) {
			return valuesOnFirstRow.get(i);
		} else {
			return getCoeffRing().zero();
		}
	}

}
