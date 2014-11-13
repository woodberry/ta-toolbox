package au.net.woodberry.ta.toolbox.indicators.volatility.cbl.longside;

import eu.verdelhan.ta4j.indicators.CachedIndicator;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;

public class CountBackLine extends CachedIndicator<TADecimal> {

    private static final int DEFAULT_COUNT_BACK_STEPS = 2;

    private final TimeSeries data;

    private TADecimal minPrice;

    private int countBackSteps;

    private TADecimal cblEntryLine;

    public CountBackLine(TimeSeries data) {
        this(data, DEFAULT_COUNT_BACK_STEPS);
    }

    public CountBackLine(TimeSeries data, int countBackSteps) {
        if (data == null) {
            throw new IllegalArgumentException("Suppled input TimeSeries is invalid: NULL");
        }
        if (countBackSteps <= 0) {
            throw new IllegalArgumentException("Supplied Count back steps is invalid: Cannot be less than or equal to 0");
        }
        this.data = data;
        this.countBackSteps = countBackSteps;
        this.minPrice = data.getTick(0).getMinPrice();
    }

    @Override
    protected TADecimal calculate(int i) {
        if (data.getTick(i).getMinPrice().isLessThan(minPrice)) {
            minPrice = data.getTick(i).getMinPrice();
            if (i >= countBackSteps) {
                TADecimal highest = data.getTick(i).getMaxPrice();
                int stepBacks = 0;
                int cblIdx = 0;
                for (int j = i; j >= data.getBegin(); j--) { // Loop backward to find the CBL
                    if (data.getTick(j).getMaxPrice().isGreaterThan(highest)) {
                        highest = data.getTick(j).getMaxPrice();
                        if (++stepBacks >= countBackSteps) { // Only look back a maximum of count back steps
                            cblIdx = j;
                            break;
                        }
                    }
                }
                cblEntryLine = cblIdx != 0 ? data.getTick(cblIdx).getMaxPrice() : null;
            }
        }
        if (cblEntryLine != null && (i - 2) >= data.getBegin()) {
            if (data.getTick(i - 2).getClosePrice().isGreaterThan(cblEntryLine)) { // Reset CBL after the entry
                minPrice = data.getTick(i).getMinPrice();
                cblEntryLine = null;
            }
        }
        return cblEntryLine;
    }

}
