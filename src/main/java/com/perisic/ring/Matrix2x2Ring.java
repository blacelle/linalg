package com.perisic.ring;

/**
* The ring of  2 x 2 matrices {{a,b},{c,d}} with a,b,c and d in B, where B is any Ring. 
* 
* <UL>
*<li> First version: 25.09.2003 by Tendiefi P Nkellefack
*<li> Last change: 06.12.2003 by Marc Conrad, changed name from MatrixRing to Matrix2x2Ring
*     and further (minor) changes. 
*<li> Last change: 01.05.2011 by Marc Conrad, small change in documentation.
*<li>
* copyright: (c) Pete Nkelle, Marc Conrad 2003-2011
*<li>
* Email: marc@pension-perisic.de. Please let me know if you use this software.
*<li>
* WWW: www.ring.perisic.com
*<li>The com.perisic.ring library is distributed under the terms of the 
* <a href="http://www.gnu.org/copyleft/lesser.html">GNU Lesser General Public License (GPL)</a>. 
*<li> If you require the package under a different licence please contact me.
*<li> disclaimer: The classes are provided "as is".
* There is no warranty implied by using the com.perisic.ring package.
* </UL>
* @author Tendiefi P Nkellefack, Marc Conrad
* @version 0.5
*/
public class Matrix2x2Ring extends Ring {
    private Ring B;
    
/**
* Returns M22(B) where B is the coefficient ring.
*/
    public String toString() { 
        return "M22("+B.toString()+")";
        }

/**
* Returns false.
*/
    public boolean isField() { return false; }
    
/**
* Returns the ring of coefficients of the matrix.
*/
    public Ring getbaseRing() { return B; }


/**
* Returns the first coefficient (element) of the matrix as an element of the base ring.
*/
    public RingElt a(RingElt m) {
           return ((Matrix2x2RingElt) map(m)).getCoefficient1();
           }

/**
* Returns the second element of the matrix as an element of the base ring.
*/
    public RingElt b(RingElt m) {
           return ((Matrix2x2RingElt) map(m)).getCoefficient2();
           }

/**
* Returns the third element of the matrix as an element of the base ring.
*/

    public RingElt c(RingElt m) {
           return ((Matrix2x2RingElt) map(m)).getCoefficient3();
           }

/**
* Returns the fourth element of the matrix as an element of the base ring.
*/

    public RingElt d(RingElt m) {
           return ((Matrix2x2RingElt) map(m)).getCoefficient4();
           }


/**
* construction of a new Matrix2x2Ring over B. The elements of this ring are
* matrices {{a, b}, {c, d}}, where a,b,c,d are elements of the BaseRing.
*@param BaseRing is the base ring
*/
    public Matrix2x2Ring(Ring BaseRing) {
           super();
           B = BaseRing;
           }

/**
* Returns the sum of a 2*2 matrix, m1 + m2.
*/
    public RingElt add( RingElt m1, RingElt m2){
         RingElt a1 = a(m1);
         RingElt b1 = b(m1);
         RingElt c1 = c(m1);
         RingElt d1 = d(m1);

         RingElt a2 = a(m2);
         RingElt b2 = b(m2);
         RingElt c2 = c(m2);
         RingElt d2 = d(m2);

         RingElt m3, a, b, c, d;

         a = B.add(a1, a2);
         b = B.add(b1, b2);
         c = B.add(c1, c2);
         d = B.add(d1, d2);

         return new Matrix2x2RingElt(this, a, b, c, d);
    }


/**
* Return the product of two 2*2 matrices, m1 * m2.
*/
    public RingElt mult(RingElt m1, RingElt m2) {

         RingElt a3 = B.add(B.mult(a(m1),a(m2)), B.mult(b(m1),c(m2)));
         RingElt b3 = B.add(B.mult(a(m1),b(m2)), B.mult(b(m1),d(m2)));
         RingElt c3 = B.add(B.mult(c(m1),a(m2)), B.mult(d(m1),c(m2)));
         RingElt d3 = B.add(B.mult(c(m1),b(m2)), B.mult(d(m1),d(m2)));


         return new Matrix2x2RingElt(this, a3, b3, c3, d3);
         }
/**
* Returns the determinant of m.
*/

public RingElt det(RingElt m1) {
    RingElt ad = B.mult(a(m1),d(m1));
    RingElt bc = B.mult(b(m1),c(m1));
    return B.add(ad,B.neg(bc));
    }
/**
* Returns the trace of m.
*/

public RingElt trace(RingElt m1) {
    return B.add(a(m1), d(m1));
    }
       
    
/**
* Returns the inverse of a matrix m1.
*/

public RingElt inv(RingElt m1){


         RingElt a1 = a(m1);
         RingElt b1 = b(m1);
         RingElt c1 = c(m1);
         RingElt d1 = d(m1);
 
         RingElt a3, b3, c3, d3, M;

         M = det(m1);

         a3 = B.tdiv(d1,M);
         b3 = B.tdiv(B.neg(b1),M);
         c3 = B.tdiv(B.neg(c1),M);
         d3 = B.tdiv(a1,M);

         return new Matrix2x2RingElt (this, a3, b3, c3, d3);
}



/**
* Returns the Identity matrix, I = {{ 1, 0 } { 0, 1}}.
*/
    public RingElt one(){
             return new Matrix2x2RingElt(this, B.one(), B.zero(), B.zero(), B.one());
            }
/**
* Returns the zero matrix { { 0, 0 } { 0, 0 }}.
*/
    public RingElt zero() {
             return new Matrix2x2RingElt(this, B.zero(), B.zero(), B.zero(), B.zero());
             }

/**
* Returns the negation of a matrix, -m1.
*/



public RingElt neg( RingElt m1) {
         return new Matrix2x2RingElt(this, B.neg(a(m1)), B.neg(b(m1)), B.neg(c(m1)), B.neg(d(m1)));
}


    
/**
* Returns true if the matrix m1 == 0. False otherwise.
* I.e. It tests for the zero matrix
*/
    public boolean equalZero( RingElt m1) {
         if (!B.equalZero(a(m1))) {return false; }
         if(!B.equalZero(b(m1))) {return false;}
         if (!B.equalZero(c(m1))) {return false;}
         if (!B.equalZero(d(m1))) {return false;}
         return true;
        }


/**
* constructs the elements of a 2*2 matrix,  a, b, c, and d.
*/

    public RingElt construct(RingElt a, RingElt b, RingElt c, RingElt d) {
          return new Matrix2x2RingElt(this, B.map(a), B.map(b), B.map(c), B.map(d));
          }

/**
*         requires a String of the form {{ **, **}{**, ** }} where the ** are
*         mapped to the coefficient ring.  TODO 
*/


    private String outerLeftDelimiter = "{";
    private String innerLeftDelimiter = "{";
    private String elementSeparator = ",";
    private String innerRightDelimiter = "}";
    private String outerRightDelimiter = "}";
/** 
*     Sets the format that is used to read and write matrices. E.g. a call 
*     setMatrixFormat("{", "{", ",", "}", "}" ); reads matrices in 
*     the format { { xxx, yyy } { uuu, www } }; This is the default.
*     More examples:<br> setMatrixFormat("[", "(", ":", ")", "]" );
*     reads and writes  matrices in the form [( xxx : yyy )( uuu : www ) ].
*     <br>setMatrixFormat("","",",","\n",""); reads and writes matrices in 
*     a block structure, e.g. "2 3\n4 5".
*/
    public void setMatrixFormat(String outerLeftDelimiter, 
                               String innerLeftDelimiter,
                               String elementSeparator,
                               String innerRightDelimiter,
                               String outerRightDelimiter ) 
              { 
              this.outerLeftDelimiter = outerLeftDelimiter;
              this.innerLeftDelimiter = innerLeftDelimiter;
              this.elementSeparator = elementSeparator;
              this.innerRightDelimiter = innerRightDelimiter;
              this.outerRightDelimiter  = outerRightDelimiter;
              }
/**
    Same as setMatrixFormat(arg[0], arg[1], arg[2], arg[3], arg[4]). 
    Correponds to getMatrixFormat,
*/
    void setMatrixFormat(String [] arg) {
        setMatrixFormat(arg[0], arg[1], arg[2], arg[3], arg[4] );
        }
        
/**
*   returns the format of the matrices. See setMatrixFormat() 
*   for details. 
*/
              
    public String [] getMatrixFormat() {
        String [] result = { outerLeftDelimiter, innerLeftDelimiter, elementSeparator,
                                 innerRightDelimiter, outerLeftDelimiter };
        return result;
        } 
/**
* Maps a 2x2 matrix m into this. If m is not a 2x2 matrix, 
* it will be mapped first into B and then into 
* the matrix {{w, 0} {0, w}}. 
*/        

    public RingElt map( RingElt m ) {
        Ring mR = m.getRing();
        if( mR == this ) { return m; }
        if( mR instanceof Matrix2x2Ring ) {
            Matrix2x2Ring M = (Matrix2x2Ring) mR;
            return construct(M.a(m), M.b(m), M.c(m), M.d(m));
            }
        else {
            RingElt w = B.map(m);
            return construct(w, B.zero(), B.zero(), w);
            }
        }

/**
*    Maps a matrix of the form { { xxx, yyy } { uuu, vvv } } into this ring. The
*    input format of the matrix can be changed using the setMatrixFormat() methods.
*/        
        
    public RingElt map(String str) { 
     String theOriginalStr = str;
     int y = str.indexOf(outerLeftDelimiter);
     System.out.println("AA: str = "+str);
     if( y == -1  ) { 
          RingElt t = B.map(str);
          return this.map(t);
          //throw new RingException ( "Matrix does not start with {");
          }
     str = str.substring(y + outerLeftDelimiter.length());
     System.out.println("AB: str = "+str);
     str = str.substring( str.indexOf(innerLeftDelimiter) + innerLeftDelimiter.length() );
     System.out.println("AC: str = "+str);
     
     RingElt result [] []  = new RingElt[2][2];
     
     for( int i = 0; i < 2; i++ ) {
      for( int j = 0; j < 2; j++ ) {
         System.out.println("AD"+i+","+j+": str = "+str);
         boolean curlyFound = false;
         int kk = str.indexOf(innerRightDelimiter);
         y = str.indexOf(elementSeparator);
         if( y == -1 || kk < y ) {
            curlyFound = true;
            y = kk;
            }    
         if( y == -1 ) {
             throw new RingException("Cannot parse "+theOriginalStr+" as an"+
             " element of "+this);
             }
         result[i][j] = B.map(str.substring(0,y));
         if( curlyFound == false ) {str = str.substring(y+elementSeparator.length()); }  
         else { str = str.substring( str.indexOf(innerLeftDelimiter)+
                             innerLeftDelimiter.length()); } 
        
         }
        }
     Matrix2x2RingElt c = new Matrix2x2RingElt(this, result[0][0], result[0][1], 
                                                     result[1][0], result[1][1]);
     return c;
    } 
    
/**
* Returns the matrix m as a String.  Default format is { { xxx, yyy } {uuu, vvv } }
* See the documentation of setMatrixFormat() for 
* changing the output format.
**/
    
    public String eltToString(RingElt m ) {
        return outerLeftDelimiter+innerLeftDelimiter+
             "" + a(m).toString() + elementSeparator + b(m).toString() + innerRightDelimiter + 
             innerLeftDelimiter + c(m).toString()+ elementSeparator + d(m).toString() + innerRightDelimiter+
             outerRightDelimiter;
        }
        
        
     private class Matrix2x2RingElt extends RingElt {
       private RingElt a;
       private RingElt b;
       private RingElt c;
       private RingElt d;
/**
*Populate matrix coefficients(elements) in the Ring
**/
public Matrix2x2RingElt(Ring R, RingElt a, RingElt b, RingElt c, RingElt d) {
           super(R);
           this.a = a;
           this.b = b;
           this.c = c;
           this.d = d;

           }

       public RingElt getCoefficient1() { return a; }//the first coefficient of the matrix
       public RingElt getCoefficient2() { return b; }//the secong coefficient of the matrix
       public RingElt getCoefficient3() { return c; }//the third coefficient of the matrix
        public RingElt getCoefficient4() { return d; }//the fouth coefficient of the matrix

/** Returns a string which describes the matrix
*/
       public String toString() {
                String c1 = a.toString();
                String c2 = b.toString();
                String c3 = c.toString();
                String c4 = d.toString();
                Matrix2x2Ring R = (Matrix2x2Ring) getRing();
                return R.eltToString(this);
           }


   }

/**
* A simple test method.
*/
public static void main(String [] args) {

    Ring Qabcd = new QuotientField(new PolynomialRing(Ring.Z,"a,b,c,d,e,f,g,h"));
    Matrix2x2Ring M22 = new Matrix2x2Ring(Qabcd);

    RingElt m1 = M22.map( "{{ a, b } { c, d }}" );
    RingElt m2 = M22.map( "{{ e, f } { g, h }}" );

    M22.setMatrixFormat("\n","",",","\n","");
    System.out.println("\nm1 = "+m1);
    System.out.println("\nm2 = "+m2);

    System.out.println("\n\nThe sum m1+m2 = "+M22.add(m1,m2));
    System.out.println("\n\nThe difference m1-m2 = "+M22.sub(m1,m2));
    System.out.println("\n\nThe product m1*m2 = "+M22.mult(m1,m2));
    System.out.println("\n\nThe Inverse of m1 = "+M22.inv(m1));

    System.out.println("\n\nThe product m1*Inv(m1)) is the identity element I = "+ 
          M22.mult(m1,M22.inv(m1)));

    Ring Zabcd = new PolynomialRing(Ring.Z,"a,b,c,d");
    Ring Zabcdx = new PolynomialRing(Zabcd, "X");
    Matrix2x2Ring MX22 = new Matrix2x2Ring(Zabcdx);
    System.out.println("\nmapping X to M22: "+ MX22.map("X"));
    RingElt u = MX22.sub(m1,MX22.map("X"));
    System.out.println("u = m1-X="+u);
    RingElt du = MX22.det(u);
    System.out.println("det(u) = "+du);
    System.out.println("Evaluation for X = m1:"+M22.evaluatePolynomial(du,m1));
    
    /* A different way to compute the characteristic polynomial */
    
    Ring M22Y = new PolynomialRing(M22, "Y");
    RingElt m1y = M22Y.sub(m1, M22Y.map("Y"));
    System.out.println("m1y = m1 - y = "+m1y);
    RingElt v = MX22.evaluatePolynomial(m1y,(new PolynomialRing(Ring.Z, "X")).map("X"));
    if( MX22.equal(v,u) == false ) { 
        System.out.println("Error: v = "+v+", u = "+u);
    } else { 
        System.out.println("m1y evaluated for Y = X in "+MX22+" equals "+u+" o.k.");
        }
        
    System.out.println("m1^2 - trace(m1) * m1 + det(m1) = "+
            M22.sub(M22.pow(m1,2), M22.sub(M22.mult(M22.trace(m1),m1),M22.det(m1)))); 
    }
}
