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

public class AreaTest {

	@Test
	public void testCreateValuesUsingUOMFactories() {
		assertThat(SquareMeters(1).toSquareMeters(), is(1d));
		assertThat(SquareCentimeters(1).toSquareCentimeters(), is(1d));
		assertThat(SquareKilometers(1).toSquareKilometers(), is(1d));
		assertThat(SquareUsMiles(1).toSquareUsMiles(), is(1d));
		assertThat(SquareYards(1).toSquareYards(), is(1d));
		assertThat(SquareFeet(1).toSquareFeet(), is(1d));
		assertThat(SquareInches(1).toSquareInches(), is(1d));
		assertThat(Hectares(1).toHectares(), is(1d));
		assertThat(Acres(1).toAcres(), is(1d));
		assertThat(Barnes(1).toBarnes(), is(1d));
	}
	
	@Test
	public void testCreateValuesFromProperlyFormattedStrings() {
		assertThat(toArea("10.22 m²").get(), is(SquareMeters(10.22)));
		assertThat(toArea("10.22 cm²").get(), is(SquareCentimeters(10.22)));
		assertThat(toArea("10.22 km²").get(), is(SquareKilometers(10.22)));
		assertThat(toArea("10.22 mi²").get(), is(SquareUsMiles(10.22)));
		assertThat(toArea("10.22 yd²").get(), is(SquareYards(10.22)));
		assertThat(toArea("10.22 ft²").get(), is(SquareFeet(10.22)));
		assertThat(toArea("10.22 in²").get(), is(SquareInches(10.22)));
		assertThat(toArea("10.22 ha").get(), is(Hectares(10.22)));
		assertThat(toArea("10.22 acre").get(), is(Acres(10.22)));
		assertThat(toArea("10.22 b").get(), is(Barnes(10.22)));
//		assertThat(toArea("10.22 zz").get(), is(SquareMeters(10.22)));
//		assertThat(toArea("ZZ m²").get(), is(SquareMeters(10.22)));

//  dimension("ZZ m²").failed.get should be(QuantityStringParseException("Unable to parse dimension", "ZZ m²"))
//  dimension("10.22 zz").failed.get should be(QuantityStringParseException("Unable to parse dimension", "10.22 zz"))
	}
	
	@Test
	public void testProperlyConvertToAllSupportedUnitsOfMeasure() {
		Area x = SquareMeters(1);
		assertThat(x.toSquareMeters(), is(1d));
		assertThat(x.toSquareCentimeters(), is(1 / SquareCentimeters.multiplier));
		assertThat(x.toSquareKilometers(), is(1 / (MetricSystem.Kilo * MetricSystem.Kilo)));
		
		assertThat(x.toSquareUsMiles(), is(1 / SquareUsMiles.multiplier));
		assertThat(x.toSquareYards(), is(1 / SquareYards.multiplier));
		assertThat(x.toSquareFeet(), is(1 / SquareFeet.multiplier));
		assertThat(x.toHectares(), is(1 / Hectares.multiplier));
		assertThat(x.toAcres(), is(1 / Acres.multiplier));
		assertThat(x.toBarnes(), is(1 / Barnes.multiplier));
	}
	
	@Test
	public void testReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure() {
		assertThat(SquareMeters(1).toString(), is("1.0 m²"));
		assertThat(SquareCentimeters(1).toString(), is("1.0 cm²"));
		assertThat(SquareKilometers(1).toString(), is("1.0 km²"));
		assertThat(SquareUsMiles(1).toString(), is("1.0 mi²"));
		assertThat(SquareYards(1).toString(), is("1.0 yd²"));
		assertThat(SquareFeet(1).toString(), is("1.0 ft²"));
		assertThat(SquareInches(1).toString(), is("1.0 in²"));
		assertThat(Hectares(1).toString(), is("1.0 ha"));
		assertThat(Acres(1).toString(), is("1.0 acre"));
		assertThat(Barnes(1).toString(), is("1.0 b"));
	}
	
	@Test
	public void testReturnVolumeWhenMultipliedByLength() {
		assertThat(SquareMeters(1).multiply(Meters(1)), is(CubicMeters(1)));
	}
	
	@Test
	public void testReturnMassWhenMultipliedByAreaDensity() {
		assertThat(SquareMeters(1).multiply(KilogramsPerSquareMeter(1)), is(Kilograms(1)));
	}
	
//	@Test
//	public void testReturnForceWhenMultipliedByPressure() {
//		assertThat(SquareMeters(1).multiply(Pascals(1)), is(Newtons)); //Force and Pressure are not yet implemented
//	}
	
//	@Test
//	public void testReturnLumensWhenMultipliedByIlluminance() {
//		assertThat(SquareMeters(1).multiply(Lux(1)), is(Lumens(1)));
//	}
	
//	@Test
//	public void testReturnCandelasWhenMultipliedByLuminance() {
//		assertThat(SquareMeters(1).multiply(CandelasPerSquareMeter(1)), is(Candelas));
//	}
	
	@Test
	public void testReturnLengthWhenDividedByLength() {
		assertThat(SquareMeters(1).div(Meters(1)), is(Meters(1)));
	}

	@Test
	public void testProvideAliasesForSingleUnitValues() {
		assertThat(squareMeter, is(SquareMeters(1)));
		assertThat(squareCentimeter, is(SquareCentimeters(1)));
		assertThat(squareKilometer, is(SquareKilometers(1)));
		assertThat(squareMile, is(SquareUsMiles(1)));
		assertThat(squareYard, is(SquareYards(1)));
		assertThat(squareFoot, is(SquareFeet(1)));
		assertThat(squareInch, is(SquareInches(1)));
		assertThat(hectare, is(Hectares(1)));
		assertThat(acre, is(Acres(1)));
		assertThat(barne, is(Barnes(1)));
	}
	
	@Test
	public void testProvideImplicitConversionFromDouble() {
		double d = 10.22;
		assertThat(squareMeters(d), is(SquareMeters(d)));
		assertThat(squareCentimeters(d), is(SquareCentimeters(d)));
		assertThat(squareKilometers(d), is(SquareKilometers(d)));
		assertThat(squareMiles(d), is(SquareUsMiles(d)));
		assertThat(squareYards(d), is(SquareYards(d)));
		assertThat(squareFeet(d), is(SquareFeet(d)));
		assertThat(squareInches(d), is(SquareInches(d)));
		assertThat(hectares(d), is(Hectares(d)));
		assertThat(acres(d), is(Acres(d)));
		assertThat(barnes(d), is(Barnes(d)));
	}

//  it should "provide Numeric support" in {
//  import AreaConversions.AreaNumeric
//
//  val as = List(SquareMeters(100), SquareMeters(10))
//  as.sum should be(SquareMeters(110))
}



