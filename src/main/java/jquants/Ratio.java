package jquants;

/**
 * Defines a class for types that represent a ratio between any two quantities
 *
 * @author  Mathias Braeu
 * @since   1.0
 *
 * @param <A> Quantity of type A
 * @param <B> Quantity of type B
 */
public class Ratio<A extends Quantity<A>, B extends Quantity<B>> { 
  public A base;
  public B counter;
  
  
  public Ratio(A base, B counter) {
    this.base = base;
    this.counter = counter;
  }

  public A convertToBase(B q) {
    return base.multiply(q.div(counter));
  }
  
  public B convertToCounter(A q) {
    return counter.multiply(q.div(base));
  }

//  public double ratio() {
//    return (base.value)/(counter.value);
//  }
//
//  public double inverseratio() {
//    return (counter.value)/(base.value);
//  }
  
}

