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
        new NR7BreakoutIndicator(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionTimeSeriesSizeIsTooSmall() {
        new NR7BreakoutIndicator(new TimeSeries(Arrays.asList(new Tick(null, null))), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionTickIsAtTimeSeriesEnd() {
        new NR7BreakoutIndicator(new TimeSeries(Arrays.asList(new Tick(null, DateTime.parse("2001-01-01")), new Tick(null, DateTime.parse("2001-01-02")))), 1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionTickNotWithinTimeSeries() {
        new NR7BreakoutIndicator(new TimeSeries(Arrays.asList(new Tick(null, DateTime.parse("2001-01-01")), new Tick(null, DateTime.parse("2001-01-02")))), 2);
    }
    
    @Test
    public void testAtIndexLessThanNrIndexReturnsNull() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_BREAKOUT_INDICATOR_TC2.stub"));
        NR7BreakoutIndicator breakoutIndicator = new NR7BreakoutIndicator(timeSeries, 3);
        NR7Breakout value = breakoutIndicator.getValue(2);
        assertNull(value);
    }
    
    @Test
    public void testAtNrIndexReturnsNull() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_BREAKOUT_INDICATOR_TC2.stub"));
        NR7BreakoutIndicator breakoutIndicator = new NR7BreakoutIndicator(timeSeries, 3);
        NR7Breakout value = breakoutIndicator.getValue(3);
        assertNull(value);
    }

    @Test
    public void testReturnsNullBeforeBreakoutIndex() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_BREAKOUT_INDICATOR_TC2.stub"));
        NR7BreakoutIndicator breakoutIndicator = new NR7BreakoutIndicator(timeSeries, 3);
        NR7Breakout value = breakoutIndicator.getValue(5);
        assertNull(value);
    }
    
    @Test
    public void testReturnsBearishResultAtBreakoutIndex() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_BREAKOUT_INDICATOR_TC2.stub"));
        NR7BreakoutIndicator breakoutIndicator = new NR7BreakoutIndicator(timeSeries, 3);
        NR7Breakout value = breakoutIndicator.getValue(6);
        assertNotNull(value);
        assertEquals(Sentiment.BEARISH, value.getSentiment());
    }
    
    @Test
    public void testReturnsBearishResultAfterBreakoutIndex() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_BREAKOUT_INDICATOR_TC2.stub"));
        NR7BreakoutIndicator breakoutIndicator = new NR7BreakoutIndicator(timeSeries, 3);
        NR7Breakout value = breakoutIndicator.getValue(7);
        assertNotNull(value);
        assertEquals(Sentiment.BEARISH, value.getSentiment());
    }

    @Test
    public void testReturnsBullishResultAtBreakoutIndex() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_BREAKOUT_INDICATOR_TC3.stub"));
        NR7BreakoutIndicator breakoutIndicator = new NR7BreakoutIndicator(timeSeries, 0);
        NR7Breakout value = breakoutIndicator.getValue(1);
        assertNotNull(value);
        assertEquals(Sentiment.BULLISH, value.getSentiment());
    }

    @Test
    public void testReturnsBullishResultAfterBreakoutIndex() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_BREAKOUT_INDICATOR_TC3.stub"));
        NR7BreakoutIndicator breakoutIndicator = new NR7BreakoutIndicator(timeSeries, 0);
        NR7Breakout value = breakoutIndicator.getValue(2);
        assertNotNull(value);
        assertEquals(Sentiment.BULLISH, value.getSentiment());
    }
}
