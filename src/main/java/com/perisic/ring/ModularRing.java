package com.perisic.ring;
import com.perisic.ring.*;
import java.math.*;
/** 
* Implements a ring R/fR where R is an element of the ring R. <P>
* Currently only implemented for polynomial rings R. Might therefore be used for
* constructing algebraic extensions over fields. <P>
* Use the class ModularIntegerRing for constructing Z/mZ.
*
* <UL>
* <li>
* Last Change: 20.01.2003, map(String) now via Quot(f.getRing()), MC.
* <li> Last Change: 13.12.2003: eltToString(), MC. 
*<li>
* Copyright:(c) Marc Conrad, 2002, 2003 
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
* @version 0.1
*/
public class ModularRing extends Ring {
    private RingElt modulus; 
    private boolean hideMod;
/** 
* Determins the behaviour of the eltToString() method. 
* If method is called with true, the return value of eltToString()
* is of the form "a" instead of "a mod b".
*/ 
    public void setHideMod(boolean hideMod) { this.hideMod = hideMod; }
/** 
* Determins the behaviour of the eltToString() method. 
* If this method is called, the return value of eltToString()
* is of the form "a" instead of "a mod b".
*/ 
    public void hideMod() { hideMod = true; }
/** 
* Determins the behaviour of the eltToString() method. 
* If this method is called, the return value of eltToString()
* is of the form "a mod b" instead of "a".
* This is the default.
*/ 
    public void displayMod() { hideMod = false; }
/** 
* true if hideMod() has been called. False otherwise.
*/
    public boolean getHideMod() { return hideMod; }
/** 
* Returns f if this is R/fR.
*/    
    public RingElt getModulus() { return modulus; }

/**
*  Returns the value of b as an element of R.
*/
   public RingElt getValue(RingElt b) {
          b = map(b);
          return ((ModularRingElt) b).getValue();
          }

/**
* Constructs m.getRing()/m * m.getRing(). Currently only implemented
* for polynomial rings.
*/
    public ModularRing(RingElt m) {
         
           super();  
           displayMod();
           if( !(m.getRing() instanceof PolynomialRing) ) {
               throw new RingException("you cannot initialize a "+this.toString()+
                         " by "+m+" of ring "+m.getRing() );
           } else {
               this.modulus = m; 
               }
           }
/**
* Addition.
*/
    public RingElt add( RingElt a, RingElt b) {
          if( a.getRing() != this ) { a = this.map(a); }
          if( b.getRing() != this ) { b = this.map(b); }        
          return map(modulus.getRing().add( 
             ((ModularRingElt) a).getValue(),
             ((ModularRingElt) b).getValue() 
             ));
         }
/**
* Multiplication.
*/
    public RingElt mult( RingElt a, RingElt b) {
          if( a.getRing() != this ) { a = this.map(a); }
          if( b.getRing() != this ) { b = this.map(b); }        
          return map(modulus.getRing().mult( 
             ((ModularRingElt) a).getValue(),
             ((ModularRingElt) b).getValue() 
             ));
         }
/**
* Returns 1.
*/
    public RingElt one(){
             return new ModularRingElt(modulus.getRing().one(),this);
             }
/**
* Returns 0.
*/
    public RingElt zero() {
             return new ModularRingElt(modulus.getRing().zero(),this);
             }
/**
* Returns the inverse b. 
* @throws RingException if gcd(b,f) != 1.
*/             
    public RingElt inv( RingElt b) {
             if( b.getRing() != this ) { b = this.map(b); }
             Ring S = modulus.getRing();
             if( !(S instanceof PolynomialRing ) ) {
                throw new RingException("Sorry, inverse of modulos is so far"
                    +" only implemented if the modulus is a polynomial ring.");
                    }
             PolynomialRing P = (PolynomialRing) S;
             RingElt [] res = P.extendedGcd( modulus, 
                  ((ModularRingElt) b).getValue() 
                  );
             if (!P.equal( res[0], P.one() ) ) {
                   throw new RingException("I cannot compute the inverse of "+b+
                         " modulo "+modulus+", because the gcd is "+res[0]);
                   } 
             return map(res[1]);
             }
/**
* Returns -b.
*/
    public RingElt neg( RingElt b) {
             if( b.getRing() != this ) { b = this.map(b); }       
             return map( modulus.getRing().neg(
                     ((ModularRingElt) b).getValue() ));
             }
/**
* true if b == 0, false otherwise.
*/
    public boolean equalZero( RingElt b) { 
         if( b.getRing() != this ) { b = this.map(b); } 
         return modulus.getRing().equalZero(
               ((ModularRingElt) b).getValue() );
         }
/**
* Maps str first into R, then into this.
*/
    public RingElt map (String str) {
       QuotientField Q = new QuotientField(modulus.getRing());
       RingElt a = Q.map(str);
       return map(a);
       // return map( modulus.getRing().map(str)); Changed: 20.01.2003, MC.
       }

/**
* If the ring of <code>a</code> is a quotient field we map
* the quotient of numerator and denominator.
* Otherwise we 
* map <code>a</code> first into R, then into this Ring.
* If a.getRing().equals(this), a is also mapped.
*/
   
    public RingElt map( RingElt a ) {
         if( a.getRing() == this ) return a;
         else if( a.getRing() instanceof QuotientField ) {
               QuotientField Q = (QuotientField) a.getRing();
               return div(Q.numerator(a), Q.denominator(a));
         }
         else if( a.getRing().equals(this) ) { 
              a = ((ModularRing) a.getRing()).getValue(a); }
         a = modulus.getRing().map(a);
         a = modulus.getRing().mod(a,modulus);
         return new ModularRingElt( a, this);
         }
         
/**
* Returns a in the form "a" or "a mod f" depending on  
* the value of hideMod.
*/
    public String eltToString(RingElt a) {
           String str = ((ModularRingElt) map(a)).getValue().toString();
           if(hideMod == false ) {
                 str += " mod "+ getModulus().toString();
                }
           return str;
           }
    
    private class ModularRingElt extends RingElt {
       private RingElt value;
      
       
       public ModularRingElt( RingElt value, Ring R) {
            super(R);
            this.value =  value; 
        
            }
       public  RingElt getValue() { return value; }
       
       
       }
}
