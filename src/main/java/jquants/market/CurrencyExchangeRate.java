package jquants.market;

import java.math.RoundingMode;

import jquants.Ratio;

/**
 * Represent the rate of exchange between two currencies
 *
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param base the base or "Fixed" currency (usually a value of 1 currency unit, but not required)
 * @param counter the counter or "Variable" currency
 */
public class CurrencyExchangeRate extends Ratio<Money, Money> {

  public CurrencyExchangeRate(Money base, Money counter) {
    super(base,counter);
    if (base.currency.equals(counter.currency))
      throw new IllegalArgumentException("Can not create Exchange Rate on matching currencies");
  }

  /**
   * @return Double the rate = counter / base
   */
  public Double rate() {
    // TODO: Same issue as in MoneyContext.divide(); Discuss exactness of scale - for now 40
    return counter.amount.divide(base.amount, 40, RoundingMode.HALF_UP).doubleValue();
  }

  /**
   * Converts the given money into the other currency of this exchange rate
   *
   * @param money Money
   * @return
   */
  public Money convert(Money money) {
    if (money.currency.equals(base.currency)) {
      return convertToCounter(money);
    } else if (money.currency.equals(counter.currency)) {
      return convertToBase(money);
    } else {
      throw new IllegalArgumentException("The currency of money must match the currency of base or counter");
    }
  }

  /** convert  */
  public Money mult(Money money) {
    return convert(money);
  }

  /**
   * Returns the rate formatted in as standard FX Quote"
   * @return
   */
  @Override
  public String toString() {
    return base.currency.code + "/" + counter.currency.code + " " + rate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof CurrencyExchangeRate) {
      CurrencyExchangeRate that = (CurrencyExchangeRate) obj;
      if(this.base.equals(that.base) && this.counter.equals(that.counter))
        return true;
    }
    return false;
  }
}
