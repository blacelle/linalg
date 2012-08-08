package bla.linearalgebra.polynomial;

/**
 * Holds a logic to be apply through a {@link IPolynomialFunction}
 */
public interface IPolynomialVisitor {

	/**
	 * The {@link IPolynomialVisitor} will be evaluated on each non-zero
	 * monomial of this
	 */
	boolean visitCoeff(int degree);
}
