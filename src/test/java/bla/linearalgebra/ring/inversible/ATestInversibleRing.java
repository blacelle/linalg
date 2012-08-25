package bla.linearalgebra.ring.inversible;

import org.junit.Assert;
import org.junit.Test;

import bla.linearalgebra.IReducedRing;
import bla.linearalgebra.IRing;
import bla.linearalgebra.ring.ATestRing;

public abstract class ATestInversibleRing<T> extends ATestRing<T> {
	@Test
	public void testInversability() {
		IRing<T> ring = makeRing();

		for (T i : getIteratable()) {
			T expected;
			if (ring instanceof IReducedRing<?>) {
				expected = ((IReducedRing<T>) ring).reduce(i);
			} else {
				expected = i;
			}
			// null != a^-1
			Assert.assertFalse(null == ring.inv(expected));
			// 1 = a * a^-1
			Assert.assertEquals(ring.one(), ring.mul(expected, ring.inv(expected)));
			// 1 = a^-1 * a
			Assert.assertEquals(ring.one(), ring.mul(ring.inv(expected), expected));
			// a= (a^-1)^-1
			Assert.assertEquals(expected, ring.inv(ring.inv(expected)));
		}
	}
}
