package com.perisic.ring;
import java.math.*;

/**
* The field Q of rational numbers. The only instance of this 
* field is Ring.Q
*
* <UL>
* <li> Last Change: 13.12.2003: GPL. 
*<li>
* Copyright: (c) Marc Conrad, 2002, 2003 
*<li>
* Email: <a href="mailto:ring@perisic.com">ring@perisic.com</a>. 
* Please let me know if you use this software.
*<li>
* WWW: www.ring.perisic.com
*<li>The com.perisic.ring library is distributed under the terms of the 
* <a href="http://www.gnu.org/copyleft/lesser.html" target="_blank">GNU Lesser General Public License (LGPL)</a>. 
*<li> If you require the package under a different licence please contact me.
*<li> disclaimer: The classes are provided "as is".
* There is no warranty implied by using the com.perisic.ring package.
* </UL>
* @author Marc Conrad
* @version 0.2
**/
public class RationalField extends Ring {
/**
* The same as Ring.Q. 
* @deprecated
*/
    static final RationalField Q = new RationalField(); 
/**
* Returns true.
*/

    public boolean isField() { return true; } 
   
     
    private  RationalField() {
           super();
           }
/**
* Returns a + b.
*/
    public RingElt add( RingElt a, RingElt b) {
         BigInteger an = ((RationalFieldElt) a).getNumerator();
         BigInteger ad = ((RationalFieldElt) a).getDenominator();
         BigInteger bn = ((RationalFieldElt) b).getNumerator();
         BigInteger bd = ((RationalFieldElt) b).getDenominator();
         
         an = an.multiply(bd);
         bn = bn.multiply(ad);
         an = an.add(bn);
         ad = ad.multiply(bd);
       
         return new RationalFieldElt(an,ad);
    
         }
/** 
* Returns a * b.
*/
    public RingElt mult( RingElt a, RingElt b) {
         BigInteger an = ((RationalFieldElt) a).getNumerator();
         BigInteger ad = ((RationalFieldElt) a).getDenominator();
         BigInteger bn = ((RationalFieldElt) b).getNumerator();
         BigInteger bd = ((RationalFieldElt) b).getDenominator();
         
         an = an.multiply(bn);
         ad = ad.multiply(bd);
      
         return new RationalFieldElt(an,ad);
         }
/** 
* Returns 1.
*/  
    public RingElt one(){
             return new RationalFieldElt(IntegerRing.BigIntegerONE, IntegerRing.BigIntegerONE);
             }
/** 
* Returns 0.
*/
    public RingElt zero() {
             return new RationalFieldElt(IntegerRing.BigIntegerZERO, IntegerRing.BigIntegerONE);
             }
/** 
* Returns the multiplicative inverse.
*/
    public RingElt inv( RingElt b) {
         BigInteger bn = ((RationalFieldElt) b).getNumerator();
         BigInteger bd = ((RationalFieldElt) b).getDenominator();
  
         return new RationalFieldElt(bd,bn,true);
             }
/** 
* Returns -b.
*/
    public RingElt neg( RingElt b) {
         BigInteger bn = ((RationalFieldElt) b).getNumerator();
         BigInteger bd = ((RationalFieldElt) b).getDenominator();
  
         return new RationalFieldElt(bn.negate(),bd,true);
         }
/**
* True if b == 0.
*/
    public boolean equalZero( RingElt b) { 
         BigInteger bn = ((RationalFieldElt) b).getNumerator();
         return bn.equals(IntegerRing.BigIntegerZERO);
         }
/** 
* Maps Ring.Z elements and into this.
*/
    public RingElt map(RingElt a) {
         if( a.getRing() == this ) { return a; }
         else if( a.getRing() == IntegerRing.Z ) {
            BigInteger v = IntegerRing.toBigInteger(a);
            return new RationalFieldElt(v, IntegerRing.BigIntegerONE,true);
         } else {
            return super.map(a);
            }
         }
/** 
* Returns "Q".
*/
    public String toString() { return "Q"; }

/**
* true if denominator of b equals 1.
*/    
    public static boolean isIntegral(RingElt b ) {
           b = RationalField.Q.map(b);
           return ((RationalFieldElt) b).getDenominator().equals(IntegerRing.BigIntegerONE);
           }
/**
* Returns the numerator r if b = r/s.
*/    
    public static BigInteger numeratorToBigInteger(RingElt b) {
           b = RationalField.Q.map(b);
           return ((RationalFieldElt) b).getNumerator();
           }
/** 
* Returns the denominator s if b = r/s.
*/
    public static BigInteger denominatorToBigInteger(RingElt b) {
           b = RationalField.Q.map(b);
           return ((RationalFieldElt) b).getDenominator();
           }
/**
* Returns numerator/denominator. 
*/          
    public RingElt construct(BigInteger numerator, BigInteger denominator) {
           return new RationalFieldElt( numerator, denominator);
           }
/**
* Maps the String a of the form xxxxx/yyyyy  and xxxxxx into this field.
* @throws RingException if the String is not of the form above.
*/           
    public RingElt map(String a) {
        try {
        return new RationalFieldElt((String) a);
        } catch( java.lang.NumberFormatException s) {
             throw new RingException("You cannot map String "+a+" into "+ this);
             } 
        }
        
        
   private class RationalFieldElt extends RingElt {
       private BigInteger denominator;
       private BigInteger numerator;
       
       
       private void reduce() {
               BigInteger gcd = numerator.gcd(denominator);
               numerator = numerator.divide(gcd);
               denominator = denominator.divide(gcd);
               if ( denominator.compareTo(IntegerRing.BigIntegerZERO) < 0 ) {
                   numerator = numerator.negate();
                   denominator = denominator.negate();
                   }
               }
       
  
       public RationalFieldElt( BigInteger numerator, BigInteger denominator) {
            super(RationalField.Q);  
            this.denominator = denominator; 
            this.numerator = numerator;
            reduce();
            }
       public RationalFieldElt( String num, String den) {
            super(RationalField.Q);  
            this.numerator =   new BigInteger(num);  
            this.denominator = new BigInteger(den); 
            reduce();
            }

       public RationalFieldElt( String fraction) {
            super(RationalField.Q);  
            int i = fraction.indexOf("/");  //
                 // System.out.println("fraction ="+fraction+" i="+i); 
            String num, den; 
            if( i != -1 ) { 
                 num = fraction.substring(0,i); 
                 den = fraction.substring(i+1); 
                 }
            else { 
                 num = fraction; 
                 den = "1";
               }
            this.numerator =   new BigInteger(num);  
            this.denominator = new BigInteger(den); 
            reduce();
            }
       public RationalFieldElt( BigInteger numerator, BigInteger denominator,
                                boolean reduced) {
           super(RationalField.Q);  
           this.denominator = denominator; 
           this.numerator = numerator; 
           if( reduced == false ) { reduce(); }
           }
        
       public BigInteger getDenominator() { return denominator; }
       public BigInteger getNumerator() { return numerator; }
       public String toString() {
            if( denominator.equals(IntegerRing.BigIntegerONE) ) {
                return numerator.toString();
                }
           return ""+numerator.toString() + "/" + denominator.toString() + "";
           }
       }
            

}
