package jquants;

public abstract class UnitOfMeasure<T extends Quantity<T>> {
  public String symbol;
  public double multiplier = 1d;
  public boolean unitMultiplier = false;
  public boolean valueUnit = false;
  /**
   * @deprecated no usage in code
   */
  public boolean baseUnit = false;
  
  public UnitOfMeasure(String symbol) {
    this(symbol, 1d);
  }

  public UnitOfMeasure(String symbol, double multiplier) {
    this(symbol, multiplier, false, false, false);
  }

  public UnitOfMeasure(String symbol, double multiplier, boolean unitMultiplier, boolean valueUnit, boolean baseUnit) {
    this.symbol = symbol;
    this.multiplier = multiplier;
    this.unitMultiplier = unitMultiplier;
    this.valueUnit = valueUnit;
    this.baseUnit = baseUnit;
  }
  
  public abstract T apply(double n);
  
  protected double converterFrom(double v) {
    if (this.unitMultiplier) {
      if (this.valueUnit) {
        return v;
      } else {
        return v * multiplier;
      }
    }
    return 0;
  }
  
  protected double converterTo(double v) {
    if (this.unitMultiplier) {
      if (this.valueUnit) {
        return v;
      } else {
        return v / multiplier;
      }
    }
    return 0;
  }
  
  public double convertFrom(double num) {
    return converterFrom(num);
  }

  public double convertTo(double num) {
    return converterTo(num);
  }

  @Override
  public String toString() {
    return this.symbol;
  }
}
