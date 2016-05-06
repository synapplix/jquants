package jquants.time;

import jquants.Quantity;

/**
 * Represents a Quantity type used as the integral of a time derivative
 *
 * @author  Mathias Braeu
 * @since   1.0
 *
 * @param A The Quantity type for the TimeDerivative for which this is the base
 */
public interface TimeIntegral<A extends Quantity<A>> {
  Time time(); 		  //generates a standard time: 1s
  A timeDerived();	//returns the derived Quantity A of the Quantity (e.g Length.timeDerived = Velocity)
  
  default A div(Time that) { return this.timeDerived().multiply((this.time().div(that))); }
  default A per(Time that) { return this.div(that); }
  public Time div (A that);
}