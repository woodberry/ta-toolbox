package au.net.woodberry.ta.toolbox.indicators.directional.pivotpoints.standard;

import au.net.woodberry.ta.toolbox.indicators.TestUtils;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class ResistanceTwoTest {

    private PivotPoint pivotPoint;

    @Before
    public void before() {
        pivotPoint = mock(PivotPoint.class);
    }

    @After
    public void after() {
        reset(pivotPoint);
    }

    /**

     Stub data used for this test:

     2013-12-31,5358.00,5367.80,5340.50,5353.10,375468700,5353.10
     2013-12-30,5323.80,5358.00,5323.80,5358.00,469708600,5358.00
     2014-01-31,5199.40,5209.90,5184.40,5205.10,846221600,5205.10
     2014-01-30,5240.60,5240.60,5175.20,5199.40,832158900,5199.40
     2014-02-26,5444.00,5461.30,5433.70,5447.00,1127947600,5447.00
     2014-02-25,5450.10,5471.80,5438.20,5444.00,965084300,5444.00

     prevHigh - 5367.80
     prevLow - 5323.80
     prevClose - 5358.00

     prevHigh - 5240.60
     prevLow - 5175.20
     prevClose - 5199.40

     */
    @Test
    public void testGetValueTC1() {
        List<TimeSeries> data = Arrays.asList(
                new TimeSeries(TestUtils.createTickData("/TEST_PIVOT_POINT_TC1A.stub")),
                new TimeSeries(TestUtils.createTickData("/TEST_PIVOT_POINT_TC1B.stub")),
                new TimeSeries(TestUtils.createTickData("/TEST_PIVOT_POINT_TC1C.stub"))
        );
        ResistanceTwo resistanceTwo = new ResistanceTwo(data, pivotPoint);
        TADecimal pivotPointCalc = TADecimal.valueOf(5000);

        when(pivotPoint.getValue(eq(0))).thenReturn(null);
        assertNull(resistanceTwo.getValue(0));

        when(pivotPoint.getValue(eq(1))).thenReturn(null);
        assertNull(resistanceTwo.getValue(1));

        when(pivotPoint.getValue(eq(2))).thenReturn(pivotPointCalc);
        assertNotNull(resistanceTwo.getValue(2));
        assertEquals(pivotPointCalc.toDouble() + (5367.80 - 5323.8), resistanceTwo.getValue(2).toDouble(), 0.01);

        when(pivotPoint.getValue(eq(3))).thenReturn(pivotPointCalc);
        assertNotNull(resistanceTwo.getValue(3));
        assertEquals(pivotPointCalc.toDouble() + (5367.80 - 5323.8), resistanceTwo.getValue(3).toDouble(), 0.01);

        when(pivotPoint.getValue(eq(4))).thenReturn(pivotPointCalc);
        assertNotNull(resistanceTwo.getValue(4));
        assertEquals(pivotPointCalc.toDouble() + (5240.6 - 5175.2), resistanceTwo.getValue(4).toDouble(), 0.01);

        when(pivotPoint.getValue(eq(5))).thenReturn(pivotPointCalc);
        assertNotNull(resistanceTwo.getValue(5));
        assertEquals(pivotPointCalc.toDouble() + (5240.6 - 5175.2), resistanceTwo.getValue(5).toDouble(), 0.01);
    }
}
