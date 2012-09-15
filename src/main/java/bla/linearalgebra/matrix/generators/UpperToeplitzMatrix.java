package bla.linearalgebra.matrix.generators;

import java.util.List;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.structured.impl.AToeplitzMatrix;

public class UpperToeplitzMatrix<T> extends AToeplitzMatrix<T> {
	protected List<T> valuesOnFirstColumn;

	public UpperToeplitzMatrix(IRing<T> coeffRing, int nbRows, List<T> valuesOnFirstColumn) {
		super(coeffRing, nbRows, valuesOnFirstColumn.size());

		this.valuesOnFirstColumn = valuesOnFirstColumn;
	}

	public UpperToeplitzMatrix(IRing<T> coeffRing, List<T> valuesOnFirstColumn) {
		super(coeffRing);

		this.valuesOnFirstColumn = valuesOnFirstColumn;
	}

	@Override
	protected T getValue(int i) {
		if (i <= 0) {
			return valuesOnFirstColumn.get(i);
		} else {
			return getCoeffRing().zero();
		}
	}

}
