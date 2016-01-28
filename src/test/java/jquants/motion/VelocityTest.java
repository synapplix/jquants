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


public class VelocityTest {

	@Test
	public void testCreateValuesUsingUOMFactories() {
		assertThat(MetersPerSecond(1).toMetersPerSecond(), is(1d));
		assertThat(FeetPerSecond(1).toFeetPerSecond(), is(1d));
		assertThat(KilometersPerHour(1).toKilometersPerHour(), is(1d));
		assertThat(UsMilesPerHour(1).toUsMilesPerHour(), is(1d));
		assertThat(InternationalMilesPerHour(1).toInternationalMilesPerHour(), is(1d));
	}
	
	@Test
	public void testCreateValuesFromProperlyFormattedStrings() {
		assertThat(toVelocity("10.22 m/s").get(), is(MetersPerSecond(10.22)));
		assertThat(toVelocity("10.22 ft/s").get(), is(FeetPerSecond(10.22)));
		assertThat(toVelocity("10.22 km/s").get(), is(KilometersPerHour(10.22)));
		assertThat(toVelocity("10.22 mph").get(), is(UsMilesPerHour(10.22)));
//		assertThat(toVelocity("10.22 zz").failed.get(), is(QuantityStringParseException("Unable to parse Velocity", "10.22 zz")));
//		assertThat(toVelocity("zz m/s").failed.get(), is(QuantityStringParseException("Unable to parse Velocity", "zz m/s")));
	}
	
	@Test
	public void testProperlyConvertToAllSupportedUnitsOfMeasure() {
		double tolerance = 0.000000000000001;
		Velocity x = MetersPerSecond(1);
		assertThat(x.toMetersPerSecond(), is(1d));
		assertThat(x.toFeetPerSecond(), is(Meters(1).toFeet()));
		assertThat(x.toKilometersPerHour(), is(Meters(1).toKilometers() / Seconds(1).toHours())); // +- tolerance));
		assertThat(x.toUsMilesPerHour(), is(Meters(1).toUsMiles() / Seconds(1).toHours())); // +- tolerance));
		assertThat(x.toInternationalMilesPerHour(), is(Meters(1).toInternationalMiles() / Seconds(1).toHours())); // +- tolerance));
		assertThat(x.toKnots(), is(Meters(1).toNauticalMiles() / Seconds(1).toHours())); // +- tolerance));
	}
	
	@Test
	public void testReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure() {
		assertThat(MetersPerSecond(1).toString(), is("1.0 m/s"));
		assertThat(FeetPerSecond(1).toString(), is("1.0 ft/s"));
		assertThat(KilometersPerHour(1).toString(), is("1.0 km/h"));
		assertThat(UsMilesPerHour(1).toString(), is("1.0 mph"));
		assertThat(InternationalMilesPerHour(1).toString(), is("1.0 imph"));
		assertThat(Knots(1).toString(), is("1.0 kn"));
	}
	
	@Test
	public void testReturnDistanceWhenMultipliedByTime() {
		assertThat(MetersPerSecond(1).multiply(Seconds(1)), is(Meters(1)));
	}
	
//	@Test TODO: Implement Momentum
//	public void testReturnMomentumWhenMultipliedByMass() {
//		assertThat(MetersPerSecond(1).multiply(Kilograms(1)), is(NewtonSeconds(1)));
//	}
	
	@Test
	public void testReturnAccelerationWhenDividedByTime() {
		assertThat(MetersPerSecond(1).div(Seconds(1)), is(MetersPerSecondSquared(1)));
	}
	
//	@Test TODO: Implement Jerk
//	public void testReturnATimeWhenDividedByAnAcceleration() {
//		assertThat(MetersPerSecond(10).div(MetersPerSecondSquared(4)), is(Seconds(2.5)));
//	}
	
	@Test
  public void testReturnADimensionlessRationWhenDividedbyAnAcceleration() {
	  assertThat(MetersPerSecond(10).div(MetersPerSecond(4)), is(2.5));
  }
	
	@Test
	public void testProvideAliasesForSingleUnitValues() {
		assertThat(meterPerSecond, is(MetersPerSecond(1)));
		assertThat(footPerSecond, is(FeetPerSecond(1)));
		assertThat(kilometerPerHour, is(KilometersPerHour(1)));
		assertThat(milePerHour, is(UsMilesPerHour(1)));
		assertThat(knot, is(Knots(1)));
	}
	
	@Test
	public void testProvideImplicitConversionFromDouble() {
		double d = 10d;
		assertThat(fps(d), is(FeetPerSecond(d)));
		assertThat(mps(d), is(MetersPerSecond(d)));
		assertThat(kph(d), is(KilometersPerHour(d)));
		assertThat(mph(d), is(UsMilesPerHour(d)));
		assertThat(knots(d), is(Knots(d)));
	}
	
//	@Test TODO: Numeric Support
//	public void testProvideNumericSupport() {
//		    val vs = List(MetersPerSecond(100), MetersPerSecond(1))
//		    vs.sum should be(MetersPerSecond(101))
//		  }

}


