package au.net.woodberry.ta.toolbox.indicators.volatility;

import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.CachedIndicator;
import eu.verdelhan.ta4j.indicators.helpers.TrueRangeIndicator;

public class AverageTrueRangeIndicator extends CachedIndicator<TADecimal> {

    private final int timeFrame;
    private final TrueRangeIndicator tr;

    public AverageTrueRangeIndicator(TimeSeries series, int timeFrame) {
        this.timeFrame = timeFrame;
        this.tr = new TrueRangeIndicator(series);
    }
    
    @Override
    protected TADecimal calculate(int index) {
        if (index < timeFrame) {
            return null;
        }
        TADecimal nbPeriods = TADecimal.valueOf(timeFrame);
        TADecimal nbPeriodsMinusOne = TADecimal.valueOf(timeFrame - 1);
        if (index == timeFrame) {
            TADecimal initial = TADecimal.ZERO;
            for (int i = 0; i < timeFrame; i++) {
                initial = initial.plus(tr.getValue(i));
            }
            return initial.dividedBy(nbPeriods);
        }
        return getValue(index - 1).multipliedBy(nbPeriodsMinusOne).dividedBy(nbPeriods).plus(tr.getValue(index).dividedBy(nbPeriods));    }
}
