package jquants.market;

public class NoSuchExchangeRateException extends RuntimeException {

  private static final long serialVersionUID = 7883072447022268741L;

  public NoSuchExchangeRateException(String s) {
    super(s);
  }
}
