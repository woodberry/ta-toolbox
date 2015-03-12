package au.net.woodberry.ta.toolbox.indicators.breakout;

import au.net.woodberry.ta.toolbox.indicators.StubDataTestUtils;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class NR7IndicatorTest {
    
    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionNullInput() {
        new NR7Indicator(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionNotEnoughInput() {
        new NR7Indicator(new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_INDICATOR_TC3.stub")));
    }
    
    @Test
    public void testAtIndexBelowMinimumRequiredReturnsNull() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_INDICATOR_TC1.stub"));
        NR7Indicator nr7Indicator = new NR7Indicator(timeSeries);
        Tick value = nr7Indicator.getValue(5);
        assertNull(value);
    }
    
    @Test
    public void testFindsResult() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_INDICATOR_TC1.stub"));
        NR7Indicator nr7Indicator = new NR7Indicator(timeSeries);
        Tick value = nr7Indicator.getValue(timeSeries.getEnd());
        assertNotNull(value);
        assertTrue(timeSeries.getTick(timeSeries.getEnd()).getEndTime().isEqual(value.getEndTime()));
    }
    
    @Test
    public void testDoesNotFindResult() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_NR7_INDICATOR_TC2.stub"));
        NR7Indicator nr7Indicator = new NR7Indicator(timeSeries);
        Tick value = nr7Indicator.getValue(timeSeries.getEnd());
        assertNull(value);
    }
}
