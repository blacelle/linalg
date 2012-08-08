package bla.linearalgebra;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;


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
			Assert.assertEquals(ring.zero(), ring.add(i, ring.neg(i)));
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
			Assert.assertEquals(expected, ring.add(i, ring.zero()));
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
