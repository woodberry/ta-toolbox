package au.net.woodberry.ta.toolbox.indicators.volatility;

import au.net.woodberry.ta.toolbox.indicators.StubDataTestUtils;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CountBackLineIndicatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullData() {
        new CountBackLineIndicator(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCountBackSteps() {
        Tick mockTick = new Tick(DateTime.now(), DateTime.now());
        new CountBackLineIndicator(new TimeSeries(Arrays.asList(mockTick)), 0, -1);
    }

    @Test
    public void testGetValueContainsCountBackLineTC1() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_COUNT_BACK_LINE_TC1.stub"));
        CountBackLineIndicator cbl = new CountBackLineIndicator(data, 12);
        for (int i = data.getBegin(); i <= 4; i++) {
            assertNull(cbl.getValue(i));
        }
        for (int i = 5; i <= data.getEnd(); i++) {
            assertNotNull(cbl.getValue(i));
            assertEquals(31.82, cbl.getValue(i).toDouble(), 0.01);
        }
    }

    @Test
    public void testGetValueDoesNotContainCountBackLineTC1() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_COUNT_BACK_LINE_TC1.stub"));
        CountBackLineIndicator cbl = new CountBackLineIndicator(data, 1, 3);
        for (int i = data.getBegin(); i <= data.getEnd(); i++) {
            assertNull(cbl.getValue(i));
        }
    }

    @Test
    public void testGetValueDoesNotContainCountBackLineTC2() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_COUNT_BACK_LINE_TC2.stub"));
        CountBackLineIndicator cbl = new CountBackLineIndicator(data, 17);
        for (int i = data.getBegin(); i <= data.getEnd(); i++) {
            assertNull(cbl.getValue(i));
        }
    }
}
