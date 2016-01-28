package jquants.time;

import jquants.Quantity;

public interface SecondTimeDerivative<A extends Quantity<A> &SecondTimeIntegral>   {
  Time time();
  
	default A multiply(TimeSquared that){ return (this.multiply(that.time1.multiply(that.time2)));};
}
