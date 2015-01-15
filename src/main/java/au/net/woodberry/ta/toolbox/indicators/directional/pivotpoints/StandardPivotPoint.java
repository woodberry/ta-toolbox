package au.net.woodberry.ta.toolbox.indicators.directional.pivotpoints;

import eu.verdelhan.ta4j.TADecimal;

public class StandardPivotPoint extends PivotPoint {

    /**
     * Reference: http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:pivot_points
     *
     * @param prevHigh - The previous period's high value
     * @param prevLow - The previous period's low value
     * @param prevClose - The previous period's close value
     */
    public StandardPivotPoint(TADecimal prevHigh, TADecimal prevLow, TADecimal prevClose) {
        if (prevHigh == null) {
            throw new IllegalArgumentException("Supplied TADecimal (previous high) is invalid: NULL");
        }
        if (prevLow == null) {
            throw new IllegalArgumentException("Supplied TADecimal (previous low) is invalid: NULL");
        }
        if (prevClose == null) {
            throw new IllegalArgumentException("Supplied TADecimal (previous close) is invalid: NULL");
        }
        TADecimal pp = prevHigh.plus(prevLow).plus(prevClose).dividedBy(TADecimal.THREE);
        TADecimal r1 = (pp.multipliedBy(TADecimal.TWO)).minus(prevLow);
        TADecimal r2 = pp.plus((prevHigh.minus(prevLow)));
        TADecimal s1 = (pp.multipliedBy(TADecimal.TWO)).minus(prevHigh);
        TADecimal s2 = pp.minus((prevHigh.minus(prevLow)));
        super.setValue(pp);
        super.setResistanceOne(r1);
        super.setResistanceTwo(r2);
        super.setSupportOne(s1);
        super.setSupportTwo(s2);
    }

    @Override
    protected TADecimal calculate(int i) {
        return super.getValue();
    }

}
