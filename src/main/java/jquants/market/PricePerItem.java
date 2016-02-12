package jquants.market;

import static jquants.Dimensionless.each;

import java.math.BigDecimal;

import jquants.Dimensionless;
import jquants.Ratio;
import jquants.UnitOfMeasure;
import jquants.market.Money.Currency;

/**
 * Represents a price
 *
 * A price is an [[jquants.Ratio]] between a quantity of [[jquants.market.Money]]
 * and a [[jquants.Dimensionless]]
 *
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public class PricePerItem extends Ratio<Money, Dimensionless> {
  
  /**
   * 
   * @param money Money
   */
  public PricePerItem(Money money) {
   super(money, each(1));
  }

  public PricePerItem plus(PricePerItem that) {
    return new PricePerItem(base.plus(that.base));
  }
  
  public PricePerItem minus(PricePerItem that) {
    return new PricePerItem(base.minus(that.base));
  }

  public PricePerItem multiply(double that) {
    return new PricePerItem(base.multiply(that));
  }
  
  public PricePerItem multiply(BigDecimal that) {
    return new PricePerItem(base.multiply(that));
  }

  public PricePerItem div(double that) {
    return new PricePerItem(base.div(that));
  }
      
  public PricePerItem div(BigDecimal that) {
    return new PricePerItem(base.div(that));
  }
  
  public BigDecimal div(PricePerItem that) {
    return base.amount.divide(that.base.amount);
  }

  
  /**
   * Returns the Cost (Money) for an Area `that` 
   * @param that Area
   * @return
   */
  public Money multiply(Dimensionless that) {
    return convertToBase(that);
  }

  public PricePerItem in(Currency currency) {
    return new PricePerItem (base.in(currency));
  }
  
  /**
   * Returns the Area that will cost that)
   * @param that Money
   * @return
   */
  public Dimensionless multiply(Money that) {
    return convertToCounter(that);
  }
  
  public double getValue(){
    return base.value;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PricePerItem) {
      PricePerItem that = (PricePerItem) obj;
      if(this.base.equals(that.base) && this.counter.equals(that.counter))
        return true;
    }
    return false;
  }
  
  @Override
  public String toString() {
    return this.base.toString() + "/" + counter.toString();
  }

  public String toString(Currency currency, UnitOfMeasure<Dimensionless> unit) {
    return base.in(currency).toString() + "/" + counter.toString(unit);
   }
}
