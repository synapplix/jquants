package jquants.energy;

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
import jquants.energy.Energy.EnergyUnit;
import jquants.market.Market;
import jquants.market.Money;
import jquants.market.Money.QuantityStringParseException;
import jquants.motion.Force;
import jquants.motion.Velocity;
import jquants.radio.RadiantIntensity;
import jquants.radio.SpectralIntensity;
import jquants.radio.SpectralPower;
import jquants.space.Length;
import jquants.space.Volume;
import jquants.time.Time;
import jquants.time.TimeIntegral;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.regex.Regex.regex;
import static jquants.energy.Power.*;
import static jquants.energy.Energy.*;
import static jquants.space.Area.*;
import static jquants.space.Volume.*;
import static jquants.time.Time.*;

/**
 * Represents a quantity of energy
 *
 * @author  Mathias Braeu
 * @since   q.0
 *
 */
public class EnergyDensity extends Quantity<EnergyDensity> {
  private EnergyDensity(double value, EnergyDensityUnit valueUnit) {
    this.value = value;
	this.valueUnit = valueUnit;
	this.dimension = EnergyDensity;
  }

  public static Option<EnergyDensity> toEnergyDensity(String s) {
	return EnergyDensity.parseString(s);
  }
			  
  public static UnitOfMeasure factory(String x) {
    return EnergyDensity.get(x);
  }
		  
  public static class EnergyDensityUnit extends BaseQuantityUnit<EnergyDensity> {

	public EnergyDensityUnit(String symbol, double multiplier) {
	  super(symbol, multiplier, true, false, false);
	}
			    
	public EnergyDensityUnit(String symbol, double multiplier, boolean baseUnit, boolean valueUnit) {
	  super(symbol, multiplier, true, baseUnit, valueUnit);
	}
			    
	@Override
	public String dimensionSymbol() {
	  return "ED";
	}
			    
	@Override
	public EnergyDensity apply(double n) {
	  return new EnergyDensity(n, this);
	}
  }
		  
  @Override
  public String toString() {
	return this.toString(this.valueUnit);
  }
  
  public Energy multiply(Volume that) {return Joules(toJoulesPerCubicMeter() * that.toCubicMeters());}
  
  public double toJoulesPerCubicMeter() {return to(JoulesPerCubicMeter);}

  public static final EnergyDensityUnit JoulesPerCubicMeter = new EnergyDensityUnit("J/m³", 1, true, true);
  public static EnergyDensity JoulesPerCubicMeter(double value) {return new EnergyDensity(value, JoulesPerCubicMeter);}
  
  public static Dimension<EnergyDensity> EnergyDensity = new Dimension<EnergyDensity>(
	      "EnergyDensity", 
	      JoulesPerCubicMeter, 
	      sequence(JoulesPerCubicMeter));

  public static EnergyDensity joulePerCubicMeter = JoulesPerCubicMeter(1);
  
  public static EnergyDensity joulesPerCubicMeter(double value) {return JoulesPerCubicMeter(value);}

//	  implicit object EnergyDensityNumeric extends AbstractQuantityNumeric[dimension](dimension.valueUnit)
}
