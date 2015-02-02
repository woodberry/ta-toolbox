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
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class TrendVolatilityLineTest {

    private final Sustainability sustainability;

    private final GuppyMultipleMovingAverage gmmaIndicator;

    private final TimeSeries data;

    private final double expectedTvlValue;

    private final TrendVolatilityLine trendVolatilityLine;

    private final DateTime from;
    private final DateTime to;
    private final String stubFile;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {

                        // TC1
                        { "/TEST_TREND_VOLATILITY_LINE_TC1.stub", Sustainability.UNKNOWN, 6.48, 6.48, DateTime.parse("2014-09-10"), DateTime.parse("2014-09-18") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC1.stub", Sustainability.HOPE, 6.48, 6.48, DateTime.parse("2014-09-19"), DateTime.parse("2014-09-23") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC1.stub", Sustainability.CONFIDENT, 6.48, 6.48, DateTime.parse("2014-09-24"), DateTime.parse("2014-09-25") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC1.stub", Sustainability.CERTAINTY, 6.48, 6.48, DateTime.parse("2014-09-26"), DateTime.parse("2014-10-05") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC1.stub", Sustainability.CERTAINTY, 6.48, 6.663, DateTime.parse("2014-10-06"), DateTime.parse("2014-10-23") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC1.stub", Sustainability.CERTAINTY, 6.48, 6.8479, DateTime.parse("2014-10-24"), DateTime.parse("2014-11-05") },
                        
                        // TC2
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.UNKNOWN, 11.45, 11.45, DateTime.parse("2014-02-12"), DateTime.parse("2014-03-03") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 11.45, 11.9141, DateTime.parse("2014-03-04"), DateTime.parse("2014-03-04") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 11.9141, 11.9661, DateTime.parse("2014-03-05"), DateTime.parse("2014-03-05") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 11.9661, 12.0102, DateTime.parse("2014-03-06"), DateTime.parse("2014-03-06") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 12.0102, 12.056, DateTime.parse("2014-03-07"), DateTime.parse("2014-03-07") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 12.056, 12.0924, DateTime.parse("2014-03-10"), DateTime.parse("2014-03-10") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 12.0924, 12.1426, DateTime.parse("2014-03-11"), DateTime.parse("2014-03-11") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 12.1426, 12.1824, DateTime.parse("2014-03-12"), DateTime.parse("2014-03-12") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 12.1824, 12.2113, DateTime.parse("2014-03-13"), DateTime.parse("2014-03-13") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 12.2113, 12.2254, DateTime.parse("2014-03-14"), DateTime.parse("2014-03-14") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 12.2254, 12.2489, DateTime.parse("2014-03-17"), DateTime.parse("2014-03-17") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 12.2489, 12.2741, DateTime.parse("2014-03-18"), DateTime.parse("2014-03-18") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 12.2741, 12.3003, DateTime.parse("2014-03-19"), DateTime.parse("2014-03-19") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 12.3003, 12.3235, DateTime.parse("2014-03-20"), DateTime.parse("2014-03-20") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 12.3235, 12.3235, DateTime.parse("2014-03-21"), DateTime.parse("2014-03-21") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 12.3235, 12.391, DateTime.parse("2014-03-24"), DateTime.parse("2014-03-24") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 12.391, 12.391, DateTime.parse("2014-03-25"), DateTime.parse("2014-03-27") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 12.391, 12.4782, DateTime.parse("2014-03-28"), DateTime.parse("2014-03-28") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 12.4782, 12.4782, DateTime.parse("2014-03-28"), DateTime.parse("2014-04-08") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.CERTAINTY, 12.4782, 12.6088, DateTime.parse("2014-04-09"), DateTime.parse("2014-04-10") },
                        { "/TEST_TREND_VOLATILITY_LINE_TC2.stub", Sustainability.UNKNOWN, 12.6088, 12.6088, DateTime.parse("2014-04-11"), DateTime.parse("2014-05-01") }
            }
        );
    }

    public TrendVolatilityLineTest(String stubFile, Sustainability sustainability, double entry, double expectedTvlValue, DateTime from, DateTime to) {
        this.data = new TimeSeries(StubDataTestUtils.createTickData(stubFile));
        if (data.getSize() == 0) {
            throw new RuntimeException("Test setup failed: No tick data available for stub file \"" + stubFile + "");
        }
        this.gmmaIndicator = new GuppyMultipleMovingAverage(new ClosePriceIndicator(data));
        this.stubFile = stubFile;
        this.sustainability = sustainability;
        this.expectedTvlValue = expectedTvlValue;
        this.trendVolatilityLine = new TrendVolatilityLine(gmmaIndicator, TADecimal.valueOf(entry));
        this.from = from;
        this.to = to;
    }

    @Test
    public void testCalculate() {
        boolean testExecuted = false;
        for (int i = 0; i < data.getSize(); i++) {
            DateTime timestamp = data.getTick(i).getEndTime();
            if ((timestamp.isEqual(from) || timestamp.isAfter(from)) && (timestamp.isEqual(to) || timestamp.isBefore(to))) {
                String date = data.getTick(i).getEndTime().toString();
                assertEquals("Sustainability is incorrect at " + date + " stub data: " + stubFile, sustainability, trendVolatilityLine.calculate(i).getSustainability());
                assertDecimalEquals(trendVolatilityLine.calculate(i).getValue(), expectedTvlValue);
                testExecuted = true;
            }
        }
        if (!testExecuted) {
            fail("No tests were executed! Check test cases");
        }
    }
}
