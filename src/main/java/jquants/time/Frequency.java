package jquants.time;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.Dimensionless.Each;
import static jquants.time.Time.Seconds;

import com.googlecode.totallylazy.Option;

import jquants.Dimension;
import jquants.Dimensionless;
import jquants.MetricSystem;
import jquants.Quantity;
import jquants.UnitOfMeasure;

/**
 * Represents a quantity of frequency, which is the number cycles (count) over time
 *
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public class Frequency extends Quantity<Frequency> implements TimeDerivative<Dimensionless> {

	private Frequency(double value, FrequencyUnit valueUnit) {
		this.value = value;
		this.valueUnit = valueUnit;
		this.dimension = Frequency;
	}

  public Time time() { return Seconds(1);}
  public Dimensionless timeIntegrated() { return Each(toHertz());}

	public double toHertz() {return to(Hertz);}
	public double toKilohertz() {return to(Kilohertz);}
	public double toMegahertz() {return to(Megahertz);}
	public double toGigahertz() {return to(Gigahertz);}
	public double toTerahertz() {return to(Terahertz);}
	public double toRevolutionsPerMinute() {return to(RevolutionsPerMinute);}
 
	public static class FrequencyUnit extends UnitOfMeasure<Frequency> {

		public FrequencyUnit(String symbol, double multiplier) {
			super(symbol, multiplier, true, false, false);
		}
		
		@Override
	    public Frequency apply(double n) {
	      return new Frequency(n, this);
	    }
	}
	
	public static Option<Frequency> toFrequency(String s) {
		return Frequency.parseString(s);
  }
	
	public static final FrequencyUnit Hertz = new FrequencyUnit("Hz", 1);
	public static final FrequencyUnit Kilohertz = new FrequencyUnit("kHz",  MetricSystem.Kilo);
	public static final FrequencyUnit Megahertz = new FrequencyUnit("MHz",  MetricSystem.Mega);
	public static final FrequencyUnit Gigahertz = new FrequencyUnit("GHz",  MetricSystem.Giga);
	public static final FrequencyUnit Terahertz = new FrequencyUnit("THz",  MetricSystem.Tera);
	public static final FrequencyUnit RevolutionsPerMinute = new FrequencyUnit("rpm", 1d / 60d);
	
	public static Frequency Hertz(double value) {return new Frequency(value, Hertz);}
	public static Frequency Kilohertz(double value) {return new Frequency(value, Kilohertz);}
	public static Frequency Megahertz(double value) {return new Frequency(value, Megahertz);}
	public static Frequency Gigahertz(double value) {return new Frequency(value, Gigahertz);}
	public static Frequency Terahertz(double value) {return new Frequency(value, Terahertz);}
	public static Frequency RevolutionsPerMinute(double value) {return new Frequency(value, RevolutionsPerMinute);}
	
	public static final Dimension<Frequency> Frequency = new Dimension<Frequency>(
		      "Frequency", 
		      Hertz,  
		      sequence(Hertz, Kilohertz, Megahertz, Gigahertz, Terahertz, RevolutionsPerMinute));

	public static Frequency hertz = Hertz(1);
	public static Frequency kilohertz = Kilohertz(1);
	public static Frequency megahertz = Megahertz(1);
	public static Frequency gighertz = Gigahertz(1);
	public static Frequency terahertz = Terahertz(1);
	public static Frequency rpm = RevolutionsPerMinute(1);

	public static Frequency hertz(double value){return Hertz(value);}
	public static Frequency kilohertz(double value) {return Kilohertz(value);}
	public static Frequency megahertz(double value) {return Megahertz(value);}
	public static Frequency gigahertz(double value) {return Gigahertz(value);} 
	public static Frequency terahertz(double value) {return Terahertz(value);}
	public static Frequency rpm(double value) {return RevolutionsPerMinute(value);}

//	implicit object FrequencyNumeric extends AbstractQuantityNumeric[dimension](dimension.primaryUnit)
}
