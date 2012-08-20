package com.perisic.ring;
import java.math.*;
/**
The finite prime field of characteristic 2. This is a wrapper class 
for the primitive data type boolean where "and" is the multiplcaton and 
"exclusive or" is the addition. <P>
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
public class F2Field extends Ring {

private class Elt extends RingElt {
     private boolean value; 
     public Elt(boolean v) { 
           super(F2Field.F2);
           value = v; }
     public boolean getValue() { return value; }
     public String toString() {
         if( value ) return "1";
         else return "0";
         }
     }
/** 
The field F2.
*/
    
    public static F2Field F2 = new F2Field();
    
    private Elt getElt(boolean b) {
        return new Elt(b);
        }
    
     
     private static final Elt ONE = F2.getElt(true);
     private static final Elt ZERO = F2.getElt(false); 

    private F2Field() { }
/*    
Returns true, as F2 is a field. 
*/
    public boolean isField() { return true; } 
/** 
Returns the String "F2".
*/
    public String toString() { return "F2"; }
/**
   Returns the boolean value of a. That is, true if a == 1 and false if a == 0.
*/
    public boolean toBoolean(RingElt a) {
          a = map(a);
          return ((Elt) a).getValue();
          }
/**
The addition a + b mod 2.
*/
    public RingElt add( RingElt a, RingElt b) {
         if ( toBoolean(a) == toBoolean(b) ) return this.ZERO;
         else return this.ONE;
         }
/**
The multiplicaton a * b  mod 2.
*/
    public RingElt mult( RingElt a, RingElt b) {
      if(  toBoolean(a) && toBoolean(b) ) return this.ONE;
      else return this.ZERO;
         }
/**
Returns 0 mod 2.
*/
    public RingElt zero() {
      return this.ZERO;
      }
/**
Returns -a mod 2. Note: As -a == +a mod 2, calling this method is the same as calling map(a).
*/
   public RingElt neg( RingElt a) {
      return map(a);
      }
/**
Returns true if a == 0. 
*/
    public boolean equalZero( RingElt a) {
       return toBoolean(a) == false;
      }  
/**
Returns the 1 of the ring.
*/
    public RingElt one()  {
       return this.ONE;
             }
/**
Returns b^-1. Throws a RingException for b == 0 mod 2 and returns map(b) otherwise.
*/
    public RingElt inv(RingElt b)  throws RingException {
             if( toBoolean(b) == false ) {
                    throw new RingException("Error: You tried to divide by "+b+
                          " which is equals 0 in "+this);
                    } 
             return this.ONE;
            }
/**
Maps false to 0 and true to 1.
*/
    public RingElt map(boolean b) {
        if( b ) return this.ONE;
        else return this.ZERO;
        }
/**
If b is a modular integer ring, such that the modulus maps to 0,
the value of b is mapped to F2. Integers and Rationals are
mapped as usual.
*/
    public RingElt map( RingElt b) {
       Ring B = b.getRing();
       if( B == this ) return b;
       if( B instanceof ModularIntegerRing  &&
           ((ModularIntegerRing) B).getModulus().equals(IntegerRing.BigIntegerZERO) ) {
             return map( ((ModularIntegerRing) B).toBigInteger(b));
             }
       return super.map(b);
      }
}
