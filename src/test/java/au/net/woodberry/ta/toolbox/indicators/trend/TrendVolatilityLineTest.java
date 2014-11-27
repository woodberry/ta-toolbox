package au.net.woodberry.ta.toolbox.indicators.trend;

import au.net.woodberry.ta.toolbox.enums.Sustainability;
import au.net.woodberry.ta.toolbox.indicators.StubDataTestUtils;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static au.net.woodberry.ta.toolbox.indicators.TADecimalTestsUtils.assertDecimalEquals;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TrendVolatilityLineTest {

    private final Sustainability sustainability;

    private final GuppyMultipleMovingAverage gmmaIndicator;

    private final TimeSeries data;

    private final double entry;

    private final double tvlValue;

    private final TrendVolatilityLine trendVolatilityLine;

    private final DateTime from;
    private final DateTime to;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                        { Sustainability.UNKNOWN, 6.48, 6.48, DateTime.parse("2014-09-10"), DateTime.parse("2014-09-18") },
                        { Sustainability.HOPE, 6.48, 6.48, DateTime.parse("2014-09-19"), DateTime.parse("2014-09-23") },
                        { Sustainability.CONFIDENT, 6.48, 6.48, DateTime.parse("2014-09-24"), DateTime.parse("2014-09-25") },
                        { Sustainability.CERTAINTY, 6.48, 6.48, DateTime.parse("2014-09-26"), DateTime.parse("2014-10-05") },
                        { Sustainability.CERTAINTY, 6.48, 6.663, DateTime.parse("2014-10-06"), DateTime.parse("2014-10-23") },
                        { Sustainability.CERTAINTY, 6.48, 6.8479, DateTime.parse("2014-10-24"), DateTime.parse("2014-11-05") }
            }
        );
    }

    public TrendVolatilityLineTest(Sustainability sustainability, double entry, double tvlValue, DateTime from, DateTime to) {
        this.data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TREND_VOLATILITY_LINE.stub"));
        this.gmmaIndicator = new GuppyMultipleMovingAverage(new ClosePriceIndicator(data));
        this.sustainability = sustainability;
        this.entry = entry;
        this.tvlValue = tvlValue;
        this.trendVolatilityLine = new TrendVolatilityLine(gmmaIndicator, TADecimal.valueOf(entry));
        this.from = from;
        this.to = to;
    }

    @Test
    public void testCalculate() {
        for (int i = 0; i < data.getSize(); i++) {
            DateTime timestamp = data.getTick(i).getEndTime();
            if ((timestamp.isEqual(from) || timestamp.isAfter(from)) && (timestamp.isEqual(to) || timestamp.isBefore(to))) {
                assertEquals("Sustainability is incorrect at " + data.getTick(i).getEndTime().toString(),
                        sustainability, trendVolatilityLine.calculate(i).getSustainability());
                assertDecimalEquals(trendVolatilityLine.calculate(i).getValue(), tvlValue);
            }
        }
    }
}
