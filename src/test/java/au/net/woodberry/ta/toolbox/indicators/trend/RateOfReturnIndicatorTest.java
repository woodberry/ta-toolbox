package au.net.woodberry.ta.toolbox.indicators.trend;

import au.net.woodberry.ta.toolbox.indicators.Assertions;
import au.net.woodberry.ta.toolbox.indicators.StubDataTestUtils;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

public class RateOfReturnIndicatorTest {

    private static final DateTimeFormatter DTF = DateTimeFormat.forPattern("dd/MM/YY");
    
    @Test(expected = IllegalArgumentException.class)
    public void testRateOfReturnThrowsExceptionNullPriceIndicator() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_RATE_OF_RETURN_TC1.stub", "\t", null, DTF));
        new RateOfReturnIndicator(null, new ClosePriceIndicator(timeSeries));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testRateOfReturnThrowsExceptionNullReferenceIndicator() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_RATE_OF_RETURN_TC1.stub", "\t", null, DTF));
        new RateOfReturnIndicator(new ClosePriceIndicator(timeSeries), null);
    }
    
    @Test
    public void testRateOfReturnUsingClosePriceReferenceIndicator() {
        TimeSeries timeSeries = new TimeSeries(StubDataTestUtils.createTickData("/TEST_RATE_OF_RETURN_TC1.stub", "\t", null, DTF));
        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(timeSeries);
        RateOfReturnIndicator rateOfReturn = new RateOfReturnIndicator(closePriceIndicator);
        Assertions.assertDecimalEquals(rateOfReturn.calculate(10), 3.75372);
    }
}
