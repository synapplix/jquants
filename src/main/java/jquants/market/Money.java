package jquants.market;

import static com.googlecode.totallylazy.Pair.pair;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.regex.Regex.regex;

import java.math.BigDecimal;
import java.util.regex.MatchResult;

import com.googlecode.totallylazy.Pair;
import com.googlecode.totallylazy.regex.Regex;

import jquants.Dimension;
import jquants.Dimensionless;
import jquants.Quantity;
import jquants.UnitOfMeasure;
import jquants.space.Area;


/**
 * Represents a quantity of Money.
 *
 * Money is similar to other quantities in that it represents an amount of something - purchasing power - and
 * it is measured in units - currencies.
 *
 * The main difference is that the conversion rate between currencies can not be certain at compile.
 * (In fact it may not always be so easy to know them at runtime as well.)
 *
 * To address this diversion from the way most other quantities work, Money overrides several of the standard methods
 * and operators to ensure one of two rules is followed:
 *
 *  1) this and that are in the same currency, or
 *  2) there is in an implicit MoneyContext in scope (which may or may not have the applicable exchange rate)
 *
 * Methods and operations applied to moneys of different currencies may throw a NoSuchExchangeRateException if the
 * implicit MoneyContext does not contain the Rate(s) necessary to perform the conversion.
 *
 * The defaultMoneyContext includes USD as the default currency, a list of ~20 other currencies and NO exchange rates
 *
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public class Money extends Quantity<Money> {

  public BigDecimal amount;
  public Currency currency;

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  /**
   * 
   * @param amount the amount of money
   * @param currency the currency in which the money is denominated
   */
  private Money(BigDecimal amount, Currency currency) {
    this.amount = amount;
    this.currency = currency;
    this.value = amount.doubleValue();
    this.valueUnit = currency;
    this.dimension = Money;
  }
  
  public static Money Money(double value) {
    MoneyContext fxContext = MoneyContext.current();
    return Money(value, fxContext.defaultCurrency);
  }

  public static Money Money(BigDecimal value) {
    MoneyContext fxContext = MoneyContext.current();
    return new Money(value, fxContext.defaultCurrency);
  }

  public static Money Money(BigDecimal value, Currency currency) {
    return new Money(value, currency);
  }

  public static Money Money(double value, String currency) {
    return Money(value, Market.defaultCurrencyMap.get(currency));
  }

  public static Money Money(double value, Currency currency) {
    return Money(new BigDecimal(Double.toString(value)), currency);
  }

  public static Money Money(BigDecimal value, String currency) {
    return new Money(value, Market.defaultCurrencyMap.get(currency));
  }
  
  public static Money Money(String s) {
    
    String currCodes = sequence(Market.defaultCurrencyMap.keySet()).toString("|");
    Regex regex = regex("([-+]?[0-9]*\\.?[0-9]+) *(" + currCodes + ")");
    if (regex.matches(s)) {
      MatchResult match = regex.match(s);
      return Money(new BigDecimal(match.group(1)), match.group(2));
    } else {
      throw new QuantityStringParseException("Unable to parse Money " + s);
    }
  }

  /**
   * Returns a string formatted with the value and currency code
   *
   * eg USD(100) yields in: "100.00 USD"
   *
   * @return String
   */
  @Override
  public String toString() {
    return amount.setScale(currency.formatDecimals, BigDecimal.ROUND_HALF_EVEN).toString() + " " + currency.code;
  }

  public String toString(Currency c) throws NoSuchExchangeRateException {
    return in(c).toString();
  }

  /**
   * Returns a string formatted with the value and currency symbol
   *
   * eg USD(100) yields in: "\$100.00"
   *
   * @return String
   */
  public String toFormattedString() {
    return currency.symbol + amount.setScale(currency.formatDecimals).toString();
  }

  public String toFormattedString(Currency c) throws NoSuchExchangeRateException {
    return in(c).toFormattedString();
  }

  /**
   * Adds this Money to that Money converted to this.currency via context using the current MoneyContext, which is required for cross currency operations
   * 
   * @param that Money
   * @return Money
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  public Money plus(Money that) throws NoSuchExchangeRateException {
    MoneyContext context = MoneyContext.current();
    return context.add(this, that);
  }

  /**
   * Subtracts that Money from this Money converted to this.currency via context using the current MoneyContext, which is required for cross currency operations
   *
   * @param that Money
   * @return Money
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  public Money minus(Money that) throws NoSuchExchangeRateException {
    MoneyContext context = MoneyContext.current();
    return context.subtract(this, that);
  }

  /**
   * Multiplies this money by that BigDecimal and returns a new Money
   *
   * @param that BigDecimal
   * @return Money
   */
  public Money multiply(BigDecimal that) {
    return new Money(amount.multiply(that), currency);
  }

  /**
   * Multiplies this money by that [[squants.market.CurrencyExchangeRate]] and returns the equal value in the other currency.
   *
   * Delegates to CurrencyExchangeRate * Money
   *
   * @param that BigDecimal
   * @return
   */
  public Money multiply(CurrencyExchangeRate that) {
    return that.mult(this);
  }

  /**
   * Divides this money by that BigDecimal and returns a new Money
   *
   * @param that BigDecimal
   * @return Money
   */
  public Money div(BigDecimal that) {
    return new Money(amount.divide(that), currency);
  }
  
  /**
   * Divides this money by that Area and returns a new PricePerArea
   *
   * @param that Area
   * @return Money
   */
  public PricePerArea div(Area that) {
    return new PricePerArea(this, that);
  }
  
  /**
   * Divides this money by that number of items and returns a new PricePerItem
   *
   * @param number Each
   * @return Money
   */
  public PricePerItem div(Dimensionless number) {
    return new PricePerItem(this.div(number.toEach()));
  }
  
  /**
   * Integer divides this money by that BigDecimal and returns the remainder
   * @param that BigDecimal
   * @return Money
   */
  public Money remainder(BigDecimal that) {
    return Money(amount.remainder(that), currency);
  }

  /**
   * Integer divides this money by that BigDecimal and returns the quotient and the remainder
   * @param that BigDecimal
   * @return (Money, Money)
   */
  public Pair<Money, Money> divideAndRemainder(BigDecimal that) {
    BigDecimal n = new BigDecimal(value);
    BigDecimal[] divideAndRemainder = n.divideAndRemainder(that);
    if (divideAndRemainder != null) {
      Money a = Money(divideAndRemainder[0].doubleValue(), currency);
      Money b = Money(divideAndRemainder[1].doubleValue(), currency);
      return pair(a, b);
    } else {
      return null;
    }
  }

  /**
   * Divides this money by that money and returns the ratio between the converted amounts using the current MoneyContext, which is required for cross currency operations
   *
   * @param that Money
   * @return
   * @throws NoSuchExchangeRateException 
   */
  public double div(Money that) throws NoSuchExchangeRateException {
    MoneyContext context = MoneyContext.current();
    return context.divide(this, that).doubleValue();
  }
  
  /**
   * Divide this money by another (non-money) Quantity and return a Price
   * @param that Quantity
   * @tparam A Quantity Type
   * @return Price[A]
   */
//  def /[A <: Quantity[A]](that: A): Price[A] = Price(this, that)

  /**
   * Supports max operation on Moneys of dislike Currency using the current MoneyContext, which is required for cross currency operations
   * @param that Money
   * @return
   */
  public Money max(Money that) {
    MoneyContext ctx = MoneyContext.current();
    return ctx.compare(this, that) == -1 ? that : this;
  }

  /**
   * Override for Quantity.max to only work on Moneys of like Currency
   * @param that Quantity
   * @return Int
   */
//  override def min(that: Money): Money = (that, that.currency) match {
//    case (m: Money, this.currency) ⇒ new Money(amount.min(m.amount))(currency)
//    case _                         ⇒ throw new UnsupportedOperationException("min not supported for cross-currency comparison - use moneyMin")
//  }

  /**
   * Supports min operation on Moneys of dislike Currency using the current MoneyContext, which is required for cross currency operations
   * @param that Money
   * @return
   */
  public Money min(Money that) {
    MoneyContext context = MoneyContext.current();
    return context.compare(this, that) == 1 ? that : this;
  }

  // TODO implement versions of equals and compare following with implicit MoneyContext
  /**
   * Override for Quantity.equal to only match Moneys of like Currency
   * @param that Money must be of matching value and unit
   * @return
   */
  @Override 
  public boolean equals(Object that) {
    if (that instanceof Money) {
      Money m = (Money) that;
      if (this.amount.compareTo(m.amount) == 0 && this.currency.equals(m.currency)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Override for Quantity.compare to only work on Moneys of like Currency
   * @param that Money
   * @return Int
   */
//  override def compare(that: Money): Int = that.currency match {
//    case this.currency ⇒ if (this.amount > that.amount) 1 else if (this.amount < that.amount) -1 else 0
//    case _             ⇒ throw new UnsupportedOperationException("Comparison between Moneys of dislike Currency is not supported")
//  }

  /**
   * Combines with that Money to create an [[squants.market.CurrencyExchangeRate]]
   *
   * Exchange Rates on the same currency are not supported
   *
   * val rate: CurrencyExchangeRate = JPY(100) toThe USD(1)
   *
   * @param that Money
   * @return
   * @throws IllegalArgumentException is the that.currency matches this.currency
   */
  public CurrencyExchangeRate toThe(Money that) {
    if (this.currency.equals(that.currency)) {
      throw new IllegalArgumentException("Can not create Exchange Rate on matching currencies");
    } else {
      return new CurrencyExchangeRate(that, this);
    }
  }

  /**
   * Convert this Money to a Double representing the currency unit using the current MoneyContext, which is required for cross currency operations
   *
   * @param unit Currency
   * @return Double
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  public double to(Currency unit) throws NoSuchExchangeRateException {
    MoneyContext context = MoneyContext.current();
    return context.convert(this, unit).value;
  }
  
  /**
   * Reboxes this Money value in a Money in the given Currency using the current MoneyContext, which is required for cross currency operations
   *
   * @param unit Currency
   * @return Money
   * @throws NoSuchExchangeRateException when no exchange rate is available
   */
  public Money in(Currency unit) throws NoSuchExchangeRateException {
    MoneyContext context = MoneyContext.current();
    return context.convert(this, unit);
  }
  
  public static class Currency extends UnitOfMeasure<Money> {
    public String code;
    public String name;
    public int formatDecimals;
    
    public Currency(String code, String name,  String symbol, int formatDecimals) {
      super(symbol);
      this.code = code;
      this.name = name;
      this.formatDecimals = formatDecimals;
    }

    @Override
    public Money apply(double n) {
      return Money(n, this);
//      
//      def apply(value: Double)(implicit fxContext: MoneyContext) = new Money(BigDecimal(value))(fxContext.defaultCurrency)
//          def apply(value: BigDecimal)(implicit fxContext: MoneyContext) = new Money(value)(fxContext.defaultCurrency)
//
//          def apply(value: BigDecimal, currency: Currency) = new Money(value)(currency)
//          def apply(value: BigDecimal, currency: String) = new Money(value)(defaultCurrencyMap(currency))
//
//          def apply[A](n: A, currency: Currency)(implicit num: Numeric[A]) = new Money(BigDecimal(num.toDouble(n)))(currency)
//          def apply[A](n: A, currency: String)(implicit num: Numeric[A]) = new Money(BigDecimal(num.toDouble(n)))(defaultCurrencyMap(currency))
//
//          def apply(s: String): Try[Money] = {
//            lazy val regex = ("([-+]?[0-9]*\\.?[0-9]+) *(" + defaultCurrencySet.map(_.code).reduceLeft(_ + "|" + _) + ")").r
//            s match {
//              case regex(value, currency) ⇒ Success(Money(value.toDouble, defaultCurrencyMap(currency)))
//              case _                      ⇒ Failure(QuantityStringParseException("Unable to parse Money", s))
//            }
//          }
    }

    @Override
    protected double converterFrom(double v) {
      // TODO Implement ConverterFrom() for money
      return 0;
    }

    @Override
    protected double converterTo(double v) {
      // TODO Implment ConverterTo() for money
      return 0;
    }
    
    @Override
    public String toString(){
      return code;
      
    }
    
    
//    def apply[A](n: A)(implicit num: Numeric[A]) = Money(BigDecimal(num.toDouble(n)), this)
//    def apply(d: BigDecimal): Money = Money(d, this)
//    protected def converterFrom: Double ⇒ Double = ???
//    protected def converterTo: Double ⇒ Double = ???
  }
  
  public static final Currency USD = new Currency("USD", "US Dollar", "$", 2);
  public static final Currency ARS = new Currency("ARS", "Argentinean Peso", "$", 2);
  public static final Currency AUD = new Currency("AUD", "Australian Dollar", "$", 2);
  public static final Currency BRL = new Currency("BRL", "Brazilian Real", "R$", 2);
  public static final Currency CAD = new Currency("CAD", "Canadian Dollar", "$", 2);
  public static final Currency CHF = new Currency("CHF", "Swiss Franc", "CHF", 2);
  public static final Currency CLP = new Currency("CLP", "Chilean Peso", "¥", 2);
  public static final Currency CNY = new Currency("CNY", "Chinese Yuan Renmimbi", "¥", 2);
  public static final Currency CZK = new Currency("CZK", "Czech Republic Koruny", "Kč", 2);
  public static final Currency DKK = new Currency("DKK", "Danish Kroner", "kr", 2);
  public static final Currency EUR = new Currency("EUR", "Euro", "€", 2);
  public static final Currency GBP = new Currency("GBP", "British Pound", "£", 2);
  public static final Currency HKD = new Currency("HKD", "Hong Kong Dollar", "$", 2);
  public static final Currency INR = new Currency("INR", "Indian Rupee", "Rp", 2);
  public static final Currency JPY = new Currency("JPY", "Japanese Yen", "¥", 2);
  public static final Currency KRW = new Currency("KRW", "South Korean Won", "kr", 2);
  public static final Currency MXN = new Currency("MXN", "Mexican Peso", "$", 2);
  public static final Currency MYR = new Currency("MYR", "Malaysian Ringgit", "RM", 2);
  public static final Currency NOK = new Currency("NOK", "Norwegian Krone", "kr", 2);
  public static final Currency NZD = new Currency("NZD", "New Zealand Dollar", "$", 2);
  public static final Currency RUB = new Currency("RUB", "Russian Ruble", "руб", 2);
  public static final Currency SEK = new Currency("SEK", "Swedish Kroner", "kr", 2);
  public static final Currency XAG = new Currency("XAG", "Silver", "oz", 4);
  public static final Currency XAU = new Currency("XAU", "Gold", "oz", 4);
  public static final Currency BTC = new Currency("BTC", "BitCoin", "B", 15);
  
  public static Money USD(double value) {return Money(value, USD);}
  public static Money ARS(double value) {return Money(value, ARS);}
  public static Money AUD(double value) {return Money(value, AUD);}
  public static Money BRL(double value) {return Money(value, BRL);}
  public static Money CAD(double value) {return Money(value, CAD);}
  public static Money CHF(double value) {return Money(value, CHF);}
  public static Money CLP(double value) {return Money(value, CLP);}
  public static Money CNY(double value) {return Money(value, CNY);}
  public static Money CZK(double value) {return Money(value, CZK);}
  public static Money DKK(double value) {return Money(value, DKK);}
  public static Money EUR(double value) {return Money(value, EUR);}
  public static Money GBP(double value) {return Money(value, GBP);}
  public static Money HKD(double value) {return Money(value, HKD);}
  public static Money INR(double value) {return Money(value, INR);}
  public static Money JPY(double value) {return Money(value, JPY);}
  public static Money KRW(double value) {return Money(value, KRW);}
  public static Money MXN(double value) {return Money(value, MXN);}
  public static Money MYR(double value) {return Money(value, MYR);}
  public static Money NOK(double value) {return Money(value, NOK);}
  public static Money NZD(double value) {return Money(value, NZD);}
  public static Money RUB(double value) {return Money(value, RUB);}
  public static Money SEK(double value) {return Money(value, SEK);}
  public static Money XAG(double value) {return Money(value, XAG);}
  public static Money XAU(double value) {return Money(value, XAU);}
  public static Money BTC(double value) {return Money(value, BTC);}
 
  public static Money USD(BigDecimal value) {return new Money(value, USD);}
  public static Money ARS(BigDecimal value) {return new Money(value, ARS);}
  public static Money AUD(BigDecimal value) {return new Money(value, AUD);}
  public static Money BRL(BigDecimal value) {return new Money(value, BRL);}
  public static Money CAD(BigDecimal value) {return new Money(value, CAD);}
  public static Money CHF(BigDecimal value) {return new Money(value, CHF);}
  public static Money CLP(BigDecimal value) {return new Money(value, CLP);}
  public static Money CNY(BigDecimal value) {return new Money(value, CNY);}
  public static Money CZK(BigDecimal value) {return new Money(value, CZK);}
  public static Money DKK(BigDecimal value) {return new Money(value, DKK);}
  public static Money EUR(BigDecimal value) {return new Money(value, EUR);}
  public static Money GBP(BigDecimal value) {return new Money(value, GBP);}
  public static Money HKD(BigDecimal value) {return new Money(value, HKD);}
  public static Money INR(BigDecimal value) {return new Money(value, INR);}
  public static Money JPY(BigDecimal value) {return new Money(value, JPY);}
  public static Money KRW(BigDecimal value) {return new Money(value, KRW);}
  public static Money MXN(BigDecimal value) {return new Money(value, MXN);}
  public static Money MYR(BigDecimal value) {return new Money(value, MYR);}
  public static Money NOK(BigDecimal value) {return new Money(value, NOK);}
  public static Money NZD(BigDecimal value) {return new Money(value, NZD);}
  public static Money RUB(BigDecimal value) {return new Money(value, RUB);}
  public static Money SEK(BigDecimal value) {return new Money(value, SEK);}
  public static Money XAG(BigDecimal value) {return new Money(value, XAG);}
  public static Money XAU(BigDecimal value) {return new Money(value, XAU);}
  public static Money BTC(BigDecimal value) {return new Money(value, BTC);}
  
  public static Dimension<Money> Money = new Dimension<Money>(
      "Money", 
      EUR, 
      sequence(USD, ARS, AUD, BRL, CAD, CHF, CLP, CNY, CZK, DKK, EUR, GBP, HKD, INR, JPY, KRW, MXN, MYR, NOK, NZD, RUB, SEK, XAG, XAU, BTC));
  
  
  public static class QuantityStringParseException extends RuntimeException {
    public QuantityStringParseException(String message) {
      super(message);
    }
  }
}
