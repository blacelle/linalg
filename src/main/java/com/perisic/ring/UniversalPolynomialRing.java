package com.perisic.ring;

import java.util.HashSet;

/**
* The field of polynomials over all
* allowed variable names as variables.
* That is, say in this ring you can add a polynomial
* a^2 + b of Z[a][b] with b - c of Z[b][c] and get as
* result a polynomial of the ring Z[a][b][c] (the order
* of the variables is undefined and may vary).
*
* <UL>
*<li>Generated January 16, 2003.
*<li>Last Change: April 18, 2003: Exception list of variables.
* <li> Last Change: 13.12.2003: GPL MC. 
* <li> Last Change: 01.05.2011: Added methods evaluatePolynomial
*<li>
* Copyright:(c) Marc Conrad, 2002, 2003,2011 
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
* 
* Example:
* 
* <pre>
public static void main(String [] args) {
// Define a polynomial ring over R
	UniversalPolynomialRing U = new UniversalPolynomialRing(Ring.R);
	RingElt poly1 = U.map("0.5 * ((x1 - x2)^2 - a * (x1 + x2)^2)");
	System.out.println("poly1="+poly1+" of "+poly1.getRing());
	
// 1st Example to substitute variables:
	String [] variables = { "x1", "x2" }; 
	RingElt [] values = { Ring.R.map(2.3), Ring.R.map(1.7) };
	RingElt poly2 = U.evaluatePolynomial(poly1, variables, values); 
	System.out.println("Example1: poly2="+poly2+" of "+poly2.getRing());
	
//	2nd Example to substitute variables:
	RingElt poly3 = U.evaluatePolynomial(poly1, "a", Ring.R.map(-1)); 
	System.out.println("Example2: poly3="+poly3+" of "+poly3.getRing());
	
//	3rd Example to substitute variables:
	RingElt poly4 = U.evaluatePolynomial(poly3, variables, values); 
	System.out.println("Example3: poly4="+poly4+" of "+poly4.getRing());
}
</pre>
Produces the following output: 
<pre>
poly1=(0.5 + -0.5*a)*x2^2 + ((-1.0 + -1.0*a)*x2)*x1 + (0.5 + -0.5*a)*x1^2 of R[a][x2][x1]
Example1: poly2=0.18000000000000016 + -7.999999999999999*a of R[a]
Example2: poly3=1.0*x2^2 + 1.0*x1^2 of R[x2][x1]
Example3: poly4=8.18 of R
</pre>
* @author Marc Conrad
* @version 0.4
*/  
public class UniversalPolynomialRing extends UniversalRing {

/**
*  The ring of the coefficients.
**/

private Ring coeffRing;
/**
* Construct the universal polynomial field by the ring of coefficients.
**/

public UniversalPolynomialRing(Ring coefficientRing) {
           super();
           coeffRing = coefficientRing;
           }
/**
* @return "R[...]" where R is the coefficient ring.
*/
    public String toString() {
        return coeffRing.toString()+"[...]";
        }
/**
* @return the coefficient ring
*/

Ring getCoefficientRing() {
         return coeffRing;
         }

/**
* true if the coefficient ring and therefore also the polynomial ring
* is an uniqe factorization domain. False otherwise.
*
* @return true if the polynomial ring is an UFD
*/
    public boolean isUFD() {
            if( coeffRing.isUFD() ) return true;
            else return false;
            }

    private java.util.HashSet exceptionVariables = null;
/** 
*   allows to give a list of variables which are not considered when 
*   a String is mapped into an universal polynomial ring. 
*   For instance when using universal polynomials over complex numbers it
*   is necessary to make <em>i</em> an exceptional variable.  
*/  
    void addExceptionVariable(String str) { //
          if( exceptionVariables == null ) {
                exceptionVariables = new java.util.HashSet();
                }
          exceptionVariables.add(str);
          }  
/**
*  removes all exception variable. See getExceptionVariableList() for 
*  details.
*/           
    void clearExceptionVariables() {
          exceptionVariables = null;
          }
/**
*   returns a list of the exceptional variables. See setExceptionVariableList()
*   for details. Might be null.
*/
    String [] getExceptionVariableList() {
        return (String []) exceptionVariables.toArray(new String[1]);
        }    
            
    private String [] findVariableNames(String str ) {
// Bug fixed in V3. This was LinkedList in V2 which leads to things like R[b][b].
     java.util.HashSet L = new HashSet();

     boolean z = false;
     String w = "";
     for( int i = 0; i < str.length(); i++ ) {
        char c = str.charAt(i);
        if(    (z == false && Character.isJavaIdentifierStart(c))
            || (z == true && Character.isJavaIdentifierPart(c)) ){
          z = true; w = w + c;
        } else if( z == true ) {
           if( exceptionVariables == null || 
               !exceptionVariables.contains(w) ) {
                    L.add(w);
                    }
           w = "";
           z = false;
        }
     }
     if( z == true ) {
       if( exceptionVariables == null || 
           !exceptionVariables.contains(w) ) {
                    L.add(w);
                    }
       }
     if( L.isEmpty() ) { return null; }
     return (String []) L.toArray(new String[1]);
    }

/**
* All Java identifiers are allowed as variables. That means the
* first character is true with
* java.lang.Character.isJavaIdentifierStart(),
* the following chracters return true with
* Character.isJavaIdentifierPart()
*/
    public RingElt map( String str ) {
        String [] vars = findVariableNames(str);
        if (vars == null ) { return coeffRing.map(str); }
        Ring P = new PolynomialRing(coeffRing, vars);
        return P.map(str);
        }

/**
*  @return the ring of coefficients.
*/

   public Ring findRing() {
       return coeffRing;
       }

/**
*  The ring over the coefficient ring with the variables of
*  a.getRing().
*/
   public Ring findRing( RingElt a ) {
      java.util.LinkedList L = new java.util.LinkedList();
      /** collect the variables */
      Ring R = a.getRing();
/* collect the variables of R into a list */
      while( !R.equals(coeffRing) &&  (R instanceof PolynomialRing) ) {
          PolynomialRing PR = (PolynomialRing) R;
          L.addFirst(PR.getVariable());
          R = PR.getCoefficientRing();
          }
      if( L.isEmpty() ) {
          return coeffRing;
          }
      else {
        return new PolynomialRing( coeffRing, (String []) L.toArray(new String[1]));
        }
      }
/**
*         The result is the coefficient ring over the variables of
*         a.getRing() and the variables of b.getRing().
*/

public Ring findRing(RingElt a, RingElt b) {

       java.util.HashSet H = new java.util.HashSet();

       Ring R = a.getRing();
       while( !R.equals(coeffRing) &&  (R instanceof PolynomialRing) ) {
          PolynomialRing PR = (PolynomialRing) R;
          H.add(PR.getVariable());
          R = PR.getCoefficientRing();
          }
       Ring S = b.getRing();
       if( S != R ) { /* not necessary but might increase performance */
         while( !S.equals(coeffRing) &&  (S instanceof PolynomialRing) ) {
          PolynomialRing PR = (PolynomialRing) S;
          H.add(PR.getVariable());
          S = PR.getCoefficientRing();
          }
          }

       if( H.isEmpty() ) {
          return coeffRing;
          }
       else {
          return new PolynomialRing( coeffRing, (String []) (H.toArray(new String [1])));
          }
       }
/**
 * Returns all the variables that really appear within p (e.g. returns a and b for 
 * p = a^2 + b^2 in R[x,a,b,c,d] )
 * @param p
 * @return
 */
private HashSet findUsedVariableSet(RingElt p) { 
	p = map(p); 
	HashSet varUsed = new HashSet(); 
	if (equalZero(p)) { 
		return varUsed; 
	}
	if( p.getRing() instanceof PolynomialRing ) {
		PolynomialRing pR = (PolynomialRing) p.getRing();
		String varPR = pR.getVariable(); 
		
		int pDeg = pR.degree(p); 	
		for( int i = 0; i <= pDeg; i++ ) { 
			RingElt pi = pR.getCoefficientAt(i,p); 
			if( pi != null) { 
				if(i != 0) { varUsed.add(varPR); }
				varUsed.addAll(findUsedVariableSet(pi)); 
			} 
		}
		return varUsed; 
		
	} 
	else {
		return varUsed;
	}
}
/**
 * Reduces the polynomial into a polynomial of 
 * the polynomial ring with the fewest variables.
 * For instance if p = a^2 + b^2 is an element of Z[x][a][y][b][c] 
 * then reduceVariables(p) = a^2 + b^2 in Z[a][b].
 */

public RingElt reduceVariables(RingElt p) { 
	RingElt result = map(p); 
	HashSet usedVars = this.findUsedVariableSet(p); 
	Ring R = result.getRing();
	/* collect the variables of R into a list */
	while( !R.equals(coeffRing) &&  (R instanceof PolynomialRing) ) {
		PolynomialRing PR = (PolynomialRing) R;
		String varPR = PR.getVariable();
		//System.out.println("Found varPR = "+varPR); 
		if( usedVars.contains(varPR) == false ) { 
			//System.out.println("Eliminating "+varPR); 
			result = evaluatePolynomial(result,varPR,coeffRing.zero() );
		}
		R = PR.getCoefficientRing();
	}
	return result; 
}

/**
 * Evaluates the polynomial p at the variables var[i] with the values b[i].
 * The lengths of var and b must be the same and is not checked. 
 */

public RingElt evaluatePolynomial(RingElt p, String [] var, RingElt [] b) {
	RingElt result = map(p); 
	for(int i = 0; i < var.length; i++ ) { 
		result = evaluatePolynomial(result, var[i], b[i]); 
	}
	return result;
}

/**
* Evaluates the Polynomial p (which may be defined over more than one variable) 
* at b for the variable var. If p is not a polynomial or if p (as a polynomial)
* does not contain the variable var the polynomial p is returned (p is the considered 
* a constant in respect to var). 
*/
public RingElt evaluatePolynomial(RingElt p, String var, RingElt b) {
	//System.out.println("Enter evaluatePolynomial with p="+p+" var="+var+" b="+b); 
	p = map(p); 
	if( p.getRing() instanceof PolynomialRing ) {
		PolynomialRing pR = (PolynomialRing) p.getRing();
		String varPR = pR.getVariable(); 
		//System.out.println("Case p is an instanceOfPolynomialRing p="+p+",pR="+pR); 
		if (varPR.equals(var)) {
			//System.out.println("Case 1: var="+var+", p="+p+" of "+p.getRing()); 
			b = map(b); 
			//System.out.println("b="+b+" of "+b.getRing()); 
			RingElt result = evaluatePolynomial(p,b); 
			//System.out.println("resutl="+result+ "of "+result.getRing()+"findRing(result)="+findRing(result)); 
			RingElt temp = map(result); 
			//System.out.println("temp="+temp); 
			return temp; 
		} else { 
			//System.out.println("Case 2: var="+var+", p="+p+" of "+p.getRing()); 
			int pDeg = pR.degree(p); 
			RingElt result = this.zero(); 
			for( int i = 0; i <= pDeg; i++ ) { 
				RingElt pi = pR.getCoefficientAt(i,p); 
				
				if( pi != null) { 
					//System.out.println("i="+i+", pi="+pi+" of "+pi.getRing()); 
					RingElt pj = this.evaluatePolynomial(pi,var,map(b)); 
					if( i > 0 ) { 
				//		System.out.println("Constructing for i="+i+", pR="+pR+" pj="+pj+" of "+pj.getRing()+"pR.cR="+pR.getCoefficientRing());
						PolynomialRing pRVar = new PolynomialRing(this.coeffRing,varPR);
						RingElt pjC = pRVar.construct(i,this.coeffRing.one());
						RingElt monomial = this.mult(pj,pjC);
						//System.out.println("monomial="+monomial+", result="+result+", fR="+findRing(monomial,result)); 
						result = this.add(result,monomial); 
					} else if( i == 0 ) { 
						result = this.add(result,pj); 
					}
					//System.out.println("result="+result+" of "+result.getRing());
				}
			}
			return result; 
		}
	} 
	else {
		//System.out.println("Case 3: var="+var+", p="+p+" of "+p.getRing()); 
		return map(p);
	}
}

private static void test001() {
	Ring QAB = new PolynomialRing(Ring.Q, "a,b,c,d");
	Ring ZCD = new PolynomialRing(Ring.Z, "c,d,a");
	
	RingElt w1 = QAB.map("a * a - 2 * b * a");
	RingElt w2 = ZCD.map("(c + d - a)^2");
	
	UniversalPolynomialRing U = new UniversalPolynomialRing(Ring.R);
	//U.addExceptionVariable("i");
	RingElt w5 = U.map("i^2 + b^2");
	System.out.println("w5 = "+w5.toString());
	
	RingElt w3 = U.add(w1,w2);
	System.out.println("w3="+w3);
	System.out.println(w3.getRing());
	
	RingElt w4 = U.map("x + y + z*d-a^3-K33");
	System.out.println("w4="+w4);
	System.out.println(w4.getRing());
	for( int i = 0; i < 1; i++ ) {
		w4 = U.add(w3,w4);
		System.out.println(w4);
		System.out.println(w4.getRing());
	}
	RingElt u1 = U.map("b^2 - b");
	System.out.println("u1="+u1+", of "+u1.getRing());
	RingElt r1 = U.evaluatePolynomial(w1,"a",u1);
	System.out.println("w1="+w1+" of "+w1.getRing()); 
	System.out.println("r1="+r1+" of "+r1.getRing()); 
	
	String [] vars1 = {"c", "d", "b" };
	RingElt [] vals1 = {Ring.R.map(7.2), Ring.R.map(8.1), U.map("5")};
	RingElt r2 = U.evaluatePolynomial(r1,vars1,vals1);
	System.out.println("r2="+r2+" of "+r2.getRing());  
	
	Ring R10 = new PolynomialRing(Ring.R,"z,a,w,w,x,b,y,q,r,s"); 
	RingElt r10 = R10.map("234098234 + y * y * y * (a^2 * (a - b) + (z-y)^13)"); 
	System.out.println("r10="+r10+" of "+r10.getRing()); 
	RingElt r10red = U.reduceVariables(r10);
	System.out.println("r10red="+r10red+" of "+r10red.getRing()); 
	
	RingElt [] vals10 = {Ring.R.map(2), Ring.R.map(2.00000001) };
	String [] var10 = {"b", "a" }; 
	
	RingElt r10ev = U.evaluatePolynomial(r10,var10,vals10); 
	System.out.println("r10ev="+r10ev+" of "+r10ev.getRing()); 
	
	RingElt r10evRed = U.evaluatePolynomial(r10red,var10,vals10); 
	System.out.println("r10evRed="+r10evRed+" of "+r10evRed.getRing()); 
	
	System.out.println("Sub="+U.sub(r10evRed,r10ev)); 
}

/**
 * Simple test method. 
 * @param args
 */
public static void main(String [] args) {
	// Define a polynomial ring over R
	UniversalPolynomialRing U = new UniversalPolynomialRing(Ring.R);
	RingElt poly1 = U.map("0.5 * ((x1 - x2)^2 - a * (x1 + x2)^2)");
	System.out.println("poly1="+poly1+" of "+poly1.getRing());
	
	// 1st Example to substitute variables:
	String [] variables = { "x1", "x2" }; 
	RingElt [] values = { Ring.R.map(2.3), Ring.R.map(1.7) };
	RingElt poly2 = U.evaluatePolynomial(poly1, variables, values); 
	System.out.println("Example1: poly2="+poly2+" of "+poly2.getRing());
	
//	2nd Example to substitute variables:
	RingElt poly3 = U.evaluatePolynomial(poly1, "a", Ring.R.map(-1)); 
	System.out.println("Example2: poly3="+poly3+" of "+poly3.getRing());
	
//	3rd Example to substitute variables:
	RingElt poly4 = U.evaluatePolynomial(poly3, variables, values); 
	System.out.println("Example3: poly4="+poly4+" of "+poly4.getRing());
}
}
