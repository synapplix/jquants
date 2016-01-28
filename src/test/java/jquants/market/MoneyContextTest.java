package jquants.market;

import static com.googlecode.totallylazy.None.none;
import static com.googlecode.totallylazy.Some.some;
import static jquants.market.Market.defaultCurrencySet;
import static jquants.market.Money.AUD;
import static jquants.market.Money.CAD;
import static jquants.market.Money.CHF;
import static jquants.market.Money.CNY;
import static jquants.market.Money.EUR;
import static jquants.market.Money.GBP;
import static jquants.market.Money.HKD;
import static jquants.market.Money.JPY;
import static jquants.market.Money.Money;
import static jquants.market.Money.NOK;
import static jquants.market.Money.USD;
import static jquants.market.Money.XAG;
import static jquants.market.Money.XAU;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.junit.Test;

import jquants.market.Money.Currency;

public class MoneyContextTest {

  
  
//   */
//  class MoneyContextSpec extends FlatSpec with Matchers {
//
//    val defCur = USD
//    val rates = List(
//      CurrencyExchangeRate(EUR(1), USD(1.25)),
//      CurrencyExchangeRate(USD(1), JPY(100)),
//      CurrencyExchangeRate(GBP(1), USD(1.6686)),
//      CurrencyExchangeRate(USD(1), CAD(1.1126)),
//      CurrencyExchangeRate(USD(1), HKD(7.7565)),
//      CurrencyExchangeRate(USD(1), CNY(6.0915)),
//      CurrencyExchangeRate(AUD(1), USD(0.8961)),
//      CurrencyExchangeRate(XAU(1), USD(1200.00)),
//      CurrencyExchangeRate(XAG(1), USD(20.00)),
//      CurrencyExchangeRate(CHF(1), NOK(40)))
//
//    val moneyContext = MoneyContext(defCur, defaultCurrencySet, rates)
//
      
  
  
      
     ArrayList<CurrencyExchangeRate> rates = new ArrayList<CurrencyExchangeRate>() {{
        add(new CurrencyExchangeRate(EUR(1), USD(1.25)));
        add(new CurrencyExchangeRate(USD(1), JPY(100.0)));
        add(new CurrencyExchangeRate(GBP(1), USD(1.6686)));
        add(new CurrencyExchangeRate(USD(1), CAD(1.1126)));
        add(new CurrencyExchangeRate(USD(1), HKD(7.7565)));
        add(new CurrencyExchangeRate(USD(1), CNY(6.0915)));
        add(new CurrencyExchangeRate(AUD(1), USD(0.8961)));
        add(new CurrencyExchangeRate(XAU(1), USD(1200.00)));
        add(new CurrencyExchangeRate(XAG(1), USD(20.00)));
        add(new CurrencyExchangeRate(CHF(1), NOK(40.0)));
    }};
    Currency defCur = USD;
    MoneyContext moneyContext = new MoneyContext(defCur, defaultCurrencySet, rates);
  
  
    private Object defCur(double d) {
      return Money(d, defCur);
    }
    
//    behavior of "MoneyContext"
//
//    it should "initialize with appropriate values" in {
//      moneyContext.defaultCurrency should be(defCur)
//      moneyContext.rates should be(rates)
//    }
  
  @Test
  public void shouldInitializeWithAppropriateValues(){
    assertThat(moneyContext.defaultCurrency, is(defCur));
    assertThat(moneyContext.rates, is(rates));
  }
//
//    it should "properly create Money using the default currency with an implicit MoneyContext in scope" in {
//      implicit val moneyContext = MoneyContext(defCur, defaultCurrencySet, rates)
//      Money(10.22) should be(defCur(10.22))
//    }
  
  @Test
  public void shouldProperlyCreateMoneyUsingTheDefaultCurrencyWithAnImplicitMoneyContextInScope(){
    MoneyContext moneyContext = new MoneyContext(defCur, defaultCurrencySet, rates);
    assertThat(Money(10.22), is(defCur(10.22)));
  }
//
//    it should "return Some(rate) for a given currency pair for which there is a rate" in {
//      moneyContext.directRateFor(USD, JPY) should be(Some(CurrencyExchangeRate(USD(1), JPY(100))))
//      moneyContext.directRateFor(JPY, USD) should be(Some(CurrencyExchangeRate(USD(1), JPY(100))))
//    }
  
  @Test
  public void shouldReturnSomeRateForAGivenCurrencyPairForWhichThereIsARate(){
    assertThat(moneyContext.directRateFor(JPY, USD), is(some(new CurrencyExchangeRate(USD(1), JPY(100)))));
    assertThat(moneyContext.directRateFor(USD, JPY), is(some(new CurrencyExchangeRate(USD(1), JPY(100)))));
  }
//
//    it should "return None for a given currency pair for which there is no rate" in {
//      moneyContext.directRateFor(CAD, AUD) should be(None)
//      moneyContext.directRateFor(EUR, JPY) should be(None)
//    }
  
  @Test
  public void shoudReturnNoneForAGivenCurrencyPairForWhichThereIsNoRate(){
    assertThat(moneyContext.directRateFor(CAD, AUD), is(none()));
    assertThat(moneyContext.directRateFor(EUR, JPY), is(none()));
  }
//
//    it should "return Some(rate) for a given currency pair for which there is a direct rate" in {
//      moneyContext.indirectRateFor(USD, JPY) should be(Some(CurrencyExchangeRate(USD(1), JPY(100))))
//      moneyContext.indirectRateFor(JPY, USD) should be(Some(CurrencyExchangeRate(USD(1), JPY(100))))
//    }
  
  @Test
  public void shouldReturnSomeRateForAGivenCurrencyPairForWhichThereIsADirectRate(){
    assertThat(moneyContext.indirectRateFor(USD, JPY), is(some(new CurrencyExchangeRate(USD(1), JPY(100)))));
    assertThat(moneyContext.directRateFor(JPY, USD), is(some(new CurrencyExchangeRate(USD(1), JPY(100)))));
  }
  
//
//    it should "return Some(rate) for a given currency pair for which there is no direct, but an indirect rate" in {
//      moneyContext.indirectRateFor(XAG, XAU).get.rate should be(CurrencyExchangeRate(XAG(60), XAU(1)).rate)
//      moneyContext.indirectRateFor(EUR, JPY).get.rate should be(CurrencyExchangeRate(EUR(0.80), JPY(100)).rate)
//    }
  
  @Test
  public void shouldReturnSomeRateForAGivenCurrencyPairForWhichThereIsNoDirectButAnIndirectRate(){
    assertThat(moneyContext.indirectRateFor(XAG, XAU).get().rate(), is(new CurrencyExchangeRate(XAG(60), XAU(1)).rate()));
    assertThat(moneyContext.indirectRateFor(EUR, JPY).get().rate(), is(new CurrencyExchangeRate(EUR(0.80), USD(100)).rate()));
  }
  
//
//    it should "return None for a given currency pair for which there is no direct or indirect rate" in {
//      moneyContext.indirectRateFor(CHF, USD) should be(None)
//      moneyContext.indirectRateFor(NOK, XAG) should be(None)
//    }
  
  @Test
  public void shouldReturnSomeRateForGivenCurrencyPairForWhichThereIsNoDirectButAnIndirectRate(){
    assertThat(moneyContext.indirectRateFor(CHF, USD), is(none()));
    assertThat(moneyContext.indirectRateFor(NOK, XAG), is(none()));
  }
//
//    it should "properly convert Money values between currencies for which there is a direct exchange rate" in {
//      moneyContext.convert(USD(10), JPY) should be(JPY(1000.00))
//      moneyContext.convert(JPY(200), USD) should be(USD(2))
//      moneyContext.convert(XAG(30), USD) should be(USD(600))
//    }
  
  @Test
  public void shouldProperlyConvertMoneyValuesBetweenCurrenciesForWhichThereIsADirectExchangeRate(){
    assertThat(moneyContext.convert(USD(10), JPY), is(JPY(1000.00)));
    assertThat(moneyContext.convert(JPY(200), USD), is(USD(2)));
    assertThat(moneyContext.convert(XAG(30), USD), is(USD(600)));
  }
//
//    it should "properly convert Money values between currencies for which there is only an indirect exchange rate" in {
//      moneyContext.convert(XAU(1), XAG) should be(XAG(60))
//      moneyContext.convert(JPY(100), EUR) should be(EUR(0.8))
//    }
  
  @Test
  public void shouldProperlyConvertMoneyValuesBetweenCurrenciesForWhichThereIsOnlyAnIndirectExchangeRate(){
    assertThat(moneyContext.convert(XAU(1), XAG), is(XAG(60)));
    assertThat(moneyContext.convert(JPY(100), EUR), is(EUR(0.8)));
  }
//
//    it should "properly return the same Money when converting to the same currency" in {
//      moneyContext.convert(USD(12.28), USD) should be(USD(12.28))
//      moneyContext.convert(XAG(12.28), XAG) should be(XAG(12.28))
//    }
  
  @Test
  public void shouldProperlyReturnTheSameMoneyWhenConvertingToTheSameCurrency(){
    assertThat(moneyContext.convert(USD(12.28), USD), is(USD(12.28)));
    assertThat(moneyContext.convert(XAG(12.28), XAG), is(XAG(12.28)));
  }
//
//    it should "properly return the same Money when converting to the same currency with an empty context" in {
//      val context = MoneyContext(defCur, defaultCurrencySet, Nil)
//      context.convert(USD(12.28), USD) should be(USD(12.28))
//      context.convert(XAG(12.28), XAG) should be(XAG(12.28))
//    }
  
  @Test
  public void shouldProperlyReturnTheSameMoneyWhenConvertingToTheSameCurrencyWithAnEmptyContext(){
    MoneyContext context = new MoneyContext(defCur, defaultCurrencySet, null);
    assertThat(context.convert(USD(12.28), USD), is(USD(12.28))); 
    assertThat(context.convert(XAG(12.28), XAG), is(XAG(12.28)));
  }
//
//    it should "throw a NoExchangeRateException when converting between Currencies for which no indirect rate can be determined" in {
//      intercept[NoSuchExchangeRateException] {
//        moneyContext.convert(CHF(100), USD)
//      }
//      intercept[NoSuchExchangeRateException] {
//        moneyContext.convert(NOK(100), XAG)
//      }
//    }
//
  
  
  @Test (expected=NoSuchExchangeRateException.class)
  public void shouldThrowANoExchangeRateExceptionWhenConvertingBetweenCurrenciesForWhichNoIndirectRateCanBeDetermined(){
    moneyContext.convert(CHF(100), USD);
    moneyContext.convert(NOK(100), XAG);
  }
  
//    it should "throw a NoExchangeRateException when converting where an indirect rate exists but is not allowed by the context" in {
//      val context = MoneyContext(defCur, defaultCurrencySet, rates, allowIndirectConversions = false)
//      intercept[NoSuchExchangeRateException] {
//        context.convert(XAU(1), XAG) should be(XAG(60))
//      }
//      intercept[NoSuchExchangeRateException] {
//        context.convert(JPY(100), EUR) should be(EUR(0.8))
//      }
//    }
//
//    it should "return Money in the first currency when adding two Moneys in different currencies" in {
//      moneyContext.add(USD(1), JPY(100)) should be(USD(2))
//    }
//
//    it should "return Money in the first currency when subtracting two Moneys in different currencies" in {
//      moneyContext.subtract(USD(2), JPY(100)) should be(USD(1))
//    }
//
//    it should "return BigDecimal when dividing two Moneys in different currencies" in {
//      moneyContext.divide(USD(2), JPY(100)) should be(2)
//    }
//
//    it should "return Int based on a standard comparison of two Moneys in different currencies" in {
//      moneyContext.compare(USD(2), JPY(100)) should be(1)
//      moneyContext.compare(USD(1), JPY(200)) should be(-1)
//      moneyContext.compare(USD(1), JPY(100)) should be(0)
//    }
//
//    it should "return a copy with a new set of rates" in {
//      val newRates = List(
//        CurrencyExchangeRate(EUR(1), USD(1.25)));
//        CurrencyExchangeRate(USD(1), JPY(100)),
//        CurrencyExchangeRate(GBP(1), USD(1.6686)),
//        CurrencyExchangeRate(USD(1), CAD(1.1126)))
//      val newContext = moneyContext.withExchangeRates(newRates)
//      newContext.defaultCurrency should be(moneyContext.defaultCurrency)
//      newContext.currencies should be(moneyContext.currencies)
//      newContext.rates should be(newRates)
//    }
//  }

  


}
