package au.net.woodberry.ta.toolbox.indicators.volatility;

import au.net.woodberry.ta.toolbox.indicators.StubDataTestUtils;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import static au.net.woodberry.ta.toolbox.indicators.Assertions.assertDecimalEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TradersATRIndicatorTest {

    private static final DateTimeFormatter DTF = DateTimeFormat.forPattern("dd-MM-YYYY");
    private static final int LOOKBACK_PERIODS = 14;
    private static final int DISPLACEMENT_FACTOR = 2; //2xATR
    
    @Test(expected = IllegalArgumentException.class)
    public void testNullAtrIndicatorThrowsException() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_TC1.stub", DTF));
        new TradersATRIndicator(null, new ClosePriceIndicator(data), DISPLACEMENT_FACTOR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullReferenceIndicatorThrowsException() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_TC1.stub", DTF));
        new TradersATRIndicator(new AverageTrueRangeIndicator(data, LOOKBACK_PERIODS), null, DISPLACEMENT_FACTOR);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testZeroDisplacementFactorThrowsException() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_TC1.stub", DTF));
        new TradersATRIndicator(new AverageTrueRangeIndicator(data, LOOKBACK_PERIODS), new ClosePriceIndicator(data), 0);
    }
    
    @Test
    public void testTradersAtrLessThanAtrLookBackValueIsNull() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_TC1.stub", DTF));
        TradersATRIndicator tradersATRIndicator = new TradersATRIndicator(new AverageTrueRangeIndicator(data, LOOKBACK_PERIODS), new ClosePriceIndicator(data), DISPLACEMENT_FACTOR);
        assertNull(tradersATRIndicator.getValue(13));
    }

    @Test
    public void testTradersAtrValueAtLookBackPosition() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_TC1.stub", DTF));
        TradersATRIndicator tradersATRIndicator = new TradersATRIndicator(new AverageTrueRangeIndicator(data, LOOKBACK_PERIODS), new ClosePriceIndicator(data), DISPLACEMENT_FACTOR);
        TADecimal tradersAtr = tradersATRIndicator.getValue(14);
        assertNotNull(tradersAtr);
        assertDecimalEquals(tradersAtr, 3.0171);
    }

    @Test
    public void testTradersAtrValueAtRandomPositionPastLookBackPosition() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_TC1.stub", DTF));
        TradersATRIndicator tradersATRIndicator = new TradersATRIndicator(new AverageTrueRangeIndicator(data, LOOKBACK_PERIODS), new ClosePriceIndicator(data), DISPLACEMENT_FACTOR);
        TADecimal tradersAtr = tradersATRIndicator.getValue(28);
        assertNotNull(tradersAtr);
        assertDecimalEquals(tradersAtr, 3.0915);
    }

    @Test
    public void testTradersAtrValueAtLastPositionPastLookBackPosition() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_TC1.stub", DTF));
        TradersATRIndicator tradersATRIndicator = new TradersATRIndicator(new AverageTrueRangeIndicator(data, LOOKBACK_PERIODS), new ClosePriceIndicator(data), DISPLACEMENT_FACTOR);
        TADecimal tradersAtr = tradersATRIndicator.getValue(data.getEnd());
        assertNotNull(tradersAtr);
        assertDecimalEquals(tradersAtr, 3.1133);
    }
}
