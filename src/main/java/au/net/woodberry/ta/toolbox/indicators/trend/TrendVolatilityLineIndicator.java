package au.net.woodberry.ta.toolbox.indicators.trend;

import au.net.woodberry.ta.toolbox.enums.Group;
import au.net.woodberry.ta.toolbox.enums.Period;
import au.net.woodberry.ta.toolbox.enums.Sustainability;
import au.net.woodberry.ta.toolbox.object.MultipleMovingAverage;
import au.net.woodberry.ta.toolbox.object.TrendVolatilityLine;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class TrendVolatilityLineIndicator extends CachedIndicator<TrendVolatilityLine> {

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
    public TrendVolatilityLine calculate(int i) {
        
        MultipleMovingAverage gmma = gmmaIndicator.calculate(i);

        if (gmma.isComplete() && gmma.getValue(gmma.lowestOf(Group.SHORTTERM)).isGreaterThan(gmma.getValue(gmma.highestOf(Group.LONGTERM)))) {

            tvl = entry;

            if (sustainability.equals(Sustainability.UNKNOWN) && tvl.isGreaterThanOrEqual(gmma.getValue(Period.FIFTEEN))) {
                sustainability = Sustainability.HOPE;
            }
            if (sustainability.equals(Sustainability.HOPE) && tvl.isLessThanOrEqual(gmma.getValue(Period.FIFTEEN))) {
                sustainability = Sustainability.CONFIDENT;
            }
            if (sustainability.equals(Sustainability.CONFIDENT) && tvl.isLessThanOrEqual(gmma.getValue(Period.THIRTY))) {
                sustainability = Sustainability.CERTAINTY;
            }
            if (sustainability.equals(Sustainability.CERTAINTY) && tvl.isLessThanOrEqual(gmma.getValue(Period.SIXTY))) {
                tvl = gmma.getValue(Period.THIRTY);
            }
        } else {
            sustainability = Sustainability.UNKNOWN;
        }
        return new TrendVolatilityLine(tvl, sustainability);
    }
}
