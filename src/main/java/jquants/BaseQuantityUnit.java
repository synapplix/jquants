package jquants;

public abstract class BaseQuantityUnit<T extends Quantity<T>> extends UnitOfMeasure {

  public BaseQuantityUnit(String symbol) {
    super(symbol);
  }

  public BaseQuantityUnit(String symbol, double multiplier, boolean unitMultiplier, boolean baseUnit, boolean valueUnit) {
    super(symbol, multiplier, unitMultiplier, baseUnit, valueUnit);
  }

  public abstract String dimensionSymbol();
}
