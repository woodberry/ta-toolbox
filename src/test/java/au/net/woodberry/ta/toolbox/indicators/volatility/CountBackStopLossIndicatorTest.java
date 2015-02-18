package au.net.woodberry.ta.toolbox.indicators.volatility;

import au.net.woodberry.ta.toolbox.indicators.StubDataTestUtils;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.Tick;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.DateTime;
import org.junit.Test;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CountBackStopLossIndicatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullData() {
        new CountBackStopLossIndicator(null, new Tick(DateTime.now(), DateTime.now()), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCountBackSteps() {
        new CountBackStopLossIndicator(new TimeSeries(Arrays.asList(new Tick(DateTime.now(), DateTime.now()))), 0, new Tick(DateTime.now(), DateTime.now()), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullTick() {
        new CountBackStopLossIndicator(new TimeSeries(Arrays.asList(new Tick(DateTime.now(), DateTime.now()))), null, 0);
    }

    @Test
    public void testGetValue() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_COUNT_BACK_LINE_TC3.stub", DateTimeFormat.forPattern("dd-MM-YYYY")));
        CountBackStopLossIndicator cbsl = new CountBackStopLossIndicator(data, data.getTick(4), 4);

        // Fine grain checks for each index
        assertNull(cbsl.getValue(0));
        assertNull(cbsl.getValue(1));
        assertNull(cbsl.getValue(2));
        assertNull(cbsl.getValue(3));
        assertNotNull(cbsl.getValue(4)); // <-- Entry tick
        assertEquals(5.07, cbsl.getValue(4).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(5));
        assertEquals(5.27, cbsl.getValue(5).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(6));
        assertEquals(5.41, cbsl.getValue(6).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(7));
        assertEquals(5.41, cbsl.getValue(7).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(8));
        assertEquals(5.41, cbsl.getValue(8).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(9));
        assertEquals(5.41, cbsl.getValue(9).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(10));
        assertEquals(5.41, cbsl.getValue(10).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(11));
        assertEquals(5.41, cbsl.getValue(11).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(12));
        assertEquals(5.41, cbsl.getValue(12).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(13));
        assertEquals(5.41, cbsl.getValue(13).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(14));
        assertEquals(5.41, cbsl.getValue(14).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(15));
        assertEquals(5.41, cbsl.getValue(15).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(16));
        assertEquals(5.41, cbsl.getValue(16).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(17));
        assertEquals(5.41, cbsl.getValue(17).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(18));
        assertEquals(5.41, cbsl.getValue(18).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(19));
        assertEquals(5.41, cbsl.getValue(19).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(20));
        assertEquals(5.41, cbsl.getValue(20).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(21));
        assertEquals(5.41, cbsl.getValue(21).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(22));
        assertEquals(5.41, cbsl.getValue(22).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(23));
        assertEquals(5.49, cbsl.getValue(23).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(24));
        assertEquals(5.49, cbsl.getValue(24).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(25));
        assertEquals(5.49, cbsl.getValue(25).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(26));
        assertEquals(5.61, cbsl.getValue(26).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(27));
        assertEquals(5.63, cbsl.getValue(27).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(28));
        assertEquals(5.63, cbsl.getValue(28).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(29));
        assertEquals(5.63, cbsl.getValue(29).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(30));
        assertEquals(5.63, cbsl.getValue(30).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(31));
        assertEquals(5.77, cbsl.getValue(31).toDouble(), 0.01);
        assertNotNull(cbsl.getValue(32));
        assertEquals(5.77, cbsl.getValue(32).toDouble(), 0.01);
    }
}
