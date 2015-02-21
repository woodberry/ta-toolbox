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

public class TradersATRStopLossIndicatorTest {

    private static final DateTimeFormatter DTF = DateTimeFormat.forPattern("dd-MM-YYYY");
    private static final int LOOKBACK_PERIODS = 14;
    private static final int DISPLACEMENT_FACTOR = 2; //2xATR

    @Test(expected = IllegalArgumentException.class)
    public void testNullAtrIndicatorThrowsException() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_STOP_LOSS_TC1.stub", DTF));
        new TradersATRStopLossIndicator(null, new ClosePriceIndicator(data), DISPLACEMENT_FACTOR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullReferenceIndicatorThrowsException() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_STOP_LOSS_TC1.stub", DTF));
        new TradersATRStopLossIndicator(new AverageTrueRangeIndicator(data, LOOKBACK_PERIODS), null, DISPLACEMENT_FACTOR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroDisplacementFactorThrowsException() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_STOP_LOSS_TC1.stub", DTF));
        new TradersATRStopLossIndicator(new AverageTrueRangeIndicator(data, LOOKBACK_PERIODS), new ClosePriceIndicator(data), 0);
    }

    @Test
    public void testTradersAtrStopLossLessThanAtrLookBackValueIsNull() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_STOP_LOSS_TC1.stub", DTF));
        TradersATRStopLossIndicator tradersAtrStopLossIndicator = new TradersATRStopLossIndicator(new AverageTrueRangeIndicator(data, LOOKBACK_PERIODS), new ClosePriceIndicator(data), DISPLACEMENT_FACTOR);
        assertNull(tradersAtrStopLossIndicator.getValue(13));
    }

    @Test
    public void testTradersAtrStopLossValueAtLookBackPosition() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_STOP_LOSS_TC1.stub", DTF));
        TradersATRStopLossIndicator tradersAtrStopLossIndicator = new TradersATRStopLossIndicator(new AverageTrueRangeIndicator(data, LOOKBACK_PERIODS), new ClosePriceIndicator(data), DISPLACEMENT_FACTOR);
        TADecimal tradersAtr = tradersAtrStopLossIndicator.getValue(14);
        assertNotNull(tradersAtr);
        assertDecimalEquals(tradersAtr, 5742.2857);
    }

    @Test
    public void testTradersAtrStopLossValueAtPositionPastLookBackPosition() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_STOP_LOSS_TC1.stub", DTF));
        TradersATRStopLossIndicator tradersAtrStopLossIndicator = new TradersATRStopLossIndicator(new AverageTrueRangeIndicator(data, LOOKBACK_PERIODS), new ClosePriceIndicator(data), DISPLACEMENT_FACTOR);
        TADecimal tradersAtr = tradersAtrStopLossIndicator.getValue(28);
        assertNotNull(tradersAtr);
        assertDecimalEquals(tradersAtr, 5398.92711);
    }

    @Test
    public void testTradersAtrStopLossValueAtLastPositionPastLookBackPosition() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TRADERS_ATR_STOP_LOSS_TC1.stub", DTF));
        TradersATRStopLossIndicator tradersAtrStopLossIndicator = new TradersATRStopLossIndicator(new AverageTrueRangeIndicator(data, LOOKBACK_PERIODS), new ClosePriceIndicator(data), DISPLACEMENT_FACTOR);
        TADecimal tradersAtr = tradersAtrStopLossIndicator.getValue(data.getEnd());
        assertNotNull(tradersAtr);
        assertDecimalEquals(tradersAtr, 4999.1219);
    }
}
