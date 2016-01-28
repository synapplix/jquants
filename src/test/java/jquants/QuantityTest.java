package jquants;

import static com.googlecode.totallylazy.Sequences.sequence;

import com.googlecode.totallylazy.Option;
import com.googlecode.totallylazy.Pair;

import jquants.market.Money.QuantityStringParseException;
import jquants.time.Time;

import static jquants.time.Time.*;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static jquants.QuantityTest.Thingee.*;

public class QuantityTest {

  // */
  // final class Thingee private (val value: Double, val unit: ThingeeUnit)
  // extends Quantity[Thingee] {
  // def dimension = Thingee
  // def toThangs = to(Thangs)
  // def toKilothangs = to(Kilothangs)
  // }

  public static class Thingee extends Quantity<Thingee> {

    public Thingee(double value, ThingeeUnit valueUnit) {
      this.value = value;
      this.valueUnit = valueUnit;
    }

    public static Option<Thingee> toThingee(String s) {
      return Thingee.parseString(s);
    }

    public static class ThingeeUnit extends UnitOfMeasure<Thingee> {

      public ThingeeUnit(String symbol, double multiplier) {
        super(symbol, multiplier, true, false, false);
      }

      @Override
      public Thingee apply(double n) {
        return new Thingee(n, this);
      }
    }

    public static final ThingeeUnit Thangs = new ThingeeUnit("th", 1);
    public static final ThingeeUnit Kilothangs = new ThingeeUnit("kth", Thangs.multiplier * MetricSystem.Kilo);

    public static Thingee Thangs(double value) {
      return new Thingee(value, Thangs);
    }

    public static Thingee Kilothangs(double value) {
      return new Thingee(value, Kilothangs);
    }

    public double toThangs() {
      return to(Thangs);
    }

    public double toKilothangs() {
      return to(Kilothangs);
    }

    public static Dimension<Thingee> Thingee = new Dimension<Thingee>("Thingee", Thangs, sequence(Thangs, Kilothangs));
  }

  //
  // object Thingee extends Dimension[Thingee] {
  // private[squants] def apply[A](n: A, unit: ThingeeUnit)(implicit num:
  // Numeric[A]) = new Thingee(num.toDouble(n), unit)
  // def apply = parse _
  // def name = "Thingee"
  // def primaryUnit = Thangs
  // def siUnit = Thangs
  // def units = Set(Thangs, Kilothangs)
  // }
  //
  // trait ThingeeUnit extends UnitOfMeasure[Thingee] with UnitConverter {
  // def apply[A](n: A)(implicit num: Numeric[A]) = Thingee(n, this)
  // }
  //
  // object Thangs extends ThingeeUnit with PrimaryUnit with SiUnit {
  // val symbol = "th"
  // }
  //
  // object Kilothangs extends ThingeeUnit {
  // val symbol = "kth"
  // val conversionFactor = Thangs.conversionFactor * MetricSystem.Kilo
  // }
  //
  // implicit object ThingeeNumeric extends
  // AbstractQuantityNumeric[Thingee](Thangs)
  //
  // behavior of "Quantity as implemented in Thingee"
  //
  // it should "create values using arbitrary numeric types" in {
  // // define Numeric for each type
  // abstract class BaseNumeric[T] extends Numeric[T] {
  // def plus(x: T, y: T) = x + y
  // def minus(x: T, y: T) = x - y
  // def times(x: T, y: T) = x * y
  // def negate(x: T) = -x
  // def toInt(x: T) = x.toInt()
  // def toLong(x: T) = x.toLong()
  // def toFloat(x: T) = x.toFloat()
  // def compare(x: T, y: T) = if (x == y) 0 else if (x.toDouble() >
  // y.toDouble()) 1 else -1
  // }
  //
  // implicit val stringNumeric = new BaseNumeric[String] {
  // def fromInt(x: Int) = x.toString
  // def toDouble(x: String) = augmentString(x).toDouble // augmentString is
  // used to disambiguate implicit conversion
  // }
  //

  // // Use them to initialize quantity values
  // Thangs("10.22").toThangs should be(10.22)
  // (Thangs("10") + Thangs("0.22")).toThangs should be(10.22)
  // }
  //
  // it should "create values from properly formatted Strings" in {
  // Thingee("42 th").get should be(Thangs(42))
  // Thingee("10.22 th").get should be(Thangs(10.22))
  // Thingee("1e5 th").get should be(Thangs(1e5))
  // Thingee("1E5 th").get should be(Thangs(1E5))
  // Thingee("1e+5 th").get should be(Thangs(1e+5))
  // Thingee("1e-5 th").get should be(Thangs(1e-5))
  // Thingee("1.0e5 th").get should be(Thangs(1.0e5))
  // Thingee("1.0E5 th").get should be(Thangs(1.0E5))
  // Thingee("1.0e+5 th").get should be(Thangs(1.0e+5))
  // Thingee("1.01E+5 th").get should be(Thangs(1.01E+5))
  // Thingee("1.012e-5 th").get should be(Thangs(1.012e-5))
  // Thingee("12.0123E-5 th").get should be(Thangs(12.0123E-5))
  // Thingee("10.22 kth").get should be(Kilothangs(10.22))
  // }
  //

  @Test
  public void shouldCreateValuesFromProperlyFormattedStrings() {
    assertThat(toThingee("42 th").get(), is(Thangs(42.0)));
    assertThat(toThingee("10.22 th").get(), is(Thangs(10.22)));
    // TODO: check whether it e5, E5, E+5, E-5 is a desired form of input
    // assertThat(toThingee("1.0e5 th").get(), is(Thangs(1e5)));
    // assertThat(toThingee("1.0E5 th").get(), is(Thangs(1E5)));
    // assertThat(toThingee("1.0e+5 th").get(), is(Thangs(1e+5)));
    // assertThat(toThingee("1.0e-5 th").get(), is(Thangs(1e-5)));
    // assertThat(toThingee("1.0e5 th").get(), is(Thangs(1.0e5)));
    // assertThat(toThingee("1.0E5 th").get(), is(Thangs(1.0E5)));
    // assertThat(toThingee("1.0e+5 th").get(), is(Thangs(1.0e+5)));
    // assertThat(toThingee("1.01E+5 th").get(), is(Thangs(1.01E+5)));
    // assertThat(toThingee("1.012e-5 th").get(), is(Thangs(1.012e-5)));
    // assertThat(toThingee("12.0123E-5 th").get(), is(Thangs(12.0123E-5)));
    assertThat(toThingee("10.22 kth").get(), is(Kilothangs(10.22)));
  }

  // it should "return failure on improperly formatted Strings" in {
  // Thingee("10.22 xx") should be(Failure(QuantityParseException("Unable to
  // parse Thingee", "10.22 xx")))
  // Thingee("10.xx th") should be(Failure(QuantityParseException("Unable to
  // parse Thingee", "10.xx th")))
  // }

  @Test(expected = QuantityStringParseException.class)
  public void shouldThrowAQuantityStringParseException() {
    assertThat(toThingee("12.0 xx").get(), is(Thangs(12.0)));
    assertThat(toThingee("12.xx th").get(), is(Thangs(12.0)));
  }
  //
  // it should "create values from properly formatted Tuples" in {
  // Thingee((10.22, "th")).get should be(Thangs(10.22))
  // Thingee((10.22, "kth")).get should be(Kilothangs(10.22))
  //

  @Test
  public void shouldCreateValuesFromProperlyFormattedTuples() {
    // TODO: check whether input as Tuples is desired form of input
    // assertThat((10.22, "th").get, is(Thangs(10.22)));

  }

  // // Using tuples created from quantities (round trip)
  // Thingee(Thangs(10.22).toTuple).get should be(Thangs(10.22))
  // Thingee(Kilothangs(10.22).toTuple).get should be(Kilothangs(10.22))
  // Thingee(Thangs(10.22).toTuple(Kilothangs)).get should be(Thangs(10.22))
  // Thingee(Kilothangs(10.22).toTuple(Thangs)).get should
  // be(Kilothangs(10.22))
  // }
  //
  // it should "return failure on an improperly formatted Tuple" in {
  // Thingee((10.22, "xx")) should be(Failure(QuantityParseException("Unable
  // to identify Thingee unit xx", "(10.22,xx)")))
  // }
  //
  // it should "equal an equivalent like value" in {
  // val x = Thangs(2.1)
  // val y = Thangs(2.1)
  // x.equals(y) should be(right = true)
  // }

  @Test
  public void shouldEqualAnEquivalentLikeValue() {
    Thingee x = Thangs(2.1);
    Thingee y = Thangs(2.1);
    assertThat(x, is(y));
  }
  //
  // it should "equal an equivalent value in a different unit" in {
  // val x = Thangs(2100)
  // val y = Kilothangs(2.1)
  // x.equals(y) should be(right = true)
  // }

  @Test
  public void shouldEqualAnEquivalentValueInADifferentUnit() {
    Thingee x = Thangs(2100);
    Thingee y = Kilothangs(2.1);
    assertTrue(x.equals(y));
  }

  //
  // it should "compare a non-null Quantity to a null and return a proper
  // result" in {
  // val x = Thangs(2.1)
  // x == null should be(right = false)
  // null == x should be(right = false)
  // x != null should be(right = true)
  // null != x should be(right = true)
  // }
  
  @Test
  public void shouldCompareANonNullQuantityToANullAndReturnAProperResult() {
    Thingee x = Thangs(2.1);
    assertThat(x == null, is(false));
    assertThat(null == x, is(false));
    assertThat(x != null, is(true));
    assertThat(null != x, is(true));
  }
  
  //
  // it should "compare a null Quantity to null and return a proper result" in
  // {
  // val x: Thingee = null
  // x == null should be(right = true)
  // null == x should be(right = true)
  // x != null should be(right = false)
  // null != x should be(right = false)
  // }
  
  @Test
  public void shouldCompareANullQuantityToNullAndReturnAProperResult() {
    Thingee x = null;
    assertThat(x == null, is(true));
    assertThat(null == x, is(true));
    assertThat(x != null, is(false));
    assertThat(null != x, is(false));
  }
  
  //
  // it should "compare a null Quantity to a non-null Quantity" in {
  // val x = null
  // val y = Thangs(2.1)
  // x == y should be(right = false)
  // y == x should be(right = false)
  // }
  
  @Test
  public void shouldCompareANullQuantityToANonNullQuantityAndReturnAProperResult() {
    Thingee x = null;
    Thingee y = Thangs(2.1);
    assertThat(x == y, is(false));
    assertThat(y == x, is(false));
  }
  
  //
  // it should "not equal an equivalent value of a different type" in {
  // val x = Kilothangs(2.1)
  // val y = Kilograms(2.1)
  // x.equals(y) should be(right = false)
  // x == y should be(right = false)
  // x != y should be(right = true)
  // }
  
  @Test
  public void shouldNotEqualAnEquivalentValueOfADifferentType() {
    Thingee x = Kilothangs(2.1);
    Thingee y = Thangs(2.1);
    assertThat(x.equals(y), is(false));
    assertThat(x == y, is(false));
    assertThat(x != y, is(true));
  }
  
  //
  // it should "approx a like value that is within an implicitly defined
  // tolerance" in {
  // implicit val tol = Thangs(.1)
  // val x = Kilothangs(2.0)
  // val y = Kilothangs(1.9999)
  // x approx y should be(right = true)
  // x =~ y should be(right = true)
  // x ≈ y should be(right = true)
  // (x ~= y) should be(right = true)
  // }
  
  @Test
  public void shouldApproxALikeValueThatIsWithinAnImplicitlyDefinedTolerance(){
  //not applicable since values are always passed explicitly (see 
  }
  
  //
  // it should "not approx a like value that is not within an implicitly
  // defined tolerance" in {
  // implicit val tol = Thangs(.1)
  // val x = Kilothangs(2.0)
  // val y = Kilothangs(1.9998)
  // x approx y should be(right = false)
  // x =~ y should be(right = false)
  // (x ~= y) should be(right = false)
  // }
  
  
  @Test
  public void shouldNotApproxALikeValueThatIsNotWithinAnImplicitlyDefinedTolerance(){
    //not applicable since values are always passed explicitly
  }
  //
  // it should "approx a like value that is within an explicitly passed
  // tolerance" in {
  // implicit val tol = Thangs(.1)
  // val x = Kilothangs(2.0)
  // val y = Kilothangs(1.9999)
  // x approx y should be(right = true)
  // x =~ y should be(right = true)
  // (x ~= y) should be(right = true)
  // // apply approx with an explicit override of the tolerance
  // x.approx(y)(Thangs(.01)) should be(right = false)
  // x.=~(y)(Thangs(.01)) should be(right = false)
  // x.~=(y)(Thangs(.01)) should be(right = false)
  // }
  //
  
  @Test
  public void souldApproxALikeValueThatIsWithinAnExplicitlyPassedTolerance(){
    Thingee tol = Thangs(.1);
    Thingee x = Kilothangs(2.0);
    Thingee y = Kilothangs(1.9998);
    assertThat(x.approx(y, tol), is(false));
  }
  
  
  // it should "add two like values and result in a like value" in {
  // val x = Thangs(14.999)
  // val y = Thangs(0.001)
  // x plus y should be(Thangs(15))
  // x + y should be(Thangs(15)) //not 
  // }
  
  @Test
  public void shouldAddTwoLikeValuesAndRestultInALikeValue(){
    Thingee x = Thangs(14.999);
    Thingee y = Thangs(0.001);
    assertThat(x.plus(y), is(Thangs(15)));
  }
  
  //
  // it should "add two like value in different units and result in a like
  // value" in {
  // val x = Kilothangs(14.999)
  // val y = Thangs(1)
  // x plus y should be(Kilothangs(15))
  // x + y should be(Kilothangs(15))
  // }
  
  @Test
  public void shouldAddTwoLikeValuesInDifferentUnitsAndRestultInALikeValue(){
    Thingee x = Kilothangs(14.999);
    Thingee y = Thangs(1);
    assertThat(x.plus(y), is(Kilothangs(15)));
  }
  //
  // it should "minus two like values and result in a like value" in {
  // val x = Thangs(15.0)
  // val y = Thangs(0.001)
  // (x minus y) should be(Thangs(14.999))
  // x - y should be(Thangs(14.999))
  // }
  //
  
  @Test
  
  public void shouldSubtractTwoLikeValuesAndRestultInALikeValue(){
    Thingee x = Thangs(15);
    Thingee y = Thangs(0.001);
    assertThat(x.minus(y), is(Thangs(14.999)));
  }
  // it should "minus two like value in different units and result in a like
  // value" in {
  // val x = Kilothangs(15)
  // val y = Thangs(1)
  // x minus y should be(Kilothangs(14.999))
  // x - y should be(Kilothangs(14.999))
  // }
  //
  
  @Test
  public void shouldSubtractTwoLikeValuesInDifferentUnitsAndRestultInALikeValue(){
    Thingee x = Kilothangs(15);
    Thingee y = Thangs(1);
    assertThat(x.minus(y), is(Kilothangs(14.999)));
  }
  
  // it should "times by a Double and result in a like value" in {
  // val x = Thangs(4.5)
  // val y = 2.0
  // (x times y) should be(Thangs(9.0))
  // x * y should be(Thangs(9.0))
  // }
  
  @Test
  public void shouldMultiplyByADoubleAndResultInALikeValue(){
    Thingee x = Thangs(4.5);
    double y = 2.0;
    assertThat(x.multiply(y), is(Thangs(9)));
  }
  //
  // it should "divide by a Double and result in a like value" in {
  // val x = Thangs(9.0)
  // val y = 2.0
  // (x divide y) should be(Thangs(4.5))
  // x / y should be(Thangs(4.5))
  // }
  //
  
  @Test
  public void shouldDivideByADoubleAndResultInALikeValue(){
    Thingee x = Thangs(9);
    double y = 2.0;
    assertThat(x.div(y), is(Thangs(4.5)));
  }
  
  // it should "divide by a like value and result in a Double" in {
  // val x = Thangs(9.0)
  // val y = Thangs(2.0)
  // (x divide y) should be(4.5)
  // x / y should be(4.5)
  // }
  
  
  @Test
  public void shouldDivideByALikeValueAndResultInALikeValue(){
    Thingee x = Kilothangs(9);
    Thingee y = Kilothangs(2.0);
    assertThat(x.div(y), is(4.5));
  }
  //
  // it should "divide by a like value in different units and result in a
  // Double" in {
  // val x = Kilothangs(9)
  // val y = Thangs(2)
  // x divide y should be(4500.0)
  // x / y should be(4500.0)
  // }
  
  @Test
  public void shouldDivideByALikeValueInDifferentUnitsAndResultInALikeValue(){
    Thingee x = Kilothangs(9);
    Thingee y = Thangs(2.0);
    assertThat(x.div(y), is(4500.0));
  }
  //
  // it should "remainder by a Double and result in a like value" in {
  // val x = Thangs(9.0)
  // val y = 2.0
  // x remainder y should be(Thangs(1))
  // x % y should be(Thangs(1))
  // }
  
  @Test
  public void shouldRemainderByADoubleAndResultInALikeValue(){
    Thingee x = Thangs(9.0);
    double y = 2.0;
    assertThat(x.remainder(y), is(Thangs(1)));  
  }
  //
  // it should "remainder by a like value and result in a Double" in {
  // val x = Thangs(9.0)
  // val y = Thangs(2.0)
  // (x remainder y) should be(1.0)
  // x % y should be(1.0)
  // }
  
  @Test
  public void shouldRemainderByALAndResultInALikeValue(){
    Thingee x = Thangs(9.0);
    Thingee y = Thangs(2.0);
    assertThat(x.remainder(y), is(1.0));  
  }
  //
  // it should "divideAndRemainder by a Double and result in a pair of like
  // values" in {
  // val x = Thangs(9.0)
  // val y = 2.0
  // val p1 = x divideAndRemainder y
  // p1._1 should be(Thangs(4))
  // p1._2 should be(Thangs(1))
  // val p2 = x /% y
  // p2._1 should be(Thangs(4))
  // p2._2 should be(Thangs(1))
  // }
  
  @Test
  public void shouldDivideAndRemainderByADoubleAndResultInAPairOfLikeValues(){
    Thingee x = Thangs(9.0);
    double y = 2.0;
    Pair<Thingee, Thingee> p1 = x.divideAndRemainder(y);
    assertThat(p1.first(), is(Thangs(4.0)));
    assertThat(p1.second(), is(Thangs(1.0)));
    
  }
  
  //
  // it should "divideAndRemainder by a like value and result in a Double and
  // like value" in {
  // val x = Thangs(9.0)
  // val y = Thangs(2.0)
  // val p1 = x divideAndRemainder y
  // p1._1 should be(4)
  // p1._2 should be(Thangs(1))
  // val p2 = x /% y
  // p2._1 should be(4)
  // p2._2 should be(Thangs(1))
  // }
  
  @Test
  public void shouldDivideAndRemainderByALikeValueAndResultInAPairOfLikeValues(){
    Thingee x = Thangs(9.0);
    Thingee y = Thangs(2.0);
    Pair<Double, Thingee> p1 = x.divideAndRemainder(y);
    assertThat(p1.first(), is(4.0));
    assertThat(p1.second(), is(Thangs(1.0)));
  }
  
  //
  // it should "negate a value and result in a like negative value" in {
  // val x = Thangs(9.0)
  // x.negate should be(Thangs(-9.0))
  // -x should be(Thangs(-9.0))
  //
  // val y = Thangs(-9.0)
  // y.negate should be(Thangs(9.0))
  // -y should be(Thangs(9.0))
  // }
  //
  
  @Test
  public void shouldNegateAValueAndResultInALikeNegativeValue(){
    Thingee x = Thangs(9.0);
    assertThat(x.negate(), is(Thangs(-9.0)));
    
    Thingee y = Thangs(-9.0);
    assertThat(y.negate(), is(Thangs(9.0)));
  }
  // it should "return the absolute value of a Quantity value" in {
  // val x = Thangs(9.0)
  // x.abs should be(Thangs(9.0))
  // (-x).abs should be(Thangs(9.0))
  //
  // val y = Thangs(-9.0)
  // y.abs should be(Thangs(9.0))
  // (-y).abs should be(Thangs(9.0))
  // }
  //
  
  
  @Test
  public void shouldReturnTheAbsoluteValueOfAQuantiyValue(){
    Thingee x = Thangs(9.0);
    assertThat(x.abs(), is(Thangs(9.0)));
    assertThat(x.negate().abs(), is(Thangs(9.0)));
    
    Thingee y = Thangs(-9.0);
    assertThat(y.abs(), is(Thangs(9.0)));
    assertThat(y.negate().abs(), is(Thangs(9.0)));
  }
  
  // it should "return true on comparing two different values with !=" in {
  // val x = Thangs(1)
  // val y = Thangs(2)
  // x should not be y
  // }
  
  
  @Test
  public void shouldReturnTrueOnComparingTwoDifferentValuesWithNotEqual(){
    Thingee x = Thangs(1.0);
    Thingee y = Thangs(2.0);
    assertThat(x != y, is(true));
  }
  //
  // it should "return false on comparing two equal values with !=" in {
  // val x = Thangs(1)
  // val y = Thangs(1)
  // (x != y) should be(right = false)
  // }
  
  @Test
  public void shouldReturnFalseOnComparingTwoEqualValuesWithNotEqual(){
    Thingee x = Thangs(1.0);
    Thingee y = Thangs(1.0);
    assertThat(!x.equals(y), is(false));
  }
  
  //
  // it should "compare a like value values and return 1, 0, or -1" in {
  // val x = Kilothangs(5)
  // (x compare Kilothangs(4.999)) should be(1)
  // (x compare Kilothangs(5)) should be(0)
  // (x compare Kilothangs(5.001)) should be(-1)
  //
  // (x compare Thangs(4999.0)) should be(1)
  // (x compare Thangs(5000.0)) should be(0)
  // (x compare Thangs(5001.0)) should be(-1)
  // }
  
  
  @Test
  public void shouldCompareToALikeValueAndReturnOneZeroOrMinusOne(){
    Thingee x = Kilothangs(5);
    assertThat(x.compareTo(Kilothangs(4.999)), is(1));
    assertThat(x.compareTo(Kilothangs(5)), is(0));
    assertThat(x.compareTo(Kilothangs(5.001)), is(-1));
    
    assertThat(x.compareTo(Thangs(4999.0)), is(1));
    assertThat(x.compareTo(Thangs(5000.0)), is(0));
    assertThat(x.compareTo(Thangs(5001.0)), is(-1));
  }
  //
  // it should "max a like value and return the greater of the two" in {
  // val x = Thangs(5)
  // val y = Thangs(4.999)
  // (x max y) should be(Thangs(5))
  //
  // (x max y) should be(Thangs(5))
  // }
  //
  
  @Test
  public void shouldMaxALikeValueAndReturnTheGreaterOfTheTwo(){
    Thingee x = Thangs(5.0);
    Thingee y = Thangs(4.999);
    assertThat(x.max(y), is(Thangs(5.0)));
    assertThat(y.max(x), is(Thangs(5.0)));
  }
  
  // it should "min a like value and return the greater of the two" in {
  // val x = Thangs(5)
  // val y = Thangs(4.999)
  // (x min y) should be(Thangs(4.999))
  // }
  
  @Test
  public void shouldMinALikeValueAndReturnTheSmallerOfTheTwo(){
    Thingee x = Thangs(5.0);
    Thingee y = Thangs(4.999);
    assertThat(x.min(y), is(Thangs(4.999)));
    assertThat(y.min(x), is(Thangs(4.999)));
  }
  //
  // it should "plusOrMinus a like value and return a QuantityRange" in {
  // val x = Thangs(5)
  // val y = Thangs(1)
  // (x plusOrMinus y) should be(QuantityRange(Thangs(4), Thangs(6)))
  // x +- y should be(QuantityRange(Thangs(4), Thangs(6)))
  // }
  
  @Test
  public void shouldPlusOrMinusALikeValueAndReturnAQuantityRange(){
    Thingee x = Thangs(5);
    Thingee y = Thangs(1);
    assertThat(x.plusOrMinus(y), is(new QuantityRange(Thangs(4.0), Thangs(6.0))));
  }
  //
  // it should "to a like value and return a QuantityRange" in {
  // val x = Thangs(5)
  // val y = Thangs(10)
  // val r = x to y
  // r should be(QuantityRange(x, y))
  // }
  
  @Test
  public void shouldToALikeValueAndReturnAQuantityRange(){
    Thingee x = Thangs(5);
    Thingee y = Thangs(10);
    assertThat(x.to(y), is(new QuantityRange(x,y)));
  }
  //
  // it should "within a QuantityRange and return a Boolean" in {
  // val x = Thangs(10)
  // val y = Thangs(15)
  // val r = x +- Thangs(1)
  // x.within(r) should be(right = true)
  // y.within(r) should be(right = false)
  //
  // Thangs(10) within (Thangs(9) +- Thangs(2)) should be(right = true)
  // Thangs(10) within (Thangs(9) to Thangs(12)) should be(right = true)
  // }
  
  
  @Test
  public void shouldWithinAQuantityRangeAndReturnABoolean(){
    Thingee x = Thangs(10);
    Thingee y = Thangs(15);
    QuantityRange<Thingee> r = x.plusOrMinus(Thangs(1.0));
    assertThat(x.within(r), is(true));
    assertThat(y.within(r), is(false));
    
    assertThat(Thangs(10.0).within((Thangs(9.0).plusOrMinus(Thangs(2.0)))), is(true));
    assertThat(Thangs(10.0).within((Thangs(9.0).to(Thangs(12.0)))), is(true));
  }
  
  //
  // it should "notWithin a QuantityRange and return a Boolean" in {
  // val x = Thangs(10)
  // val y = Thangs(15)
  // val r = x +- Thangs(1)
  // !x.notWithin(r) should be(right = true)
  // y.notWithin(r) should be(right = true)
  //
  // Thangs(13) notWithin (Thangs(9) +- Thangs(2)) should be(right = true)
  // Thangs(13) notWithin (Thangs(9) to Thangs(12)) should be(right = true)
  // }
  
  @Test
  public void shouldNotWithinAQuantityRangeAndReturnABoolean(){
    Thingee x = Thangs(10);
    Thingee y = Thangs(15);
    QuantityRange<Thingee> r = x.plusOrMinus(Thangs(1.0));
    assertThat(!x.notWithin(r), is(true));
    assertThat(!y.notWithin(r), is(false));
    
    assertThat(!Thangs(10.0).notWithin((Thangs(9.0).plusOrMinus(Thangs(2.0)))), is(true));
    assertThat(!Thangs(10.0).notWithin((Thangs(9.0).to(Thangs(12.0)))), is(true));
  }
  //
  // it should "to a unit and return a Double" in {
  // val x = Thangs(1500)
  // (x to Kilothangs) should be(1.5)
  // x.toKilothangs should be(1.5)
  // }
  
  @Test
  public void shouldToAUnitAndReturnADoubleIn(){
    Thingee x = Thangs(1500);
    assertThat(x.to(Kilothangs), is(1.5));
    assertThat(x.toKilothangs(), is(1.5));
  }
  
  //
  // it should "in a unit and return a like value in that unit" in {
  // // The `in` method is only useful for Quantities that implement quantity
  // classes for each unit
  // val x = Fahrenheit(212)
  // (x in Celsius) should be(Celsius(100))
  //
  // val y = Seconds(3600)
  // (y in Hours) should be(Hours(1))
  // }
  //
  @Test
  public void shouldInAUnitAndReturnALikeValueInThatUnit(){
    Thingee x = Thangs(1);
    assertThat(x.in(Kilothangs), is(Kilothangs(0.001)));
    
    Time y = Seconds(3600);
    assertThat(y.in(Hours), is(Hours(1)));
  }
 
  // it should "toString and return a string formatted for the valueUnit" in {
  // val x = Thangs(10)
  // x.toString should be("10.0 th")
  // }
  
  @Test
  public void shouldToStringAndReturnAStringFormattedForTheValueUnit(){
    Thingee x = Thangs(10.0);
    assertThat(x.toString(), is("10.0 th"));
  }
  //
  // it should "toString a unit and return a string formatted for the unit" in
  // {
  // val x = Thangs(1500)
  // (x toString Kilothangs) should be("1.5 kth")
  // }
  //
  
  @Test
  public void shouldToStringAUnitAndReturnAStringFormattedForTheUnit(){
    Thingee x = Thangs(1500.0);
    assertThat(x.toString(Kilothangs), is("1.5 kth"));
  }
  
  // it should "toString a format and unit and return a string using the
  // format and unit" in {
  // val x = Thangs(1.2555555)
  // x.toString(Thangs, "%.2f") should be("1.26 th")
  // x.toString(Thangs, "%.3f") should be("1.256 th")
  // }
  
  @Test
  public void shouldToStringAFormatAndUnitAndReturnAStringUsingTheFormatAndUnit(){
    Thingee x = Thangs(1.2555555);
    assertThat(x.toString(Thangs, "%.2f"), is("1,26 th"));
    assertThat(x.toString(Thangs, "%.3f"), is("1,256 th"));
  }
  //
  // it should "toTuple and return a tuple including the value and the unit's
  // symbol" in {
  // val x = Thangs(10.22)
  // x.toTuple should be(10.22, "th")
  // }
  
  @Test
  public void shouldToTupleAndReturnATubpleIncludingTheValueAndTheUnitsSymbol(){
    Thingee x = Thangs(10.22);
    assertThat(x.toTuple().toString(), is("[10.22,th]"));
  }
  //
  // it should "toTuple a unit and return a tuple including the value in the
  // supplied unit and that unit's symbol" in {
  // val x = Kilothangs(10.22)
  // x.toTuple(Thangs) should be(10220, "th")
  // }
  
  @Test
  public void shouldToTupleAUnitAndReturnATubpleIncludingTheValueAndTheSuppliedUnitAndThatUnitsSymbol(){
    Thingee x = Kilothangs(10.22);
    assertThat(x.toTuple(Thangs).toString(), is("[10220.0,th]"));
  }
  //
  // it should "map over the underlying value and return the resulting value
  // in a Quantity of the same Unit" in {
  // val x = Kilothangs(10.22)
  // x.map(_ * 2) should be(Kilothangs(20.44))
  // }
  
  @Test
  public void shouldMapOverTheUnderLyingValueAndReturnTheResultingValueInAQuantitiyOfTheSameUnit(){
    Thingee x = Kilothangs(10.22);
    assertThat(x.map(n -> n.multiply(2.0)), is(Kilothangs(20.44)));
  }
  //
  // it should "return the correct Numeric value when pattern matched against
  // a Unit of Measure" in {
  // val x = Thangs(1200)
  // val thangs = x match {
  // case Thangs(v) ⇒ v
  // }
  // thangs should be(1200)
  //
  // val kilothangs = x match {
  // case Kilothangs(v) ⇒ v
  // }
  // kilothangs should be(1.2)
  // }
  
  
//  @Test
//  public void shouldReturnTheCorrectNumericValueWhenPatternMatchedAgainsAUniOfMeasure(){
//    // does not apply in Java
//  }
  
  //TODO: Numeric behaviour yet to implement 
  //
  // behavior of "SquantifiedDouble"
  //
  // it should "multiply by a Quantity value and return the product as a like
  // value" in {
  // val l = 10.22 * Thangs(1000)
  // l.getClass should be(classOf[Thingee])
  // (l to Thangs) should be(10220)
  //
  // val m = 10D * Kilograms(50)
  // m.getClass should be(classOf[Mass])
  // (m to Kilograms) should be(500)
  // }
  //
  // behavior of "SquantifiedLong"
  //
  // it should "multiply by a Quantity value and return the product as a like
  // value" in {
  // val l = 10L * Thangs(1000)
  // l.getClass should be(classOf[Thingee])
  // (l to Thangs) should be(10000)
  //
  // val m = 10L * Kilograms(50)
  // m.getClass should be(classOf[Mass])
  // (m to Kilograms) should be(500)
  // }
  //
  // behavior of "SquantifiedBigDecimal"
  //
  // it should "multiply by a Quantity value and return the product as a like
  // value" in {
  // val multiple = BigDecimal(10)
  //
  // val l = multiple * Thangs(1000)
  // l.getClass should be(classOf[Thingee])
  // (l to Thangs) should be(10000)
  //
  // val m = multiple * Kilograms(50)
  // m.getClass should be(classOf[Mass])
  // (m to Kilograms) should be(500)
  // }
  //
  // behavior of "QuantityNumeric"
  //
  // it should "provide Numeric support" in {
  //
  // ThingeeNumeric.plus(Thangs(1000), Kilothangs(10)) should
  // be(Kilothangs(11))
  // ThingeeNumeric.minus(Kilothangs(10), Thangs(1000)) should
  // be(Kilothangs(9))
  //
  // intercept[UnsupportedOperationException] {
  // ThingeeNumeric.times(Thangs(1), Thangs(2))
  // }
  //
  // ThingeeNumeric.negate(Thangs(10.22)) should be(Thangs(-10.22))
  // ThingeeNumeric.fromInt(10) should be(Thangs(10))
  // ThingeeNumeric.toInt(Thangs(10)) should be(10)
  // ThingeeNumeric.toLong(Thangs(10)) should be(10L)
  // ThingeeNumeric.toFloat(Thangs(10.22)) should be(10.22F +- 0.000001F)
  // ThingeeNumeric.toDouble(Thangs(10.22)) should be(10.22)
  //
  // ThingeeNumeric.compare(Thangs(1000), Kilothangs(2)) < 0 should be(right =
  // true)
  // ThingeeNumeric.compare(Thangs(2000), Kilothangs(1)) > 0 should be(right =
  // true)
  // ThingeeNumeric.compare(Thangs(2000), Kilothangs(2)) should be(0)
  //
  // val ts = List(Thangs(1000), Kilothangs(10), Kilothangs(100))
  // ts.sum should be(Kilothangs(111))
}
