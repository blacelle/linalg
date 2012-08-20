package com.perisic.ring;
/**
* This abstract class is the base class for all elements of concrete rings.<P> 
* <UL>
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
* @version 0.2
*/
public abstract class RingElt  {
     private Ring myRing; 
/**
Constructs the ring element as an element of the ring R.
*/
     public RingElt(Ring R) { 
         myRing = R; 
         }
/**
Returns the ring R which has been used for construction of the element.
*/
     public Ring getRing() { 
          return myRing; 
     }
     
     private RingElt() { }
/**
Equality is checked via the method equal() of the ring R to which the
element belongs. Before checking for equalitiy, it is tried to map ob to R.
If this cannot be done, false is returned. <P>
*/
/*
Note: It is usually not necessary to override this method in 
child classes. Override the equal() method of the Ring instead.
*/  
     public boolean equals(Object ob) {
         RingElt w;
         try {
           w = myRing.map(ob);
           } catch ( RingException tmp) {
               return false;
               }
         return myRing.equal(this,w);
         }
/** 
The String is found using the eltToString() method of the ring to which 
the element belongs. 
*/
    public String toString() {
        return myRing.eltToString(this);
        }
}
