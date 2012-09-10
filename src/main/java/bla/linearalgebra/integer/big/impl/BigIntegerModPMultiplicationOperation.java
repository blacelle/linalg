package bla.linearalgebra.integer.big.impl;

import java.math.BigInteger;

import bla.linearalgebra.IRing;
import bla.linearalgebra.IRingReducer;

public class BigIntegerModPMultiplicationOperation extends BigIntegerMultiplicationOperation implements IRingReducer<BigInteger> {

	protected BigInteger modP;

	public static final BigInteger IntegerMAX_VALUE = BigInteger.valueOf(Integer.MAX_VALUE);
	public static final BigInteger IntegerMIN_VALUE = BigInteger.valueOf(Integer.MIN_VALUE);

	public static final BigInteger LongMAX_VALUE = BigInteger.valueOf(Long.MAX_VALUE);
	public static final BigInteger LongMIN_VALUE = BigInteger.valueOf(Long.MIN_VALUE);

	public BigIntegerModPMultiplicationOperation(BigInteger modP) {
		this.modP = modP;

		if (modP.signum() <= 0) {
			throw new RuntimeException("Invalid modp: " + modP);
		}
	}

	public BigIntegerModPMultiplicationOperation(long modP) {
		this(BigInteger.valueOf(modP));
	}

	// TODO: a dedicated mulMod
	@Override
	public BigInteger add(BigInteger left, BigInteger right) {
		int modPAsInt = modP.intValue();
		if (modPAsInt < Integer.MAX_VALUE && left.compareTo(LongMAX_VALUE) <= 0 && right.compareTo(LongMAX_VALUE) <= 0 && left.compareTo(LongMIN_VALUE) >= 0
				&& right.compareTo(LongMIN_VALUE) >= 0) {

			// long output;
			// {
			// byte[] leftBytes = left.abs().toByteArray();
			//
			// if (leftBytes.length % 8 != 0) {
			// byte[] newLeftBytes = new byte[leftBytes.length + 8 -
			// leftBytes.length % 8];
			// Arrays.copyOf(leftBytes, leftBytes.length + 8 - leftBytes.length
			// % 8);
			// System.arraycopy(leftBytes, 0, newLeftBytes, 8 - leftBytes.length
			// % 8, leftBytes.length);
			// leftBytes = newLeftBytes;
			// }
			//
			// long leftAsArray = ByteBuffer.wrap(leftBytes).getLong();
			// if (left.signum() < 0) {
			// leftAsArray = -leftAsArray;
			// }
			//
			// byte[] rightBytes = right.abs().toByteArray();
			//
			// if (rightBytes.length % 8 != 0) {
			// byte[] newrightBytes = new byte[rightBytes.length + 8 -
			// rightBytes.length % 8];
			// Arrays.copyOf(rightBytes, rightBytes.length + 8 -
			// rightBytes.length % 8);
			// System.arraycopy(rightBytes, 0, newrightBytes, 8 -
			// rightBytes.length % 8, rightBytes.length);
			// rightBytes = newrightBytes;
			// }
			//
			// long rightAsArray = ByteBuffer.wrap(rightBytes).getLong();
			// if (right.signum() < 0) {
			// rightAsArray = -rightAsArray;
			// }
			//
			// output = (leftAsArray % modPAsInt) * (rightAsArray % modPAsInt);
			// }

			// {
			// return BigInteger.valueOf(output).remainder(modP);
			// }

			{
				BigInteger output = BigInteger.valueOf(((long) (left.longValue() % modPAsInt)) * ((long) (right.longValue() % modPAsInt)));

				// if (!output.remainder(modP).equals(super.add(left,
				// right).remainder(modP))) {
				// // re-run. NO ELSE STACKOVERFLOW
				// // this.add(left, right);
				//
				// output = BigInteger.valueOf(((long)
				// (left.remainder(modP).intValue() % modPAsInt))
				// * ((long) (right.remainder(modP).intValue() % modPAsInt)));
				// }
				//
				// assert output.remainder(modP).equals(super.add(left,
				// right).remainder(modP)) : output + " should have been "
				// + super.add(left, right).remainder(modP);

				return output;
			}
		} else {
			return super.add(left, right).remainder(modP);
		}
	}

	@Override
	public BigInteger reduce(BigInteger element) {
		return element.remainder(modP);
	}

	@Override
	public BigInteger opposite(BigInteger element) {
		try {
			return element.modInverse(modP);
		} catch (ArithmeticException e) {
			return null;
		}
	}

	@Override
	public BigInteger minus(BigInteger left, BigInteger right) {
		return left.divide(right);
	}

	public static void main(String[] args) {
		System.out.println(Integer.MAX_VALUE);
		System.out.println((long) Integer.MAX_VALUE * (long) Integer.MAX_VALUE);
		System.out.println(BigInteger.valueOf(Integer.MAX_VALUE));
		System.out.println(BigInteger.valueOf(Integer.MAX_VALUE).multiply(BigInteger.valueOf(Integer.MAX_VALUE)));
		System.out.println(Long.MAX_VALUE);
	}

	@Override
	public BigInteger findNthPrimitiveRootOfUnity(IRing<BigInteger> ring, int n) {
		return null;
	}
}
