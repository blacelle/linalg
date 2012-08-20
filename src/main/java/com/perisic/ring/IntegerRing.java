package com.perisic.ring;
import com.perisic.ring.*;
import java.math.*;
/**
* The ring of Integers. In essential this class wraps the java.math.Biginteger
* class. <P>
* Note that this class has no public constructor. The only public instance 
* of this class is Ring.Z. <P>
*
* Example of use: 
* <UL> 
*<pre>
* import com.perisic.ring.*;
* import java.math.*;
* class IntegerRingExample { 
* public static void main(String [] args) { 
*    RingElt a = Ring.Z.map("3");
*    RingElt b = Ring.Z.map( new BigInteger("1000000"));
*    a = Ring.Z.add(a,b);
*    System.out.println("a = "+a); 
*    }
*}
*</pre>
*writes "a = 1000003" to the output.
* <UL>
* <li> Last Change: 13.12.2003: GPL, MC. 
*<li>
* Copyright:(c) Marc Conrad, 2002, 2003 
*<li>
* Email: <a href="mailto:ring@perisic.com">ring@perisic.com</a>. 
* Please let me know if you use this software.
*<li>
* WWW: www.ring.perisic.com
*<li>The com.perisic.ring library is distributed under the terms of the 
* <a href="http://www.gnu.org/copyleft/lesser.html">GNU Lesser General Public License (LGPL)</a>. 
*<li> If you require the package under a different licence please contact me.
*<li> disclaimer: The classes are provided "as is".
* There is no warranty implied by using the com.perisic.ring package.
* </UL>
* @author Marc Conrad
* @version 0.2
*/
/*
Last Change 25.04.2002: Introduced BigIntegerZERO and BigIntegerONE to 
replace BigInteger.ZERO and BigInteger.ONE
*/
public class IntegerRing extends Ring {

    static final IntegerRing Z = new IntegerRing(); 
    static final BigInteger BigIntegerZERO = new BigInteger("0");
    static final BigInteger BigIntegerONE = new BigInteger("1");
    
    private IntegerRing() {
           super();
           }
/**
* Returns "Z".
* @return "Z".
*/
    public String toString() { return "Z"; }
/**
* Returns true as Z is an Euclidian ring.
* @return true.
*/
    public boolean isEuclidian() { 
          return true; 
          }
 
/**
* Returns the sum of the parameters.
* @param a a RingElt which can be mapped to an element of this ring.
* @param b a RingElt which can be mapped to an element of this ring.
* @return a + b.
*/
    public RingElt add( RingElt a, RingElt b) {
         a = map(a); b = map(b);
         return new IntegerRingElt( 
            ((IntegerRingElt) a).getValue().add( 
                          ((IntegerRingElt) b).getValue()) 
                              );
    
         }

/**
* Returns the product of the parameters.
* @param a a RingElt which can be mapped to an element of this ring.
* @param b a RingElt which can be mapped to an element of this ring.
* @return a * b.
*/
    public RingElt mult( RingElt a, RingElt b) {
         a = map(a); b = map(b);
         return new IntegerRingElt( 
            ((IntegerRingElt) a).getValue().multiply( 
                          ((IntegerRingElt) b).getValue()) 
                              );
    
         }
/**
* Returns b for b == 1 and b == -1.
* Otherwise throws a RingException.
* @param b a RingElt which can be mapped to an element of this ring.
* @throws RingException if b is neither 1 or -1.
* @return b^-1.
*/
    public RingElt inv(RingElt b)  throws RingException { 
             b = map(b);
             if( equal(b,neg(one())) ) { return b;}
             return super.inv(b);
            }

/**
* Euclidian division. Performs division with remainder.
* @param a a RingElt which can be mapped to an element of this ring.
* @param b a RingElt which can be mapped to an element of this ring.
* @return a/b (Euclidian division).
* @see BigInteger#divide
*/
    public RingElt ediv( RingElt a, RingElt b) {
         a = map(a);   b = map(b);
         return new IntegerRingElt( 
            ((IntegerRingElt) a).getValue().divide( 
                          ((IntegerRingElt) b).getValue()) 
                              );
    
         }
/**
* True division. If the division cannot be performed without remainder
* a RingException is thrown.
* @param a a RingElt which can be mapped to an element of this ring.
* @param b a RingElt which can be mapped to an element of this ring.
* @throws RingException if b divides not a. 
* @return a/b if b divides a.
*/
    public RingElt tdiv(RingElt a, RingElt b) {
         BigInteger [] result = 
          ((IntegerRingElt) a).getValue().divideAndRemainder( 
                          ((IntegerRingElt) b).getValue()); 
                          
         if( result[1].equals(IntegerRing.BigIntegerZERO) ) {
             return new IntegerRingElt( result[0] );
         } else {
           throw new RingException("Tried to true divide: "+a+"/"+b+
          " which gives a remainder of "+result[1]); //
         }
         }
/**
* Remainder of Euclidian division. 
True division. If the division cannot be performed without remainder
* @param a a RingElt which can be mapped to an element of this ring.
* @param b a RingElt which can be mapped to an element of this ring.
* @return a%b (remainder of Euclidian division).
*/
    public RingElt mod( RingElt a, RingElt b) {
         a = map(a); b = map(b);
         return new IntegerRingElt( 
            ((IntegerRingElt) a).getValue().remainder( 
                          ((IntegerRingElt) b).getValue()) 
                              );
    
         }
/** 
* Returns 1 as an element of this Ring.
* @return 1.
*/
    public RingElt one(){
             return new IntegerRingElt(IntegerRing.BigIntegerONE);
             }
/** 
* Returns 0 as an element of this Ring.
* @return 0.
*/
    public RingElt zero() {
             return new IntegerRingElt(IntegerRing.BigIntegerZERO);
             }

/** 
* Returns -b as an element of this Ring.
* @param b a RingElt which can be mapped to an element of this ring.
* @return -b.
*/
    public RingElt neg( RingElt b) {
               b = map(b);
               return new IntegerRingElt( 
                     ((IntegerRingElt) b).getValue().negate());
         }
/** 
* Returns true if b is equals to zero, false otherwise.
* @param b a RingElt which can be mapped to an element of this ring.
* @return true if and only if b == 0.
*/
    public boolean equalZero( RingElt b) { 
         b = map(b);
         return ((IntegerRingElt) b).getValue().equals(IntegerRing.BigIntegerZERO);
         }
/** 
* Returns the value of b as a BigInteger. 
* @param b a RingElt which can be mapped to an element of this ring.
* @return b as BigInteger. 
*/
    public static BigInteger toBigInteger(RingElt b) {
         return ((IntegerRingElt) b).getValue();
         }     

    private class IntegerRingElt extends RingElt {
       private BigInteger value;

       public IntegerRingElt( BigInteger value) { 
             super(IntegerRing.Z); 
             this.value = value; 
             }

       public BigInteger getValue() { return value; }

       public String toString() {
           return ""+value + "";
           }
       }
            
}
