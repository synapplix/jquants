package jquants;

import static jquants.MetricSystem.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class MetricSystemTest {
  @Test
  public void test() {
    assertThat(Exa, is(Peta * 1000d));
    assertThat(Peta, is(Tera * 1000d));
    assertThat(Tera, is(Giga * 1000d));
    assertThat(Giga, is(Mega * 1000d));
    assertThat(Mega, is(Kilo * 1000d));
    assertThat(Kilo, is(1000d));
    assertThat(Milli, is(.001));
    assertThat(Micro, is(Milli * .001));
    assertThat(Nano, is(Micro * .001));
    assertThat(Pico, is(Nano * .001));
    assertThat(Femto, is(Pico * .001));
    assertThat(Atto, is(Femto * .001));
  }
}
