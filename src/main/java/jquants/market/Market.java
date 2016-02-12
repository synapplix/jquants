package jquants.market;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jquants.market.Money.Currency;
import static jquants.market.Money.*;

/**
 * Squants Market API
 *
 * Market Types are similar but not quite the same as other quantities in the library.
 *
 * The primary type, [[squants.market.Money]], is derived from Quantity, and its Units of Measure are Currencies.
 * However, because the conversion multipliers between units can not be predefined, many of the behaviors have been
 * overridden and augmented to realize correct behavior.
 *
 * [[squants.market.Price]]s represent a Ratio between Money and some other Quantity.  Prices can be created from
 * ratios of Money and a Quantity
 * {{{
 *   val money: Money = USD(10)
 *   val length: Length = Meters(1)
 *   val price: Price[Length] = money / length
 * }}}
 * Multiplying a Price * Quantity will yield Money amount that represents the cost of the quantity
 * {{{
 *   val cost: Money = price * Meters(3.8)
 * }}}
 * and multiplying Price * Money will yield the corresponding Quantity amount
 * {{{
 *   val budget: Money = USD(250)
 *   val quote: Length = price * budget
 * }}}
 *
 * [[squants.market.CurrencyExchangeRate]]s represent conversion rates between currencies.
 * Use them to explicitly convert Money values in one currency to values in another.
 *
 * [[squants.market.MoneyContext]] provide the implicit context necessary to perform cross-currency operations
 * on Money values with conversions automatically applied.
 *
 * Some binary math operations will work on Moneys of like Currency with no MoneyContext in scope.
 * Attempts to perform these operations on Moneys of dissimilar currencies will throw an exception at runtime.
 *
 * Other operations, including direct conversions to other currencies, require a MoneyContext and will not compile without it.
 * However, there is no compile time check to determine if the correct exchange rates will be available at runtime.
 * Operation requiring conversion without the required rates available will throw a NoSuchExchangeRateException at runtime.
 *
 * The defaultMoneyContext uses the USD as the default and provides a list of ~20 common currencies, and NO exchange rates.
 * If your application requires something different you should initialize your own implicit MoneyContext
 *
 * @author  Mathias Braeu
 * @since   1.0
 */
public class Market {

  public static final Set<Currency> defaultCurrencySet = new HashSet<Currency>();
  static {
    defaultCurrencySet.add(USD);
    defaultCurrencySet.add(ARS);
    defaultCurrencySet.add(AUD);
    defaultCurrencySet.add(BRL);
    defaultCurrencySet.add(CAD);
    defaultCurrencySet.add(CHF);
    defaultCurrencySet.add(CLP);
    defaultCurrencySet.add(CNY);
    defaultCurrencySet.add(CZK);
    defaultCurrencySet.add(DKK);
    defaultCurrencySet.add(EUR);
    defaultCurrencySet.add(GBP);
    defaultCurrencySet.add(HKD);
    defaultCurrencySet.add(INR);
    defaultCurrencySet.add(JPY);
    defaultCurrencySet.add(KRW);
    defaultCurrencySet.add(MXN);
    defaultCurrencySet.add(MYR);
    defaultCurrencySet.add(NOK);
    defaultCurrencySet.add(NZD);
    defaultCurrencySet.add(RUB);
    defaultCurrencySet.add(SEK);
    defaultCurrencySet.add(XAG);
    defaultCurrencySet.add(XAU);
    defaultCurrencySet.add(BTC);
  }

  public static final Map<String, Currency> defaultCurrencyMap = new HashMap<String, Currency>();
  static {
    for (Currency c : defaultCurrencySet) {
      defaultCurrencyMap.put(c.code, c);
    }
  }

  public static final MoneyContext defaultMoneyContext = new MoneyContext(USD, defaultCurrencySet, null, false);
}
