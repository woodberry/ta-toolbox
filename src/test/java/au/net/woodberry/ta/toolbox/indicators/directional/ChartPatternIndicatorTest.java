package au.net.woodberry.ta.toolbox.indicators.directional;

import au.net.woodberry.ta.toolbox.enums.Sentiment;
import au.net.woodberry.ta.toolbox.object.ChartPattern;
import au.net.woodberry.ta.toolbox.object.NR7Breakout;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static au.net.woodberry.ta.toolbox.indicators.Assertions.assertDecimalEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ChartPatternIndicatorTest {
   
    @Test
    public void testReturnsNullForNoBullishOrBearishSignalsOnSpecifiedIndexes() {
        TimeSeries timeSeries = new TimeSeries(Arrays.asList(
                new Tick(null, DateTime.parse("2000-01-01")),
                new Tick(null, DateTime.parse("2000-01-02")),
                new Tick(null, DateTime.parse("2000-01-03"))
        ));
        List<NR7Breakout> breakouts = Arrays.asList(
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-01")), new Tick(null, DateTime.parse("2000-01-03")), Sentiment.BULLISH, 2),
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-02")), new Tick(null, DateTime.parse("2000-01-03")), Sentiment.BEARISH, 1)
        );
        ChartPatternIndicator chartPatternIndicator = new ChartPatternIndicator(timeSeries, breakouts);
        assertNull(chartPatternIndicator.getValue(0));
        assertNull(chartPatternIndicator.getValue(1));
    }

    @Test
    public void testNullSentiment() {
        TimeSeries timeSeries = new TimeSeries(Arrays.asList(
                new Tick(null, DateTime.parse("2000-01-01")),
                new Tick(null, DateTime.parse("2000-01-02")),
                new Tick(null, DateTime.parse("2000-01-03"))
        ));
        List<NR7Breakout> breakouts = Arrays.asList(
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-01")), new Tick(null, DateTime.parse("2000-01-03")), Sentiment.BULLISH, 2),
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-02")), new Tick(null, DateTime.parse("2000-01-03")), Sentiment.BEARISH, 1)
        );
        ChartPatternIndicator chartPatternIndicator = new ChartPatternIndicator(timeSeries, breakouts);
        ChartPattern chartPattern = chartPatternIndicator.getValue(2);
        assertNotNull(chartPattern);
        assertDecimalEquals(chartPattern.getRatio(), 50);
        assertEquals(null, chartPattern.getSentiment());
    }
    
    @Test
    public void testBullishSentiment() {
        TimeSeries timeSeries = new TimeSeries(Arrays.asList(
                new Tick(null, DateTime.parse("2000-01-01")),
                new Tick(null, DateTime.parse("2000-01-02")),
                new Tick(null, DateTime.parse("2000-01-03")),
                new Tick(null, DateTime.parse("2000-01-04")),
                new Tick(null, DateTime.parse("2000-01-05")),
                new Tick(null, DateTime.parse("2000-01-06"))
        ));
        List<NR7Breakout> breakouts = Arrays.asList(
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-01")), new Tick(null, DateTime.parse("2000-01-02")), Sentiment.BULLISH, 1),
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-01")), new Tick(null, DateTime.parse("2000-01-02")), Sentiment.BEARISH, 1),
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-02")), new Tick(null, DateTime.parse("2000-01-04")), Sentiment.BULLISH, 2),
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-02")), new Tick(null, DateTime.parse("2000-01-06")), Sentiment.BULLISH, 4),
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-02")), new Tick(null, DateTime.parse("2000-01-06")), Sentiment.BULLISH, 4),
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-02")), new Tick(null, DateTime.parse("2000-01-06")), Sentiment.BULLISH, 4),
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-01")), new Tick(null, DateTime.parse("2000-01-06")), Sentiment.BEARISH, 4)
        );
        ChartPatternIndicator chartPatternIndicator = new ChartPatternIndicator(timeSeries, breakouts);
        assertNotNull(chartPatternIndicator.getValue(1));
        assertNull(chartPatternIndicator.getValue(2)); // <- No break out signals on this tick/period
        assertNotNull(chartPatternIndicator.getValue(3));
        assertNotNull(chartPatternIndicator.getValue(5));
        assertDecimalEquals(chartPatternIndicator.getValue(1).getRatio(), 50);
        assertDecimalEquals(chartPatternIndicator.getValue(3).getRatio(), 100);
        assertDecimalEquals(chartPatternIndicator.getValue(5).getRatio(), 75);
        assertEquals(Sentiment.BULLISH, chartPatternIndicator.getValue(5).getSentiment());
    }
    
    @Test
    public void testBearishSentiment() {
        TimeSeries timeSeries = new TimeSeries(Arrays.asList(
                new Tick(null, DateTime.parse("2000-01-01")),
                new Tick(null, DateTime.parse("2000-01-02")),
                new Tick(null, DateTime.parse("2000-01-03")),
                new Tick(null, DateTime.parse("2000-01-04")),
                new Tick(null, DateTime.parse("2000-01-05")),
                new Tick(null, DateTime.parse("2000-01-06"))
        ));
        List<NR7Breakout> breakouts = Arrays.asList(
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-01")), new Tick(null, DateTime.parse("2000-01-02")), Sentiment.BEARISH, 1),
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-01")), new Tick(null, DateTime.parse("2000-01-02")), Sentiment.BEARISH, 1),
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-02")), new Tick(null, DateTime.parse("2000-01-04")), Sentiment.BULLISH, 2),
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-03")), new Tick(null, DateTime.parse("2000-01-05")), Sentiment.BEARISH, 2),
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-03")), new Tick(null, DateTime.parse("2000-01-05")), Sentiment.BEARISH, 2),
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-03")), new Tick(null, DateTime.parse("2000-01-05")), Sentiment.BULLISH, 2),
                new NR7Breakout(new Tick(null, DateTime.parse("2000-01-03")), new Tick(null, DateTime.parse("2000-01-05")), Sentiment.BEARISH, 2)
        );
        ChartPatternIndicator chartPatternIndicator = new ChartPatternIndicator(timeSeries, breakouts);
        assertNotNull(chartPatternIndicator.getValue(1));
        assertNotNull(chartPatternIndicator.getValue(3));
        assertDecimalEquals(chartPatternIndicator.getValue(1).getRatio(), 0);
        assertDecimalEquals(chartPatternIndicator.getValue(3).getRatio(), 100);
        assertEquals(Sentiment.BULLISH, chartPatternIndicator.getValue(3).getSentiment());
        assertDecimalEquals(chartPatternIndicator.getValue(4).getRatio(), 25);
        assertEquals(Sentiment.BEARISH, chartPatternIndicator.getValue(1).getSentiment());
    }
}
