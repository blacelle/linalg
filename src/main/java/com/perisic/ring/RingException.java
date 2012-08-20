package com.perisic.ring;
/**
* The exception class for all errors.
* <UL>
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
public class RingException extends ArithmeticException { 
     private RingException() { 
          super( "Ring Exception (unspecified)" );
          }
/**
* Construction. 
* @param what is passed to the parent class.
*/
     public RingException(String what) { 
          super( what );
          }
}
