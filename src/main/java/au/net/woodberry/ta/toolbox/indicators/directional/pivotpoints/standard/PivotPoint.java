package au.net.woodberry.ta.toolbox.indicators.directional.pivotpoints.standard;

import au.net.woodberry.ta.toolbox.indicators.directional.pivotpoints.AbstractPivotPoint;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;

import java.util.List;

public class PivotPoint extends AbstractPivotPoint {

    /**
     * @param data A list of TimeSeries data split into periods (See below).
     *             The split TimeSeries data, once joined again, is assumed to be one continuous TimeSeries.
     *
     *             The pivot points always use the previous period's data to calculate the current period's pivot points.
     *             For 1, 5, 10 and 15 minute charts this is the prior day's high, low and close
     *             For 30 and 60 minute charts this is the prior week's high, low and close
     *             For daily charts this is the prior month's data
     *
     * Reference: http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:pivot_points
     */
    public PivotPoint(List<TimeSeries> data) {
        super(data);
    }

    @Override
    protected TADecimal calculate(int i) {
        TADecimal prevHigh = getPrevHigh(i);
        TADecimal prevClose = getPrevClose(i);
        TADecimal prevLow = getPrevLow(i);
        if (prevHigh != null && prevClose != null && prevLow != null) {
            return prevHigh.plus(prevLow).plus(prevClose).dividedBy(TADecimal.THREE);
        }
        return null;
    }
}
