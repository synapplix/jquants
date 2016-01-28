package jquants.motion;


import org.hamcrest.core.Is;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import jquants.motion.VolumeFlowRate;
import static jquants.motion.VolumeFlowRate.*;

import jquants.space.Volume;
import static jquants.space.Volume.*;


import jquants.time.Time;
import static jquants.time.Time.*;

public class VolumeFlowRateTest  {

//behavior of "VolumeFlow and its Units of Measure"
//
//  it should "create values using UOM factories" in {
//    CubicMetersPerSecond(1).toCubicMetersPerSecond should be(1)
//    CubicFeetPerHour(1).toCubicFeetPerHour should be(1)
//    GallonsPerDay(1).toGallonsPerDay should be(1)
//    GallonsPerHour(1).toGallonsPerHour should be(1)
//    GallonsPerMinute(1).toGallonsPerMinute should be(1)
//    GallonsPerSecond(1).toGallonsPerSecond should be(1)
//  }
  @Test
  public void tesBbehaviorOfVolumeFlowAndItsUnitsOfMeasure() {
    assertThat(CubicMetersPerSecond(1).toCubicMetersPerSecond(), is(1.0));
    assertThat(CubicFeetPerHour(1).toCubicFeetPerHour(), is(1.0));
    assertThat(GallonsPerDay(1).toGallonsPerDay(), is(1.0));
    assertThat(GallonsPerHour(1).toGallonsPerHour(), is(1.0));
    assertThat(GallonsPerMinute(1).toGallonsPerMinute(), is(1.0));
    assertThat(GallonsPerSecond(1).toGallonsPerSecond(), is(1.0));
  }

//
  @Test
  public void tesShouldCreateValuesFromProperlyFormattedStrings() {
    
  assertThat(  toVolumeFlowRate("10.22 m³/s").get(), is(CubicMetersPerSecond(10.22)));
  assertThat(  toVolumeFlowRate("10.22 ft³/hr").get(), is(CubicFeetPerHour(10.22)));
  assertThat(  toVolumeFlowRate("10.22 GPD").get(), is(GallonsPerDay(10.22)));
  assertThat(  toVolumeFlowRate("10.22 GPH").get(), is(GallonsPerHour(10.22)));
  assertThat(  toVolumeFlowRate("10.22 GPM").get(), is(GallonsPerMinute(10.22)));
  assertThat(  toVolumeFlowRate("10.22 GPS").get(), is(GallonsPerSecond(10.22)));
//  assertThat(  toVolumeFlowRate("10.22 zz").failed.get(), is(QuantityParseException("Unable to parse VolumeFlowRate", "10.22 zz")));
//  assertThat(  toVolumeFlowRate("zz m³/s").failed.get(), is(QuantityParseException("Unable to parse VolumeFlow", "zz m³/s")));
  }

  
  @Test
  public void testShouldProperlyConvertToAllSupportedUnitsOfMeasure() {
    
    VolumeFlowRate x = CubicMetersPerSecond(10.22);
    assertThat(  x.toCubicMetersPerSecond(), is(10.22));
    assertEquals(  x.toCubicFeetPerHour(), (CubicMeters(10.22).toCubicFeet()) / (Seconds(1).toHours()) , 0.00000001);
    assertEquals(  x.toGallonsPerDay(), (CubicMeters(10.22).toUsGallons()) / (Seconds(1).toDays()) , 0.00000001);
    assertEquals(  x.toGallonsPerHour(), (CubicMeters(10.22).toUsGallons()) / (Seconds(1).toHours()) , 0.00000001);
    assertEquals(  x.toGallonsPerMinute(), (CubicMeters(10.22).toUsGallons()) / (Seconds(1).toMinutes()) , 0.00000001);
    assertEquals(  x.toGallonsPerSecond(), (CubicMeters(10.22).toUsGallons()) / (Seconds(1).toSeconds()) , 0.00000001);
  }
  
//
//  it should "return properly formatted strings for all supported Units of Measure" in {

  @Test
  public void shouldReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure() {
    
    assertThat( CubicMetersPerSecond(1).toString(CubicMetersPerSecond), is("1.0 m³/s"));
    assertThat( CubicFeetPerHour(1).toString(CubicFeetPerHour), is("1.0 ft³/hr"));
    assertThat( GallonsPerDay(1).toString(GallonsPerDay), is("1.0 GPD"));
    assertThat( GallonsPerHour(1).toString(GallonsPerHour), is("1.0 GPH"));
    assertThat( GallonsPerMinute(1).toString(GallonsPerMinute), is("1.0 GPM"));
    assertThat( GallonsPerSecond(1).toString(GallonsPerSecond), is("1.0 GPS"));
  }
//
//  it should "return Volume when multiplied by Time" in {
  @Test
  public void testShouldReturnVolumeWhenMultipliedbyTime() {
    
    assertThat(  CubicMetersPerSecond(1).multiply(Seconds(1)), is(CubicMeters(1)));
  }
//
//  behavior of "VolumeFlowConversions"
//
//  it should "provide aliases for single unit values" in {
//    import VolumeFlowConversions._
  @Test
  public void testShouldProvideAliasesForSingleUnitValues() {
    
    assertThat(  cubicMeterPerSecond, is(CubicMetersPerSecond(1)));
    assertThat(  cubicFeetPerHour, is(CubicFeetPerHour(1)));
    assertThat(  gallonPerDay, is(GallonsPerDay(1)));
    assertThat(  gallonPerHour, is(GallonsPerHour(1)));
    assertThat(  gallonPerMinute, is(GallonsPerMinute(1)));
    assertThat(  gallonPerSecond, is(GallonsPerSecond(1)));
  }
//
//  it should "provide implicit conversion from Double" in {
//    import VolumeFlowConversions._
  @Test
  public void testShouldProvideImplicitConversionFromDouble() {
   double n = 10.22;
    assertThat( CubicMetersPerSecond(n), is(CubicMetersPerSecond(n)));
    assertThat( CubicFeetPerHour(n), is(CubicFeetPerHour(n)));
    assertThat( GallonsPerDay(n), is(GallonsPerDay(n)));
    assertThat( GallonsPerHour(n), is(GallonsPerHour(n)));
    assertThat( GallonsPerMinute(n), is(GallonsPerMinute(n)));
    assertThat( GallonsPerSecond(n), is(GallonsPerSecond(n)));
//  }
//
//  it should "provide Numeric support" in {
//    import VolumeFlowConversions.VolumeFlowNumeric
//    implicit val tolerance = GallonsPerDay(0.0000000000001)
//  assertThat(  val vfrs = List(GallonsPerDay(24), GallonsPerHour(1)));
//  assertThat(  vfrs.sum(), isApproximately(GallonsPerDay(48)));
//  }
//}  
  
  
}
}
