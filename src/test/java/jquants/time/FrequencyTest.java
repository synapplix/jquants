package jquants.time;

import static jquants.time.Frequency.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static jquants.time.*;
import static jquants.Dimensionless.*;

import org.junit.Test;

import jquants.Dimensionless;
import jquants.MetricSystem;

public class FrequencyTest {

	/* testCreateValuesUsingUOMFactories
	 * testCreateValuesFromProperlyFormattedStrings()
	 * testProperlyConvertToAllSupportedUnitsOfMeasure()
	 * testProvideAliasesForSingleUnitValues()
	 * testProvideImplicitConversionFromDouble()
	 * testProvideImplicitConversionsFromArbitraryNumericTypes()
	 * testProvideImplicitConversionsFromSdtring()
	 * testReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure()*/
	
//	 behavior of "Frequency and its Units of Measure"
//
//	  it should "create values using UOM factories" in {
//	    Hertz(1).toHertz should be(1)
//	    Kilohertz(1).toKilohertz should be(1)
//	    Megahertz(1).toMegahertz should be(1)
//	    Gigahertz(1).toGigahertz should be(1)
//	    Terahertz(1).toTerahertz should be(1)
//	    RevolutionsPerMinute(1).toRevolutionsPerMinute should be(1)
//	  }

	@Test
	public void testCreateValuesUsingUOMFactories(){
		
		assertThat(Hertz(1).toHertz(), is(1d));
		assertThat(Kilohertz(1).toKilohertz(), is(1d));
		assertThat(Megahertz(1).toMegahertz(), is(1d));
		assertThat(Gigahertz(1).toGigahertz(), is(1d));
		assertThat(Terahertz(1).toTerahertz(), is(1d));
		assertThat(RevolutionsPerMinute(1).toRevolutionsPerMinute(), is(1d));
	}
	
//	  it should "create values from properly formatted Strings" in {
//	    Frequency("10.22 Hz").get should be(Hertz(10.22))
//	    Frequency("10.22 kHz").get should be(Kilohertz(10.22))
//	    Frequency("10.22 MHz").get should be(Megahertz(10.22))
//	    Frequency("10.22 GHz").get should be(Gigahertz(10.22))
//	    Frequency("10.22 THz").get should be(Terahertz(10.22))
//	    Frequency("10.22 rpm").get should be(RevolutionsPerMinute(10.22))
//	    Frequency("10.45 zz").failed.get should be(QuantityParseException("Unable to parse Frequency", "10.45 zz"))
//	    Frequency("zz Hz").failed.get should be(QuantityParseException("Unable to parse Frequency", "zz Hz"))
//	  }
	
	@Test
	public void testCreateValuesFromProperlyFormattedStrings() {
	
	assertThat(toFrequency("10.22 Hz").get(), is(Hertz(10.22)));
	assertThat(toFrequency("10.22 kHz").get(), is(Kilohertz(10.22)));
	assertThat(toFrequency("10.22 MHz").get(), is(Megahertz(10.22)));
	assertThat(toFrequency("10.22 GHz").get(), is(Gigahertz(10.22)));
	assertThat(toFrequency("10.22 THz").get(), is(Terahertz(10.22)));
	assertThat(toFrequency("10.22 rpm").get(), is(RevolutionsPerMinute(10.22)));
//	assertThat(toFrequency("10.22 zz").get(), is(Hertz(10.22)));
//	assertThat(toFrequency("zz rpm").get(), is(Hertz(10.22)));
	
	}
	
//	  it should "properly convert to all supported Units of Measure" in {
//	    val x = Hertz(1)
//	    x.toHertz should be(1)
//	    x.toKilohertz should be(1 / MetricSystem.Kilo)
//	    x.toMegahertz should be(1 / MetricSystem.Mega)
//	    x.toTerahertz should be(1 / MetricSystem.Tera)
//	    x.toRevolutionsPerMinute should be(60)
//	  }
//
	@Test
	public void testProvideAliasesForSingleUnitValues() {
	Frequency x = Hertz(1);
	assertThat(x.toHertz(), is(1.0));
	assertThat(x.toKilohertz(), is(1.0/MetricSystem.Kilo));
	assertThat(x.toMegahertz(), is(1.0/MetricSystem.Mega));
	assertThat(x.toGigahertz(), is(1.0/MetricSystem.Giga));
	assertThat(x.toTerahertz(), is(1.0/MetricSystem.Tera));
	assertThat(x.toRevolutionsPerMinute(), is(60.0));
	
	}
	
//	  it should "return properly formatted strings for all supported Units of Measure" in {
//	    Hertz(1).toString(Hertz) should be("1.0 Hz")
//	    Kilohertz(1).toString(Kilohertz) should be("1.0 kHz")
//	    Megahertz(1).toString(Megahertz) should be("1.0 MHz")
//	    Gigahertz(1).toString(Gigahertz) should be("1.0 GHz")
//	    Terahertz(1).toString(Terahertz) should be("1.0 THz")
//	    RevolutionsPerMinute(1).toString(RevolutionsPerMinute) should be("1.0 rpm")
//	  }
//
	@Test
	public void shouldReturnProperlryFormattedStringsForAllSupportedUnitsOfMeasure() {
	
		assertThat(Hertz(1).toString(),is("1.0 Hz"));
		assertThat(Kilohertz(1).toString(),is("1.0 kHz"));
		assertThat(Megahertz(1).toString(),is("1.0 MHz"));
		assertThat(Gigahertz(1).toString(),is("1.0 GHz"));
		assertThat(Terahertz(1).toString(),is("1.0 THz"));
		assertThat(RevolutionsPerMinute(1).toString(),is("1.0 rpm"));
	}
	
//	  it should "return Count when multiplied by Time" in {
//	    Hertz(1) * Seconds(1) should be(Each(1))
//	  }
//
	
	@Test
	public void testShouldReturnCountWhenMultipliedByTime(){
		
		assertThat(Hertz(1).multiply(Time.Seconds(1.0)), is(Each(1)));
	}
	
	
//	  behavior of "FrequencyConversions"
//
//	  it should "provide implicit conversion from Double" in {
//	    import FrequencyConversions._
//
//	    val d = 10d
//	    d.hertz should be(Hertz(d))
//	    d.kilohertz should be(Kilohertz(d))
//	    d.megahertz should be(Megahertz(d))
//	    d.gigahertz should be(Gigahertz(d))
//	    d.terahertz should be(Terahertz(d))
//	    d.rpm should be(RevolutionsPerMinute(d))
//	  }
//	
	
	@Test
	public void testShouldProvideImplicitConversionFromDouble() {
		
	  double d = 10;
		assertThat(hertz(d), is(Hertz(d)));
		assertThat(kilohertz(d), is(Kilohertz(d)));
		assertThat(megahertz(d), is(Megahertz(d)));
		assertThat(gigahertz(d), is(Gigahertz(d)));
		assertThat(terahertz(d), is(Terahertz(d)));
		assertThat(rpm(d), is(RevolutionsPerMinute(d)));
	}
}