package jquants.motion;

import com.googlecode.totallylazy.Option;

import jquants.MetricSystem;
import jquants.Quantity;
import jquants.Dimension;
import jquants.UnitOfMeasure;
import jquants.energy.Energy;
import jquants.mass.Mass;
import jquants.motion.Velocity.VelocityUnit;
import jquants.space.Length;
import jquants.space.Length.LengthUnit;
import jquants.time.SecondTimeDerivative;
import jquants.time.Time;
import jquants.time.Time.TimeUnit;
import jquants.time.TimeDerivative;
import jquants.time.TimeIntegral;
import jquants.time.TimeSquared;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.space.Length.*;
import static jquants.time.Time.*;
import static jquants.motion.Acceleration.*;
import static jquants.motion.Velocity.*;
import static jquants.motion.Jerk.*;

/**
 * Represents a quantity of acceleration
 *
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public class Acceleration extends Quantity<Acceleration> 
                           implements TimeDerivative<Velocity>, SecondTimeDerivative<Length>, TimeIntegral<Jerk> {
  public Velocity change;
  public Time time;

  private Acceleration(Velocity velocity, Time time) {
    this.change = velocity;
    this.time = time;
    this.value = toMetersPerSecondSquared();
    this.valueUnit = MetersPerSecondSquared;
    this.dimension = Acceleration;
  }
  
  public static Option<Acceleration> toAcceleration(String s) {
    return Acceleration.parseString(s);
  }
  
  public static UnitOfMeasure factory(String x) {
    return Acceleration.get(x);
  }

//public Force multiply(Mass that) {return Newtons(toMetersPerSecondSquared * that.toKilograms());}
  public Time time() {return Seconds(1);}
  public Velocity timeIntegrated() { return MetersPerSecond(toMetersPerSecondSquared());}
  public Jerk timeDerived() {return MetersPerSecondCubed(toMetersPerSecondSquared());}
  
  public double to(AccelerationUnit unit) { return change.to(unit.velocityUnit) / time.to(unit.timeUnit);}
  public double toFeetPerSecondSquared() {return to(FeetPerSecondSquared);}
  public double toMetersPerSecondSquared() {return to(MetersPerSecondSquared);}
  public double toUsMilesPerHourSquared() {return to(UsMilesPerHourSquared);}
  public double toEarthGravities() {return to(EarthGravities);}
  
  public String oString(AccelerationUnit unit) { return to(unit) + " " + unit.symbol;}
  @Override
	public String toString() {
		if (change.change.valueUnit == Meters && time.valueUnit == Seconds) {
			return to((AccelerationUnit) MetersPerSecondSquared) + " " + "m/s²";//change.valueUnit.symbol + "/" + time.valueUnit.symbol;
		}
		if (change.change.valueUnit == Feet && time.valueUnit == Seconds) {
			return to((AccelerationUnit) FeetPerSecondSquared) + " " + "ft/s²";//change.valueUnit.symbol + "/" + time.valueUnit.symbol;
		}
		if (change.change.valueUnit == UsMiles && time.valueUnit == Hours) {
			return to((AccelerationUnit) UsMilesPerHourSquared) + " " + "mph²";//change.valueUnit.symbol + "/" + time.valueUnit.symbol;
		}
//		if (change.change.valueUnit == UsMilesPerHour && time.valueUnit == Hours) {
//			return to((AccelerationUnit) EarthGravities) + " " + "g";//change.valueUnit.symbol + "/" + time.valueUnit.symbol;
//		}
		return "";
	}
  
  public static class AccelerationUnit extends UnitOfMeasure<Acceleration> {

  	public VelocityUnit velocityUnit;
  	public Time timeInterval;
  	public TimeUnit timeUnit;
  
  	public AccelerationUnit(String symbol, VelocityUnit velocityUnit, Time timeInterval, TimeUnit timeUnit) {
  	  this(symbol, velocityUnit, timeInterval, timeUnit, false);
  	}
  
  	public AccelerationUnit(String symbol, VelocityUnit velocityUnit, Time timeInterval, TimeUnit timeUnit, boolean valueUnit) {
  	  super(symbol, 1, false, valueUnit, false );
  	  this.velocityUnit = velocityUnit;
  	  this.timeInterval = timeInterval;
  	  this.timeUnit = timeUnit;
  	}
  	    
  	@Override
  	public Acceleration apply(double n) {
  	  return new Acceleration(velocityUnit.apply(n), timeInterval);
  	}
  } 
  
  public static final AccelerationUnit MetersPerSecondSquared = new AccelerationUnit("m/s²", MetersPerSecond, Seconds(1), Seconds, true);
  public static final AccelerationUnit FeetPerSecondSquared = new AccelerationUnit("ft/s²", FeetPerSecond, Seconds(1), Seconds);
  public static final AccelerationUnit UsMilesPerHourSquared = new AccelerationUnit("mph²", UsMilesPerHour, Hours(1), Hours);
  public static final AccelerationUnit EarthGravities = new AccelerationUnit("g", MetersPerSecond, Seconds(1), Seconds);
  
  public static Acceleration MetersPerSecondSquared(double value) {return new Acceleration(MetersPerSecond(value), Seconds(1));}
  public static Acceleration FeetPerSecondSquared(double value) {return new Acceleration(FeetPerSecond(value), Seconds(1));}
  public static Acceleration UsMilesPerHourSquared(double value) {return new Acceleration(UsMilesPerHour(value), Hours(1));}
  public static Acceleration EarthGravities(double value) {return new Acceleration(MetersPerSecond(value), Seconds(1));}
  
  public static Dimension<Acceleration> Acceleration = new Dimension<Acceleration>(
	      "Acceleration", 
	      MetersPerSecondSquared, 
	      sequence(MetersPerSecondSquared, FeetPerSecondSquared, UsMilesPerHourSquared, EarthGravities));
  
  public static Acceleration mpss(double value) {return MetersPerSecondSquared(value);}
  public static Acceleration fpss(double value) {return FeetPerSecondSquared(value);}
  
//implicit object AccelerationNumeric extends AbstractQuantityNumeric[dimension](dimension.valueUnit)
}

