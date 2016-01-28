package jquants;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static jquants.space.Length.*;
import static jquants.mass.Mass.*;
import org.junit.Test;

import jquants.mass.Mass;
import jquants.space.Length;

public class RatioTest {

//  it should "create a ratio with the correct base and counter values" in {
//    val ratio = TestRatio(Kilograms(10), Meters(5))
//    ratio.base should be(Kilograms(10))
//    ratio.counter should be(Meters(5))
//  }
  
  @Test
  public void shouldCreateARatioWithTheCorrectBaseAndCounterAndRatioValues(){
    Ratio<Mass,Length> ratio = new Ratio(Kilograms(10), Meters(5));
    assertThat(ratio.base, is(Kilograms(10)));
    assertThat(ratio.counter, is(Meters(5)));
  }
  
//
//  it should "convert a value of the base type to a value of the counter type" in {
//    val ratio = TestRatio(Kilograms(10), Meters(5))
//    ratio.convertToCounter(Kilograms(25)) should be(Meters(12.5))
//  }
  
  @Test
  public void shouldConvertAValueOfTheBaseTypeToAValueOfTheCounterType(){
    Ratio ratio = new Ratio(Kilograms(10), Meters(5));
    assertThat(ratio.convertToCounter(Kilograms(25)), is(Meters(12.5)));
  }
  
  //
//  it should "convert a value of the counter type to a value of the base type" in {
//    val ratio = TestRatio(Kilograms(10), Meters(5))
//    ratio.convertToBase(Meters(25)) should be(Kilograms(50))
//  }
  
  @Test
  public void shouldConvertAValueOfTheCounterTypeToAValueOfTheBaseType(){
    Ratio ratio = new Ratio(Kilograms(10), Meters(5));
    assertThat(ratio.convertToBase(Meters(25)), is(Kilograms(50)));
  }

//  public double ratio() {
//return (base.value)/(counter.value);
//}
//
//public double inverseratio() {
//return (counter.value)/(base.value);
//
  
  @Test
  public void shouldCreateALikeRatioWithTheCorrectBaseAndCounterAndRatioValues(){
    LikeRatio<Length> likeRatio = new LikeRatio(Meters(10), Meters(5));
    assertThat(likeRatio.base, is(Meters(10)));
    assertThat(likeRatio.counter, is(Meters(5)));
    assertThat(likeRatio.ratio(), is(2.0));
    assertThat(likeRatio.inverseratio(), is(0.5));
  }
}
