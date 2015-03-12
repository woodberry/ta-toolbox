package au.net.woodberry.ta.toolbox.indicators.breakout;

import au.net.woodberry.ta.toolbox.enums.Sentiment;
import au.net.woodberry.ta.toolbox.indicators.StubDataTestUtils;
import au.net.woodberry.ta.toolbox.object.NR7Breakout;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NR7BreakoutIndicatorTest {
    
    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionNullTimeSeries() {
        new NR7BreakoutIndicator(null, new Tick(null, null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionNullTick() {
        new NR7BreakoutIndicator(new TimeSeries(Arrays.asList(new Tick(null, null))), null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionTickNotWithinTimeSeries() {
        new NR7BreakoutIndicator(new TimeSeries(Arrays.asList(new Tick(null, DateTime.parse("2001-01-01")))), new Tick(null, DateTime.parse("2001-01-02")));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionTickIsAtTimeSeriesEnd() {
        new NR7BreakoutIndicator(new TimeSeries(Arrays.asList(new Tick(null, DateTime.parse("2001-01-01")))), new Tick(null, DateTime.parse("2001-01-01")));
    }
    
    @Test
    public void testAtIndexLessThanNrIndexReturnsNull() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_BREAKOUT_INDICATOR_TC2.stub"));
        Tick nrTick = timeSeries.getTick(3); 
        NR7BreakoutIndicator breakoutIndicator = new NR7BreakoutIndicator(timeSeries, nrTick);
        NR7Breakout value = breakoutIndicator.getValue(2);
        assertNull(value);
    }
    
    @Test
    public void testAtNrIndexReturnsNull() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_BREAKOUT_INDICATOR_TC2.stub"));
        Tick nrTick = timeSeries.getTick(3);
        NR7BreakoutIndicator breakoutIndicator = new NR7BreakoutIndicator(timeSeries, nrTick);
        NR7Breakout value = breakoutIndicator.getValue(3);
        assertNull(value);
    }

    @Test
    public void testReturnsNullBeforeBreakoutIndex() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_BREAKOUT_INDICATOR_TC2.stub"));
        Tick nrTick = timeSeries.getTick(3);
        NR7BreakoutIndicator breakoutIndicator = new NR7BreakoutIndicator(timeSeries, nrTick);
        NR7Breakout value = breakoutIndicator.getValue(5);
        assertNull(value);
    }
    
    @Test
    public void testReturnsBearishResultAtBreakoutIndex() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_BREAKOUT_INDICATOR_TC2.stub"));
        Tick nrTick = timeSeries.getTick(3);
        NR7BreakoutIndicator breakoutIndicator = new NR7BreakoutIndicator(timeSeries, nrTick);
        NR7Breakout value = breakoutIndicator.getValue(6);
        assertNotNull(value);
        assertEquals(3, value.getPeriods());
        assertEquals(Sentiment.BEARISH, value.getSentiment());
    }
    
    @Test
    public void testReturnsBearishResultAfterBreakoutIndex() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_BREAKOUT_INDICATOR_TC2.stub"));
        Tick nrTick = timeSeries.getTick(3);
        NR7BreakoutIndicator breakoutIndicator = new NR7BreakoutIndicator(timeSeries, nrTick);
        NR7Breakout value = breakoutIndicator.getValue(7);
        assertNotNull(value);
        assertEquals(3, value.getPeriods());
        assertEquals(Sentiment.BEARISH, value.getSentiment());
    }

    @Test
    public void testReturnsBullishResultAtBreakoutIndex() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_BREAKOUT_INDICATOR_TC3.stub"));
        Tick nrTick = timeSeries.getTick(0);
        NR7BreakoutIndicator breakoutIndicator = new NR7BreakoutIndicator(timeSeries, nrTick);
        NR7Breakout value = breakoutIndicator.getValue(1);
        assertNotNull(value);
        assertEquals(1, value.getPeriods());
        assertEquals(Sentiment.BULLISH, value.getSentiment());
    }

    @Test
    public void testReturnsBullishResultAfterBreakoutIndex() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_BREAKOUT_INDICATOR_TC3.stub"));
        Tick nrTick = timeSeries.getTick(0);
        NR7BreakoutIndicator breakoutIndicator = new NR7BreakoutIndicator(timeSeries, nrTick);
        NR7Breakout value = breakoutIndicator.getValue(2);
        assertNotNull(value);
        assertEquals(1, value.getPeriods());
        assertEquals(Sentiment.BULLISH, value.getSentiment());
    }
}
