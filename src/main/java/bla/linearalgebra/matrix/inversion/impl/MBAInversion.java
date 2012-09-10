package bla.linearalgebra.matrix.inversion.impl;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.IMatrix;
import bla.linearalgebra.matrix.MatrixSquareUtil;
import bla.linearalgebra.matrix.impl.FourSquareMatrix;
import bla.linearalgebra.matrix.impl.SubMatrix;
import bla.linearalgebra.matrix.structured.impl.DiagonalMatrix;

public class MBAInversion<T> implements IMatrixInvertion<T> {
	protected int problemSize = -1;
	protected int problemDone = -1;

	public static int getTopLeftSize(int fullSize) {
		// TODO return the highest power of 2, strictly lower than fullSize
		return fullSize / 2;
	}

	public static final <T> IMatrix<T> doInverseSize1Matrix(IMatrix<T> iA) {
		if (MatrixSquareUtil.getSize(iA) != 1) {
			throw new RuntimeException("Expected a matrix of size 1");
		}

		T inverse = iA.getCoeffRing().inv(iA.getValue(0, 0));

		if (inverse == null) {
			return null;
		}

		return new DiagonalMatrix<T>(iA.getCoeffRing(), 1, 1, inverse);
	}

	public IMatrix<T> doInverse(final IRing<IMatrix<T>> matrixRing, IMatrix<T> iA) {

		int sizeA = MatrixSquareUtil.getSize(iA);

		if (problemSize < 0) {
			problemSize = sizeA;
			problemDone = 0;
		}

		if (sizeA == 1) {
			return doInverseSize1Matrix(iA);
		}

		// TODO: garder un seul des 2, A est carree
		int sizeA11 = getTopLeftSize(sizeA);

		// Extract A11
		final IMatrix<T> A11 = new SubMatrix<T>(iA, 0, sizeA11, 0, sizeA11);

		// ----------------------------------
		// STEP 1: inverse recursively on A11
		// ----------------------------------

		RecursiveTask<IMatrix<T>> A11InvTask = new RecursiveTask<IMatrix<T>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected IMatrix<T> compute() {
				return doInverse(matrixRing, A11);
			}
		};

		A11InvTask.fork();

		final IMatrix<T> A11Inv = A11InvTask.join();

//		if (problemDone < sizeA11) {
//			int currentSize = problemSize;
//
//			for (int i = 0; i < 16; i++) {
//				currentSize = getTopLeftSize(currentSize);
//
//				if (sizeA11 == currentSize) {
//					problemDone = sizeA11 / 3;
//					System.out.println((100D * problemDone) / problemSize);
//				}
//			}
//		}

		if (MatrixSquareUtil.getSize(A11Inv) < sizeA11) {
			// A11 is singular, we return the inverse of the biggest up-left
			// inversible submatrix
			return A11Inv;
		}

		// -----------------------------------
		// Step2: compute the Schur complement
		// -----------------------------------
		IMatrix<T> A21Minus = matrixRing.neg(new SubMatrix<T>(iA, sizeA11, sizeA, 0, sizeA11));
		//
		IMatrix<T> MinusA21A11Inv = matrixRing.mul(A21Minus, A11Inv);
		//
		// if (SLA_SUPPLEMENTARY_REDUCTIONS)
		// {
		// //TODO: find a better bound on the displacement rank of that block
		// //because it seems to be always quite small
		//
		// //Reduction (MinusA21A11Inv, -1, full_random);
		// Reduction (MinusA21A11Inv);
		// }
		//
		// MinusA21A11Inv.negate();

		IMatrix<T> A12 = new SubMatrix<T>(iA, 0, sizeA11, sizeA11, sizeA);
		IMatrix<T> MinusA21A11A12 = matrixRing.mul(MinusA21A11Inv, A12);

		IMatrix<T> Delta = new SubMatrix<T>(iA, sizeA11, sizeA, sizeA11, sizeA);
		Delta = matrixRing.add(Delta, MinusA21A11A12);
		//
		// //The displacement rank of Delta is at most the one of A
		// //Reduction (Delta, iA.length(), full_random);
		// Reduction (Delta, iA.length());
		//
		// -----------------------------------------------
		// Step3: call recursively on the Schur complement
		// -----------------------------------------------
		final IMatrix<T> DeltaInv = doInverse(matrixRing, Delta);

//		if (problemDone < sizeA11) {
//			int currentSize = problemSize;
//
//			for (int i = 0; i < 16; i++) {
//				currentSize = getTopLeftSize(currentSize);
//
//				if (sizeA11 == currentSize) {
//					problemDone = (2 * sizeA11) / 3;
//					System.out.println((100D * problemDone) / problemSize);
//				}
//			}
//		}

		int sizeDeltaInv = MatrixSquareUtil.getSize(DeltaInv);

		if (sizeDeltaInv == 0) {
			// A11 is the biggest up-left inversible submatrix
			return A11Inv;
		}

		// ---------------------------------
		// Step4: recompose the inverse a Ar
		// ---------------------------------
		int sizeAr = sizeA11 + sizeDeltaInv;
		//
		final IMatrix<T> MinusA21rrA11Inv;
		final IMatrix<T> A12rr;
		//
		if (sizeAr < sizeA) {
			MinusA21rrA11Inv = new SubMatrix<T>(MinusA21A11Inv, 0, sizeDeltaInv, 0, sizeA11);

			// if (SLA_SUPPLEMENTARY_REDUCTIONS)
			// {
			// //Reduction (MinusA21rrA11Inv, -1, full_random);
			// Reduction (MinusA21rrA11Inv);
			// }

			A12rr = new SubMatrix<T>(A12, 0, sizeA11, 0, sizeDeltaInv);
		} else {
			MinusA21rrA11Inv = MinusA21A11Inv;
			A12rr = A12;
		}

		RecursiveTask<IMatrix<T>> A11InvA12rrTask = new RecursiveTask<IMatrix<T>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected IMatrix<T> compute() {
				return matrixRing.mul(A11Inv, A12rr);
			}
		};
//		A11InvA12rrTask.fork();

		RecursiveTask<IMatrix<T>> DeltaInvMinusA21rrA11InvTask = new RecursiveTask<IMatrix<T>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected IMatrix<T> compute() {
				return matrixRing.mul(DeltaInv, MinusA21rrA11Inv);
			}
		};
//		DeltaInvMinusA21rrA11InvTask.fork();
		
		ForkJoinTask.invokeAll(A11InvA12rrTask, DeltaInvMinusA21rrA11InvTask);

		IMatrix<T> B12 = matrixRing.mul(A11InvA12rrTask.join(), DeltaInv);

		// if (SLA_SUPPLEMENTARY_REDUCTIONS)
		// {
		// Reduction (B12, iA.length()+1, full_random);
		// Reduction (B12, iA.length()+1);
		// }

		B12 = matrixRing.neg(B12);

		IMatrix<T> B21 = DeltaInvMinusA21rrA11InvTask.join();
		IMatrix<T> B11 = matrixRing.add(matrixRing.mul(B12, MinusA21rrA11Inv), A11Inv);

		// if (!SLA_LESS_MULTIPLICATIONS)
		// {
		// We redo the spared multiplications just for time benchs
		// IMatrix<T> BIS_MinusA21A11Inv = A21Minus*A11Inv;
		// IMatrix<T> TER_MinusA21A11Inv = A21Minus*A11Inv;
		// IMatrix<T> BIS_B12 = (A11Inv * A12rr) * DeltaInv;
		// }

		IMatrix<T> oAInv = new FourSquareMatrix<T>(B11, B12, B21, DeltaInv);

		// The displacement rank of PhiMinus(AInv) is equals to the one of
		// PhiPlus(A)
		// Reduction (oAInv, iA.length(), full_random);
		// Reduction(oAInv, iA.length());

		return oAInv;
	}
}
