package jquants.mass;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static jquants.space.Area.*;
import static jquants.space.Volume.*;
import static jquants.mass.Density.*;
import static jquants.mass.Mass.*;

import java.util.ArrayList;

import org.junit.Test;

public class DensityTest {

	@Test
	public void testCreateValuesUsingUOMFactories() {
		assertThat(KilogramsPerCubicMeter(1).toKilogramsPerCubicMeter(), is(1d));
	}
	
	@Test
	public void testCreateValuesFromProperlyFormattedStrings() {
		assertThat(toDensity("10.22 kg/m³").get(), is(KilogramsPerCubicMeter(10.22)));
//		assertThat(toDensity("10.22 kg/m³").get(), is(KilogramsPerCubicMeter(10.22)));
//		assertThat(toDensity("10.22 kg/m³").get(), is(KilogramsPerCubicMeter(10.22)));
	}
//  it should "create values from properly formatted Strings" in {
//  Density("10.22 kg/m³").get should be(KilogramsPerCubicMeter(10.22))
//  Density("10.45 zz").failed.get should be(QuantityStringParseException("Unable to parse Density", "10.45 zz"))
//  Density("zz kg/m³").failed.get should be(QuantityStringParseException("Unable to parse Density", "zz kg/m³"))
//}
	
	@Test
	public void testProperlyConvertToAllSupportedUnitsOfMeasure() {
		Density x = KilogramsPerCubicMeter(1);
		assertThat(x.toKilogramsPerCubicMeter(), is(1d));
	}
	
	@Test
	public void testReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure() {
		assertThat(KilogramsPerCubicMeter(1).toString(), is("1.0 kg/m³"));
	}
	
	@Test
	public void testReturnMassWhenMultipliedByVolume() {
		assertThat(KilogramsPerCubicMeter(1).multiply(CubicMeters(1)), is(Kilograms(1)));
	}
	
	@Test
	public void testProvideAliasesForSingleUnitValues() {
		assertThat(kilogramPerCubicMeter, is(KilogramsPerCubicMeter(1)));
	}

	@Test
	public void testProvideImplicitConversionFromDouble() {
		double d = 10.22d;
		assertThat(kilogramsPerCubicMeter(d), is(KilogramsPerCubicMeter(d)));
	}
	
//	@Test
//	public void testProvideNumericSupport() {
//		
//		ArrayList as = List(KilogramsPerCubicMeter(100), KilogramsPerCubicMeter(10));
//		assertThat(as.sum(), is(KilogramsPerCubicMeter(110)));
//	}

	
}



