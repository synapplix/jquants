package jquants.mass;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.mass.Mass.Kilograms;

import com.googlecode.totallylazy.Option;

import jquants.BaseQuantity;
import jquants.BaseQuantityUnit;
import jquants.Dimension;
import jquants.Quantity;
import jquants.UnitOfMeasure;
import jquants.mass.Density.DensityUnit;
import jquants.mass.Mass.MassUnit;
import jquants.space.Area;
import jquants.space.Volume;

/**
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public class AreaDensity extends Quantity<AreaDensity> {

	private AreaDensity(double value, AreaDensityUnit valueUnit) {
	    this.value = value;
	    this.valueUnit = valueUnit;
	    this.dimension = AreaDensity;
	}
	
	public static UnitOfMeasure factory(String x) {
		return AreaDensity.get(x);
	  }
	
	public Mass multiply(Area that) {return Kilograms(value * that.toSquareMeters());}
	
	public double toKilogramsPerSquareMeter() {return to(KilogramsPerSquareMeter);}

	
	public static class AreaDensityUnit extends BaseQuantityUnit<AreaDensity> {
		public AreaDensityUnit(String symbol, double multiplier) {
			super(symbol, multiplier, true, false, false);
		}
		
		public AreaDensityUnit(String symbol, double multiplier, boolean baseUnit, boolean valueUnit) {
			super(symbol, multiplier, true, baseUnit, valueUnit);
		}
		
	    @Override
	    public AreaDensity apply(double n) {
	      return new AreaDensity(n, this);
	    }

	    @Override
	    public String dimensionSymbol() {
	      return "kg/m²";
	    }
	}
	
	public static final AreaDensityUnit KilogramsPerSquareMeter = new AreaDensityUnit("kg/m²", 1, true, true);
	public static AreaDensity KilogramsPerSquareMeter(double value) {return new AreaDensity(value, KilogramsPerSquareMeter);}
	
	public static AreaDensity kilogramPerSquareMeter = KilogramsPerSquareMeter(1);
	public static AreaDensity kilogramsPerSquareMeter(double value) {return KilogramsPerSquareMeter(value);}
	
	public static Dimension<AreaDensity> AreaDensity = new Dimension<AreaDensity>(
		      "AreaDensity", 
		      KilogramsPerSquareMeter, 
		      sequence(KilogramsPerSquareMeter));

	public static Option<AreaDensity> toAreaDensity(String s) {
	  return AreaDensity.parseString(s);
	}
	
	@Override
	public String toString() {
		return this.toString(this.valueUnit);
	}
}

//  implicit object AreaDensityNumeric extends AbstractQuantityNumeric[dimension](dimension.valueUnit)
