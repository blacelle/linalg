package com.perisic.ring;
import java.math.*;
/* 
Last Change: 25.04.2002 parseDouble() replaced and it works fine with JDK1.1
*/
/**
* The real numbers. This is a wrapper class for the primitive Java
* data type double. This class is not yet fully documented, 
* but methods are similar to the the methods of Ring.Z.
* This class cannot be instantiated as there is no public
* constructor. Use Ring.R instead.
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
public class DoubleField extends Ring {

    public static final DoubleField R = new DoubleField();

    private DoubleField() {
           super();
           }
/**
* @return "R"
*/
    public String toString() { return "R"; }
/**
* Returns true. To be honest, DoubleField is of course not a field. It's 
* not even associative concerning addition (the usual problems
* concerning real arithmetic in a computer). But for our purposes here it seems wise
* to return true anyway.
*
* @return true
*/
    public boolean isField() {
          return true;
          }
/**
* Addition.
* @return a + b.
*/

    public RingElt add( RingElt a, RingElt b) {
         a = map(a);
         b = map(b);
         return new DoubleFieldElt(
            ((DoubleFieldElt) a).getValue() + 
                          ((DoubleFieldElt) b).getValue()
                              );

         }
/**
* Multiplication.
* @return a * b
*/
    public RingElt mult( RingElt a, RingElt b) {
         a = map(a);
         b = map(b);
         return new DoubleFieldElt(
            ((DoubleFieldElt) a).getValue() *
                          ((DoubleFieldElt) b).getValue()
                              );

         }
/** 
* Multiplicative Inverse.
* @return a^-1
*/
    public RingElt inv( RingElt a) {
         a = map(a);
         return new DoubleFieldElt ( 
              1.0 / ((DoubleFieldElt) a).getValue()); //
         }
         
/**
* The 1 of the field.
* @return 1
*/ 
    public RingElt one(){
             return new DoubleFieldElt(1.0);
             }
/** 
* The 0 of the field.
* @return 0
*/
    public RingElt zero() {
             return new DoubleFieldElt(0.0);
             }
/** 
* The additive inverse of b.
* @return -b
*/
    public RingElt neg( RingElt b) {
               b= map(b);
               return new DoubleFieldElt(
                     -(((DoubleFieldElt) b).getValue()));
         }
/** 
* true if b == 0.
* @return b == 0
*/        
    public boolean equalZero( RingElt b) {
         b = map(b);
         return ((DoubleFieldElt) b).getValue() == 0;
         }
/** 
* returns the double value of b.
* @return the double value of b.
*/
    public static double toDouble(RingElt b) {
         return ((DoubleFieldElt) b).getValue();
         }
/** 
* Maps a double to this field.
* @return r as an element of this Field.
*/         
    public RingElt map( double r ) {
           return new DoubleFieldElt(r);
           }
/** 
* Returns str as a DoubleField. Tries first to map 
* str via java.lang.Double.valueOf(java.lang.String).toDouble(). 
* If this fails the Ring.map(java.lang.String) method is called.
* 
* @see java.lang.Double#parseDouble(java.lang.String).
* @see Ring#map(java.lang.String).
* @return str as element of this Field.
*/           
    public RingElt map( String str ) {
           RingElt r;
           try {
                 r = new DoubleFieldElt( Double.valueOf(str).doubleValue() );
             } catch ( java.lang.NumberFormatException s) {
                 r = super.map(str);
             }
             return r;
           }
    private class DoubleFieldElt extends RingElt {
       private double value;

       public DoubleFieldElt( double value) {
             super(DoubleField.R);
             this.value = value;
             }

       public double getValue() { return value; }

       public String toString() {
           return ""+value;
           }
       }

}
