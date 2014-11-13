package au.net.woodberry.ta.toolbox.indicators.volatility.cbl.longside;

import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CountBackEntryTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullData() {
        new CountBackEntry(null, new ArrayList<TADecimal>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullCountBackLine() {
        TimeSeries data = new TimeSeries(Utils.createTickData("/TEST_COUNT_BACK_LINE_TC1.stub"));
        new CountBackEntry(data, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTimeSeriesAndCblValuesSizeMismatch() {
        new CountBackEntry(new TimeSeries(Utils.createTickData("/TEST_COUNT_BACK_LINE_TC1.stub")), new ArrayList<TADecimal>());
    }

    @Test
    public void testGetValueTC1() {
        TimeSeries data = new TimeSeries(Utils.createTickData("/TEST_COUNT_BACK_LINE_TC1.stub"));
        CountBackLine cbl = new CountBackLine(data);
        List<TADecimal> cblValues = new ArrayList<>();
        for (int i = 0; i <= data.getEnd(); i++) {
            cblValues.add(cbl.getValue(i));
        }
        CountBackEntry cbe = new CountBackEntry(data, cblValues);
        for (int i = 0; i < data.getEnd(); i++) {
            TADecimal entryValue = cbe.getValue(i);
            if (i == 34) { // Test data will contain two entry signals at positions 34 and 47
                assertEquals(31.22, entryValue.toDouble(), 0.01);
            } else if (i == 47) {
                assertEquals(32.47, cbe.getValue(i).toDouble(), 0.01);
            } else {
                assertNull(cbe.getValue(i));
            }
        }
    }

    @Test
    public void testGetValueTC3() {
        TimeSeries data = new TimeSeries(Utils.createTickData("/TEST_COUNT_BACK_LINE_TC3.stub", DateTimeFormat.forPattern("dd-MM-YYYY")));
        CountBackLine cbl = new CountBackLine(data);
        List<TADecimal> cblValues = new ArrayList<>();
        for (int i = 0; i <= data.getEnd(); i++) {
            cblValues.add(cbl.getValue(i));
        }
        CountBackEntry cbe = new CountBackEntry(data, cblValues);

        // Entry
        assertNotNull(cbe.getValue(27));
        assertEquals(4.22, cbe.getValue(27).toDouble(), 0.01);

        // Entry
        assertNotNull(cbe.getValue(46));
        assertEquals(4.66, cbe.getValue(46).toDouble(), 0.01);
    }

}
