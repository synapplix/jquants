package jquants.motion;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.motion.Acceleration.MetersPerSecondSquared;
import static jquants.space.Length.Feet;
import static jquants.space.Length.InternationalMiles;
import static jquants.space.Length.Kilometers;
import static jquants.space.Length.Meters;
import static jquants.space.Length.NauticalMiles;
import static jquants.space.Length.UsMiles;
import static jquants.time.Time.Hours;
import static jquants.time.Time.Seconds;

import com.googlecode.totallylazy.Option;

import jquants.Dimension;
import jquants.Quantity;
import jquants.UnitOfMeasure;
import jquants.space.Length;
import jquants.space.Length.LengthUnit;
import jquants.time.SecondTimeIntegral;
import jquants.time.Time;
import jquants.time.Time.TimeUnit;
import jquants.time.TimeDerivative;
import jquants.time.TimeIntegral;
import jquants.time.TimeSquared;

/**
 * Represents a quantify of Velocity
 *
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public class Velocity extends Quantity<Velocity> implements TimeDerivative<Length>, TimeIntegral<Acceleration>, SecondTimeIntegral<Jerk> {
  public Length change;
  public Time time;

  private Velocity(Length distance, Time time) {
    this.change = distance;
    this.time = time;
    this.value = toMetersPerSecond();
    this.valueUnit = MetersPerSecond;
    this.dimension = Velocity;
  }
  //TODO: should the method be private?
  public static Velocity Velocity(Length distance, Time time) {
	    return new Velocity(distance, time);
  }
  
  public static Option<Velocity> toVelocity(String s) {
    return Velocity.parseString(s);
  }
  
  public static UnitOfMeasure factory(String x) {
    return Velocity.get(x);
  }
  
 
//public Momentum multiply(Mass that) { return Momentum(that, this);}
  
//  public Length multiply(Time that) { return change.multiply(that.div(time));}
  
  @Override
  public Time time() { return  Seconds(1);}
  @Override
  public Acceleration timeDerived() { return MetersPerSecondSquared(toMetersPerSecond());}
  @Override
  public Length timeIntegrated() { return Meters(toMetersPerSecond());}
  @Override
  public Time div(Acceleration that) {return that.time().multiply(this.timeDerived().div(that));}
  @Override
  public TimeSquared div(Jerk that) {return ((this).div((that).timeIntegrated()).multiply(time())); }
  public String oString(VelocityUnit unit) { return to(unit) + " " + unit.symbol;}
  
  @Override
	public String toString() {
		if (change.valueUnit == Meters && time.valueUnit == Seconds) {
			return to((VelocityUnit) MetersPerSecond) + " " + change.valueUnit.symbol + "/" + time.valueUnit.symbol;
		}
		if (change.valueUnit == Feet && time.valueUnit == Seconds) {
			return to((VelocityUnit) FeetPerSecond) + " " + change.valueUnit.symbol + "/" + time.valueUnit.symbol;
		}
		if (change.valueUnit == Kilometers && time.valueUnit == Hours) {
			return to((VelocityUnit) KilometersPerHour) + " " + change.valueUnit.symbol + "/" + time.valueUnit.symbol;
		}
		if (change.valueUnit == UsMiles && time.valueUnit == Hours) {
			return to((VelocityUnit) UsMilesPerHour) + " " + "mph";//change.valueUnit.symbol + "/" + time.valueUnit.symbol;
		}
		if (change.valueUnit == InternationalMiles && time.valueUnit == Hours) {
			return to((VelocityUnit) InternationalMilesPerHour) + " " + "imph";//change.valueUnit.symbol + "/" + time.valueUnit.symbol;
		}
		if (change.valueUnit == NauticalMiles && time.valueUnit == Hours) {
			return to((VelocityUnit) Knots) + " " + "kn";//change.valueUnit.symbol + "/" + time.valueUnit.symbol;
		}
		return "";
	}
//
  public double to(VelocityUnit unit) { return change.to(unit.distanceUnit) / time.to(unit.timeUnit);}
  //public double from(VelocityUnit unit) { return change.to(unit.distanceUnit) / time.to(unit.timeUnit);}
  public double toFeetPerSecond() { return to(FeetPerSecond);}
  public double toMetersPerSecond() { return to(MetersPerSecond);}
  public double toKilometersPerHour() { return to(KilometersPerHour);}
  public double toUsMilesPerHour() { return to(UsMilesPerHour);}
  public double toInternationalMilesPerHour() { return to(InternationalMilesPerHour);}
  public double toKnots() { return to(Knots);}

  public static class VelocityUnit extends UnitOfMeasure<Velocity> {
   
    public LengthUnit distanceUnit;
    public Time timeInterval;
    public TimeUnit timeUnit;
    
    public VelocityUnit(String symbol, LengthUnit distanceUnit, Time timeInterval, TimeUnit timeUnit) {
      this(symbol, distanceUnit, timeInterval, timeUnit, false);
    }
    
    public VelocityUnit(String symbol, LengthUnit distanceUnit, Time timeInterval, TimeUnit timeUnit, boolean valueUnit) {
      super(symbol, 1, false, false, valueUnit);
      this.distanceUnit = distanceUnit;
      this.timeInterval = timeInterval;
      this.timeUnit = timeUnit;
    }
    
    @Override
    public Velocity apply(double n) {
      return new Velocity(distanceUnit.apply(n), timeInterval);
    }
  }

  public static final VelocityUnit FeetPerSecond = new VelocityUnit("ft/s", Feet, Seconds(1), Seconds);
  public static final VelocityUnit MetersPerSecond = new VelocityUnit("m/s", Meters, Seconds(1), Seconds, true);
  public static final VelocityUnit KilometersPerHour = new VelocityUnit("km/s", Kilometers, Hours(1), Hours);
  public static final VelocityUnit UsMilesPerHour = new VelocityUnit("mph", UsMiles, Hours(1), Hours);
  public static final VelocityUnit InternationalMilesPerHour = new VelocityUnit("mph", InternationalMiles, Hours(1), Hours);
  public static final VelocityUnit Knots = new VelocityUnit("kn", NauticalMiles, Hours(1), Hours);
  
  public static Velocity FeetPerSecond(double value) {return new Velocity(Feet(value), Seconds(1));}
  public static Velocity MetersPerSecond(double value) {return new Velocity(Meters(value), Seconds(1));}
  public static Velocity KilometersPerHour(double value) {return new Velocity(Kilometers(value), Hours(1));}
  public static Velocity UsMilesPerHour(double value) {return new Velocity(UsMiles(value), Hours(1));}
  public static Velocity InternationalMilesPerHour(double value) {return new Velocity(InternationalMiles(value), Hours(1));}
  public static Velocity Knots(double value) {return new Velocity(NauticalMiles(value), Hours(1));}
  
  public static final Dimension<Velocity> Velocity = new Dimension<Velocity>(
	      "Velocity", 
	      MetersPerSecond, 
	      sequence(MetersPerSecond, FeetPerSecond, KilometersPerHour, UsMilesPerHour, InternationalMilesPerHour, Knots));
  
  public static Velocity footPerSecond = FeetPerSecond(1);
  public static Velocity meterPerSecond = MetersPerSecond(1);
  public static Velocity kilometerPerHour = KilometersPerHour(1);
  public static Velocity milePerHour = UsMilesPerHour(1);
  public static Velocity knot = Knots(1);

  public static Velocity fps(double value) {return FeetPerSecond(value);}
  public static Velocity mps(double value) {return MetersPerSecond(value);}
  public static Velocity kph(double value) {return KilometersPerHour(value);}
  public static Velocity mph(double value) {return UsMilesPerHour(value);}
  public static Velocity knots(double value) {return Knots(value);}
//  implicit object VelocityNumeric extends AbstractQuantityNumeric[dimension](MetersPerSecond)
  
}
