package jquants;
/**
 * 
 * @author Mathias Braeu
 * @since 1.0
 *
 * @param <T>
 */
public abstract class BaseQuantity<T extends Quantity<T>> extends Quantity<T> {
  public abstract UnitOfMeasure baseUnit();
}
