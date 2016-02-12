package jquants.market;

import java.math.BigDecimal;

import jquants.Quantity;
import jquants.Ratio;
import jquants.UnitOfMeasure;
import jquants.market.Money.Currency;

/**
 * Represents a price
 *
 * A price is an {@link Ratio} between a quantity of {@link Money}
 * and some other {@link Quantity}
 *
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public class Price<A extends Quantity<A>> extends Ratio<Money, A> {
  
  /**
   * 
   * @param money Money
   * @param quantity Quantity of type A
   */
  public Price(Money money, A quantity) {
    super(money, quantity);
  }

  // TODO Add verification that money amounts are the same OR convert
  public Price<A> plus(Price<A> that) {
    return new Price<A>(base.plus(that.base), counter);
  }
  
  public Price<A> minus(Price<A> that) {
    return new Price<A>(base.minus(that.base), counter);
  }

  public Price<A> multiply(double that) {
    return new Price<A>(base.multiply(that), counter);
  }
  
  public Price<A> multiply(BigDecimal that) {
    return new Price<A>(base.multiply(that), counter);
  }

  public Price<A> div(double that) {
    return new Price<A>(base.div(that), counter);
  }
      
  public Price<A> div(BigDecimal that) {
    return new Price<A>(base.div(that), counter);
  }
  
  public BigDecimal div(Price<A> that) {
    return base.amount.divide(that.base.amount);
  }

  public Price<A> in(Currency currency) {
    return new Price<A>(base.in(currency), counter);
  }
  
  /**
   * Returns the Cost (Money) for a quantity `that` of A
   * @param that Quantity
   * @return
   */
  public Money multiply(A that) {
    return convertToBase(that);
  }

  /**
   * Returns the Quantity that will cost that)
   * @param that Money
   * @return
   */
  public A multiply(Money that) {
    return convertToCounter(that);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Price) {
      Price<?> that = (Price<?>) obj;
      if(this.base.equals(that.base) && this.counter.equals(that.counter))
        return true;
    }
    return false;
  }
  
  @Override
  public String toString() {
    return this.base.toString() + "/" + counter.toString();
  }

  public String toString(Currency currency, UnitOfMeasure<A> unit) {
    return base.in(currency).toString() + "/" + counter.toString(unit);
   }
}
