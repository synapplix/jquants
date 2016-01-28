package jquants.energy;

import jquants.MetricSystem;

import org.hamcrest.core.Is;
import org.junit.Test;

import static jquants.energy.SpecificEnergy.*;
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

public class SpecificEnergyTest {

	@Test
	public void testCreateValuesUsingUOMFactories() {
		assertThat(Grays(1).toGrays(), is(1d));
	}
	
	@Test
	public void testCreateValuesFromProperlyFormattedStrings() {
		assertThat(toSpecificEnergy("10.22 Gy").get(), is(Grays(10.22)));
//		assertThat(toSpecificEnergy("10.22 zz").failed.get(), is(QuantityStringParseException("Unable to parse SpecificEnergy", "10.22 zz")));
//		assertThat(toSpecificEnergy("ZZ Gy").failed.get(), is(QuantityStringParseException("Unable to parse SpecificEnergy", "ZZ Gy")));
		  }
	
	@Test
	public void testProperlyConvertToAllSupportedUnitsOfMeasure() {
		SpecificEnergy x = Grays(1);

		assertThat(x.toGrays(), is(1d));
	}
	
	@Test
	public void testReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure() {
		assertThat(Grays(1).toString(), is("1.0 Gy"));
		  }
	
	@Test
	public void testReturnEnergyWhenMultipliedByMass() {
		assertThat(Grays(1).multiply(Kilograms(10)), is(Joules(10)));
		  }
	
	@Test
	public void testProvideAliasesForSingleUnitValues() {
		assertThat(gray, is(Grays(1)));
		  }
	
	@Test
	public void testProvideImplicitConversionFromDouble() {
		double d = 10d;
		assertThat(grays(d), is(Grays(d)));
		  }
	
//	@Test
//	public void test() {
//		    val ses = List(Grays(100), Grays(10))
//		    ses.sum should be(Grays(110))
//		  }

}
