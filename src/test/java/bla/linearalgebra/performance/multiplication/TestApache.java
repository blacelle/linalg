package bla.linearalgebra.performance.multiplication;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;

public class TestApache {
	public static void main(String[] args) {

		RealMatrix m = new Array2DRowRealMatrix(new double[1000][1000]);

		RealMatrix n = new Array2DRowRealMatrix(new double[1000][100]);

		// Note: The constructor copies the input double[][] array.

		// Now multiply m by n
		// start work
		long start = System.currentTimeMillis();
		m.multiply(n);
		long elapsedTimeMillis = System.currentTimeMillis() - start;
		// float elapsedTimeSec = elapsedTimeMillis / 1000F;
		System.out.println("Elapsed time: " + elapsedTimeMillis);
	}
}