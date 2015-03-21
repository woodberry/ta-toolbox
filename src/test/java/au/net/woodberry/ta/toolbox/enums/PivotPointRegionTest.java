package au.net.woodberry.ta.toolbox.enums;

import au.net.woodberry.ta.toolbox.object.PivotPoint;
import eu.verdelhan.ta4j.TADecimal;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PivotPointRegionTest {
    
    private PivotPoint pivotPoint;
    
    @Before
    public void before() {
        pivotPoint = createPivotPoint(1,3,5,7,9);
    }
    
    @Test
    public void testGetRegionBelowSupportTwo() {
        assertEquals(PivotPointRegion.BELOW_SUPPORT_TWO, PivotPointRegion.getRegion(pivotPoint, TADecimal.valueOf(0.9)));
    }

    @Test
    public void testGetRegionBetweenSupportTwoAndSupportOne() {
        assertEquals(PivotPointRegion.BETWEEN_SUPPORT_TWO_AND_SUPPORT_ONE, PivotPointRegion.getRegion(pivotPoint, TADecimal.valueOf(1)));
        assertEquals(PivotPointRegion.BETWEEN_SUPPORT_TWO_AND_SUPPORT_ONE, PivotPointRegion.getRegion(pivotPoint, TADecimal.valueOf(2)));
    }

    @Test
    public void testGetRegionBetweenSupportOneAndPivotPoint() {
        assertEquals(PivotPointRegion.BETWEEN_SUPPORT_ONE_AND_PIVOT_POINT, PivotPointRegion.getRegion(pivotPoint, TADecimal.valueOf(3)));
        assertEquals(PivotPointRegion.BETWEEN_SUPPORT_ONE_AND_PIVOT_POINT, PivotPointRegion.getRegion(pivotPoint, TADecimal.valueOf(4)));
    }
    
    @Test
    public void testGetRegionBetweenPivotPointAndResistanceOne() {
        assertEquals(PivotPointRegion.BETWEEN_PIVOT_POINT_AND_RESISTANCE_ONE, PivotPointRegion.getRegion(pivotPoint, TADecimal.valueOf(5)));
        assertEquals(PivotPointRegion.BETWEEN_PIVOT_POINT_AND_RESISTANCE_ONE, PivotPointRegion.getRegion(pivotPoint, TADecimal.valueOf(6)));
    }

    @Test
    public void testGetRegionBetweenResistanceOneAndResistanceTwo() {
        assertEquals(PivotPointRegion.BETWEEN_RESISTANCE_ONE_AND_RESISTANCE_TWO, PivotPointRegion.getRegion(pivotPoint, TADecimal.valueOf(7)));
        assertEquals(PivotPointRegion.BETWEEN_RESISTANCE_ONE_AND_RESISTANCE_TWO, PivotPointRegion.getRegion(pivotPoint, TADecimal.valueOf(9)));
    }

    @Test
    public void testGetRegionAboveResistanceTwo() {
        assertEquals(PivotPointRegion.ABOVE_RESISTANCE_TWO, PivotPointRegion.getRegion(pivotPoint, TADecimal.valueOf(10)));
    }
    
    @Test
    public void testDirectionUp() {
        assertEquals(Direction.UP, PivotPointRegion.getDirection(PivotPointRegion.BELOW_SUPPORT_TWO, PivotPointRegion.BETWEEN_SUPPORT_TWO_AND_SUPPORT_ONE));
        assertEquals(Direction.UP, PivotPointRegion.getDirection(PivotPointRegion.BETWEEN_SUPPORT_TWO_AND_SUPPORT_ONE, PivotPointRegion.BETWEEN_SUPPORT_ONE_AND_PIVOT_POINT));
        assertEquals(Direction.UP, PivotPointRegion.getDirection(PivotPointRegion.BETWEEN_SUPPORT_ONE_AND_PIVOT_POINT, PivotPointRegion.BETWEEN_PIVOT_POINT_AND_RESISTANCE_ONE));
        assertEquals(Direction.UP, PivotPointRegion.getDirection(PivotPointRegion.BETWEEN_PIVOT_POINT_AND_RESISTANCE_ONE, PivotPointRegion.BETWEEN_RESISTANCE_ONE_AND_RESISTANCE_TWO));
        assertEquals(Direction.UP, PivotPointRegion.getDirection(PivotPointRegion.BETWEEN_RESISTANCE_ONE_AND_RESISTANCE_TWO, PivotPointRegion.ABOVE_RESISTANCE_TWO));
        assertEquals(Direction.UP, PivotPointRegion.getDirection(PivotPointRegion.BELOW_SUPPORT_TWO, PivotPointRegion.ABOVE_RESISTANCE_TWO));
    }
    
    @Test
    public void testDirectionDown() {
        assertEquals(Direction.DOWN, PivotPointRegion.getDirection(PivotPointRegion.BETWEEN_SUPPORT_TWO_AND_SUPPORT_ONE, PivotPointRegion.BELOW_SUPPORT_TWO));
        assertEquals(Direction.DOWN, PivotPointRegion.getDirection(PivotPointRegion.BETWEEN_SUPPORT_ONE_AND_PIVOT_POINT, PivotPointRegion.BETWEEN_SUPPORT_TWO_AND_SUPPORT_ONE));
        assertEquals(Direction.DOWN, PivotPointRegion.getDirection(PivotPointRegion.BETWEEN_PIVOT_POINT_AND_RESISTANCE_ONE, PivotPointRegion.BETWEEN_SUPPORT_ONE_AND_PIVOT_POINT));
        assertEquals(Direction.DOWN, PivotPointRegion.getDirection(PivotPointRegion.BETWEEN_RESISTANCE_ONE_AND_RESISTANCE_TWO, PivotPointRegion.BETWEEN_PIVOT_POINT_AND_RESISTANCE_ONE));
        assertEquals(Direction.DOWN, PivotPointRegion.getDirection(PivotPointRegion.ABOVE_RESISTANCE_TWO, PivotPointRegion.BETWEEN_RESISTANCE_ONE_AND_RESISTANCE_TWO));
        assertEquals(Direction.DOWN, PivotPointRegion.getDirection(PivotPointRegion.ABOVE_RESISTANCE_TWO, PivotPointRegion.BELOW_SUPPORT_TWO));
    }
    
    @Test
    public void testDirectionNone() {
        assertEquals(Direction.NONE, PivotPointRegion.getDirection(PivotPointRegion.BELOW_SUPPORT_TWO, PivotPointRegion.BELOW_SUPPORT_TWO));
        assertEquals(Direction.NONE, PivotPointRegion.getDirection(PivotPointRegion.BETWEEN_SUPPORT_TWO_AND_SUPPORT_ONE, PivotPointRegion.BETWEEN_SUPPORT_TWO_AND_SUPPORT_ONE));
        assertEquals(Direction.NONE, PivotPointRegion.getDirection(PivotPointRegion.BETWEEN_SUPPORT_ONE_AND_PIVOT_POINT, PivotPointRegion.BETWEEN_SUPPORT_ONE_AND_PIVOT_POINT));
        assertEquals(Direction.NONE, PivotPointRegion.getDirection(PivotPointRegion.BETWEEN_PIVOT_POINT_AND_RESISTANCE_ONE, PivotPointRegion.BETWEEN_PIVOT_POINT_AND_RESISTANCE_ONE));
        assertEquals(Direction.NONE, PivotPointRegion.getDirection(PivotPointRegion.BETWEEN_RESISTANCE_ONE_AND_RESISTANCE_TWO, PivotPointRegion.BETWEEN_RESISTANCE_ONE_AND_RESISTANCE_TWO));
        assertEquals(Direction.NONE, PivotPointRegion.getDirection(PivotPointRegion.ABOVE_RESISTANCE_TWO, PivotPointRegion.ABOVE_RESISTANCE_TWO));
    }
    
    
    private static PivotPoint createPivotPoint(double supportTwo, double supportOne, double value,
                                               double resistanceOne, double resistanceTwo) {
        PivotPoint pivotPoint = new PivotPoint();
        pivotPoint.setSupportOne(TADecimal.valueOf(supportOne));
        pivotPoint.setSupportTwo(TADecimal.valueOf(supportTwo));
        pivotPoint.setPivotPoint(TADecimal.valueOf(value));
        pivotPoint.setResistanceOne(TADecimal.valueOf(resistanceOne));
        pivotPoint.setResistanceTwo(TADecimal.valueOf(resistanceTwo));
        return pivotPoint;
    } 
}
