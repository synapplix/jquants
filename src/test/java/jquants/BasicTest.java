package jquants;

import static jquants.energy.Power.Kilowatts;
import static jquants.energy.Power.Milliwatts;
import static jquants.energy.Power.Watts;

import static jquants.space.Length.*;

import static jquants.market.Money.JPY;
import static jquants.market.Money.USD;
import static jquants.time.Time.Hours;
import static org.junit.Assert.*;

import jquants.space.Area;

import org.junit.Test;

import com.googlecode.totallylazy.time.Seconds;

import jquants.energy.Energy;
import jquants.energy.Power;
import jquants.market.CurrencyExchangeRate;
import jquants.market.Money;
import jquants.time.Time;
import jquants.space.Length;
import jquants.mass.*;

public class BasicTest {

  @Test
  public void testUnits() {
    Power load = Kilowatts(1.2);
    Time time = Hours(2);
    
    
    double[] randomValues = new double[40];
    
    //fill the array with random values from 0 - 100
    for (int i=0; i<40; i++){
      randomValues[i] = (Math.random()) * 100;
    };
    
    Area area0 = Meters(randomValues[0]).multiply(Centimeters(randomValues[1]));
    double areainSquareMeters0 = randomValues[0] * (0.01 *(randomValues[1])); 
    assertTrue(Double.compare(area0.value, areainSquareMeters0)==0);
//    System.out.println(area0.toString());
//    System.out.println(areainSquareMeters0);
//  Energy energyUsed = load.multiply(time);
//  assertEquals(KilowattHours(2.4), energyUsed);
    Length l1 = Meters(12.0);
    Length l2 = Centimeters(10);
    Length l3 = l1.plus(l2);
    
    Length testlength = Meters(4.215555324);
    Area sum = testlength.multiply(testlength);
//    System.out.print(sum.value);
//    System.out.println(l3.getDimension());
//  System.out.println(l3);

	Power a = Watts(10.0);
	Power doubled =  a.plus(a);
//	System.out.println(doubled);
    
    Power b1 = Watts(12.0);
	Power a1 = Kilowatts(10.0);
	Power sum1 =  b1.plus(a1);
//  System.out.println(sum1);
    
    double minutes = time.toMinutes();
    assertEquals(120, minutes, 0);

    double seconds = time.toSeconds();
    assertEquals(120 * 60, seconds, 0);

//    Power aveLoad = energyUsed.d / time
//        aveLoad should be(Kilowatts(1.2)
  }
  
  @Test
  public void testFXSupport() {
    // create an exchange rate
    CurrencyExchangeRate rate = new CurrencyExchangeRate(USD(1), JPY(100));

    Money someYen = JPY(350);
    Money someBucks = USD(23.50);

    // Use the convert method which automatically converts the money to the 'other' currency
    Money dollarAmount = rate.convert(someYen); // returns USD(3.5)
    assertEquals(3.5, dollarAmount.value, 0);
    Money yenAmount = rate.convert(someBucks);  // returns JPY(2350)
    assertEquals(2350, yenAmount.value, 0);
    

    // or just use the * operator in either direction (money * rate, or rate * money)
    Money dollarAmount2 = rate.mult(someYen); // returns USD(3.5)
    assertEquals(3.5, dollarAmount2.value, 0);
    Money yenAmount2 = someBucks.multiply(rate);  // returns JPY(2350)
    assertEquals(2350, yenAmount2.value, 0);
  }
}
