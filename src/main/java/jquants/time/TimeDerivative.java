package jquants.time;

import jquants.Quantity;
import jquants.time.Time;

public interface TimeDerivative<A extends Quantity<A>> {
  Time time();
  A timeIntegrated();
  
  default A multiply(Time that) {return timeIntegrated().multiply(that.div(time()));}
}
