package bla.linearalgebra.ring;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import bla.linearalgebra.IReducedRing;
import bla.linearalgebra.IRing;

public abstract class ATestRing<T> {
	public abstract IRing<T> makeRing();

	public abstract Iterable<T> getIteratable();

	public Iterator<T> getInfiniteIterator() {
		return new Iterator<T>() {
			protected Iterator<T> underlying = getIteratable().iterator();

			public boolean hasNext() {
				return true;
			}

			public T next() {
				if (!underlying.hasNext()) {
					underlying = getIteratable().iterator();
				}

				return underlying.next();
			}

			public void remove() {
				throw new RuntimeException("Can't remove");
			}
		};
	}

	@Test
	public void testOpposite() {
		IRing<T> ring = makeRing();

		for (T i : getIteratable()) {
			// 0 = a + (-a)
			Assert.assertEquals(ring.zero(), ring.add(i, ring.neg(i)));
			// 0 = a - a
			Assert.assertEquals(ring.zero(), ring.sub(i, i));
		}
	}

	@Test
	public void testZeroStability() {
		IRing<T> ring = makeRing();

		for (T i : getIteratable()) {
			T expected;
			if (ring instanceof IReducedRing<?>) {
				expected = ((IReducedRing<T>) ring).reduce(i);
			} else {
				expected = i;
			}
			// a = a + 0
			Assert.assertEquals(expected, ring.add(i, ring.zero()));
			// a = 0 + a
			Assert.assertEquals(expected, ring.add(ring.zero(), i));
			// a = a - 0
			Assert.assertEquals(expected, ring.sub(i, ring.zero()));
			// a = 0 - a
			Assert.assertEquals(ring.neg(expected), ring.sub(ring.zero(), i));
		}
	}

	@Test
	public void testOneStability() {
		IRing<T> ring = makeRing();

		for (T i : getIteratable()) {
			T expected;
			if (ring instanceof IReducedRing<?>) {
				expected = ((IReducedRing<T>) ring).reduce(i);
			} else {
				expected = i;
			}
			// a = 1 * a
			Assert.assertEquals(expected, ring.mul(i, ring.one()));
			// a = a * 1
			Assert.assertEquals(expected, ring.mul(ring.one(), i));
		}
	}

	@Test
	public void testMulDistributionOverAdd() {
		IRing<T> ring = makeRing();

		// a * (b+c) = a*b + a*c
		T a = null;
		T b = null;
		T c = null;
		for (T i : getIteratable()) {
			if (a == null) {
				a = i;
			} else if (b == null) {
				b = i;
			} else if (c == null) {
				c = i;
			} else {
				a = b;
				b = c;
				c = i;
			}

			if (c != null) {
				Assert.assertEquals(ring.mul(a, ring.add(b, c)), ring.add(ring.mul(a, b), (ring.mul(a, c))));
			}
		}
	}
}
