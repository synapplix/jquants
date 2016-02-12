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
import jquants.mass.Mass;
import jquants.motion.Force;
import jquants.motion.Velocity;
import jquants.radio.RadiantIntensity;
import jquants.radio.SpectralIntensity;
import jquants.radio.SpectralPower;
import jquants.space.Length;
import jquants.time.Time;
import jquants.time.TimeIntegral;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.regex.Regex.regex;
import static jquants.energy.Energy.*;
import static jquants.energy.Power.*;
import static jquants.space.Area.*;
import static jquants.space.Volume.*;
import static jquants.time.Time.*;

/**
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public class SpecificEnergy extends Quantity<SpecificEnergy> {
  private SpecificEnergy(double value, SpecificEnergyUnit valueUnit) {
	this.value = value;
	this.valueUnit = valueUnit;
	this.dimension = SpecificEnergy;
  }

  public static Option<SpecificEnergy> toSpecificEnergy(String s) {
	return SpecificEnergy.parseString(s);
  }
			  
  public static UnitOfMeasure factory(String x) {
    return SpecificEnergy.get(x);
  }
		  
  public static class SpecificEnergyUnit extends BaseQuantityUnit<SpecificEnergy> {

	public SpecificEnergyUnit(String symbol, double multiplier) {
	  super(symbol, multiplier, true, false, false);
	}
			    
	public SpecificEnergyUnit(String symbol, double multiplier, boolean baseUnit, boolean valueUnit) {
	  super(symbol, multiplier, true, baseUnit, valueUnit);
	}
			    
	@Override
	public String dimensionSymbol() {
	  return "J/kg";
	}
			    
	@Override
	public SpecificEnergy apply(double n) {
	  return new SpecificEnergy(n, this);
	}
  }
		  
  @Override
  public String toString() {
	return this.toString(this.valueUnit);
  }
  
  public Energy multiply(Mass that) {return Joules(toGrays() * that.toKilograms());}
//  def /(that: Time) = ??? // returns AbsorbedEnergyRate
  
  public double toGrays() {return to(Grays);}
  
  
  public static final SpecificEnergyUnit Grays = new SpecificEnergyUnit("Gy", 1, true, true);
  
  public static SpecificEnergy Grays(double value) {return new SpecificEnergy(value, Grays);}
  
  public static Dimension<SpecificEnergy> SpecificEnergy = new Dimension<SpecificEnergy>(
	      "SpecificEnergy", 
	      Grays, 
	      sequence(Grays));
  
  public static SpecificEnergy gray = Grays(1);
  
  public static SpecificEnergy grays(double value) {return Grays(value);}

//  implicit object SpecificEnergyNumeric extends AbstractQuantityNumeric[dimension](dimension.valueUnit)
}



