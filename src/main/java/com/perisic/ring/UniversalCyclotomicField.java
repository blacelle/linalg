package com.perisic.ring;

/**
* The infinite algebraic extension of Q which contains all 
* unit roots.
*
*<UL>
* 
* <li> Last Change: 13.12.2003: GPL MC. 
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
* @version 0.2
*/  
public class UniversalCyclotomicField extends UniversalRing {


/**
* returns true, as this is a field.
*/

public boolean isField() { 
        return true;
        }
/** the unit root prefix */
private String z;

/**
* Construct the universal cyclotomic field by the prefix for the variable.
* That means, when the parameter is &quot;z&quot; the unit roots are denoted
* as z3, z5, z15, z24, etc. meaning a primitive third, fifth, 15th, 24th, etc.
* unit root. The relationship between the unitroots is zn<sup>n/d</sup>=zd.
* 
**/

public UniversalCyclotomicField(String unitRootPrefix) {
           super();
           z = unitRootPrefix;
           }
/**
* @return "CYC(z*)" where R is the coefficient ring and z the prefix of the variable.
*/
    public String toString() {
        return "CYC("+z+"*)";
        }

// Returns 0 if v is not of the form prefixNumber.
private int stripNfromVariable(String v) {
        if( v.startsWith(z) ) {
            String str = v.substring(z.length());
            int result = 0; 
            try {
                result = Integer.parseInt(str); 
                }
            catch( NumberFormatException ex ) {
                return 0; 
                }
            return result;
            }
        return 0;     
        }
/**
* maps the string str into the 
* n-th cyclotomic field
*/
public RingElt map( int n, String str ) {
         Ring C = new CyclotomicField(n,z+n);
         return C.map(str);
         }
  
/**
* The following Rings are mapped: Cyclotomic fields, where the variable is of 
* the form z* where z ist the preifx of the variable and * is a number; 
* Polynomial rings and Quotient fields over Polynomial rings 
* where the variables are of the form z*; the usual suspects (Z, Q). 
*/
public RingElt map(RingElt r) {
         
         Ring R = r.getRing(); 
         //System.out.println("map: "+r+" of "+R);
         if( R instanceof CyclotomicField ) {
             int n = ((CyclotomicField) R).getN();
             String v =  ((PolynomialRing) ((CyclotomicField) R).getModulus().getRing()).getVariable();
             if( v.equals(z+n) ) {
                return r;
                }
             } 
         else if( R instanceof QuotientField ) {
                RingElt rn = ((QuotientField) R).numerator(r);
                RingElt rd = ((QuotientField) R).denominator(r);
                rn = map(rn);
                rd = map(rd);
                return div(rn,rd);
                }
         else if( R instanceof PolynomialRing ) {
             String var = ((PolynomialRing) R).getVariable();
             int n = stripNfromVariable(var);
             if( n == 0 ) {
                throw new RingException("Cannot map an element of "+R.toString()+" to "+this.toString());
                }
             RingElt x = map(n, var);
             RingElt result = this.zero();
             int m = ((PolynomialRing) R).degree(r);
             if( m < 0 ) { return this.zero(); }
             for( int i = m; i >= 0; i--) { // Horner scheme
                RingElt ri = ((PolynomialRing) R).getCoefficientAt(i,r);
                if( ri != null ) {
                    result = add(result, map(ri));
                    }
                if( i != 0 ) {
                    result = mult(result, x); 
                    }
                }
             return result;
            } 
         return super.map(r);
         // more to follow, but what exactly?
         }

/**
* Strings denoting Rational functions (elements of Quotient fields of Polynomial rings)
* over variables of the form z* where z ist the preifx of the variable and * is a number;
* are mapped. 
*/
public RingElt map( String str ) {
        Ring UP = new QuotientField( new UniversalPolynomialRing(Ring.Q) );
        RingElt p = UP.map(str);
        return this.map(p);
        }



/**
*  @return the rational numbers.
*/

   public Ring findRing() {
       
       return Ring.Q;
       }

/**
*  Returns the ring of the argument a if this a Cyclotomic field or Q.
*  Otherwise a RingException is thrown.
* @throws RingException
*/
   public Ring findRing( RingElt a ) { 
        Ring R = a.getRing(); 
        if ( R instanceof CyclotomicField ) {
            return R;
            }
        else if( R == Ring.Q ) {
            return Ring.Q;
            }
        throw new RingException("Cannot map an element "+a+" of "+R.toString()+" to "+this.toString());
      }
      
private static int gcd(int a, int b) {
     
      while( a != 0 ) {
           int tmp = b % a;
           b = a;
           a = tmp;
           }
      return b;
      }
           
/**
*       Returns  cyaclotomic field which contains both a and b.
*       If a is a ring of the n-th cyclotomic field and b 
*       of the m-th cyclotomic field, then the k-th cyclotomic
*       field with k = lcm(n,m) (least common multiple) is returned.
*/

public Ring findRing(RingElt a, RingElt b) {
       Ring Ra = a.getRing();
       Ring Rb = b.getRing();

       if( Ra == Ring.Q )  { return Rb; }
       else if ( Rb == Ring.Q ) { return Ra; } 
       
       if( ! (Ra instanceof CyclotomicField) ) {
               throw new RingException("Cannot find a ring for "+a+" as Ra="+Ra+" is not a cyclotomic field.");
            } 
       if( !(Rb instanceof CyclotomicField) ) {
            throw new RingException("Unable to find a ring for "+b+" as Rb="+Rb+" is not a cyclotomic field.");
            } 
       int na = ((CyclotomicField)Ra).getN();
       int nb = ((CyclotomicField) Rb).getN();
       if( na == nb ) { return Ra; }
       else if( na % nb == 0 ) { return Ra; }
       else if( nb % na == 0 ) { return Rb; }
       else {
           int nc = nb * (na / gcd(nb,na)); //
           Ring Rc = new CyclotomicField(nc,z+nc);
           return Rc;
           }
       }

/**
* This is for testing this class only.
*/

       public static void main(String [] args) {
           UniversalCyclotomicField U = new UniversalCyclotomicField("e");
           // Test case: Equation (216) of my Ph.D. thesis (Ennolas relation): 
           RingElt lhs = U.map("1 - e60^41");
           System.out.print("lhs = "+lhs);
           System.out.println(" of "+lhs.getRing());
           
           RingElt rhs1 = U.map(" e37^2 * e15^14 * (1 - e60^37)");
                   rhs1 = U.mult(rhs1, U.map("(1 - e20) * ( 1 - e15)^-1"));
                   rhs1 = U.mult(rhs1, U.map("(1 - e12)^-1 * (( 1 - e5^2) / ( 1 - e5))^-1")); //
                   rhs1 = U.div(rhs1, U.map("e37^2"));
           System.out.println("rhs1 = "+rhs1+" of "+rhs1.getRing());        
                   
           RingElt rhs = U.map( " e15^14 * (1 - e60^37)"
                              + " * (1 - e20) * ( 1 - e15)^-1"
                              + " * (1 - e12)^-1 * (( 1 - e5^2) / ( 1 - e5))^-1" );   //
                              
         
                  
           System.out.print("rhs = "+rhs);
           System.out.println(" of "+rhs.getRing()); 
           if( U.equal(rhs1, rhs) ) {
                    System.out.println("rhs1 == rhs that's ok");
                    } 
           
           RingElt result = U.sub(rhs, lhs);
           System.out.print("rhs  - lhs ="+result );
           System.out.println(" of "+result.getRing());

           }

}
