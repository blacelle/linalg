package bla.linearalgebra.matrix.displacemenyt;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.IMatrix;
import bla.linearalgebra.matrix.impl.AProxyMatrix;

public class ShiftDisplacementMatrix<T> extends AProxyMatrix<T> {
	protected final int rowDisplacement;
	protected final int columnDisplacement;

	/**
	 * 
	 * @param rowDisplacement
	 *            if positive, this will be shifted to the right. The first
	 *            columns will be filled with zeros
	 * @param columnDisplacement
	 *            if positive, this will be shifted to the bottom. The first
	 *            rows will be filled with zeros
	 */
	public ShiftDisplacementMatrix(IRing<T> coeffRing, IMatrix<T> parent, int rowDisplacement, int columnDisplacement) {
		super(coeffRing, parent);

		this.rowDisplacement = rowDisplacement;
		this.columnDisplacement = columnDisplacement;
	}

	@Override
	public T getValue(int rowIndex, int columnIndex) {
		if (rowIndex < rowDisplacement || columnIndex < columnDisplacement) {
			return getCoeffRing().zero();
		} else {
			return getParent().getValue(rowIndex - rowDisplacement, columnIndex - columnDisplacement);
		}
	}

}
