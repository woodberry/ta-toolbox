package au.net.woodberry.ta.toolbox.indicators.directional;

import au.net.woodberry.ta.toolbox.object.PivotPoint;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class StandardPivotPointIndicator extends CachedIndicator<PivotPoint> {

    private final PivotPoint result;
    private TADecimal pivotPoint;
    private TADecimal resistanceOne;
    private TADecimal resistanceTwo;
    private TADecimal supportOne;
    private TADecimal supportTwo;
    
    /**
     * Reference: http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:pivot_points
     *
     * @param prevHigh - The previous period's high value
     * @param prevLow - The previous period's low value
     * @param prevClose - The previous period's close value
     */
    public StandardPivotPointIndicator(TADecimal prevHigh, TADecimal prevLow, TADecimal prevClose) {
        if (prevHigh == null) {
            throw new IllegalArgumentException("Supplied TADecimal (previous high) is invalid: NULL");
        }
        if (prevLow == null) {
            throw new IllegalArgumentException("Supplied TADecimal (previous low) is invalid: NULL");
        }
        if (prevClose == null) {
            throw new IllegalArgumentException("Supplied TADecimal (previous close) is invalid: NULL");
        }
        this.pivotPoint = prevHigh.plus(prevLow).plus(prevClose).dividedBy(TADecimal.THREE);
        this.resistanceOne = (pivotPoint.multipliedBy(TADecimal.TWO)).minus(prevLow);
        this.resistanceTwo = pivotPoint.plus((prevHigh.minus(prevLow)));
        this.supportOne = (pivotPoint.multipliedBy(TADecimal.TWO)).minus(prevHigh);
        this.supportTwo = pivotPoint.minus((prevHigh.minus(prevLow)));

        this.result = new PivotPoint();
        result.setPivotPoint(pivotPoint);
        result.setResistanceOne(resistanceOne);
        result.setResistanceTwo(resistanceTwo);
        result.setSupportOne(supportOne);
        result.setSupportTwo(supportTwo);
    }

    @Override
    protected PivotPoint calculate(int i) {
        return result;
    }

}
