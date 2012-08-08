package bla.linearalgebra.matrix;

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
}
