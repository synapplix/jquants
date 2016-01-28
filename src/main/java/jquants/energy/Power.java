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
import jquants.market.Market;
import jquants.market.Money;
import jquants.market.Money.QuantityStringParseException;
import jquants.motion.Force;
import jquants.motion.Velocity;
import jquants.radio.RadiantIntensity;
import jquants.radio.SpectralIntensity;
import jquants.radio.SpectralPower;
import jquants.space.Length;
import jquants.time.Time;
import jquants.time.TimeDerivative;
import jquants.time.TimeIntegral;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.regex.Regex.regex;
import static jquants.energy.Energy.*;
import static jquants.space.Area.*;
import static jquants.space.Volume.*;
import static jquants.time.Time.*;
import static jquants.energy.PowerRamp.*;

public class Power extends Quantity<Power> implements TimeDerivative<Energy>, TimeIntegral<PowerRamp> {

  public Energy change;
  
  private Power(double value, PowerUnit valueUnit) {
    this.value = value;
    this.valueUnit = valueUnit;
    this.change = WattHours(value);
    this.dimension = Power;
  }

  public static Option<Power> toPower(String s) {
	return Power.parseString(s);
  }
	  
  public static UnitOfMeasure factory(String x) {
    return Power.get(x);
  }
  
  public static class PowerUnit extends BaseQuantityUnit<Power> {

  	public PowerUnit(String symbol, double multiplier) {
  	  super(symbol, multiplier, true, false, false);
  	}
  	    
  	public PowerUnit(String symbol, double multiplier, boolean baseUnit, boolean valueUnit) {
  	  super(symbol, multiplier, true, baseUnit, valueUnit);
  	}
  	    
  	@Override
  	public String dimensionSymbol() {
  	  return "P";
  	}
  	    
  	@Override
  	public Power apply(double n) {
  	  return new Power(n, this);
  	}
  }
  
  @Override
  public String toString() {
	return this.toString(this.valueUnit);
  }
  
//  public SpectralPower div(Length that) { return WattsPerMeter(toWatts() / that.toMeters());}
//  public Length div(SpectralPower that) { return Meters(toWatts() / that.toWattsPerMeter());}
//  public Irradiance div(dimension that) { return Irradiance(this, that);}
//  public dimension div(Irradiance that) { return SquareMeters(this.toWatts() / that.toWattsPerSquareMeter());}
//  public SolidAngle div(RadiantIntensity that) { return SquaredRadians(toWatts() / that.toWattsPerSteradian());}
//  public RadiantIntensity div(SolidAngle that) { return RadiantIntensity(this, that);}
//  public ElectricCurrent div(ElectricPotential that) { return Amperes(toWatts() / that.toVolts());}
//  public ElectricPotential div(ElectricCurrent that) { return Volts(toWatts() / that.toAmperes());}
  public Energy multiply(Time that) {return WattHours(toWatts() * that.toHours());}

  public Time time() {return Hours(1);}
  public Energy timeIntegrated() {return WattHours(toWatts());}
  public PowerRamp timeDerived() {return WattsPerHour(toWatts());}
  
  public double toMilliwatts() { return to(Milliwatts);}
  public double toWatts() { return to(Watts);}
  public double toKilowatts() { return to(Kilowatts);}
  public double toMegawatts() { return to(Megawatts);}
  public double toGigawatts() { return to(Gigawatts);}
  public double toBtusPerHour() { return to(BtusPerHour);}
  
  public static final PowerUnit Watts = new PowerUnit("W", 1, false, true);
  public static final PowerUnit Milliwatts = new PowerUnit("mW", MetricSystem.Milli);
  public static final PowerUnit Kilowatts = new PowerUnit("kW", MetricSystem.Kilo);
  public static final PowerUnit Megawatts = new PowerUnit("MW", MetricSystem.Mega);
  public static final PowerUnit Gigawatts = new PowerUnit("GW", MetricSystem.Giga);
  public static final PowerUnit BtusPerHour = new PowerUnit("Btu/hr", MetricSystem.Kilo);
  
  public static Power Watts(double value) {return new Power(value, Watts);}
  public static Power Milliwatts(double value) {return new Power(value, Milliwatts);}
  public static Power Kilowatts(double value) {return new Power(value, Kilowatts);}
  public static Power Megawatts(double value) {return new Power(value, Megawatts);}
  public static Power Gigawatts(double value) {return new Power(value, Gigawatts);}
  public static Power BtusPerHour(double value) {return new Power(value, BtusPerHour);}
  
  public static Dimension<Power> Power = new Dimension<Power>(
	      "Power", 
	      Watts, 
	      sequence(Watts, Milliwatts, Kilowatts, Megawatts, Gigawatts, BtusPerHour));

  public static Power milliwatt = Milliwatts(1);
  public static Power mW = milliwatt;
  public static Power watt = Watts(1);
  public static Power W = watt;
  public static Power kilowatt = Kilowatts(1);
  public static Power kW = kilowatt;
  public static Power megawatt = Megawatts(1);
  public static Power MW = megawatt;
  public static Power gigawatt = Gigawatts(1);
  public static Power GW = gigawatt;

  public static Power mW(double value) {return Milliwatts(value);}
  public static Power W(double value) {return Watts(value);}
  public static Power kW(double value) {return Kilowatts(value);}
  public static Power MW(double value) {return Megawatts(value);}
  public static Power GW(double value) {return Gigawatts(value);}
  public static Power milliwatts(double value) {return Milliwatts(value);}
  public static Power watts(double value) {return Watts(value);}
  public static Power kilowatts(double value) {return Kilowatts(value);}
  public static Power megawatts(double value) {return Megawatts(value);}
  public static Power gigawatts(double value) {return Gigawatts(value);}
  public static Power BTUph(double value) {return BtusPerHour(value);}

//    implicit object PowerNumeric extends AbstractQuantityNumeric[dimension](dimension.valueUnit)  
}
