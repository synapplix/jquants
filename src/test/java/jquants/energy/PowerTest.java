package jquants.energy;

import jquants.MetricSystem;

import org.hamcrest.core.Is;
import org.junit.Test;

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

public class PowerTest {

	@Test
	public void testCreateValuesUsingUOMFactories() {
		assertThat(Watts(1).toWatts(), is(1d));
		assertThat(Milliwatts(1).toMilliwatts(), is(1d));
		assertThat(Kilowatts(1).toKilowatts(), is(1d));
		assertThat(Megawatts(1).toMegawatts(), is(1d));
		assertThat(Gigawatts(1).toGigawatts(), is(1d));
		assertThat(BtusPerHour(1).toBtusPerHour(), is(1d));
		  }
	
	@Test
	public void testCreateValuesFromProperlyFormattedStrings() {
		assertThat(toPower("10.22 mW").get(), is(Milliwatts(10.22)));
		assertThat(toPower("10.22 W").get(), is(Watts(10.22)));
		assertThat(toPower("10.22 kW").get(), is(Kilowatts(10.22)));
		assertThat(toPower("10.22 MW").get(), is(Megawatts(10.22)));
		assertThat(toPower("10.22 GW").get(), is(Gigawatts(10.22)));
		assertThat(toPower("10.22 Btu/hr").get(), is(BtusPerHour(10.22)));
//		assertThat(toPower("10.22 zz").failed.get(), is(QuantityStringParseException("Unable to parse Power", "10.22 zz")));
//		assertThat(toPower("ZZ W").failed.get(), is(QuantityStringParseException("Unable to parse Power", "ZZ W")));
		  }
	
	@Test
	public void testProperlyConvertToAllSupportedUnitsOfMeasure() {
		Power x = Watts(1);

		assertThat(x.toWatts(), is(1d));
		assertThat(x.toMilliwatts(), is(1 / MetricSystem.Milli));
		assertThat(x.toKilowatts(), is(1 / MetricSystem.Kilo));
		assertThat(x.toMegawatts(), is(1 / MetricSystem.Mega));
		assertThat(x.toGigawatts(), is(1 / MetricSystem.Giga));
		assertThat(x.toBtusPerHour(), is(1 / BtusPerHour.multiplier));
		  }
	
	@Test
	public void testReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure() {
		assertThat(Watts(1).toString(Watts), is("1.0 W"));
		assertThat(Milliwatts(1).toString(Milliwatts), is("1.0 mW"));
		assertThat(Kilowatts(1).toString(Kilowatts), is("1.0 kW"));
		assertThat(Megawatts(1).toString(Megawatts), is("1.0 MW"));
		assertThat(Gigawatts(1).toString(Gigawatts), is("1.0 GW"));
		assertThat(BtusPerHour(1).toString(BtusPerHour), is("1.0 Btu/hr"));
		  }
	
	@Test
	public void testReturnEnergyWhenMultipliedByTime() {
		assertThat(Watts(1).multiply(Hours(1)), is(WattHours(1)));
		  }
	
	@Test
	public void testProvideAliasesForSingleUnitValues() {
		assertThat(milliwatt, is(Milliwatts(1)));
		assertThat(mW, is(Milliwatts(1)));
		assertThat(watt, is(Watts(1)));
		assertThat(W, is(Watts(1)));
		assertThat(kilowatt, is(Kilowatts(1)));
		assertThat(kW, is(Kilowatts(1)));
		assertThat(megawatt, is(Megawatts(1)));
		assertThat(MW, is(Megawatts(1)));
		assertThat(gigawatt, is(Gigawatts(1)));
		assertThat(GW, is(Gigawatts(1)));
		  }
	
	@Test
	public void testProvideImplicitConversionFromDouble() {
		double d = 10d;
		assertThat(mW(d), is(Milliwatts(d)));
		assertThat(W(d), is(Watts(d)));
		assertThat(kW(d), is(Kilowatts(d)));
		assertThat(MW(d), is(Megawatts(d)));
		assertThat(GW(d), is(Gigawatts(d)));
		assertThat(milliwatts(d), is(Milliwatts(d)));
		assertThat(watts(d), is(Watts(d)));
		assertThat(kilowatts(d), is(Kilowatts(d)));
		assertThat(megawatts(d), is(Megawatts(d)));
		assertThat(gigawatts(d), is(Gigawatts(d)));
		assertThat(BTUph(d), is(BtusPerHour(d)));
		  }
	
	@Test
	public void testProvideImplicitConversionsFromString() {
		assertThat(toPower("10.22 mW").get(), is(Milliwatts(10.22)));
		assertThat(toPower("10.22 W").get(), is(Watts(10.22)));
		assertThat(toPower("10.22 kW").get(), is(Kilowatts(10.22)));
		assertThat(toPower("10.22 MW").get(), is(Megawatts(10.22)));
		assertThat(toPower("10.22 GW").get(), is(Gigawatts(10.22)));
		assertThat(toPower("10.22 Btu/hr").get(), is(BtusPerHour(10.22)));
//		assertThat(toPower("10.22 zz").failed.get(), is(QuantityStringParseException("Unable to parse Power", "10.22 zz"))
//		assertThat(toPower("ZZ W").failed.get(), is(QuantityStringParseException("Unable to parse Power", "ZZ W"))
		  }
	
//	@Test
//	public void testProvideNumericSupportIn() {
//		    val ps = List(Watts(1000), Kilowatts(10), Megawatts(.1))
//		    ps.sum should be(Kilowatts(111))
//		  }

}


