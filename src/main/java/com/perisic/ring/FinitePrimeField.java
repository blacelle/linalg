package com.perisic.ring;
import com.perisic.ring.*;
/**
* Title: FinitePrimeField <p>
* Description: This class represents a finite prime field. 
* In fact it is not checked if the modulus is a prime, so it is
* the responsibility of the user to ensure this. <P>
* Using a non prime as modulus can lead to an ArithmeticException
* in the BigInteger.gcd() method. <p>
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
public class FinitePrimeField extends ModularIntegerRing {
/**
* Generates a finite prime field of characteristic modulus.
* The argument can be any Object which can be maped into
* an IntegerRingElt (via the IntegerRing.Z.map() method). 
* Note: The primality of modulus is not checked!
*/   
    public FinitePrimeField(Object modulus) {
           super(modulus);
           }
/**
* Generates a finite prime field of characteristic m.
* Note: The primality of m is not checked!
*/
    public FinitePrimeField(int m) {
           super(m);
           }
/** 
returns true. This is in fact the only difference to the 
ModularRing class.
*/
    public boolean isField() { return true; }

 
}
