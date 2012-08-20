package bla.linearalgebra.performance.multiplication;
//
//import Jama.Matrix;
//
//public class TestJAMA {
//	public static void main(String[] args) {
//
//		Matrix A = new Matrix(new double[1000][1000]);
//		Matrix B = new Matrix(new double[1000][100]);
//		// start work
//		long start = System.currentTimeMillis();
//		A.times(B);
//		long elapsedTimeMillis = System.currentTimeMillis() - start;
//		// float elapsedTimeSec = elapsedTimeMillis / 1000F;
//		System.out.println("Elapsed time: " + elapsedTimeMillis);
//	}
//}