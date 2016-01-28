package jquants.market;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.market.Money.JPY;
import static jquants.market.Money.Money;
import static jquants.market.Money.USD;
import static jquants.space.Area.SquareCentimeters;
import static jquants.space.Area.SquareMeters;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

public class PricePerAreaTest {

  @Test
  public void testCreatePricePerAreaObjectsUsingThePrimaryConstructor() {
    PricePerArea p = new PricePerArea(USD(100), SquareMeters(1));
    assertThat(p.base, is(USD(100)));
    assertThat(p.counter, is(SquareMeters(1)));
  }
  
  @Test
  public void testProperlyAddTwoLikePricePerAreas() {
    PricePerArea p1 = new PricePerArea(Money(5, "USD"), SquareMeters(1));
    PricePerArea p2 = new PricePerArea(Money(8, "USD"), SquareMeters(1));
    PricePerArea p3 = new PricePerArea(Money(13, "USD"), SquareMeters(1));
    assertThat(p3, is(p1.plus(p2)));
  }

  @Test
  public void testProperlySubtractTwoLikePricePerAreas() {
    PricePerArea p1 = new PricePerArea(Money(15, "USD"), SquareMeters(1));
    PricePerArea p2 = new PricePerArea(Money(8, "USD"), SquareMeters(1));
    PricePerArea p3 = new PricePerArea(Money(7, "USD"), SquareMeters(1));
    assertThat(p3, is(p1.minus(p2)));
  }

  @Test
  public void testProperlyMultiplyBADouble() {
    PricePerArea p1 = new PricePerArea(Money(9, "USD"), SquareMeters(1));
    PricePerArea p2 = new PricePerArea(Money(3, "USD"), SquareMeters(1));
    assertThat(p1, is(p2.multiply(3)));
  }
  
  @Test
  public void testProperlyMultiplyBABigDecimal() {
    PricePerArea p1 = new PricePerArea(Money(9, "USD"), SquareMeters(1));
    PricePerArea p2 = new PricePerArea(Money(3, "USD"), SquareMeters(1));
    assertThat(p1, is(p2.multiply(new BigDecimal(3))));
  }
  
  @Test
  public void testProperlyDivideByADouble() {
    PricePerArea p1 = new PricePerArea(Money(9, "USD"), SquareMeters(1));
    PricePerArea p2 = new PricePerArea(Money(3, "USD"), SquareMeters(1));
    assertThat(p2, is(p1.div(3)));
  }
  
  @Test
  public void testProperlyDivideByABigDecimal() {
    PricePerArea p1 = new PricePerArea(Money(9, "USD"), SquareMeters(1));
    PricePerArea p2 = new PricePerArea(Money(3, "USD"), SquareMeters(1));
    assertThat(p2, is(p1.div(new BigDecimal(3))));
  }
  
  @Test
  public void testProperlyDivideByALikePricePerArea() {
    PricePerArea p1 = new PricePerArea(Money(9, "USD"), SquareMeters(1));
    PricePerArea p2 = new PricePerArea(Money(3, "USD"), SquareMeters(1));
    assertThat(new BigDecimal(3), is(p1.div(p2)));
    assertThat(3d, is(p1.div(p2).doubleValue()));
  }
  
  @Test
  public void testReturnMoneyWhenMultipliedByQuantity() {
    PricePerArea p = new PricePerArea(Money(10, "USD"), SquareMeters(1));
    assertThat(Money(100, "USD"), is(p.multiply(SquareMeters(10)))); 
  }

  @Test
  public void testReturnQuantityWhenMultipliedByMoney() {
    PricePerArea p = new PricePerArea(Money(10, "USD"), SquareMeters(1));
    assertThat(SquareMeters(4), is(p.multiply(Money(40, "USD")))); 
  }
  
  @Test
  public void testReturnProperlyFormattedString() {
    PricePerArea p = new PricePerArea(Money(10.22, "USD"), SquareMeters(1));
    assertThat(p.base.toString() + "/" + p.counter.toString(), is(p.toString()));
  }
  
  
  @Test
  public void testConvertAPricePerAreaToADifferentCurrencyWithMoneyContext() {
    MoneyContext.set(USD, Market.defaultCurrencySet, sequence(USD(1).toThe(JPY(100))).toList());
    PricePerArea p = new PricePerArea(Money(10, "USD"), SquareMeters(1));
    assertThat(new PricePerArea(USD(10).in(JPY), SquareMeters(1)), is(p.in(JPY))); 
  }

  @Test
  public void testReturnPrpperlyFormattedStringConvertedToDifferentCurrency() {
    MoneyContext.set(USD, Market.defaultCurrencySet, sequence(USD(1).toThe(JPY(100))).toList());
    PricePerArea p = new PricePerArea(Money(10, "USD"), SquareMeters(1));
    assertThat(p.base.in(JPY).toString() + "/" + p.counter.toString(SquareCentimeters), is(p.toString(JPY, SquareCentimeters))); 
  }
  
  @Test
  public void testGetters(){
    PricePerArea p = new PricePerArea(Money(10, "USD"), SquareMeters(1));
    assertThat(p.getValue(), is(10.0));
    assertThat(p.getBase(), is(USD(10)));
    assertThat(p.getArea(), is(SquareMeters(1)));
  }
}
