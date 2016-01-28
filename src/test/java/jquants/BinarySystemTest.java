package jquants;

import static jquants.BinarySystem.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class BinarySystemTest {
  @Test
  public void test() {
    assertThat(Yotta, is(Zetta * 1024d));
    assertThat(Zetta, is(Exa * 1024d));
    assertThat(Exa, is(Peta * 1024d));
    assertThat(Peta, is(Tera * 1024d));
    assertThat(Tera, is(Giga * 1024d));
    assertThat(Giga, is(Mega * 1024d));
    assertThat(Mega, is(Kilo * 1024d));
    assertThat(Kilo, is(1024d));
  }
}
