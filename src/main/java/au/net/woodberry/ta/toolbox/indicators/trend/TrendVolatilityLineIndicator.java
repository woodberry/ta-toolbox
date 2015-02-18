package au.net.woodberry.ta.toolbox.indicators.trend;

import au.net.woodberry.ta.toolbox.enums.Group;
import au.net.woodberry.ta.toolbox.enums.Sustainability;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class TrendVolatilityLineIndicator extends CachedIndicator<TrendVolatilityLineIndicator.Object> {

    private final GuppyMultipleMovingAverageIndicator gmmaIndicator;
    private TADecimal entry;
    private TADecimal tvl;
    private Sustainability sustainability;

    /**
     * Construct a Trend Volatility Line indicator
     *
     * @param gmmaIndicator - The TVL is a function of price volatility and not price directly. For this,  gmma is used to calculate the TVL.
     * @param index - The position within the time series data from which to start the calculation
     * @param entry - A fixed value, this is typically an entry price
     */
    public TrendVolatilityLineIndicator(GuppyMultipleMovingAverageIndicator gmmaIndicator, int index, TADecimal entry) {
        if (gmmaIndicator == null) {
            throw new IllegalArgumentException("Supplied input GuppyMultipleMovingAverage is invalid: NULL");
        }
        if (entry == null) {
            throw new IllegalArgumentException("Supplied input TADecimal entry is invalid: NULL");
        }
        this.sustainability = Sustainability.UNKNOWN;
        this.gmmaIndicator = gmmaIndicator;
        this.entry = entry;
    }

    public TrendVolatilityLineIndicator(GuppyMultipleMovingAverageIndicator gmmaIndicator, TADecimal entry) {
        this(gmmaIndicator, 0, entry);
    }

    @Override
    public TrendVolatilityLineIndicator.Object calculate(int i) {
        
        GuppyMultipleMovingAverageIndicator.Object gmma = gmmaIndicator.calculate(i);

        if (gmma.isComplete() && gmma.getValue(gmma.lowestOf(Group.SHORTTERM)).isGreaterThan(gmma.getValue(gmma.highestOf(Group.LONGTERM)))) {

            tvl = entry;

            if (sustainability.equals(Sustainability.UNKNOWN) && tvl.isGreaterThanOrEqual(gmma.getValue(GuppyMultipleMovingAverageIndicator.Period.FIFTEEN))) {
                sustainability = Sustainability.HOPE;
            }
            if (sustainability.equals(Sustainability.HOPE) && tvl.isLessThanOrEqual(gmma.getValue(GuppyMultipleMovingAverageIndicator.Period.FIFTEEN))) {
                sustainability = Sustainability.CONFIDENT;
            }
            if (sustainability.equals(Sustainability.CONFIDENT) && tvl.isLessThanOrEqual(gmma.getValue(GuppyMultipleMovingAverageIndicator.Period.THIRTY))) {
                sustainability = Sustainability.CERTAINTY;
            }
            if (sustainability.equals(Sustainability.CERTAINTY) && tvl.isLessThanOrEqual(gmma.getValue(GuppyMultipleMovingAverageIndicator.Period.SIXTY))) {
                tvl = gmma.getValue(GuppyMultipleMovingAverageIndicator.Period.THIRTY);
            }
        } else {
            sustainability = Sustainability.UNKNOWN;
        }
        return new TrendVolatilityLineIndicator.Object(tvl, sustainability);
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
            if (!(aThat instanceof TrendVolatilityLineIndicator.Object)) {
                return false;
            }
            TrendVolatilityLineIndicator.Object that = (TrendVolatilityLineIndicator.Object)aThat;
            return this.getSustainability().equals(that.getSustainability())
                    && this.getValue().equals(that.getValue());
        }
        
        @Override
        public String toString() {
            return "Value: " + value + " Sustainability: " + sustainability;
        }
    }
}
