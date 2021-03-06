package au.net.woodberry.ta.toolbox.indicators.trackers;

import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class EMAIndicator extends CachedIndicator<TADecimal> {

    private final Indicator<? extends TADecimal> indicator;

    private final int timeFrame;

    /**
     * An EMA indicator, unlike
     * eu.verdelhan.ta4j.indicators.trackers.EMAIndicator, this does not seed the initial EMA with an SMA value
     * Instead, it uses the first value as supplied by the input indicator.
     *
     * @param indicator An indicator, normally a closing price indicator
     * @param timeFrame An applied time frame to perform the calculation
     */
    public EMAIndicator(Indicator<? extends TADecimal> indicator, int timeFrame) {
        this.indicator = indicator;
        this.timeFrame = timeFrame;
    }

    private TADecimal multiplier() {
        return TADecimal.TWO.dividedBy(TADecimal.valueOf(timeFrame + 1));
    }

    @Override
    protected TADecimal calculate(int index) {
        if (index == 0) {
            return indicator.getValue(0);
        }
        TADecimal emaPrev = getValue(index - 1);
        return indicator.getValue(index).minus(emaPrev).multipliedBy(multiplier()).plus(emaPrev);
    }
}
