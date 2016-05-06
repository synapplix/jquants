package jquants.time;

import static jquants.Dimensionless.Each;
import static jquants.energy.Energy.WattHours;
import static jquants.energy.Energy.Wh;
import static jquants.energy.Power.Watts;
import static jquants.energy.PowerRamp.Wph;
import static jquants.energy.PowerRamp.Wpm;
import static jquants.mass.Mass.Kilograms;
import static jquants.motion.Acceleration.MetersPerSecondSquared;
import static jquants.motion.Jerk.MetersPerSecondCubed;
import static jquants.motion.MassFlowRate.KilogramsPerSecond;
import static jquants.motion.Velocity.KilometersPerHour;
import static jquants.motion.Velocity.UsMilesPerHour;
import static jquants.space.Length.Kilometers;
import static jquants.space.Length.Meters;
import static jquants.space.Length.UsMiles;
import static jquants.time.Frequency.Hertz;
import static jquants.time.Time.Hours;
import static jquants.time.Time.Seconds;
import static jquants.time.TimeSquared.HoursSquared;
import static jquants.time.TimeSquared.SecondsSquared;
import static jquants.energy.PowerRamp.*;
import static jquants.motion.Velocity.*;
import static jquants.space.Volume.*;
import static jquants.motion.VolumeFlowRate.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

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

    // Methods of the Quantitiy class
    assertThat( UsMiles(110).div(UsMiles(55)), is(2.0)); // Length/Length => double
    assertThat( Kilometers(110).div(Meters(100)), is(1100.0)); // Length/Length => double
    assertThat( UsMiles(110).div(2), is(UsMiles(55))); // Length/double => Length
    
    // Using the div Method of the TimeIntegral
    assertThat( Hours(2.0), is(UsMiles(110).div(UsMilesPerHour(55)))); // Length/Velocity => Time
    
    assertEquals( Seconds(2.0), MetersPerSecondSquared(110).div(MetersPerSecondCubed(55))); //Acceleration
    assertEquals( Seconds(2.0), Each(110).div(Hertz(55))); //Dimensionless
    assertEquals( Hours(2.0), WattHours(110).div(Watts(55))); //Energy
    assertEquals( Seconds(2.0), Kilograms(110).div(KilogramsPerSecond(55))); //Mass 
    assertEquals( Hours(2.0), Watts(110).div(WattsPerHour(55))); //Power
    assertEquals( Seconds(2.0), MetersPerSecond(110).div(MetersPerSecondSquared(55))); //Velocity
    assertEquals( Seconds(2.0), CubicMeters(110).div(CubicMetersPerSecond(55)) ); //Volume
    assertEquals( Meters(100.0), (MetersPerSecondSquared(100).multiply(SecondsSquared(1)))); //Volume
    
    // Using the div Method of the SecondTimeIntegral
    assertThat( Meters(100).div(MetersPerSecondSquared(100)), is(SecondsSquared(1.0))); // Length/Acceleration => Time²
    
	}
	
	@Test
	public void testScondTimeIntegralDividedByItsSecondtimeDerivative() {
	  assertEquals( SecondsSquared(1.0), Meters(10).div(MetersPerSecondSquared(10)));
	  assertEquals( HoursSquared(1.0), Wh(10).div(Wpm(600)));
	  assertEquals( HoursSquared(1.0), Wh(10).div(Wph(10)));
	}
	
	@Test
  public void testSecondTimeDerivativeMultipliedByTimeSquared(){
    assertEquals( Meters(10.0), MetersPerSecondSquared(10).multiply(SecondsSquared(1))); //Accleration
    assertEquals( MetersPerSecond(10.0), MetersPerSecondCubed(10).multiply(SecondsSquared(1))); //Jerk
    assertEquals( WattHours(10.0), WattsPerHour(10).multiply(HoursSquared(1))); //PowerRamp
  }
  
	
}
