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
import jquants.mass.Mass;
import jquants.motion.Force;
import jquants.motion.Velocity;
import jquants.radio.RadiantIntensity;
import jquants.radio.SpectralIntensity;
import jquants.radio.SpectralPower;
import jquants.space.Length;
import jquants.space.Volume;
import jquants.time.SecondTimeIntegral;
import jquants.time.Time;
import jquants.time.TimeIntegral;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.regex.Regex.regex;
import static jquants.energy.Power.*;
import static jquants.energy.EnergyDensity.*;
import static jquants.energy.SpecificEnergy.*;
import static jquants.mass.Mass.*;
import static jquants.space.Area.*;
import static jquants.space.Volume.*;
import static jquants.time.Time.*;

/**
 * Represents a quantity of energy
 *
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public class Energy extends Quantity<Energy> implements TimeIntegral<Power>, SecondTimeIntegral<PowerRamp>{
  private Energy(double value, EnergyUnit valueUnit) {
    this.value = value;
    this.valueUnit = valueUnit;
    this.dimension = Energy;
  }

  public static Option<Energy> toEnergy(String s) {
	return Energy.parseString(s);
  }
	  
  public static UnitOfMeasure factory(String x) {
    return Energy.get(x);
  }
  
  public static class EnergyUnit extends BaseQuantityUnit<Energy> {

	public EnergyUnit(String symbol, double multiplier) {
	  super(symbol, multiplier, true, false, false);
	}
	    
	public EnergyUnit(String symbol, double multiplier, boolean baseUnit, boolean valueUnit) {
	  super(symbol, multiplier, true, baseUnit, valueUnit);
	}
	    
	@Override
	public String dimensionSymbol() {
	  return "E";
	}
	    
	@Override
	public Energy apply(double n) {
	  return new Energy(n, this);
	}
  }
  
  @Override
  public String toString() {
	return this.toString(this.valueUnit);
  }
  
  
//  public Force div(Length that) {return  Newtons(toJoules() / that.toMeters());}
//  public Length div(Force that) {return Meters(toJoules() / that.toKilograms());}
  public SpecificEnergy div(Mass that) {return Grays(toJoules() / that.toKilograms());}
  public Mass div(SpecificEnergy that) {return Kilograms(toJoules() / that.toGrays());}
  public EnergyDensity div(Volume that) {return JoulesPerCubicMeter(toJoules() / that.toCubicMeters());}
  public Volume div(EnergyDensity that) {return CubicMeters(toJoules() / that.toJoulesPerCubicMeter());}
//  public ElectricPotential div(ElectricCharge that) {return Volts(this.toJoules() / that.toCoulombs());}
//  public ElectricCharge div(ElectricPotential that) {return Coulombs(this.toJoules() / that.toVolts());}
//  public ThermalCapacity div(Temperature that) {return JoulesPerKelvin(toJoules() / that.toKelvinDegrees());}
//  public Kelvin div(ThermalCapacity that) {return Kelvin(toJoules() / that.toJoulesPerKelvin());}
  public Power div(Time that) {return Watts(toJoules() / that.toHours());}
  public Time div(Power that) {return Hours(toJoules() / that.toWatts());}

//  def /(that: ChemicalAmount) = ??? // return MolarEnergy
//  def /(that: dimension) = ??? // return Torque (dimensionally equivalent to energy as Angles are dimensionless)
//
//  def /(that: TimeSquared): PowerRamp = this / that.time1 / that.time2

  public Power timeDerived() {return Watts(this.toWattHours());}
  public Time time() {return Hours(1);}    
  
  public double toWattHours() {return to(WattHours);}
  public double toKilowattHours() {return to(KilowattHours);}
  public double toMegawattHours() {return to(MegawattHours);}
  public double toGigawattHours() {return to(GigawattHours);}

  public double toJoules() {return to(Joules);}
  public double toPicojoules() {return to(Picojoules);}
  public double toNanojoules() {return to(Nanojoules);}
  public double toMicrojoules() {return to(Microjoules);}
  public double toMillijoules() {return to(Millijoules);}
  public double toKilojoules() {return to(Kilojoules);}
  public double toMegajoules() {return to(Megajoules);}
  public double toGigajoules() {return to(Gigajoules);}
  public double toTerajoules() {return to(Terajoules);}

  public double toBtus() {return to(BritishThermalUnits);}
  public double toMBtus() {return to(MBtus);}
  public double toMMBtus() {return to(MMBtus);}
  
  
  public static final EnergyUnit WattHours = new EnergyUnit("Wh", 1);
  public static final EnergyUnit KilowattHours = new EnergyUnit("kWh", WattHours.multiplier * MetricSystem.Kilo);
  public static final EnergyUnit MegawattHours = new EnergyUnit("MWh", WattHours.multiplier * MetricSystem.Mega);
  public static final EnergyUnit GigawattHours = new EnergyUnit("GWh", WattHours.multiplier * MetricSystem.Giga);
  
  public static final EnergyUnit Joules = new EnergyUnit("J", 1, true, true);
  public static final EnergyUnit Picojoules = new EnergyUnit("pJ", Joules.multiplier * MetricSystem.Pico);
  public static final EnergyUnit Nanojoules = new EnergyUnit("nJ", Joules.multiplier * MetricSystem.Nano);
  public static final EnergyUnit Microjoules = new EnergyUnit("µJ", Joules.multiplier * MetricSystem.Micro);
  public static final EnergyUnit Millijoules = new EnergyUnit("mJ", Joules.multiplier * MetricSystem.Milli);
  public static final EnergyUnit Kilojoules = new EnergyUnit("kJ", Joules.multiplier * MetricSystem.Kilo);
  public static final EnergyUnit Megajoules = new EnergyUnit("MJ", Joules.multiplier * MetricSystem.Mega);
  public static final EnergyUnit Gigajoules = new EnergyUnit("GJ", Joules.multiplier * MetricSystem.Giga);
  public static final EnergyUnit Terajoules = new EnergyUnit("TJ", Joules.multiplier * MetricSystem.Tera);
  
  public static final EnergyUnit BritishThermalUnits = new EnergyUnit("Btu", Joules.multiplier * 1055.05585262);
  public static final EnergyUnit MBtus = new EnergyUnit("MBtu", BritishThermalUnits.multiplier * MetricSystem.Kilo);
  public static final EnergyUnit MMBtus = new EnergyUnit("MMBtu", BritishThermalUnits.multiplier * MetricSystem.Mega);
  
  public static Energy WattHours(double value) {return new Energy(value, WattHours);}
  public static Energy KilowattHours(double value) {return new Energy(value, KilowattHours);}
  public static Energy MegawattHours(double value) {return new Energy(value, MegawattHours);}
  public static Energy GigawattHours(double value) {return new Energy(value, GigawattHours);}
  
  public static Energy Joules(double value) {return new Energy(value, Joules);}
  public static Energy Picojoules(double value) {return new Energy(value, Picojoules);}
  public static Energy Nanojoules(double value) {return new Energy(value, Nanojoules);}
  public static Energy Microjoules(double value) {return new Energy(value, Microjoules);}
  public static Energy Millijoules(double value) {return new Energy(value, Millijoules);}
  public static Energy Kilojoules(double value) {return new Energy(value, Kilojoules);}
  public static Energy Megajoules(double value) {return new Energy(value, Megajoules);}
  public static Energy Gigajoules(double value) {return new Energy(value, Gigajoules);}
  public static Energy Terajoules(double value) {return new Energy(value, Terajoules);}
  
  public static Energy BritishThermalUnits(double value) {return new Energy(value, BritishThermalUnits);}
  public static Energy MBtus(double value) {return new Energy(value, MBtus);}
  public static Energy MMBtus(double value) {return new Energy(value, MMBtus);}
  
  public static Dimension<Energy> Energy = new Dimension<Energy>(
	      "Energy", 
	      Joules, 
	      sequence(WattHours, KilowattHours, MegawattHours, GigawattHours, Joules, Picojoules, 
	    		  Nanojoules, Microjoules, Millijoules, Kilojoules, Megajoules, Gigajoules, 
	    		  Terajoules, BritishThermalUnits, MBtus, MMBtus));


  public static Energy wattHour = WattHours(1);
  public static Energy Wh = wattHour;
  public static Energy kilowattHour = KilowattHours(1);
  public static Energy kWh = kilowattHour;
  public static Energy megawattHour = MegawattHours(1);
  public static Energy MWh = megawattHour;
  public static Energy gigawattHour = GigawattHours(1);
  public static Energy GWh = gigawattHour;

  public static Energy joule = Joules(1);
  public static Energy picojoule = Picojoules(1);
  public static Energy nanojoule = Nanojoules(1);
  public static Energy microjoule = Microjoules(1);
  public static Energy millijoule = Millijoules(1);
  public static Energy kilojoule = Kilojoules(1);
  public static Energy megajoule = Megajoules(1);
  public static Energy gigajoule = Gigajoules(1);
  public static Energy terajoule = Terajoules(1);

  public static Energy btu = BritishThermalUnits(1);
  public static double btuMultiplier = BritishThermalUnits.multiplier;

  public static Energy J(double value) {return Joules(value);}
  public static Energy joules(double value) {return Joules(value);}
  public static Energy pJ(double value) {return Picojoules(value);}
  public static Energy picojoules(double value) {return Picojoules(value);}
  public static Energy nJ(double value) {return Nanojoules(value);}
  public static Energy nanojoules(double value) {return Nanojoules(value);}
  public static Energy µJ(double value) {return Microjoules(value);}
  public static Energy microjoules(double value) {return Microjoules(value);}
  public static Energy mJ(double value) {return Millijoules(value);}
  public static Energy milljoules(double value) {return Millijoules(value);}
  public static Energy kJ(double value) {return Kilojoules(value);}
  public static Energy kilojoules(double value) {return Kilojoules(value);}
  public static Energy MJ(double value) {return Megajoules(value);}
  public static Energy megajoules(double value) {return Megajoules(value);}
  public static Energy GJ(double value) {return Gigajoules(value);}
  public static Energy gigajoules(double value) {return Gigajoules(value);}
  public static Energy TJ(double value) {return Terajoules(value);}
  public static Energy terajoules(double value) {return Terajoules(value);}

  public static Energy Wh(double value) {return WattHours(value);}
  public static Energy kWh(double value) {return KilowattHours(value);}
  public static Energy MWh(double value) {return MegawattHours(value);}
  public static Energy GWh(double value) {return GigawattHours(value);}
  public static Energy Btu(double value) {return BritishThermalUnits(value);}
  public static Energy MBtu(double value) {return MBtus(value);}
  public static Energy MMBtu(double value) {return MMBtus(value);}
  public static Energy wattHours(double value) {return WattHours(value);}
  public static Energy kilowattHours(double value) {return KilowattHours(value);}
  public static Energy megawattHours(double value) {return MegawattHours(value);}
  public static Energy gigawattHours(double value) {return GigawattHours(value);}



//  implicit object EnergyNumeric extends AbstractQuantityNumeric[dimension](dimension.valueUnit)

}


