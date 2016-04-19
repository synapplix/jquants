package jquants.energy;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.energy.Energy.Joules;

import com.googlecode.totallylazy.Option;

import jquants.BaseQuantityUnit;
import jquants.Dimension;
import jquants.Quantity;
import jquants.UnitOfMeasure;
import jquants.mass.Mass;

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



