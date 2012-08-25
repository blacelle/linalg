//package bla.linearalgebra.field;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import bla.linearalgebra.ATestRing;
//import bla.linearalgebra.IReducedRing;
//
//public abstract class ATestField<T> extends ATestRing<T> {
//	public abstract IField<T> makeRing();
//
//	@Test
//	public void testFieldOneStability() {
//		IField<T> ring = makeRing();
//
//		for (T i : getIteratable()) {
//			T expected;
//			if (ring instanceof IReducedRing<?>) {
//				expected = ((IReducedRing<T>) ring).reduce(i);
//			} else {
//				expected = i;
//			}
//			Assert.assertEquals(expected, ring.div(i, ring.one()));
//			Assert.assertEquals(ring.inv(expected), ring.div(ring.one(), i));
//		}
//	}
//}
