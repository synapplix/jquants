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

import org.junit.Test;

import com.googlecode.totallylazy.Sequence;
import static com.googlecode.totallylazy.Pair.*;

public class MoneyTest {

  @Test
  public void testCreateValuesUsingFactoriesThatTakeCurrency() {
    assertThat(Money(new BigDecimal(10), USD), is(Money(10, USD)));
    assertThat(Money(10, USD), is(Money(10, USD)));
  }
  
  @Test
  public void testCreateValuesUsingFactoriesThatTakeCurrencyCode() {
    assertThat(Money(new BigDecimal(10), "USD") , is(Money(10, USD)));
    assertThat(Money(10, "USD"), is(Money(10, USD)));
  }
  
  @Test
  public void testCreateValuesUsingCurrencyFactories() {
    // create values using Currency (UOM) factories
    assertThat(USD(new BigDecimal(10)), is(Money(10, USD)));
    assertThat(USD(10), is(Money(10, USD)));
  }
  
  @Test
  public void testCreateValuesUsingNoCurrency() {
    // create values using 'no currency' factories with an implicit MoneyContext in scope
    MoneyContext.set(USD, Market.defaultCurrencySet, null);
    assertThat(Money(new BigDecimal(10)), is(USD(10)));
    assertThat(Money(10), is(USD(10)));
    MoneyContext.remove(); 
  }
  
  @Test
  public void testCreateValuesFromFormattedString() {
    // create values from formatted strings
    assertThat(Money("500 USD"), is(USD(500)));
    assertThat(Money("500USD"), is(USD(500)));
    assertThat(Money("5.50USD"), is(USD(5.5)));
    assertThat(Money("500 EUR"), is(EUR(500)));
    assertThat(Money("10000.0 JPY"), is(JPY(10000)));
    assertThat(Money("23.45 CAD"), is(CAD(23.45)));
    try {
      Money("23.45 ZZZ");
      fail("Expected exception");
    } catch(QuantityStringParseException e) {
      assertThat(e.getMessage(), is(equalTo("Unable to parse Money 23.45 ZZZ")));
    }
  }
  
  @Test
  public void testReturnProperResultWhenComparingLikeCurrencies() {

    assertThat(USD(10), is(USD(10)));
    assertThat(USD(10).equals(USD(10)), is(equalTo(true)));
    assertThat(!USD(10).equals(USD(9.99)), is(equalTo(true)));
    assertThat(USD(10).equals(USD(9.99)), is(equalTo(false)));
    assertThat(!USD(10).equals(USD(10)), is(equalTo(false)));
    
    assertThat(USD(10).compareTo(USD(9.99)) > 0, is(equalTo(true)));
    assertThat(USD(10).compareTo(USD(9.99)) >= 0, is(equalTo(true)));
    assertThat(USD(10).compareTo(USD(10)) >= 0, is(equalTo(true)));

    assertThat(USD(9.99).compareTo(USD(10)) < 0, is(equalTo(true)));
    assertThat(USD(9.99).compareTo(USD(10)) <= 0, is(equalTo(true)));
    assertThat(USD(10).compareTo(USD(10)) <= 0, is(equalTo(true)));
  }
  
  @Test
  public void testReturnProperResultWhenComparingDislikeCurrenciesWithNoMoneyContext() {
    assertThat(USD(10).equals(JPY(10)), is(equalTo(false)));
    assertThat(!USD(10).equals(JPY(10)), is(equalTo(true)));
  }
  
  
  @Test
  public void testCompareNonNullQuantityToANullAndReturnProperResult() {
    Money x = USD(2.1);
    
    assertThat(x == null, is(false));
    assertThat(null == x, is(false));
    assertThat(x != null, is(true));
    assertThat(null != x, is(true));
  }
  
  @Test
  public void testComparNullQuantityToNullAndReturnProperResult() {
    Money x = null;
    
    assertThat(x == null, is(true));
    assertThat(null == x, is(true));
    assertThat(x != null, is(false));
    assertThat(null != x, is(false));
  }
  
  @Test
  public void testComparANullQuantityToANonNullQuantity() {
    Money x = null;
    Money y = USD(2.1);

    assertThat(x == y, is(false));
    assertThat(y == x, is(false));
  }
  
  @Test
  public void testNotEqualAnEquivalentValueOfADifferentType() {
    Money x = USD(2.1);
    Mass y = Kilograms(2.1);
    
    assertThat(x.equals(y), is(false));
    assertThat(!x.equals(y), is(true));
  }
  
  @Test
  public void testProperResultOnMinMaxOperationsWithImplicitMoneyContext() {
    CurrencyExchangeRate r1 = new CurrencyExchangeRate(USD(1), JPY(100));
    CurrencyExchangeRate r2 = new CurrencyExchangeRate(USD(1), EUR(.75));
    Sequence<CurrencyExchangeRate> rates = sequence(r1, r2);

    MoneyContext.set(USD, Market.defaultCurrencySet, rates.toList());
    
    Money x = USD(100);
    Money y = JPY(100);
    assertThat(x.max(y), is(x));
    assertThat(y.min(x), is(y));
    
    MoneyContext.remove();    
  }
  
  @Test
  public void testReturnPorperResultWhenAddingCurrenciesWithNoMoneyContext() {
    assertThat(USD(1).plus(USD(2)), is(USD(3)));
  }
  
  @Test
  public void testReturnProperResultWhenSubtractingCurrenciesWithNoMoneyContext() {
    assertThat(USD(5).minus(USD(2)), is(USD(3)));
  }
  
  @Test
  public void testReturnPorperResultWhenDividingCurrenciesWithNoMoneyContext() {
    assertThat(USD(10).div(USD(2)), is(5d));
  }
  
  @Test(expected=NoSuchExchangeRateException.class)
  public void testNoSuchExchangeRateExceptionWhenAddingCrossCurrencyValuesWithOnlyDefaultMoneyContext() {
    assertThat(USD(1).plus(JPY(2)), is(CAD(3)));
  }
  
  @Test(expected=NoSuchExchangeRateException.class)
  public void testNoSuchExchangeRateExceptionWhenSubtractingCrossCurrencyValuesWithOnlyDefaultMoneyContext() {
    assertThat(USD(1).minus(JPY(2)), is(CAD(3)));
  }
  
  @Test(expected=NoSuchExchangeRateException.class)
  public void testNoSuchExchangeRateExceptionWhenDividingCrossCurrencyValuesWithOnlyDefaultMoneyContext() {
    assertThat(USD(1).div(JPY(2)), is(3d));
  }
  
  @Test
  public void testReturnProperResultWhenMultiplyingInDouble() {
    assertThat(USD(10).multiply(2), is(USD(20)));
    assertThat(JPY(23.50).multiply(3), is(JPY(70.50)));
  }
  
  @Test
  public void testReturnProperResultWhenMultiplyingInBigDecimal() {
    assertThat(USD(10).multiply(new BigDecimal(2)), is(USD(20)));
    assertThat(JPY(23.50).multiply(new BigDecimal(3)), is(JPY(70.50)));
  }
  
  @Test
  public void testReturnTheEqualValueInTheOtherCurrencyWhenMultipliedWithCurrencyExchangeRate() {
    CurrencyExchangeRate rate = new CurrencyExchangeRate(USD(1), JPY(100));
    assertThat(USD(10).multiply(rate), is(JPY(1000)));
  }
  
  @Test
  public void testReturnProperResultWhenDividingByDouble() {
    assertThat(USD(10).div(2), is(USD(5)));
    assertThat(JPY(75).div(3), is(JPY(25)));
  }

  @Test
  public void testReturnProperResultWhenDividingByBigDecimal() {
    assertThat(USD(10).div(new BigDecimal(2)), is(USD(5)));
    assertThat(JPY(75).div(new BigDecimal(3)), is(JPY(25)));
  }

  @Test
  public void testReturnProperResultWhenRemainderingByDouble() {
    assertThat(USD(10).remainder(4), is(USD(2)));
    assertThat(JPY(75).remainder(3), is(JPY(0)));
  }
  
  @Test
  public void testReturnProperResultWhenRemainderingByBigDecimal() {
    assertThat(USD(10).remainder(new BigDecimal(4)), is(USD(2)));
    assertThat(JPY(75).remainder(new BigDecimal(3)), is(JPY(0)));
  }
  
  @Test
  public void testReturnProperResultWhenDividingAndRemainderingByDouble() {
    assertThat(USD(10).divideAndRemainder(3), is(pair(USD(3), USD(1))));
    assertThat(JPY(75).divideAndRemainder(3), is(pair(JPY(25), JPY(0))));
  }
  
  @Test
  public void testReturnProperResultWhenDividingAndRemainderingByBigDecimal() {
    assertThat(USD(10).divideAndRemainder(new BigDecimal(3)), is(pair(USD(3), USD(1))));
    assertThat(JPY(75).divideAndRemainder(new BigDecimal(3)), is(pair(JPY(25), JPY(0))));
  }
  
  @Test
  public void testReturnProperResultWhenDividingByMoneyOfLikeCurrency() {
    assertThat(USD(10).div(USD(2)), is(5d));
  }
  
  @Test
  public void testReturnProperResultWhenDividingByMoneyOfAnotherCurrency() {
    CurrencyExchangeRate r1 = new CurrencyExchangeRate(USD(1), JPY(100));
    CurrencyExchangeRate r2 = new CurrencyExchangeRate(USD(1), EUR(.75));
    MoneyContext.set(USD, Market.defaultCurrencySet, sequence(r1, r2).toList());
    
    assertThat(USD(10).div(JPY(200)), is(5d));
    assertThat(EUR(7.5).div(USD(10)), is(1d));
    
    MoneyContext.remove();
  }
  
  @Test
  public void testReturnProperResultWhenGettingTheAbsoulteValue() {
    assertThat(USD(10).abs(), is(USD(10)));
    assertThat(USD(-10).abs(), is(USD(10)));
  }
  
  @Test
  public void testCrossCurrenciesWithAMoneyContextAndApplicableRatesInScope() {
    CurrencyExchangeRate r1 = new CurrencyExchangeRate(USD(1), JPY(100));
    CurrencyExchangeRate r2 = new CurrencyExchangeRate(USD(1), EUR(.50));
    MoneyContext.set(USD, Market.defaultCurrencySet, sequence(r1, r2).toList());

    assertThat(USD(10).plus(JPY(500)), is(USD(15)));
    assertThat(EUR(10).plus(USD(10)), is(EUR(15)));
    assertThat(EUR(7.5).plus(JPY(500)), is(EUR(10))); // Uses indirect rate via USD

    assertThat(USD(10).minus(JPY(500)), is(USD(5)));
    assertThat(EUR(10).minus(USD(10)), is(EUR(5)));
    assertThat(EUR(7.5).minus(JPY(500)), is(EUR(5))); // Uses indirect rate via USD
    
    MoneyContext.remove();
  }
  
  @Test
  public void testReturnAnExchangeRateInADifferentCurrency() {
    assertThat(JPY(100).toThe(USD(1)), is(new CurrencyExchangeRate(USD(1), JPY(100))));
    assertThat(USD(1).toThe(JPY(100)), is(new CurrencyExchangeRate(JPY(100), USD(1))));
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void testThrowIllegalArgumentExceptionOntoTheSameCurrency() {
    assertThat(USD(100).toThe(USD(1)), is(new CurrencyExchangeRate(USD(1), JPY(100))));
  }
  
  @Test
  public void testConvertCurrenciesBasedOnMoneyContext() {
    CurrencyExchangeRate r1 = new CurrencyExchangeRate(USD(1), JPY(100));
    CurrencyExchangeRate r2 = new CurrencyExchangeRate(USD(1), EUR(.75));
    MoneyContext.set(USD, Market.defaultCurrencySet, sequence(r1, r2).toList());

    assertThat(USD(1.5).in(JPY), is(JPY(150)));
    assertThat(USD(1).in(EUR), is(EUR(0.75)));
    assertThat(JPY(100).in(USD), is(USD(1)));
    assertThat(EUR(75).in(USD), is(USD(100)));
    assertThat(EUR(75).in(JPY), is(JPY(10000))); // Uses indirect rate via USD
    assertThat(JPY(100).in(EUR), is(EUR(0.75))); // Uses indirect rate via USD
    
    assertThat(USD(1.5).to(JPY), is(150d));
    assertThat(USD(1).to(EUR), is(0.75d));
    assertThat(JPY(100).to(USD), is(1d));
    assertThat(EUR(75).to(USD), is(100d));
    assertThat(EUR(75).to(JPY), is(10000d)); // Uses indirect rate via USD
    assertThat(JPY(100).to(EUR), is(0.75d)); // Uses indirect rate via USD
    
    MoneyContext.remove();
  }

  @Test
  public void testReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure() {
    assertThat(USD(10).toString(), is("10.00 USD"));
    assertThat(USD(10).toFormattedString(), is("$10.00"));
  }
  
  @Test
  public void testReturnProperlyFormattedStringsInDifferentCurrenciesWithMoneyContextInScope() {
    MoneyContext.set(USD, Market.defaultCurrencySet, sequence(USD(1).toThe(JPY(100))).toList());
    assertThat(USD(10).toString(JPY), is("1000.00 JPY"));
    assertThat(USD(10).toFormattedString(JPY),  is("¥1000.00"));
    MoneyContext.remove();
  }
}
