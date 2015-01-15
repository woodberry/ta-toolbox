package au.net.woodberry.ta.toolbox.indicators.volatility;

import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class CountBackLine extends CachedIndicator<TADecimal> {

    private static final int DEFAULT_COUNT_BACK_STEPS = 2;

    private final Integer countBackIdx;

    private TADecimal countBackLine;

    /**
     * @param data TimeSeries data containing price ticker information
     * @param tickIdx Index position within the time-series data that contains the lowest pivot point
     */
    public CountBackLine(TimeSeries data, int tickIdx) {
        this(data, tickIdx, DEFAULT_COUNT_BACK_STEPS);
    }

    /**
     * @param data TimeSeries data containing price ticker information
     * @param pivotPtIdx Index position within the time-series data that contains the lowest pivot point
     * @param countBackSteps Number of steps to count back by
     */
    public CountBackLine(TimeSeries data, int pivotPtIdx, int countBackSteps) {
        if (data == null) {
            throw new IllegalArgumentException("Supplied input TimeSeries is invalid: NULL");
        }
        if (countBackSteps <= 0) {
            throw new IllegalArgumentException("Supplied input count back steps is invalid: Cannot be less than or equal to 0");
        }
        this.countBackIdx = countBack(data, pivotPtIdx, countBackSteps);
        this.countBackLine = countBackIdx != null ? data.getTick(countBackIdx).getMaxPrice() : null;
    }

    @Override
    protected TADecimal calculate(int i) {
        return (countBackIdx != null && i >= countBackIdx) ? countBackLine : null;
    }

    // Algorithm to perform the index position of the line. Note: Only supports long side trading!
    private static Integer countBack(TimeSeries data, int pivotPtIdx, int countBackSteps) {
        Integer cblIdx = null;
        if (pivotPtIdx >= countBackSteps) {
            int stepBacks = 0;
            TADecimal highest = data.getTick(pivotPtIdx).getMaxPrice();
            for (int j = pivotPtIdx; j >= data.getBegin(); j--) {
                if (data.getTick(j).getMaxPrice().isGreaterThan(highest)) {
                    highest = data.getTick(j).getMaxPrice();
                    if (++stepBacks >= countBackSteps) { // Only look back a maximum of count back steps
                        cblIdx = j;
                        break;
                    }
                }
            }
        }
        return cblIdx;
    }
}
