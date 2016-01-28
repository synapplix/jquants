package jquants.market;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.googlecode.totallylazy.Option;
import com.googlecode.totallylazy.Predicate;
import com.googlecode.totallylazy.Sequence;

import jquants.market.Money.Currency;
import static jquants.market.Money.Money;
import static com.googlecode.totallylazy.None.*;
import static com.googlecode.totallylazy.Some.*;
import static com.googlecode.totallylazy.Sequences.sequence;

/**
 * MoneyContext
 *
 * Provides a context for Money specific operations.
 *
 * When provided as an implicit parameter, the defaultCurrency will be used by the
 * Money factory when no other currency is provided.
 *
 * Provides for cross-currency conversions.
 *
 * Will act as an implicit parameter to cross currency operations to allow for
 * easy conversions
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param defaultCurrency Currency used when none is supplied to the Money factory
 * @param rates Collection of Exchange Rates used for currency conversions
 */
public class MoneyContext {

  Currency defaultCurrency;
  Set<Currency> currencies;
  List<CurrencyExchangeRate> rates;
  boolean allowIndirectConversions = true;

  private static ThreadLocal<MoneyContext> moneyContext = new ThreadLocal<MoneyContext>();

  public static MoneyContext get() {
    return moneyContext.get();
  }
  
  public static void set(Currency defaultCurrency, Set<Currency> currencies, List<CurrencyExchangeRate> rates, boolean allowIndirectConversions) {
    MoneyContext ctx = new MoneyContext(defaultCurrency, currencies, rates, allowIndirectConversions);
    moneyContext.set(ctx);
  }
  
  public static void set(Currency defaultCurrency, Set<Currency> currencies, List<CurrencyExchangeRate> rates) {
    MoneyContext ctx = new MoneyContext(defaultCurrency, currencies, rates);
    moneyContext.set(ctx);
  }
  
  public static void set(MoneyContext ctx) {
    moneyContext.set(ctx);
  }
  
  
  public static void remove() {
    moneyContext.remove();
  }
  
  public static MoneyContext current() {
    return MoneyContext.get() != null ? MoneyContext.get() : Market.defaultMoneyContext;
  }
  
  public MoneyContext(Currency defaultCurrency, Set<Currency> currencies, List<CurrencyExchangeRate> rates) {
    this(defaultCurrency, currencies, rates, true);
  }

  public MoneyContext(Currency defaultCurrency, Set<Currency> currencies, List<CurrencyExchangeRate> rates, boolean allowIndirectConversions) {
    this.defaultCurrency = defaultCurrency;
    this.currencies = currencies;
    this.rates = rates;
    this.allowIndirectConversions = allowIndirectConversions;
  }
  
  
  /**
   * Returns an Option on an exchange rate if a direct rate exists, otherwise None
   *
   * @param curA Currency A
   * @param curB Currency B
   * @return
   */
  public Option<CurrencyExchangeRate> directRateFor(final Currency curA, final Currency curB ) {
    Option<CurrencyExchangeRate> find = sequence(rates).find(new Predicate<CurrencyExchangeRate>() {
      public boolean matches(CurrencyExchangeRate r) {
        return (r.base.currency == curA && r.counter.currency == curB || r.base.currency == curB && r.counter.currency == curA);
      }
    });
    return find;
  }

  /**
   * Return an Option on an exchange rate.  If a direct rate exists an Option on that will be returned.
   * Otherwise, if a cross rate can be determined (1 hop limit), it will be created and returned in an Option.
   * Otherwise, None will be returned
   *
   * @param curA Currency A
   * @param curB Currency B
   * @return
   * @throws NoSuchExchangeRateException 
   */
  public Option<CurrencyExchangeRate> indirectRateFor(final Currency curA, final Currency curB) throws NoSuchExchangeRateException {

    // TODO Improve this to attempt to use defaultCurrency first
    Option<CurrencyExchangeRate> directRateFor = directRateFor(curA, curB);
    if (!directRateFor.isEmpty()) {
      return directRateFor;
    } else {
      List<CurrencyExchangeRate> ratesWithCurA = new ArrayList<CurrencyExchangeRate>();
      List<CurrencyExchangeRate> ratesWithCurB = new ArrayList<CurrencyExchangeRate>();
      for (CurrencyExchangeRate r : this.rates) {
        if (r.base.currency == curA || r.counter.currency == curA) {
          ratesWithCurA.add(r);
        }
        if (r.base.currency == curB || r.counter.currency == curB) {
          ratesWithCurB.add(r);
        }
      }
      List<Currency> curs = new ArrayList<Currency>();
      for (Currency cur : this.currencies) {
        if ((containsBaseCurrency(ratesWithCurA, cur) || containsCounterCurrency(ratesWithCurA, cur)) && 
            (containsBaseCurrency(ratesWithCurB, cur) || containsCounterCurrency(ratesWithCurB, cur))) {
          curs.add(cur);
        }
      }
      if (curs.size() > 0) {
        Money m = Money(1d, curs.get(0));
        return some(new CurrencyExchangeRate(convert(m, curA), convert(m, curB)));
      } else {
        return none();
      }
    }
  }

  private boolean containsBaseCurrency(List<CurrencyExchangeRate> rates, Currency cur) {
    for (CurrencyExchangeRate rate : rates) {
      if (rate.base.currency.equals(cur)) return true;
    }
    return false;
  }
  
  private boolean containsCounterCurrency(List<CurrencyExchangeRate> rates, Currency cur) {
    for (CurrencyExchangeRate rate : rates) {
      if (rate.counter.currency.equals(cur)) return true;
    }
    return false;
  }

  /**
   * Converts a Money value to the specified currency.
   *
   * The conversion first attempts to use an existing exchange rate for the two currencies in question.
   * If no direct exchange works, a cross rate (limited to 1 hop) will be calculated and used.
   * If no cross rate can be calculated a NoSuchElementException is thrown
   *
   * @param money  Money to be converted
   * @param currency Currency to be converted to
   * @return
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  public Money convert(Money money, Currency currency) throws NoSuchExchangeRateException {

    if (money.currency == currency) {
      return money;
    }else  {
      Option<CurrencyExchangeRate> rate = directRateFor(money.currency, currency);
      if (!rate.isEmpty()) {
        return rate.get().convert(money);
      } else {
        if (allowIndirectConversions) {
          Option<CurrencyExchangeRate> crossRate = indirectRateFor(money.currency, currency);
          if (!crossRate.isEmpty()) {
            return crossRate.get().convert(money);
          } else {
            throw new NoSuchExchangeRateException(String.format("Rate for currency pair %s / %s)", money.currency, currency));
          }
        }
      }
    }
    throw new NoSuchExchangeRateException(String.format("Rate for currency pair %s / %s)", money.currency, currency)); 
  }

  /**
   * Adds two money values that may or may not be in the same currency.
   *
   * The result will be in the same currency as the first parameter.
   *
   * @param moneyA Money A
   * @param moneyB Money B
   * @return
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  public Money add(Money moneyA, Money moneyB) throws NoSuchExchangeRateException {
    return Money(moneyA.amount.add(convert(moneyB, moneyA.currency).amount), moneyA.currency);
  }

  /**
   * Subtracts two money values that may or may not be in the same currency
   *
   * The result will be in the same currency as the first parameter.
   *
   * @param moneyA Money A
   * @param moneyB Money B
   * @return
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  public Money subtract(Money moneyA, Money moneyB) throws NoSuchExchangeRateException {
    return Money(moneyA.amount.subtract(convert(moneyB, moneyA.currency).amount), moneyA.currency);
  }

  /**
   * Divides two money value that may or may not be in the same currency after converting the second to the first
   *
   * @param moneyA Money A
   * @param moneyB Money B
   * @return
   * @throws NoSuchExchangeRateException 
   */
  public BigDecimal divide(Money moneyA, Money moneyB) throws NoSuchExchangeRateException {
    BigDecimal amount = convert(moneyB, moneyA.currency).amount;
    // BigDecimal representation will fail in case of a repetend as not representatble since not terminating: e.g. 1/3 = 0.3333333... as no
    // TODO: Discuss the needed exactness (amount of scale); 
    //       lower scale will impair the exactness; until then set to 40
    return moneyA.amount.divide(amount, 40, RoundingMode.HALF_UP);
  }

  /**
   * Performs a standard compare on two money values that may or may not be in the same currency
   * @param moneyA Money A
   * @param moneyB Money B
   * @return
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  public int compare(Money moneyA, Money moneyB) throws NoSuchExchangeRateException {
    if (moneyA.amount.doubleValue() > convert(moneyB, moneyA.currency).amount.doubleValue()) return 1;
    else if (moneyA.amount.doubleValue() < convert(moneyB, moneyA.currency).amount.doubleValue()) return -1;
    else return 0;
  }

  /**
   * Create a copy of this context with a new list of rates
   * @param rates List[CurrencyExchangeRateshould]
   * @return
   */
  public List<CurrencyExchangeRate> withExchangeRates(List<CurrencyExchangeRate> rates) {
    // TODO: return copy(rates = rates);
    return rates;
  }
}
