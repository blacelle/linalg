package bla.linearalgebra.integer.impl;

public class IntegerUtils {

	public static int reduce(int valueToReduce, int modulo) {
		if (valueToReduce >= 0 && valueToReduce < modulo) {
			return valueToReduce;
		} else {
			valueToReduce = valueToReduce % modulo;
			if (valueToReduce < 0)
				valueToReduce += modulo;
			return valueToReduce;
		}
	}

	public static int inv(int valueToInverse, int modulo) {
		// I want to inverse 3 modulo 7. So I look for b such that
		// 3*b == 1 % 7

		// so I'm looking for b,c and d such that
		// 3*b+7*(3*c+d)=1

		// I begin by looking for d such that
		// 7*d=1+3e
//		int g = (modulo - (modulo / valueToInverse) * valueToInverse);
		return 0;
	}
}
