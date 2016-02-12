package jquants.space;

import java.math.BigDecimal;
import java.util.Set;
import java.util.regex.MatchResult;

import com.googlecode.totallylazy.Option;
import com.googlecode.totallylazy.Sequence;
import com.googlecode.totallylazy.Sequences;
import com.googlecode.totallylazy.regex.Regex;

import jquants.BaseQuantity;
import jquants.BaseQuantityUnit;
import jquants.MetricSystem;
import jquants.Quantity;
import jquants.Dimension;
import jquants.UnitOfMeasure;
import jquants.electro.Conductivity;
import jquants.electro.ElectricalConductance;
import jquants.electro.ElectricalResistance;
import jquants.electro.Resistivity;
import jquants.energy.Energy;
import jquants.energy.Power;
import jquants.market.Market;
import jquants.market.Money;
import jquants.market.Money.QuantityStringParseException;
import jquants.motion.Force;
import jquants.motion.Velocity;
import jquants.radio.RadiantIntensity;
import jquants.radio.SpectralIntensity;
import jquants.radio.SpectralPower;
import jquants.space.Length.LengthUnit;
import jquants.time.Time;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.regex.Regex.regex;
import static jquants.energy.Power.*;
import static jquants.space.Area.*;
import static jquants.space.Volume.*;

/**
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public final class Angle extends Quantity<Angle> {

  private Angle(double value, AngleUnit valueUnit) {
    this.value = value;
    this.valueUnit = valueUnit;
    this.dimension = Angle;
  }
  
  public static Option<Angle> toAngle(String s) {
	return Angle.parseString(s);
  }
	  
  public static UnitOfMeasure factory(String x) {
    return Angle.get(x);
  }
  
  public double toRadians() { return to(Radians);}
  public double toDegrees() { return to(Degrees);}
  public double toGradians() { return to(Gradians);}
  public double toTurns() { return to(Turns);}
  public double toArcminutes() { return to(Arcminutes);}
  public double toArcseconds() { return to(Arcseconds);}

  public double sin() { return Math.sin(toRadians());}
  public double cos() { return Math.cos(toRadians());}
  public double tan() { return Math.tan(toRadians());}
  public double asin() { return Math.asin(toRadians());}
  public double acos() { return Math.acos(toRadians());}

  @Override
  public String toString() {
	return this.toString(this.valueUnit);
  }
  
  public static class AngleUnit extends BaseQuantityUnit<Angle> {
    public AngleUnit(String symbol, double multiplier) {
      super(symbol, multiplier, true, false, false);
    }
    
    public AngleUnit(String symbol, double multiplier, boolean baseUnit, boolean valueUnit) {
      super(symbol, multiplier, true, baseUnit, valueUnit);
    }
    
    @Override
    public String dimensionSymbol() {
    	return "R";
    }
    
    @Override
    public Angle apply(double n) {
      return new Angle(n, this);
    }
  }

  public static final AngleUnit Radians = new AngleUnit("rad", 1, true, true);
  public static final AngleUnit Degrees = new AngleUnit("°", Math.PI / 180d);
  public static final AngleUnit Turns = new AngleUnit("turns", 2 * Math.PI);
  public static final AngleUnit Gradians = new AngleUnit("grad", Turns.multiplier / 400d);
  public static final AngleUnit Arcminutes = new AngleUnit("amin", Math.PI / 10800d);
  public static final AngleUnit Arcseconds = new AngleUnit("asec", 1d / 60d * Arcminutes.multiplier);

  public static Angle Radians(double value) {return new Angle(value, Radians);}
  public static Angle Degrees(double value) {return new Angle(value, Degrees);}
  public static Angle Turns(double value) {return new Angle(value, Turns);}
  public static Angle Gradians(double value) {return new Angle(value, Gradians);}
  public static Angle Arcminutes(double value) {return new Angle(value, Arcminutes);}
  public static Angle Arcseconds(double value) {return new Angle(value, Arcseconds);}
  
  
  public static final Dimension<Angle> Angle = new Dimension<Angle>(
	      "Angle", 
	      Radians, 
	      sequence(Radians, Degrees, Turns, Gradians, Arcminutes, Arcseconds));


  public static Angle radian = Radians(1);
  public static Angle degree = Degrees(1);
  public static Angle gradian = Gradians(1);
  public static Angle turn = Turns(1);
  public static Angle arcminute = Arcminutes(1);
  public static Angle arcsecond = Arcseconds(1);

  public static Angle radians(double value) {return Radians(value);}
  public static Angle degrees(double value) {return Degrees(value);}
  public static Angle gradians(double value) {return Gradians(value);}
  public static Angle turns(double value) {return Turns(value);}
  public static Angle arcminutes(double value) {return Arcminutes(value);}
  public static Angle arcseconds(double value) {return Arcseconds(value);}

//  implicit object AngleNumeric extends AbstractQuantityNumeric[dimension](dimension.valueUnit)
}
