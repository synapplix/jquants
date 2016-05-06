package jquants;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.time.Frequency.Hertz;
import static jquants.time.Time.Seconds;

import com.googlecode.totallylazy.Option;

import jquants.time.Frequency;
import jquants.time.Time;
import jquants.time.TimeIntegral;

/**
 * Represents a quantity of some thing for which there is no dimension.
 *
 * This may be used to represent counts or other discrete amounts of everyday life,
 * but may also represent ratios between like quantities where the units have cancelled out.
 *
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public class Dimensionless extends Quantity<Dimensionless> implements TimeIntegral<Frequency> {
  
private Dimensionless(double value, DimensionlessUnit valueUnit) {
    this.value = value;
    this.valueUnit = valueUnit;
}
  
//Operations
//TODO: implement all desired combinations of multiply() and div() with other Dimensions

  public Dimensionless multiply(Dimensionless that) {
    return Each(toEach() * that.toEach());
  }

  @Override
  public Time time() { return Seconds(1);}
  
  @Override
  public Frequency timeDerived() { return Hertz(this.toEach());}
  @Override
  public Time div(Frequency that) {return that.time().multiply(this.timeDerived().div(that));}
  

  public double toPercent() {return to(Percent);}
  public double toEach() {return to(Each);}
  public double toDozen() {return to(Dozen);}
  public double toScore() {return to(Score);}
  public double toGross() {return to(Gross);}


  public static class DimensionlessUnit extends UnitOfMeasure<Dimensionless> {

    public DimensionlessUnit(String symbol, double multiplier) {
      super(symbol, multiplier, true, false, false);
    }

    @Override
    public Dimensionless apply(double n) {
      return new Dimensionless(n, this);
    }
    
  }
  
  public static Option<Dimensionless> toDimensionless(String s) {
		return dimension.parseString(s);
  }
  

/**
 * Represents a unit of singles
 */
  public static final DimensionlessUnit Percent = new DimensionlessUnit("%", 1e-2);
  public static final DimensionlessUnit Each = new DimensionlessUnit("ea", 1);
  public static final DimensionlessUnit Dozen = new DimensionlessUnit("dz", 12d);
  public static final DimensionlessUnit Score = new DimensionlessUnit("score", 20d);
  public static final DimensionlessUnit Gross = new DimensionlessUnit("gr", 144d);

  public static Dimensionless Percent(double value) {return new Dimensionless(value, Percent);}
  public static Dimensionless Each(double value) {return new Dimensionless(value, Each);}
  public static Dimensionless Dozen(double value) {return new Dimensionless(value, Dozen);}
  public static Dimensionless Score(double value) {return new Dimensionless(value, Score);}
  public static Dimensionless Gross(double value) {return new Dimensionless(value, Gross);}
  
  public static Dimension<Dimensionless> dimension = new Dimension<Dimensionless>(
	      "Dimensionless", 
	      Each,  
	      sequence(Percent, Each, Dozen, Score, Gross));

  public static Dimensionless percent = Percent(1);
  public static Dimensionless each = Each(1);
  public static Dimensionless score = Score(1);
  public static Dimensionless dozen = Dozen(1);
  public static Dimensionless gross = Gross(1);
  public static Dimensionless hundred = Each(1e2);
  public static Dimensionless thousand = Each(1e3);
  public static Dimensionless million = Each(1e6);
		  
  public static Dimensionless percent(double value){return Percent(value);}
  public static Dimensionless each(double value) {return Each(value);}
  public static Dimensionless ea(double value) {return Each(value);}
  public static Dimensionless dozen(double value) {return Dozen(value);} 
  public static Dimensionless dz(double value) {return Dozen(value);}
  public static Dimensionless score(double value) {return Score(value);}
  public static Dimensionless gross(double value) {return Gross(value);}
  public static Dimensionless gr(double value) {return Gross(value);}
  public static Dimensionless hundred(double value) {return Each(1e2*value);}
  public static Dimensionless thousand(double value) {return Each(1e3*value); }
  public static Dimensionless million(double value) {return Each(1e6*value);}
}