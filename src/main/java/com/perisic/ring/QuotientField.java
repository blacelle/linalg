package com.perisic.ring;
import java.math.*;
import java.io.*;
import com.perisic.ring.*;

/**
* A field of fractions p/q with p,q in B, where B is any Ring. Usually
* B is here a PolynomialRing, for example F2[t] or Z[a][b].
* B must be at least an UFD. <P>
*
* You can use this class also to constuct a rational field Q with B = Z.
* For performance reasons it is recommended to use Ring.Q of type RationalField
* instead.
*
* <UL>
*<li>Last Change: 16.01.2003
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
public class QuotientField extends Ring {
    private Ring B;

   // static final QuotientField Q = new QuotientField(IntegerRing.Z);

   // For performance reasons the B.one() of the Ring.
   private RingElt BOne = null;

/**
* Returns true.
*/
    public boolean isField() { return true; }
/**
* Returns the denominator and numerator ring B (the base ring).
*/
    public Ring getBaseRing() { return B; }
/**
* Returns the numerator of b as an element of the base ring.
*/
    public RingElt numerator(RingElt b) {
           return ((QuotientFieldElt) map(b)).getNumerator();
           }
/**
* Returns the denominator of b as an element of the base ring.
*/

    public RingElt denominator(RingElt b) {
           return ((QuotientFieldElt) map(b)).getDenominator();
           }
/**
* Construction.
*/
    public QuotientField(Ring BaseRing) {
           super();
           B = BaseRing;
           BOne = B.one();
           }
/**
* Addition a + b.
*/
    public RingElt add( RingElt a, RingElt b) {
         RingElt ad = denominator(a);
         RingElt bd = denominator(b);

         RingElt cd, cn;
         if( ad != bd ) {
            cn = B.add(B.mult(numerator(a),bd), B.mult(numerator(b),ad));
            cd = B.mult(ad,bd);
         } else {
            cn = B.add(numerator(a), numerator(b));
            cd = ad;
            }

         return new QuotientFieldElt(this, cn,cd, false);
          }

/**
* Multiplication a * b.
*/
    public RingElt mult( RingElt a, RingElt b) {

         RingElt cn = B.mult(numerator(a),numerator(b));
         RingElt ad = denominator(a);
         RingElt bd = denominator(b);
         RingElt cd;
         if( ad == BOne )  { cd = denominator(b); }
         else if( bd == BOne ) { cd = denominator(a); }
         else {
            cd = B.mult(denominator(a),denominator(b));
            }
         return new QuotientFieldElt(this,cn,cd, false);
         }
/**
* Returns 1.
*/
    public RingElt one(){
             return new QuotientFieldElt(this, BOne, BOne, true);
             }
/**
* Returns 0.
*/
    public RingElt zero() {
             return new QuotientFieldElt(this, B.zero(), BOne, true);
             }
/**
* Returns b^-1.
*/
    public RingElt inv( RingElt b) {
         return new QuotientFieldElt(this, denominator(b),numerator(b),false);
         }
/**
* Returns -b.
*/
    public RingElt neg( RingElt b) {
         return new QuotientFieldElt(this, B.neg(numerator(b)),denominator(b),true);
         }
/**
* True if b == 0. False otherwise.
*/
    public boolean equalZero( RingElt b) {
         return B.equalZero(numerator(b));
         }
/**
* If a is an element of another QuotientRing, numerator and
* denominator are mapped to B. Otherwise a is mapped to B
* and the denominator set to 1.
*/
    public RingElt map(RingElt a) {

         Ring A = a.getRing();
         //System.out.println("mappe "+a+" of "+A+" into "+this);
         if( A.equals(this) ) { return a; }
         else if( A instanceof QuotientField ) {
            RingElt cn = B.map(((QuotientField) A).numerator(a));
            RingElt cd = B.map(((QuotientField) A).denominator(a));
            return new QuotientFieldElt(this,cn,cd,false);
         } else {
            return new QuotientFieldElt(this,B.map(a), BOne,true);
            }
         }
/**
* Returns "Quot(str)" where str = B.toString().
*/
    public String toString() { return "Quot("+B+")"; }
/**
* true if the denominator is one.
*/
    public boolean isIntegral(RingElt b ) {
           return B.equal(denominator(b), BOne);
           }
/**
* Constructs numerator/denominator.
*/
    public RingElt construct(RingElt numerator, RingElt denominator) {
          return new QuotientFieldElt(this,B.map(numerator), B.map(denominator),false);
          }
    private RingElt evaluateRationalFunction(RingElt p, RingElt x) {
         return div( evaluatePolynomial(((QuotientField) p.getRing()).numerator(p),x),
                     evaluatePolynomial(((QuotientField) p.getRing()).denominator(p),x) );
         }
   private int findLastTrueMinus(String a) {
       boolean found = false;
       int res = -1;
       for( int i = a.length() -1; i >= 0; i-- ) {
             if( a.charAt(i) == '-' ) {
                    res = i;
                    found = true;
                    }
             else if( found && a.charAt(i) == '^' ) {
                    found = false;
                    }
             else if( found && a.charAt(i) == ' ' ) {
                    // do nothing
                    }
             else if( found ) { return res;}
             }
       if( found ) return res;
       else return -1;
       }
/**
* Maps the String a into this Ring. This method is similar
* to the PolynomialRing.map(java.lang.String) method.
* In addition negative exponents are allowed.
*/
   public RingElt map(String a ) {
    int bc = 0;
    int y;
    a = a.trim();
     if(( B instanceof PolynomialRing ) &&
         a.equals(((PolynomialRing) B).getVariable()) ) {
          return ((PolynomialRing) B).construct(1,
                 ((PolynomialRing) B).getCoefficientRing().one());
          }
       if( (y = a.indexOf("(")) != -1  ) {
          bc = 1;
          int i = y+1;
          while( bc != 0 ) {
              if( i == a.length() ) {
               throw new RingException("You tried to map "+a+" which has "+
                    "unbalanced ( and )");
                    }
          if( a.charAt(i) == '(' ) { bc++; }
          else if ( a.charAt(i) == ')' ) { bc--; }
          i++;
          }
       RingElt c = map(a.substring(y+1,i-1));
       if( y == 0 && i == a.length() ) {
            return c;
            }
            // Changed this to B in July 2003.
       PolynomialRing P = new PolynomialRing(B);
       QuotientField Q = new QuotientField(P);

       RingElt d = Q.map( a.substring(0,y) + P.getVariable()+
           (i == a.length() ? "" : a.substring(i)) );
           //System.out.println("d = "+d+", c = "+c);
       return evaluateRationalFunction(d,c);
         } else if ( (y = a.lastIndexOf("+") ) != -1 ) {
          String pre = a.substring(0,y).trim();
          String suf = a.substring(y+1).trim();
          if( pre.equals("") ) { return map(suf); }
          char last = pre.charAt(pre.length()-1);

          if( last == '^' || last == '+' || last == '-' || last == '*' || last == '/' ) {  //
               return map(pre + suf);
               }
          RingElt c = map(pre);
          RingElt d = map(suf);
          return add(c,d);
          }
     else if ( (y = findLastTrueMinus(a) ) != -1 ) {
          String pre = a.substring(0,y).trim();
          String suf = a.substring(y+1).trim();
          if( pre.equals("") ) { return neg(map(suf)); }
          char last = pre.charAt(pre.length()-1);

          if( last == '^' ) {
               throw new RingException("negative exponents not (yet) implemented.");
               }
          if( last == '+' || last == '-' || last == '*' || last == '/' ) {  //
               return map(pre + "(0-"+suf+")");
               }
          RingElt c = map(pre);
          RingElt d = map(suf);
          return sub(c,d);
          }

     else if ( (y = a.lastIndexOf("*") ) != -1 ) {
          RingElt c = map(a.substring(0,y).trim());
          RingElt d = map(a.substring(y+1).trim());
          return mult(c,d);
          }
     else if ( (y = a.lastIndexOf("/") ) != -1 ) { //
          RingElt c = map(a.substring(0,y).trim());
          RingElt d = map(a.substring(y+1).trim());
          return tdiv(c,d);
          }

     else if ( (y = a.lastIndexOf("^") ) != -1 ) {
          RingElt c = map(a.substring(0,y).trim());
          String str = a.substring(y+1).trim();
          int exp = 0;
            try {
             exp = Integer.parseInt(str);
          } catch ( java.lang.NumberFormatException s ) {
            throw new RingException("You cannot have "+str+" as exponent "+
                  "while parsing "+a+" for "+this);
                  }
          return pow(c,exp);
          }
    if( B instanceof PolynomialRing ) {
        return map( ((PolynomialRing) B).getCoefficientRing().map(a));
        }
    else {
       return map(B.map(a));
        }
    }


   private class QuotientFieldElt extends RingElt {
       private RingElt denominator;
       private RingElt numerator;


       private void reduce() {
               if( denominator == BOne || numerator == BOne ) return;
               //Ring B = denominator.getRing(); changed: 16.01.2003
               // Using B both for this and below leads to
               // problems with the UniversalPolynomialRing
               Ring B = getBaseRing();
               RingElt gcd = B.gcd(numerator,denominator);
               numerator =   B.tdiv(numerator, gcd);
               denominator = B.tdiv(denominator,gcd);

               RingElt d;
               Ring DB = denominator.getRing();
               if (DB instanceof PolynomialRing ) {
                   d = ((PolynomialRing) DB).globalLeadingCoefficient(denominator);
                   }
               else {
                   d = denominator;
                   }

               if( d.getRing().isField() ) {
                     denominator = B.tdiv(denominator,d);
                     numerator = B.tdiv(numerator,d);
                     }
               else if( d.getRing() instanceof IntegerRing &&
                   IntegerRing.Z.toBigInteger(d).signum() < 0 ) {
                   numerator = B.neg(numerator);
                   denominator = B.neg(denominator);
                   }
               }


       public QuotientFieldElt( Ring R, RingElt numerator, RingElt denominator,
                                boolean reduced) {
           super(R);
           this.denominator = denominator;
           this.numerator = numerator;
           if( reduced == false ) { reduce(); }
           }

       public RingElt getDenominator() { return denominator; }
       public RingElt getNumerator() { return numerator; }
       public String toString() {
            String sn = numerator.toString();
            if( denominator.equals(denominator.getRing().one())) {
                    return sn;
                    }
            String sd = denominator.toString();

            if( sd.equals("1") ) { return sn; }
            if( sn.indexOf("+") > 0 || sn.lastIndexOf("-") > 0 ||
                sn.indexOf("/") > -1 ) { sn = "("+sn+")"; } //
            if( sd.indexOf("+") > 0 || sd.lastIndexOf("-") > 0 ||
                sd.indexOf("/") > -1 || sd.indexOf("*") > -1 ) {
                        sd = "("+sd+")"; } //

           return sn + "/" + sd;
           }
       }
       //
   public static void main ( String [] args ) {
            QuotientField P = new QuotientField(new PolynomialRing(
                 Ring.F2,
                 "a,b,c,d"));
//         P = new QuotientField( new QuotientField( new PolynomialRing(P,"x,y")));
//               "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z");
            // PolynomialRing P = new PolynomialRing(
            //        RationalField.Q,"a,b,c,d");
            BufferedReader reader = new BufferedReader(
                              new InputStreamReader(System.in));
            RingElt p = null;
            RingElt s = P.zero();
            do {
                String str = "0";
                System.out.println("Enter a rational function of (0 for exit) "+P);
                try {
                str = reader.readLine();
                } catch (IOException e) {
                     System.out.println("An IOException has happened.");
                      }
                System.out.println("Trying to map "+str+
                     " into "+ P);
                p = P.map(str);
                System.out.println("result = " + p);

                s = P.add(s,p);
                System.out.println("sum of this and the previous sum = " + s);
                } while ( P.equalZero(p) == false);
         }


}
