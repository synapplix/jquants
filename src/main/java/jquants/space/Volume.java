package jquants.space;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.motion.VolumeFlowRate.CubicMetersPerSecond;
import static jquants.space.Area.SquareMeters;
import static jquants.space.Length.Feet;
import static jquants.space.Length.Inches;
import static jquants.space.Length.Meters;
import static jquants.space.Length.UsMiles;
import static jquants.space.Length.Yards;
import static jquants.time.Time.Seconds;

import java.math.BigDecimal;

import com.googlecode.totallylazy.Option;

import jquants.BaseQuantityUnit;
import jquants.Dimension;
import jquants.MetricSystem;
import jquants.Quantity;
import jquants.UnitOfMeasure;
import jquants.motion.VolumeFlowRate;
import jquants.time.Time;
import jquants.time.TimeIntegral;

/**
 * Represents a quantity of dimension (three-dimensional space)
 *
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public final class Volume extends Quantity<Volume> implements TimeIntegral<VolumeFlowRate> {

  private Volume(double value, VolumeUnit valueUnit) {
    this.value = value;
    this.valueUnit = valueUnit;
    this.dimension = Volume;
  }

  public static Volume Volume(Area area, Length length) {
    return new Volume(area.toSquareMeters() * length.toMeters(), CubicMeters);
  }
  
  public static Option<Volume> toVolume(String s) {
    return Volume.parseString(s);
  }
	  
  public static UnitOfMeasure factory(String x) {
    return Volume.get(x);
  }
  
//  public Mass multiply(Density that) { return Kilograms(toCubicMeters() * that.toKilogramsPerCubicMeter());}
//  public Energy multiply(EnergyDensity that) { return Joules(toCubicMeters() * that.toJoulesPerCubicMeter());}
  public Length div(Area that) { return Meters(toCubicMeters() / that.toSquareMeters());}
  public Area div(Length that) { return SquareMeters(toCubicMeters() / that.toMeters());}
 
//  public Object div(that: Mass) = ??? // returns SpecificVolume (inverse of Density)
//  public Object div(that: ChemicalAmount) = ??? // return MolarVolume

  @Override
  public Time time(){return Seconds(1);}
  @Override
  public VolumeFlowRate timeDerived() {return CubicMetersPerSecond(toCubicMeters());}
  @Override
  public Time div(VolumeFlowRate that) {return that.time().multiply(this.timeDerived().div(that));}
  
  
  public double toCubicMeters() { return to(CubicMeters);}
  public double toLitres() { return to(Litres);}
  public double toNanolitres() { return to(Nanolitres);}
  public double toMicrolitres() { return to(Microlitres);}
  public double toMillilitres() { return to(Millilitres);}
  public double toCentilitres() { return to(Centilitres);}
  public double toDecilitres() { return to(Decilitres);}
  public double toHectolitres() { return to(Hectolitres);}

  public double toCubicMiles() { return to(CubicMiles);}
  public double toCubicYards() { return to(CubicYards);}
  public double toCubicFeet() { return to(CubicFeet);}
  public double toCubicInches() { return to(CubicInches);}

  public double toUsGallons() { return to(UsGallons);}
  public double toUsQuarts() { return to(UsQuarts);}
  public double toUsPints() { return to(UsPints);}
  public double toUsCups() { return to(UsCups);}
  public double toFluidOunces() { return to(FluidOunces);}
  public double toTablespoons() { return to(Tablespoons);}
  public double toTeaspoons() { return to(Teaspoons);}

  public double toUsDryGallons() { return to(UsDryGallons);}
  public double toUsDryQuarts() { return to(UsDryQuarts);}
  public double toUsDryPints() { return to(UsDryPints);}
  public double toUsDryCups() { return to(UsDryCups);}

  public double toImperialGallons() { return to(ImperialGallons);}
  public double toImperialQuarts() { return to(ImperialQuarts);}
  public double toImperialPints() { return to(ImperialPints);}
  public double toImperialCups() { return to(ImperialCups);}


  @Override
  public String toString() {
	return this.toString(this.valueUnit);
  }

  public static final class VolumeUnit extends BaseQuantityUnit<Volume> {
    public VolumeUnit(String symbol, double multiplier) {
      super(symbol, multiplier, true, false, false);
    }
    
    public VolumeUnit(String symbol, double multiplier, boolean baseUnit, boolean valueUnit) {
      super(symbol, multiplier, true, baseUnit, valueUnit);
    }
    
    @Override
    public String dimensionSymbol() {
      return "V";
    }
    
    @Override
    public Volume apply(double n) {
      return new Volume(n, this);
    }
  }

  public static final VolumeUnit CubicMeters = new VolumeUnit("m³", 1, true, true);
  public static final VolumeUnit Litres = new VolumeUnit("L", .001);
  public static final VolumeUnit Nanolitres = new VolumeUnit("nl", Litres.multiplier * MetricSystem.Nano);
  public static final VolumeUnit Microlitres = new VolumeUnit("µl", Litres.multiplier * MetricSystem.Micro);
  public static final VolumeUnit Millilitres = new VolumeUnit("ml", Litres.multiplier * MetricSystem.Milli);
  public static final VolumeUnit Centilitres = new VolumeUnit("cl", Litres.multiplier * MetricSystem.Centi);
  public static final VolumeUnit Decilitres = new VolumeUnit("dl", Litres.multiplier * MetricSystem.Deci);
  public static final VolumeUnit Hectolitres = new VolumeUnit("hl", Litres.multiplier * MetricSystem.Hecto);
  public static final VolumeUnit CubicMiles = new VolumeUnit("mi³", Math.pow(UsMiles.multiplier, 3));
  public static final VolumeUnit CubicYards = new VolumeUnit("yd³", new BigDecimal(Yards.multiplier).pow(3).doubleValue());
  public static final VolumeUnit CubicFeet = new VolumeUnit("ft³", new BigDecimal(Feet.multiplier).pow(3).doubleValue());
  public static final VolumeUnit CubicInches = new VolumeUnit("in³", new BigDecimal(Inches.multiplier).pow(3).doubleValue());
  public static final VolumeUnit UsGallons = new VolumeUnit("gal", Millilitres.multiplier * 3785.411784);
  public static final VolumeUnit UsQuarts = new VolumeUnit("qt", UsGallons.multiplier / 4d);
  public static final VolumeUnit UsPints = new VolumeUnit("pt", UsGallons.multiplier / 8d);
  public static final VolumeUnit UsCups = new VolumeUnit("c", UsGallons.multiplier / 16d);
  public static final VolumeUnit FluidOunces = new VolumeUnit("oz", UsGallons.multiplier / 128d);
  public static final VolumeUnit Tablespoons = new VolumeUnit("tbsp", FluidOunces.multiplier / 2d);
  public static final VolumeUnit Teaspoons = new VolumeUnit("tsp", FluidOunces.multiplier / 6d);
  public static final VolumeUnit UsDryGallons = new VolumeUnit("gal", Millilitres.multiplier * 4404.8837);
  public static final VolumeUnit UsDryQuarts = new VolumeUnit("qt", UsDryGallons.multiplier / 4d);
  public static final VolumeUnit UsDryPints = new VolumeUnit("pt", UsDryGallons.multiplier / 8d);
  public static final VolumeUnit UsDryCups = new VolumeUnit("c", UsDryGallons.multiplier / 16d);
  public static final VolumeUnit ImperialGallons = new VolumeUnit("gal", Millilitres.multiplier * 4546.09);
  public static final VolumeUnit ImperialQuarts = new VolumeUnit("qt", ImperialGallons.multiplier / 4d);
  public static final VolumeUnit ImperialPints = new VolumeUnit("pt", ImperialGallons.multiplier / 8d);
  public static final VolumeUnit ImperialCups = new VolumeUnit("c", ImperialGallons.multiplier / 16d);

  public static Volume CubicMeters(double value) {return new Volume(value, CubicMeters);}
  public static Volume Litres(double value) {return new Volume(value, Litres);}
  public static Volume Nanolitres(double value) {return new Volume(value, Nanolitres);}
  public static Volume Microlitres(double value) {return new Volume(value, Microlitres);}
  public static Volume Millilitres(double value) {return new Volume(value, Millilitres);}
  public static Volume Centilitres(double value) {return new Volume(value, Centilitres);}
  public static Volume Decilitres(double value) {return new Volume(value, Decilitres);}
  public static Volume Hectolitres(double value) {return new Volume(value, Hectolitres);}
  public static Volume CubicMiles(double value) {return new Volume(value, CubicMiles);}
  public static Volume CubicYards(double value) {return new Volume(value, CubicYards);}
  public static Volume CubicFeet(double value) {return new Volume(value, CubicFeet);}
  public static Volume CubicInches(double value) {return new Volume(value, CubicInches);}
  public static Volume UsGallons(double value) {return new Volume(value, UsGallons);}
  public static Volume UsQuarts(double value) {return new Volume(value, UsQuarts);}
  public static Volume UsPints(double value) {return new Volume(value, UsPints);}
  public static Volume UsCups(double value) {return new Volume(value, UsCups);}
  public static Volume FluidOunces(double value) {return new Volume(value, FluidOunces);}
  public static Volume Tablespoons(double value) {return new Volume(value, Tablespoons);}
  public static Volume Teaspoons(double value) {return new Volume(value, Teaspoons);}
  public static Volume UsDryGallons(double value) {return new Volume(value, UsDryGallons);}
  public static Volume UsDryQuarts(double value) {return new Volume(value, UsDryQuarts);}
  public static Volume UsDryPints(double value) {return new Volume(value, UsDryPints);}
  public static Volume UsDryCups(double value) {return new Volume(value, UsDryCups);}
  public static Volume ImperialGallons(double value) {return new Volume(value, ImperialGallons);}
  public static Volume ImperialQuarts(double value) {return new Volume(value, ImperialQuarts);}
  public static Volume ImperialPints(double value) {return new Volume(value, ImperialPints);}
  public static Volume ImperialCups(double value) {return new Volume(value, ImperialCups);}
  
  public static final Dimension<Volume> Volume = new Dimension<Volume>(
	      "Volume", 
	      CubicMeters, 
	      sequence(CubicMeters, Litres, Nanolitres, Microlitres, Millilitres, Centilitres, 
	    		  Decilitres, Hectolitres, CubicMiles, CubicYards, CubicFeet, CubicInches, UsGallons, UsQuarts, 
	    		  UsPints, UsCups, FluidOunces, Tablespoons, Teaspoons, UsDryGallons, UsDryQuarts, UsDryPints, 
	    		  UsDryCups, ImperialGallons, ImperialQuarts, ImperialPints, ImperialCups));
  
  public static Volume cubicMeter = CubicMeters(1);
  public static Volume litre = Litres(1);
  public static Volume liter = Litres(1);
  public static Volume nanolitre = Nanolitres(1);
  public static Volume nanoliter = Nanolitres(1);
  public static Volume microlitre = Microlitres(1);
  public static Volume microliter = Microlitres(1);
  public static Volume millilitre = Millilitres(1);
  public static Volume milliliter = Millilitres(1);
  public static Volume centilitre = Centilitres(1);
  public static Volume centiliter = Centilitres(1);
  public static Volume decilitre = Decilitres(1);
  public static Volume deciliter = Decilitres(1);
  public static Volume hectolitre = Hectolitres(1);
  public static Volume hectoliter = Hectolitres(1);

  public static Volume cubicMile = CubicMiles(1);
  public static Volume cubicYard = CubicYards(1);
  public static Volume cubicFoot = CubicFeet(1);
  public static Volume cubicInch = CubicInches(1);

  public static Volume gallon = UsGallons(1);
  public static Volume quart = UsQuarts(1);
  public static Volume pint = UsPints(1);
  public static Volume cup = UsCups(1);
  
  public static Volume fluidOunce = FluidOunces(1);
  public static Volume tableSpoon = Tablespoons(1);
  public static Volume teaSpoon = Teaspoons(1);
  
  public static Volume cubicMeters(double value) {return CubicMeters(value);}
  public static Volume cubicMetres(double value) {return CubicMeters(value);}
  public static Volume litres(double value) {return Litres(value);}
  public static Volume liters(double value) {return Litres(value);}
  public static Volume nanolitres(double value) {return Nanolitres(value);}
  public static Volume nanoliters(double value) {return Nanolitres(value);}
  public static Volume microlitres(double value) {return Microlitres(value);}
  public static Volume microliters(double value) {return Microlitres(value);}
  public static Volume millilitres(double value) {return Millilitres(value);}
  public static Volume milliliters(double value) {return Millilitres(value);}
  public static Volume centilitres(double value) {return Centilitres(value);}
  public static Volume centiliters(double value) {return Centilitres(value);}
  public static Volume decilitres(double value) {return Decilitres(value);}
  public static Volume deciliters(double value) {return Decilitres(value);}
  public static Volume hectolitres(double value) {return Hectolitres(value);}
  public static Volume hectoliters(double value) {return Hectolitres(value);}

  public static Volume cubicMiles(double value) {return CubicMiles(value);}
  public static Volume cubicYards(double value) {return CubicYards(value);}
  public static Volume cubicFeet(double value) {return CubicFeet(value);}
  public static Volume cubicInches(double value) {return CubicInches(value);}

  public static Volume gallons(double value) {return UsGallons(value);}
  public static Volume quarts(double value) {return UsQuarts(value);}
  public static Volume pints(double value) {return UsPints(value);}
  public static Volume cups(double value) {return UsCups(value);}
  
  public static Volume fluidOunces(double value) {return FluidOunces(value);}
  public static Volume tableSpoons(double value) {return Tablespoons(value);}
  public static Volume teaSpoons(double value) {return Teaspoons(value);}

//  implicit object VolumeNumeric extends AbstractQuantityNumeric[dimension](dimension.valueUnit)
}
