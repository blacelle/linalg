package bla.linearalgebra.matrix.generators;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.IMatrix;
import bla.linearalgebra.matrix.MatrixUtil;
import bla.linearalgebra.matrix.impl.AMatrix;
import bla.linearalgebra.matrix.impl.DenseMatrix;
import bla.linearalgebra.matrix.impl.FourSquareMatrix;
import bla.linearalgebra.matrix.structured.impl.ZeroMatrix;

public class SteinPlusGeneratedMatrix<T> extends AMatrix<T> {

	protected IMatrix<T> y;
	protected IMatrix<T> z;

	protected int nbGenerators;

	public SteinPlusGeneratedMatrix(IRing<T> coeffRing, int nbRows, int nbColumns) {
		super(coeffRing, nbRows, nbColumns);

		this.nbGenerators = 0;

		y = new ZeroMatrix<T>(coeffRing, nbRows, 0);
		z = new ZeroMatrix<T>(coeffRing, 0, nbColumns);
	}

	@Override
	public T getValue(int rowIndex, int columnIndex) {
		throw new RuntimeException("Can not get a " + this.getClass() + " value");
	}

	@Override
	public void setValue(int rowIndex, int columnIndex, T value) {
		throw new RuntimeException("Can not set a " + this.getClass() + " value");
	}

	public IMatrix<T> getY() {
		return y;
	}

	public IMatrix<T> getZ() {
		return z;
	}

	public SteinPlusGeneratedMatrix<T> mulWithGenerated(IRing<IMatrix<T>> ring, SteinPlusGeneratedMatrix<T> right) {
		MatrixUtil.checkMultiplicationCompatibility(this, right);

		SteinPlusGeneratedMatrix<T> output = new SteinPlusGeneratedMatrix<T>(this.getCoeffRing(), nbRows(), right.nbColumns());

		output.pushGenerators(this.getY(), right.mulMatrixWithThis(ring, this.getZ()));

		// TODO: more to push

		return output;
	}

	protected void pushGenerators(IMatrix<T> y2, IMatrix<T> z2) {
		this.nbGenerators += y2.nbColumns();

		y = new FourSquareMatrix<>(y, y2, new ZeroMatrix<>(getCoeffRing(), 0, y.nbRows()), new ZeroMatrix<>(getCoeffRing(), 0, y2.nbRows()));
		z = new FourSquareMatrix<>(z, new ZeroMatrix<>(getCoeffRing(), z.nbColumns(), 0), z2, new ZeroMatrix<>(getCoeffRing(), z2.nbColumns(), 0));
	}

	public IMatrix<T> mulMatrixWithThis(IRing<IMatrix<T>> ring, IMatrix<T> right) {
		return new DenseMatrix<T>(this.getCoeffRing(), nbRows(), nbColumns());
	}
}
