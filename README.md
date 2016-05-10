Jquants 
=====
### The Java API for Mixed Calculations of Scientific Units and Sums of Money
Jquants addresses the insufficient support for Mixed Calculations of Scientific Units and Sums of Money within Java. The library has been created by translating the Scala library [Squants](https://github.com/garyKeorkunian/squants/) by GaryKeorkun into Java. The outcome is capable of reducing both, the complexity and susceptibility to errors for end-users when dealing with such calculations.

#### **Jquants provides**: 

- **Type safety**
- Close to full **test coverage** (93,3% - last updated 26.01.2016 and measured with the tool [EclEmma](http://eclemma.com/))
- **All vital quantities for business applications**  (*see Implementation*)
- About the **same performance as its model Squants** (measured with a microbenchmark test using the [Java Microbenchmark Harness tool](http://openjdk.java.net/projects/code-tools/jmh/))

The library has emerged within the work on my bachelor thesis conducted at the Technical University Munich (TUM). The thesis has addressed the creation of a solution for "Mixed Calculations of Scientific Units and Sums of Money in Java" cooperating with one of the solution-seeking users, the company [Synapplix](http://www.synapplix.de/). 
After identifying the translation of [Squants](https://github.com/garyKeorkunian/squants/), as the most viable option, the Java library Jquants has been implemented thereof. 
For further information on the analysis of currently existing solutions, Jquants' implementation or the performance test I refer to the latest version of my [bachelor thesis](https://view.publitas.com/p222-8537/mixed-calculations-of-scientific-units-and-sums-of-money-in-java/) and its [presentation](https://view.publitas.com/p222-8537/mixed-calculations-of-scientific-units-and-sums-of/) .


### Current Version ###

Current Version: V 1.0

Model Squants version: [0.6.1](https://github.com/garyKeorkunian/squants/wiki/Release-History#061-snapshot)


### Implementation ###
All within the project initially specified packages have been translated and tested with their respective unit-tests. 
All quantity values are implemented as type double; Solely Money values are held in BigDecimal.

|**Package**  |**Quantities**|
|:-------     |:------ |
|**Market**   |Money.scala|
|             |Price.scala|
|             |PricePerArea|
|             |PricePerItem|
|**Mass**     | ~~AreaDensity.scala~~|
|             |~~ChemicalAmount.scala~~|
|             |~~Density.scala~~|
|             |Mass.scala|
|**Motion**   |Acceleration.scala|
|             |AngularVelocity.scala|
|             |Force.scala|
|             |Jerk.scala|
|             |MassFlow.scala|
|             |Momentum.scala|
|             |~~Pressure.scala~~|
|             |~~PressureChange.scala~~|
|             |Velocity.scala|
|             |VolumeFlow.scala|
|             |Yank.scala|
|**Space**    |Angle.scala|
|             |Area.scala|
|             |Length.scala|
|             |SolidAngle.scala|
|             |Volume.scala|
|**Time**     |Frequency.scala|
|             |Time.scala|
|             |TimeDerivative \& Integral|
|             |TimeSquared.scala|
|**Energy**   |Energy.scala|
|             |EnergyDensity.scala|
|             |Power.scala|
|             |PowerRamp.scala|
|             |SpecificEnergy.scala|

(**~~crossed out~~** quantities were not included in the initially specified set and are thus seen as optional for implementation.)   


### Usage


When performing operations with different quantities developers often use the type double, which provides no information about the quantity, unit or conversions, ultimately leading to misunderstandings and incorrect operations. Jquants tackles this source of error by ensuring dimensional type safety and dimensional correct type conversions.

Flawed double example: 

This examples depicts the mistakes one can easily make when using the type double for quantities:
````java
double lengthINcm = 1;        // represents 1 cm
double lengthINm = 1;         // represents 1 m
double time = 10;             // represents 10 s
double sum = lengthINcm + lengthINm + time;     // falsely returns 12
````
Due to the use of double one is able to add quantities of the same dimension with different units (1 cm and 1 m) without any conversion.
Furthermore, the user is able to add quantities of different dimensions (length and time), thus resulting in a useless value.

#### Dimensional Type safety

*One may only compare, add or subtract quantities of the same dimensions.*

Therefore Jquants checks operations at compile time and automatically applies scale and type conversions at run-time: 

````java
Length lengthA = Meters(1.0);         // Length: 1 m
Length lengthB = Centimeters(100);    // Length: 1 cm
Length sum = lengthA.plus(lengthB);   // returns Meters(2)
````

This works since meters and centimeters are both units of the dimension Length.

However, if one tries to add two quantities of different dimensions the code does not compile and thus helps prevent mistakes that were easily made with the double example above.

````java
Length length = Meters(1.0);        // Length 1 m
Time time = Seconds(10.0);          // Time: 10 s
Length sum = length.plus(time);     // Invalid operation - does not compile
````


#### Dimensionally Correct Type Conversions

*One may multiply and divide quantities of different dimensions.*

Dimensionally correct type conversions are a key feature of Jquants. By having defined all valid operations and their resulting dimensions one receives the resulting dimension when multiplying or dividing two quantities of different dimensions:

````java
Time time = Hours(1.0);               // Time: 1 h
Length distance = Kilometers(120.0);  // Length: 120 km
Velocity speed = distance.div(time);  // returns KilometersPerHour(120)

Time t1 = Seconds(30.0);    // Time: 30 s
Time t2 = Minutes(1.0);     // Time: 1 m
double ratio = t1.div(t2);  // returns double: 0.5
````

#### Unit Conversions

Quantity values are based in the units you create them:

````java
Length lengthA = Meters(1.0);       // Length: 1 m
Length lengthB = Centimeters(1.0);  // Length: 1 cm
`````


While units are implicitly converted within calculations, one may also explicitly convert quantities into different units:

Convert quantity into different unit using `in`.

````java
Length lengthA = Meters(1.0);               // Length: 1 m
Length lengthB = lengthA.in(Centimeters);   // returns Centimeters(100)
````

Extract a double value converted into a specific unit using `to`.

````java
Length lengthA = Meters(1.0);               // Length: 1 m
double lengthB = lengthA.to(Centimeters);   // returns Double: 100
````


Most types also include convenient aliases for the `to` method.

````java
Length lengthA = Meters(1.0);               // Length: 1 m
double lengthB = lengthA.toCentimeters();   // returns Double: 100
double lengthC = lengthA.toDecimeters();    // returns Double: 10
double lengthD = lengthA.toFeet();          // returns Double: 3.2808333333464565 
````

Represent quantities as a String with `toString`:

````java
Length length = Meters(1.0);    // Length: 1 m
String s = length.toString();   // returns String: "1 m"
````

Represent quantities as a String converted into a different unit using `toString(<UnitOfMeasure>)`:

````java
Length length = Meters(1.0);              // Length: 1 m
String s = length.toString(Centimeters);  // returns String: "100 cm"
````


Represent quantities in tuples using `toTuple()`.

`````java
Length length = Meters(1.0);                    // Length: 1 m
Pair<Double, String> tuple = length.toTuple();  // returns Tuple: [1.0,m]
````

#### Mapping over Quantity values

Apply a `Double => Double` function to alter the value of a quantity while preserving its type and unit.

````java
Length length = Meters(1.0);      // Length: 1 m
Length mappedLength = length.map(n -> n.multiply(2).plus(Meters(1)));   // returns Meters: 3 m
````

#### Approximations

Approximate whether values lay within a specified tolerance from a quantity using `approx`.

````java
Length lengthApprox = Meters(1.998);
boolean result = lengthApprox.approx(Meters(2), Meters(0.003));     // returns true
````

#### Market Package

The market package comprises different means of dealing with money. The primary type, Money, is a Dimensional Quantity, and its Units of Measure are Currencies.

##### Money
A Quantity of purchasing power measured in Currency units.
````java
Money tenBucks = USD(10.0);       // Money: 10 USD
Money someYen = JPY(1200);        // Money: 1200 JPY
Money goldStash = XAU(50);        // Money: 50 XAU
Money digitalCache = BTC(50);     // Money: 50 BTC
````

##### Price<Quantity>
A Ratio between Money and another Quantity. A Price value is typed on a Quantity and can be denominated in any defined Currency.

*Price = Money / Quantity*

Jquants further offers the types *PricePerArea* and *PricePerItem* as being often required in business applications:

- PricePerArea 
````java
Area area = SquareMeters(100.0);      // Area: 100 m²
Money price = EUR(1000.0);            // Money: 1000 €
PricePerArea rent = price.div(area);  // returns PricePerArea: 1000.00 EUR/100.0 m²
````

- PricePerItem
````java
Dimensionless items = Each(10.0)      // Dimensionless: 10 ea
Money price = USD(100.0);             // Money: 1000 $
PricePerItem pricePerProduct = price.div(items); // returns PricePerItem: 10 $ / 1.0 ea
````

#### QuantityRanges

QuantityRanges are used to represent a range of Quantity values between an upper an lower bound.

````java
Time time1 = Seconds(1.0);     // Time: 1 s 
Time time2 = Seconds(4.0);     // Time: 4 s 
QuantityRange<Time> range = time1.to(time2);  // returns Range: 1.0 s to 4.0 s
 ````
These QuantityRanges can be used or altered by a set of methods (`contains, dec, decFrom, decFromIncTo, decTo, divide, foldLeft, foldRight, foreach, inc, incFrom, incFromDecTo,
includes, includes, incTo, map, partiallyContains, partiallyIncludes, times, toList, toQuantity, toSeq, toString`).


Apply methods to ranges by using `foreach`, `map`, `foldLeft`, or `foldRight`:

````java
QuantityRange<Length> range = new QuantityRange(Meters(0), Meters(5));        // QuantityRange: 1.0 s to 4.0 s
range.foreach(Meters(2.5), r -> System.out.print("("+r.lower.in(Centimeters)+","+r.upper.in(Centimeters)+")")); //returns (0.0 cm, 250.0 cm)(250.0 cm,500.0 cm)
ArrayList<?> mapped = range.map(2, x -> x);                       // returns [0.0 m to 2.5 m, 2.5 m to 5.0 m]
Length foldLeft = range.foldLeft(Meters(1), Meters(0),(z, x) -> z.plus(x.upper));   // returns Length: 15.0 m
````


### Possible Extensions 

The following Squants-packages embody possible extensions: 
- Electro
- Photo
- Radio
- Storage


### Installation ###
 Via Maven:
 ```xml
<dependency>
    <groupId>org.jquants</groupId>
    <artifactId>jquants</artifactId>
    <version>1.0</version>
</dependency>
 ```
 For more information check out Jquants on [Maven Central](http://search.maven.org/#artifactdetails|org.jquants|jquants|1.0|jar)
 
### Contributors ###
  • Mathias Bräu (@mbraeu)
  
  • Florian Pahl (@johnpaul2)
