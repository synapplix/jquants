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
	
	default TimeSquared div(A that){ 
	  if(this instanceof Length) {return (((Length)this).div(((Acceleration)that).timeIntegrated()).multiply(time())); }
	  else if(this instanceof Velocity) {return (((Velocity)this).div(((Jerk)that).timeIntegrated()).multiply(time())); }
	  else if(this instanceof Energy) {return (((Energy)this).div(((PowerRamp)that).timeIntegrated()).multiply(time())); }
	  else{throw new UnsupportedOperationException();}
	}
}
