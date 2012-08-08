package bla.linearalgebra.performance.multiplication;

import org.jblas.DoubleMatrix;

public class TestJblas {
	public static void main(String[] args) {
		DoubleMatrix m = new DoubleMatrix(new double[1000][1000]);
		DoubleMatrix n = new DoubleMatrix(new double[1000][100]);
		DoubleMatrix k = new DoubleMatrix();
		// start work
		long start = System.currentTimeMillis();

		m.mmul(n);

		long elapsedTimeMillis = System.currentTimeMillis() - start;
		// float elapsedTimeSec = elapsedTimeMillis / 1000F;
		System.out.println("Elapsed time: " + elapsedTimeMillis);
		System.out.println(m.rows);
		System.out.println(m.columns);

		System.out.println(n.rows);
		System.out.println(n.columns);

		System.out.println(k.rows);
		System.out.println(k.columns);
	}
}