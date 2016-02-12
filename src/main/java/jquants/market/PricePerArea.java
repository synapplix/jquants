package jquants.market;

import java.math.BigDecimal;

import jquants.Ratio;
import jquants.UnitOfMeasure;
import jquants.market.Money.Currency;
import jquants.space.Area;

/**
 * Represents a price
 *
 * A price is an {@link Ratio} between a quantity of {@link Money}
 * and a {@link Area}.
 *
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public class PricePerArea extends Ratio<Money, Area> {
  
  /**
   * 
   * @param money Money
   * @param area Area
   */
  public PricePerArea(Money money, Area area) {
    super(money,area);
  }

  public PricePerArea plus(PricePerArea  that) {
    return new PricePerArea (base.plus(that.base), counter);
  }
  
  public PricePerArea minus(PricePerArea  that) {
    return new PricePerArea (base.minus(that.base), counter);
  }

  public PricePerArea multiply(double that) {
    return new PricePerArea (base.multiply(that), counter);
  }
  
  public PricePerArea multiply(BigDecimal that) {
    return new PricePerArea (base.multiply(that), counter);
  }

  public PricePerArea div(double that) {
    return new PricePerArea (base.div(that), counter);
  }
      
  public PricePerArea div(BigDecimal that) {
    return new PricePerArea (base.div(that), counter);
  }
  
  public BigDecimal div(PricePerArea  that) {
    return base.amount.divide(that.base.amount);
  }

  public PricePerArea in(Currency currency) {
    return new PricePerArea (base.in(currency), counter);
  }
  
  /**
   * Returns the Cost (Money) for an Area `that` 
   * @param that Area
   * @return
   */
  public Money multiply(Area that) {
    return convertToBase(that);
  }

  /**
   * Returns the Area that will cost that)
   * @param that Money
   * @return
   */
  public Area multiply(Money that) {
    return convertToCounter(that);
  }
  
  /**
   * Returns the Value
   * @return
   */
  public double getValue(){
    return base.value/counter.value;
  }
  
  public Money getBase(){
    return base;
  }
  
  public Area getArea(){
    return counter;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PricePerArea) {
      PricePerArea that = (PricePerArea) obj;
      if(this.base.equals(that.base) && this.counter.equals(that.counter))
        return true;
    }
    return false;
  }
  
  @Override
  public String toString() {
    return this.base.toString() + "/" + counter.toString();
  }

  public String toString(Currency currency, UnitOfMeasure<Area> unit) {
    return base.in(currency).toString() + "/" + counter.toString(unit);
   }
}
