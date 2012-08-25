package bla.linearalgebra.matrix.inversion.impl;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.IMatrix;
import bla.linearalgebra.matrix.impl.FourSquareMatrix;
import bla.linearalgebra.matrix.impl.SubMatrix;
import bla.linearalgebra.matrix.structured.impl.DiagonalMatrix;

public class MBAInversion {
	public static int getSize(IMatrix<?> iA) {
		int nbCols = iA.nbColumns();

		if (nbCols != iA.nbRows()) {
			throw new RuntimeException("Not Square");
		}

		return nbCols;
	}

	public static int getTopLeftSize(int fullSize) {
		// TODO return the highest power of 2, strictly loiwer than fullSize
		return fullSize / 2;
	}

	public static final <T> IMatrix<T> doInverseSize1Matrix(IMatrix<T> iA) {
		if (getSize(iA) != 1) {
			throw new RuntimeException("Expected a matrix of size 1");
		}
		return new DiagonalMatrix<T>(iA.getCoeffRing(), 1, 1, iA.getValue(0, 0));
	}

	public static <T> IMatrix<T> doInverse(IRing<IMatrix<T>> matrixRing, IMatrix<T> iA) {
		int sizeA = getSize(iA);

		if (sizeA == 1) {
			return doInverseSize1Matrix(iA);
		}

		// TODO: garder un seul des 2, A est carree
		int sizeA11 = getTopLeftSize(sizeA);

		// Extract A11
		IMatrix<T> A11 = new SubMatrix<T>(iA.getCoeffRing(), iA, 0, sizeA11, 0, sizeA11);

		// ----------------------------------
		// STEP 1: inverse recursively on A11
		// ----------------------------------
		IMatrix<T> A11Inv = doInverse(matrixRing, A11);

		if (getSize(A11Inv) < sizeA11) {
			// A11 is singular, we return the inverse of the biggest up-left
			// inversible submatrix
			return A11Inv;
		}

		// -----------------------------------
		// Step2: compute the Schur complement
		// -----------------------------------
		IMatrix<T> A21Minus = matrixRing.mul(matrixRing.makeFromInt(-1), new SubMatrix<T>(iA.getCoeffRing(), iA, sizeA11, sizeA, 0, sizeA11));
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

		IMatrix<T> A12 = new SubMatrix<T>(iA.getCoeffRing(), iA, 0, sizeA11, sizeA11, sizeA);
		IMatrix<T> MinusA21A11A12 = matrixRing.mul(MinusA21A11Inv, A12);

		IMatrix<T> Delta = new SubMatrix<T>(iA.getCoeffRing(), iA, sizeA11, sizeA11, sizeA, sizeA);
		Delta = matrixRing.add(Delta, MinusA21A11A12);
		//
		// //The displacement rank of Delta is at most the one of A
		// //Reduction (Delta, iA.length(), full_random);
		// Reduction (Delta, iA.length());
		//
		// -----------------------------------------------
		// Step3: call recursively on the Schur complement
		// -----------------------------------------------
		IMatrix<T> DeltaInv = doInverse(matrixRing, Delta);

		int sizeDeltaInv = getSize(DeltaInv);

		if (sizeDeltaInv == 0) {
			// A11 is the biggest up-left inversible submatrix
			return A11Inv;
		}

		// ---------------------------------
		// Step4: recompose the inverse a Ar
		// ---------------------------------
		int sizeAr = sizeA11 + sizeDeltaInv;
		//
		IMatrix<T> MinusA21rrA11Inv;
		IMatrix<T> A12rr;
		//
		if (sizeAr < sizeA) {
			MinusA21rrA11Inv = new SubMatrix<T>(iA.getCoeffRing(), MinusA21A11Inv, 0, sizeDeltaInv, 0, sizeA11);

			// if (SLA_SUPPLEMENTARY_REDUCTIONS)
			// {
			// //Reduction (MinusA21rrA11Inv, -1, full_random);
			// Reduction (MinusA21rrA11Inv);
			// }

			A12rr = new SubMatrix<T>(iA.getCoeffRing(), A12, 0, sizeA11, 0, sizeDeltaInv);
		} else {
			MinusA21rrA11Inv = MinusA21A11Inv;
			A12rr = A12;
		}

		IMatrix<T> B12 = matrixRing.mul(matrixRing.mul(A11Inv, A12rr), DeltaInv);

		// if (SLA_SUPPLEMENTARY_REDUCTIONS)
		// {
		// Reduction (B12, iA.length()+1, full_random);
		// Reduction (B12, iA.length()+1);
		// }

		B12 = matrixRing.mul(matrixRing.makeFromInt(-1), B12);

		IMatrix<T> B21 = matrixRing.mul(DeltaInv, MinusA21rrA11Inv);
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
