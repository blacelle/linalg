package bla.linearalgebra.matrix;

import java.math.BigInteger;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.structured.IConstantMatrix;
import bla.linearalgebra.matrix.structured.IDiagonalMatrix;
import bla.linearalgebra.matrix.structured.impl.DiagonalMatrix;
import bla.linearalgebra.matrix.structured.impl.ZeroMatrix;

public class MatrixUtil {
	public static <T> IConstantMatrix<T> makeZero(IRing<T> coeffRing, int nbRows, int nbColumns) {
		return new ZeroMatrix<T>(coeffRing, nbRows, nbColumns);
	}

	public static <T> IDiagonalMatrix<T> makeOne(IRing<T> coeffRing, int nbRows, int nbColumns) {
		return new DiagonalMatrix<T>(coeffRing, nbRows, nbColumns, coeffRing.one());
	}

	public static <T> void swap(IMatrix<T> matrix, int rowIndex1, int columnIndex1, int rowIndex2, int columnIndex2) {
		T t = matrix.getValue(rowIndex1, columnIndex1);
		matrix.setValue(rowIndex1, columnIndex1, matrix.getValue(rowIndex2, columnIndex2));
		matrix.setValue(rowIndex2, columnIndex2, t);
	}

	public static <T> void checkMultiplicationCompatibility(IMatrix<T> left, IMatrix<T> right) {

	}

	public static <T> boolean equals(final IMatrix<T> expected, final IMatrix<T> toCheck) {
		if (expected.nbRows() != toCheck.nbRows()) {
			return false;
		} else if (expected.nbColumns() != toCheck.nbColumns()) {
			return false;
		} else {
			// OK ONLY IF EXPECTED WILL GO THROUGH ALL CELLS? WHICH IS NOT THE CASE FOR MATRIX LIKE IDiagoNAL
			return expected.accept(new IMatrixVisitor() {

				@Override
				public boolean visitCell(int rowIndex, int columnIndex) {
					if (expected.getCoeffRing().equals(expected.getValue(rowIndex, columnIndex), toCheck.getValue(rowIndex, columnIndex))) {
						return true;
					} else {
						return false;
					}
				}
			});
		}
	}
}
