package com.perisic.ring;
import java.math.*;
import java.io.*;
/**
* Multivariate Polynomials over a Ring.
*
* Example of use (Polynomials of Z[a][b]): 
* <UL> 
*<pre>
* import com.perisic.ring.*;
* import java.math.*;
* public class PolynomialRingExample1 { 
* public static void main(String [] args) { 
*    PolynomialRing P = new PolynomialRing(Ring.Z,"a,b");
*    RingElt a = P.map("a^2 - b^2");
*    RingElt b = P.map("(a - b)^2");
*    
*    RingElt d = P.gcd(a,b);
*    System.out.println("gcd("+a+","+b+") = "+d+" (in "+P+")"); 
*    }
*}
*</pre>
*writes "gcd(a^2 + -1*b^2,a^2 + -2*a*b + b^2) = -1*a + b (in Z[a][b])" to the output.
* <UL>
* <li> Last Change: July 03 (Documentation fixed for getTrueCoefficientAt).
* <li> Last Change:  13.12.03 (GPL)
* <li> Last Change: 01.05.11 (Minor changes in documentation
* <li>
* Copyright: (c) Marc Conrad, 2002-2011
*<li>
* Email: <a href="mailto:ring@perisic.com">ring@perisic.com</a>. 
* Please let me know if you use this software.
*<li>
* WWW: www.ring.perisic.com
*<li>The com.perisic.ring library is distributed under the terms of the 
* <a href="http://www.gnu.org/copyleft/lesser.html" target="_blank">
* GNU Lesser General Public License (LGPL)</a>. 
*<li> If you require the package under a different licence please contact me.
*<li> disclaimer: The classes are provided "as is".
* There is no warranty implied by using the com.perisic.ring package.
* </UL>
* 
* @author Marc Conrad
* @version 0.3
* @see Ring#Z
*/
public class PolynomialRing extends Ring {
    private String variable; 
    private Ring F; 
    static private int counter = 0;
    
/**
* Returns a the polynomial ring in the format 
* R[x1][x2].<!-- -->.<!-- -->.<!-- -->[xn].
* As polynomial rings are constructed recursively the
* general form is S[a] where a is the variable of the
* polynomial ring and S the String description of the
* coefficient ring.
*
* @return a String which describes the polynomial ring.
*/
    public String toString() {
        return F.toString()+"["+variable+"]";
        }
/**
* true if the coefficient ring is a field. False otherwise.
* This is equivalent to the condition that the Polynomial Ring
* is Euclidian.
*
* @return true if the polynomial ring is Euclidian.
*/
    public boolean isEuclidian() { 
            if( F.isField() ) return true; 
            else return false; 
            }
/**
* true if the coefficient ring and therefore also the polynomial ring
* is an uniqe factorization domain. False otherwise.
*
* @return true if the polynomial ring is an UFD
*/
    public boolean isUFD() { 
            if( F.isUFD() ) return true; 
            else return false; 
            }
/**
* Constructs a polynomial ring in one variable over the
* coefficient ring R with a unique (new) variable.
*/ 
   
   PolynomialRing(Ring R) {
           super();
           this.F = R;
           this.variable = "_@"+counter; 
           counter++;
           }
/**
*  Constructs a new polynomial ring over one or more variables.
*  Examples: <P>
*  <UL>
*  <li>
*  <code>P = new PolynomialRing(Ring.Z, "a,x1,x2,x3,y");</code> generates
*  P = Z[a][x1][x2][x3][y]. 
*  <LI>
*  <code>S = new PolynomialRing(new QuotientField(P),"W");</code>
*  with P as before, generates
*  S = Z(a,x1,x2,x3,y)[W].
*  </UL>
*  Notes:
*  <UL>
*  <LI> Variables names should start with a letter followed by 
*  letters or numbers. However every combination of characters
*  is possible but might lead to unexpected behaviour when 
*  parsing from a string or mapping from/to the PolynomialRing. 
*  <LI> PolynomialRings are constructed recursively. So, 
* <code>S = new PolynomialRing(R,"a,b");</code> is the same as 
* <code>T = new PolynomialRing(R,"a"); S = new PolynomialRing(T,"b");
* </UL>
*
*  @param variables is a comma separated list of alphanumeric characters.
*  @param R is the (global) Coefficient Ring 
*/       
    public PolynomialRing(Ring R, String variables) {
           super();
           
           int y = variables.lastIndexOf(",");
           if( y != -1 ) {
                 this.F = new PolynomialRing(R,variables.substring(0,y));
                 this.variable = variables.substring(y+1);
                 }
           else {
              this.F = R; 
              this.variable = variables; 
              }
           
           }
/**
*  Constructs a new polynomial ring over variables.<!-- -->length variables.
*  The behaviour is similar as of PolynomialRing(Ring,java.lang.String).
*
*  @see PolynomialRing#PolynomialRing(Ring,java.lang.String).
*
*  @param variables an String array of alphanumeric characters.
*  @param R is the (global) Coefficient Ring 
*/       
    public PolynomialRing( Ring R, String [] variables ) {
           super();     
           Ring  P = R;
           for( int i = 0; i < variables.length - 1; i++) {
              P = new PolynomialRing(P, variables[i]);
              }
           this.F = P;
           this.variable = variables[variables.length-1]; 
           }
/**
*  Returns the Ring R sucht that the PolynomialRing is the 
*  Ring R[x] where x is the variable of the PolynomialRing.
*  So for Z[a][b] we have R = Z[a] and x = b.
*
*  @return The (local) coefficient ring.
*/          
    public Ring getCoefficientRing() { return F; }
    
/**
*  Returns the variable x sucht that the PolynomialRing is the 
*  Ring R[x] where R is a Ring.
*  So for Z[a][b] we have R = Z[a] and x = b.
*
*  @return The main variable.
*/       
    public String getVariable() { return variable; }
    
/**
* Returns the sum of the parameters.
* @param p a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @param q a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @return p + q.
*/
    public RingElt add( RingElt p, RingElt q) {
         PolynomialRingElt a = (PolynomialRingElt) map(p);
         PolynomialRingElt b = (PolynomialRingElt) map(q);
         
         int minDeg = Math.min(a.getDegree(), b.getDegree()); 
         PolynomialRingElt c;
         

   
        if( minDeg == a.getDegree() ) {  
              c = new PolynomialRingElt(this, b.getDegree());
              for( int i = b.getDegree(); i > minDeg; i-- ) { 
                    c.setCoefficientAt(b.getCoefficientAt(i),i);
                    }
              }
         else { 
              c = new PolynomialRingElt(this, a.getDegree());
              for( int i = a.getDegree(); i > minDeg; i-- ) { 
                    c.setCoefficientAt(a.getCoefficientAt(i),i);
                    }
              }
         for( int i = minDeg; i  >= 0; i-- ) { 
                RingElt ai = a.getCoefficientAt(i);
                RingElt bi = b.getCoefficientAt(i);
                RingElt ci; 
               
                if( ai == null ) { ci = bi; } 
                else if( bi == null ) { ci = ai; } 
                else { ci = F.add(ai,bi); }

                if( ci != null && !F.equalZero(ci) ) { 
                    c.setCoefficientAt(ci,i);
                    }
                }
         return c; 
         }
/* The performance of this method can be improved. It is in fact not 
necessary always to generate a new PolynomialRingElt, but we 
can add the result imediately to something.
*/
private PolynomialRingElt multByMonomial(PolynomialRingElt p, 
                         RingElt a, int exponent) {
           if( a == null ) { return (PolynomialRingElt) this.zero(); }
           PolynomialRingElt c = new PolynomialRingElt(this, p.getDegree()+exponent);
           for(int i = p.getDegree(); i >= 0; i-- ) {
                RingElt ci = p.getCoefficientAt(i);
                if( ci != null ) {
                     ci = F.mult(ci,a);
                     c.setCoefficientAt(ci, i + exponent);
                     }
           
               }
           return c;
           }
/**
* Returns the product of the parameters.
* @param p a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @param q a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @return p * q.
*/
    public RingElt mult( RingElt p, RingElt q) {
         PolynomialRingElt b = (PolynomialRingElt) map(p);
         PolynomialRingElt a = (PolynomialRingElt) map(q);
         RingElt c = this.zero();
         for( int i = a.getDegree(); i>= 0; i-- ) {
             RingElt ai = a.getCoefficientAt(i);
             PolynomialRingElt h = multByMonomial(b,ai,i);
             c = this.add(h,c);
             }         
         return c; 
         }
         
/**
* Returns an array { p/q, p%q }. Here, p/q is euclidian division and
* p/q the remainder arising from euclidian division.
* <P>
* Note: For non Euclidian rings this method still works if the leading
* coefficient of q is a unit in the Coefficient Ring.
*  
* @param p a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @param q a RingElt which can be mapped to via map(RingElt) an element of this ring.
* @return An Array { p/q, p%q } of Euclidian quotient and remainder.
*/
    public RingElt [] divmod( RingElt p, RingElt q) {
         PolynomialRingElt b = (PolynomialRingElt) map(p);
         int bd = b.getDegree();
         PolynomialRingElt a = (PolynomialRingElt) map(q); 
         int ad = a.getDegree();
         PolynomialRingElt res = new PolynomialRingElt(this, bd - ad);
         //System.out.println("a = "+a);
         //System.out.println("b = "+b);
         while( (bd = b.getDegree()) >= ad ) {
              
            RingElt c = F.tdiv(b.getCoefficientAt(bd), a.getCoefficientAt(ad));
            b = (PolynomialRingElt) this.sub(b,multByMonomial(a,c,bd-ad));
            res.setCoefficientAt(c, bd-ad);
            }
         RingElt [] result = { res, b};
         return result;
         }
/**
* Returns p/q (Euclidian division).
* @param p a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @param q a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @return p/q (Euclidian quotient).
*/    
    public RingElt ediv( RingElt p, RingElt q) {
      
         return divmod(p,q)[0];
         }
/**
* Returns p/q (true division). If the division cannot be performed, that is
* p%q is not equals 0, a RingException is thrown.
* @param p a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @param q a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @throws RingException for p%q != 0.
* @return p/q (true quotient).
*/    
   public RingElt tdiv(RingElt p, RingElt q) {
        RingElt [] result = divmod(p,q);
        if(equalZero(result[1])){
            return result[0];
        } else {
          throw new RingException("Tried to true divide "+p+" by "+q+
          " which gives a remainder of "+result[1]);
          }
        }
        
/**
* Returns p%q (remainder of Euclidian division).
* @param p a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @param q a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @return p%q (remainder of Euclidian division).
*/    
   public RingElt mod( RingElt p, RingElt q) {
         return divmod(p,q)[1];
         }
/**
* Greatest common divisor of the parameters. 
*
* @param p a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @param q a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @return gcd(p,q).
*/
    public RingElt gcd( RingElt p, RingElt q) {
         RingElt a = map(p); 
         RingElt b = map(q);
         if( a == b ) { return a; }
         if( equalZero(b) ) { 
           if( equalZero(a) ) { 
             throw new RingException("Trying to compute gcd("+b+","+a+".");
           } else { 
                if( F.isField() ) {  return normalize(a); }
                else { return a; } 
                }
         } 
         if( equal(a,one()) || equal(b,one()) ) { return one(); }
         else if( equal(a,b) ) { return a; }
         if( F.isField() ) { 
              b = super.gcd(b,a);
              return normalize(b); 
              }
         else if ( F.isUFD() ) { // Corollary 6.10.
              if( equalZero(a) ) { 
                   return b; }
              //System.out.println("In Gcd: a = "+a+" b = "+b+" this = "+this);
              boolean dcOne = true;
              RingElt ac = contents(a); 
              
              if( F.equal(ac, F.one()) == false ) { a = tdiv(a,ac); dcOne = false;  }
              RingElt bc = contents(b);
              if( F.equal(bc, F.one()) == false ) { b = tdiv(b,bc); dcOne = false; }
              RingElt d =  primitiveGcd( a,b );
              if( dcOne == false ) {
                   d = mult(d,F.gcd(ac,bc)); 
                   }
              try { 
                  // System.out.println("normalizing "+d+" to ");
                  d = normalize(d); 
                  // System.out.println(d);
              } catch( RingException tmp ) {  }
              // System.out.println("Result ("+a+","+b+" of "+this+"="+d);
              return d; 
         }
         throw new RingException( "gcd is not implemented for R[X]"+
                   " where R is not an UFD.");
         }

private void checkExtendedGcd(RingElt a, RingElt b, RingElt [] result ) { 
        RingElt h1 = mult(b,result[1]); 
        RingElt h2 = sub(h1,result[0]); 
        RingElt h3 = mod(h2,a); 
        if( !equalZero(h3) ) { 
              System.out.println("Fehler 8888: "); 
              System.out.println("a =  " + a); 
              System.out.println("b =  " + b); 
              System.out.println("d =  " + result[0]); 
              System.out.println("s =  " + result[1]); 
              System.out.println("h1 =  " + h1); 
              System.out.println("h2 =  " + h2); 
              System.out.println("h3 =  " + h3); 
              throw new RingException("Fehler 8888"); 
              }
      }
/**
* Extended greatest common divisor of the parameters. Returns an
* array { d, s } where the first entry d is the greatest common
* divisor of the parameters a and b and the second entry is 
* an inverse of b modulo a. The coefficient ring must be a field, 
* otherwise an error is thrown.
*
* @throws RingException if the coefficient ring is not a field. 
* @param a a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @param b a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @return { d, s } such that d = gcd(a,b) such that  b * s = d mod a.
*/
    public RingElt [] extendedGcd(RingElt a, RingElt b) { 
       if( !F.isField() ) { 
            throw new RingException("extendedGcd not implemented for F="+F);
            }
       RingElt d1, d2, s1, s2, s3, d; 
       d1 = a; 
       d2 = b; 
       s1 = zero(); 
       s2 = one(); 
       while( !equalZero(d2) ) { 
           RingElt [] res = divmod(d1,d2); 
           d =  res[1]; 
           d1 = d2; 
           d2 = d; 

           s3 = sub(s1,mult(res[0], s2));
           s1 = s2; 
           s2 = s3; 
           }
       RingElt lcd = leadingCoefficient(d1); 
       RingElt [] result = { div(d1,lcd), div(s1,lcd) };
       // checkExtendedGcd(a,b, result); 
       return result; 
       }
/**
* The leading coefficient of b, where b is considered as 
* an univariate polynomial. So, if b = an * x^n ... + a1 * x + a0 the
* coefficient an is returned. The return value for the null polynomial
* is 0.
*
* @param b a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @return the leading coefficient of b.
*/
public RingElt leadingCoefficient(RingElt b) {
           b = map(b); 
           int degB = ((PolynomialRingElt) b).getDegree();
           if( degB == -1 ) { return F.zero(); } 
           RingElt bn = ((PolynomialRingElt) b).getCoefficientAt(degB);
           return bn; 
    } 
/**
* The degree of b, where b is considered as 
* an univariate polynomial. So, if b = an * x^n ... + a1 * x + a0 
* with an != 0, the
* value n is returned. 
*
* @param b a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @return the degree of the univariate polynomial b.
*/
public int degree(RingElt b) {
    return ((PolynomialRingElt) map(b)).getDegree();
    }
/**
* Returns the coefficient for x^i of b (or null), where b is considered as 
* an univariate polynomial over x. So, if b = an * x^n ... + a1 * x + a0 with 
* an != 0 the
* coefficient ai is returned if ai != 0. For ai == 0 <code>null</code>
* is returned. If i > n or i < 0 a java.lang.IndexOutOfBoundException
* is thrown.<P>
* Note: If the coefficient is 0 then <code>null</code> is returned!
*
* @throws IndexOutOfBoundException for i < 0 or i > degree(b).
* @param b a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @return the i-th coefficient of b or null.
*/
public RingElt getCoefficientAt(int i, RingElt b) {
    return((PolynomialRingElt) map(b)).getCoefficientAt(i);
    }
    
/**
* Returns the coefficient for x^i of b, where b is considered as 
* an univariate polynomial over x. So, if b = an * x^n ... + a1 * x + a0
* the coefficient ai is returned. Note that while getCoefficientAt() might
* return null, getTrueCoefficientAt() always returns an element of the 
* coefficient ring. 
*
* @param b a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @return the i-th coefficient of b.
*/
public RingElt getTrueCoefficientAt(int i, RingElt b) {
    b = map(b);
    if( i < 0 || i > degree(b) ) { return F.zero(); }
    
    RingElt c = ((PolynomialRingElt) b).getCoefficientAt(i);
    if( c == null ) { return F.zero(); }
    return c;
    }
/**
b and a must be primitive polynomials! 
*/  
//    private static int count = 0;
//    private static int depth = 0;
    private RingElt primitiveGcd( RingElt b, RingElt a) {
          
            //System.out.println(" b = "+b); 
            if( equalZero(a) ) { return b; }
            else if (this.equal(b,one())) { return one(); }
            else if( this.equal(a,one())) { return one(); }
            /* 
            int lcount = count++; 
            depth++;
            System.out.print("sta c="+lcount+"d="+depth+" b="+b); 
             System.out.println(" a = "+a);
             */ 
            while( !equalZero(b) ) {
            //System.out.println("(1) primitiveGcd a = "+a); 
            //System.out.println("(1) primitiveGcd b = "+b); 
                    b = primitivePart(b);
                    int degA = ((PolynomialRingElt) a).getDegree();
                    int degB = ((PolynomialRingElt) b).getDegree();
                    if( degB >= 0 && degA >= degB) {
                      RingElt bn = ((PolynomialRingElt) b).getCoefficientAt(degB);
                      bn = F.pow(bn,degA - degB + 1);
                      a = mult(a, bn);
                      }
                    RingElt tmp = b;
            //System.out.println("(2) (a/b) primitiveGcd a = "+a); 
            //System.out.println("(2) (a/b) primitiveGcd b = "+b); 
                    b = mod(a,b);
                    a = tmp;
                    } 

/*                     depth--;
                     System.out.print("o.k c="+lcount+"d="+depth+" this="+this);
                     if( a.equals(this.one()) ) { 
                          System.out.println("  = 1 in "+this);
                          }
                     else {
                           System.out.println("...");
                           }
                           */
         return a; 
         }
/**
* Returns a normal form for the polynomial b. The normal form for 
* multivariate polynomials over a field is defined as the global 
* leading coefficient is equals 1. For multivariate polynomials over
* Z the normal form is defined as the global leading coefficient 
* being positive. 
*
* @throws RingException if the polynomial is not (consindered as multivariate)
* a polynomoial over a field or 
* Z. 
* @param b A RingElt which can be mapped via map(RingElt) to an element of this ring.
* @return A normal form of b.
*/
    public RingElt normalize(RingElt b) { 
         b = map(b);
         RingElt lc = globalLeadingCoefficient(b);
         if( lc.getRing().isField() ) { 
              return  div(b,lc);
              }
         else if( lc.getRing().equals(IntegerRing.Z) ) { 
             if( IntegerRing.toBigInteger(lc).signum() == -1 ) { 
                 return mult(b,neg(one())); 
             } else { 
                 return b; 
                 }
         } else { 
            throw new RingException("You cannot normalize the polynomial "+
                    b+" over "+b.getRing()+" because "+lc.getRing()+
                    " is neither Z nor a field.");   
            }
         }
/**
* Determins recursively the global leading Coefficient of the polynomial
* over all variables. Note that the result depends on the 
* order of the variables, e.g. the global leading coefficient of 2 * a^2 + 3 * b^3 is 
* equals 3 in Q[a][b] but 2 in Q[b][a].
*
* @param b A RingElt which can be mapped via map(RingElt) to an element of this ring.
* @return The global leading coefficient.
*/         
    public RingElt globalLeadingCoefficient(RingElt b) {
         b = map(b);
         while( b.getRing() instanceof PolynomialRing ){
            b = ((PolynomialRing) b.getRing()).leadingCoefficient(b);
            }
         return b; 
    }
    private PolynomialRingElt cashedOne = null;
/** 
* Returns 1 as an element of this Ring.
* @return 1.
*/
    public RingElt one(){
             if( cashedOne == null ) {
                 cashedOne = new PolynomialRingElt(this,0);
                 cashedOne.setCoefficientAt(F.one(), 0);
                 }
             return cashedOne; 
             }
    private PolynomialRingElt cashedZero = null;
/** 
* Returns 0 as an element of this Ring.
* @return 0.
*/
    public RingElt zero() {
             if( cashedZero == null ) {
               cashedZero = new PolynomialRingElt(this,-1);
               }
             return cashedZero; 
             }
/** 
* Returns 1/b as an element of this Ring.
* An RingException is thrown if the degree of b is 
* greater then 
* @param b A RingElt which can be mapped via map(RingElt) to an element of this ring.
* @return 1/b.
*/
    public RingElt inv( RingElt b) {
         b = map(b);
         if (((PolynomialRingElt) b).getDegree() == 0 ) {
            return construct( 0,
               F.inv(((PolynomialRingElt) b).getCoefficientAt(0)));
            }
         return super.inv(b);
          }
/** 
* Returns -b as an element of this Ring.
* @param b a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @return -b.
*/
    public RingElt neg( RingElt b) {
         PolynomialRingElt a = (PolynomialRingElt) map(b);
         PolynomialRingElt c = new PolynomialRingElt(this, a.getDegree());

         for( int i = a.getDegree(); i >= 0; i-- ) { 
                    RingElt ai = a.getCoefficientAt(i);
                    if( ai != null ) { 
                        c.setCoefficientAt(F.neg(ai),i);
                        }
                    }
         return c; 
         }
         
/** 
* Returns true if b is equals to zero, false otherwise.
* @param b a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @return true if and only if b == 0.
*/
    public boolean equalZero( RingElt b ) { 
         int deg = ((PolynomialRingElt) map(b)).getDegree();
         if( deg == -1 ) return true; 
         return false; 
         }
   
/** 
* Returns the contents of b. The contents is the greatest common divisor 
* over all coefficients. 
* @param b a RingElt which can be mapped via map(RingElt)to an element of this ring.
* @return The greatest common divisor of all coefficients of b.
*/
    public RingElt contents(RingElt b) {
         b = map(b);
         
         if( F.isField() ) { 
            // return F.one(); }
            return leadingCoefficient(b);
            }
         RingElt commonGcd = F.zero();
         
         PolynomialRingElt a = (PolynomialRingElt) b; 
         for( int i = a.getDegree(); i >= 0; i -- ) { 
             RingElt ai = a.getCoefficientAt(i);
             if( ai != null ) { 
                 commonGcd = F.gcd(commonGcd, ai); 
                 if( F.equal(commonGcd, F.one())) { return F.one(); }
                 }
             }
         return commonGcd; 
         }
/** 
* Returns b/contents(b). The contents is the greatest common divisor 
* over all coefficients. 
* @param a a RingElt which can be mapped via map(RingElt) to an element of this ring.
* @return The primitive part of b.
*/
    public RingElt primitivePart(RingElt a) {
         RingElt c = contents(a);
         if( c == F.one()) return a;
         return this.tdiv(a,c); 
         }
/**
* true if coefficient ring and variable are the same.
* False, if ob is not a PolynomialRing or coefficient ring or variable
* differ. Note that, according to this definition R[a][b] and R[b][a]
* are different.
*
* @param ob an object that may or may not be a PolynomialRing.
* @return b == this.
*/
    public boolean equals(Object ob) {
        if( ob == this ) return true;
        if( (ob instanceof PolynomialRing) && 
              ((PolynomialRing) ob).getVariable().equals(variable) &&
              ((PolynomialRing) ob).getCoefficientRing().equals(F) ) {
                 return true;
              }
        return false;
        }
        
/**
* Maps a RingElt of various other rings to this ring.
* The elements of the following Rings are mapped to elements of this PolynomialRing
* by this method.
* <UL>
* <LI> Elements of the Ring.Z of integers.
* <LI> Elements of the Ring.Q of rational numbers, if the denominator is
*      a unit in the coefficient ring.
* <LI> Elements of each other Ring which can be mapped into the coefficient
*      ring. 
* <LI> Elements of another PolynomialRing if (1) the set of variables 
*      is a subset of the set of variables of this PolynomialRing.
*      and if (2) the coefficient ring can be mapped.  
* <LI> Elements of a QuotientField if the denominator is invertible in 
*      this PolynomialRing and if numerator and denominator can be mapped
*      into the PolynomialRing.
* </UL>
* Example:<P>
* Elements of the following rings are mapped into F2[a][b][c][d]:<P>
* Q[a][b][c][d], F2[a], Z[d][b], Q[b][a], F2[d][b][c][a], Quot(Z[b][d]), ...
*
* @throws RingException if the element cannot be mapped.
* @param a a RingElt which can be mapped to an element of this ring.
* @return b == this.
*/
    public RingElt map(RingElt a ) {
         Ring Fa = a.getRing();
         if( Fa.equals(this) )  { return a; }
         //System.out.println("Embedding "+a+" of "+a.getRing()+" in "+this);
         if (Fa == F) { return construct(0,a); }
         if( Fa instanceof PolynomialRing) {
              if (((PolynomialRing) Fa).getVariable().equals(variable) ) {
              PolynomialRingElt pa = (PolynomialRingElt) a;
              PolynomialRingElt b = new PolynomialRingElt(this, pa.getDegree());
              for( int i = pa.getDegree(); i >= 0; i-- ) {
                  RingElt ai = pa.getCoefficientAt(i);
                  if( ai != null ) {
                       b.setCoefficientAt(F.map(ai),i);
                       }
              }
              return b;
              } else if ( ((PolynomialRing) Fa).getCoefficientRing() 
                            instanceof PolynomialRing ) {

              PolynomialRingElt pa = (PolynomialRingElt) a;
              if( pa.getDegree() > 0 ) {
               RingElt b = this.zero();

               RingElt X = construct(0,
                      F.map(((PolynomialRing) Fa).getVariable()));

               return evaluatePolynomial(a,X);
               }
              else if( pa.getDegree() == 0 ) {
               return ( map(pa.getCoefficientAt(0)));
              } else 
              {
              return zero();
              }
         
              }
         } else if ( Fa instanceof QuotientField ) {
           return tdiv( ((QuotientField) Fa).numerator(a),
                        ((QuotientField) Fa).denominator(a) );
         }
         a = construct(0,a);
         //System.out.println("Received: "+a+" of "+this);
         return a; 
    }
/**
* Maps a String to an element of this PolynomialRing.
* Valid Strings are combinations of variables, numbers, +,
* -, *, /, (, ), and ^ which can be sesibly interpreted as a 
* polynomial of this PolynomialRing. Restrictions: 
* ^ must be followed by a positive number (the exponent), **
* for exponentiation is not valid (use ^ instead),  
* the * must be explicitely  written (i.e. 2 * a instead of 2a). 
* Examples for valid inputs for this = R[a][b] with R = Ring.R:<P>
* a - b,  (a + b)^2 * 1.2/7, 1 + ((a + b + a * 5.0 + (3 - b)^13)^17)/10.
* <P>
* Known Bugs/Problems:<P;>
* Expressions containing arithmetic operators with an alternative meaning
* lead to problems. For example 2E-3 for the DoubleField Number 0.002 
* leads to a RingException. Use 2/(10^3) instead.
*
* @throws RingException if the String cannot be interpreted.
* @param a a RingElt of this PolynomialRing which is described by the String.
* @return b == this.
*/
   public RingElt map(String a ) {
     int y = 0;
     a = a.trim(); 
     try {
     //System.out.println("Embedding String "+a+" into "+this);
     if( a.equals(variable) ) {
          return construct(1,F.one());
          }  
     else if( (y = a.indexOf("(")) != -1  ) {
       int bc = 1;
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
       PolynomialRing P = new PolynomialRing(this);
       String sss =  a.substring(0,y) + P.getVariable()+
           (i == a.length() ? "" : a.substring(i));
       try { 
          // For performance reasons we try first to map the 
          // element directly. 
          RingElt d = P.map(sss); 
          return evaluatePolynomial(d,c);       
       } catch ( RingException tmp ) {
          QuotientField QP = new QuotientField(P);
          RingElt q = QP.map(sss);
          RingElt r = evaluatePolynomial(QP.numerator(q),c);
          RingElt s = evaluatePolynomial(QP.denominator(q),c);
          return tdiv(r,s);
          }
      
       } else if ( (y = a.indexOf("+") ) != -1 ) {
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
     else if ( (y = a.lastIndexOf("-") ) != -1 ) {
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
    
     else if ( (y = a.indexOf("*") ) != -1 ) {
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
     RingElt b = map(F.map(a)); 
     return b;
     } catch(RingException tmp1) {
       QuotientField QP = new QuotientField(this);
       return map(QP.map(a));
       
     }
    } 
/**
* Returns a Polynomial by matching exponents to 
* coefficients. All values exponents[i] must be different.
* The length of the two arrays must be the same. 
* If exponents = { e0, e1, ..., en } and 
* coefficients = { a0, a1, ..., an } the polynomial 
* a0 * X^e0 + a1 * X^e1 + ... an * X^en is returned (the coefficients are 
* mapped into the coefficient ring first), where X is the variable of
* this PolynomialRing.
* 
* @param exponents An array of positive integers.
* @param coefficients An array of objects which can be mapped
*        to elements of the Coefficient Ring.
* @return Returns the polynomial obtained by matching exponents to coefficients.
*/
    public RingElt construct(int [] exponents, Object [] coefficients ) {
      if( exponents.length != coefficients.length ) { 
             throw new RingException("Cannot construct a polynomial with "
                +"exponents.length = "+exponents.length+" and "
                +"coefficients.length = "+coefficients.length);
             }
      int maxExpo = -1;
      for( int i = 0; i < exponents.length; i++ ) {
          if( exponents[i] > maxExpo ) { maxExpo = exponents[i]; }
          }
      PolynomialRingElt c = new PolynomialRingElt(this, maxExpo); 
      for( int i = 0; i < exponents.length; i++ ) { 
               c.setCoefficientAt(F.map(coefficients[i]),exponents[i]);
               }
             return c; 
      }
/**
* Returns the Polynomial coefficient * X^exponent, where X is the variable
* of this PolynomialRing. 
* @param coefficient An object which can be mapped to an element of the 
* coefficient ring.
*
* @see PolynomialRing#map(java.lang.String)
* @see PolynomialRing#map(RingElt)
* @see Ring#map(java.math.BigInteger)
* @param exponent A positive integer.
* @return coefficient * X^exponent.
*/

    public RingElt construct(int exponent, Object coefficient ) {
      //System.out.println("PR_construct: expo="+exponent+"coeff="+coefficient+" of "+coefficient.getClass().toString()+
     //		  " this="+this+" F="+F);
      PolynomialRingElt c = new PolynomialRingElt(this, exponent); 
      c.setCoefficientAt(F.map(coefficient),exponent);
      return c; 
      }

static RingElt evaluate(Ring R, RingElt p, RingElt b) {
      return ((PolynomialRingElt) p).evaluate(R,b);
      }


private class PolynomialRingElt extends RingElt {
       private RingElt [] data;
       private int degree;
       
       
/** 
* Every negative value for maxdegree is valid and is interpreted 
* as 0 polynom. This feature is used in PolynomialRing.divmod.
*/
       public PolynomialRingElt( Ring R, int maxdegree ) {
            super(R);
            if( maxdegree < 0 ) {
               data = null;
               }
            else {
               data = new RingElt[maxdegree+1];
               }
            degree = -1;
            }
       public void setCoefficientAt( RingElt a, int exponent) {
            if( a != null && a.getRing().equalZero(a) ) { 
                if( exponent <= degree ) { 
                    data[exponent] = null;
                    }
                if( exponent == degree ) {  
                     degree = -1; 
                     for( int i = 0; i < exponent; i++ ) { 
                           if( getCoefficientAt(i) != null ) { 
                                degree = i; 
                                }
                           }
                      }
                return; 
            } 
           if( exponent > degree ) {
                 if( exponent < data.length ) {
                      degree = exponent;
                      }
                 else {
                     throw new RingException("Trying to insert a coefficient "+
                        "for x^"+exponent+", but the maximal possible degree for"+
                        " this polynomial is "+data.length);
                        }
                
                 }    
            data[exponent] = a;
            }
       public RingElt getCoefficientAt(int exponent ) {
            if( data == null ) { return null; }
            return data[exponent];            
            }
/** 
Evaluates the polynomial in the Ring R at b. That means b
is substituted for the variable of the polynomial. 
*/
       public RingElt evaluate(Ring R, RingElt b) {
    	   //System.out.println("AA: this="+this+", this.getRing()="+this.getRing()+", R="+R+", b="+b+" of "+b.getRing());
           if( degree == -1 ) {
               return R.zero();
               }
           b = R.map(b);
           RingElt result = R.map(getCoefficientAt(degree));
           if( degree != 0 ) { 
              result = R.mult(result, b);
              for( int i = degree-1; i >= 0 ; i-- ) {
                 RingElt d = getCoefficientAt(i);
                 if( d != null ) {
                    result = R.add(result,d);
                    }
                 if( i != 0 ) {
                    result = R.mult(result, b);
                    }
              }
              }
           return result;
           }

       public int getDegree() { return degree; }
       public String toString() {
           String str = "";
           RingElt a;
           boolean somethingFound = false;
           for( int i = 0; i <= degree; i++ ) {
                if( somethingFound == true ) {
                   str += " + ";
                   somethingFound = false;
                   }
                if( (a = getCoefficientAt(i)) != null ) {
                   String astr;
                   if( i == 0 || a.getRing() instanceof IntegerRing ||
                           a.getRing() instanceof DoubleField ||
                           a.getRing() instanceof F2Field ) {
                      astr = a.toString();
                      }
                   else if ( a.getRing().equals(Ring.Q) &&
                         Ring.Q.isIntegral(a) ) { 
                       
                      astr = a.toString();
                   } else if ( a instanceof PolynomialRingElt && 
                          a.toString().indexOf("+") == -1 && 
                          a.toString().indexOf("/") == -1 ) { //
                      astr = a.toString();
                   } else {
                      astr = "("+a.toString()+")";
                      }
                   if( i != 0 ) {
                      str += (astr.equals("1") ? "" : astr + "*")
                      +((PolynomialRing) getRing()).getVariable();
                      }
                   else { str += astr; }
                   if( i > 1 ) {
                      str += "^"+i;
                    }
                   somethingFound = true;
                   }
                }
           if( str.equals("") ) { str = "0"; }
     
           return str;
           }
       }
}
