package com.perisic.ring;
import com.perisic.ring.*;
import java.math.*;
/**
* The modular integer ring Z/nZ.
* This ring uses the BigInteger arithmetic of the java.math.*
* package and is therefore more efficient then a construction 
* via the ModularRing class of this package
* <UL>
* <li> Last Change: 13.12.2003: eltToString(), GPL. 
* <li> Last Change: 01.05.2011: fixed error in documentation.
*<li>
* Copyright: (c) Marc Conrad, 2002-2011.
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
* @version 0.3
**/
public class ModularIntegerRing extends Ring {
    private BigInteger modulus; 
/**
* Returns m where this ModularIntegerRing is Z/mZ.
*/    
    public BigInteger getModulus() { return modulus; } //
    
/** 
* Returns the BigInteger value of b. A value between 0 and m
*/
   public static BigInteger toBigInteger(RingElt b) {
       return ((ModularIntegerRingElt) b).getValue();
       }
/**
* Construction of Z/mZ with m = modulus. 
* @param modulus An instance of a BigInteger or a RingElt which can be mapped
*  to Ring.Z.
*/     
    public ModularIntegerRing(Object modulus) {
         
           super();  
           if( modulus instanceof BigInteger ) {
               this.modulus = (BigInteger) modulus; 
           } else {
               RingElt m = IntegerRing.Z.map(modulus);
               this.modulus = IntegerRing.toBigInteger(m); 
               }
           }
/* 
* Construction of Z/mZ
*/
    public ModularIntegerRing(int m) {
           super();
           this.modulus = modulus.valueOf(m); 
           }
/**
* Returns a + b mod m.
*/
    public RingElt add( RingElt a, RingElt b) {
          if( a.getRing() != this ) { a = this.map(a); }
          if( b.getRing() != this ) { b = this.map(b); }        
          BigInteger resultValue = 
             ((ModularIntegerRingElt) a).getValue().add( 
                      ((ModularIntegerRingElt) b).getValue() );
             resultValue = resultValue.remainder(modulus);
             return new ModularIntegerRingElt(resultValue, this);
    
         }
/** 
* Returns a * b mod m.
*/
    public RingElt mult( RingElt a, RingElt b) {
          if( a.getRing() != this ) { a = this.map(a); }
          if( b.getRing() != this ) { b = this.map(b); }        
          BigInteger resultValue = 
             ((ModularIntegerRingElt) a).getValue().multiply( 
                      ((ModularIntegerRingElt) b).getValue() );
             resultValue = resultValue.remainder(modulus);
             return new ModularIntegerRingElt(resultValue, this);
         }
/** 
* Returns 1.
*/
    public RingElt one(){
             return new ModularIntegerRingElt(IntegerRing.BigIntegerONE,this);
             }
/** 
* Returns 0.
*/
    public RingElt zero() {
             return new ModularIntegerRingElt(IntegerRing.BigIntegerZERO,this);
             }
/** 
* Returns b^-1 mod m. b must be an unit in Z/mZ. 
* @throws ArithmeticException if b is not an unit mod m.
*/
    public RingElt inv( RingElt b) {
             if( b.getRing() != this ) { b = this.map(b); }       
             BigInteger bV = ((ModularIntegerRingElt) b).getValue();
             bV = bV.modInverse(modulus);
             return new ModularIntegerRingElt(bV, this);
             }
/**
* The same as div(a,b). b must be a unit of Z/mZ.
* @throws ArithmeticException if b is not a unit mod m.
*/
    public RingElt tdiv(RingElt a, RingElt b) {
             return div(a,b);
     }
/** 
* Returns -b mod m.
*/
    public RingElt neg( RingElt b) {
             if( b.getRing() != this ) { b = this.map(b); }       
             BigInteger bV = ((ModularIntegerRingElt) b).getValue();
             if( bV.equals(IntegerRing.BigIntegerZERO) ) return b;
             bV = modulus.subtract(bV);
             return new ModularIntegerRingElt(bV, this);
             }
/**
* True if b == 0, false otherwise.
*/
    public boolean equalZero( RingElt b) { 
         if( b.getRing() != this ) { b = this.map(b); } 
         BigInteger bn = ((ModularIntegerRingElt) b).getValue();
         return bn.equals(IntegerRing.BigIntegerZERO);
         }
/** 
* Two ModularIntegerRing objects are equal, if the modulus is the same.
*/         
    public boolean equals( Object ob ) {
         if( (ob instanceof ModularIntegerRing) &&
             ((ModularIntegerRing) ob).getModulus().equals(modulus ) ) return true;
         return false;
         }
/**
* Performs the ususal map as in Ring.map(RingElt). In addition an element
* of another ModularIntegerRing M with M.modulus == this.modulus
* is mapped to this. (Not yet implemented: Mapping in case that this.modulus is
* a divisor of M.modulus. Also to do (if this makes sense): 
* Mapping from F2Field if modulus == 2).
*/
            
    public RingElt map( RingElt a ) {
         if( a.getRing() == this ) return a;
         else if( a.getRing().equals(this) ) {
              return map(a.getRing().map(toBigInteger(a)));
              }
         if( a.getRing().equals(IntegerRing.Z) ){
             return map( IntegerRing.toBigInteger(a));
            }
         return super.map(a);
         }

private class ModularIntegerRingElt extends RingElt {
       private BigInteger value;
      
       
       public ModularIntegerRingElt( BigInteger value, Ring R) {
            super(R);
            this.value = value; 
        
            }
       public BigInteger getValue() { return value; }
       
       public String toString() {
           return ""+value.toString() + " mod " +
                ((ModularIntegerRing) getRing()).getModulus().toString() + "";
           }
       }
}
