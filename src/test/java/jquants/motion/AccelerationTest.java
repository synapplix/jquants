package jquants.motion;

import jquants.MetricSystem;

import org.hamcrest.core.Is;
import org.junit.Test;

import static jquants.motion.Acceleration.*;
import static jquants.motion.Velocity.*;
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

public class AccelerationTest {

	@Test
	public void testCreateValuesUsingUOMFactories() {
		assertThat(MetersPerSecondSquared(1).toMetersPerSecondSquared(), is(1d));
		assertThat(FeetPerSecondSquared(1).toFeetPerSecondSquared(), is(1d));
		assertThat(UsMilesPerHourSquared(1).toUsMilesPerHourSquared(), is(1d));
		assertThat(EarthGravities(1).toEarthGravities(), is(1d));
	}
	
	@Test
	public void testCreateValuesFromProperlyFormattedStrings() {
		assertThat(toAcceleration("10.22 m/s²").get(), is(MetersPerSecondSquared(10.22)));
		assertThat(toAcceleration("10.22 ft/s²").get(), is(FeetPerSecondSquared(10.22)));
		assertThat(toAcceleration("10.22 mph²").get(), is(UsMilesPerHourSquared(10.22)));
		assertThat(toAcceleration("10.22 g").get(), is(EarthGravities(10.22)));
//		assertThat(dimension("10.22 zz").failed.get(), is(QuantityStringParseException("Unable to parse dimension", "10.22 zz")));
//		assertThat(dimension("zz g").failed.get(), is(QuantityStringParseException("Unable to parse dimension", "zz g")));
		  }
	
	@Test
	public void testProperlyConvertToAllSupportedUnitsOfMeasure() {
		Acceleration x = MetersPerSecondSquared(1);
		assertThat(x.toMetersPerSecondSquared(), is(1d));
		assertThat(x.toFeetPerSecondSquared(), is(Meters(1).toFeet()));
		assertThat(x.toUsMilesPerHourSquared(), is(Meters(1).toUsMiles() / (Seconds(1).toHours() * Seconds(1).toHours())));
//		assertThat(x.toEarthGravities(), is(1d / squants.motion.StandardEarthGravity.toMetersPerSecondSquared));
	}
	
	@Test
	public void testReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure() {
		assertThat(MetersPerSecondSquared(1).toString(), is("1.0 m/s²"));
		assertThat(FeetPerSecondSquared(1).toString(), is("1.0 ft/s²"));
		assertThat(UsMilesPerHourSquared(1).toString(), is("1.0 mph²"));
		// implicit conversion factor needed
		// assertThat(EarthGravities(1).toString(), is("1.0 g"));
	}
	
//	@Test
//	public void testReturnVelocityWhenMultipliedByTime() {
//		assertThat(MetersPerSecondSquared(1).multiply(Seconds(1)), is(MetersPerSecond(1)));
//	}
	
//	@Test
//	public void testReturnForceWhenMultpliedByMass() {
//		assertThat(MetersPerSecondSquared(1).multiply(Kilograms(1)), is(Newtons(1)));
//	}
//	
//	@Test
//	public void testReturnJerkWhenDividedByTime() {
//		assertThat(MetersPerSecondSquared(1).div(Seconds(1)), is(MetersPerSecondCubed(1)));
//	}
//	
//	@Test
//	public void testReturnTimeWhenDividedByJerk() {
//		assertThat(MetersPerSecondSquared(1).div(MetersPerSecondCubed(1)), is(Seconds(1)));
//	}
	
	@Test
	public void testProvideImplicitConversionFromDouble() {
		double d = 10d;
		assertThat(mpss(d), is(MetersPerSecondSquared(d)));
		assertThat(fpss(d), is(FeetPerSecondSquared(d)));
	}
	
//	@Test
//	public void testProvideNumericSupport() {
//		    val as = List(MetersPerSecondSquared(100), MetersPerSecondSquared(10))
//		    as.sum should be(MetersPerSecondSquared(110))
//		  }

}
