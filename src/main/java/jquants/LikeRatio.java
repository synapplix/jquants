package jquants;

/**
 * 
 * @author mbraeu
 * @since 1.0
 * @param <A>
 */
public class LikeRatio<A extends Quantity<A>> extends Ratio<A, A> {
  public LikeRatio(A base, A counter) {
    super(base, counter);
  }

  public double ratio() {
    return (base.value)/(counter.value);
  }

  public double inverseratio() {
    return (counter.value)/(base.value);
  }
}
