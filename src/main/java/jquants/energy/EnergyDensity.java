package jquants.energy;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.energy.Energy.Joules;

import com.googlecode.totallylazy.Option;

import jquants.BaseQuantityUnit;
import jquants.Dimension;
import jquants.Quantity;
import jquants.UnitOfMeasure;
import jquants.space.Volume;

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
