package jquants.market;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import static jquants.market.Money.*;

public class CurrencyExchangeRateTest {

  
//    behavior of "CurrencyExchangeRate"
//
//    it should "create CurrencyExchangeRates using the default factory method" in {
//      val rate = CurrencyExchangeRate(USD(1), JPY(100))
//      rate.base should be(USD(1))
//      rate.counter should be(JPY(100))
//    }
  
  @Test
  public void shouldCreateCurrencyExchangeRatesUsingTheDefaultFactoryMethod(){
    CurrencyExchangeRate rate = new CurrencyExchangeRate(USD(1.0), JPY(100.0));
    assertThat(rate.base, is(USD(1.0)));
    assertThat(rate.counter, is(JPY(100.0)));
  }
  
//
//    it should "properly return a Currency Exchange Rate" in {
//      val rate = CurrencyExchangeRate(USD(1), JPY(100))
//      rate.rate should be(100)
//    }

  @Test
  public void shouldProperlyReturnACurrencyEchangeRate(){
    CurrencyExchangeRate rate = new CurrencyExchangeRate(USD(1.0), JPY(100));
    assertThat(rate.rate(), is(100.0));
  }
  
  //
//    it should "properly return a converted Money value" in {
//      val rate1 = CurrencyExchangeRate(USD(1), JPY(100))
//      val rate2 = CurrencyExchangeRate(USD(1), EUR(75))
//
//      // using the convert method directly
//      rate1.convert(JPY(100)) should be(USD(1))
//      rate1.convert(USD(1)) should be(JPY(100))
//      rate2.convert(EUR(75)) should be(USD(1))
//      rate2.convert(USD(1)) should be(EUR(75))
//
//      // using the * operator
//      rate1 * JPY(100) should be(USD(1))
//      rate1 * USD(1) should be(JPY(100))
//      rate2 * EUR(75) should be(USD(1))
//      rate2 * USD(1) should be(EUR(75))
//
//      // using the methods inherited from Ratio
//      rate1.convertToBase(JPY(100)) should be(USD(1))
//      rate1.convertToCounter(USD(1)) should be(JPY(100))
//      rate2.convertToBase(EUR(75)) should be(USD(1))
//      rate2.convertToCounter(USD(1)) should be(EUR(75))
//    }
//
  
  @Test
  public void shouldProperlyReturnAConvertedMoneyValue(){
    CurrencyExchangeRate rate1 = new CurrencyExchangeRate(USD(1.0), JPY(100));
    CurrencyExchangeRate rate2 = new CurrencyExchangeRate(USD(1.0), EUR(75));

    // using the convert method directly
    assertThat(rate1.convert(JPY(100)), is(USD(1)));
    assertThat(rate1.convert(USD(1)), is(JPY(100.0)));
    assertThat(rate2.convert(EUR(75)), is(USD(1)));
    assertThat(rate2.convert(USD(1)), is(EUR(75.0)));
    
    // using the multiply
    assertThat(rate1.mult(JPY(100)), is(USD(1.0)));
    assertThat(rate1.mult(USD(1)), is(JPY(100.0)));
    assertThat(rate2.mult(EUR(75)), is(USD(1.0)));
    assertThat(rate2.mult(USD(1)), is(EUR(75.0)));
    
    // using the methods inherited from Ratio
    assertThat(rate1.convertToBase(JPY(100)), is(USD(1)));
    assertThat(rate1.convertToCounter(USD(1)), is(JPY(100)));
    assertThat(rate2.convertToBase(EUR(75)), is(USD(1)));
    assertThat(rate2.convertToCounter(USD(1)), is(EUR(75)));
  }
  
  
//    it should "properly return a string formatted as an FX quote" in {
//      val rate = CurrencyExchangeRate(USD(1), JPY(100))
//      rate.toString should be("USD/JPY 100.0")
//    }
//  }
  
  @Test
  public void shouldProperlyReturnAStringFormattedAsAnFxQuote(){
    CurrencyExchangeRate rate = new CurrencyExchangeRate(USD(1.0), JPY(100));
    assertThat(rate.toString(), is("USD/JPY 100.0"));
  }

}
