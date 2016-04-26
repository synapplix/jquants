package jquants.time;

import jquants.Quantity;

public interface SecondTimeDerivative<A extends Quantity<A> &SecondTimeIntegral>  {
  Time time();
  
  A multiply(TimeSquared that);
}
