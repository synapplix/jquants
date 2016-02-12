package jquants;

import static com.googlecode.totallylazy.Pair.pair;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.stream.Stream;

import com.googlecode.totallylazy.Pair;

public abstract class Quantity<A extends Quantity<A>> {
  
  public double value;
  public UnitOfMeasure<A> valueUnit;
  public Dimension<A> dimension; 
  
  public Dimension<A> getDimension(){
    return dimension;
  }
  
  public A plus(A that) {
    return this.valueUnit.apply(value + that.to(this.valueUnit));
  }
  
  public A minus(A that) {
    return plus(that.negate());
  }
  
  public A multiply(double that) {
    return this.valueUnit.apply(value * that);
  }
  
  public A div(double that) {
    return this.valueUnit.apply(value / that);
  }
  
  public double div(A that) {
   return value / that.to(this.valueUnit);
  }
  
  public A remainder(double that) {
    return this.valueUnit.apply(value % that);
  }
  
  public double remainder(A that) {
    return value % that.value;
  }
  
  public Pair<A, A> divideAndRemainder(double that) {
    BigDecimal n = new BigDecimal(value);
    BigDecimal[] divideAndRemainder = n.divideAndRemainder(new BigDecimal(that));
    if (divideAndRemainder != null) {
      A a = this.valueUnit.apply(divideAndRemainder[0].doubleValue());
      A b = this.valueUnit.apply(divideAndRemainder[1].doubleValue());
      return pair(a, b);
    } else {
      return null;
    }
  }

  public Pair<Double, A> divideAndRemainder(A that) {
    BigDecimal n = new BigDecimal(value);
    BigDecimal[] divideAndRemainder = n.divideAndRemainder(new BigDecimal(that.value));
    if (divideAndRemainder != null) {
      Double a = divideAndRemainder[0].doubleValue();
      A b = this.valueUnit.apply(divideAndRemainder[1].doubleValue());
      return pair(a, b);
    } else {
      return null;
    }
  }

  public A negate() {
    return this.valueUnit.apply(-value);
  }
  
  public A abs(){
    return this.valueUnit.apply(Math.abs(value));
  }
 
  public boolean equals(Object that){
    if (that instanceof Quantity) {
    	Quantity x = (Quantity) that; 
          int compareValue = Double.compare(value ,x.to(this.valueUnit));
          return valueUnit.getClass() == x.valueUnit.getClass() && (compareValue == 0)  ;
	    } else {
	      return false;
	    } 
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
  
  public boolean approx(A that, A tolerance) {
    return that.within(this.plusOrMinus(tolerance));
  }
  public int compareTo(A that) {
    if (value > that.to(this.valueUnit)) {
      return 1;
    } else if (value < that.to(this.valueUnit)) {
      return -1;
    } else {
      return 0;
    }
  }

  public A max(A that) {
    return (A) (value >= that.value ? this : that);
  }

  public A min(A that) {
    return (A) (value <= that.value ? this : that);
  }

  public QuantityRange<A> plusOrMinus(A that) { 
    return new QuantityRange<A>(this.minus(that), this.plus(that));
  }

  /**
   * Returns a QuantityRange that goes from this to that
   * @param that Quantity
   * @return QuantityRange
   */
  public QuantityRange<A> to(A that) {
    return new QuantityRange(this.div(1.0), that);
  }

  
  /**
   * Returns true if this value is within (contains) the range
   * @param range QuantityRange
   * @return Boolean
   */
  public boolean within(QuantityRange<A> range) {
    return range.contains((A) this);
  }

  /**
   * Returns true if this value is not within (contains) the range
   * @param range QuantityRange
   * @return Boolean
   */
  public boolean notWithin(QuantityRange<A> range) {
    return !range.contains((A) this);
  }

  /**
   * Returns a Double representing the quantity in terms of the supplied unit
   * {{{
   *   val d = Feet(3)
   *   (d to Inches) should be(36)
   * }}}
   * @param unit UnitOfMeasure[A]
   * @return Double
   */
  public double to(UnitOfMeasure<A> unit) {
	  if(unit == this.valueUnit) {
		  return this.value;
	  }else {
		  return unit.convertTo(valueUnit.convertFrom(value));
	  }
  }
  
  /**
   * Returns an equivalent Quantity boxed with the supplied Unit
   *
   * This is really only useful for Quantity classes that box at the UOM level
   * e.g. Temperature and currently Time
   *
   * @param unit UnitOfMeasure[A]
   * @return Quantity
   */
  public A in(UnitOfMeasure<A> unit) {
    return (unit == this.valueUnit) ? (A) this : unit.apply(this.to(unit));
  }

  @Override
  public String toString() {
    return this.value + " " + this.valueUnit;
  }
  
  /**
   * Returns a string representing the quantity's value in the given `unit`
   * @param unit UnitOfMeasure[A] with UnitConverter
   * @return String
   */
  public String toString(UnitOfMeasure<A> unit) {
    return to(unit) + " " + unit.symbol;
  }

  /**
   * Returns a string representing the quantity's value in the given `unit` in the given `format`
   * @param unit UnitOfMeasure[A] with UnitConverter
   * @param format String containing the format for the value (ie "%.3f")
   * @return String
   */
  public String toString(UnitOfMeasure<A> unit, String format) {
    return String.format("%s %s", String.format(format, to(unit)), unit.symbol);
  }
  
  /**
   * Returns a tuple representing the numeric value and the unit's symbol
   * @return
   */
  public Pair<Double, String> toTuple(){
    return pair(this.value, this.valueUnit.toString());
  }

  /**
   * Returns a pair representing the numeric value and the uom's symbol
   * @param uom UnitOfMeasure[A]
   * @return
   */
  public Pair<Double, String> toTuple(UnitOfMeasure uom){
    return pair(this.to(uom), uom.symbol);
  }
  /**
   * Applies a function to the underlying value of the Quantity, returning a new Quantity in the same unit
   * @param f Double => Double function
   * @return 
   * @return
   */
//  def map(f: Double ⇒ Double) = unit(f(value))
  public Quantity<A> map(Function<Quantity<A>, Quantity<A>> op) {
   return Stream.of(this).map(op).findFirst().get();
                                                                   // map given operation op to the result; 
                                                                   // retrieve the result as an array
  }
  
  
  
}
