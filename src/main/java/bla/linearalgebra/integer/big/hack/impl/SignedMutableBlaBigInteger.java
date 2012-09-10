/*
 * Copyright (c) 1999, 2007, Oracle and/or its affiliates. All rights reserved.
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

package bla.linearalgebra.integer.big.hack.impl;

/**
 * A class used to represent multiprecision integers that makes efficient
 * use of allocated space by allowing a number to occupy only part of
 * an array so that the arrays do not have to be reallocated as often.
 * When performing an operation with many iterations the array used to
 * hold a number is only increased when necessary and does not have to
 * be the same size as the number it represents. A mutable number allows
 * calculations to occur on the same number without having to create
 * a new number for every step of the calculation as occurs with
 * BigIntegers.
 *
 * Note that SignedMutableBlaBigIntegers only support signed addition and
 * subtraction. All other operations occur as with MutableBlaBigIntegers.
 *
 * @see     BigInteger
 * @author  Michael McCloskey
 * @since   1.3
 */

class SignedMutableBlaBigInteger extends MutableBlaBigInteger {

   /**
     * The sign of this MutableBlaBigInteger.
     */
    int sign = 1;

    // Constructors

    /**
     * The default constructor. An empty MutableBlaBigInteger is created with
     * a one word capacity.
     */
    SignedMutableBlaBigInteger() {
        super();
    }

    /**
     * Construct a new MutableBlaBigInteger with a magnitude specified by
     * the int val.
     */
    SignedMutableBlaBigInteger(int val) {
        super(val);
    }

    /**
     * Construct a new MutableBlaBigInteger with a magnitude equal to the
     * specified MutableBlaBigInteger.
     */
    SignedMutableBlaBigInteger(MutableBlaBigInteger val) {
        super(val);
    }

   // Arithmetic Operations

   /**
     * Signed addition built upon unsigned add and subtract.
     */
    void signedAdd(SignedMutableBlaBigInteger addend) {
        if (sign == addend.sign)
            add(addend);
        else
            sign = sign * subtract(addend);

    }

   /**
     * Signed addition built upon unsigned add and subtract.
     */
    void signedAdd(MutableBlaBigInteger addend) {
        if (sign == 1)
            add(addend);
        else
            sign = sign * subtract(addend);

    }

   /**
     * Signed subtraction built upon unsigned add and subtract.
     */
    void signedSubtract(SignedMutableBlaBigInteger addend) {
        if (sign == addend.sign)
            sign = sign * subtract(addend);
        else
            add(addend);

    }

   /**
     * Signed subtraction built upon unsigned add and subtract.
     */
    void signedSubtract(MutableBlaBigInteger addend) {
        if (sign == 1)
            sign = sign * subtract(addend);
        else
            add(addend);
        if (intLen == 0)
             sign = 1;
    }

    /**
     * Print out the first intLen ints of this MutableBlaBigInteger's value
     * array starting at offset.
     */
    public String toString() {
        return this.toBlaBigInteger(sign).toString();
    }

}
