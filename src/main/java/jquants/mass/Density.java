package jquants.mass;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.mass.Mass.Kilograms;

import com.googlecode.totallylazy.Option;

import jquants.BaseQuantityUnit;
import jquants.BaseQuantity;
import jquants.Quantity;
import jquants.Dimension;
import jquants.UnitOfMeasure;
import jquants.space.Volume;
import jquants.mass.Mass;


public class Density extends Quantity<Density> {
	
	public Density(double value, DensityUnit valueUnit) {
		this.value = value;
		this.valueUnit = valueUnit;
		this.dimension = Density;
	}
	
	public static UnitOfMeasure factory(String x) {
		return Density.get(x);
	  }
	
	public static class DensityUnit extends BaseQuantityUnit<Density> {
		public DensityUnit(String symbol, double multiplier) {
			super(symbol, multiplier, true, false, false);
		}
		
		public DensityUnit(String symbol, double multiplier, boolean baseUnit, boolean valueUnit) {
			super(symbol, multiplier, true, baseUnit, valueUnit);
		}
		
	    @Override
	    public Density apply(double n) {
	      return new Density(n, this);
	    }

	    @Override
	    public String dimensionSymbol() {
	      return "D";
	    }
		
	}
	
	public static final DensityUnit KilogramsPerCubicMeter = new DensityUnit("kg/m³", 1, true, true);
	public static Density KilogramsPerCubicMeter(double value) {return new Density(value, KilogramsPerCubicMeter);}
	
	public Mass multiply(Volume that) {return Kilograms(value * that.toCubicMeters());}
	
	public double toKilogramsPerCubicMeter() {return to(KilogramsPerCubicMeter);}

	public static Dimension<Density> Density = new Dimension<Density>(
		      "Density", 
		      KilogramsPerCubicMeter, 
		      sequence(KilogramsPerCubicMeter));

	public static Option<Density> toDensity(String s) {
	  return Density.parseString(s);
	}
	  
	public static Density kilogramPerCubicMeter = KilogramsPerCubicMeter(1);
	public static Density kilogramsPerCubicMeter(double value) {return KilogramsPerCubicMeter(value);}
	  
	@Override
	public String toString() {
		return this.toString(this.valueUnit);
	}
	
}


//	  implicit object DensityNumeric extends AbstractQuantityNumeric[dimension](KilogramsPerCubicMeter)

