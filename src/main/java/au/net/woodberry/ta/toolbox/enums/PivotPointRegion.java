package au.net.woodberry.ta.toolbox.enums;

import au.net.woodberry.ta.toolbox.object.PivotPoint;
import eu.verdelhan.ta4j.TADecimal;

public enum PivotPointRegion {

    BELOW_SUPPORT_TWO(6),
    BETWEEN_SUPPORT_TWO_AND_SUPPORT_ONE(5),
    BETWEEN_SUPPORT_ONE_AND_PIVOT_POINT(4),
    BETWEEN_PIVOT_POINT_AND_RESISTANCE_ONE(3),
    BETWEEN_RESISTANCE_ONE_AND_RESISTANCE_TWO(2),
    ABOVE_RESISTANCE_TWO(1);

    private final int rank;

    PivotPointRegion(int rank) {
        this.rank = rank;
    }
    
    public int getRank() {
        return rank;
    }

    /**
     * Determines the region given a value and pivot point
     *  
     * @param pivotPoint The pivot point object
     * @param value A reference value
     * @return
     */
    public static PivotPointRegion getRegion(PivotPoint pivotPoint, TADecimal value) {
        if (value.isLessThan(pivotPoint.getSupportTwo())) {
            return PivotPointRegion.BELOW_SUPPORT_TWO;
        } else if (value.isGreaterThanOrEqual(pivotPoint.getSupportTwo()) && value.isLessThan(pivotPoint.getSupportOne())) {
            return PivotPointRegion.BETWEEN_SUPPORT_TWO_AND_SUPPORT_ONE;
        } else if (value.isGreaterThanOrEqual(pivotPoint.getSupportOne()) && value.isLessThan(pivotPoint.getPivotPoint())) {
            return PivotPointRegion.BETWEEN_SUPPORT_ONE_AND_PIVOT_POINT;
        } else if (value.isGreaterThanOrEqual(pivotPoint.getPivotPoint()) && value.isLessThan(pivotPoint.getResistanceOne())) {
            return PivotPointRegion.BETWEEN_PIVOT_POINT_AND_RESISTANCE_ONE;
        } else if (value.isGreaterThanOrEqual(pivotPoint.getResistanceOne()) && value.isLessThanOrEqual(pivotPoint.getResistanceTwo())) {
            return PivotPointRegion.BETWEEN_RESISTANCE_ONE_AND_RESISTANCE_TWO;
        } else if (value.isGreaterThan(pivotPoint.getResistanceTwo())) {
            return PivotPointRegion.ABOVE_RESISTANCE_TWO;
        } else {
            return null;
        }
    }

    /**
     * Determines the direction between two regions
     *
     * @param from The region going from
     * @param to The region going to
     * @return
     */
    public static Direction getDirection(PivotPointRegion from, PivotPointRegion to) {
        if (from.getRank() > to.getRank()) {
            return Direction.UP;
        } else if (from.getRank() == to.getRank()) {
            return Direction.NONE;
        } else if (from.getRank() < to.getRank()) {
            return Direction.DOWN;
        } else {
            return null;
        }
    }
}
