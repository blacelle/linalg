package bla.linearalgebra.matrix;

/**
 * Holds a logic to be apply through a {@link IMatrix}
 */
public interface IMatrixVisitor {

	boolean visitCell(int rowIndex, int columnIndex);

}
