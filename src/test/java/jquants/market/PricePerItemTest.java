package jquants.market;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.Dimensionless.Each;
import static jquants.market.Money.JPY;
import static jquants.market.Money.Money;
import static jquants.market.Money.USD;
import static jquants.space.Area.SquareCentimeters;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

public class PricePerItemTest {

  @Test
  public void testCreatePricePerItemObjectsUsingThePrimaryConstructor() {
    PricePerItem p = new PricePerItem(Money(100, "USD"));
    assertThat(p.base, is(Money(100, "USD")));
    assertThat(p.counter, is(Each(1)));
  }
  
  @Test
  public void testProperlyAddTwoLikePricePerItems() {
    PricePerItem p1 = new PricePerItem(Money(5, "USD"));
    PricePerItem p2 = new PricePerItem(Money(8, "USD"));
    PricePerItem p3 = new PricePerItem(Money(13, "USD"));
    assertThat(p3, is(p1.plus(p2)));
  }

  @Test
  public void testProperlySubtractTwoLikePricePerItems() {
    PricePerItem p1 = new PricePerItem(Money(15, "USD"));
    PricePerItem p2 = new PricePerItem(Money(8, "USD"));
    PricePerItem p3 = new PricePerItem(Money(7, "USD"));
    assertThat(p3, is(p1.minus(p2)));
  }

  @Test
  public void testProperlyMultiplyBADouble() {
    PricePerItem p1 = new PricePerItem(Money(9, "USD"));
    PricePerItem p2 = new PricePerItem(Money(3, "USD"));
    assertThat(p1, is(p2.multiply(3)));
  }
  
  @Test
  public void testProperlyMultiplyBABigDecimal() {
    PricePerItem p1 = new PricePerItem(Money(9, "USD"));
    PricePerItem p2 = new PricePerItem(Money(3, "USD"));
    assertThat(p1, is(p2.multiply(new BigDecimal(3))));
  }
  
  @Test
  public void testProperlyDivideByADouble() {
    PricePerItem p1 = new PricePerItem(Money(9, "USD"));
    PricePerItem p2 = new PricePerItem(Money(3, "USD"));
    assertThat(p2, is(p1.div(3)));
  }
  
  @Test
  public void testProperlyDivideByABigDecimal() {
    PricePerItem p1 = new PricePerItem(Money(9, "USD"));
    PricePerItem p2 = new PricePerItem(Money(3, "USD"));
    assertThat(p2, is(p1.div(new BigDecimal(3))));
  }
  
  @Test
  public void testProperlyDivideByALikePricePerItem() {
    PricePerItem p1 = new PricePerItem(Money(9, "USD"));
    PricePerItem p2 = new PricePerItem(Money(3, "USD"));
    assertThat(new BigDecimal(3), is(p1.div(p2)));
    assertThat(3d, is(p1.div(p2).doubleValue()));
  }
  
  @Test
  public void testReturnMoneyWhenMultipliedByQuantity() {
    PricePerItem p = new PricePerItem(Money(10, "USD"));
    assertThat(Money(10, "USD"), is(p.multiply(Each(1)))); 
  }

  @Test
  public void testReturnQuantityWhenMultipliedByMoney() {
    PricePerItem p = new PricePerItem(Money(10, "USD"));
    assertThat(Each(4), is(p.multiply(Money(40, "USD")))); 
  }
  
  @Test
  public void testReturnProperlyFormattedString() {
    PricePerItem p = new PricePerItem(Money(10.22, "USD"));
    assertThat(p.base.toString() + "/" + p.counter.toString(), is(p.toString()));
  }
  
  
  @Test
  public void testConvertAPricePerItemToADifferentCurrencyWithMoneyContext() {
    MoneyContext.set(USD, Market.defaultCurrencySet, sequence(USD(1).toThe(JPY(100))).toList());
    PricePerItem p = new PricePerItem(Money(10, "USD"));
    assertThat(new PricePerItem(USD(10).in(JPY)), is(p.in(JPY))); 
  }

  @Test
  public void testReturnPrpperlyFormattedStringConvertedToDifferentCurrency() {
    MoneyContext.set(USD, Market.defaultCurrencySet, sequence(USD(1).toThe(JPY(100))).toList());
    PricePerItem p = new PricePerItem(Money(10, "USD"));
    assertThat(p.base.in(JPY).toString() + "/" + p.counter.toString(SquareCentimeters), is(p.toString(JPY, SquareCentimeters))); 
  }
  
  @Test
  public void testGetters(){
    PricePerItem p = new PricePerItem(Money(10, "USD"));
    assertThat(p.getValue(), is(10.0));
  }
}
