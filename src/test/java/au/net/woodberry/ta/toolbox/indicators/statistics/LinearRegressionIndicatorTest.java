package au.net.woodberry.ta.toolbox.indicators.statistics;

import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static au.net.woodberry.ta.toolbox.indicators.Assertions.assertDecimalEquals;

public class LinearRegressionIndicatorTest {
    
    @Test
    public void testLinearRegressionIndicator() {
        /* 
         * Example adapted from: http://onlinestatbook.com/2/regression/intro.html
         */
        List<Tick> ticks = Arrays.asList(
                new Tick(DateTime.parse("2001-01-01"), 0,0,0,1,0),
                new Tick(DateTime.parse("2001-01-02"), 0,0,0,2,0),
                new Tick(DateTime.parse("2001-01-03"), 0,0,0,1.3,0),
                new Tick(DateTime.parse("2001-01-04"), 0,0,0,3.75,0),
                new Tick(DateTime.parse("2001-01-05"), 0,0,0,2.25,0)
        );
        TimeSeries timeSeries = new TimeSeries(ticks);
        LinearRegressionIndicator linearRegressionIndicator = new LinearRegressionIndicator(new ClosePriceIndicator(timeSeries), timeSeries.getEnd());

        assertDecimalEquals(linearRegressionIndicator.getValue(0), 1.21);
        assertDecimalEquals(linearRegressionIndicator.getValue(1), 1.635);
        assertDecimalEquals(linearRegressionIndicator.getValue(2), 2.06);
        assertDecimalEquals(linearRegressionIndicator.getValue(3), 2.485);
        assertDecimalEquals(linearRegressionIndicator.getValue(4), 2.91);
    }
}
