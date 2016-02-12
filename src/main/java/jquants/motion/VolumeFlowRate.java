package jquants.motion;

import static com.googlecode.totallylazy.Sequences.sequence;
import static jquants.space.Length.Feet;
import static jquants.time.Time.*;

import com.googlecode.totallylazy.Option;

import jquants.Dimension;
import jquants.Quantity;
import jquants.UnitOfMeasure;
import jquants.motion.Velocity.VelocityUnit;
import jquants.space.Area;
import jquants.space.Length;
import jquants.space.Volume;
import jquants.space.Length.LengthUnit;
import jquants.space.Volume.VolumeUnit;
import jquants.time.Time;
import static jquants.time.Time.*;
import jquants.time.Time.TimeUnit;
import jquants.time.TimeDerivative;
import static jquants.space.Volume.*;

/**
 * @author  Matahias Braeu 
 * @since   1.0
 *
 */
public class VolumeFlowRate extends Quantity<VolumeFlowRate> implements TimeDerivative<Volume>{
  public Volume change;
  public Time time;
  
  private VolumeFlowRate(Volume volume, Time time) {
    this.change = volume;
    this.time = time;
    this.valueUnit = CubicMetersPerSecond;
    this.value = to(valueUnit);  
    this.dimension = VolumeFlowRate;
  }
  
  private VolumeFlowRate(double value, VolumeFlowRateUnit unit){
    this.value = value;
    this.valueUnit = unit;
  }
  
  public static Option<VolumeFlowRate> toVolumeFlowRate(String s) {
    return VolumeFlowRate.parseString(s);
  }
  
  public Volume timeIntegrated() {return CubicMeters(toCubicMetersPerSecond());}
  public Time time(){ return Seconds(1);}

  public double toCubicMetersPerSecond() {return to(CubicMetersPerSecond);}
  public double toGallonsPerDay() {return to(GallonsPerDay);}
  public double toGallonsPerHour() {return to(GallonsPerHour);}
  public double toGallonsPerMinute() {return to(GallonsPerMinute);}
  public double toGallonsPerSecond() {return to(GallonsPerSecond);}
  public double toCubicFeetPerHour() {return to(CubicFeetPerHour);}
  
  public static class VolumeFlowRateUnit extends UnitOfMeasure<VolumeFlowRate> {
    
    public VolumeFlowRateUnit(String symbol, double multiplier) {
      super(symbol, multiplier, true, false, false);
    }
   
    @Override
    public VolumeFlowRate apply(double n) {
      return new VolumeFlowRate(n, this);
    }
  }

  public static final VolumeFlowRateUnit CubicMetersPerSecond = new VolumeFlowRateUnit("m³/s", 1);
  public static final VolumeFlowRateUnit CubicFeetPerHour = new VolumeFlowRateUnit("ft³/hr", (CubicMeters.multiplier * CubicFeet.multiplier) / SecondsPerHour);
  public static final VolumeFlowRateUnit GallonsPerDay = new VolumeFlowRateUnit("GPD", (CubicMeters.multiplier * UsGallons.multiplier) / SecondsPerDay);
  public static final VolumeFlowRateUnit GallonsPerHour = new VolumeFlowRateUnit("GPH", (CubicMeters.multiplier * UsGallons.multiplier) / SecondsPerHour);
  public static final VolumeFlowRateUnit GallonsPerMinute = new VolumeFlowRateUnit("GPM", (CubicMeters.multiplier * UsGallons.multiplier) / SecondsPerMinute);
  public static final VolumeFlowRateUnit GallonsPerSecond = new VolumeFlowRateUnit("GPS", CubicMeters.multiplier * UsGallons.multiplier);
  
  public static VolumeFlowRate cubicMeterPerSecond = CubicMetersPerSecond(1);
  public static VolumeFlowRate cubicFeetPerHour = CubicFeetPerHour(1);
  public static VolumeFlowRate gallonPerDay = GallonsPerDay(1);
  public static VolumeFlowRate gallonPerHour = GallonsPerHour(1);
  public static VolumeFlowRate gallonPerMinute = GallonsPerMinute(1);
  public static VolumeFlowRate gallonPerSecond = GallonsPerSecond(1);
  
  public static final Dimension<VolumeFlowRate> VolumeFlowRate = new Dimension<VolumeFlowRate>(
      "VolumeFlowRatee", 
      CubicMetersPerSecond, 
      sequence(CubicMetersPerSecond, CubicFeetPerHour, GallonsPerHour, GallonsPerDay, GallonsPerHour, GallonsPerMinute, GallonsPerSecond));
  
  public static VolumeFlowRate CubicMetersPerSecond(double value) {return new VolumeFlowRate(value,  CubicMetersPerSecond);}
  public static VolumeFlowRate CubicFeetPerHour(double value) {return new VolumeFlowRate(value,  CubicFeetPerHour);}
  public static VolumeFlowRate GallonsPerDay(double value) {return new VolumeFlowRate(value,  GallonsPerDay);}
  public static VolumeFlowRate GallonsPerHour(double value) {return new VolumeFlowRate(value,  GallonsPerHour);}
  public static VolumeFlowRate GallonsPerMinute(double value) {return new VolumeFlowRate(value,  GallonsPerMinute);}
  public static VolumeFlowRate GallonsPerSecond(double value) {return new VolumeFlowRate(value,  GallonsPerSecond);}

  //  implicit object VolumeFlowRateNumeric extends AbstractQuantityNumeric[dimension](CubicMetersPerSecond)
}
