package jquants.time;

import jquants.Quantity;
import jquants.energy.Energy;
import jquants.energy.PowerRamp;
import jquants.motion.Acceleration;
import jquants.motion.Jerk;
import jquants.motion.Velocity;
import jquants.space.Length;

public interface SecondTimeIntegral<A extends Quantity<A> &SecondTimeDerivative &TimeDerivative> {
	Time time();

  default A div(TimeSquared that) { return this.div(that.time1.multiply(that.time2));}
	default A per(TimeSquared that) { return this.div(that);}
	
	TimeSquared div(A that); // A will be TimeIntegral of the class implementing this interface

}