package au.net.woodberry.ta.toolbox.indicators.trackers;

import au.net.woodberry.ta.toolbox.indicators.StubDataTestUtils;
import eu.verdelhan.ta4j.TimeSeries;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import static au.net.woodberry.ta.toolbox.indicators.Assertions.assertDecimalEquals;
import static org.junit.Assert.assertNotNull;

public class HMAIndicatorTest {

    private static final DateTimeFormatter DTF = DateTimeFormat.forPattern("dd-MM-YYYY");
    private static final int TIMEFRAME = 26;
    
    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionForNullTimeSeries() {
        new HMAIndicator(null, TIMEFRAME);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionForInvalidTimeFrame() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_STOP_LOSS_TC1.stub", DTF));
        new HMAIndicator(data, 1);
    }
    
    @Test
    public void testIndicatorResultsTimeFrameInitialValue() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_STOP_LOSS_TC1.stub", DTF));
        HMAIndicator indicator = new HMAIndicator(data, TIMEFRAME);
        assertNotNull(indicator.getValue(0));
        assertDecimalEquals(indicator.getValue(0), data.getTick(0).getClosePrice().toDouble());
    }

    @Test
    public void testIndicatorResultsTimeFrameRandomValue() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_STOP_LOSS_TC1.stub", DTF));
        HMAIndicator indicator = new HMAIndicator(data, TIMEFRAME);
        assertNotNull(indicator.getValue(0));
        assertDecimalEquals(indicator.getValue(30), 5178.7529);
    }

    @Test
    public void testIndicatorResultsTimeFrameLastValue() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_STOP_LOSS_TC1.stub", DTF));
        HMAIndicator indicator = new HMAIndicator(data, TIMEFRAME);
        assertNotNull(indicator.getValue(0));
        assertDecimalEquals(indicator.getValue(data.getEnd()), 4956.3222);
    }
}
