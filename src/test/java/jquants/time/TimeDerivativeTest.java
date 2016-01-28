package jquants.time;

import static org.junit.Assert.*;

import org.junit.Test;

import com.googlecode.totallylazy.Atomic.constructors;

import static org.hamcrest.CoreMatchers.is;

import jquants.time.*;
import static jquants.time.Time.*;
import jquants.motion.Velocity;
import static jquants.motion.Velocity.*;
import jquants.space.Length;
import static jquants.space.Length.*;
import jquants.motion.Acceleration;
import static jquants.motion.Acceleration.*;
import static jquants.time.TimeSquared.*;
import static jquants.energy.Energy.*;
import static jquants.energy.PowerRamp.*;





public class TimeDerivativeTest {
 
	
//	 behavior of "Time Derivatives and Integrals as implemented in Distance and Velocity"
//
//	  it should "satisfy Derivative = Integral / Time" in {
//	    implicit val tolerance = UsMilesPerHour(0.0000000000001)
//	    UsMilesPerHour(55) should beApproximately(UsMiles(55) / Hours(1))
//	  }
	
	@Test
	public void testShouldSatisfyDerivateEqualsIntegralDividedByTime(){
		
		
		//TODO: implement Operations in all classes implementing TimeDerivative
	  assertThat( Kilometers(55).div(Hours(1.0)), is(KilometersPerHour(55)));
	}
	
//
//	  it should "satisfy Integral = Derivative * Time" in {
//	    implicit val tolerance = UsMiles(0.0000000000001)
//	    UsMiles(110) should beApproximately(UsMilesPerHour(55) * Hours(2))
//	    UsMiles(110) should beApproximately(Hours(2) * UsMilesPerHour(55))
//	  }
	
	@Test
	public void testShouldSatisfyIntegralEqualsDerivativeMultipliedByTime(){
		
		assertThat( UsMiles(110), is(UsMilesPerHour(55).multiply(Hours(2))));
		assertThat( UsMiles(110), is(Hours(2).multiply(UsMilesPerHour(55))));
	}
	
	
//	  it should "satisfy Time = Integral / Derivative" in {
//	    implicit val tolerance = Hours(0.0000000000001)
//	    Hours(2) should beApproximately(UsMiles(110) / UsMilesPerHour(55))
//	  }
//	
	
	@Test
	public void testShouldSatisfyTimeEqualsIntegralDividedByDerivative(){
		
		assertEquals( Seconds(7200), UsMiles(110).div(UsMilesPerHour(55)) );
	}
	
	@Test
	public void testScondTimeIntegralDividedByItsSecondtimeDerivative() {
	  
	  assertEquals( SecondsSquared(1.0), Meters(10).div(MetersPerSecondSquared(10)));
	  assertEquals( HoursSquared(1.0), Wh(10).div(Wpm(600)));
	  assertEquals( HoursSquared(1.0), Wh(10).div(Wph(10)));
	}
	
}
