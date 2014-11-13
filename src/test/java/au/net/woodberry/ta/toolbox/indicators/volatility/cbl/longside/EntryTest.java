package au.net.woodberry.ta.toolbox.indicators.volatility.cbl.longside;

import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EntryTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullData() {
        new Entry(null, new ArrayList<TADecimal>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullCountBackLine() {
        TimeSeries data = new TimeSeries(Utils.createTickData("/TEST_COUNT_BACK_LINE_TC1.stub"));
        new Entry(data, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTimeSeriesAndCblValuesSizeMismatch() {
        new Entry(new TimeSeries(Utils.createTickData("/TEST_COUNT_BACK_LINE_TC1.stub")), new ArrayList<TADecimal>());
    }

    @Test
    public void testGetValue() {
        TimeSeries data = new TimeSeries(Utils.createTickData("/TEST_COUNT_BACK_LINE_TC1.stub"));
        CountBackLine cbl = new CountBackLine(data);
        List<TADecimal> cblValues = new ArrayList<>();
        for (int i = 0; i <= data.getEnd(); i++) {
            cblValues.add(cbl.getValue(i));
        }
        Entry entry = new Entry(data, cblValues);
        for (int i = 0; i < data.getEnd(); i++) {
            TADecimal entryValue = entry.getValue(i);
            if (i == 34) { // Test data will contain two entry signals at positions 34 and 47
                assertEquals(31.22, entryValue.toDouble(), 0.01);
            } else if (i == 47) {
                assertEquals(32.47, entry.getValue(i).toDouble(), 0.01);
            } else {
                assertNull(entry.getValue(i));
            }
        }
    }

}
