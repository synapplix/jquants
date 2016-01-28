package jquants.space;

import jquants.MetricSystem;

import org.hamcrest.core.Is;
import org.junit.Test;

import static jquants.space.Length.*;
import static jquants.space.Area.*;
import static jquants.space.Volume.*;
import static jquants.motion.Velocity.*;
import static jquants.time.Time.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LengthTest {

  @Test
  public void testEquals(){
	  assertTrue(Meters(1).equals(Centimeters(100)));
	  assertThat(Meters(1), is(Centimeters(100)));
	  assertThat(toLength("10.33 in").get(), is(Inches(10.33)));
  }
	
  @Test
  public void testCreateValuesUsingUOMFactories() {
    assertThat(Meters(1).toMeters(), is(1d));
    assertThat(Nanometers(1).toNanometers(), is(1d));
    assertThat(Microns(1).toMicrons(), is(1d));
    assertThat(Millimeters(1).toMillimeters(), is(1d));
    assertThat(Centimeters(1).toCentimeters(), is(1d));
    assertThat(Decimeters(1).toDecimeters(), is(1d));
    assertThat(Decameters(1).toDecameters(), is(1d));
    assertThat(Hectometers(1).toHectometers(), is(1d));
    assertThat(Kilometers(1).toKilometers(), is(1d));

    assertThat(Inches(1).toInches(), is(1d));
    assertThat(Feet(1).toFeet(), is(1d));
    assertThat(Yards(1).toYards(), is(1d));
    assertThat(UsMiles(1).toUsMiles(), is(1d));
    assertThat(InternationalMiles(1).toInternationalMiles(), is(1d));
    assertThat(NauticalMiles(1).toNauticalMiles(), is(1d));
    assertThat(AstronomicalUnits(1).toAstronomicalUnits(), is(1d));
    assertThat(LightYears(1).toLightYears(), is(1d));
  }

  @Test
  public void testCreateValuesFromProperlyFormattedStrings() {
	assertThat(toLength("10.33 nm").get(), is(Nanometers(10.33)));
    assertThat(toLength("10.33 µm").get(), is(Microns(10.33)));
    assertThat(toLength("10.33 mm").get(), is(Millimeters(10.33)));
    assertThat(toLength("10.33 cm").get(), is(Centimeters(10.33)));
    assertThat(toLength("10.33 dm").get(), is(Decimeters(10.33)));
    assertThat(toLength("10.33 m").get(), is(Meters(10.33)));
    assertThat(toLength("10.33 dam").get(), is(Decameters(10.33)));
    assertThat(toLength("10.33 hm").get(), is(Hectometers(10.33)));
    assertThat(toLength("10.33 km").get(), is(Kilometers(10.33)));
    assertThat(toLength("10.33 in").get(), is(Inches(10.33)));
    assertThat(toLength("10.33 ft").get(), is(Feet(10.33)));
    assertThat(toLength("10.33 yd").get(), is(Yards(10.33)));
    assertThat(toLength("10.33 mi").get(), is(UsMiles(10.33)));
    assertThat(toLength("10.33 InternationalMiles").get(), is(InternationalMiles(10.33)));
    assertThat(toLength("10.33 nmi").get(), is(NauticalMiles(10.33)));
    assertThat(toLength("10.33 au").get(), is(AstronomicalUnits(10.33)));
    assertThat(toLength("10.33 ly").get(), is(LightYears(10.33)));
//    assertThat(toLength("10.33 zz").get(), is(Inches(10.33)));
//    assertThat(toLength("ZZ m").get(), is(Inches(10.33)));

//    Length("10.33 zz").failed.get should be(QuantityStringParseException("Unable to parse Length", "10.33 zz"))
//    Length("ZZ m").failed.get should be(QuantityStringParseException("Unable to parse Length", "ZZ m"))
  }
  
  @Test
  public void testOperations() {
    assertThat(Meters(1).plus(Meters(2)), is(Meters(3)));
    assertThat(Meters(1).plus(Meters(2)).toString(), is("3.0 m"));
    
    assertThat(Meters(1).plus(Centimeters(10)), is(Meters(1.1)));
    assertThat(Meters(1).plus(Centimeters(10)).toString(), is("1.1 m"));
  }
  
  @Test
  public void testProperlyConvertToAllSupportedUnitsOfMeasure() {
	  Length x = Meters(1);
	  assertThat(x.toMeters(), is(1d));
	  assertThat(x.toNanometers(), is(1 / MetricSystem.Nano));
	  assertThat(x.toMicrons(), is(1 / MetricSystem.Micro));
	  assertThat(x.toMillimeters(), is(1 / MetricSystem.Milli));
	  assertThat(x.toCentimeters(), is(1 / MetricSystem.Centi));
	  assertThat(x.toDecimeters(), is(1 / MetricSystem.Deci));
	  assertThat(x.toDecameters(), is(1 / MetricSystem.Deca));
	  assertThat(x.toHectometers(), is(1 / MetricSystem.Hecto));
	  assertThat(x.toKilometers(), is(1 / MetricSystem.Kilo));
	  
	  double metersPerFoot = 0.3048006096;
	  assertThat(x.toInches(), is(1 / (metersPerFoot / 12)));
	  assertThat(x.toFeet(), is(1 / metersPerFoot));
	  assertThat(x.toYards(), is(1 / (metersPerFoot * 3)));
	  assertThat(x.toUsMiles(), is(1 / (metersPerFoot * 5280)));
	  assertThat(x.toInternationalMiles(), is(1 / 1609.344));
	  assertThat(x.toNauticalMiles(), is(1 / 1852d));
	  assertThat(x.toAstronomicalUnits(), is(1 / 149597870700d));
	  assertThat(x.toLightYears(), is(1 / 9460730472580800d));
  }
  
  @Test
  public void testReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure() {
	  assertThat(Meters(1).toString(), is("1.0 m"));
	  assertThat(Nanometers(1).toString(), is("1.0 nm"));
	  assertThat(Microns(1).toString(), is("1.0 µm"));
	  assertThat(Millimeters(1).toString(), is("1.0 mm"));
	  assertThat(Centimeters(1).toString(), is("1.0 cm"));
	  assertThat(Decimeters(1).toString(), is("1.0 dm"));
	  assertThat(Decameters(1).toString(), is("1.0 dam"));
	  assertThat(Hectometers(1).toString(), is("1.0 hm"));
	  assertThat(Kilometers(1).toString(), is("1.0 km"));
	  
	  assertThat(Inches(1).toString(), is("1.0 in"));
	  assertThat(Feet(1).toString(), is("1.0 ft"));
	  assertThat(Yards(1).toString(), is("1.0 yd"));
	  assertThat(UsMiles(1).toString(), is("1.0 mi"));
	  assertThat(InternationalMiles(1).toString(), is("1.0 InternationalMiles"));
	  assertThat(NauticalMiles(1).toString(), is("1.0 nmi"));
	  assertThat(AstronomicalUnits(1).toString(), is("1.0 au"));
	  assertThat(LightYears(1).toString(), is("1.0 ly"));
  }
  
  @Test
  public void testReturnAreaWhenMultipliedByLength() {
	  assertThat(Meters(1).squared(), is(SquareMeters(1)));
  }
  
  @Test
  public void testReturnVolumeWhenMultipliedByArea() {
	  assertThat(Meters(1).cubed(), is(CubicMeters(1)));
  }
  
//  @Test
//  public void testReturnEnergyWhenMultipliedByForce() {
//	  assertThat(Meters(1).multiply(Newtons(1)), is(Joules(1)));
//  }
//  
//  @Test
//  public void testReturnElectricalConductanceWhenMultipliedByConductivity() {
//	  assertThat(Meters(1).multiply(SiemensPerMeter(1)), is(Siemens(1)));
//  }
//  
//  @Test
//  public void testReturnResistivityWhenMultipliedByElectricalResistance() {
//	  assertThat(Meters(1).multiply(Ohms(1)), is(OhmMeters(1)));
//  }
//  
//  @Test
//  public void testReturnVelocityWhenDividedByTime() {
//	  assertThat(Meters(1).div(Seconds(1)), is(MetersPerSecond(1)));
//  }
//  
//  @Test
//  public void testReturnTimeWhenDividedByVelocity() {
//	  assertThat(Meters(1).div(MetersPerSecond(1)), is(Seconds(1)));
//  }
  
  @Test
  public void testReturnAreaWhenSquared() {
	  assertThat(Meters(4).squared(), is(SquareMeters(16)));
  }
  
  @Test
  public void testReturnAVolumeWhenCubed() {
	  assertThat(Meters(3).cubed(), is(CubicMeters(27)));
  }
  
  @Test
  public void testProvideAliasesForSingleUnitValues() {
	  assertThat(nanometer, is(Nanometers(1)));
	  assertThat(nanometre, is(Nanometers(1)));
	  assertThat(micron, is(Microns(1)));
	  assertThat(micrometer, is(Microns(1)));
	  assertThat(micrometre, is(Microns(1)));
	  assertThat(millimeter, is(Millimeters(1)));
	  assertThat(millimetre, is(Millimeters(1)));
	  assertThat(centimeter, is(Centimeters(1)));
	  assertThat(centimetre, is(Centimeters(1)));
	  assertThat(meter, is(Meters(1)));
	  assertThat(metre, is(Meters(1)));
	  assertThat(decameter, is(Decameters(1)));
	  assertThat(decametre, is(Decameters(1)));
	  assertThat(hectometer, is(Hectometers(1)));
	  assertThat(hectometre, is(Hectometers(1)));
	  assertThat(kilometer, is(Kilometers(1)));
	  assertThat(kilometre, is(Kilometers(1)));
	  assertThat(inch, is(Inches(1)));
	  assertThat(foot, is(Feet(1)));
	  assertThat(yard, is(Yards(1)));
	  assertThat(mile, is(UsMiles(1)));
	  assertThat(nauticalMile, is(NauticalMiles(1)));
	  assertThat(astronomicalUnit, is(AstronomicalUnits(1)));
	  assertThat(lightYear, is(LightYears(1)));
  }
  
  @Test
  public void testProvideImplicitConversionFromDouble() {
	  double d = 18;
	  assertThat(nm(d), is(Nanometers(d)));
	  assertThat(nanometers(d), is(Nanometers(d)));
	  assertThat(nanometres(d), is(Nanometers(d)));
	  assertThat(µm(d), is(Microns(d)));
	  assertThat(microns(d), is(Microns(d)));
	  assertThat(mm(d), is(Millimeters(d)));
	  assertThat(millimeters(d), is(Millimeters(d)));
	  assertThat(millimetres(d), is(Millimeters(d)));
	  assertThat(cm(d), is(Centimeters(d)));
	  assertThat(centimeter(d), is(Centimeters(d)));
	  assertThat(centimetre(d), is(Centimeters(d)));
	  assertThat(dm(d), is(Decimeters(d)));
	  assertThat(meters(d), is(Meters(d)));
	  assertThat(metres(d), is(Meters(d)));
	  assertThat(dam(d), is(Decameters(d)));
	  assertThat(hm(d), is(Hectometers(d)));
	  assertThat(km(d), is(Kilometers(d)));
	  assertThat(kilometers(d), is(Kilometers(d)));
	  assertThat(kilometres(d), is(Kilometers(d)));
	  assertThat(inches(d), is(Inches(d)));
	  assertThat(ft(d), is(Feet(d)));
	  assertThat(feet(d), is(Feet(d)));
	  assertThat(yd(d), is(Yards(d)));
	  assertThat(yards(d), is(Yards(d)));
	  assertThat(miles(d), is(UsMiles(d)));
	  assertThat(nmi(d), is(NauticalMiles(d)));
	  assertThat(au(d), is(AstronomicalUnits(d)));
	  assertThat(ly(d), is(LightYears(d)));
	  assertThat(lightYears(d), is(LightYears(d)));
  }
  

  @Test
  public void testProvideImplicitConversionFromString() {
	assertThat(toLength("10.33 nm").get(), is(Nanometers(10.33)));
    assertThat(toLength("10.33 µm").get(), is(Microns(10.33)));
    assertThat(toLength("10.33 mm").get(), is(Millimeters(10.33)));
    assertThat(toLength("10.33 cm").get(), is(Centimeters(10.33)));
    assertThat(toLength("10.33 dm").get(), is(Decimeters(10.33)));
    assertThat(toLength("10.33 m").get(), is(Meters(10.33)));
    assertThat(toLength("10.33 dam").get(), is(Decameters(10.33)));
    assertThat(toLength("10.33 hm").get(), is(Hectometers(10.33)));
    assertThat(toLength("10.33 km").get(), is(Kilometers(10.33)));
    assertThat(toLength("10.33 in").get(), is(Inches(10.33)));
    assertThat(toLength("10.33 ft").get(), is(Feet(10.33)));
    assertThat(toLength("10.33 yd").get(), is(Yards(10.33)));
    assertThat(toLength("10.33 mi").get(), is(UsMiles(10.33)));
    assertThat(toLength("10.33 InternationalMiles").get(), is(InternationalMiles(10.33)));
    assertThat(toLength("10.33 nmi").get(), is(NauticalMiles(10.33)));
    assertThat(toLength("10.33 au").get(), is(AstronomicalUnits(10.33)));
    assertThat(toLength("10.33 ly").get(), is(LightYears(10.33)));
//    assertThat(toLength("10.33 zz").get(), is(Inches(10.33)));
//    assertThat(toLength("ZZ m").get(), is(Inches(10.33)));
  }


//    "10.33 zz".toLength.failed.get should be(QuantityStringParseException("Unable to parse Length", "10.33 zz"))
//    "ZZ m".toLength.failed.get should be(QuantityStringParseException("Unable to parse Length", "ZZ m"))

//  it should "provide Numeric support" in {
//    import LengthConversions.LengthNumeric
//
//    val ls = List(Meters(1000), Kilometers(1))
//    ls.sum should be(Meters(2000))

}
