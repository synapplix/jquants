package jquants;
import static jquants.Dimensionless.Dozen;
import static jquants.Dimensionless.Each;
import static jquants.Dimensionless.Gross;
import static jquants.Dimensionless.Percent;
import static jquants.Dimensionless.Score;
import static jquants.Dimensionless.dozen;
import static jquants.Dimensionless.dz;
import static jquants.Dimensionless.ea;
import static jquants.Dimensionless.each;
import static jquants.Dimensionless.gr;
import static jquants.Dimensionless.gross;
import static jquants.Dimensionless.hundred;
import static jquants.Dimensionless.million;
import static jquants.Dimensionless.percent;
import static jquants.Dimensionless.score;
import static jquants.Dimensionless.thousand;
import static jquants.Dimensionless.toDimensionless;
import static jquants.time.Frequency.Hertz;
import static jquants.time.Time.Seconds;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DimensionlessTest {

//	behavior of "Dimensionless and its Units of Measure"
//
//	  it should "create values using UOM factories" in {
//	    Percent(1).toPercent should be(1)
//	    Each(1).toEach should be(1)
//	    Dozen(1).toDozen should be(1)
//	    Score(1).toScore should be(1)
//	    Gross(1).toGross should be(1)
//	  }
//
	
	@Test
	public void testCreateValuesUsingUOMFactories(){
		
		assertThat(Percent(1).toPercent(), is(1d));
		assertThat(Each(1).toEach(), is(1d));
		assertThat(Dozen(1).toDozen(), is(1d));
		assertThat(Score(1).toScore(), is(1d));
		assertThat(Gross(1).toGross(), is(1d));
	}
	
	
//	  it should "create values from properly formatted Strings" in {
//	    Dimensionless("10.22 %").get should be(Percent(10.22))
//	    Dimensionless("10.22 ea").get should be(Each(10.22))
//	    Dimensionless("10.22 dz").get should be(Dozen(10.22))
//	    Dimensionless("10.22 score").get should be(Score(10.22))
//	    Dimensionless("10.22 gr").get should be(Gross(10.22))
//	    Dimensionless("10.45 zz").failed.get should be(QuantityParseException("Unable to parse Dimensionless", "10.45 zz"))
//	    Dimensionless("zz ea").failed.get should be(QuantityParseException("Unable to parse Dimensionless", "zz ea"))
//	  }
//
	
	@Test
	public void testCreateValuesFromProperlyFormattedStrings() {
		
		assertThat(toDimensionless("10.22 %").get(), is(Percent(10.22)));
		assertThat(toDimensionless("10.22 ea").get(), is(Each(10.22)));
		assertThat(toDimensionless("10.22 dz").get(), is(Dozen(10.22)));
		assertThat(toDimensionless("10.22 score").get(), is(Score(10.22)));
		assertThat(toDimensionless("10.22 gr").get(), is(Gross(10.22)));
		//assertThat(toDimensionless("10.22 zz").get(), is((10.22)));
		//assertThat(toDimensionless("10.22 zz ea").get(), is(Micrograms(10.22)));
		
	}


//	  it should "properly convert to all supported Units of Measure" in {
//	    val x = Gross(1)
//	    x.toPercent should be(14400)
//	    x.toEach should be(144)
//	    x.toDozen should be(12)
//	    x.toScore should be(144d / 20)
//	    x.toGross should be(1)
//	  }
//
	
	@Test
	public void testProperlyConvertToAllSupportedUnitsOfMeasure() {
		
		Dimensionless x = Gross(1);
		assertThat(x.toPercent(), is(14400.0));
		assertThat(x.toEach(), is(144.0));
		assertThat(x.toDozen(), is(12.0));
		assertThat(x.toScore(), is(144d / 20));
		assertThat(x.toGross(), is(1.0));
	}	
	
//	  it should "return properly formatted strings for all supported Units of Measure" in {
//	    Percent(1).toString(Percent) should be("1.0 %")
//	    Each(1).toString(Each) should be("1.0 ea")
//	    Dozen(1).toString(Dozen) should be("1.0 dz")
//	    Score(1).toString(Score) should be("1.0 score")
//	    Gross(1).toString(Gross) should be("1.0 gr")
//	  }
	
	
	@Test
	public void shouldReturnProperlryFormattedStringsForAllSupportedUnitsOfMeasure() {
	
		 assertThat(Percent(1).toString(),is("1.0 %"));
		 assertThat(Each(1).toString(), is("1.0 ea"));
		 assertThat(Dozen(1).toString(),is("1.0 dz"));
		 assertThat(Score(1).toString(),is("1.0 score"));
		 assertThat(Gross(1).toString(),is("1.0 gr"));
	}
	
//
//	  it should "return another Dimensionless  when multiplied by a Dimensionless" in {
//	    Each(2) * Dozen(1) should be(Dozen(2))
//	    Dozen(5) * Percent(10) should be(Each(6))
//	  }
	

	@Test
	public void returnAnotherDimensionWhenMultipliedByADimensionless(){
		
		assertThat(Each(2).multiply(Dozen(2)), is(Each(48))); 		//Returns value in Each
		assertThat(Dozen(5).multiply(Percent(10)), is(Each(6)));
	}
	
//
//	  it should "return a Frequency when divided by Time" in {
//	    Each(60) / Seconds(1) should be(Hertz(60))
//	  }
	
	@Test
	public void returnATimeWhenDividedByFrequencyIn() {
		
		assertThat(Each(60).div(Seconds(1.0)) , is(Hertz(60))); 
	}
//
//	  it should " return a Time when divided by Frequency" in {
//	    Each(60) / Hertz(60) should be(Seconds(1))
//	  }
	
	@Test
	public void returnATimeWhenDividedByFrequency() {
		
		assertThat(Each(60).div(Hertz(1)) , is(Seconds(60))); 
	}
//
//	  behavior of "CountsConversions"
//
//	  it should "provide aliases for single unit values" in {
//	    import DimensionlessConversions._
//
//	    percent should be(Percent(1))
//	    each should be(Each(1))
//	    dozen should be(Dozen(1))
//	    score should be(Score(1))
//	    gross should be(Gross(1))
//	    hundred should be(Each(1e2))
//	    thousand should be(Each(1e3))
//	    million should be(Each(1e6))
//	  }
	
	@Test 
	public void shouldProvideAliasesForSingleUnitValues() {
		
		assertThat( percent, is(Percent(1)));
		assertThat( each, is(Each(1)));
		assertThat( dozen, is(Dozen(1)));
		assertThat( score, is(Score(1)));
		assertThat( gross, is(Gross(1)));
		assertThat( hundred, is(Each(1e2)));
		assertThat( thousand, is(Each(1e3)));
		assertThat( million, is(Each(1e6)));
	}
	
//	  it should "provide implicit conversion from Double" in {
//	    import DimensionlessConversions._
//
//	    val coefficient = 10d
//	    coefficient.percent should be(Percent(coefficient))
//	    coefficient.each should be(Each(coefficient))
//	    coefficient.ea should be(Each(coefficient))
//	    coefficient.dozen should be(Dozen(coefficient))
//	    coefficient.dz should be(Dozen(coefficient))
//	    coefficient.score should be(Score(coefficient))
//	    coefficient.gross should be(Gross(coefficient))
//	    coefficient.gr should be(Gross(coefficient))
//	    coefficient.hundred should be(Each(coefficient * 1e2))
//	    coefficient.thousand should be(Each(coefficient * 1e3))
//	    coefficient.million should be(Each(coefficient * 1e6))
//	  }
//
	
	@Test
	public void testProvideImplicitConversionFromDouble(){
		
		 double d = 18;
		 assertThat(percent(d), is(Percent(d)));
		 assertThat(each(d), is(Each(d)));
		 assertThat(ea(d), is(Each(d)));
		 assertThat(dozen(d), is(Dozen(d)));
		 assertThat(dz(d), is(Dozen(d)));
		 assertThat(score(d), is(Score(d)));
		 assertThat(gross(d), is(Gross(d)));
		 assertThat(gr(d), is(Gross(d)));
		 assertThat(hundred(d), is(Each(d*1e2)));
		 assertThat(thousand(d), is(Each(d*1e3)));
		 assertThat(million(d), is(Each(d*1e6)));
	}
	
//	  it should "provide Numeric support" in {
//	    import DimensionlessConversions.DimensionlessNumeric
//
//  // The `times` operation is allowed for Dimensionless quantities
//  DimensionlessNumeric.times(Each(10), Dozen(3)) should be(Dozen(30))
//}
//}

	@Test
	public void shouldProvideNumericSupport() {
		
		//TODO: Dimensionless Numeric
	}
}
