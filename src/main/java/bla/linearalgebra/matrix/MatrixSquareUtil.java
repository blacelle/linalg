package bla.linearalgebra.matrix;

public class MatrixSquareUtil {

	public static int getSize(IMatrix<?> iA) {
		if (iA == null) {
			return 0;
		}

		int nbCols = iA.nbColumns();

		if (nbCols != iA.nbRows()) {
			throw new RuntimeException("Not Square");
		}

		return nbCols;
	}
}
