package au.net.woodberry.ta.toolbox.indicators.trend;

import au.net.woodberry.ta.toolbox.enums.Group;
import au.net.woodberry.ta.toolbox.enums.Sustainability;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class TrendVolatilityLine extends CachedIndicator<TrendVolatilityLine.Object> {

    private final GuppyMultipleMovingAverage gmmaIndicator;
    private TADecimal entry;
    private TADecimal tvl;
    private Sustainability sustainability;

    /**
     * Construct a Trend Volatility Line indicator
     *
     * @param gmmaIndicator - The TVL is a function of the gmma (trend) and not price. This gmma is used to calculate the TVL.
     * @param index - The position within the time series data from which to start the calculation
     * @param entry - A fixed value, this is typically an entry price
     */
    public TrendVolatilityLine(GuppyMultipleMovingAverage gmmaIndicator, int index, TADecimal entry) {
        if (gmmaIndicator == null) {
            throw new IllegalArgumentException("Supplied input GuppyMultipleMovingAverage is invalid: NULL");
        }
        if (entry == null) {
            throw new IllegalArgumentException("Supplied input TADecimal entry is invalid: NULL");
        }
        this.gmmaIndicator = gmmaIndicator;
        this.entry = entry;
        TrendVolatilityLine.Object initial = calculate(index);
        this.tvl = initial.getValue();
        this.sustainability = initial.getSustainability();
    }

    public TrendVolatilityLine(GuppyMultipleMovingAverage gmmaIndicator, TADecimal entry) {
        this(gmmaIndicator, 0, entry);
    }

    @Override
    public TrendVolatilityLine.Object calculate(int i) {
        
        GuppyMultipleMovingAverage.Object gmma = gmmaIndicator.calculate(i);

        if (gmma.getValue(gmma.lowestOf(Group.SHORTTERM)).isGreaterThan(gmma.getValue(gmma.highestOf(Group.LONGTERM)))) {
            
            if (sustainability.equals(Sustainability.UNKNOWN) && tvl.isGreaterThanOrEqual(gmma.getValue(GuppyMultipleMovingAverage.Period.FIFTEEN))) {
                sustainability = Sustainability.HOPE;
            }
            if (sustainability.equals(Sustainability.HOPE) && tvl.isLessThanOrEqual(gmma.getValue(GuppyMultipleMovingAverage.Period.FIFTEEN))) {
                sustainability = Sustainability.CONFIDENT;
            }
            if (sustainability.equals(Sustainability.CONFIDENT) && tvl.isLessThanOrEqual(gmma.getValue(GuppyMultipleMovingAverage.Period.THIRTY))) {
                sustainability = Sustainability.CERTAINTY;
            }
            if (sustainability.equals(Sustainability.CERTAINTY) && tvl.isLessThanOrEqual(gmma.getValue(GuppyMultipleMovingAverage.Period.SIXTY))) {
                tvl = gmma.getValue(GuppyMultipleMovingAverage.Period.THIRTY);
            }
        } else {
            tvl = entry; // Reset any step ups in the tvl from a trend back to its original entry value.
            sustainability = Sustainability.UNKNOWN;
        }
        return new TrendVolatilityLine.Object(tvl, sustainability);
    }

    public class Object {

        private final TADecimal value;
        private final Sustainability sustainability;

        public Object(TADecimal value, Sustainability sustainability) {
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
        public boolean equals(java.lang.Object aThat) {
            if ( this == aThat ) {
                return true;
            }
            if (!(aThat instanceof TrendVolatilityLine.Object)) {
                return false;
            }
            TrendVolatilityLine.Object that = (TrendVolatilityLine.Object)aThat;
            return this.getSustainability().equals(that.getSustainability())
                    && this.getValue().equals(that.getValue());
        }
        
        @Override
        public String toString() {
            return "Value: " + value + " Sustainability: " + sustainability;
        }
    }
}
