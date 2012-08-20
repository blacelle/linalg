package com.perisic.ring;

/**
* An abstract class for an universal ring. It can be used to
* implement a cyclotomic field of all unit roots (an inifinite
* algebraic extension) or a polynomial ring over an infinite
* number of variables. See the abstract method
* findRing(...) for
* details.
*
* <UL>
*<li>Generated January 16, 2003
* <li> Last Change: 13.12.2003: GPL, MC
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
abstract public class UniversalRing extends Ring {

/**
* No parameters in the constructor
**/

public UniversalRing() {
           super();
           }
/**
* @return "(unspecified) Universal Ring"
*/
    public String toString() { return "(unspecified) Universal Ring"; }

/**
* Addition.
* @return a + b.
*/

    public RingElt add( RingElt a, RingElt b) {
         Ring R = findRing(a,b);
         return R.add(a,b);
         }
/**
* Multiplication.
* @return a * b
*/
    public RingElt mult( RingElt a, RingElt b) {
        Ring R = findRing(a,b);
        return R.mult(a,b);
         }

/**
* True division.
* @return a / b
*/
    public RingElt tdiv( RingElt a, RingElt b) {
        Ring R = findRing(a,b);
        return R.tdiv(a,b);
         }
/**
* Euclidian division.
* @return S.ediv(a,b) for a suitable ring S.
*/
    public RingElt ediv( RingElt a, RingElt b) {
        Ring R = findRing(a,b);
        return R.ediv(a,b);
         }
/**
* Modular computation.
* @return a % b
*/
    public RingElt mod( RingElt a, RingElt b) {
        Ring R = findRing(a,b);
        return R.mod(a,b);
         }
/**
* gcd.
* @return gcd(a,b)
*/
    public RingElt gcd( RingElt a, RingElt b) {
        Ring R = findRing(a,b);
        return R.gcd(a,b);
         }
/**
* Multiplicative Inverse.
* @return a^-1
*/
    public RingElt inv( RingElt a) {
         Ring R = findRing(a);
         return R.inv(a);
         }
/**
* The additive inverse of b.
* @return -b
*/
    public RingElt neg( RingElt b) {
         Ring R = findRing(b);
         return R.neg(b);
         }
/**
* The 1 of the ring.
* @return 1
*/
    public RingElt one(){
             return findRing().one();
             }
/**
* The 0 of the ring.
* @return 0
*/
    public RingElt zero() {
             return findRing().zero();
             }

/**
* true if b == 0.
* @return b == 0
*/
    public boolean equalZero( RingElt b) {
         Ring R = findRing(b);
         return R.equalZero(b);
         }

/**
* Maps a string to the ring obtained by findRing()
* without parameter. It is recommended to override this
* method in order to get enhanced flexibility.
*/
    public RingElt map( String str ) {
           return findRing().map(str);
           }

/**
* Maps a RingElt using the findRing() method with one parameter.
**/

   public RingElt map(RingElt a) {
           return findRing(a).map(a);
           }

/**
* A suitable ring able to map 0 (and 1).
*/

   abstract public Ring findRing();

/**
*  A suitable ring able to map a.
*/
   abstract public Ring findRing( RingElt a );

/**
* A suitable ring able to map a and b.
*/

  abstract public Ring findRing(RingElt a, RingElt b);


}
