package au.net.woodberry.ta.toolbox.object;

import au.net.woodberry.ta.toolbox.enums.Sustainability;
import eu.verdelhan.ta4j.TADecimal;

public class TrendVolatilityLine {

    private final TADecimal value;
    private final Sustainability sustainability;

    public TrendVolatilityLine(TADecimal value, Sustainability sustainability) {
        this.value = value;
        this.sustainability = sustainability;
    }

    public Sustainability getSustainability() {
        return sustainability;
    }

    public TADecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object aThat) {
        if ( this == aThat ) {
            return true;
        }
        if (!(aThat instanceof TrendVolatilityLine)) {
            return false;
        }
        TrendVolatilityLine that = (TrendVolatilityLine)aThat;
        return this.getSustainability().equals(that.getSustainability())
                && this.getValue().equals(that.getValue());
    }

    @Override
    public String toString() {
        return "Value: " + value + " Sustainability: " + sustainability;
    }
}
