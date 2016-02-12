package jquants;

import jquants.energy.Energy;

/**
 * Unit for a BaseQuantity
 *
 * The first time derivative of {@link Energy}
 * 
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public abstract class BaseQuantityUnit<T extends Quantity<T>> extends UnitOfMeasure {

  public BaseQuantityUnit(String symbol) {
    super(symbol);
  }

  public BaseQuantityUnit(String symbol, double multiplier, boolean unitMultiplier, boolean baseUnit, boolean valueUnit) {
    super(symbol, multiplier, unitMultiplier, baseUnit, valueUnit);
  }

  public abstract String dimensionSymbol();
}
