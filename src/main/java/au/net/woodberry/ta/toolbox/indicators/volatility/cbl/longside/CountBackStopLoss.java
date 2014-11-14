package au.net.woodberry.ta.toolbox.indicators.volatility.cbl.longside;

import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class CountBackStopLoss extends CachedIndicator<TADecimal> {

    private static final int DEFAULT_COUNT_BACK_STEPS = 2;

    private final TimeSeries data;

    private final int countBackSteps;

    private final Tick tick;

    private final int tickIdx;

    private TADecimal stopLoss;

    private TADecimal highestPrice;

    /**
     * @param data TimeSeries data set
     * @param tick An initial ticker from which to build the stop loss. In Daryl Guppy's Essential Methods for Modern Trading,
     *             this is the entry bar
     * @param tickIdx The index that this tick exists within the time series data
     */
    public CountBackStopLoss(TimeSeries data, Tick tick, int tickIdx) {
        this(data, DEFAULT_COUNT_BACK_STEPS, tick, tickIdx);
    }

    public CountBackStopLoss(TimeSeries data, int countBackSteps, Tick tick, int tickIdx) {
        if (data == null) {
            throw new IllegalArgumentException("Supplied input TimeSeries is invalid: NULL");
        }
        if (countBackSteps <= 0) {
            throw new IllegalArgumentException("Supplied Count back steps is invalid: Cannot be less than or equal to 0");
        }
        if (tick == null) {
            throw new IllegalArgumentException("Supplied Tick is invalid: NULL");
        }
        this.data = data;
        this.countBackSteps = countBackSteps;
        this.tick = tick;
        this.tickIdx = tickIdx;
        this.highestPrice = tick.getMaxPrice(); // Initially the tick's max price
    }

    @Override
    protected TADecimal calculate(int i) {
        if (i == tickIdx || (i >= countBackSteps && data.getTick(i).getMaxPrice().isGreaterThan(highestPrice))) {
            stopLoss = recalculateStopLoss(data, i, countBackSteps);
            highestPrice = data.getTick(i).getMaxPrice();
        }
        return stopLoss;
    }

    private static TADecimal recalculateStopLoss(TimeSeries data, int i, int countBackSteps) {
        TimeSeries subSeries = data.subseries(data.getBegin(), i);
        TADecimal stopLoss = null;
        TADecimal lowest = subSeries.getTick(subSeries.getEnd()).getMinPrice();
        boolean foundStopLossIdx = false;
        int stopLossIdx = 0;
        int stepBacks = 0;
        if (lowest != null) {
            for (int j = subSeries.getEnd(); j >= subSeries.getBegin(); j--) {
                if (subSeries.getTick(j).getMinPrice().isLessThan(lowest)) {
                    lowest = subSeries.getTick(j).getMinPrice();
                    if (++stepBacks >= countBackSteps) { // Only look back a maximum of count back steps
                        foundStopLossIdx = true;
                        stopLossIdx = j;
                        break;
                    }
                }
            }
            stopLoss = foundStopLossIdx ? subSeries.getTick(stopLossIdx).getMinPrice()
                    : null;
        }
        return stopLoss;
    }
}
