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
import jquants.photo.LuminousFlux;
import jquants.photo.LuminousIntensity;
import jquants.radio.RadiantIntensity;
import jquants.radio.SpectralIntensity;
import jquants.radio.SpectralPower;
import jquants.time.Time;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.regex.Regex.regex;
import static jquants.energy.Power.*;
import static jquants.space.Area.*;
import static jquants.space.Volume.*;

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.space.SquaredRadians]]
 */
public final class SolidAngle extends Quantity<SolidAngle> {

  private SolidAngle(double value, SolidAngleUnit valueUnit) {
    this.value = value;
    this.valueUnit = valueUnit;
    this.dimension = SolidAngle;
  }
  
  public static Option<SolidAngle> toSolidAngle(String s) {
	return SolidAngle.parseString(s);
  }
	  
  public static UnitOfMeasure factory(String x) {
    return SolidAngle.get(x);
  }
  
//  public LuminousFlux multiply(LuminousIntensity that) {return Lumens(toSquaredRadians() * that.toCandelas());}
//  public Power multiply(RadiantIntensity that) {return Watts(toSquaredRadians() * that.toWattsPerSteradian());}

  public double toSquaredRadians() { return to(SquaredRadians);}
  public double toSteradians() { return to(SquaredRadians);}

  @Override
  public String toString() {
	return this.toString(this.valueUnit);
  }

  public static class SolidAngleUnit extends BaseQuantityUnit<SolidAngle> {
	  
	public SolidAngleUnit(String symbol, double multiplier) {
	  super(symbol, multiplier, true, false, false);
	}
    
    public SolidAngleUnit(String symbol, double multiplier, boolean baseUnit, boolean valueUnit) {
      super(symbol, multiplier, true, baseUnit, valueUnit);
    }    

    @Override
    public String dimensionSymbol() {
    	return "sr";
    }
    
    @Override
    public SolidAngle apply(double n) {
      return new SolidAngle(n, this);
    }
  }

  public static final SolidAngleUnit SquaredRadians = new SolidAngleUnit("sr", 1, true, true);

  public static SolidAngle SquaredRadians(double value) {return new SolidAngle(value, SquaredRadians);}
  
  public static final Dimension<SolidAngle> SolidAngle = new Dimension<SolidAngle>(
	      "SolidAngle", 
	      SquaredRadians, 
	      sequence(SquaredRadians));

  public static SolidAngle squaredRadian = SquaredRadians(1);
  public static SolidAngle steradian = SquaredRadians(1);

  public static SolidAngle squaredRadians(double value) {return SquaredRadians(value);}
  public static SolidAngle steradians(double value) {return SquaredRadians(value);}

//  implicit object SolidAngleNumeric extends AbstractQuantityNumeric[dimension](dimension.valueUnit)
}

