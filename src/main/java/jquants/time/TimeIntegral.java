package jquants.time;

import jquants.Dimensionless;
import jquants.Quantity;
import jquants.energy.Power;
import jquants.energy.PowerRamp;
import jquants.mass.Mass;
import jquants.motion.Acceleration;
import jquants.motion.Jerk;
import jquants.motion.MassFlowRate;
import jquants.motion.Velocity;
import jquants.motion.VolumeFlowRate;
import jquants.space.Length;

import static jquants.time.Time.*;

import java.awt.Window.Type;

public interface TimeIntegral<A extends Quantity<A> &TimeDerivative> {
  Time time(); 		  //generates a standard time: 1s
  A timeDerived();	//returns the derived Quantity A of the Quantity (e.g Length.timeDerived = Velocity)
  
  //default A div(Time that) { return this.timeDerived().multiply((this.time().div(that))); }
  default A div(Time that) { return this.timeDerived().multiply((this.time().div(that))); }
  
  default A per(Time that){ return this.div(that); }
  default Time div (A that) { return that.time().multiply(this.timeDerived().div(that));  }
  
}