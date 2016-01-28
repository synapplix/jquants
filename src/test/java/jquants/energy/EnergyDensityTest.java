package jquants.energy;

import jquants.MetricSystem;

import org.hamcrest.core.Is;
import org.junit.Test;

import static jquants.energy.EnergyDensity.*;
import static jquants.energy.Power.*;
import static jquants.energy.Energy.*;
import static jquants.space.Length.*;
import static jquants.space.Area.*;
import static jquants.space.Volume.*;
import static jquants.motion.Velocity.*;
import static jquants.time.Time.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class EnergyDensityTest {

	@Test
	public void testCreateValuesUsingUOMFactories() {
	    assertThat(JoulesPerCubicMeter(1).toJoulesPerCubicMeter(), is(1d));
	}
	
	@Test
	public void testCreateValuesFromProperlyFormattedStrings() {
	    assertThat(toEnergyDensity("10.22 J/m³").get(), is(JoulesPerCubicMeter(10.22)));
//	    assertThat(EnergyDensity("10.22 zz").failed.get(), is(QuantityStringParseException("Unable to parse EnergyDensity", "10.22 zz")));
//	    assertThat(EnergyDensity("ZZ J/m³").failed.get(), is(QuantityStringParseException("Unable to parse EnergyDensity", "ZZ j/m³")));
	}
	
	@Test
	public void testProperlyConvertToAllSupportedUnitsOfMeasure() {
	    EnergyDensity x = JoulesPerCubicMeter(1);

	    assertThat(x.toJoulesPerCubicMeter(), is(1d));
	}
	
	@Test
	public void testReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure() {
	    assertThat(JoulesPerCubicMeter(1).toString(), is("1.0 J/m³"));
	}

	@Test
	public void testReturnEnergyWhenMultipliedByVolume() {
	    assertThat(JoulesPerCubicMeter(1).multiply(CubicMeters(10)), is(Joules(10)));
	}
	
	@Test
	public void testProvideAliasesForSingleUnitValues() {
	    assertThat(joulePerCubicMeter, is(JoulesPerCubicMeter(1)));
	}
	
	@Test
	public void testProvideImplicitConversionFromDouble() {
	    double d = 10.22d;
	    assertThat(joulesPerCubicMeter(d), is(JoulesPerCubicMeter(d)));
	}
	
//	@Test
//	public void testProvideNumericSupport() {
//	    val eds = List(JoulesPerCubicMeter(10), JoulesPerCubicMeter(100))
//	    eds.sum should be(JoulesPerCubicMeter(110))
//	}

}
