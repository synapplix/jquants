package jquants.space;

import jquants.MetricSystem;

import org.hamcrest.core.Is;
import org.junit.Test;

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

public class AngleTest {

	@Test
	public void testCreateValuesUsingUOMFactories() {
	    assertThat(Radians(1).toRadians(), is(1d));
	    assertThat(Degrees(1).toDegrees(), is(1d));
	    assertThat(Gradians(1).toGradians(), is(1d));
	    assertThat(Turns(1).toTurns(), is(1d));
	    assertThat(Arcminutes(1).toArcminutes(), is(1d));
	    assertThat(Arcseconds(1).toArcseconds(), is(1d));
	}

	@Test
	public void testCreateValuesFromProperlyFormattedStrings() {
	    assertThat(toAngle("10.22 rad").get(), is(Radians(10.22)));
	    assertThat(toAngle("10.22°").get(), is(Degrees(10.22)));
	    assertThat(toAngle("10.22 grad").get(), is(Gradians(10.22)));
	    assertThat(toAngle("10.22 turns").get(), is(Turns(10.22)));
	    assertThat(toAngle("10.22 amin").get(), is(Arcminutes(10.22)));
	    assertThat(toAngle("10.22 asec").get(), is(Arcseconds(10.22)));
//	    dimension("10.33 zz").failed.get should be(QuantityStringParseException("Unable to parse dimension", "10.33 zz")));
//	    dimension("ZZ rad").failed.get should be(QuantityStringParseException("Unable to parse dimension", "ZZ rad")));
	  }
	
	@Test
	public void testProperlyConvertToAllSupportedUnitsOfMeasure() {
	    Angle x = Radians(1);
	    assertThat(x.toRadians(), is(1d));
	    assertThat(x.toDegrees(), is(180d / Math.PI));
	    assertThat(x.toGradians(), is(200d / Math.PI));
	    assertThat(x.toTurns(), is(1d / (Math.PI * 2d)));
	    assertThat(x.toArcminutes(), is(1d / (Math.PI / 10800d)));
	    assertThat(x.toArcseconds(), is(1d / ((Math.PI / 10800d) / 60d)));
	  }
	
	@Test
	public void testReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure() {
		assertThat(Radians(1).toString(), is("1.0 rad"));
		assertThat(Degrees(1).toString(), is("1.0 °"));
		assertThat(Gradians(1).toString(), is("1.0 grad"));
		assertThat(Turns(1).toString(), is("1.0 turns"));
		assertThat(Arcminutes(1).toString(), is("1.0 amin"));
		assertThat(Arcseconds(1).toString(), is("1.0 asec"));
	  }

	@Test
	public void testReturnTheCosOfAnAngle() {
	    assertThat(Radians(1).cos(), is(Math.cos(1d)));
	  }

	@Test
	public void testReturnTheSinOfAnAngle() {
	    assertThat(Radians(1).sin(), is(Math.sin(1d)));
	  }

	@Test
	public void testReturnTheAcosOfAnAngle() {
	    assertThat(Radians(1).acos(), is(Math.acos(1d)));
	  }

	@Test
	public void testReturnTheAsinOfAnAngle() {
	    assertThat(Radians(1).asin(), is(Math.asin(1d)));
	  }

	@Test
	public void testProvideAliasesForSingleUnitValues() {
		assertThat(radian, is(Radians(1)));
		assertThat(degree, is(Degrees(1)));
		assertThat(gradian, is(Gradians(1)));
		assertThat(turn, is(Turns(1)));
		assertThat(arcminute, is(Arcminutes(1)));
		assertThat(arcsecond, is(Arcseconds(1)));
	  }
	
	@Test
	public void testProvideImplicitConversionFromDouble() {
	    double d = 10d;
	    assertThat(radians(d), is(Radians(d)));
	    assertThat(degrees(d), is(Degrees(d)));
	    assertThat(gradians(d), is(Gradians(d)));
	    assertThat(turns(d), is(Turns(d)));
	    assertThat(arcminutes(d), is(Arcminutes(d)));
	    assertThat(arcseconds(d), is(Arcseconds(d)));
	  }

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
//	  it should "provide Numeric support" in {
//	    import AngleConversions.AngleNumeric
//
//	    val as = List(Radians(100), Radians(1))
//	    as.sum should be(Radians(101))
//	  }
//	}
	
	
}
