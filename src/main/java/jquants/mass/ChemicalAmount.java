package jquants.mass;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.mass.Mass.Kilograms;

import com.googlecode.totallylazy.Option;

import jquants.BaseQuantity;
import jquants.BaseQuantityUnit;
import jquants.Dimension;
import jquants.UnitOfMeasure;
import jquants.mass.Mass.MassUnit;
import jquants.space.Volume;

public class ChemicalAmount extends BaseQuantity<ChemicalAmount> {

	private ChemicalAmount(double value, ChemicalAmountUnit valueUnit) {
	    this.value = value;
	    this.valueUnit = valueUnit;
	    this.dimension = ChemicalAmount;
	  }
	  
	public static UnitOfMeasure factory(String x) {
		return ChemicalAmount.get(x);
	  }

	@Override
	public UnitOfMeasure baseUnit() {
	  return Moles;
	}
	
    public static Option<ChemicalAmount> toChemicalAmount(String s) {
	  return ChemicalAmount.parseString(s);
	}
	
//  def /(that: Volume) = ??? // returns SubstanceConcentration
	
	public double toMoles() {return to(Moles);}
	public double toPoundMoles() {return to(PoundMoles);}

	public static class ChemicalAmountUnit extends BaseQuantityUnit<ChemicalAmount> {
		  
		  public ChemicalAmountUnit(String symbol, double multiplier) {
		    super(symbol, multiplier, true, false, false);
		  }
		    
		  public ChemicalAmountUnit(String symbol, double multiplier, boolean baseUnit, boolean valueUnit) {
		    super(symbol, multiplier, true, baseUnit, valueUnit);
		  }
		    
		  @Override
		  public ChemicalAmount apply(double n) {
		    return new ChemicalAmount(n, this);
		  }

		  @Override
		  public String dimensionSymbol() {
		    return "N";
		  }
		}
	
	public static final ChemicalAmountUnit Moles = new ChemicalAmountUnit("mol", 1, true, true);
	public static final ChemicalAmountUnit PoundMoles = new ChemicalAmountUnit("lb-mol", 453.59237);
	
	public static ChemicalAmount Moles(double value) {return new ChemicalAmount(value, Moles);}
	public static ChemicalAmount PoundMoles(double value) {return new ChemicalAmount(value, PoundMoles);}

	public static Dimension<ChemicalAmount> ChemicalAmount = new Dimension<ChemicalAmount>(
		      "ChemicalAmount", 
		      Moles, 
		      sequence(Moles, PoundMoles));
 
	  @Override
	  public String toString() {
		  return this.toString(this.valueUnit);
	  }
	  
	  public static ChemicalAmount mole = Moles(1);
	  public static ChemicalAmount poundMole = PoundMoles(1);
	  
	  public static ChemicalAmount moles(double value) {return Moles(value);}
	  public static ChemicalAmount poundMoles(double value) {return PoundMoles(value);}
	  

//  implicit object ChemicalAmountNumeric extends AbstractQuantityNumeric[dimension](dimension.valueUnit)
}

