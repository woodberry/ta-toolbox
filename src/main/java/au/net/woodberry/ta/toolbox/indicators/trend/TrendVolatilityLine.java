package au.net.woodberry.ta.toolbox.indicators.trend;

import au.net.woodberry.ta.toolbox.enums.Group;
import au.net.woodberry.ta.toolbox.enums.Sustainability;
import eu.verdelhan.ta4j.indicators.CachedIndicator;
import eu.verdelhan.ta4j.TADecimal;

public class TrendVolatilityLine extends CachedIndicator<TrendVolatilityLine.Object> {

    private final GuppyMultipleMovingAverage gmmaIndicator;
    private final TADecimal entry;
    private TADecimal tvl;
    private Sustainability sustainability;

    public TrendVolatilityLine(GuppyMultipleMovingAverage gmmaIndicator, TADecimal entry) {
        if (gmmaIndicator == null) {
            throw new IllegalArgumentException("Supplied input GuppyMultipleMovingAverage is invalid: NULL");
        }
        if (entry == null) {
            throw new IllegalArgumentException("Supplied input TADecimal (entry) is invalid: NULL");
        }
        this.gmmaIndicator = gmmaIndicator;
        this.entry = entry;
        this.tvl = entry;
        this.sustainability = Sustainability.UNKNOWN;
    }

    @Override
    public TrendVolatilityLine.Object calculate(int i) {
        GuppyMultipleMovingAverage.Object gmma = gmmaIndicator.calculate(i);
        
        // Only under certain conditions can the TVL sustainability be determined, those which cannot are considered unknown:
        // The entire trend is beneath the entry, unknown
        sustainability = Sustainability.UNKNOWN;

        // In a long side, all the long term ema's must be below the short term ema's for the calculation to be determined
        if (gmma.getValue(gmma.lowestOf(Group.SHORTTERM)).isGreaterThan(gmma.getValue(gmma.highestOf(Group.LONGTERM)))) {

            if (gmma.getValue(GuppyMultipleMovingAverage.Period.THIRTY).isGreaterThanOrEqual(entry)) {
                sustainability = Sustainability.CERTAINTY;
            } else if (gmma.getValue(GuppyMultipleMovingAverage.Period.FIFTEEN).isGreaterThanOrEqual(entry)) {
                sustainability = Sustainability.CONFIDENT;
            } else if (gmma.getValue(GuppyMultipleMovingAverage.Period.THREE).isGreaterThanOrEqual(entry)) {
                sustainability = Sustainability.HOPE;
            }

            // Set a new TVL
            if (gmma.getValue(GuppyMultipleMovingAverage.Period.SIXTY).isGreaterThanOrEqual(tvl)) {
                tvl = gmma.getValue(GuppyMultipleMovingAverage.Period.THIRTY);
            }            
        }
        return new TrendVolatilityLine.Object(tvl, entry, sustainability);
    }

    public class Object {

        private final TADecimal value;

        private final Sustainability sustainability;

        public Object(TADecimal value, TADecimal breakEvenLine, Sustainability sustainability) {
            this.value = value;
            this.sustainability = sustainability;
        }

        public Sustainability getSustainability() {
            return sustainability;
        }

        public TADecimal getValue() {
            return value;
        }
    }
}
