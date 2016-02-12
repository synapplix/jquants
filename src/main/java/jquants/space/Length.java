package jquants.space;

import java.math.BigDecimal;
import java.util.Set;
import java.util.regex.MatchResult;

import org.hamcrest.Matcher;

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
import jquants.motion.Acceleration;
import jquants.motion.Force;
import jquants.motion.Velocity;
import static jquants.motion.Velocity.*;

import jquants.radio.RadiantIntensity;
import jquants.radio.SpectralIntensity;
import jquants.radio.SpectralPower;
import jquants.time.SecondTimeIntegral;
import jquants.time.Time;
import jquants.time.TimeIntegral;
import jquants.time.TimeSquared;

import static jquants.time.Time.*;

import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.regex.Regex.regex;
import static jquants.energy.Power.*;
import static jquants.space.Area.*;
import static jquants.space.Volume.*;

/**
 * Represents a quantity of length
 *
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public final class Length extends BaseQuantity<Length> implements TimeIntegral<Velocity>, SecondTimeIntegral<Acceleration> {
  
  private Length(double value, LengthUnit valueUnit) {
    this.value = value;
    this.valueUnit = valueUnit;
    this.dimension = Length;
    
  }
  
  public static Length Length(double value) {
    return new Length(value, (LengthUnit) Length.valueUnit);
  }

  public static Option<Length> toLength(String s) {
    return Length.parseString(s);
  }
  
  @Override
  public UnitOfMeasure baseUnit() {
    return Meters;
  }
  
  public static UnitOfMeasure factory(String x) {
    return Length.get(x);
  }
  
  /**
   * @return the length
   */

  public Area multiply(Length that) { return Area(this, that);  }
  public Volume multiply(Area that) { return Volume(that, this); }
//  public Energy multiply(Force that) { return Joules(toMeters() * that.toNewtons()); }
//  public RadiantIntensity multiply(SpectralIntensity that) { return WattsPerSteradian(toMeters() * that.toWattsPerSteradianPerMeter());}
//  public Power multiply(SpectralPower that) { return Watts(toMeters() * that.toWattsPerMeter());}
//  public ElectricalConductance multiply(Conductivity that) { return Siemens(toMeters() * that.toSiemensPerMeter());}
//  public Resistivity multiply(ElectricalResistance that) { return OhmMeters(toMeters() * that.toOhms());}

  public Time time() { return Seconds(1);}
  public Velocity timeDerived() {return MetersPerSecond(toMeters());}
  public Acceleration div(TimeSquared that) {return this.div(that.time1.multiply(that.time2));}

  public Area squared() { return this.multiply(this);}
  public Volume cubed() { return this.multiply(this).multiply(this);}

  public double toNanometers() { return to(Nanometers);}
  public double toMicrons() { return to(Microns);}
  public double toMillimeters() { return to(Millimeters);}
  public double toCentimeters() { return to(Centimeters);}
  public double toDecimeters() { return to(Decimeters);}
  public double toMeters() { return to(Meters);}
  public double toDecameters() { return to(Decameters);}
  public double toHectometers() { return to(Hectometers);}
  public double toKilometers() { return to(Kilometers);}
  public double toInches() { return to(Inches);}
  public double toFeet() { return to(Feet);}
  public double toYards() { return to(Yards);}
  public double toUsMiles() { return to(UsMiles);}
  public double toInternationalMiles() { return to(InternationalMiles);}
  public double toNauticalMiles() { return to(NauticalMiles);}
  public double toAstronomicalUnits() { return to(AstronomicalUnits);}
  public double toLightYears() { return to(LightYears);}

  
  @Override
  public String toString() {
	return this.toString(this.valueUnit);
  }

  public static class LengthUnit extends BaseQuantityUnit<Length> {

    public LengthUnit(String symbol, double multiplier) {
      super(symbol, multiplier, true, false, false);
    }
    
    public LengthUnit(String symbol, double multiplier, boolean baseUnit, boolean valueUnit) {
      super(symbol, multiplier, true, baseUnit, valueUnit);
    }
    
    @Override
    public String dimensionSymbol() {
      return "L";
    }
    
    @Override
    public Length apply(double n) {
      return new Length(n, this);
    }
  }

  public static final LengthUnit Nanometers = new LengthUnit("nm", MetricSystem.Nano);
  public static final LengthUnit Microns = new LengthUnit("µm", MetricSystem.Micro);
  public static final LengthUnit Millimeters = new LengthUnit("mm", MetricSystem.Milli);
  public static final LengthUnit Centimeters = new LengthUnit("cm", MetricSystem.Centi);
  public static final LengthUnit Decimeters = new LengthUnit("dm", MetricSystem.Deci);
  public static final LengthUnit Meters = new LengthUnit("m", 1, true, true);
  public static final LengthUnit Decameters = new LengthUnit("dam", MetricSystem.Deca);
  public static final LengthUnit Hectometers = new LengthUnit("hm", MetricSystem.Hecto);
  public static final LengthUnit Kilometers = new LengthUnit("km", MetricSystem.Kilo);
  public static final LengthUnit Feet = new LengthUnit("ft",0.3048006096);
  public static final LengthUnit Inches = new LengthUnit("in", Feet.multiplier / 12);
  public static final LengthUnit Yards = new LengthUnit("yd", Feet.multiplier * 3);
  public static final LengthUnit UsMiles = new LengthUnit("mi", Feet.multiplier * 5280);
  public static final LengthUnit InternationalMiles = new LengthUnit("InternationalMiles", 1609.344);
  public static final LengthUnit NauticalMiles = new LengthUnit("nmi", 1852D);
  public static final LengthUnit AstronomicalUnits = new LengthUnit("au", 149597870700D);
  public static final LengthUnit LightYears = new LengthUnit("ly", 9460730472580800D);

//  public static dimension Nanometers(double value) {return Nanometers.apply(value);}
  public static Length Nanometers(double value) {return new Length(value, Nanometers);}
  public static Length Microns(double value) {return new Length(value, Microns);}
  public static Length Millimeters(double value) {return new Length(value, Millimeters);}
  public static Length Centimeters(double value) {return new Length(value, Centimeters);}
  public static Length Decimeters(double value) {return new Length(value, Decimeters);}
  public static Length Meters(double value) {return new Length(value, Meters);}
  public static Length Decameters(double value) {return new Length(value, Decameters);}
  public static Length Hectometers(double value) {return new Length(value, Hectometers);}
  public static Length Kilometers(double value) {return new Length(value, Kilometers);}
  public static Length Feet(double value) {return new Length(value, Feet);}
  public static Length Inches(double value) {return new Length(value, Inches);}
  public static Length Yards(double value) {return new Length(value, Yards);}
  public static Length UsMiles(double value) {return new Length(value, UsMiles);}
  public static Length InternationalMiles(double value) {return new Length(value, InternationalMiles);}
  public static Length NauticalMiles(double value) {return new Length(value, NauticalMiles);}
  public static Length AstronomicalUnits(double value) {return new Length(value, AstronomicalUnits);}
  public static Length LightYears(double value) {return new Length(value, LightYears);}

  public static final Dimension<Length> Length = new Dimension<Length>(
      "Length", 
      Meters, 
      sequence(Nanometers, Microns, Millimeters, Centimeters, Decimeters, Meters, Decameters, Hectometers, Kilometers, Inches, Feet, Yards, UsMiles, InternationalMiles, NauticalMiles, AstronomicalUnits, LightYears));
  
  public static Length nanometer = Nanometers(1);
  public static Length nanometre = Nanometers(1);
  public static Length micron = Microns(1);
  public static Length micrometer = Microns(1);
  public static Length micrometre = Microns(1);
  public static Length millimeter = Millimeters(1);
  public static Length millimetre = Millimeters(1);
  public static Length centimeter = Centimeters(1);
  public static Length centimetre = Centimeters(1);
  public static Length decimeter = Decimeters(1);
  public static Length decimetre = Decimeters(1);
  public static Length meter = Meters(1);
  public static Length metre = Meters(1);
  public static Length decameter = Decameters(1);
  public static Length decametre = Decameters(1);
  public static Length hectometer = Hectometers(1);
  public static Length hectometre = Hectometers(1);
  public static Length kilometer = Kilometers(1);
  public static Length kilometre = Kilometers(1);
  public static Length inch = Inches(1);
  public static Length foot = Feet(1);
  public static Length yard = Yards(1);
  public static Length mile = UsMiles(1);
  public static Length nauticalMile = NauticalMiles(1);
  public static Length astronomicalUnit = AstronomicalUnits(1);
  public static Length lightYear = LightYears(1);

  public static Length nm(double value) {return Nanometers(value);}
  public static Length nanometers(double value) {return nm(value);}
  public static Length nanometres(double value) {return nm(value);}
  public static Length µm(double value) {return Microns(value);}
  public static Length microns(double value) {return µm(value);}
  public static Length micrometer(double value) {return µm(value);}
  public static Length micrometre(double value) {return µm(value);}
  public static Length mm(double value) {return Millimeters(value);}
  public static Length millimeters(double value) {return mm(value);}
  public static Length millimetres(double value) {return mm(value);}
  public static Length cm(double value) {return Centimeters(value);}
  public static Length centimeter(double value) {return cm(value);}
  public static Length centimetre(double value) {return cm(value);}
  public static Length dm(double value) {return Decimeters(value);}
  public static Length meters(double value) {return Meters(value);}
  public static Length metres(double value) {return Meters(value);}
  public static Length dam(double value) {return Decameters(value);}
  public static Length hm(double value) {return Hectometers(value);}
  public static Length km(double value) {return Kilometers(value);}
  public static Length kilometers(double value) {return km(value);}
  public static Length kilometres(double value) {return km(value);}
  public static Length inches(double value) {return Inches(value);}
  public static Length ft(double value) {return Feet(value);}
  public static Length feet(double value) {return ft(value);}
  public static Length yd(double value) {return Yards(value);}
  public static Length yards(double value) {return Yards(value);}
  public static Length miles(double value) {return UsMiles(value);}
  public static Length nmi(double value) {return NauticalMiles(value);}
  public static Length au(double value) {return AstronomicalUnits(value);}
  public static Length ly(double value) {return LightYears(value);}
  public static Length lightYears(double value) {return ly(value);}

  //  implicit object LengthNumeric extends AbstractQuantityNumeric[dimension](dimension.valueUnit)
}

