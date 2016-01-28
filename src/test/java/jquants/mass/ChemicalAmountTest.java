package jquants.mass;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static jquants.mass.Mass.*;
import static jquants.mass.ChemicalAmount.*;

import org.junit.Test;

public class ChemicalAmountTest {

	@Test
	public void testCreateValuesUsingUOMFactories() {
		assertThat(Moles(1).toMoles(), is(1d));
	}
	
	@Test
	public void testCreateValuesFromProperlyFormattedStrings() {
		assertThat(toChemicalAmount("10.22 mol").get(), is(Moles(10.22)));
		assertThat(toChemicalAmount("10.22 lb-mol").get(), is(PoundMoles(10.22)));
//		assertThat(toChemicalAmount("10.45 zz").get(), is(Moles(10.22)));
//		assertThat(toChemicalAmount("zz mol").get(), is(Moles(10.22)));
	}
//  it should "create values from properly formatted Strings" in {
//  ChemicalAmount("10.22 mol").get should be(Moles(10.22))
//  ChemicalAmount("10.22 lb-mol").get should be(PoundMoles(10.22))
//  ChemicalAmount("10.45 zz").failed.get should be(QuantityStringParseException("Unable to parse ChemicalAmount", "10.45 zz"))
//  ChemicalAmount("zz mol").failed.get should be(QuantityStringParseException("Unable to parse ChemicalAmount", "zz mol"))
//}
	
	@Test
	public void testProperlyConvertToAllSupportedUnitsOfMeasure() {
		ChemicalAmount x = Moles(1);
		assertThat(x.toMoles(), is(1d));
		assertThat(x.toPoundMoles(), is(1 / 453.59237));
	}

	@Test
	public void testReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure() {
		assertThat(Moles(1).toString(), is("1.0 mol"));
		assertThat(PoundMoles(1).toString(), is("1.0 lb-mol"));
	}

	@Test
	public void testProvideAliasesForSingleUnitValues() {
		assertThat(mole, is(Moles(1)));
		assertThat(poundMole, is(PoundMoles(1)));
	}
	
	@Test
	public void testProvideImplicitConversionFromDouble() {
		double d = 10d;
		assertThat(moles(d), is(Moles(d)));
		assertThat(poundMoles(d), is(PoundMoles(d)));
	}
	
	@Test
	public void testProvideNumericSupport() {

	}
	
//  it should "provide Numeric support" in {
//  import ChemicalAmountConversions.ChemicalAmountNumeric
//
//  val cas = List(Moles(100), Moles(10))
//  cas.sum should be(Moles(110))
//}
	
}


