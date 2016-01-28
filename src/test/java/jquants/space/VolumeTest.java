package jquants.space;

import jquants.MetricSystem;

import org.hamcrest.core.Is;
import org.junit.Test;

import static jquants.mass.AreaDensity.*;
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

import static jquants.motion.VolumeFlowRate.*;

public class VolumeTest {

	@Test
	public void testCreateValuesUsingUOMFactories() {
		assertThat(CubicMeters(1).toCubicMeters(), is(1d));
		assertThat(Litres(1).toLitres(), is(1d));
		assertThat(Nanolitres(1).toNanolitres(), is(1d));
		assertThat(Microlitres(1).toMicrolitres(), is(1d));
		assertThat(Millilitres(1).toMillilitres(), is(1d));
		assertThat(Centilitres(1).toCentilitres(), is(1d));
		assertThat(Decilitres(1).toDecilitres(), is(1d));
		assertThat(Hectolitres(1).toHectolitres(), is(1d));

		assertThat(CubicMiles(1).toCubicMiles(), is(1d));
		assertThat(CubicYards(1).toCubicYards(), is(1d));
		assertThat(CubicFeet(1).toCubicFeet(), is(1d));
		assertThat(CubicInches(1).toCubicInches(), is(1d));
		
		assertThat(UsGallons(1).toUsGallons(), is(1d));
		assertThat(UsQuarts(1).toUsQuarts(), is(1d));
		assertThat(UsPints(1).toUsPints(), is(1d));
		assertThat(UsCups(1).toUsCups(), is(1d));

		assertThat(FluidOunces(1).toFluidOunces(), is(1d));
		assertThat(Tablespoons(1).toTablespoons(), is(1d));
		assertThat(Teaspoons(1).toTeaspoons(), is(1d));
    }

	@Test
	public void testCreateValuesFromProperlyFormattedStrings() {
		assertThat(toVolume("10.22 m³").get(), is(CubicMeters(10.22)));
		assertThat(toVolume("10.22 L").get(), is(Litres(10.22)));
		assertThat(toVolume("10.22 nl").get(), is(Nanolitres(10.22)));
		assertThat(toVolume("10.22 µl").get(), is(Microlitres(10.22)));
		assertThat(toVolume("10.22 ml").get(), is(Millilitres(10.22)));
		assertThat(toVolume("10.22 cl").get(), is(Centilitres(10.22)));
		assertThat(toVolume("10.22 dl").get(), is(Decilitres(10.22)));
		assertThat(toVolume("10.22 hl").get(), is(Hectolitres(10.22)));
		assertThat(toVolume("10.22 mi³").get(), is(CubicMiles(10.22)));
		assertThat(toVolume("10.22 yd³").get(), is(CubicYards(10.22)));
		assertThat(toVolume("10.22 ft³").get(), is(CubicFeet(10.22)));
		assertThat(toVolume("10.22 in³").get(), is(CubicInches(10.22)));
		assertThat(toVolume("10.22 gal").get(), is(UsGallons(10.22)));
		assertThat(toVolume("10.22 qt").get(), is(UsQuarts(10.22)));
		assertThat(toVolume("10.22 pt").get(), is(UsPints(10.22)));
		assertThat(toVolume("10.22 c").get(), is(UsCups(10.22)));
		assertThat(toVolume("10.22 oz").get(), is(FluidOunces(10.22)));
		assertThat(toVolume("10.22 tbsp").get(), is(Tablespoons(10.22)));
		assertThat(toVolume("10.22 tsp").get(), is(Teaspoons(10.22)));
//    	Volume("10.22 zz").failed.get should be(QuantityStringParseException("Unable to parse Volume", "10.22 zz"))
//    	Volume("ZZ L").failed.get should be(QuantityStringParseException("Unable to parse Volume", "ZZ L"))
    }

	@Test
	public void testProperöyConvertToAllSupportedUnitsOfMeasure() {
		Volume x = CubicMeters(1);
		
		assertThat(x.toCubicMeters(), is(1d));

		assertThat(x.toLitres(), is(1000d));
		assertThat(x.toNanolitres() - 1000000000000.0d < 1, is(true)); // Some issues with conversion precision
		assertThat(x.toMicrolitres() - 1000000000.0d < 1, is(true)); // Some issues with conversion precision
		assertThat(x.toMillilitres() - 1000000.0d < 1, is(true)); // Some issues with conversion precision
		assertThat(x.toCentilitres() - 100000 < 1, is(true)); // Some issues with conversion precision
		assertThat(x.toDecilitres(), is(10000d));
		assertThat(x.toHectolitres(), is(10d));
		
		assertThat(x.toCubicMiles(), is(1 / Math.pow(UsMiles.multiplier, 3)));
		assertThat(x.toCubicYards(), is(1 / Math.pow(Yards.multiplier, 3)));
		assertThat(x.toCubicFeet(), is(1 / Math.pow(Feet.multiplier, 3)));
		assertThat(x.toCubicInches(), is(1 / Math.pow(Inches.multiplier, 3)));

		double litresPerUsGallon = 3.785411784;
		assertThat(x.toUsGallons(), is(1000d / litresPerUsGallon));
		assertThat(x.toUsQuarts(), is(4000d / litresPerUsGallon));
		assertThat(x.toUsPints(), is(8000d / litresPerUsGallon));
		assertThat(x.toUsCups(), is(16000d / litresPerUsGallon));
		assertThat(x.toFluidOunces(), is(128000d / litresPerUsGallon));
		assertThat(x.toTablespoons(), is((128000d / litresPerUsGallon) * 2d));
		assertThat(x.toTeaspoons(), is((128000d / litresPerUsGallon) * 6d));
		
		double litresPerUsDryGallon = 4.4048837;
		assertThat(x.toUsDryGallons(), is(1000d / litresPerUsDryGallon));
		assertThat(x.toUsDryQuarts(), is(4000d / litresPerUsDryGallon));
		assertThat(x.toUsDryPints(), is(8000d / litresPerUsDryGallon));
		assertThat(x.toUsDryCups(), is(16000d / litresPerUsDryGallon));
		
		double litresPerImperialGallon = 4.54609;
		assertThat(x.toImperialGallons(), is(1000d / litresPerImperialGallon));
		assertThat(x.toImperialQuarts(), is(4000d / litresPerImperialGallon));
		assertThat(x.toImperialPints(), is(8000d / litresPerImperialGallon));
		assertThat(x.toImperialCups(), is(16000d / litresPerImperialGallon));
	}

  @Test
  public void testProperlyDormattedStringsForAllSupportedUnitsOfMeasure() {
    assertThat(CubicMeters(1).toString(), is("1.0 m³"));
    assertThat(Litres(1).toString(), is("1.0 L"));
    assertThat(Nanolitres(1).toString(), is("1.0 nl"));
    assertThat(Microlitres(1).toString(), is("1.0 µl"));
    assertThat(Millilitres(1).toString(), is("1.0 ml"));
    assertThat(Centilitres(1).toString(), is("1.0 cl"));
    assertThat(Decilitres(1).toString(), is("1.0 dl"));
    assertThat(Hectolitres(1).toString(), is("1.0 hl"));

    assertThat(CubicMiles(1).toString(), is("1.0 mi³"));
    assertThat(CubicYards(1).toString(), is("1.0 yd³"));
    assertThat(CubicFeet(1).toString(), is("1.0 ft³"));
    assertThat(CubicInches(1).toString(), is("1.0 in³"));

    assertThat(UsGallons(1).toString(), is("1.0 gal"));
    assertThat(UsQuarts(1).toString(), is("1.0 qt"));
    assertThat(UsPints(1).toString(), is("1.0 pt"));
    assertThat(UsCups(1).toString(), is("1.0 c"));
  }

  @Test
//  public void testReturnMassWhenMultipliedByDensity() {
//    assertThat(CubicMeters(1).multiply(KilogramsPerCubicMeter(10)), is(Kilograms(10)));
//  }
//
//  public void testReturnEnergyWhenMultipliedByEnergyDensity() {
//	assertThat(CubicMeters(1).multiply(JoulesPerCubicMeter(10)), is(Joules(10)));
//  }

  public void testReturnLengthWhenDividedByArea() {
	assertThat(CubicMeters(1).div(SquareMeters(1)), is(Meters(1)));
  }

  public void testReturnReturnAreaWhenDividedByLength() {
	assertThat(CubicMeters(1).div(Meters(1)), is(SquareMeters(1)));
  }

  public void testReturnVolumeFlowRateWhenDividedByTime() {
    assertThat(CubicMeters(1).div(Seconds(1)), is(CubicMetersPerSecond(1)));
  }
//
  public void testReturnTimeWhenDividedByVolumeFlowRate() {
    assertThat(CubicMeters(1).div(CubicMetersPerSecond(1)), is(Seconds(1)));
  }

	@Test
	public void testProvideAliasesForSingleUnitValues() {
    	assertThat(cubicMeter, is(CubicMeters(1)));
    	assertThat(litre, is(Litres(1)));
    	assertThat(nanoliter, is(Nanolitres(1)));
    	assertThat(nanolitre, is(Nanolitres(1)));
    	assertThat(microliter, is(Microlitres(1)));
    	assertThat(microlitre, is(Microlitres(1)));
    	assertThat(milliliter, is(Millilitres(1)));
    	assertThat(millilitre, is(Millilitres(1)));
    	assertThat(centiliter, is(Centilitres(1)));
    	assertThat(centilitre, is(Centilitres(1)));
    	assertThat(deciliter, is(Decilitres(1)));
    	assertThat(decilitre, is(Decilitres(1)));
    	assertThat(hectoliter, is(Hectolitres(1)));
    	assertThat(hectolitre, is(Hectolitres(1)));

    	assertThat(cubicMile, is(CubicMiles(1)));
    	assertThat(cubicYard, is(CubicYards(1)));
    	assertThat(cubicFoot, is(CubicFeet(1)));
    	assertThat(cubicInch, is(CubicInches(1)));

    	assertThat(gallon, is(UsGallons(1)));
    	assertThat(quart, is(UsQuarts(1)));
    	assertThat(pint, is(UsPints(1)));
    	assertThat(cup, is(UsCups(1)));
    	assertThat(fluidOunce, is(FluidOunces(1)));
    	assertThat(tableSpoon, is(Tablespoons(1)));
    	assertThat(teaSpoon, is(Teaspoons(1)));
    }

	@Test
	public void testProvideImplicitConversionFromDouble() {
    	double d = 10;
    	
    	assertThat(cubicMeters(d), is(CubicMeters(d)));
    	assertThat(cubicMetres(d), is(CubicMeters(d)));
    	assertThat(litres(d), is(Litres(d)));
    	assertThat(nanoliters(d), is(Nanolitres(d)));
    	assertThat(nanolitres(d), is(Nanolitres(d)));
    	assertThat(microliters(d), is(Microlitres(d)));
    	assertThat(microlitres(d), is(Microlitres(d)));
    	assertThat(milliliters(d), is(Millilitres(d)));
    	assertThat(millilitres(d), is(Millilitres(d)));
    	assertThat(centiliters(d), is(Centilitres(d)));
    	assertThat(centilitres(d), is(Centilitres(d)));
    	assertThat(deciliters(d), is(Decilitres(d)));
    	assertThat(decilitres(d), is(Decilitres(d)));
    	assertThat(hectoliters(d), is(Hectolitres(d)));
    	assertThat(hectolitres(d), is(Hectolitres(d)));

    	assertThat(cubicMiles(d), is(CubicMiles(d)));
    	assertThat(cubicYards(d), is(CubicYards(d)));
    	assertThat(cubicFeet(d), is(CubicFeet(d)));
    	assertThat(cubicInches(d), is(CubicInches(d)));
    	
    	assertThat(gallons(d), is(UsGallons(d)));
    	assertThat(quarts(d), is(UsQuarts(d)));
    	assertThat(pints(d), is(UsPints(d)));
    	assertThat(cups(d), is(UsCups(d)));
    	assertThat(fluidOunces(d), is(FluidOunces(d)));
    	assertThat(tableSpoons(d), is(Tablespoons(d)));
    	assertThat(teaSpoons(d), is(Teaspoons(d)));
    }

//  it should "provide Numeric support" in {
//    import VolumeConversions.VolumeNumeric
//
//    val vs = List(CubicMeters(100), CubicMeters(1))
//    vs.sum should be(CubicMeters(101))
//  }

}