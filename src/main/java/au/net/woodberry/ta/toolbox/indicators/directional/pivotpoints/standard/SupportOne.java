package au.net.woodberry.ta.toolbox.indicators.directional.pivotpoints.standard;

import au.net.woodberry.ta.toolbox.indicators.directional.pivotpoints.AbstractPivotPoint;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;

import java.util.List;

public class SupportOne extends AbstractPivotPoint {

    private final PivotPoint pivotPoint;

    /**
     * @see PivotPoint
     */
    public SupportOne(List<TimeSeries> data, PivotPoint pivotPoint) {
        super(data);
        this.pivotPoint = pivotPoint;
    }

    @Override
    protected TADecimal calculate(int i) {
        TADecimal pivotPointValue = pivotPoint.getValue(i);
        TADecimal prevHigh = getPrevHigh(i);
        if (pivotPointValue != null && prevHigh != null) {
            return (pivotPointValue.multipliedBy(TADecimal.TWO)).minus(prevHigh);
        }
        return null;
    }
}
