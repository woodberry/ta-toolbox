package au.net.woodberry.ta.toolbox.indicators.volatility;

import au.net.woodberry.ta.toolbox.enums.Zone;
import au.net.woodberry.ta.toolbox.indicators.trackers.HMAIndicator;
import au.net.woodberry.ta.toolbox.object.Range;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class RangeIndicator extends CachedIndicator<Range> {
    
    private static final double DEFAULT_DISPLACEMENT_LOWER = 2.5;
    private static final double DEFAULT_DISPLACEMENT_UPPER = 3.0;
    private static final int DEFAULT_TIME_FRAME = 13;
    
    private final TimeSeries timeSeries;
    private final HMAIndicator hmaIndicator;
    private final AverageTrueRangeIndicator atrIndicator;
    private final TradersATRIndicator tradersAtrIndicator;
    private final TADecimal displacementUpper;
    
    public RangeIndicator(TimeSeries timeSeries) {
        this(timeSeries, DEFAULT_DISPLACEMENT_LOWER, DEFAULT_DISPLACEMENT_UPPER, DEFAULT_TIME_FRAME);
    }
    
    public RangeIndicator(TimeSeries timeSeries, double displacementLower, double displacementUpper, int timeFrame) {
        if (timeSeries == null) {
            throw new IllegalArgumentException("Supplied TimeSeries is invalid: NULL");
        }
        if (displacementLower <= 0) {
            throw new IllegalArgumentException("Supplied DisplacementLower is invalid: Cannot be less than or equal to zero");
        }
        if (displacementUpper <= 0) {
            throw new IllegalArgumentException("Supplied DisplacementUpper is invalid: Cannot be less than or equal to zero");
        }
        if (timeFrame <= 0) {
            throw new IllegalArgumentException("Supplied TimeFrame is invalid: Cannot be less than or equal to zero");
        }
        this.timeSeries = timeSeries;
        this.hmaIndicator = new HMAIndicator(timeSeries, timeFrame);
        this.atrIndicator = new AverageTrueRangeIndicator(timeSeries, timeFrame);
        this.tradersAtrIndicator = new TradersATRIndicator(atrIndicator, hmaIndicator, displacementLower);
        this.displacementUpper = TADecimal.valueOf(displacementUpper);
    }
    
    @Override
    protected Range calculate(int index) {
        TADecimal centralCord = hmaIndicator.getValue(index) != null ? hmaIndicator.getValue(index) : null;
        TADecimal upperDeviation = atrIndicator.getValue(index) != null ? displacementUpper.multipliedBy(atrIndicator.getValue(index)).plus(centralCord) : null;
        TADecimal lowerDeviation = tradersAtrIndicator.getValue(index) != null ? tradersAtrIndicator.getValue(index) : null;
        if (centralCord != null && upperDeviation != null && lowerDeviation != null) {
            TADecimal close = timeSeries.getTick(index).getClosePrice();
            Zone zone;
            if (close.isGreaterThanOrEqual(upperDeviation)) {
                zone = Zone.PROFIT_TAKE;
            } else if (close.isGreaterThanOrEqual(centralCord)) {
                zone = Zone.PROFIT_TAKE_HOLD;
            } else if (close.isGreaterThanOrEqual(lowerDeviation)) {
                zone = Zone.BUY_HOLD;
            } else {
                zone = Zone.SELL;
            }
            return new Range(upperDeviation, centralCord, lowerDeviation, zone);
        }
        return null;
    }
}
