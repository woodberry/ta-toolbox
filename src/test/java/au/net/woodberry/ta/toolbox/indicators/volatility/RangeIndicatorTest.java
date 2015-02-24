package au.net.woodberry.ta.toolbox.indicators.volatility;

import au.net.woodberry.ta.toolbox.enums.Zone;
import au.net.woodberry.ta.toolbox.indicators.StubDataTestUtils;
import eu.verdelhan.ta4j.TimeSeries;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import static au.net.woodberry.ta.toolbox.indicators.Assertions.assertDecimalEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RangeIndicatorTest {

    private static final DateTimeFormatter DTF = DateTimeFormat.forPattern("dd-MM-YYYY");
    
    @Test(expected = IllegalArgumentException.class)
    public void testNullTimeSeriesThrowsException() {
        new RangeIndicator(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDisplacementLowerLessThanZeroThrowsException() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_RANGE_INDICATOR_TC1.stub", DTF));
        new RangeIndicator(data,-1,1,1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDisplacementUpperLessThanZeroThrowsException() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_RANGE_INDICATOR_TC1.stub", DTF));
        new RangeIndicator(data,1,0,1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testTimeFrameLessThanZeroThrowsException() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_RANGE_INDICATOR_TC1.stub", DTF));
        new RangeIndicator(data,1,1,0);
    }
    
    @Test
    public void testIndexLessThanTimeFrameResultIsNull() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_RANGE_INDICATOR_TC1.stub", DTF));
        RangeIndicator indicator = new RangeIndicator(data);
        assertNull(indicator.getValue(12));
    }

    @Test
    public void testUpperDeviationValue() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_RANGE_INDICATOR_TC1.stub", DTF));
        RangeIndicator indicator = new RangeIndicator(data);
        assertNotNull(indicator.getValue(13));
        assertDecimalEquals(indicator.getValue(13).getUpperDeviation(), 4278.7562);
    }

    @Test
    public void testLowerDeviationValue() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_RANGE_INDICATOR_TC1.stub", DTF));
        RangeIndicator indicator = new RangeIndicator(data);
        assertNotNull(indicator.getValue(13));
        assertDecimalEquals(indicator.getValue(13).getLowerDeviation(), 4054.7031);
    }

    @Test
    public void testCentralCordValue() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_RANGE_INDICATOR_TC1.stub", DTF));
        RangeIndicator indicator = new RangeIndicator(data);
        assertNotNull(indicator.getValue(13));
        assertDecimalEquals(indicator.getValue(13).getCentralCord(), 4156.5454);
    }
    
    @Test
    public void testInZoneProfitTake() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_RANGE_INDICATOR_TC1.stub", DTF));
        RangeIndicator indicator = new RangeIndicator(data);
        assertEquals(Zone.PROFIT_TAKE, indicator.getValue(48).getZone());
    }
    
    @Test
    public void testInZoneProfitTakeHold() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_RANGE_INDICATOR_TC1.stub", DTF));
        RangeIndicator indicator = new RangeIndicator(data);
        assertEquals(Zone.PROFIT_TAKE_HOLD, indicator.getValue(13).getZone());
    }
    
    @Test
    public void testInZoneBuyHold() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_RANGE_INDICATOR_TC1.stub", DTF));
        RangeIndicator indicator = new RangeIndicator(data);
        assertEquals(Zone.BUY_HOLD, indicator.getValue(17).getZone());
    }
    
    @Test
    public void testInZoneSell() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_RANGE_INDICATOR_TC1.stub", DTF));
        RangeIndicator indicator = new RangeIndicator(data);
        assertEquals(Zone.SELL, indicator.getValue(34).getZone());
    }
}
