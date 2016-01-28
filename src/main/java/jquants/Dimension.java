package jquants;

import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.Some.some;
import static com.googlecode.totallylazy.regex.Regex.regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.MatchResult;

import jquants.market.Money.QuantityStringParseException;
import jquants.space.Length;

import com.googlecode.totallylazy.None;
import com.googlecode.totallylazy.Option;
import com.googlecode.totallylazy.Sequence;
import com.googlecode.totallylazy.predicates.LogicalPredicate;
import com.googlecode.totallylazy.regex.Regex;

@SuppressWarnings("rawtypes")
public class Dimension<A extends Quantity<A>> {
  public String name;
  public UnitOfMeasure<A> valueUnit;
  public Set<UnitOfMeasure<A>> units;

  private static List<Dimension> companions = new ArrayList<Dimension>();

  public Dimension(String name, UnitOfMeasure valueUnit, Sequence<? extends UnitOfMeasure> units) {
    this.name = name;
    this.valueUnit = valueUnit;
    this.units = (Set<UnitOfMeasure<A>>) units.toSet();
  }

  private static void initQuantityCompanions() {
    companions.add(Length.Length);
  }
  
  public UnitOfMeasure get(final String symbol) {
    if (companions.size() == 0) initQuantityCompanions();
    
    for (Dimension c : companions) {
      Option unit = c.symbolToUnit(symbol);
      if (!unit.isEmpty()) return (UnitOfMeasure) unit.get();
    }
    return null;
  }
  
  public Option<UnitOfMeasure<A>> symbolToUnit(final String symbol) {
    return sequence(this.units).find(new LogicalPredicate<UnitOfMeasure>() {
      public boolean matches(UnitOfMeasure u) {
        return u.symbol.equals(symbol);
      }
    });
  }

  public Option<A> parseString(String s) {
    String pattern = "([-+]?[0-9]*\\.?[0-9]+) *(" + sequence(units).toString("|") + ")$";
	Regex regex = regex(pattern);
    if (regex.matches(s)) {
      MatchResult match = regex.match(s);
      Option<UnitOfMeasure<A>> unit = symbolToUnit(match.group(2));
      double value = new Double(match.group(1)).doubleValue();
      return some(unit.get().apply(value));
    } else {
      throw new QuantityStringParseException("Unable to parse " + name + " " + s);
    }
  }
}
