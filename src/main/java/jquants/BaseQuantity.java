package jquants;

public abstract class BaseQuantity<T extends Quantity<T>> extends Quantity<T> {
  public abstract UnitOfMeasure baseUnit();
}
