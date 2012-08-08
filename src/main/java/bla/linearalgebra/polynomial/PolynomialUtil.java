package bla.linearalgebra.polynomial;

import java.util.regex.Pattern;

import bla.linearalgebra.IRing;
import bla.linearalgebra.impl.RingUtil;

public class PolynomialUtil {
	public static final String ZERO_STRING = "0";

	public static final String toString(IPolynomialFunction<?> polynomial) {
		if (polynomial == null) {
			return null;
		} else {
			StringBuilder sb = new StringBuilder();

			// sb.append('[');

			boolean needPlus = false;
			boolean atLeastOneMonomial = false;
			for (int i = 0; i <= polynomial.getMaxPower(); i++) {
				Object coeff = polynomial.getCoedd(i);
				if (!coeff.equals(polynomial.getCoeffiRing().zero())) {
					if (needPlus) {
						sb.append("+");
						needPlus = false;
					}
					sb.append(coeff);
					sb.append(".");
					sb.append(i);
					needPlus = true;
					atLeastOneMonomial = true;
				}
			}

			if (!atLeastOneMonomial) {
				sb.append(ZERO_STRING);
			}

			// sb.append(']');

			return sb.toString();
		}
	}

	public static <T> IPolynomialFunction<T> fromString(IRing<IPolynomialFunction<T>> makeRing, String asString) {
		if (asString == null) {
			return null;
		} else if (ZERO_STRING.equals(asString)) {
			return makeRing.one();
		} else {
			String[] monomials = asString.split(Pattern.quote("+"));

			IPolynomialFunction<T> output = makeRing.zero();

			for (String monomial : monomials) {
				String[] splitIn2 = monomial.split(Pattern.quote("."));

				IPolynomialFunction<T> coeff = makeRing.makeFromInt(Integer.parseInt(splitIn2[0]));
				IPolynomialFunction<T> power = RingUtil.power(makeRing, makeRing.one().shift(1), Integer.parseInt(splitIn2[1]));

				output = makeRing.add(output, makeRing.mul(coeff, power));
			}

			return output;
		}
	}
}
