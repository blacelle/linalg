package com.perisic.ring;

import java.util.*;
/**
* This class implements a cyclotomic field.
** <UL>
*<li>
* Last Change: 20.01.2003, Mapping from cyclotomic field to 
* cyclotomic field added.
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
*/

public class CyclotomicField extends ModularRing {
/**
* returns true.
*/
     public boolean isField() { return true; }
     
     private int N;
/**
* returns the <em>n</em> if this is the <em>n</em>th cyclotomic 
* field
**/
     public int getN() {
          return N;
          }
/**
* returns CYC(<em>n</em>).
*/
     public String toString() {
          return "CYC("+getN()+")";
          }
    
     private static boolean isPrime(int n) {
           int m = (int) Math.round(Math.sqrt(n)) + 1;
           for(int i = 2; i < m; i++ ) {
                 if( n % i == 0 ) { return false; }
                 }
           return true;
           }
     private static RingElt consXpowNminus1(PolynomialRing P, int n) {
             int [] expo = { n, 0 };
             Object [] coeff = { "1", "-1" }; 
             return P.construct(expo, coeff); 
           }
/** 
* Constructs the <code>n</code>-th cyclotomic polynomial over the ring F as a 
* polynomial in the variable <code>variable</code>.
*/
     public static RingElt getCyclotomicPolynomial(Ring F, int n, String variable) {
           PolynomialRing P = new PolynomialRing(F,variable);
           RingElt g = consXpowNminus1(P,n);
           for( int i = 2; i <= n; i++ ) {
               if( i == n || (n % i == 0 && isPrime(i))) {
                RingElt h = consXpowNminus1(P,n/ i); //
                RingElt t; 
                if( F.isField() ) { 
                    t = P.extendedGcd(h,g)[0]; 
                    } 
                else { t = P.gcd(h,g);
                    }
                g = P.tdiv(g, t);
                }
               }
           return g;
           }
/** 
* Constructs the algebraic number field which contains all <code>n</code>th 
* roots of unity.
*/
      public CyclotomicField(int n, String variable) {
          super( CyclotomicField.getCyclotomicPolynomial(Ring.Q, n, variable) );
          this.N = n;   
          hideMod();      
          }
/**
* If the ring of the argument is of a <em>d</em>th cyclotomic field and <em>d</em>
a divisor of <em>n</em> we embed 
* via the mapping <em>z<sub>d</sub> -&gt; z<sub>n</sub><sup>n/d</sup></em>
* where <em>z<sub>n</sub></em> denotes a fixed <em>n</em>th root of unity.
*<br>
*If the ring is not a cycltomic field the map() method of the parent class
is invoked.
*/

    public RingElt map(RingElt a) {
          Ring Rd = a.getRing();
          // trivial mapping first for performance reason.
          if( Rd == this ) { return a; }
          if ( Rd instanceof CyclotomicField ) {
               CyclotomicField Cd = (CyclotomicField) Rd;
               int d = Cd.getN();
               int n = getN();
               if( n % d == 0 ) {
                   // The polynomial ring P is the ring which is used to construct
                   // this cyclotomic field.
                   PolynomialRing P = (PolynomialRing) (getModulus().getRing()); 
                   RingElt zt = P.construct(n/d, Ring.Z.one()); // construct z^(n/d)
                 
                   RingElt p = Cd.getValue(a); // Returns the element as a polynomial
                   RingElt w = evaluatePolynomial(p,zt);
                   return w;
                   }
               else {
                   throw new RingException("You cannot embedd an element "+a+
                       " of the "+d+" th cyclotomic field into an element of"+
                       " the "+n+" th cyclotomic field.");
                       }
                   }
          else { // Rd is NOT an instance of a cyclotomic field:
               return super.map(a);
               }
         }
         
/**
* A very simple tester for this class.
*/
         public static void main( String [] args ) {
                 Ring C5 = new CyclotomicField(5, "z5");
                 Ring C105 = new CyclotomicField(105, "z105");
                 RingElt w1;
                 if( args.length > 0 ) {
                     String str = "";
                     for( int i = 0; i < args.length; i++ ) {
                         str = str + args[i]+" ";
                         }
                     System.out.println("got: "+str);
                     w1 = C5.map(str);
                     }
                 else {
                    w1 = C5.map( "(1 - z5)*(1 - z5^2)*(1/z5^4)");
                    }
         
                 RingElt w2 = C105.map(w1);
                 System.out.println(w1.toString()+" maps to "+w2.toString() );
                 System.out.println("w1^2="+C5.pow(w1,2));
                 System.out.println("w2^2="+C105.pow(w2,2));
                 }
}
