package au.net.woodberry.ta.toolbox.indicators.volume;

import au.net.woodberry.ta.toolbox.enums.MarketCapitalization;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static au.net.woodberry.ta.toolbox.indicators.Assertions.assertDecimalEquals;
import static org.junit.Assert.assertTrue;

public class MarketCapitalizationIndicatorTest {
    
    @Test
    public void testLargeMarketCapitalization() {
        List<Tick> ticks = Arrays.asList(new Tick(DateTime.parse("2001-01-01"),1,1,1,2,5000000000.0));
        TimeSeries timeSeries = new TimeSeries(ticks);
        MarketCapitalizationIndicator marketCapitalizationIndicator = new MarketCapitalizationIndicator(timeSeries);
        TADecimal marketCap = marketCapitalizationIndicator.getValue(0);
        assertDecimalEquals(marketCap, 10000000000.0);
        assertTrue(MarketCapitalization.getCapitalization(marketCap).equals(MarketCapitalization.LARGE_CAP));
    }

    @Test
    public void testMidMarketCapitalization() {
        List<Tick> ticks = Arrays.asList(new Tick(DateTime.parse("2001-01-01"),1,1,1,1,2000000000.0));
        TimeSeries timeSeries = new TimeSeries(ticks);
        MarketCapitalizationIndicator marketCapitalizationIndicator = new MarketCapitalizationIndicator(timeSeries);
        TADecimal marketCap = marketCapitalizationIndicator.getValue(0);
        assertDecimalEquals(marketCap, 2000000000.0);
        assertTrue(MarketCapitalization.getCapitalization(marketCap).equals(MarketCapitalization.MID_CAP));
    }

    @Test
    public void testSmallMarketCapitalization() {
        List<Tick> ticks = Arrays.asList(new Tick(DateTime.parse("2001-01-01"),1,1,1,4,40000000.0));
        TimeSeries timeSeries = new TimeSeries(ticks);
        MarketCapitalizationIndicator marketCapitalizationIndicator = new MarketCapitalizationIndicator(timeSeries);
        TADecimal marketCap = marketCapitalizationIndicator.getValue(0);
        assertDecimalEquals(marketCap, 160000000.0);
        assertTrue(MarketCapitalization.getCapitalization(marketCap).equals(MarketCapitalization.SMALL_CAP));
    }

    @Test
    public void testUnknownMarketCapitalization() {
        List<Tick> ticks = Arrays.asList(new Tick(DateTime.parse("2001-01-01"),1,1,1,1,0.0));
        TimeSeries timeSeries = new TimeSeries(ticks);
        MarketCapitalizationIndicator marketCapitalizationIndicator = new MarketCapitalizationIndicator(timeSeries);
        TADecimal marketCap = marketCapitalizationIndicator.getValue(0);
        assertDecimalEquals(marketCap, 0.0);
        assertTrue(MarketCapitalization.getCapitalization(marketCap).equals(MarketCapitalization.UNKNOWN));
    }
}
