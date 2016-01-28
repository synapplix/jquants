package jquants.mass;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static jquants.space.Area.*;
import static jquants.space.Volume.*;
import static jquants.mass.AreaDensity.*;
import static jquants.mass.Mass.*;

import java.util.ArrayList;

import org.junit.Test;

public class AreaDensityTest {

	@Test
	public void testCreateValuesUsingUOMFactories() {
		assertThat(KilogramsPerSquareMeter(1).toKilogramsPerSquareMeter(), is(1d));
	}

	@Test
	public void testCreateValuesFromProperlyFormattedStrings() {
		assertThat(toAreaDensity("10.22 kg/m²").get(), is(KilogramsPerSquareMeter(10.22)));
//		assertThat(toAreaDensity("10.45 zz").get(), is(KilogramsPerSquareMeter(10.22)));
//		assertThat(toAreaDensity("zz kg/m²").get(), is(KilogramsPerSquareMeter(10.22)));
	}
//  it should "create values from properly formatted Strings" in {
//  AreaDensity("10.22 kg/m²").get should be(KilogramsPerSquareMeter(10.22))
//  AreaDensity("10.45 zz").failed.get should be(QuantityStringParseException("Unable to parse AreaDensity", "10.45 zz"))
//  AreaDensity("zz kg/m²").failed.get should be(QuantityStringParseException("Unable to parse AreaDensity", "zz kg/m²"))
//}
	
	@Test
	public void testProperlyConvertToAllSupportedUnitsOfMeasure() {
		AreaDensity x = KilogramsPerSquareMeter(1);
		assertThat(x.toKilogramsPerSquareMeter(), is(1d));
	}
	
	@Test
	public void testReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure() {
		assertThat(KilogramsPerSquareMeter(1).toString(), is("1.0 kg/m²"));
	}
	
	@Test
	public void testReturnMassWhenMultipliedByArea() {
		assertThat(KilogramsPerSquareMeter(1).multiply(SquareMeters(1)), is(Kilograms(1)));
	}
	
	@Test
	public void testProvideAliasesForSingleUnitValues() {
		assertThat(kilogramPerSquareMeter, is(KilogramsPerSquareMeter(1)));
	}
	
	@Test
	public void testProvideImplicitConversionFromDouble() {
		double d = 10.22;
				assertThat(kilogramsPerSquareMeter(d), is(KilogramsPerSquareMeter(d)));
	}
	
	@Test
	public void testProvideNumericSupport() {
		
	}
//  it should "provide Numeric support" in {
//  import AreaDensityConversions.AreaDensityNumeric
//
//  val as = List(KilogramsPerSquareMeter(100), KilogramsPerSquareMeter(10))
//  as.sum should be(KilogramsPerSquareMeter(110))
//}
	
}






