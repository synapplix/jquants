package jquants.space;

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
import jquants.mass.AreaDensity;
import jquants.mass.Mass;
import jquants.motion.Force;
import jquants.motion.Velocity;
import jquants.radio.Irradiance;
import jquants.radio.RadiantIntensity;
import jquants.radio.SpectralIntensity;
import jquants.radio.SpectralPower;
import jquants.time.Time;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.regex.Regex.regex;
import static jquants.energy.Power.*;
import static jquants.space.Area.*;
import static jquants.space.Volume.*;
import static jquants.space.Length.*;
import static jquants.mass.AreaDensity.*;
import static jquants.mass.Mass.*;

/**
 * 
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param value value in [[squants.space.SquareMeters]]
 */
public final class Area extends Quantity<Area> {

  private Area(double value, AreaUnit valueUnit) {
    this.value = value;
    this.valueUnit = valueUnit;
    this.dimension = Area;
  }

  public static Area Area(Length length1, Length length2) {
    return new Area(length1.toMeters() * length2.toMeters(), SquareMeters);
  }
  
  public static Option<Area> toArea(String s) {
	    return Area.parseString(s);
	  }
	  public static UnitOfMeasure factory(String x) {
		return Area.get(x);
	  }

  public Volume multiply(Length that) { return CubicMeters(toSquareMeters() * that.toMeters()); }
  public Mass multiply(AreaDensity that) { return Kilograms(toSquareMeters() * that.toKilogramsPerSquareMeter()); }
//  public Force multiply(Pressure that) { return Newtons(toSquareMeters() * that.toPascals()); }
//  public LuminousFlux multiply(Illuminance that) { return Lumens(toSquareMeters() * that.toLux());}
//  public LuminousIntensity multiply(Luminance that) { return Candelas(toSquareMeters() * that.toCandelasPerSquareMeters());}
//  public MagneticFlux multiply(MagneticFluxDensity that) { return Webers(toSquareMeters() * that.toTeslas());}
  public Power multiply(Irradiance that) { return Watts(toSquareMeters() * that.toWattsPerSquareMeter());}
//  public RadiantIntensity multiply(Radiance that) { return WattsPerSteradian(toSquareMeters() * that.toWattsPerSteradianPerSquareMeter());}
  public Length div(Length that) { return Meters(toSquareMeters() / that.toMeters());}

  public double toSquareMeters() { return to(SquareMeters);}
  public double toSquareCentimeters() { return to(SquareCentimeters);}
  public double toSquareKilometers() { return to(SquareKilometers);}
  public double toSquareUsMiles() { return to(SquareUsMiles);}
  public double toSquareYards() { return to(SquareYards);}
  public double toSquareFeet() { return to(SquareFeet);}
  public double toSquareInches() { return to(SquareInches);}
  public double toHectares() { return to(Hectares);}
  public double toAcres() { return to(Acres);}
  public double toBarnes() { return to(Barnes);}
  
  @Override
  public String toString() {
	return this.toString(this.valueUnit);
  }

  public static class AreaUnit extends BaseQuantityUnit<Area> {
    
    public AreaUnit(String symbol, double multiplier) {
      super(symbol, multiplier, true, false, false);
    }
    
    public AreaUnit(String symbol, double multiplier, boolean baseUnit, boolean valueUnit) {
      super(symbol, multiplier, true, baseUnit, valueUnit);
    }
    
    @Override
    public String dimensionSymbol() {
      return "A";
    }
    
    @Override
    public Area apply(double n) {
      return new Area(n, this);
    }
  }

  public static final AreaUnit SquareMeters = new AreaUnit("m²", 1, true, true);
  public static final AreaUnit SquareCentimeters = new AreaUnit("cm²", MetricSystem.Centi * MetricSystem.Centi);
  public static final AreaUnit SquareKilometers = new AreaUnit("km²",MetricSystem.Kilo * MetricSystem.Kilo);
  public static final AreaUnit SquareUsMiles = new AreaUnit("mi²",2.589988110336 * SquareKilometers.multiplier);
  public static final AreaUnit SquareYards = new AreaUnit("yd²", 0.83612736);
  public static final AreaUnit SquareFeet = new AreaUnit("ft²", .09290304);
  public static final AreaUnit SquareInches = new AreaUnit("in²", 6.4516 * SquareCentimeters.multiplier);
  public static final AreaUnit Hectares = new AreaUnit("ha", 10000D);
  public static final AreaUnit Acres = new AreaUnit("acre", 43560 * SquareFeet.multiplier);
  public static final AreaUnit Barnes = new AreaUnit("b", Math.pow(10, -28));

  public static Area SquareMeters(double value) {return new Area(value, SquareMeters);}
  public static Area SquareCentimeters(double value) {return new Area(value, SquareCentimeters);}
  public static Area SquareKilometers(double value) {return new Area(value, SquareKilometers);}
  public static Area SquareUsMiles(double value) {return new Area(value, SquareUsMiles);}
  public static Area SquareYards(double value) {return new Area(value, SquareYards);}
  public static Area SquareFeet(double value) {return new Area(value, SquareFeet);}
  public static Area SquareInches(double value) {return new Area(value, SquareInches);}
  public static Area Hectares(double value) {return new Area(value, Hectares);}
  public static Area Acres(double value) {return new Area(value, Acres);}
  public static Area Barnes(double value) {return new Area(value, Barnes);}

  public static final Dimension<Area> Area = new Dimension<Area>(
      "Area", 
      SquareMeters,
      sequence(SquareMeters, SquareCentimeters, SquareKilometers, SquareUsMiles, SquareYards, SquareFeet, SquareInches, Hectares, Acres, Barnes));
  
  
  public static Area squareMeter = SquareMeters(1);
  public static Area squareCentimeter = SquareCentimeters(1);
  public static Area squareKilometer = SquareKilometers(1);
  public static Area squareMile = SquareUsMiles(1);
  public static Area squareYard = SquareYards(1);
  public static Area squareFoot = SquareFeet(1);
  public static Area squareInch = SquareInches(1);
  public static Area hectare = Hectares(1);
  public static Area acre = Acres(1);
  public static Area barne = Barnes(1);
  
  public static Area squareMeters(double value) {return SquareMeters(value);}
  public static Area squareCentimeters(double value) {return SquareCentimeters(value);}
  public static Area squareKilometers(double value) {return SquareKilometers(value);}
  public static Area squareMiles(double value) {return SquareUsMiles(value);}
  public static Area squareYards(double value) {return SquareYards(value);}
  public static Area squareFeet(double value) {return SquareFeet(value);}
  public static Area squareInches(double value) {return SquareInches(value);}
  public static Area hectares(double value) {return Hectares(value);}
  public static Area acres(double value) {return Acres(value);}
  public static Area barnes(double value) {return Barnes(value);}

//  implicit object AreaNumeric extends AbstractQuantityNumeric[dimension](dimension.valueUnit)
}
