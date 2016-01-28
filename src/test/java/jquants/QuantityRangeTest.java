package jquants;

import static jquants.space.Length.Meters;
import static jquants.time.Time.Minutes;
import static jquants.time.Time.Seconds;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.junit.Test;

import jquants.space.Length;

public class QuantityRangeTest {

//  it should "throw an IllegalArgumentException when the lower bound >= upper bound" in {
//    an[IllegalArgumentException] should be thrownBy QuantityRange(Meters(2), Meters(1))
//  }
  @Test (expected=IllegalArgumentException.class)
  public void shouldThrowAnIllegalArgumentExceptionWhenTheLowerBoundGEUpperBound(){
    new QuantityRange(Meters(2), Meters(1));
  }
  
//
//  it should "create a range with lower and upper bound" in {
//    val r1 = QuantityRange(Meters(1), Meters(2))
//    r1.lower should be(Meters(1))
//    r1.upper should be(Meters(2))
//
//    val r2 = QuantityRange(Celsius(0), Fahrenheit(100))
//    r2.lower should be(Celsius(0))
//    r2.upper should be(Fahrenheit(100))
//  }
//
  @Test
  public void shouldCreateARangeWithLowerAndUpperBound() {
    QuantityRange<Length> r1 = new QuantityRange<Length>(Meters(1), Meters(2));
    assertThat(r1.lower, is(Meters(1)));
    assertThat(r1.upper, is(Meters(2)));
    
    QuantityRange r2 = new QuantityRange(Seconds(1), Minutes(2));
    assertThat(r2.lower, is(Seconds(1)));
    assertThat(r2.upper, is(Minutes(2)));
  }
  
//  it should "times a Double and get back a List of ranges" in {
//    val r = QuantityRange(Meters(0), Meters(10))
//    val rs = r times 3
//    rs.head should be(QuantityRange(Meters(0), Meters(10)))
//    rs.tail.head should be(QuantityRange(Meters(10), Meters(20)))
//    rs.tail.tail.head should be(QuantityRange(Meters(20), Meters(30)))
//
//    val rs2 = r * 3
//    rs2.head should be(QuantityRange(Meters(0), Meters(10)))
//    rs2.tail.head should be(QuantityRange(Meters(10), Meters(20)))
//    rs2.tail.tail.head should be(QuantityRange(Meters(20), Meters(30)))
//  }
  
  @Test
  public void shouldTimesADoubleAndGetBackAListOfRanges(){
    QuantityRange r = new QuantityRange(Meters(0), Meters(10));
    ArrayList<QuantityRange> rs = r.times(3);
    assertThat(rs.get(0) , is(new QuantityRange(Meters(0), Meters(10))));
    assertThat(rs.get(1) , is(new QuantityRange(Meters(10), Meters(20))));
    assertThat(rs.get(2) , is(new QuantityRange(Meters(20), Meters(30))));
  }
//
//  it should "divide a like value and get back a List of ranges" in {
//    val r = QuantityRange(Meters(0), Meters(10))
//    val rs = r / Meters(4)
//    rs.head should be(QuantityRange(Meters(0), Meters(4)))
//    rs.tail.head should be(QuantityRange(Meters(4), Meters(8)))
//    rs.tail.tail.head should be(QuantityRange(Meters(8), Meters(10)))
//  }
  
  @Test
  public void shouldDivideALikeValueAndGetBackAListOfRanges() {
    QuantityRange r = new QuantityRange(Meters(0), Meters(10));
    ArrayList<QuantityRange> rs = r.divide(Meters(4));
    assertThat(rs.get(0) , is(new QuantityRange(Meters(0), Meters(4))));
    assertThat(rs.get(1) , is(new QuantityRange(Meters(4), Meters(8))));
    assertThat(rs.get(2) , is(new QuantityRange(Meters(8), Meters(10))));
  }
//
//  it should "divide a Double and get back a List of ranges" in {
//    val r = QuantityRange(Meters(0), Meters(10))
//    val rs = r / 2.5
//    rs.head should be(QuantityRange(Meters(0), Meters(4)))
//    rs.tail.head should be(QuantityRange(Meters(4), Meters(8)))
//    rs.tail.tail.head should be(QuantityRange(Meters(8), Meters(10)))
//  }
  
  @Test
  public void shouldDivideADoubleAndGetBackAListOfRanges() {
    QuantityRange r = new QuantityRange(Meters(0), Meters(10));
    ArrayList<QuantityRange> rs = r.divide(2.5);
    assertThat(rs.get(0) , is(new QuantityRange(Meters(0), Meters(4))));
    assertThat(rs.get(1) , is(new QuantityRange(Meters(4), Meters(8))));
    assertThat(rs.get(2) , is(new QuantityRange(Meters(8), Meters(10))));
    
  }
//
//  it should "foreach a like value and execute an operation on each sub range" in {
//    val r = QuantityRange(Meters(0), Meters(5))
//    var accum = 0d
//    r.foreach(Meters(1))(sub ⇒ accum = accum + sub.upper.toMeters)
//    accum should be(15)
//  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void shouldForeachAlikeValueAndExecuteAnOperationOnEachSubRange(){
    QuantityRange<Length> r = new QuantityRange<Length>(Meters(0), Meters(5));
    ArrayList<Double> accum = new ArrayList<Double>();
    r.foreach(Meters(1), sub -> accum.add( ((QuantityRange<Length>)sub).upper.value)); //.sum()
    double sumOfAccum = accum.stream().mapToDouble(Double::doubleValue).sum();
    assertThat(sumOfAccum, is(15.0));
  }
  
//
//  it should "map a like value and applies a map operation on each sub range" in {
//    val r = QuantityRange(Meters(0), Meters(5))
//    val rs = r / Meters(1)
//    val rsMapped = r.map(Meters(1))(x ⇒ x)
//    rsMapped should be(rs)
//  }
  
  @Test
  public void shouldMapAlikeValueAndAppliesAMapOperationOnEachSubRange() {
    QuantityRange<Length> r = new QuantityRange<Length>(Meters(0), Meters(5));
    ArrayList<QuantityRange<Length>> rs = r.divide(Meters(1));
    ArrayList<QuantityRange<Length>> rsMapped = r.map(Meters(1), x -> x);
    assertThat(rs, is(rsMapped));
  }
//
//  it should "map a Double and applies a map operation on each sub range" in {
//    val r = QuantityRange(Meters(0), Meters(35))
//    val rs = r / 5
//    val rsMapped = r.map(5)(x ⇒ x)
//    rsMapped should be(rs)
//  }
//
  @Test
  public void shouldMapADoubleAndAppliesAMapOperationOnEachSubRange() {
    QuantityRange<Length> r = new QuantityRange<Length>(Meters(0), Meters(35));
    ArrayList<QuantityRange<Length>> rs = r.divide(5.0);
    ArrayList<?> rsMapped = r.map(5, x -> x);
    assertThat(rsMapped, is(rs));
  }
  
//  it should "foldLeft a like value and applies an operation on each sub range" in {
//    val r = QuantityRange(Meters(0), Meters(5))
//    val total = r.foldLeft(Meters(1), Meters(0))((z, x) ⇒ z + x.upper)
//    total should be(Meters(15))
//  }
  
  @Test
  public void shouldFoldLeftAlikeValueAndAppliesAnOperationOnEachSubRange() {
    QuantityRange<Length> r = new QuantityRange(Meters(0), Meters(5));
    Length foldLeft = r.foldLeft(Meters(1), Meters(0),(z, x) -> z.plus(x.upper)); 
    assertThat(foldLeft,  is(Meters(15)));
  }
//
//  it should "foldLeft a Double and applies an operation on each sub range" in {
//    val r = QuantityRange(Meters(0), Meters(5))
//    val total = r.foldLeft(5, Meters(0))((z, x) ⇒ z + x.upper)
//    total should be(Meters(15))
//  }
  
  @Test
  public void shouldFoldLeftADoubleValueAndAppliesAnOperationOnEachSubRange() {
    QuantityRange<Length> r = new QuantityRange(Meters(0), Meters(5));
    Length foldLeft = r.foldLeft(5.0, Meters(0),(z, x) -> z.plus(x.upper)); 
    assertThat(foldLeft,  is(Meters(15)));
  }
//
//  it should "foldRight a like value and applies an operation on each sub range" in {
//    val r = QuantityRange(Meters(0), Meters(5))
//    val total = r.foldRight(Meters(1), Meters(0))((x, z) ⇒ z + x.upper)
//    total should be(Meters(15))
//  }
  
  @Test
  public void shouldFoldRightAlikeValueAndAppliesAnOperationOnEachSubRange() {
    QuantityRange<Length> r = new QuantityRange(Meters(0), Meters(5));
    Length foldLeft = r.foldRight(Meters(1), Meters(0),(x, z) -> z.plus(x.upper)); 
    assertThat(foldLeft,  is(Meters(15)));
  }
//
//  it should "foldRight a Double and applies an operation on each sub range" in {
//    val r = QuantityRange(Meters(0), Meters(5))
//    val total = r.foldRight(5, Meters(0))((x, z) ⇒ z + x.upper)
//    total should be(Meters(15))
//  }
  
  @Test
  public void shouldFoldRightADoubleValueAndAppliesAnOperationOnEachSubRange() {
    QuantityRange<Length> r = new QuantityRange(Meters(0), Meters(5));
    Length foldLeft = r.foldRight(5.0, Meters(0),(x, z) -> z.plus(x.upper)); 
    assertThat(foldLeft,  is(Meters(15)));
  }
//
//  it should "inc and return a QualityRange with lower and upper bounds incremented by the range value" in {
//    QuantityRange(Meters(0), Meters(10)).++ should be(QuantityRange(Meters(10), Meters(20)))
//  }
  
  @Test
  public void shouldIincAndReturnAQualityRangeWithLlowerAndUpperBoundsIncrementedByTheRangeValue() {
    assertThat(new QuantityRange(Meters(0), Meters(10)).inc(), is(new QuantityRange(Meters(10), Meters(20))));
  }
//
//  it should "inc a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
//    QuantityRange(Meters(0), Meters(10)) ++ Meters(5) should be(QuantityRange(Meters(5), Meters(15)))
//  }
  
  @Test
  public void shouldIincALikeValueAndReturnAQualityRangeWithLlowerAndUpperBoundsIncrementedByTheRangeValue() {
    assertThat(new QuantityRange(Meters(0), Meters(10)).inc(Meters(5)), is(new QuantityRange(Meters(5), Meters(15))));
  }
//
//  it should "dec and return a QualityRange with lower and upper bounds incremented by the range value" in {
//    QuantityRange(Meters(10), Meters(20)).-- should be(QuantityRange(Meters(0), Meters(10)))
//  }
  
  @Test
  public void shouldDecAndReturnAQualityRangeWithLowerAndUpperBoundsIncrementedByTheRangeValue() {
    assertThat(new QuantityRange(Meters(10), Meters(20)).dec(), is(new QuantityRange(Meters(0), Meters(10))));
  }
//
//  it should "dec a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
//    QuantityRange(Meters(10), Meters(20)) -- Meters(5) should be(QuantityRange(Meters(5), Meters(15)))
//  }
  
  @Test
  public void shouldDecALikeValueAndReturnAQualityRangeWithLowerAndUpperBoundsIncrementedByTheRangeValue() {
    assertThat(new QuantityRange(Meters(10), Meters(20)).dec(Meters(5)), is(new QuantityRange(Meters(5), Meters(15))));
  }
//
//  it should "incTo a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
//    QuantityRange(Meters(0), Meters(10)) =+ Meters(5) should be(QuantityRange(Meters(0), Meters(15)))
//  }
  
  @Test
  public void shouldIntToALikeValueAndReturnAQualityRangeWithLowerAndUpperBoundsIncrementedByTheRangeValue() {
    assertThat(new QuantityRange(Meters(0), Meters(10)).incTo(Meters(5)), is(new QuantityRange(Meters(0), Meters(15))));
  }
//
//  it should "decTo a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
//    QuantityRange(Meters(0), Meters(10)) =- Meters(5) should be(QuantityRange(Meters(0), Meters(5)))
//  }
  
  @Test
  public void shouldDecToALikeValueAndReturnAQualityRangeWithLowerAndUpperBoundsIncrementedByTheRangeValue() {
    assertThat(new QuantityRange(Meters(0), Meters(10)).decTo(Meters(5)), is(new QuantityRange(Meters(0), Meters(5))));
  }
//
//  it should "incFrom a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
//    (QuantityRange(Meters(0), Meters(10)) += Meters(5)) should be(QuantityRange(Meters(5), Meters(10)))
//  }
  
  @Test
  public void shouldIncFromALikeValueAndReturnAQualityRangeWithLowerAndUpperBoundsIncrementedByTheRangeValue() {
    assertThat(new QuantityRange(Meters(0), Meters(10)).incFrom(Meters(5)), is(new QuantityRange(Meters(5), Meters(10))));
  }
//
//  it should "decFrom a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
//    (QuantityRange(Meters(0), Meters(10)) -= Meters(5)) should be(QuantityRange(Meters(-5), Meters(10)))
//  }
  
  @Test
  public void shouldDecFromALikeValueAndReturnAQualityRangeWithLowerAndUpperBoundsIncrementedByTheRangeValue() {
    assertThat(new QuantityRange(Meters(0), Meters(10)).decFrom(Meters(5)), is(new QuantityRange(Meters(-5), Meters(10))));
  }
//
//  it should "incFromDecTo a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
//    (QuantityRange(Meters(0), Meters(10)) +- Meters(4)) should be(QuantityRange(Meters(4), Meters(6)))
//  }

  
  @Test
  public void shouldIncFromDecToALikeValueAndReturnAQualityRangeWithLowerAndUpperBoundsIncrementedByTheRangeValue() {
    assertThat(new QuantityRange(Meters(0), Meters(10)).incFromDecTo(Meters(4)), is(new QuantityRange(Meters(4), Meters(6))));

  }
  
//  it should "decFromIncTo a like value and return a QualityRange with lower and upper bounds incremented by the range value" in {
//    (QuantityRange(Meters(0), Meters(10)) -+ Meters(4)) should be(QuantityRange(Meters(-4), Meters(14)))
//  }
  
  @Test
  public void shoulddecFromIncToToALikeValueAndReturnAQualityRangeWithLowerAndUpperBoundsIncrementedByTheRangeValue() {
    assertThat(new QuantityRange(Meters(0), Meters(10)).decFromIncTo(Meters(4)), is(new QuantityRange(Meters(-4), Meters(14))));
  }
//
//  it should "contains a like value and return true or false" in {
//    !QuantityRange(Meters(0), Meters(10)).contains(Meters(-1)) should be(right = true)
//    QuantityRange(Meters(0), Meters(10)).contains(Meters(0)) should be(right = true)
//    QuantityRange(Meters(0), Meters(10)).contains(Meters(5)) should be(right = true)
//    !QuantityRange(Meters(0), Meters(10)).contains(Meters(10)) should be(right = true)
//    !QuantityRange(Meters(0), Meters(10)).contains(Meters(11)) should be(right = true)
//  }
  
  @Test
  public void shouldContainAlikeValueAndReturnTrueOrFalse() {
    assertThat(!new QuantityRange(Meters(0), Meters(10)).contains(Meters(-1)), is(true));
    assertThat(new QuantityRange(Meters(0), Meters(10)).contains(Meters(0)), is(true));
    assertThat(new QuantityRange(Meters(0), Meters(10)).contains(Meters(5)), is(true));
    assertThat(!new QuantityRange(Meters(0), Meters(10)).contains(Meters(10)), is(true));
    assertThat(!new QuantityRange(Meters(0), Meters(10)).contains(Meters(11)), is(true));
  }
//
//  it should "return equality" in {
//    val r1 = QuantityRange(Meters(10), Meters(15))
//    val r2 = QuantityRange(Meters(10), Meters(15))
//    val r3 = QuantityRange(Meters(11), Meters(15))
//
//    r1 should be(r2)
//    r1 != r3 should be(right = true)
//  }
  
  @Test
  public void shouldReturnEquality() {
    QuantityRange<Length> r1 = new QuantityRange<>(Meters(10), Meters(15));
    QuantityRange<Length> r2 = new QuantityRange<>(Meters(10), Meters(15));
    QuantityRange<Length> r3 = new QuantityRange<>(Meters(11), Meters(15));
    assertThat(r1, is(r2));
    assertThat(!r1.equals(r3), is(true));
  }
//
//  it should "contains a another range and return true or false" in {
//    val r = QuantityRange(Meters(30), Meters(100))
//    val rIn = QuantityRange(Meters(30), Meters(99))
//    val rInOnEnd = QuantityRange(Meters(30), Meters(100))
//    val rBeyondLower = QuantityRange(Meters(25), Meters(50))
//    val rBeyondUpper = QuantityRange(Meters(50), Meters(110))
//    val rBeyondBoth = QuantityRange(Meters(25), Meters(105))
//    val rBelow = QuantityRange(Meters(10), Meters(20))
//    val rOver = QuantityRange(Meters(105), Meters(205))
//    r.contains(rIn) should be(right = true)
//    !r.contains(rInOnEnd) should be(right = true)
//    !r.contains(rBeyondLower) should be(right = true)
//    !r.contains(rBeyondUpper) should be(right = true)
//    !r.contains(rBeyondBoth) should be(right = true)
//    !r.contains(rBelow) should be(right = true)
//    !r.contains(rOver) should be(right = true)
//  }
  
  @Test
  public void shouldContainAnotherRangeAndReturnsTrueOrFalse() {
  QuantityRange<Length> r = new QuantityRange(Meters(30), Meters(100));
  QuantityRange<Length> rIn = new QuantityRange(Meters(30), Meters(99));
  QuantityRange<Length> rInOnEnd = new QuantityRange(Meters(30), Meters(100));
  QuantityRange<Length> rBeyondLower = new QuantityRange(Meters(25), Meters(50));
  QuantityRange<Length> rBeyondUpper = new QuantityRange(Meters(50), Meters(110));
  QuantityRange<Length> rBeyondBoth = new QuantityRange(Meters(25), Meters(105));
  QuantityRange<Length> rBelow = new QuantityRange(Meters(10), Meters(20));
  QuantityRange<Length> rOver = new QuantityRange(Meters(105), Meters(205));
  assertThat(r.contains(rIn), is(true));
  assertThat(!r.contains(rInOnEnd), is(true));
  assertThat(!r.contains(rBeyondLower), is(true));
  assertThat(!r.contains(rBeyondUpper), is(true));
  assertThat(!r.contains(rBeyondBoth), is(true));
  assertThat(!r.contains(rBelow), is(true));
  assertThat(!r.contains(rOver), is(true));
  }
  
//  it should "partiallyContains a another range and return true or false" in {
//    val r = QuantityRange(Meters(30), Meters(100))
//    val rIn = QuantityRange(Meters(30), Meters(99))
//    val rInOnEnd = QuantityRange(Meters(30), Meters(100))
//    val rBeyondLower = QuantityRange(Meters(25), Meters(50))
//    val rBeyondUpper = QuantityRange(Meters(50), Meters(110))
//    val rBeyondBoth = QuantityRange(Meters(25), Meters(105))
//    val rBelow = QuantityRange(Meters(10), Meters(20))
//    val rOver = QuantityRange(Meters(105), Meters(205))
//    r.partiallyContains(rIn) should be(right = true)
//    r.partiallyContains(rInOnEnd) should be(right = true)
//    r.partiallyContains(rBeyondLower) should be(right = true)
//    r.partiallyContains(rBeyondUpper) should be(right = true)
//    r.partiallyContains(rBeyondBoth) should be(right = true)
//    !r.partiallyContains(rBelow) should be(right = true)
//    !r.partiallyContains(rOver) should be(right = true)
//  }
  
  
  @Test
  public void shoulPartiallyContainAnotherRangeAndReturnsTrueOrFalse() {
    QuantityRange<Length> r = new QuantityRange(Meters(30), Meters(100));;
    QuantityRange<Length> rIn = new QuantityRange(Meters(30), Meters(99));
    QuantityRange<Length> rInOnEnd = new QuantityRange(Meters(30), Meters(100));
    QuantityRange<Length> rBeyondLower = new QuantityRange(Meters(25), Meters(50));
    QuantityRange<Length> rBeyondUpper = new QuantityRange(Meters(50), Meters(110));
    QuantityRange<Length> rBeyondBoth = new QuantityRange(Meters(25), Meters(105));
    QuantityRange<Length> rBelow = new QuantityRange(Meters(10), Meters(20));
    QuantityRange<Length> rOver = new QuantityRange(Meters(105), Meters(205));
    assertThat(r.partiallyContains(rIn), is(true));
    assertThat(r.partiallyContains(rInOnEnd), is(true));
    assertThat(r.partiallyContains(rBeyondLower), is(true));
    assertThat(r.partiallyContains(rBeyondUpper), is(true));
    assertThat(r.partiallyContains(rBeyondBoth), is(true));
    assertThat(!r.partiallyContains(rBelow), is(true));
    assertThat(!r.partiallyContains(rOver), is(true));
  }
//
//  it should "includes a like value and return true or false" in {
//    !QuantityRange(Meters(0), Meters(10)).includes(Meters(-1)) should be(right = true)
//    QuantityRange(Meters(0), Meters(10)).includes(Meters(0)) should be(right = true)
//    QuantityRange(Meters(0), Meters(10)).includes(Meters(5)) should be(right = true)
//    QuantityRange(Meters(0), Meters(10)).includes(Meters(10)) should be(right = true)
//    !QuantityRange(Meters(0), Meters(10)).includes(Meters(11)) should be(right = true)
//  }
  
  
  @Test
  public void shouldIncludeAlikeValueAndReturnTrueOrFalse() {
    assertThat(!new QuantityRange(Meters(0), Meters(10)).includes(Meters(-1)), is(true));
    assertThat(new QuantityRange(Meters(0), Meters(10)).includes(Meters(0)), is(true));
    assertThat(new QuantityRange(Meters(0), Meters(10)).includes(Meters(5)), is(true));
    assertThat(new QuantityRange(Meters(0), Meters(10)).includes(Meters(10)), is(true));
    assertThat(! new QuantityRange(Meters(0), Meters(10)).includes(Meters(11)), is(true));
  }
//
//  it should "includes a another range and return true or false" in {
//    val r = QuantityRange(Meters(30), Meters(100))
//    val rIn = QuantityRange(Meters(30), Meters(99))
//    val rInOnEnd = QuantityRange(Meters(30), Meters(100))
//    val rBeyondLower = QuantityRange(Meters(25), Meters(50))
//    val rBeyondUpper = QuantityRange(Meters(50), Meters(110))
//    val rBeyondBoth = QuantityRange(Meters(25), Meters(105))
//    val rBelow = QuantityRange(Meters(10), Meters(20))
//    val rOver = QuantityRange(Meters(105), Meters(205))
//    r.includes(rIn) should be(right = true)
//    r.includes(rInOnEnd) should be(right = true)
//    !r.includes(rBeyondLower) should be(right = true)
//    !r.includes(rBeyondUpper) should be(right = true)
//    !r.includes(rBeyondBoth) should be(right = true)
//    !r.includes(rBelow) should be(right = true)
//    !r.includes(rOver) should be(right = true)
//  }
  
  
  @Test
  public void shouldIncludeAnotherRangeAndReturnTrueOrFalse() {
    QuantityRange<Length> r = new QuantityRange(Meters(30), Meters(100));
    QuantityRange<Length> rIn = new QuantityRange(Meters(30), Meters(99));
    QuantityRange<Length> rInOnEnd = new QuantityRange(Meters(30), Meters(100));
    QuantityRange<Length> rBeyondLower = new QuantityRange(Meters(25), Meters(50));
    QuantityRange<Length> rBeyondUpper = new QuantityRange(Meters(50), Meters(110));
    QuantityRange<Length> rBeyondBoth = new QuantityRange(Meters(25), Meters(105));
    QuantityRange<Length> rBelow = new QuantityRange(Meters(10), Meters(20));
    QuantityRange<Length> rOver = new QuantityRange(Meters(105), Meters(205));
    assertThat(r.includes(rIn), is(true));
    assertThat(r.includes(rInOnEnd), is(true));
    assertThat(!r.includes(rBeyondLower), is(true));
    assertThat(!r.includes(rBeyondUpper), is(true));
    assertThat(!r.includes(rBeyondBoth), is(true));
    assertThat(!r.includes(rBelow), is(true));
    assertThat(!r.includes(rOver), is(true));
  }
//
//  it should "partiallyIncludes a another range and return true or false" in {
//    val r = QuantityRange(Meters(30), Meters(100))
//    val rIn = QuantityRange(Meters(30), Meters(99))
//    val rInOnEnd = QuantityRange(Meters(30), Meters(100))
//    val rBeyondLower = QuantityRange(Meters(25), Meters(50))
//    val rBeyondUpper = QuantityRange(Meters(50), Meters(110))
//    val rBeyondBoth = QuantityRange(Meters(25), Meters(105))
//    val rBelow = QuantityRange(Meters(10), Meters(20))
//    val rOver = QuantityRange(Meters(105), Meters(205))
//    r.partiallyIncludes(rIn) should be(right = true)
//    r.partiallyIncludes(rInOnEnd) should be(right = true)
//    r.partiallyIncludes(rBeyondLower) should be(right = true)
//    r.partiallyIncludes(rBeyondUpper) should be(right = true)
//    r.partiallyIncludes(rBeyondBoth) should be(right = true)
//    !r.partiallyIncludes(rBelow) should be(right = true)
//    !r.partiallyIncludes(rOver) should be(right = true)
//  }
  
  @Test
  public void shouldPartiallyIncludesAanotherRangeAndReturnTrueOrFalse() {
    QuantityRange<Length> r = new QuantityRange(Meters(30), Meters(100));
    QuantityRange<Length> rIn = new QuantityRange(Meters(30), Meters(99));
    QuantityRange<Length> rInOnEnd = new QuantityRange(Meters(30), Meters(100));
    QuantityRange<Length> rBeyondLower = new QuantityRange(Meters(25), Meters(50));
    QuantityRange<Length> rBeyondUpper = new QuantityRange(Meters(50), Meters(110));
    QuantityRange<Length> rBeyondBoth = new QuantityRange(Meters(25), Meters(105));
    QuantityRange<Length> rBelow = new QuantityRange(Meters(10), Meters(20));
    QuantityRange<Length> rOver = new QuantityRange(Meters(105), Meters(205));
    assertThat(r.partiallyIncludes(rIn), is(true));
    assertThat(r.partiallyIncludes(rInOnEnd), is(true));
    assertThat(r.partiallyIncludes(rBeyondLower), is(true));
    assertThat(r.partiallyIncludes(rBeyondUpper), is(true));
    assertThat(r.partiallyIncludes(rBeyondBoth), is(true));
    assertThat(!r.partiallyIncludes(rBelow), is(true));
    assertThat(!r.partiallyIncludes(rOver), is(true));
  }
//
//  it should "toQuantity and return value representing the difference between the upper and lower bounds" in {
//    val x = Meters(10)
//    val y = Meters(30)
//    val r = QuantityRange(x, y)
//    r.toQuantity should be(Meters(20))
//  }
  
  @Test
  public void shouldToQuantityAndReturnValueRepresentingTheDifferenceBetweenTheUpperAndLowerBounds() {
    Length x = Meters(10);
    Length y = Meters(30);
    QuantityRange r = new QuantityRange(x, y);
    assertThat(r.toQuantity(), is(Meters(20)));
  }
//
//  it should "toSeq and return a list containing the lower and upper bounds" in {
//    val r = QuantityRange(Meters(0), Meters(10))
//    r.toSeq.size should be(2)
//    r.toSeq.head should be(Meters(0))
//    r.toSeq.tail.head should be(Meters(10))
//  }
  
  @Test
  public void shouldToSeqAndReturnAlistContainingTheLowerAndUpperBounds() {
    QuantityRange r = new QuantityRange(Meters(0), Meters(10));
    assertThat(r.toSeq().size(), is(2)); 
    assertThat(r.toSeq().head(), is(Meters(0)));
    assertThat(r.toSeq().tail().head(), is(Meters(10)));
  }
//
//  it should "toList and return a list containing the lower and upper bounds" in {
//    val r = QuantityRange(Meters(0), Meters(10))
//    r.toList.size should be(2)
//    r.toList.head should be(Meters(0))
//    r.toList.tail.head should be(Meters(10))
//  }
  
  @Test
  public void shouldToListAndReturnAlistContainingTheLowerAndUpperBounds() {
    QuantityRange r = new QuantityRange(Meters(0), Meters(10));
    assertThat(r.toList().size(), is(2));
    assertThat(r.toList().get(0), is(Meters(0)));
    assertThat(r.toList().get(r.toList().size()-1), is(Meters(10)));
  }
}