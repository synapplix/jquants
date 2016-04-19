package jquants.motion;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.mass.Mass.Kilograms;
import static jquants.mass.Mass.Pounds;
import static jquants.time.Time.Seconds;
import static jquants.time.Time.SecondsPerHour;

import jquants.Dimension;
import jquants.MetricSystem;
import jquants.Quantity;
import jquants.UnitOfMeasure;
import jquants.mass.Mass;
import jquants.time.Time;
import jquants.time.TimeDerivative;

/**
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public class MassFlowRate extends Quantity<MassFlowRate> implements TimeDerivative<Mass>{

  public Mass change;
  public Time time;
  
  private MassFlowRate(double value, MassFlowRateUnit valueUnit) {
    this.value = value;
    this.valueUnit = valueUnit;
    this.dimension = MassFlowRate;
  }
  
  public static MassFlowRate MassFlowRate(Mass mass, Time time) {
    return null;
  }
  
//  public static Option<Volume> toMassFlowRateString(String s) {
//    return MassFlowRate.parseString(s);
//  }
  
//def dimension = MassFlow

  public Mass timeIntegrated() {return Kilograms(toKilogramsPerSecond());}
  public Time time() {return Seconds(1);}

  public double toKilogramsPerSecond() { return to(KilogramsPerSecond);}
  public double toPoundsPerSecond() { return to(PoundsPerSecond);}
  public double toPoundsPerHour() { return to(PoundsPerHour);}
  public double toKilopoundsPerHour() { return to(KilopoundsPerHour);}
  public double toMegapoundsPerHour() { return to(MegapoundsPerHour);}

  public static final class MassFlowRateUnit extends UnitOfMeasure<MassFlowRate>{
    public MassFlowRateUnit(String symbol, double multiplier) {
      super(symbol, multiplier, true, false, false);
    }

    @Override
    public MassFlowRate apply(double n) {
      return new MassFlowRate(n, this);
    }
  
  }
  
  
//object MassFlow extends Dimension[MassFlow] {
//private[motion] def apply[A](n: A, unit: MassFlowUnit)(implicit num: Numeric[A]) = new MassFlow(num.toDouble(n), unit)
//def apply = parse _
//def name = "MassFlow"
//def primaryUnit = KilogramsPerSecond
//def siUnit = KilogramsPerSecond
//def units = Set(KilogramsPerSecond, PoundsPerSecond, PoundsPerHour, KilopoundsPerHour, MegapoundsPerHour)
//}
//
//
  public static final MassFlowRateUnit KilogramsPerSecond = new MassFlowRateUnit("kg/s", 1);
  public static final MassFlowRateUnit PoundsPerSecond = new MassFlowRateUnit("lb/s", Pounds.multiplier / Kilograms.multiplier);
  public static final MassFlowRateUnit PoundsPerHour = new MassFlowRateUnit("lb/hr", PoundsPerSecond.multiplier / SecondsPerHour);
  public static final MassFlowRateUnit KilopoundsPerHour = new MassFlowRateUnit("klb/hr", PoundsPerHour.multiplier * MetricSystem.Kilo);
  public static final MassFlowRateUnit MegapoundsPerHour = new MassFlowRateUnit("Mlb/hr", PoundsPerHour.multiplier * MetricSystem.Mega);

  public static MassFlowRate KilogramsPerSecond(double value) {return new MassFlowRate(value, KilogramsPerSecond);}
  public static MassFlowRate PoundsPerSecond(double value) {return new MassFlowRate(value, PoundsPerSecond);}
  public static MassFlowRate PoundsPerHour(double value) {return new MassFlowRate(value, PoundsPerHour);}
  public static MassFlowRate KilopoundsPerHour(double value) {return new MassFlowRate(value, KilopoundsPerHour);}
  public static MassFlowRate MegapoundsPerHour(double value) {return new MassFlowRate(value, MegapoundsPerHour);}
  
  public static final Dimension<MassFlowRate> MassFlowRate = new Dimension<MassFlowRate>(
      "MassFlowRate", 
      KilogramsPerSecond, 
      sequence(KilogramsPerSecond, PoundsPerSecond, PoundsPerHour, KilopoundsPerHour, MegapoundsPerHour));
  
  public static MassFlowRate kilogramPerSecond = KilogramsPerSecond(1);
  public static MassFlowRate poundPerSecond = PoundsPerSecond(1);
  public static MassFlowRate poundPerHour = PoundsPerHour(1);
  public static MassFlowRate kilopoundPerHour = KilopoundsPerHour(1);
  public static MassFlowRate megapoundPerHour = MegapoundsPerHour(1);

//implicit object MassFlowNumeric extends AbstractQuantityNumeric[MassFlow](MassFlow.primaryUnit)
}
