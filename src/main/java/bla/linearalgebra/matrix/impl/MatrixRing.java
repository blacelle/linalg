package bla.linearalgebra.matrix.impl;

import bla.linearalgebra.IRing;
import bla.linearalgebra.IRingWithUnderlyingRing;
import bla.linearalgebra.impl.GenericRing;
import bla.linearalgebra.matrix.IMatrix;
import bla.linearalgebra.matrix.structured.impl.DiagonalMatrix;

public class MatrixRing<T> extends GenericRing<IMatrix<T>> implements IRingWithUnderlyingRing<T, IMatrix<T>> {
	protected IRing<T> coeffRing;

	public MatrixRing(IMatrix<T> additionNeutral, IMatrix<T> multiplicationNeutral) {
		super(new MatrixAdditionOperation<T>(additionNeutral), new MatrixMultiplicationOperation<T>(multiplicationNeutral));

		coeffRing = additionNeutral.getCoeffRing();
	}

	@Override
	public IRing<T> getCoeffRing() {
		return coeffRing;
	}

	@Override
	public IMatrix<T> makeFromInt(int i) {
		return makeFromField(getCoeffRing().makeFromInt(i));
	}

	@Override
	public IMatrix<T> makeFromField(T i) {
		if (i == null) {
			return null;
		} else {
			return new DiagonalMatrix<T>(getCoeffRing(), i);
		}
	}

}
