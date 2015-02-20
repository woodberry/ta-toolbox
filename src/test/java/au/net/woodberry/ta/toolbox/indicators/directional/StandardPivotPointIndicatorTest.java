package au.net.woodberry.ta.toolbox.indicators.directional;

import au.net.woodberry.ta.toolbox.object.PivotPoint;
import eu.verdelhan.ta4j.TADecimal;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class StandardPivotPointIndicatorTest {

    private static final double PREVIOUS_HIGH = 5367.8;
    private static final double PREVIOUS_LOW = 4308.4;
    private static final double PREVIOUS_CLOSE = 5323.8;
    private static final double PIVOT_POINT_VALUE = (PREVIOUS_HIGH + PREVIOUS_LOW + PREVIOUS_CLOSE) / 3;
    private static final double DELTA = 0.01;

    private StandardPivotPointIndicator standardPivotPoint;

    @Before
    public void before() {
        standardPivotPoint = new StandardPivotPointIndicator(TADecimal.valueOf(PREVIOUS_HIGH), TADecimal.valueOf(PREVIOUS_LOW), TADecimal.valueOf(PREVIOUS_CLOSE));
    }
    @Test
    public void testPivotPoint() {
        PivotPoint pivotPoint = standardPivotPoint.getValue(0);
        assertNotNull(pivotPoint);
        assertNotNull(pivotPoint.getPivotPoint());
        assertEquals(PIVOT_POINT_VALUE, standardPivotPoint.getValue(0).getPivotPoint().toDouble(), DELTA);
    }

    @Test
    public void testResistanceOne() {
        PivotPoint pivotPoint = standardPivotPoint.getValue(0);
        assertNotNull(pivotPoint);
        assertNotNull(pivotPoint.getResistanceOne());
        assertEquals((PIVOT_POINT_VALUE * 2) - PREVIOUS_LOW, pivotPoint.getResistanceOne().toDouble(), DELTA);
    }

    @Test
    public void testResistanceTwo() {
        PivotPoint pivotPoint = standardPivotPoint.getValue(0);
        assertNotNull(pivotPoint);
        assertNotNull(pivotPoint.getResistanceTwo());
        assertEquals(PIVOT_POINT_VALUE + (PREVIOUS_HIGH - PREVIOUS_LOW), pivotPoint.getResistanceTwo().toDouble(), DELTA);
    }

    @Test
    public void testSupportOne() {
        PivotPoint pivotPoint = standardPivotPoint.getValue(0);
        assertNotNull(pivotPoint);
        assertNotNull(pivotPoint.getSupportOne());
        assertEquals((PIVOT_POINT_VALUE * 2) - PREVIOUS_HIGH, pivotPoint.getSupportOne().toDouble(), DELTA);
    }

    @Test
    public void testSupportTwo() {
        PivotPoint pivotPoint = standardPivotPoint.getValue(0);
        assertNotNull(pivotPoint);
        assertNotNull(pivotPoint.getSupportTwo());
        assertEquals(PIVOT_POINT_VALUE - (PREVIOUS_HIGH - PREVIOUS_LOW), pivotPoint.getSupportTwo().toDouble(), DELTA);
    }

}
