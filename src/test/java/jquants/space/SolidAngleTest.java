package jquants.space;

import jquants.MetricSystem;

import org.hamcrest.core.Is;
import org.junit.Test;

import static jquants.space.SolidAngle.*;
import static jquants.space.Angle.*;
import static jquants.space.Length.*;
import static jquants.space.Area.*;
import static jquants.space.Volume.*;
import static jquants.motion.Velocity.*;
import static jquants.time.Time.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class SolidAngleTest {

	@Test
	public void testCreateValuesUsingUOMFactories() {
		assertThat(SquaredRadians(1).toSquaredRadians(), is(1d));
		assertThat(SquaredRadians(1).toSteradians(), is(1d));
	}

	@Test
	public void testCreateValuesFromProperlyFormattedStrings() {
		assertThat(toSolidAngle("10.22 sr").get(), is(SquaredRadians(10.22)));
//		SolidAngle("10.22 zz").failed.get should be(QuantityStringParseException("Unable to parse SolidAngle", "10.22 zz"))
//		SolidAngle("ZZ sr").failed.get should be(QuantityStringParseException("Unable to parse SolidAngle", "ZZ sr"))
    }

	@Test
	public void testProperlyConvertToAllSupportedUnitsOfMeasure() {
		SolidAngle x = SquaredRadians(1);
		assertThat(x.toSquaredRadians(), is(1d));
    }

	@Test
	public void testReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure(){
		assertThat(SquaredRadians(1).toString(), is("1.0 sr"));
	}

//	@Test
//	public void testReturnLumiousFluxWhenMultipliedByLuminousIntensity() {
//		assertThat(SquaredRadians(1).multiply(Candelas(1)), is(Lumens(1)));
//	}

	@Test
	public void testProvideAliasesForSingleUnitValues() {
		assertThat(squaredRadian, is(SquaredRadians(1)));
		assertThat(steradian, is(SquaredRadians(1)));
	}
		  
		  
	@Test
	public void testProvideImplicitConversionFromDouble() {
		double d = 10d;
		assertThat(squaredRadians(d), is(SquaredRadians(d)));
		assertThat(steradians(d), is(SquaredRadians(d)));
	}

		  
//	it should "provide Numeric support" in {
//		import SolidAngleConversions.SolidAngleNumeric
//
//		val sas = List(SquaredRadians(100), SquaredRadians(1))
//		sas.sum should be(SquaredRadians(101))
//	}
}
