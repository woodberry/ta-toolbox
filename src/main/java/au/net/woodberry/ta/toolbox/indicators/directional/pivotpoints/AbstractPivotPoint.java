package au.net.woodberry.ta.toolbox.indicators.directional.pivotpoints;

import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

import java.util.List;

public abstract class AbstractPivotPoint extends CachedIndicator<TADecimal> {

    private final List<TimeSeries> data;

    /**
     * An abstract base class that provides common utility methods for getting the previous close / high and low
     * values
     */
    protected AbstractPivotPoint(List<TimeSeries> data) {
        if (data == null) {
            throw new IllegalArgumentException("Supplied input TimeSeries is invalid: NULL");
        }
        if (data.isEmpty()) {
            throw new IllegalArgumentException("Supplied input TimeSeries is invalid: Empty");
        }
        this.data = data;
    }

    protected TADecimal getPrevClose(int i) {
        if (i < data.get(0).getSize()) { // First period, previous cannot be calculated
            return null;
        }
        int dataIdx = dataIdx(data, i);
        TimeSeries periodData = data.get(dataIdx - 1);
        return periodData.getTick(periodData.getEnd()).getClosePrice();
    }

    // TODO: Make getPrev/High more smart, since once the pivot point is calculated these remain fixed
    protected TADecimal getPrevHigh(int i) {
        if (i < data.get(0).getSize()) { // First period, previous cannot be calculated
            return null;
        }
        int dataIdx = dataIdx(data, i);
        TimeSeries prevData = data.get(dataIdx - 1);
        TADecimal high = null;
        if (prevData != null) {
            for (int j = prevData.getBegin(); j <= prevData.getEnd(); j++) {
                if (high == null || prevData.getTick(j).getMaxPrice().isGreaterThan(high)) {
                    high = prevData.getTick(j).getMaxPrice();
                }
            }
        }
        return high;
    }

    protected TADecimal getPrevLow(int i) {
        if (i < data.get(0).getSize()) { // First period, previous cannot be calculated
            return null;
        }
        int dataIdx = dataIdx(data, i);
        TimeSeries prevData = data.get(dataIdx - 1);
        TADecimal low = null;
        if (prevData != null) {
            for (int j = prevData.getBegin(); j <= prevData.getEnd(); j++) {
                if (low == null || prevData.getTick(j).getMinPrice().isLessThan(low)) {
                    low = prevData.getTick(j).getMinPrice();
                }
            }
        }
        return low;
    }

    // Returns the 'true' index after joining the list of time series data together
    private static int dataIdx(List<TimeSeries> data, int i) {
        int j = 0;
        int cumulative = 0;
        for (TimeSeries timeSeries : data) {
            cumulative += timeSeries.getSize();
            if (i < cumulative) {
                break;
            }
            j++;
        }
        return j;
    }

}
