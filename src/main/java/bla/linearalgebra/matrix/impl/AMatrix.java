package bla.linearalgebra.matrix.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import bla.linearalgebra.IRing;
import bla.linearalgebra.matrix.IMatrix;
import bla.linearalgebra.matrix.IMatrixVisitor;

public abstract class AMatrix<T> implements IMatrix<T> {

	protected final IRing<T> coeffRing;

	protected int nbRows;
	protected int nbColumns;

	public AMatrix(IRing<T> coeffRing, int nbRows, int nbColumns) {
		this.coeffRing = coeffRing;
		this.nbRows = nbRows;
		this.nbColumns = nbColumns;
	}

	@Override
	public IRing<T> getCoeffRing() {
		return coeffRing;
	}

	@Override
	public int nbRows() {
		return nbRows;
	}

	@Override
	public int nbColumns() {
		return nbColumns;
	}

	@Override
	public boolean accept(final IMatrixVisitor iMatrixVisitor) {
		if (iMatrixVisitor instanceof IParallelMatrixVisitor && ForkJoinTask.inForkJoinPool()) {
			final AtomicBoolean cancel = new AtomicBoolean(false);

			RecursiveAction ra = new RecursiveAction() {
				private static final long serialVersionUID = -7768555256688964902L;

				@Override
				protected void compute() {
					List<RecursiveAction> tasks = new ArrayList<>();

					for (int i = 0; i < nbRows(); i++) {
						final int ii = i;
						for (int j = 0; j < nbColumns(); j++) {
							final int jj = j;
							tasks.add(new RecursiveAction() {
								private static final long serialVersionUID = 1L;

								@Override
								protected void compute() {
									if (!iMatrixVisitor.visitCell(ii, jj)) {
										this.cancel(true);
									}
								}
							});
						}
					}

					ForkJoinTask.invokeAll(tasks);
				}
			};

			ra.invoke();
		} else {
			for (int i = 0; i < nbRows(); i++) {
				for (int j = 0; j < nbColumns(); j++) {
					if (!iMatrixVisitor.visitCell(i, j)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coeffRing == null) ? 0 : coeffRing.hashCode());

		final int[] resultAsArray = new int[] { result };
		this.accept(new IMatrixVisitor() {

			@Override
			public boolean visitCell(int rowIndex, int columnIndex) {
				T coeff = getValue(rowIndex, columnIndex);
				resultAsArray[0] = prime * resultAsArray[0] + (coeff == null ? 0 : coeff.hashCode());

				return true;
			}
		});

		return resultAsArray[0];
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		// if (getClass() != obj.getClass())
		// return false;
		final AMatrix<?> other = (AMatrix<?>) obj;
		if (coeffRing == null) {
			if (other.coeffRing != null)
				return false;
		} else if (!coeffRing.equals(other.coeffRing))
			return false;

		final boolean[] resultAsArray = new boolean[] { true };
		this.accept(new IMatrixVisitor() {

			@Override
			public boolean visitCell(int rowIndex, int columnIndex) {
				T coeff = getValue(rowIndex, columnIndex);

				if (coeff == null) {
					if (other.getValue(rowIndex, columnIndex) != null) {
						resultAsArray[0] = false;
						return false;
					}
				} else if (!coeff.equals(other.getValue(rowIndex, columnIndex))) {
					resultAsArray[0] = false;
					return false;
				}

				return true;
			}
		});

		return resultAsArray[0];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < nbRows(); i++) {
			sb.append(i);
			sb.append(": ");
			for (int j = 0; j < nbColumns(); j++) {
				sb.append(getValue(i, j));
				sb.append(" ");
			}
			sb.append('\n');
		}

		return sb.toString();
	}
}
