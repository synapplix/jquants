package jquants.time;

import static jquants.time.Time.Days;
import static jquants.time.Time.Hours;
import static jquants.time.Time.Microseconds;
import static jquants.time.Time.Milliseconds;
import static jquants.time.Time.Minutes;
import static jquants.time.Time.Seconds;
import static jquants.time.Time.*;
import static org.hamcrest.CoreMatchers.is;


import jquants.motion.Velocity;
import jquants.space.Length;
import static jquants.motion.Acceleration.*;
import static jquants.motion.Velocity.*;
import static jquants.motion.Jerk.*;
import static jquants.space.Length.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TimeTest {

	@Test
	public void testCreateValuesUsingUOMFactories() {
		
		assertThat(Nanoseconds(1).toNanoseconds(), is(1.0));
		assertThat(Microseconds(1).toMicroseconds(), is(1.0));
		assertThat(Milliseconds(1).toMilliseconds(), is(1.0));
		assertThat(Seconds(1).toSeconds(), is(1.0));
		assertThat(Minutes(1).toMinutes(), is(1.0));
		assertThat(Hours(1).toHours(), is(1.0));
		assertThat(Days(1).toDays(), is(1.0));
		
	}

	@Test
	public void testCreateValuesFromProperlyFormattedStrings() {
		assertThat( toTime("10.22 ns").get() , is(Nanoseconds(10.22)));
		assertThat( toTime("10.22 µs").get() , is(Microseconds(10.22)));
		assertThat( toTime("10.22 ms").get() , is(Milliseconds(10.22)));
		assertThat( toTime("10.22 s").get() , is(Seconds(10.22)));
		assertThat( toTime("10.22 m").get() , is(Minutes(10.22)));
		assertThat( toTime("10.22 h").get() , is(Hours(10.22)));
		assertThat( toTime("10.22 d").get() , is(Days(10.22)));
//	assertThat( toTime("10.22 z").failed.get() , is(QuantityParseException("Unable to parse toTime", "10.22 z")));
//	assertThat( toTime("ZZ ms").failed.get() , is(QuantityParseException("Unable to parse Time", "ZZ ms")));
	}

// TODO	Use Substitude for Scala Duration
//	  it should "create values from Scala Durations" in {
//	    Time(Duration(10, NANOSECONDS)) , is(Nanoseconds(10))
//	    Time(Duration(10, MICROSECONDS)) , is(Microseconds(10))
//	    Time(Duration(10, MILLISECONDS)) , is(Milliseconds(10))
//	    Time(Duration(10, SECONDS)) , is(Seconds(10))
//	    Time(Duration(10, MINUTES)) , is(Minutes(10))
//	    Time(Duration(10, HOURS)) , is(Hours(10))
//	    Time(Duration(10, DAYS)) , is(Days(10))
//	  }
	
	@Test
	public void testReturnEqualityForUnitsInDifferentUnits() {
		
	  assertThat( Nanoseconds(3000) , is(Microseconds(3)));
		assertThat( Microseconds(1000) , is(Milliseconds(1)));
		assertThat( Milliseconds(1000) , is(Seconds(1)));
		assertThat( Seconds(60) , is(Minutes(1)));
		assertThat( Minutes(60) , is(Hours(1)));
		assertThat( Hours(24) , is(Days(1)));
	}
	
	@Test
	public void testReturnTimeWhenAddedToTime() {

		assertThat(Seconds(60).plus(Minutes(1)), is(Minutes(2)));
		assertThat(Minutes(15).plus(Hours(1.75)), is(Hours(2)));
	}
	
	@Test
	public void testReturnTimeWhenSubtractedToTime() {
		
		assertThat(Minutes(5).minus(Seconds(30)) , is(Minutes(4.5)));
		assertThat(Hours(5).minus(Minutes(30)) , is(Hours(4.5)));
		assertThat(Days(5).minus(Hours(12)) , is(Days(4.5)));
	}
	
	@Test
	public void testProperlyConvertToAllSupportedUnitsOfMeasure() {
		Time x = Seconds(1);
		assertThat(x.toNanoseconds(), is(1e9));
		assertThat(x.toMicroseconds(), is(1e6));
		assertThat(x.toMilliseconds(), is(1000.0));
		assertThat(x.toSeconds(), is(1.0));
		assertThat(x.toMinutes(), is(1d / 60.0));
		assertThat(x.toHours(), is(1d / 3600.0));
		assertThat(x.toDays(), is(1d / (3600 * 24)));
	}
	
	@Test
	public void testRetrunProperlyformattedStringsForAllSupportedUnitsOfMeasure() {
		
	  assertThat(Nanoseconds(1).toString(Nanoseconds) , is("1.0 ns"));
	  assertThat(Microseconds(1).toString(Microseconds) , is("1.0 µs"));
	  assertThat(Milliseconds(1).toString(Milliseconds) , is("1.0 ms"));
	  assertThat(Seconds(1).toString(Seconds) , is("1.0 s"));
	  assertThat(Minutes(1).toString(Minutes) , is("1.0 m"));
	  assertThat(Hours(1).toString(Hours) , is("1.0 h"));
	  assertThat(Days(1).toString(Days) , is("1.0 d"));
	}
	
	@Test
	public void  testreturnTimeIntegralWhenMultipliedByATimeDerivative() {

		 assertThat(Seconds(1).multiply(MetersPerSecond(1)) , is(Meters(1)));
		 assertThat(Seconds(1).multiply(MetersPerSecondSquared(1)) , is(MetersPerSecond(1))); 
//	 assertThat(Seconds(1).multiply(MetersPerSecondCubed(1)) , is(MetersPerSecondSquared(1))); TODO: Implement Jerk
	}
	
	@Test
	public void testProvideImplicitConversionFromDouble() {
		
		assertThat(nanoseconds , is(Nanoseconds(1)));
		assertThat(microseconds , is(Microseconds(1))); 
		assertThat(milliseconds , is(Milliseconds(1)));
		assertThat(seconds , is(Seconds(1)));
		assertThat(minutes , is(Minutes(1)));
		assertThat(halfHour , is(Minutes(30)));
		assertThat(hours , is(Hours(1)));
		assertThat(days , is(Days(1)));
	}

	@Test
	public void testProvideImplicitConversionsFromDouble() {

		double d = 10;
		assertThat(nanoseconds(d), is(Nanoseconds(d)));
		assertThat(microseconds(d), is(Microseconds(d)));
		assertThat(milliseconds(d), is(Milliseconds(d)));
		assertThat(seconds(d), is(Seconds(d)));
		assertThat(minutes(d), is(Minutes(d)));
		assertThat(hours(d), is(Hours(d)));
		assertThat(days(d), is(Days(d)));
	}
	
	@Test
	public void testProvideImplicitConversionsFromString() {

		//assertThat(toTime("10.22 ns").get() , is(Nanoseconds(10.22)));
		assertThat(toTime("10.22 µs").get() , is(Microseconds(10.22)));
		assertThat(toTime("10.22 ms").get() , is(Milliseconds(10.22)));
		assertThat(toTime("10.22 s").get() , is(Seconds(10.22)));
		assertThat(toTime("10.22 m").get() , is(Minutes(10.22)));
		assertThat(toTime("10.22 h").get() , is(Hours(10.22)));
		assertThat(toTime("10.22 d").get() , is(Days(10.22)));
		//assertThat("10.22 z".toTime.failed.get , is(QuantityParseException("Unable to parse Time", "10.22 z")));
		//assertThat("ZZ ms".toTime.failed.get , is(QuantityParseException("Unable to parse Time", "ZZ ms")));
	}
	
// TODO Use Substitude for Scala Duration
//	  it should "convert a Scala Concurrent Duration to a Time with the same units" in {
//	    import TimeConversions._
//
//	    val nanoseconds: Time = Duration(1, NANOSECONDS)
//	    nanoseconds.value , is(1)
//	    nanoseconds.unit , is(Nanoseconds)
//
//	    val microseconds: Time = Duration(1, MICROSECONDS)
//	    microseconds.value , is(1)
//	    microseconds.unit , is(Microseconds)
//
//	    val milliseconds: Time = Duration(1, MILLISECONDS)
//	    milliseconds.value , is(1)
//	    milliseconds.unit , is(Milliseconds)
//
//	    val seconds: Time = Duration(1, SECONDS)
//	    seconds.value , is(1)
//	    seconds.unit , is(Seconds)
//
//	    val minutes: Time = Duration(1, MINUTES)
//	    minutes.value , is(1)
//	    minutes.unit , is(Minutes)
//
//	    val hours: Time = Duration(1, HOURS)
//	    hours.value , is(1)
//	    hours.unit , is(Hours)
//
//	    val days: Time = Duration(1, DAYS)
//	    days.value , is(1)
//	    days.unit , is(Days)
//	  }
//
//	  it should "implicitly convert a Scala Concurrent Duration to a Time in expressions that require it" in {
//	    import TimeConversions._
//
//	    val duration: Duration = Duration(1, "second")
//	    Meters(1) / duration , is(MetersPerSecond(1))
//	  }	
//
//	  it should "convert a Time to a Scala Concurrent Duration with the same units" in {
//	    import TimeConversions._
//
//	    val nanoseconds: Duration = Nanoseconds(1)
//	    nanoseconds.length , is(1)
//	    nanoseconds.unit , is(NANOSECONDS)
//
//	    val microseconds: Duration = Microseconds(1)
//	    microseconds.length , is(1)
//	    microseconds.unit , is(MICROSECONDS)
//
//	    val milliseconds: Duration = Milliseconds(1)
//	    milliseconds.length , is(1)
//	    milliseconds.unit , is(MILLISECONDS)
//
//	    val seconds: Duration = Seconds(1)
//	    seconds.length , is(1)
//	    seconds.unit , is(SECONDS)
//
//	    val minutes: Duration = Minutes(1)
//	    minutes.length , is(1)
//	    minutes.unit , is(MINUTES)
//
//	    val hours: Duration = Hours(1)
//	    hours.length , is(1)
//	    hours.unit , is(HOURS)
//
//	    val days: Duration = Days(1)
//	    days.length , is(1)
//	    days.unit , is(DAYS)
//	  }
//	  it should "convert a Time to a Scala Concurrent Duration in expressions that require it" in {
//	    import TimeConversions._
//
//	    def doSomethingWithDuration(duration: Duration): Unit = duration , is(Duration(10, SECONDS))
//
//	    doSomethingWithDuration(Seconds(10))
//	  }
//	}
}
