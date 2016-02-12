package jquants.time;

import jquants.Quantity;
import jquants.time.Time;

/**
 * Represents a rate of change over time of the integral quantity
 *
 * @author  Mathias Braeu
 * @since   1.0
 *
 * @param A The type of quantity changing
 */
public interface TimeDerivative<A extends Quantity<A>> {
  Time time();
  A timeIntegrated();
  
  default A multiply(Time that) {return timeIntegrated().multiply(that.div(time()));}
}
