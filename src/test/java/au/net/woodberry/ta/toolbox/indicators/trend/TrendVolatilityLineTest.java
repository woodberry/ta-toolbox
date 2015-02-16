package au.net.woodberry.ta.toolbox.indicators.trend;

import au.net.woodberry.ta.toolbox.enums.Sustainability;
import au.net.woodberry.ta.toolbox.indicators.StubDataTestUtils;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TrendVolatilityLineTest {
    
    private static final DateTimeFormatter DTF = DateTimeFormat.forPattern("dd/MM/YY");
    private static final double DELTA = 0.0001;

    
    @Test(expected = IllegalArgumentException.class)
    public void testNullGMMAIndicatorThrowsException() {
        new TrendVolatilityLine(null, TADecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullEntryValueThrowsException() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TREND_VOLATILITY_LINE_TC1.stub", "\t", null, DTF));
        new TrendVolatilityLine(new GuppyMultipleMovingAverage(new ClosePriceIndicator(timeSeries)), null);
    }
    
    @Test
    public void testCalculateForFirstEntryTC1() {
        final TADecimal entry = TADecimal.valueOf(4245.7);
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TREND_VOLATILITY_LINE_TC1.stub", "\t", null, DTF));
        TrendVolatilityLine tvlIndicator = new TrendVolatilityLine(new GuppyMultipleMovingAverage(new ClosePriceIndicator(timeSeries)), entry);
        TrendVolatilityLine.Object tvl = tvlIndicator.calculate(index(timeSeries, DateTime.parse("1/3/12", DTF)));
        assertNull(tvl.getValue());
        assertEquals(Sustainability.UNKNOWN, tvl.getSustainability());
    }
    
    @Test
    public void testCalculateWhenUnknownSustainabilityTurnsToHopeTC1() {
        final TADecimal entry = TADecimal.valueOf(4245.7);
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TREND_VOLATILITY_LINE_TC1.stub", "\t", null, DTF));
        TrendVolatilityLine tvlIndicator = new TrendVolatilityLine(new GuppyMultipleMovingAverage(new ClosePriceIndicator(timeSeries)), entry);
        
        TrendVolatilityLine.Object tvl = tvlIndicator.calculate(index(timeSeries, DateTime.parse("27/7/12", DTF)));
        assertNull(tvl.getValue());
        assertEquals(Sustainability.UNKNOWN, tvl.getSustainability());

        tvl = tvlIndicator.calculate(index(timeSeries, DateTime.parse("30/7/12", DTF)));
        assertNotNull(tvl.getValue());
        assertEquals(4245.7, tvl.getValue().toDouble(), DELTA);
        assertEquals(Sustainability.HOPE, tvl.getSustainability());
    }

    @Test
    public void testCalculateWhenHopeSustainabilityTurnsToConfidentTC1() {
        final TADecimal entry = TADecimal.valueOf(4245.7);
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TREND_VOLATILITY_LINE_TC1.stub", "\t", null, DTF));
        TrendVolatilityLine tvlIndicator = new TrendVolatilityLine(new GuppyMultipleMovingAverage(new ClosePriceIndicator(timeSeries)), entry);

        TrendVolatilityLine.Object tvl = tvlIndicator.calculate(index(timeSeries, DateTime.parse("10/8/12", DTF)));
        assertNotNull(tvl.getValue());
        assertEquals(4245.7, tvl.getValue().toDouble(), DELTA);
        assertEquals(Sustainability.HOPE, tvl.getSustainability());
        
        tvl = tvlIndicator.calculate(index(timeSeries, DateTime.parse("13/8/12", DTF)));
        assertNotNull(tvl.getValue());
        assertEquals(4245.7, tvl.getValue().toDouble(), DELTA);
        assertEquals(Sustainability.CONFIDENT, tvl.getSustainability());
    }

    @Test
    public void testCalculateWhenConfidentSustainabilityTurnsToCertaintyTC1() {
        final TADecimal entry = TADecimal.valueOf(4245.7);
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TREND_VOLATILITY_LINE_TC1.stub", "\t", null, DTF));
        TrendVolatilityLine tvlIndicator = new TrendVolatilityLine(new GuppyMultipleMovingAverage(new ClosePriceIndicator(timeSeries)), entry);

        tvlIndicator.calculate(index(timeSeries, DateTime.parse("10/8/12", DTF))); // Hope

        TrendVolatilityLine.Object tvl = tvlIndicator.calculate(index(timeSeries, DateTime.parse("20/8/12", DTF)));
        assertNotNull(tvl.getValue());
        assertEquals(4245.7, tvl.getValue().toDouble(), DELTA);
        assertEquals(Sustainability.CONFIDENT, tvl.getSustainability());
        
        tvl = tvlIndicator.calculate(index(timeSeries, DateTime.parse("21/8/12", DTF)));
        assertNotNull(tvl.getValue());
        assertEquals(4245.7, tvl.getValue().toDouble(), DELTA);
        assertEquals(Sustainability.CERTAINTY, tvl.getSustainability());
    }
    
    @Test
    public void testCalculateTVLValueUpdatesWhenTrendRisesWithinCertaintyTC1() {
        final TADecimal entry = TADecimal.valueOf(4245.7);
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TREND_VOLATILITY_LINE_TC1.stub", "\t", null, DTF));
        TrendVolatilityLine tvlIndicator = new TrendVolatilityLine(new GuppyMultipleMovingAverage(new ClosePriceIndicator(timeSeries)), entry);

        tvlIndicator.calculate(index(timeSeries, DateTime.parse("10/8/12", DTF))); // Hope
        tvlIndicator.calculate(index(timeSeries, DateTime.parse("20/8/12", DTF))); // Confident
        tvlIndicator.calculate(index(timeSeries, DateTime.parse("21/8/12", DTF))); // Certainty

        TrendVolatilityLine.Object tvl = tvlIndicator.calculate(index(timeSeries, DateTime.parse("31/8/12", DTF)));
        assertNotNull(tvl.getValue());
        assertEquals(4245.7, tvl.getValue().toDouble(), DELTA);
        assertEquals(Sustainability.CERTAINTY, tvl.getSustainability());

        tvl = tvlIndicator.calculate(index(timeSeries, DateTime.parse("3/9/12", DTF)));
        assertNotNull(tvl.getValue());
        assertEquals(4293.182773, tvl.getValue().toDouble(), DELTA);
        assertEquals(Sustainability.CERTAINTY, tvl.getSustainability());
    }
    
    @Test
    public void testCalculateWhenCertaintySustainabilityTurnsToUnknownTC1() {
        final TADecimal entry = TADecimal.valueOf(4245.7);
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TREND_VOLATILITY_LINE_TC1.stub", "\t", null, DTF));
        TrendVolatilityLine tvlIndicator = new TrendVolatilityLine(new GuppyMultipleMovingAverage(new ClosePriceIndicator(timeSeries)), entry);

        tvlIndicator.calculate(index(timeSeries, DateTime.parse("10/8/12", DTF))); // Hope
        tvlIndicator.calculate(index(timeSeries, DateTime.parse("20/8/12", DTF))); // Confident

        TrendVolatilityLine.Object tvl = tvlIndicator.calculate(index(timeSeries, DateTime.parse("9/11/12", DTF))); // Certainty
        assertEquals(Sustainability.CERTAINTY, tvl.getSustainability());

        tvl = tvlIndicator.calculate(index(timeSeries, DateTime.parse("12/11/12", DTF)));
        assertNotNull(tvl.getValue());
        assertEquals(4471.8808, tvl.getValue().toDouble(), DELTA);
        assertEquals(Sustainability.UNKNOWN, tvl.getSustainability());
    }
    
    @Test
    public void testCalculateLastTvlValueIsKeptWhileSustainabilityIsUnknownTC1() {

        final TADecimal entry = TADecimal.valueOf(4245.7);
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TREND_VOLATILITY_LINE_TC1.stub", "\t", null, DTF));
        TrendVolatilityLine tvlIndicator = new TrendVolatilityLine(new GuppyMultipleMovingAverage(new ClosePriceIndicator(timeSeries)), entry);

        tvlIndicator.calculate(index(timeSeries, DateTime.parse("10/8/12", DTF))); // Hope
        tvlIndicator.calculate(index(timeSeries, DateTime.parse("20/8/12", DTF))); // Confident
        tvlIndicator.calculate(index(timeSeries, DateTime.parse("9/11/12", DTF))); // Certainty
        TrendVolatilityLine.Object tvl = tvlIndicator.calculate(index(timeSeries, DateTime.parse("19/11/12", DTF))); // Unknown
        assertEquals(Sustainability.UNKNOWN, tvl.getSustainability());
        assertNotNull(tvl.getValue());
        assertEquals(4471.8808, tvl.getValue().toDouble(), DELTA);
    }

    @Test
    public void testCalculateTvlValueIsResetForNewTrendTC1() {

        final TADecimal entry = TADecimal.valueOf(4245.7);
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TREND_VOLATILITY_LINE_TC1.stub", "\t", null, DTF));
        TrendVolatilityLine tvlIndicator = new TrendVolatilityLine(new GuppyMultipleMovingAverage(new ClosePriceIndicator(timeSeries)), entry);

        tvlIndicator.calculate(index(timeSeries, DateTime.parse("10/8/12", DTF))); // Hope
        tvlIndicator.calculate(index(timeSeries, DateTime.parse("20/8/12", DTF))); // Confident
        tvlIndicator.calculate(index(timeSeries, DateTime.parse("9/11/12", DTF))); // Certainty
        tvlIndicator.calculate(index(timeSeries, DateTime.parse("30/11/12", DTF))); // Unknown
        TrendVolatilityLine.Object tvl = tvlIndicator.calculate(index(timeSeries, DateTime.parse("3/12/12", DTF)));
        assertNotNull(tvl.getValue());
        assertEquals(entry.toDouble(), tvl.getValue().toDouble(), DELTA);
    }
    
    @Test
    public void testCalculateFromSpecificIndexTC1() {
        final TADecimal entry = TADecimal.valueOf(4245.7);
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_TREND_VOLATILITY_LINE_TC1.stub", "\t", null, DTF));
        final int index = index(timeSeries, DateTime.parse("27/07/12", DTF));
        TrendVolatilityLine tvlIndicator = new TrendVolatilityLine(new GuppyMultipleMovingAverage(new ClosePriceIndicator(timeSeries)), index, entry);

        TrendVolatilityLine.Object tvl = tvlIndicator.calculate(index(timeSeries, DateTime.parse("30/7/12", DTF)));
        assertEquals(Sustainability.HOPE, tvl.getSustainability());
        tvl = tvlIndicator.calculate(index(timeSeries, DateTime.parse("13/8/12", DTF)));
        assertEquals(Sustainability.CONFIDENT, tvl.getSustainability());
        tvl = tvlIndicator.calculate(index(timeSeries, DateTime.parse("21/08/12", DTF)));
        assertEquals(Sustainability.CERTAINTY, tvl.getSustainability());
        tvl = tvlIndicator.calculate(index(timeSeries, DateTime.parse("12/11/12", DTF)));
        assertEquals(Sustainability.UNKNOWN, tvl.getSustainability());
    }
    
    private static Integer index(TimeSeries timeSeries, DateTime dateTime) {
        for (int i = 0; i <= timeSeries.getEnd(); i++) {
            if (timeSeries.getTick(i).getEndTime().equals(dateTime)) {
                return i;
            }
        }
        return null;
    }
}
