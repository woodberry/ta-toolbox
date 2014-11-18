package au.net.woodberry.ta.toolbox.indicators.directional.pivotpoints.standard;

import au.net.woodberry.ta.toolbox.indicators.TestUtils;
import eu.verdelhan.ta4j.TimeSeries;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PivotPointTest {

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
        PivotPoint pivotPoint = new PivotPoint(data);
        assertNull(pivotPoint.getValue(0));
        assertNull(pivotPoint.getValue(1));
        assertNotNull(pivotPoint.getValue(2));
        assertEquals((5367.8 + 5323.8 + 5358) / 3, pivotPoint.getValue(2).toDouble(), 0.01);
        assertNotNull(pivotPoint.getValue(3));
        assertEquals((5367.8 + 5323.8 + 5358) / 3, pivotPoint.getValue(3).toDouble(), 0.01);
        assertNotNull(pivotPoint.getValue(4));
        assertEquals((5240.6 + 5175.2 + 5199.4) / 3, pivotPoint.getValue(4).toDouble(), 0.01);
        assertNotNull(pivotPoint.getValue(5));
        assertEquals((5240.6 + 5175.2 + 5199.4) / 3, pivotPoint.getValue(5).toDouble(), 0.01);
    }
}
