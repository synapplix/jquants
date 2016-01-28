package jquants.mass;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.motion.MassFlowRate.KilogramsPerSecond;
import static jquants.motion.MassFlowRate.MassFlowRate;
import static jquants.space.Area.SquareMeters;
import static jquants.time.Time.Seconds;

import com.googlecode.totallylazy.Option;

import jquants.BaseQuantity;
import jquants.BaseQuantityUnit;
import jquants.MetricSystem;
import jquants.Dimension;
import jquants.UnitOfMeasure;
import jquants.motion.MassFlowRate;
import jquants.space.Area;
import jquants.time.Time;
import jquants.time.TimeIntegral;

/**
 * Represents a quantity of Mass
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value the value in the [[squants.mass.Grams]]
 */
public class Mass extends BaseQuantity<Mass> implements TimeIntegral<MassFlowRate> {

  private Mass(double value, MassUnit valueUnit) {
    this.value = value;
    this.valueUnit = valueUnit;
    this.dimension = Mass;
  }
  
  public static UnitOfMeasure factory(String x) {
    return Mass.get(x);
  }

  @Override
  public UnitOfMeasure baseUnit() {
    return Grams;
  }
  
//  public Energy multiply(SpecificEnergy that) { return Joules(toKilograms() * that.toGrays());}
//  public Momentum multiply(Velocity that) { return Momentum(this, that);}
//  public Force multiply(dimension that) { return Force(this, that);}
//  public Volume div(Density that) { return CubicMeters(toKilograms() / that.toKilogramsPerCubicMeter());}
//  public Density div(Volume that) { return Density(this, that);}
  public Time div(MassFlowRate that) { return that.time.multiply(this.div(that.change));}
  public MassFlowRate div(Time that) { return MassFlowRate(this, that);}
  public Area div(AreaDensity that) { return SquareMeters(toKilograms() / that.toKilogramsPerSquareMeter());}
//  public AreaDensity div(dimension that) { return KilogramsPerSquareMeter(toKilograms() / that.toSquareMeters());}
  
  public MassFlowRate timeDerived() { return KilogramsPerSecond(toKilograms());} //Implement MassFlowRate
  public Time time() {return Seconds(1);}
  
  public double toMicrograms() { return to(Micrograms);}
  public double toMilligrams() { return to(Milligrams);}
  public double toGrams() { return to(Grams);}
  public double toKilograms() { return to(Kilograms);}
  public double toTonnes() { return to(Tonnes);}
  public double toPounds() { return to(Pounds);}
  public double toOunces() { return to(Ounces);}

	@Override
	public String toString() {
		return this.toString(this.valueUnit);
	}
	
  public static Option<Mass> toMass(String s) {
    return Mass.parseString(s);
  }
  
  public static class MassUnit extends BaseQuantityUnit<Mass> {
  
    public MassUnit(String symbol, double multiplier) {
      super(symbol, multiplier, true, false, false);
    }
    
    public MassUnit(String symbol, double multiplier, boolean baseUnit, boolean valueUnit) {
      super(symbol, multiplier, true, baseUnit, valueUnit);
    }
    
    @Override
    public Mass apply(double n) {
      return new Mass(n, this);
    }

    @Override
    public String dimensionSymbol() {
      return "M";
    }
  }

  public static final MassUnit Grams = new MassUnit("g", 1, true, true);
  public static final MassUnit Micrograms = new MassUnit("mcg", MetricSystem.Micro);
  public static final MassUnit Milligrams = new MassUnit("mg", MetricSystem.Milli);
  public static final MassUnit Kilograms = new MassUnit("kg", MetricSystem.Kilo);
  public static final MassUnit Tonnes = new MassUnit("t", MetricSystem.Mega);
  public static final MassUnit Pounds = new MassUnit("lb", Kilograms.multiplier * 0.45359237);
  public static final MassUnit Ounces = new MassUnit("oz", Pounds.multiplier / 16d);
  
  public static Dimension<Mass> Mass = new Dimension<Mass>(
	      "Mass", 
	      Grams, 
	      sequence(Grams, Micrograms, Milligrams, Kilograms, Tonnes, Ounces, Pounds));

  public static Mass Grams(double value) {return new Mass(value, Grams);}
  public static Mass Micrograms(double value) {return new Mass(value, Micrograms);}
  public static Mass Milligrams(double value) {return new Mass(value, Milligrams);}
  public static Mass Kilograms(double value) {return new Mass(value, Kilograms);}
  public static Mass Tonnes(double value) {return new Mass(value, Tonnes);}
  public static Mass Pounds(double value) {return new Mass(value, Pounds);}
  public static Mass Ounces(double value) {return new Mass(value, Ounces);}
  
  public static Mass microgram = Micrograms(1);
  public static Mass milligram = Milligrams(1);
  public static Mass gram = Grams(1);
  public static Mass kilogram = Kilograms(1);
  public static Mass tonne = Tonnes(1);
  public static Mass pound = Pounds(1);
  public static Mass ounce = Ounces(1);
  
  public static Mass mcg(double value) {return Micrograms(value);}
  public static Mass mg(double value) {return Milligrams(value);}
  public static Mass milligrams(double value) {return mg(value);}
  public static Mass g(double value) {return Grams(value);}
  public static Mass grams(double value) {return g(value);}
  public static Mass kg(double value) {return Kilograms(value);}
  public static Mass kilograms(double value) {return kg(value);}
  public static Mass tonnes(double value) {return Tonnes(value);}
  public static Mass pounds(double value) {return Pounds(value);}
  public static Mass ounces(double value) {return Ounces(value);}
}

