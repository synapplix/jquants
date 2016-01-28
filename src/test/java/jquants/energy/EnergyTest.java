package jquants.energy;

import jquants.MetricSystem;

import org.hamcrest.core.Is;
import org.junit.Test;

import static jquants.energy.SpecificEnergy.*;
import static jquants.energy.EnergyDensity.*;
import static jquants.energy.Power.*;
import static jquants.energy.Energy.*;
import static jquants.mass.Mass.*;
import static jquants.space.Length.*;
import static jquants.space.Area.*;
import static jquants.space.Volume.*;
import static jquants.motion.Velocity.*;
import static jquants.time.Time.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class EnergyTest {

  @Test
  public void testCreateValuesUsingUOMFactories() {
    assertThat(WattHours(1).toWattHours(), is(1d));
    assertThat(KilowattHours(1).toKilowattHours(), is(1d));
    assertThat(MegawattHours(1).toMegawattHours(), is(1d));
    assertThat(GigawattHours(1).toGigawattHours(), is(1d));

    assertThat(Joules(1).toJoules(), is(1d));
    assertThat(Picojoules(1).toPicojoules(), is(1d));
    assertThat(Nanojoules(1).toNanojoules(), is(1d));
    assertThat(Microjoules(1).toMicrojoules(), is(1d));
    assertThat(Millijoules(1).toMillijoules(), is(1d));
    assertThat(Kilojoules(1).toKilojoules(), is(1d));
    assertThat(Megajoules(1).toMegajoules(), is(1d));
    assertThat(Gigajoules(1).toGigajoules(), is(1d));
    assertThat(Terajoules(1).toTerajoules(), is(1d));

    assertThat(BritishThermalUnits(1).toBtus(), is(1d));
    assertThat(MBtus(1).toMBtus(), is(1d));
    assertThat(MMBtus(1).toMMBtus(), is(1d));
  }
	
	

  @Test
  public void testCreateValuesFromProperlyFormattedStrings() {
    assertThat(toEnergy("10.22 J").get(), is(Joules(10.22)));
    assertThat(toEnergy("10.22 Wh").get(), is(WattHours(10.22)));
    assertThat(toEnergy("10.22 kWh").get(), is(KilowattHours(10.22)));
    assertThat(toEnergy("10.22 MWh").get(), is(MegawattHours(10.22)));
    assertThat(toEnergy("10.22 GWh").get(), is(GigawattHours(10.22)));
    assertThat(toEnergy("10.22 Btu").get(), is(BritishThermalUnits(10.22)));
    assertThat(toEnergy("10.22 MBtu").get(), is(MBtus(10.22)));
    assertThat(toEnergy("10.22 MMBtu").get(), is(MMBtus(10.22)));
//    assertThat(toEnergy("10.22 zz").failed.get(), is(QuantityStringParseException("Unable to parse Energy", "10.22 zz")));
//    assertThat(toEnergy("ZZ J").failed.get(), is(QuantityStringParseException("Unable to parse Energy", "ZZ J")));
  }
	
  @Test
  public void testProperlyConvertToAllSupportedUnitsOfMeasure() {
  Energy x = WattHours(1);

    assertThat(x.toWattHours(), is(1d));
    assertThat(x.toKilowattHours(), is(1 / MetricSystem.Kilo));
    assertThat(x.toMegawattHours(), is(1 / MetricSystem.Mega));
    assertThat(x.toGigawattHours(), is(1 / MetricSystem.Giga));

    assertThat(x.toJoules(), is(1 / Joules.multiplier));
    assertThat(x.toPicojoules(), is(1 / Picojoules.multiplier));
    assertThat(x.toNanojoules(), is(1 / Nanojoules.multiplier));
    assertThat(x.toMicrojoules(), is(1 / Microjoules.multiplier));
    assertThat(x.toMillijoules(), is(1 / Millijoules.multiplier));
    assertThat(x.toKilojoules(), is(1 / Kilojoules.multiplier));
    assertThat(x.toMegajoules(), is(1 / Megajoules.multiplier));
    assertThat(x.toGigajoules(), is(1 / Gigajoules.multiplier));

    assertThat(x.toBtus(), is(1 / BritishThermalUnits.multiplier));
    assertThat(x.toMBtus(), is(1 / MBtus.multiplier));
    assertThat(x.toMMBtus(), is(1 / MMBtus.multiplier));
  }
	
  @Test
  public void testReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure() {
    assertThat(WattHours(1).toString(), is("1.0 Wh"));
    assertThat(KilowattHours(1).toString(), is("1.0 kWh"));
    assertThat(MegawattHours(1).toString(), is("1.0 MWh"));
    assertThat(GigawattHours(1).toString(), is("1.0 GWh"));

    assertThat(Joules(1).toString(), is("1.0 J"));
    assertThat(Picojoules(1).toString(), is("1.0 pJ"));
    assertThat(Nanojoules(1).toString(), is("1.0 nJ"));
    assertThat(Microjoules(1).toString(), is("1.0 µJ"));
    assertThat(Millijoules(1).toString(), is("1.0 mJ"));
    assertThat(Kilojoules(1).toString(), is("1.0 kJ"));
    assertThat(Megajoules(1).toString(), is("1.0 MJ"));
    assertThat(Gigajoules(1).toString(), is("1.0 GJ"));
    assertThat(Terajoules(1).toString(), is("1.0 TJ"));

    assertThat(BritishThermalUnits(1).toString(), is("1.0 Btu"));
    assertThat(MBtus(1).toString(), is("1.0 MBtu"));
    assertThat(MMBtus(1).toString(), is("1.0 MMBtu"));
  }
	
  @Test
  public void testReturnPowerWhenDividedByTime() {
    assertThat(WattHours(1).div(Hours(1)), is(Watts(1)));
  }
	
  @Test
  public void testReturnTimeWhenDividedByPower() {
    assertThat(WattHours(1).div(Watts(1)), is(Hours(1)));
  }
	
//  @Test
//  public void testReturnElectricalPotentialWhenDividedByElectricalCharge() {
//    assertThat(Joules(1).div(Coulombs(1)), is(Volts(1)));
//  }
//	
//  @Test
//  public void testReturnForceWhenDividedByLength() {
//    assertThat(Joules(1).div(Meters(1)), is(Newtons(1)));
//  }
	
  @Test
  public void testReturnMassWhenDividedBySpecificEnergy() {
    assertThat(Joules(1).div(Grays(1)), is(Kilograms(1)));
  }
	
  @Test
  public void testReturnSpecificEnergyWhenDividedByMass() {
    assertThat(Joules(1).div(Kilograms(1)), is(Grays(1)));
  }
	
  @Test
  public void testReturnVolumeWhenDividedByEnergyDensity() {
    assertThat(Joules(1).div(JoulesPerCubicMeter(1)), is(CubicMeters(1)));
  }
	
  @Test
  public void testReturnEnergyDensityWhenDividedByVolume() {
    assertThat(Joules(1).div(CubicMeters(1)), is(JoulesPerCubicMeter(1)));
  }
	
//  @Test
//  public void testReturnThermalCapacityWhenDividedByTemperature() {
//    assertThat(Joules(10).div(KelvinDegrees(4)), is(JoulesPerKelvin(2.5)));
//  }
	
//  @Test
//  public void testReturnTemperatureWhenDividedByThermalCapacity() {
//    assertThat(Joules(10).div(JoulesPerKelvin(2)), is(Kelvin(5)));
//  }
	
//  @Test //not yet implemented
//  public void testCalculateKinticEnergyFromMassAndVelocity() {
//    assertThat(KineticEnergy(Kilograms(10), MetersPerSecond(5)), is(Joules(125))
//    assertThat(KineticEnergy(Kilograms(5), MetersPerSecond(10)), is(Joules(250))
//  }
//	
//  @Test //not yet implemented
//  public void testCalculateKineticEnergyFromMassAndMomentum() {
//    assertThat(KineticEnergy(Kilograms(10), NewtonSeconds(5)), is(Joules(.25))
//    assertThat(KineticEnergy(Kilograms(5), NewtonSeconds(10)), is(Joules(1))
//  }
	
  @Test
  public void testProvideAliasesForSingleUnitValues() {
    assertThat(wattHour, is(WattHours(1)));
    assertThat(Wh, is(WattHours(1)));
    assertThat(kilowattHour, is(KilowattHours(1)));
    assertThat(kWh, is(KilowattHours(1)));
    assertThat(megawattHour, is(MegawattHours(1)));
    assertThat(MWh, is(MegawattHours(1)));
    assertThat(gigawattHour, is(GigawattHours(1)));
    assertThat(GWh, is(GigawattHours(1)));

    assertThat(joule, is(Joules(1)));
    assertThat(picojoule, is(Picojoules(1)));
    assertThat(nanojoule, is(Nanojoules(1)));
    assertThat(microjoule, is(Microjoules(1)));
    assertThat(millijoule, is(Millijoules(1)));
    assertThat(kilojoule, is(Kilojoules(1)));
    assertThat(megajoule, is(Megajoules(1)));
    assertThat(gigajoule, is(Gigajoules(1)));
    assertThat(terajoule, is(Terajoules(1)));

    assertThat(btu, is(BritishThermalUnits(1)));
    assertThat(btuMultiplier, is(joule.value * 1055.05585262));
  }
	
  @Test
  public void testProvideImplicitConversionsFromDouble() {
  double d = 10d;
    assertThat(Wh(d), is(WattHours(d)));
    assertThat(kWh(d), is(KilowattHours(d)));
    assertThat(MWh(d), is(MegawattHours(d)));
    assertThat(GWh(d), is(GigawattHours(d)));
    assertThat(wattHours(d), is(WattHours(d)));
    assertThat(kilowattHours(d), is(KilowattHours(d)));
    assertThat(megawattHours(d), is(MegawattHours(d)));
    assertThat(gigawattHours(d), is(GigawattHours(d)));

    assertThat(J(d), is(Joules(d)));
    assertThat(joules(d), is(Joules(d)));
    assertThat(picojoules(d), is(Picojoules(d)));
    assertThat(nanojoules(d), is(Nanojoules(d)));
    assertThat(microjoules(d), is(Microjoules(d)));
    assertThat(milljoules(d), is(Millijoules(d)));
    assertThat(kilojoules(d), is(Kilojoules(d)));
    assertThat(megajoules(d), is(Megajoules(d)));
    assertThat(gigajoules(d), is(Gigajoules(d)));

    assertThat(Btu(d), is(BritishThermalUnits(d)));
    assertThat(MBtu(d), is(MBtus(d)));
    assertThat(MMBtu(d), is(MMBtus(d)));
  }
	
  @Test
  public void testProvideImplicitConversionsFromString() {
	    assertThat(toEnergy("10.22 J").get(), is(Joules(10.22)));
	    assertThat(toEnergy("10.22 Wh").get(), is(WattHours(10.22)));
	    assertThat(toEnergy("10.22 kWh").get(), is(KilowattHours(10.22)));
	    assertThat(toEnergy("10.22 MWh").get(), is(MegawattHours(10.22)));
	    assertThat(toEnergy("10.22 GWh").get(), is(GigawattHours(10.22)));
	    assertThat(toEnergy("10.22 Btu").get(), is(BritishThermalUnits(10.22)));
	    assertThat(toEnergy("10.22 MBtu").get(), is(MBtus(10.22)));
	    assertThat(toEnergy("10.22 MMBtu").get(), is(MMBtus(10.22)));
//	    assertThat(toEnergy("10.22 zz").failed.get(), is(QuantityStringParseException("Unable to parse Energy", "10.22 zz")));
//	    assertThat(toEnergy("ZZ J").failed.get(), is(QuantityStringParseException("Unable to parse Energy", "ZZ J")));
	  }
	
	
	
//  it should "provide Numeric support" in {
//  import EnergyConversions.EnergyNumeric
//
//  val es = List(WattHours(100), KilowattHours(1))
//  es.sum should be(KilowattHours(1.1))
//}

}

