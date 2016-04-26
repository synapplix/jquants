package jquants.motion;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.motion.Acceleration.MetersPerSecondSquared;
import static jquants.space.Length.Feet;
import static jquants.space.Length.Meters;
import static jquants.time.Time.Seconds;

import jquants.Quantity;
import jquants.Dimension;
import jquants.UnitOfMeasure;
import jquants.time.SecondTimeDerivative;
import jquants.time.Time;
import jquants.time.TimeDerivative;
import jquants.time.TimeSquared;

/**
 * Represents the third time derivative of position after Velocity and Acceleration
 *
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public class Jerk extends Quantity<Jerk> implements TimeDerivative<Acceleration>, SecondTimeDerivative<Velocity>{

  private Jerk(double value, JerkUnit valueUnit) {
    this.value = value;
    this.valueUnit = valueUnit;
    this.dimension = Jerk;
  }
  
  public Acceleration timeIntegrated() {return MetersPerSecondSquared(toMetersPerSecondCubed());}
  public Time time() {return Seconds(1);}
  @Override
  public Velocity multiply(TimeSquared that) {return this.multiply(that.time1).multiply(that.time2);}
  
  public double toMetersPerSecondCubed() { return to(MetersPerSecondCubed);}
  public double toFeetPerSecondCubed() { return to(FeetPerSecondCubed);}
  
  public static final class JerkUnit extends UnitOfMeasure<Jerk> {
    
    public JerkUnit(String symbol, double multiplier) {
      super(symbol, multiplier, true, false, false);
    }

    @Override
    public Jerk apply(double n) {
      return new Jerk(n, this);
    }
  }
  
  public static final JerkUnit MetersPerSecondCubed = new JerkUnit("m/s³", 1);
  public static final JerkUnit FeetPerSecondCubed = new JerkUnit("ft/s³", Meters.multiplier * Feet.multiplier);
  
  public static Jerk MetersPerSecondCubed(double value) {return new Jerk(value, MetersPerSecondCubed);}
  public static Jerk FeetPerSecondCubed(double value) {return new Jerk(value, FeetPerSecondCubed);}
  
  public static Dimension<Jerk> Jerk = new Dimension<Jerk>(
      "Jerk", 
      MetersPerSecondCubed, 
      sequence(MetersPerSecondCubed, FeetPerSecondCubed));
  
  public static Jerk metersPerSecondCubed = MetersPerSecondCubed(1);
  public static Jerk feetPerSecondCubed = FeetPerSecondCubed(1);
  
//
//    implicit object JerkNumeric extends AbstractQuantityNumeric[dimension](dimension.primaryUnit)
}
