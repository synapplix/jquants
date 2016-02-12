package jquants;

import static com.googlecode.totallylazy.Sequences.sequence;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

import com.googlecode.totallylazy.Callable2;
import com.googlecode.totallylazy.Sequence;

/**
 * Represents a Range starting at one Quantity value and going up to another
 *
 * @author  Mathias Braeu
 * @since   1.0
 *
 * @param <A> type of the Quantity of the range
 */
public class QuantityRange<A extends Quantity<A>> {

  public A lower;
  public A upper;

  /**
   * New QuantityRange with Quantities of type A
   * 
   * @param lower
   *          Quantity representing the lower bound of the range
   * @param upper
   *          Quantity representing the upper bound of the range
  */
  public QuantityRange(A lower, A upper) {
    if (lower.value >= upper.value)
      throw new IllegalArgumentException("QuantityRange upper bound must be greater than or equal to the lower bound");
    this.lower = lower;
    this.upper = upper;
  }

  public boolean equals(Object obj) {
    if (obj instanceof QuantityRange) {
      @SuppressWarnings("unchecked")
      QuantityRange<A> that = (QuantityRange<A>) obj;
      return this.lower.equals(that.lower) && this.upper.equals(that.upper);
    } else {
      return false;
    }
  }

  public String toString() {
    return this.lower.value + " " + this.lower.valueUnit.toString() + " to " + this.upper.value + " " + this.upper.valueUnit.toString();
  }

  /**
   * Create a Seq of `multiple` ranges equal in size to the original with
   * sequential range values If `multiple` contains a fractional component, the
   * last item in the resulting range will be equal to that fraction of the
   * original
   *
   * @param multiple
   *          Number of ranges to create
   * @return
   */
  // public QuantitySeries<A> times(double multiple) {
  // double remainder = multiple % 1;
  // double count = ((multiple - remainder) / 1).toInt;
  // val ranges = (0 until count).map(n ⇒ QuantityRange(lower + (toQuantity *
  // n), upper + (toQuantity * n)))

  // if (remainder > 0) ranges :+ QuantityRange(lower + (toQuantity * count),
  // lower + (toQuantity * (count + remainder)));
  // else ranges;
  // return null;
  // }

  public ArrayList<QuantityRange<A>> times(double multiple) {
    double remainder = multiple % 1;
    double count = ((multiple - remainder));
    ArrayList<QuantityRange<A>> ranges = new ArrayList<QuantityRange<A>>();
    for (int i = 0; i < count; i++) {
      ranges.add(new QuantityRange<A>(lower.plus(this.toQuantity().multiply(i)), upper.plus(this.toQuantity().multiply(i))));
    }
    if (remainder > 0)
      ranges.add(new QuantityRange<A>(lower.plus(this.toQuantity().multiply(count)), upper.plus(this.toQuantity().multiply(count + remainder))));
    return ranges;
  }

  /**
   * Divides the range in Seq of ranges each with a range with a Quantity of
   * `that` The Seq will begin at `from` and go till `to`. If the range is not
   * evenly divisible by `that`, the last item in the list will contain the
   * remainder
   *
   * QuantityRange(Count(1), Count(4) / Count(1) will return:  Seq(Count(1), Count(2),
   * Count(3), Count(4))
   *
   * @param that
   *          Quantity
   * @return
   */
  // public QuantitySeries<A> divide(A that) {
  // @tailrec
  // def accumulate(acc: QuantitySeries[A], start: A): QuantitySeries[A] = {
  // if (start >= upper) acc
  // else accumulate(acc :+ (start to (start +
  // that).min(upper).asInstanceOf[A]), start + that)
  // }
  // accumulate(IndexedSeq.empty.asInstanceOf[QuantitySeries[A]], lower)
  // return null;
  // }
  public ArrayList<QuantityRange<A>> divide(A that) {
    ArrayList<QuantityRange<A>> acc = new ArrayList<QuantityRange<A>>();
    return accumulate(acc, lower, that);
  }

  private ArrayList<QuantityRange<A>> accumulate(ArrayList<QuantityRange<A>> acc, A start, A that) {

    if (start.value >= upper.value)
      return acc; // break condition
    else {
      A newStart = start.plus(that);
      acc.add(new QuantityRange<A>(start, (A) (newStart.value < upper.value ? newStart : upper)));
      return accumulate(acc, newStart, that); // recursive invocation until
                                              // break condition is met
    }
  }

  /**
   * Divides the range into a Seq of `divisor` ranges The Seq will begin at
   * `from` and go till `to`. If `that` is an integer value, the range will
   * evenly divided at all points. If `that` has a fractional component, the
   * first n-1 ranges will be evenly divided by the `that` and the last range in
   * the list will contain the remainder.
   *
   * @param that
   *          Double
   * @return
   */
  public ArrayList<QuantityRange<A>> divide(double that) {
    return divide(this.toQuantity().div(that));
  }

  /**
   * Divides the range into a Seq of ranges of `size` each and applies a f to
   * each element having A as Quantity type of the size parameter
   *
   * @param size
   *          Quantity representing the size for each QuantityRange in the Seq
   * @param op
   *          the side affecting operation
   *
   */
  // def foreach[U](size: A)(op: QuantityRange[A] ⇒ U) = /(size).foreach(op)
  public void foreach(A size, Consumer<? super QuantityRange> op) {
    this.divide(size).forEach(op);
  }

  /**
   * Divides the range into a Seq of `divisor` ranges and applies a f to each
   * element
   *
   * @param divisor
   *          Quantity representing the size for each QuantityRange in the Seq
   * @param op
   *          the side affecting operation
   */
  // def foreach[U](divisor: Double)(op: QuantityRange[A] ⇒ U) =
  // /(divisor).foreach(op)
  public void foreach(double divisor, Consumer<? super QuantityRange<A>> op) {
    this.divide(divisor).forEach(op);
  }

  /**
   * Divides the range into a Seq of ranges of `size` each and applies a map
   * operation to each. 
   *
   * @param size
   *          Quantity representing the size for each QuantityRange in the Seq
   * @param op
   *          the transformation operation
   *          
   * @return ArrayList filled with resulting {@link QuantityRange} consisting of Quantities of type A of the map operation
   */
  // def map[B](size: A)(op: QuantityRange[A] ⇒ B): Seq[B] = /(size).map(op)
  // def map[B](divisor: Double)(op: QuantityRange[A] ⇒ B): Seq[B] =
  // map(toQuantity / divisor)(op)

  public ArrayList<QuantityRange<A>> map(A size, Function<QuantityRange<A>, ?> op) {
    Object[] array = this.divide(size).stream().map(op).toArray(); // divide this through size; 
                                                                   // map given operation op to the result; 
                                                                   // retrieve the result as an array
    return new ArrayList<QuantityRange<A>>() {
      {// return the array as an arrayList and cast each member to a QuantityRange
        for (Object e : array) {
          add((QuantityRange<A>) e);
        }
      }
    };
  }

  /**
   * Divides the range into a Seq of `divisor` ranges and applies a map
   * operation to each
   *
   * @param divisor
   *          Quantity representing the size for each QuantityRange in the Seq
   * @param op
   *          the transformation operation
   * @return
   */
  public ArrayList<?> map(double divisor, Function<QuantityRange<A>, ?> op) {
    Object[] array = this.divide(divisor).stream().map(op).toArray();
    return new ArrayList<QuantityRange<A>>() {
      {
        for (Object e : array) {
          add((QuantityRange<A>) e);
        }
      }
    };
  }

  /**
   * Divides the range into a Seq of ranges of `size` each and applies a
   * foldLeft operation
   * 
   * @param size
   *          Quantity representing the size for each QuantityRange in the Seq
   * @param z
   *          the start value, a Quantity of type A
   * @param op
   *          the binary operator
   * @return
   */
  // def foldLeft[B](size: A, z: B)(op: (B, QuantityRange[A]) ⇒ B): B =
  // /(size).foldLeft[B](z)(op)
  // public <B> B foldLeft(A size, A z, BiFunction<B, B, B> op ){
  public A foldLeft(A size, A z, Callable2<A, QuantityRange<A>,A> op) {
    
    // Divide Range in size parts; build a com.googlecode.totallylazy.Sequence with list-elements and apply foldLeft with given Function op
    ArrayList<QuantityRange<A>> list = this.divide(size);
    return sequence(list).foldLeft(z, op);
  }

  /**
   * Divides the range into a Seq of ranges of `size` each and applies a
   * foldLeft operation
   *
   * @param size
   *          The number of ranges to split the range into
   * @param z
   *          the start value, a Quantity of type A
   * @param op
   *          the binary operator
   * @return
   */
  // def foldLeft[B](divisor: Double, z: B)(op: (B, QuantityRange[A]) ⇒ B): B =
  // /(divisor).foldLeft[B](z)(op)
  public A foldLeft(Double size, A z, Callable2<A, QuantityRange<A>,A> op) {
      
    // Divide Range in size parts; build a com.googlecode.totallylazy.Sequence with list-elements and apply foldLeft with given Function op
    ArrayList<QuantityRange<A>> list = this.divide(size);
    return sequence(list).foldLeft(z, op);
    }
  
  /**
   * Divides the range into a Seq of ranges of `size` each and applies a
   * foldRight operation
   *
   * @param size
   *          Quantity representing the size for each QuantityRange in the Seq
   * @param z
   *          the start value, a Quantity of type A
   * @param op
   *          the binary operator
   * @return
   */
  // def foldRight[B](size: A, z: B)(op: (QuantityRange[A], B) ⇒ B): B =
  // /(size).foldRight[B](z)(op)
  public A foldRight(A size, A z, Callable2<QuantityRange<A>,A,A> op) {
    
    //Divide Range in size parts; build a com.googlecode.totallylazy.Sequence with list-elements and apply foldRight with given Function op
    ArrayList<QuantityRange<A>> list = this.divide(size);
    return sequence(list).foldRight(z, op); 
  }
  /**
   * Divides the range into a Seq of ranges of `size` each and applies a
   * foldRight operation
   *
   * @param size
   *          The number of ranges to split the range into
   * @param z
   *          the start value, a Quantity of type A
   * @param op
   *          the binary operator
   * @return
   */
  // def foldRight[B](divisor: Double, z: B)(op: (QuantityRange[A], B) ⇒ B): B =
  // /(divisor).foldRight[B](z)(op)
  public A foldRight(Double size, A z, Callable2<QuantityRange<A>,A,A> op) {
    
    //Divide Range in size parts; build a com.googlecode.totallylazy.Sequence with list-elements and apply foldRight with given Function op
    ArrayList<QuantityRange<A>> list = this.divide(size);
    return sequence(list).foldRight(z, op); 
  }
  /**
   * Increments the range's from and to values by an amount equal to the
   * Quantity value of the range
   * 
   * @return
   */
  // lazy val inc = QuantityRange(lower + toQuantity, upper + toQuantity)
  public QuantityRange<A> inc() {
    return new QuantityRange<A>(this.lower.plus(toQuantity()), this.upper.plus(toQuantity()));
  }

  /**
   * Increments the range's from and to values by an amount equal to the value
   * of `that`
   * 
   * @param that
   *          Quantity
   * @return
   */
  // def inc(that: A) = QuantityRange(lower + that, upper + that)
  public QuantityRange<A> inc(A that) {
    return new QuantityRange<A>(this.lower.plus(that), this.upper.plus(that));
  }

  /**
   * Decrements the range's from and to value by an amount equal to the Quantity
   * value of the range
   * 
   * @return
   */
  // lazy val dec = QuantityRange(lower - toQuantity, upper - toQuantity)
  public QuantityRange<A> dec() {
    return new QuantityRange<A>(this.lower.minus(toQuantity()), this.upper.minus(toQuantity()));
  }

  /**
   * Decrements the range's from and to values by an amount equal to the value
   * of `that`
   * 
   * @param that
   *          Quantity
   * @return
   */
  // def dec(that: A) = QuantityRange(lower - that, upper - that)
  public QuantityRange<A> dec(A that) {
    return new QuantityRange<A>(this.lower.minus(that), this.upper.minus(that));
  }

  /**
   * Increments the `to` value by an amount equal to the value of `that`
   * 
   * @param that
   *          Quantity
   * @return
   */
  // def incTo(that: A) = QuantityRange(lower, upper + that)
  public QuantityRange<A> incTo(A that) {
    return new QuantityRange<A>(lower, this.upper.plus(that));
  }

  // def decTo(that: A) = QuantityRange(lower, upper - that)
  public QuantityRange<A> decTo(A that) {
    return new QuantityRange<A>(lower, this.upper.minus(that));
  }

  /**
   * Increments the `from` value by an amount equal to the value of `that`
   * 
   * @param that
   *          Quantity
   * @return
   */
  // def incFrom(that: A) = QuantityRange(lower + that, upper)
  public QuantityRange<A> incFrom(A that) {
    return new QuantityRange<A>(lower.plus(that), upper);
  }

  /**
   * Decrements the `from` value by an amount equal to the value of `that`
   * 
   * @param that
   *          Quantity
   * @return
   */
  // def decFrom(that: A) = QuantityRange(lower - that, upper)
  public QuantityRange<A> decFrom(A that) {
    return new QuantityRange<A>(lower.minus(that), upper);
  }

  /**
   * Decrements the `from` value and increments the `to` by an amount equal to
   * the value of `that`
   * 
   * @param that
   *          Quantity
   * @return
   */
  // def decFromIncTo(that: A) = QuantityRange(lower - that, upper + that)
  public QuantityRange<A> decFromIncTo(A that) {
    return new QuantityRange<A>(lower.minus(that), upper.plus(that));
  }

  /**
   * Increments the `from` value and decrements the `to` by an amount equal to
   * the value of `that`
   * 
   * @param that
   *          Quantity
   * @return
   */
  // def incFromDecTo(that: A) = QuantityRange(lower + that, upper - that)
  public QuantityRange<A> incFromDecTo(A that) {
    return new QuantityRange<A>(lower.plus(that), upper.minus(that));
  }

  /**
   * Returns true if the quantity is contained within this range, otherwise
   * false
   * 
   * @param q
   *          Quantity
   * @return
   */
  public boolean contains(A q) {
    return q.value >= lower.value && q.value < upper.value;
  }

  /**
   * Return true if `that` range is completely contained with `this` range,
   * otherwise false
   * 
   * @param that
   *          Quantity
   * @return
   */
  // def contains(that: QuantityRange[A]) =
  // that.lower >= lower &&
  // that.lower < upper &&
  // that.upper >= lower &&
  // that.upper < upper
  public boolean contains(QuantityRange<A> that) {
    return that.lower.value >= this.lower.value && that.lower.value < this.upper.value && that.upper.value >= this.lower.value && that.upper.value < this.upper.value;
  }

  /**
   * Returns true if `that` range contains any part that is in `this` range,
   * otherwise false
   * 
   * @param range
   *          QuantityRange[A]
   * @return
   */
  // def partiallyContains(range: QuantityRange[A]) = range.lower < upper &&
  // range.upper > lower
  public boolean partiallyContains(QuantityRange<A> range) {
    return range.lower.value < this.upper.value && range.upper.value > this.lower.value;
  }

  /**
   * Returns true if `that` quantity is included within `this` range
   * 
   * @param q
   *          Quantity
   * @return
   */
  // def includes(q: A) = q >= lower && q <= upper
  public boolean includes(A q) {
    return q.value >= lower.value && q.value <= upper.value;
  }

  /**
   * Returns true if `that` range is completely included in `this` range,
   * otherwise false
   * 
   * @param that
   *          QuantityRange[A]
   * @return
   */
  // def includes(that: QuantityRange[A]) =
  // that.lower >= lower &&
  // that.lower <= upper &&
  // that.upper >= lower &&
  // that.upper <= upper
  public boolean includes(QuantityRange<A> that) {
    return that.lower.value >= this.lower.value && that.lower.value <= this.upper.value && that.upper.value >= this.lower.value && that.upper.value <= this.upper.value;
  }

  /**
   * Returns true if `that` range includes any part that is in `this` range,
   * otherwise false
   * 
   * @param range
   *          QuantityRange[A]
   * @return
   */
  // def partiallyIncludes(range: QuantityRange[A]) = range.lower <= upper &&
  // range.upper >= lower
  public boolean partiallyIncludes(QuantityRange<A> range) {
    return range.lower.value <= this.upper.value && range.upper.value >= this.lower.value;
  }

  /**
   * Returns a quantity that is equal to the difference between the `from` and
   * `to`
   * 
   * @return
   */
  // lazy val toQuantity = upper - lower
  public A toQuantity() {
    return upper.minus(lower);
  }
  /**
   * Returns this Range's boundary values as a Seq[A] of the two
   * 
   * @return
   */
  // lazy val toSeq: Seq[A] = Seq(lower, upper)
  public Sequence<A> toSeq() {
    return sequence(this.lower, this.upper);
  }
  /**
   * Return this Range's boundary values as List[A] or the two
   * 
   * @return
   */
  // lazy val toList: List[A] = List(lower, upper)
  public ArrayList<A> toList() {
    return new ArrayList<A>() {
      {
        add(lower);
        add(upper);
      }
    };
  }

}