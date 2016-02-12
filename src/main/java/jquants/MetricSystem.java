package jquants;

/**
 * Singleton defining Metric System multipliers
 *
 * @author  Mathias Braeu
 * @since   1.0
 *
 */
public class MetricSystem {
  public static final double Deci = .1;
  public static final double Centi = .01;
  public static final double Milli = .001;
  public static final double Micro = .001 * Milli;
  public static final double Nano = .001 * Micro;
  public static final double Pico = .001 * Nano;
  public static final double Femto = .001 * Pico;
  public static final double Atto = .001 * Femto;
  public static final double Zepto = .001 * Atto;
  public static final double Yocto = .001 * Zepto;

  public static final double Deca = 10d;
  public static final double Hecto = 100d;
  public static final double Kilo = 1000d;
  public static final double Mega = 1000d * Kilo;
  public static final double Giga = 1000d * Mega;
  public static final double Tera = 1000d * Giga;
  public static final double Peta = 1000d * Tera;
  public static final double Exa = 1000d * Peta;
  public static final double Zetta = 1000d * Exa;
  public static final double Yotta = 1000d * Zetta;
}
