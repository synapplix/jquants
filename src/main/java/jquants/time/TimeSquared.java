package jquants.time;

import static jquants.time.Time.Hours;
import static jquants.time.Time.Minutes;
import static jquants.time.Time.Seconds;

import jquants.Quantity;
import jquants.UnitOfMeasure;

/**
 * Represents an intermediate value used in 2nd derivative time calculations
 *
 * Create objects by calling the Time.squared method.
 *
 * q1 / TimeSquared(t1, t2) == q1 / t1 / t2 == q1 / (t1 * t2)
 *
 * q2 * TimeSquared(t1, t2) == q2 * t1 * t2
 *
 * q1 / t1.squared == q1 / t1 / t1
 *
 * q2 * t1.squared == q2 * t1 * t1
 *
 * where q1 is a second degree time integral
 * and q2 is a second degree time derivative
 *
 *
 * @author Mathias Braeu
 * @since 1.0
 */
public class TimeSquared extends Quantity<TimeSquared> {
  
  public Time time1; //TODO: changed visibility to Package for the SecondTimeIntegral
  public Time time2;

  private TimeSquared(Time time1, Time time2) {
    this.time1 = time1;
    this.time2 = time2;
  }
 
  public static TimeSquared TimeSquared(Time time1, Time time2) {
      return new TimeSquared(time1, time2);
  }

  public Time squareRoot(Time time1, Time time2) {return time1.valueUnit.apply(Math.sqrt(time1.value * time2.to(time1.valueUnit))); }
  public TimeSquared apply(Time time) { return TimeSquared(time, time); }

  @Override
  public boolean equals(Object that){
    if (that instanceof TimeSquared) {
      TimeSquared x = (TimeSquared) that; 
          int compareValue = Double.compare(this.time1.toSeconds() * this.time2.toSeconds() ,x.time1.toSeconds() * x.time2.toSeconds());
          return this.getClass() == that.getClass() && (compareValue == 0)  ;
      } else {
        return false;
      } 
  }
  
  public static class TimeSquaredUnit extends UnitOfMeasure<TimeSquared> {

    public Time.TimeUnit timeUnit;

    public TimeSquaredUnit(String symbol, double multiplier, Time.TimeUnit timeUnit) {
      super(symbol, multiplier);
      this.timeUnit = timeUnit;
    }
    
    @Override
    public TimeSquared apply(double n) {
      return new TimeSquared(timeUnit.apply(n), timeUnit.apply(n));
    }
   }
  
   public static final TimeSquaredUnit SecondsSquared = new TimeSquaredUnit("s²", 1, Seconds);
   public static final TimeSquaredUnit MinutesSquared = new TimeSquaredUnit("min²", 3600, Minutes);
   public static final TimeSquaredUnit HoursSquared = new TimeSquaredUnit("h²", 3600*3600, Hours);

   public static final TimeSquared SecondsSquared(double n){ return new TimeSquared(Seconds(n), Seconds(n));}
   public static final TimeSquared MinutesSquared(double n){ return new TimeSquared(Minutes(n), Minutes(n));}
   public static final TimeSquared HoursSquared(double n){ return new TimeSquared(Hours(n), Hours(n));}
}
