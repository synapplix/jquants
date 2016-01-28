package jquants.energy;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.energy.Power.Watts;
import static jquants.time.Time.Hours;

import com.googlecode.totallylazy.Option;

import jquants.BaseQuantity;
import jquants.BaseQuantityUnit;
import jquants.MetricSystem;
import jquants.Quantity;
import jquants.Dimension;
import jquants.UnitOfMeasure;
import jquants.motion.VolumeFlowRate;
import jquants.space.Volume;
import jquants.time.SecondTimeDerivative;
import jquants.time.Time;
import jquants.time.TimeDerivative;
import jquants.time.TimeIntegral;
import jquants.time.TimeSquared;

public class PowerRamp extends Quantity<PowerRamp> implements TimeDerivative<Power>, SecondTimeDerivative<Energy>{
  
  public Power change;
  public Time time;

  public static PowerRamp PowerRamp(Power power, Time time) {
    return new PowerRamp(power.toWatts() / time.toHours(), WattsPerHour);
  }
  
  private PowerRamp(double value, PowerRampUnit valueUnit) {
    this.value = value;
    this.valueUnit = valueUnit;
    this.change = Watts(value);
    this.time = Hours(1);
    this.dimension = PowerRamp;
  }
  
  public static Option<PowerRamp> toPowerRamp(String s) {
	return PowerRamp.parseString(s);
  }
	  
  public Power multiply(Time that) {return Watts(toWattsPerHour() * that.toHours());}
	public Energy multiply(TimeSquared that){return this.multiply( (that.time1).multiply(that.time2));}
 
	public Time time() {return Hours(1);}
	public Power timeIntegrated() {return Watts(toWattsPerHour());}

  public double toWattsPerHour() { return to(WattsPerHour);}
  public double toWattsPerMinute() { return to(WattsPerMinute);}
  public double toKilowattsPerHour() { return to(KilowattsPerHour);}
  public double toKilowattsPerMinute() { return to(KilowattsPerMinute);}
  public double toMegawattsPerHour() { return to(MegawattsPerHour);}
  public double toGigawattsPerHour() { return to(GigawattsPerHour);}

  public static class PowerRampUnit extends BaseQuantityUnit<PowerRamp> {

    public PowerRampUnit(String symbol, double multiplier) {
      super(symbol, multiplier, true, false, false);
    }
    
    public PowerRampUnit(String symbol, double multiplier, boolean baseUnit, boolean valueUnit) {
      super(symbol, multiplier, true, baseUnit, valueUnit);
    }
    
    @Override
    public PowerRamp apply(double n) {
      return new PowerRamp(n, this);
    }

  	@Override
  	public String dimensionSymbol() {
  		return "W/h";
  	}
  }
  
  @Override
  public String toString() {
    return this.toString(this.valueUnit);
  }

  public static final PowerRampUnit WattsPerHour = new PowerRampUnit("W/h", 1, true, true);
  public static final PowerRampUnit WattsPerMinute = new PowerRampUnit("W/m", 1 / 60d);
  public static final PowerRampUnit KilowattsPerHour = new PowerRampUnit("kW/h", MetricSystem.Kilo);
  public static final PowerRampUnit KilowattsPerMinute = new PowerRampUnit("kW/m", KilowattsPerHour.multiplier / 60d);
  public static final PowerRampUnit MegawattsPerHour = new PowerRampUnit("MW/h", MetricSystem.Mega);
  public static final PowerRampUnit GigawattsPerHour = new PowerRampUnit("GW/h", MetricSystem.Giga);

  public static PowerRamp WattsPerHour(double value) {return new PowerRamp(value, WattsPerHour);}
  public static PowerRamp WattsPerMinute(double value) {return new PowerRamp(value, WattsPerMinute);}
  public static PowerRamp KilowattsPerHour(double value) {return new PowerRamp(value, KilowattsPerHour);}
  public static PowerRamp KilowattsPerMinute(double value) {return new PowerRamp(value, KilowattsPerMinute);}
  public static PowerRamp MegawattsPerHour(double value) {return new PowerRamp(value, MegawattsPerHour);}
  public static PowerRamp GigawattsPerHour(double value) {return new PowerRamp(value, GigawattsPerHour);}
  
  public static Dimension<PowerRamp> PowerRamp = new Dimension<PowerRamp>(
	      "PowerRamp", 
	      WattsPerHour, 
	      sequence(WattsPerHour, WattsPerMinute, KilowattsPerHour, KilowattsPerMinute, MegawattsPerHour, GigawattsPerHour));

  public static PowerRamp wattPerHour = WattsPerHour(1);
  public static PowerRamp Wph = wattPerHour;
  public static PowerRamp wattPerMinute = WattsPerMinute(1);
  public static PowerRamp Wpm = wattPerMinute;
  public static PowerRamp kilowattPerHour = KilowattsPerHour(1);
  public static PowerRamp kWph = kilowattPerHour;
  public static PowerRamp kilowattPerMinute = KilowattsPerMinute(1);
  public static PowerRamp kWpm = kilowattPerMinute;
  public static PowerRamp megawattPerHour = MegawattsPerHour(1);
  public static PowerRamp MWph = megawattPerHour;
  public static PowerRamp gigawattPerHour = GigawattsPerHour(1);
  public static PowerRamp GWph = gigawattPerHour;

  public static PowerRamp Wph(double value) {return WattsPerHour(value);}
  public static PowerRamp Wpm(double value) {return WattsPerMinute(value);}
  public static PowerRamp kWph(double value) {return KilowattsPerHour(value);}
  public static PowerRamp kWpm(double value) {return KilowattsPerMinute(value);}
  public static PowerRamp MWph(double value) {return MegawattsPerHour(value);}
  public static PowerRamp GWph(double value) {return GigawattsPerHour(value);}
//  implicit object PowerRampNumeric extends AbstractQuantityNumeric[dimension](dimension.valueUnit)
}
