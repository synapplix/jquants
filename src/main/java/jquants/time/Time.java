package jquants.time;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.space.Length.Length;
import static jquants.time.TimeSquared.TimeSquared;

import com.googlecode.totallylazy.Option;

import jquants.BaseQuantity;
import jquants.Dimension;
import jquants.UnitOfMeasure;
import jquants.motion.Acceleration;
import jquants.motion.Velocity;
import jquants.space.Length;

/**
 * Represents a quantity of Time
 * 
 * @author Mathias Braeu
 * @since 1.0
 *
 */
public class Time extends BaseQuantity<Time> {
  
  private Time(double value, TimeUnit valueUnit) {
    this.value = value;
    this.valueUnit = valueUnit;
    this.dimension = Time;
  }
  
  public Length multiply(Velocity that) {	return (  Length(this.toSeconds() * that.toMetersPerSecond()));  }
  public TimeSquared multiply(Time that) {return TimeSquared(this, that);  }
  public Velocity multiply(Acceleration that) { return that.change.multiply(this.div(that.time));}
  
  @Override
  public UnitOfMeasure baseUnit() { return Seconds; }
  
  public static Option<Time> toTime(String s) {
    return Time.parseString(s);
  }
  
  public static class TimeUnit extends UnitOfMeasure<Time>{//BaseQuantityUnit<Time> {
    
    public TimeUnit(String symbol, double multiplier) {
      super(symbol, multiplier, true, false, false);
    }
    
    public TimeUnit(String symbol, double multiplier, boolean baseUnit, boolean valueUnit) {
      super(symbol, multiplier, true, baseUnit, valueUnit);
    }

    @Override
    public Time apply(double n) {
      return new Time(n, this);
    }
 }
  public static final double NanosecondsPerSecond = 1.0e9;
  public static final double MicrosecondsPerSecond = 1.0e6;
  public static final double MillisecondsPerNanosecond = 1.0e-6;
  public static final double MillisecondsPerMicrosecond = 1.0e-3;
  public static final double MillisecondsPerSecond = 1e3;
  public static final double MillisecondsPerMinute = MillisecondsPerSecond * 60d;
  public static final double MillisecondsPerHour = MillisecondsPerMinute * 60d;
  public static final double MillisecondsPerDay = MillisecondsPerHour * 24d;
  public static final double SecondsPerMinute = 60d;
  public static final double SecondsPerHour = SecondsPerMinute * 60d;
  public static final double SecondsPerDay = SecondsPerHour * 24d;
  public static final double MinutesPerHour = 60d;
  public static final double HoursPerDay = 24d;
  public static final double x = 1.0e-3;
  
  public static final TimeUnit Milliseconds = new TimeUnit("ms", 1);
  public static final TimeUnit Nanoseconds = new TimeUnit("ns", Milliseconds.multiplier / MicrosecondsPerSecond);
  public static final TimeUnit Microseconds = new TimeUnit("µs", Milliseconds.multiplier / MillisecondsPerSecond);
  public static final TimeUnit Seconds = new TimeUnit("s", Milliseconds.multiplier* MillisecondsPerSecond);
  public static final TimeUnit Minutes = new TimeUnit("m", Seconds.multiplier * SecondsPerMinute); //TODO: should be min?
  public static final TimeUnit Hours = new TimeUnit("h", Minutes.multiplier * MinutesPerHour);
  public static final TimeUnit Days = new TimeUnit("d", Hours.multiplier * HoursPerDay);
  
  
  public static final Dimension<Time> Time = new Dimension<Time>(
      "Time", 
      Milliseconds, 
      sequence(Milliseconds, Nanoseconds, Microseconds,Seconds, Minutes, Hours, Days ));
  
  public static Time Nanoseconds(double value) {return new Time(value, Nanoseconds);}
  public static Time Milliseconds(double value) {return new Time(value, Milliseconds);}
  public static Time Microseconds(double value) {return new Time(value, Microseconds);}
  public static Time Seconds(double value) {return new Time(value, Seconds);}
  public static Time Minutes(double value) {return new Time(value, Minutes);}
  public static Time Hours(double value) {return new Time(value, Hours);}
  public static Time Days(double value) {return new Time(value, Days);}

  public static Time nanoseconds = Nanoseconds(1);
  public static Time milliseconds = Milliseconds(1);
  public static Time microseconds = Microseconds(1);
  public static Time seconds = Seconds(1);
  public static Time minutes = Minutes(1);
  public static Time halfHour = Minutes(30);
  public static Time hours = Hours(1);
  public static Time days = Days(1);
  
  public static Time nanoseconds(double value){return Nanoseconds(value);}
  public static Time milliseconds(double value){return Milliseconds(value);}
  public static Time microseconds(double value){return Microseconds(value);}
  public static Time seconds(double value) {return Seconds(value);}
  public static Time minutes(double value) {return Minutes(value);}
  public static Time halfHour(double value) {return Minutes(value);}
  public static Time hours(double value) {return Hours(value);}
  public static Time days(double value) {return Days(value);}
  
  public double toMicroseconds() { return to(Microseconds);}
  public double toNanoseconds() { return to(Nanoseconds);}
  public double toMilliseconds() { return to(Milliseconds);}
  public double toSeconds() { return to(Seconds);}
  public double toMinutes() { return to(Minutes);}
  public double toHours() { return to(Hours);}
  public double toDays() { return to(Days);}
}
