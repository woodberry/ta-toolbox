package au.net.woodberry.ta.toolbox.indicators.trend;

import au.net.woodberry.ta.toolbox.enums.Group;
import au.net.woodberry.ta.toolbox.enums.Sustainability;
import au.net.woodberry.ta.toolbox.object.MultipleMovingAverage;
import au.net.woodberry.ta.toolbox.object.TrendVolatilityLine;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

import java.util.ArrayList;
import java.util.List;

public class TrendVolatilityLineIndicator extends CachedIndicator<TrendVolatilityLine> {

    private final Indicator<? extends MultipleMovingAverage> mmaIndicator;
    private final TADecimal entry;
    private List<TADecimal> tvls;
    private Sustainability sustainability;

    /**
     * Construct a Trend Volatility Line indicator
     *
     * @param mmaIndicator - The TVL is a function of price volatility and not price directly. For this, a multiple moving average indicator is used.
     * @param index - The position within the time series data from which to start the calculation
     * @param entry - A fixed value, this is typically an entry price
     */
    public TrendVolatilityLineIndicator(Indicator<? extends MultipleMovingAverage> mmaIndicator, int index, TADecimal entry) {
        if (mmaIndicator == null) {
            throw new IllegalArgumentException("Supplied input Indicator is invalid: NULL");
        }
        if (entry == null) {
            throw new IllegalArgumentException("Supplied input TADecimal entry is invalid: NULL");
        }
        this.sustainability = Sustainability.UNKNOWN;
        this.mmaIndicator = mmaIndicator;
        this.entry = entry;
        this.tvls = new ArrayList<>();
        tvls.add(entry);
    }

    public TrendVolatilityLineIndicator(Indicator<? extends MultipleMovingAverage> mmaIndicator, TADecimal entry) {
        this(mmaIndicator, 0, entry);
    }

    @Override
    protected TrendVolatilityLine calculate(int index) {
        
        MultipleMovingAverage mma = mmaIndicator.getValue(index);
        TADecimal tvl = null;
        if (mma.isComplete() && mma.getValue(mma.lowestOf(Group.SHORTTERM)).isGreaterThan(mma.getValue(mma.highestOf(Group.LONGTERM)))) {
            
            if (!sustainability.equals(Sustainability.CERTAINTY)) {
                
                if (sustainability.equals(Sustainability.UNKNOWN) && entry.isGreaterThanOrEqual(mma.getValue(mma.longestOf(Group.SHORTTERM)))) {
                    sustainability = Sustainability.HOPE;
                }
                if (sustainability.equals(Sustainability.HOPE) && entry.isLessThanOrEqual(mma.getValue(mma.longestOf(Group.SHORTTERM)))) {
                    sustainability = Sustainability.CONFIDENT;
                }
                if (sustainability.equals(Sustainability.CONFIDENT) && entry.isLessThanOrEqual(mma.getValue(mma.shortestOf(Group.LONGTERM)))) {
                    sustainability = Sustainability.CERTAINTY;
                }
            }
            if (sustainability.equals(Sustainability.CERTAINTY) && lastTvl().isLessThanOrEqual(mma.getValue(mma.longestOf(Group.LONGTERM)))) {
                tvls.add(mma.getValue(mma.shortestOf(Group.LONGTERM)));
            }
            tvl = lastTvl();
        } else {
            sustainability = Sustainability.UNKNOWN;
        }
        return new TrendVolatilityLine(tvl, sustainability);
    }
    
    private TADecimal lastTvl() {
        return tvls.get(tvls.size() - 1);
    }
}
