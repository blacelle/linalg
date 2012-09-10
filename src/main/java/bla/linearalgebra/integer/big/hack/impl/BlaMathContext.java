/*
 * Copyright (c) 2003, 2007, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 * Portions Copyright IBM Corporation, 1997, 2001. All Rights Reserved.
 */

package bla.linearalgebra.integer.big.hack.impl;
import java.io.*;

/**
 * Immutable objects which encapsulate the context settings which
 * describe certain rules for numerical operators, such as those
 * implemented by the {@link BigDecimal} class.
 *
 * <p>The base-independent settings are:
 * <ol>
 * <li>{@code precision}:
 * the number of digits to be used for an operation; results are
 * rounded to this precision
 *
 * <li>{@code BlaRoundingMode}:
 * a {@link BlaRoundingMode} object which specifies the algorithm to be
 * used for rounding.
 * </ol>
 *
 * @see     BigDecimal
 * @see     BlaRoundingMode
 * @author  Mike Cowlishaw
 * @author  Joseph D. Darcy
 * @since 1.5
 */

public final class BlaMathContext implements Serializable {

    /* ----- Constants ----- */

    // defaults for constructors
    private static final int DEFAULT_DIGITS = 9;
    private static final BlaRoundingMode DEFAULT_BlaRoundingMode = BlaRoundingMode.HALF_UP;
    // Smallest values for digits (Maximum is Integer.MAX_VALUE)
    private static final int MIN_DIGITS = 0;

    // Serialization version
    private static final long serialVersionUID = 5579720004786848255L;

    /* ----- Public Properties ----- */
    /**
     *  A {@code BlaMathContext} object whose settings have the values
     *  required for unlimited precision arithmetic.
     *  The values of the settings are:
     *  <code>
     *  precision=0 BlaRoundingMode=HALF_UP
     *  </code>
     */
    public static final BlaMathContext UNLIMITED =
        new BlaMathContext(0, BlaRoundingMode.HALF_UP);

    /**
     *  A {@code BlaMathContext} object with a precision setting
     *  matching the IEEE 754R Decimal32 format, 7 digits, and a
     *  rounding mode of {@link BlaRoundingMode#HALF_EVEN HALF_EVEN}, the
     *  IEEE 754R default.
     */
    public static final BlaMathContext DECIMAL32 =
        new BlaMathContext(7, BlaRoundingMode.HALF_EVEN);

    /**
     *  A {@code BlaMathContext} object with a precision setting
     *  matching the IEEE 754R Decimal64 format, 16 digits, and a
     *  rounding mode of {@link BlaRoundingMode#HALF_EVEN HALF_EVEN}, the
     *  IEEE 754R default.
     */
    public static final BlaMathContext DECIMAL64 =
        new BlaMathContext(16, BlaRoundingMode.HALF_EVEN);

    /**
     *  A {@code BlaMathContext} object with a precision setting
     *  matching the IEEE 754R Decimal128 format, 34 digits, and a
     *  rounding mode of {@link BlaRoundingMode#HALF_EVEN HALF_EVEN}, the
     *  IEEE 754R default.
     */
    public static final BlaMathContext DECIMAL128 =
        new BlaMathContext(34, BlaRoundingMode.HALF_EVEN);

    /* ----- Shared Properties ----- */
    /**
     * The number of digits to be used for an operation.  A value of 0
     * indicates that unlimited precision (as many digits as are
     * required) will be used.  Note that leading zeros (in the
     * coefficient of a number) are never significant.
     *
     * <p>{@code precision} will always be non-negative.
     *
     * @serial
     */
    final int precision;

    /**
     * The rounding algorithm to be used for an operation.
     *
     * @see BlaRoundingMode
     * @serial
     */
    final BlaRoundingMode blaRoundingMode;

    /* ----- Constructors ----- */

    /**
     * Constructs a new {@code BlaMathContext} with the specified
     * precision and the {@link BlaRoundingMode#HALF_UP HALF_UP} rounding
     * mode.
     *
     * @param setPrecision The non-negative {@code int} precision setting.
     * @throws IllegalArgumentException if the {@code setPrecision} parameter is less
     *         than zero.
     */
    public BlaMathContext(int setPrecision) {
        this(setPrecision, DEFAULT_BlaRoundingMode);
        return;
    }

    /**
     * Constructs a new {@code BlaMathContext} with a specified
     * precision and rounding mode.
     *
     * @param setPrecision The non-negative {@code int} precision setting.
     * @param setBlaRoundingMode The rounding mode to use.
     * @throws IllegalArgumentException if the {@code setPrecision} parameter is less
     *         than zero.
     * @throws NullPointerException if the rounding mode argument is {@code null}
     */
    public BlaMathContext(int setPrecision,
                       BlaRoundingMode setBlaRoundingMode) {
        if (setPrecision < MIN_DIGITS)
            throw new IllegalArgumentException("Digits < 0");
        if (setBlaRoundingMode == null)
            throw new NullPointerException("null BlaRoundingMode");

        precision = setPrecision;
        blaRoundingMode = setBlaRoundingMode;
        return;
    }

    /**
     * Constructs a new {@code BlaMathContext} from a string.
     *
     * The string must be in the same format as that produced by the
     * {@link #toString} method.
     *
     * <p>An {@code IllegalArgumentException} is thrown if the precision
     * section of the string is out of range ({@code < 0}) or the string is
     * not in the format created by the {@link #toString} method.
     *
     * @param val The string to be parsed
     * @throws IllegalArgumentException if the precision section is out of range
     * or of incorrect format
     * @throws NullPointerException if the argument is {@code null}
     */
    public BlaMathContext(String val) {
        boolean bad = false;
        int setPrecision;
        if (val == null)
            throw new NullPointerException("null String");
        try { // any error here is a string format problem
            if (!val.startsWith("precision=")) throw new RuntimeException();
            int fence = val.indexOf(' ');    // could be -1
            int off = 10;                     // where value starts
            setPrecision = Integer.parseInt(val.substring(10, fence));

            if (!val.startsWith("BlaRoundingMode=", fence+1))
                throw new RuntimeException();
            off = fence + 1 + 13;
            String str = val.substring(off, val.length());
            blaRoundingMode = BlaRoundingMode.valueOf(str);
        } catch (RuntimeException re) {
            throw new IllegalArgumentException("bad string format");
        }

        if (setPrecision < MIN_DIGITS)
            throw new IllegalArgumentException("Digits < 0");
        // the other parameters cannot be invalid if we got here
        precision = setPrecision;
    }

    /**
     * Returns the {@code precision} setting.
     * This value is always non-negative.
     *
     * @return an {@code int} which is the value of the {@code precision}
     *         setting
     */
    public int getPrecision() {
        return precision;
    }

    /**
     * Returns the BlaRoundingMode setting.
     * This will be one of
     * {@link  BlaRoundingMode#CEILING},
     * {@link  BlaRoundingMode#DOWN},
     * {@link  BlaRoundingMode#FLOOR},
     * {@link  BlaRoundingMode#HALF_DOWN},
     * {@link  BlaRoundingMode#HALF_EVEN},
     * {@link  BlaRoundingMode#HALF_UP},
     * {@link  BlaRoundingMode#UNNECESSARY}, or
     * {@link  BlaRoundingMode#UP}.
     *
     * @return a {@code BlaRoundingMode} object which is the value of the
     *         {@code BlaRoundingMode} setting
     */

    public BlaRoundingMode getBlaRoundingMode() {
        return blaRoundingMode;
    }

    /**
     * Compares this {@code BlaMathContext} with the specified
     * {@code Object} for equality.
     *
     * @param  x {@code Object} to which this {@code BlaMathContext} is to
     *         be compared.
     * @return {@code true} if and only if the specified {@code Object} is
     *         a {@code BlaMathContext} object which has exactly the same
     *         settings as this object
     */
    public boolean equals(Object x){
        BlaMathContext mc;
        if (!(x instanceof BlaMathContext))
            return false;
        mc = (BlaMathContext) x;
        return mc.precision == this.precision
            && mc.blaRoundingMode == this.blaRoundingMode; // no need for .equals()
    }

    /**
     * Returns the hash code for this {@code BlaMathContext}.
     *
     * @return hash code for this {@code BlaMathContext}
     */
    public int hashCode() {
        return this.precision + blaRoundingMode.hashCode() * 59;
    }

    /**
     * Returns the string representation of this {@code BlaMathContext}.
     * The {@code String} returned represents the settings of the
     * {@code BlaMathContext} object as two space-delimited words
     * (separated by a single space character, <tt>'&#92;u0020'</tt>,
     * and with no leading or trailing white space), as follows:
     * <ol>
     * <li>
     * The string {@code "precision="}, immediately followed
     * by the value of the precision setting as a numeric string as if
     * generated by the {@link Integer#toString(int) Integer.toString}
     * method.
     *
     * <li>
     * The string {@code "BlaRoundingMode="}, immediately
     * followed by the value of the {@code BlaRoundingMode} setting as a
     * word.  This word will be the same as the name of the
     * corresponding public constant in the {@link BlaRoundingMode}
     * enum.
     * </ol>
     * <p>
     * For example:
     * <pre>
     * precision=9 BlaRoundingMode=HALF_UP
     * </pre>
     *
     * Additional words may be appended to the result of
     * {@code toString} in the future if more properties are added to
     * this class.
     *
     * @return a {@code String} representing the context settings
     */
    public java.lang.String toString() {
        return "precision=" +           precision + " " +
               "BlaRoundingMode=" +        blaRoundingMode.toString();
    }

    // Private methods

    /**
     * Reconstitute the {@code BlaMathContext} instance from a stream (that is,
     * deserialize it).
     *
     * @param s the stream being read.
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();     // read in all fields
        // validate possibly bad fields
        if (precision < MIN_DIGITS) {
            String message = "BlaMathContext: invalid digits in stream";
            throw new java.io.StreamCorruptedException(message);
        }
        if (blaRoundingMode == null) {
            String message = "BlaMathContext: null BlaRoundingMode in stream";
            throw new java.io.StreamCorruptedException(message);
        }
    }

}
