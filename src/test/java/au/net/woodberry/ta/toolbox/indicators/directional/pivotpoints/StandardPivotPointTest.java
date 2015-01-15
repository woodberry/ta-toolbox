package au.net.woodberry.ta.toolbox.indicators.directional.pivotpoints;

import eu.verdelhan.ta4j.TADecimal;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class StandardPivotPointTest {

    private static final double PREVIOUS_HIGH = 5367.8;
    private static final double PREVIOUS_LOW = 4308.4;
    private static final double PREVIOUS_CLOSE = 5323.8;
    private static final double PIVOT_POINT_VALUE = (PREVIOUS_HIGH + PREVIOUS_LOW + PREVIOUS_CLOSE) / 3;
    private static final double DELTA = 0.01;

    private StandardPivotPoint standardPivotPoint;

    @Before
    public void before() {
        standardPivotPoint = new StandardPivotPoint(TADecimal.valueOf(PREVIOUS_HIGH), TADecimal.valueOf(PREVIOUS_LOW), TADecimal.valueOf(PREVIOUS_CLOSE));
    }
    @Test
    public void testPivotPoint() {
        assertNotNull(standardPivotPoint.getValue());
        assertEquals(PIVOT_POINT_VALUE, standardPivotPoint.getValue().toDouble(), DELTA);
    }

    @Test
    public void testResistanceOne() {
        assertEquals((PIVOT_POINT_VALUE * 2) - PREVIOUS_LOW, standardPivotPoint.getResistanceOne().toDouble(), DELTA);
    }

    @Test
    public void testResistanceTwo() {
        assertEquals(PIVOT_POINT_VALUE + (PREVIOUS_HIGH - PREVIOUS_LOW), standardPivotPoint.getResistanceTwo().toDouble(), DELTA);
    }

    @Test
    public void testSupportOne() {
        assertEquals((PIVOT_POINT_VALUE * 2) - PREVIOUS_HIGH, standardPivotPoint.getSupportOne().toDouble(), DELTA);
    }

    @Test
    public void testSupportTwo() {
        assertEquals(PIVOT_POINT_VALUE - (PREVIOUS_HIGH - PREVIOUS_LOW), standardPivotPoint.getSupportTwo().toDouble(), DELTA);
    }

}
