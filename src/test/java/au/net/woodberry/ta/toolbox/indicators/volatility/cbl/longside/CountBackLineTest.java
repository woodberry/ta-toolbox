package au.net.woodberry.ta.toolbox.indicators.volatility.cbl.longside;

import eu.verdelhan.ta4j.TimeSeries;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CountBackLineTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullData() {
        new CountBackLine(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCountBackSteps() {
        new CountBackLine(new TimeSeries(Utils.createTickData("/TEST_COUNT_BACK_LINE_TC1.stub")), -1);
    }

    @Test
    public void testGetValue() {
        TimeSeries timeSeries = new TimeSeries(Utils.createTickData("/TEST_COUNT_BACK_LINE_TC1.stub"));
        CountBackLine cbl = new CountBackLine(timeSeries);
        assertNull(cbl.getValue(0));
        assertNull(cbl.getValue(1));
        assertNull(cbl.getValue(2));
        assertNull(cbl.getValue(3));
        assertNull(cbl.getValue(4));
        assertNull(cbl.getValue(5));
        assertNull(cbl.getValue(6));
        assertNull(cbl.getValue(7));
        assertNull(cbl.getValue(8));
        assertNull(cbl.getValue(9));
        assertNull(cbl.getValue(10));
        assertNull(cbl.getValue(11));
        assertNull(cbl.getValue(12));
        assertNull(cbl.getValue(13));
        assertNull(cbl.getValue(14));
        assertNull(cbl.getValue(15));
        assertNull(cbl.getValue(16));
        assertNull(cbl.getValue(17));
        assertNull(cbl.getValue(18));
        assertNull(cbl.getValue(19));
        assertNull(cbl.getValue(20));
        assertNull(cbl.getValue(21));
        assertEquals(33.20, cbl.getValue(22).toDouble(), 0.01);
        assertEquals(32.70, cbl.getValue(23).toDouble(), 0.01);
        assertEquals(32.49, cbl.getValue(24).toDouble(), 0.01);
        assertEquals(32.49, cbl.getValue(25).toDouble(), 0.01);
        assertEquals(32.49, cbl.getValue(26).toDouble(), 0.01);
        assertEquals(32.49, cbl.getValue(27).toDouble(), 0.01);
        assertEquals(32.49, cbl.getValue(28).toDouble(), 0.01);
        assertEquals(32.49, cbl.getValue(29).toDouble(), 0.01);
        assertEquals(31.83, cbl.getValue(30).toDouble(), 0.01);
        assertEquals(31.76, cbl.getValue(31).toDouble(), 0.01);
        assertEquals(31.20, cbl.getValue(32).toDouble(), 0.01);
        assertEquals(31.20, cbl.getValue(33).toDouble(), 0.01);
        assertEquals(31.20, cbl.getValue(34).toDouble(), 0.01); // <-- Entry
        assertNull(cbl.getValue(35)); // <-- CBL reset
        assertNull(cbl.getValue(36));
        assertEquals(32, cbl.getValue(37).toDouble(), 0.01);
        assertEquals(32, cbl.getValue(38).toDouble(), 0.01);
        assertEquals(32, cbl.getValue(39).toDouble(), 0.01);
        assertEquals(32, cbl.getValue(40).toDouble(), 0.01);
        assertEquals(31.97, cbl.getValue(41).toDouble(), 0.01);
        assertEquals(31.97, cbl.getValue(42).toDouble(), 0.01);
        assertEquals(31.97, cbl.getValue(43).toDouble(), 0.01);
        assertEquals(31.97, cbl.getValue(44).toDouble(), 0.01);
        assertEquals(31.97, cbl.getValue(45).toDouble(), 0.01);
        assertEquals(31.97, cbl.getValue(46).toDouble(), 0.01);
        assertEquals(31.97, cbl.getValue(47).toDouble(), 0.01); // <-- Entry
        assertNull(cbl.getValue(48)); // <-- CBL reset
        assertNull(cbl.getValue(49));
        assertNull(cbl.getValue(50));
        assertNull(cbl.getValue(51));
        assertNull(cbl.getValue(52));
        assertNull(cbl.getValue(53));
        assertNull(cbl.getValue(54));
        assertNull(cbl.getValue(55));
        assertNull(cbl.getValue(56));
        assertNull(cbl.getValue(57));
        assertNull(cbl.getValue(58));
        assertNull(cbl.getValue(59));
        assertNull(cbl.getValue(60));
        assertEquals(33.89, cbl.getValue(61).toDouble(), 0.01);
        assertEquals(33.89, cbl.getValue(62).toDouble(), 0.01);
        assertEquals(32.88, cbl.getValue(63).toDouble(), 0.01);
    }
}
