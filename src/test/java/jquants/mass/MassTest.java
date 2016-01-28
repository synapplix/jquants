package jquants.mass;

import static com.googlecode.totallylazy.Sequences.sequence;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static jquants.mass.Mass.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jquants.MetricSystem;
import jquants.motion.Velocity.*;
import static jquants.space.Area.*;
import static jquants.space.Volume.*;
import static jquants.time.Time.*;

import org.junit.Test;

import static jquants.MetricSystem.*;

public class MassTest {

	@Test
	public void testCreateValuesUsingUOMFactories() {
		assertThat(Grams(10.22).toGrams(), is(10.22));
		assertThat(Micrograms(10.22).toMicrograms(), is(10.22));
		assertThat(Milligrams(10.22).toMilligrams(), is(10.22));
		assertThat(Kilograms(10.22).toKilograms(), is(10.22));
		assertThat(Tonnes(10.22).toTonnes(), is(10.22));
		assertThat(Pounds(10.22).toPounds(), is(10.22));
		assertThat(Ounces(10.22).toOunces(), is(10.22));
	}

	@Test
	public void testCreateValuesFromProperlyFormattedStrings() {
		assertThat(toMass("10.22 mcg").get(), is(Micrograms(10.22)));
		assertThat(toMass("10.22 mg").get(), is(Milligrams(10.22)));
		assertThat(toMass("10.22 g").get(), is(Grams(10.22)));
		assertThat(toMass("10.22 kg").get(), is(Kilograms(10.22)));
		assertThat(toMass("10.22 t").get(), is(Tonnes(10.22)));
		assertThat(toMass("10.22 lb").get(), is(Pounds(10.22)));
		assertThat(toMass("10.22 oz").get(), is(Ounces(10.22)));
//		assertThat(toMass("10.45 zz").get(), is(Grams(10.22)));
//		assertThat(toMass("zz g").get(), is(Grams(10.22)));
	}

//  Mass("10.45 zz").failed.get should be(QuantityStringParseException("Unable to parse Mass", "10.45 zz"))
//  Mass("zz g").failed.get should be(QuantityStringParseException("Unable to parse Mass", "zz g"))
	

	@Test
	public void testProperlyConvertToAllSupportedUnitsOfMeasure() {
		Mass x = Grams(1);
		assertThat(x.toMicrograms(), is(1 / MetricSystem.Micro));
		assertThat(x.toMilligrams(), is(1 / MetricSystem.Milli));
		assertThat(x.toGrams(), is(1d));
		assertThat(x.toKilograms(), is(1 / MetricSystem.Kilo));
		assertThat(x.toTonnes(), is(1 / MetricSystem.Mega));
		assertThat(x.toPounds(), is(1 / Pounds.multiplier));
		assertThat(x.toOunces(), is(1 / Pounds.multiplier * 16d));
		
//		It isn't recognised, that the two quantities are always identical
//		assertThat(Grams(1000), is(Kilograms(1)));
//		assertThat(Kilograms(0.45359237), is(Pounds(1)));
//		assertThat(Ounces(16), is(Pounds(1)));
	}
	
	@Test
	public void testReturnProperlyFormattedStringsForAllSupportedUnitsOfMeasure() {
		assertThat(Micrograms(1).toString(), is("1.0 mcg"));
		assertThat(Milligrams(1).toString(), is("1.0 mg"));
		assertThat(Grams(1).toString(), is("1.0 g"));
		assertThat(Kilograms(1).toString(), is("1.0 kg"));
		assertThat(Tonnes(1).toString(), is("1.0 t"));
		assertThat(Pounds(1).toString(), is("1.0 lb"));
		assertThat(Ounces(1).toString(), is("1.0 oz"));
	}
	

	
	
//	@Test
//	public void testReturnMomentumWhenMultipliedByVelocity() {
//		assertThat(Kilograms(1).multiplyMetersPerSecond(1)), is(NewtonSeconds(1)));
//	}
//	
//	@Test
//	public void testReturnForceWhenMultipliedByAcceleration() {
//		assertThat(Kilograms(1).multiply(MetersPerSecondSquared(1)), is(Newtons(1)));
//	}
//	
//	@Test
//	public void testReturnVolumeWhenDividedByDensity() {
//		assertThat(Kilograms(1).div(KilogramsPerCubicMeter(1)), is(CubicMeters(1)));
//	}
//	
//	@Test
//	public void testReturnTimeWhenDividedByMassFlowRate() {
//		assertThat(Kilograms(1).div(KilogramsPerSecond(1)), is(Seconds(1)));
//	}
//	
//	@Test
//	public void testReturnMassFlowRateWhenDividedByTime() {
//		assertThat(Kilograms(1).div(Seconds(1)), is(KilogramsPerSecond(1)));
//	}
//
//	@Test
//	public void testReturnDensityWhenDividedByVolume() {
//		assertThat(Kilograms(1).div(CubicMeters(1)), is(KilogramsPerCubicMeter(1)));
//	}
//
//	@Test
//	public void testReturnAreaDensityWhenDividedByArea() {
//		assertThat(Kilograms(1).div(SquareMeters(1)), is(KilogramsPerSquareMeter(1)));
//	}
//
//	private Object KilogramsPerSquareMeter(int i) {
//		throw new UnsupportedOperationException("not yet implemented");
//	}
//
//	@Test
//	public void testReturnAreaWhenDividedByAreaDensity() {
//		assertThat(Kilograms(1).div(KilogramsPerSquareMeter(1)), is(SquareMeters(1)));
//	}
//

	@Test
	public void testProvideAliasesForSingleUnitValues() {
		assertThat(microgram, is(Micrograms(1)));
		assertThat(milligram, is(Milligrams(1)));
		assertThat(gram, is(Grams(1)));
		assertThat(kilogram, is(Kilograms(1)));
		assertThat(tonne, is(Tonnes(1)));
		assertThat(pound, is(Pounds(1)));
		assertThat(ounce, is(Ounces(1)));
	}
	
	@Test
	public void testProvideImplicitConversionFromDouble() {
		double d = 10.22;
		assertThat(mcg(d), is(Micrograms(d)));
		assertThat(mg(d), is(Milligrams(d)));
		assertThat(milligrams(d), is(Milligrams(d)));
		assertThat(g(d), is(Grams(d)));
		assertThat(grams(d), is(Grams(d)));
		assertThat(kg(d), is(Kilograms(d)));
		assertThat(kilograms(d), is(Kilograms(d)));
		assertThat(tonnes(d), is(Tonnes(d)));
		assertThat(pounds(d), is(Pounds(d)));
		assertThat(ounces(d), is(Ounces(d)));
	}

	@Test
	public void testProvideImplicitConversionsFromArbitraryNumericTypes() {
		int i = 10;
		assertThat(grams(i), is(Grams(i)));
		
		BigDecimal bd = new BigDecimal(10.22);
		assertThat(grams(bd.doubleValue()), is(Grams(bd.doubleValue())));
	}
	
	
	@Test
	public void testProvideImplicitConversionsFromString() {
		assertThat(toMass("10.45 mcg").get(), is(Micrograms(10.45)));
		assertThat(toMass("10.45 mg").get(), is(Milligrams(10.45)));
		assertThat(toMass("10.45 g").get(), is(Grams(10.45)));
		assertThat(toMass("10.45 kg").get(), is(Kilograms(10.45)));
		assertThat(toMass("10.45 t").get(), is(Tonnes(10.45)));
		assertThat(toMass("10.45 lb").get(), is(Pounds(10.45)));
		assertThat(toMass("10.45 oz").get(), is(Ounces(10.45)));
//		assertThat(toMass("10.45 zz").get(), is(Micrograms(10.45)));
//		assertThat(toMass("zz oz").get(), is(Micrograms(10.45)));
	}

//  "10.45 zz".toMass.failed.get should be(QuantityStringParseException("Unable to parse Mass", "10.45 zz"))
//  "zz oz".toMass.failed.get should be(QuantityStringParseException("Unable to parse Mass", "zz oz"))


//	@Test
//	public void testProvideNumericSupport() {
//		
//		ArrayList ms = List(Grams(1000), Kilograms(1));
//		assertThat(ms.sum(), is(Kilograms(11)));
//	}

}

