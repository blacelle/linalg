package bla.linearalgebra.matrix.structured;

import bla.linearalgebra.matrix.IMatrix;

/**
 * A Matrix which is constant along its main diagonal.
 * 
 * For instance, the following is a Toeplitz matrix
 * 3 4 5
 * 2 3 4
 * 1 2 3
 */
public interface IToeplitzMatrix<T> extends IMatrix<T> {

}
