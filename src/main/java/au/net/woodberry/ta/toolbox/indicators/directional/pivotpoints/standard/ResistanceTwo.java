package au.net.woodberry.ta.toolbox.indicators.directional.pivotpoints.standard;

import au.net.woodberry.ta.toolbox.indicators.directional.pivotpoints.AbstractPivotPoint;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;

import java.util.List;

public class ResistanceTwo extends AbstractPivotPoint {

    private final PivotPoint pivotPoint;

    /**
     * @see PivotPoint
     */
    public ResistanceTwo(List<TimeSeries> data, PivotPoint pivotPoint) {
        super(data);
        this.pivotPoint = pivotPoint;
    }

    @Override
    protected TADecimal calculate(int i) {
        TADecimal pivotPointValue = pivotPoint.getValue(i);
        TADecimal prevLow = getPrevLow(i);
        TADecimal prevHigh = getPrevHigh(i);
        if (pivotPointValue != null && prevLow != null && prevHigh != null) {
            return pivotPointValue.plus((prevHigh.minus(prevLow)));
        }
        return null;
    }
}
