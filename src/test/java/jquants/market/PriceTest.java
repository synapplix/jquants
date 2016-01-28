package jquants.market;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.market.Money.CAD;
import static jquants.market.Money.EUR;
import static jquants.market.Money.JPY;
import static jquants.market.Money.Money;
import static jquants.market.Money.USD;
import static jquants.mass.Mass.Kilograms;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import jquants.market.Money.QuantityStringParseException;
import jquants.mass.Mass;
import jquants.space.Length;

import org.junit.Test;

import com.googlecode.totallylazy.Sequence;

import static com.googlecode.totallylazy.Pair.*;
import static jquants.space.Length.*;

public class PriceTest {

  @Test
  public void testCreatePriceObjectsUsingThePrimaryConstructor() {
    Price<Length> p = new Price<Length>(Money(100, "USD"), Meters(1));
    assertThat(p.base, is(Money(100, "USD")));
    assertThat(p.counter, is(Meters(1)));
  }
  
  @Test
  public void testProperlyAddTwoLikePrices() {
    Price<Length> p1 = new Price<Length>(Money(5, "USD"), Meters(1));
    Price<Length> p2 = new Price<Length>(Money(8, "USD"), Meters(1));
    Price<Length> p3 = new Price<Length>(Money(13, "USD"), Meters(1));
    assertThat(p3, is(p1.plus(p2)));
  }

  @Test
  public void testProperlySubtractTwoLikePrices() {
    Price<Length> p1 = new Price<Length>(Money(15, "USD"), Meters(1));
    Price<Length> p2 = new Price<Length>(Money(8, "USD"), Meters(1));
    Price<Length> p3 = new Price<Length>(Money(7, "USD"), Meters(1));
    assertThat(p3, is(p1.minus(p2)));
  }

  @Test
  public void testProperlyMultiplyBADouble() {
    Price<Length> p1 = new Price<Length>(Money(9, "USD"), Meters(1));
    Price<Length> p2 = new Price<Length>(Money(3, "USD"), Meters(1));
    assertThat(p1, is(p2.multiply(3)));
  }
  
  @Test
  public void testProperlyMultiplyBABigDecimal() {
    Price<Length> p1 = new Price<Length>(Money(9, "USD"), Meters(1));
    Price<Length> p2 = new Price<Length>(Money(3, "USD"), Meters(1));
    assertThat(p1, is(p2.multiply(new BigDecimal(3))));
  }
  
  @Test
  public void testProperlyDivideByADouble() {
    Price<Length> p1 = new Price<Length>(Money(9, "USD"), Meters(1));
    Price<Length> p2 = new Price<Length>(Money(3, "USD"), Meters(1));
    assertThat(p2, is(p1.div(3)));
  }
  
  @Test
  public void testProperlyDivideByABigDecimal() {
    Price<Length> p1 = new Price<Length>(Money(9, "USD"), Meters(1));
    Price<Length> p2 = new Price<Length>(Money(3, "USD"), Meters(1));
    assertThat(p2, is(p1.div(new BigDecimal(3))));
  }
  
  @Test
  public void testProperlyDivideByALikePrice() {
    Price<Length> p1 = new Price<Length>(Money(9, "USD"), Meters(1));
    Price<Length> p2 = new Price<Length>(Money(3, "USD"), Meters(1));
    assertThat(new BigDecimal(3), is(p1.div(p2)));
    assertThat(3d, is(p1.div(p2).doubleValue()));
  }
  
  @Test
  public void testReturnMoneyWhenMultipliedByQuantity() {
    Price<Length> p = new Price<Length>(Money(10, "USD"), Meters(1));
    assertThat(Money(100, "USD"), is(p.multiply(Meters(10)))); 
  }

  @Test
  public void testReturnQuantityWhenMultipliedByMoney() {
    Price<Length> p = new Price<Length>(Money(10, "USD"), Meters(1));
    assertThat(Meters(4), is(p.multiply(Money(40, "USD")))); 
  }
  
  @Test
  public void testReturnProperlyFormattedString() {
    Price<Length> p = new Price<Length>(Money(10.22, "USD"), Meters(1));
    assertThat(p.base.toString() + "/" + p.counter.toString(), is(p.toString()));
  }
  
  
  @Test
  public void testConvertAPriceToADifferentCurrencyWithMoneyContext() {
    MoneyContext.set(USD, Market.defaultCurrencySet, sequence(USD(1).toThe(JPY(100))).toList());
    Price<Length> p = new Price<Length>(Money(10, "USD"), Meters(1));
    assertThat(new Price<Length>(USD(10).in(JPY), Meters(1)), is(p.in(JPY))); 
  }

  @Test
  public void testReturnPrpperlyFormattedStringConvertedToDifferentCurrency() {
    MoneyContext.set(USD, Market.defaultCurrencySet, sequence(USD(1).toThe(JPY(100))).toList());
    Price<Length> p = new Price<Length>(Money(10, "USD"), Meters(1));
    assertThat(p.base.in(JPY).toString() + "/" + p.counter.toString(Yards), is(p.toString(JPY, Yards))); 
  }
}
