package jquants.energy;

import jquants.MetricSystem;

import org.hamcrest.core.Is;
import org.junit.Test;

import static jquants.energy.PowerRamp.*;
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

public class PowerRampTest {

	@Test
	public void testCreateValuesUsingUOMFactories() {
	    assertThat(WattsPerHour(1).toWattsPerHour(), is(1d));
	    assertThat(WattsPerMinute(1).toWattsPerMinute(), is(1d));
	    assertThat(KilowattsPerHour(1).toKilowattsPerHour(), is(1d));
	    assertThat(KilowattsPerMinute(1).toKilowattsPerMinute(), is(1d));
	    assertThat(MegawattsPerHour(1).toMegawattsPerHour(), is(1d));
	    assertThat(GigawattsPerHour(1).toGigawattsPerHour(), is(1d));
	}
	
	@Test
	public void testCreateValuesFromProperlyFormattedStrings() {
		assertThat(toPowerRamp("10.22 W/h").get(), is(WattsPerHour(10.22)));
		assertThat(toPowerRamp("10.22 W/m").get(), is(WattsPerMinute(10.22)));
		assertThat(toPowerRamp("10.22 kW/h").get(), is(KilowattsPerHour(10.22)));
		assertThat(toPowerRamp("10.22 kW/m").get(), is(KilowattsPerMinute(10.22)));
		assertThat(toPowerRamp("10.22 MW/h").get(), is(MegawattsPerHour(10.22)));
		assertThat(toPowerRamp("10.22 GW/h").get(), is(GigawattsPerHour(10.22)));
//		assertThat(toPowerRamp("10.22 zz").failed.get(), is(QuantityStringParseException("Unable to parse PowerRamp", "10.22 zz")));
//		assertThat(toPowerRamp("ZZ W/h").failed.get(), is(QuantityStringParseException("Unable to parse PowerRamp", "ZZ W/h")));
	}
	
	@Test
	public void testProperlyConvertToAllSupportedUnitsOfMeasure() {
		    PowerRamp x = WattsPerHour(1);

		assertThat(x.toWattsPerHour(), is(1d));
		assertThat(x.toWattsPerMinute(), is(60d));
		assertThat(x.toKilowattsPerHour(), is(1 / MetricSystem.Kilo));
		assertThat(x.toKilowattsPerMinute(), is(60 / MetricSystem.Kilo));
		assertThat(x.toMegawattsPerHour(), is(1 / MetricSystem.Mega));
		assertThat(x.toGigawattsPerHour(), is(1 / MetricSystem.Giga));

//		assertThat(WattsPerHour(1), is(WattsPerMinute(60)));
		  }
	
	@Test
	public void testReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure() {
		assertThat(WattsPerHour(1).toString(), is("1.0 W/h"));
		assertThat(WattsPerMinute(1).toString(), is("1.0 W/m"));
		assertThat(KilowattsPerHour(1).toString(), is("1.0 kW/h"));
		assertThat(KilowattsPerMinute(1).toString(), is("1.0 kW/m"));
		assertThat(MegawattsPerHour(1).toString(), is("1.0 MW/h"));
		assertThat(GigawattsPerHour(1).toString(), is("1.0 GW/h"));
		  }
	
	@Test
	public void testReturnPowerWhenMultipliedByTime() {
		assertThat(WattsPerHour(1).multiply(Hours(1)), is(Watts(1)));
		  }
	
	@Test
	public void testProvideAliasesForSingleUnitValues() {
		assertThat(wattPerHour, is(WattsPerHour(1)));
		assertThat(Wph, is(WattsPerHour(1)));
		assertThat(wattPerMinute, is(WattsPerMinute(1)));
		assertThat(Wpm, is(WattsPerMinute(1)));
		assertThat(kilowattPerHour, is(KilowattsPerHour(1)));
		assertThat(kWph, is(KilowattsPerHour(1)));
		assertThat(kilowattPerMinute, is(KilowattsPerMinute(1)));
		assertThat(kWpm, is(KilowattsPerMinute(1)));
		assertThat(megawattPerHour, is(MegawattsPerHour(1)));
		assertThat(MWph, is(MegawattsPerHour(1)));
		assertThat(gigawattPerHour, is(GigawattsPerHour(1)));
		assertThat(GWph, is(GigawattsPerHour(1)));
		  }
	
	@Test
	public void testProvideImplicitConversionFromDouble() {
		double d = 10d;
		assertThat(Wph(d), is(WattsPerHour(d)));
		assertThat(Wpm(d), is(WattsPerMinute(d)));
		assertThat(kWph(d), is(KilowattsPerHour(d)));
		assertThat(kWpm(d), is(KilowattsPerMinute(d)));
		assertThat(MWph(d), is(MegawattsPerHour(d)));
		assertThat(GWph(d), is(GigawattsPerHour(d)));
	}
	
	@Test
	public void testProvideImplicitConversionsFromString() {
		assertThat(toPowerRamp("10.22 W/h").get(), is(WattsPerHour(10.22)));
		assertThat(toPowerRamp("10.22 W/m").get(), is(WattsPerMinute(10.22)));
		assertThat(toPowerRamp("10.22 kW/h").get(), is(KilowattsPerHour(10.22)));
		assertThat(toPowerRamp("10.22 kW/m").get(), is(KilowattsPerMinute(10.22)));
		assertThat(toPowerRamp("10.22 MW/h").get(), is(MegawattsPerHour(10.22)));
		assertThat(toPowerRamp("10.22 GW/h").get(), is(GigawattsPerHour(10.22)));
//		assertThat(toPowerRamp("10.22 zz").failed.get(), is(QuantityStringParseException("Unable to parse PowerRamp", "10.22 zz")));
//		assertThat(toPowerRamp("ZZ W/h").failed.get(), is(QuantityStringParseException("Unable to parse PowerRamp", "ZZ W/h")));
	}
	
//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
//	  it should "provide Numeric support" in {
//		    import PowerRampConversions.PowerRampNumeric
//
//		    val prs = List(WattsPerHour(100), KilowattsPerHour(1))
//		    prs.sum should be(KilowattsPerHour(1.1))
//		  }

}


