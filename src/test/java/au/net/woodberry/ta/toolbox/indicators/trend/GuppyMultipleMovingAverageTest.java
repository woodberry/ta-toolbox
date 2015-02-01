package au.net.woodberry.ta.toolbox.indicators.trend;

import au.net.woodberry.ta.toolbox.enums.Group;
import au.net.woodberry.ta.toolbox.indicators.StubDataTestUtils;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.TimeSeries;
import org.junit.Before;
import org.junit.Test;

import static au.net.woodberry.ta.toolbox.indicators.TADecimalTestsUtils.assertDecimalEquals;
import static org.junit.Assert.assertEquals;

public class GuppyMultipleMovingAverageTest {

    private GuppyMultipleMovingAverage gmmaIndicator;

    @Before
    public void before() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_GUPPY_MULTIPLE_MOVING_AVERAGE_TC1.stub"));
        gmmaIndicator = new GuppyMultipleMovingAverage(new ClosePriceIndicator(data));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullIndicatorInput() {
        gmmaIndicator = new GuppyMultipleMovingAverage(null);
    }

    @Test
    public void testInitialValue() {
        double[] expectedValues = {5.42, 5.42, 5.42, 5.42, 5.42, 5.42, 5.42, 5.42, 5.42, 5.42, 5.42, 5.42};
        assertGuppyMultipleMovingAverage(gmmaIndicator.getValue(0), expectedValues);
    }

    @Test
    public void testValueInsideShortTermGroup() {
        double[] expectedValues = {6.0549, 6.0301, 5.9743, 5.9351, 5.8982, 5.8493, 5.6966, 5.6665, 5.6421, 5.6221, 5.6054, 5.579};
        assertGuppyMultipleMovingAverage(gmmaIndicator.getValue(11), expectedValues);
    }

    @Test
    public void testValueInsideLongTermGroup() {
        double[] expectedValues = {6.8078, 6.8039, 6.8164, 6.8203, 6.8177, 6.8029, 6.6416, 6.5813, 6.5235, 6.4691, 6.4184, 6.3279};
        assertGuppyMultipleMovingAverage(gmmaIndicator.getValue(45), expectedValues);
    }

    @Test
    public void testValueOutsideLongTermGroup() {
        double[] expectedValues = {7.6664, 7.6421, 7.5915, 7.5580, 7.5260, 7.4804, 7.2757, 7.2113, 7.1485, 7.0877, 7.0292, 6.9197};
        assertGuppyMultipleMovingAverage(gmmaIndicator.getValue(70), expectedValues);
    }

    @Test
    public void testFinalValue() {
        double[] expectedValues = {7.556, 7.583, 7.5847, 7.573, 7.5561, 7.5261, 7.3509, 7.2902, 7.23, 7.1709, 7.1135, 7.0046};
        assertGuppyMultipleMovingAverage(gmmaIndicator.getValue(74), expectedValues);
    }
    
    @Test
    public void testLowestOfShortTermGroup() {
        assertEquals(GuppyMultipleMovingAverage.Period.FIFTEEN, gmmaIndicator.getValue(1).lowestOf(Group.SHORTTERM));
    }
    
    @Test
    public void testHighestOfShortTermGroup() {
        assertEquals(GuppyMultipleMovingAverage.Period.THREE, gmmaIndicator.getValue(1).highestOf(Group.SHORTTERM));
    }
    
    @Test
    public void testLowestOfLongTermGroup() {
        assertEquals(GuppyMultipleMovingAverage.Period.SIXTY, gmmaIndicator.getValue(1).lowestOf(Group.LONGTERM));
    }
    
    @Test
    public void testHighestOfLongTermGroup() {
        assertEquals(GuppyMultipleMovingAverage.Period.THIRTY, gmmaIndicator.getValue(1).highestOf(Group.LONGTERM));
    }

    private static void assertGuppyMultipleMovingAverage(GuppyMultipleMovingAverage.Object gmma, double... expectedValues) {
        assertEquals(12, gmma.getValues().size()); // 12 - One for each period
        assertEquals(6, gmma.getValues(Group.SHORTTERM).size());
        assertEquals(6, gmma.getValues(Group.LONGTERM).size());
        assertDecimalEquals(gmma.getValue(GuppyMultipleMovingAverage.Period.THREE), expectedValues[0]);
        assertDecimalEquals(gmma.getValue(GuppyMultipleMovingAverage.Period.FIVE), expectedValues[1]);
        assertDecimalEquals(gmma.getValue(GuppyMultipleMovingAverage.Period.EIGHT), expectedValues[2]);
        assertDecimalEquals(gmma.getValue(GuppyMultipleMovingAverage.Period.TEN), expectedValues[3]);
        assertDecimalEquals(gmma.getValue(GuppyMultipleMovingAverage.Period.TWELVE), expectedValues[4]);
        assertDecimalEquals(gmma.getValue(GuppyMultipleMovingAverage.Period.FIFTEEN), expectedValues[5]);
        assertDecimalEquals(gmma.getValue(GuppyMultipleMovingAverage.Period.THIRTY), expectedValues[6]);
        assertDecimalEquals(gmma.getValue(GuppyMultipleMovingAverage.Period.THIRTYFIVE), expectedValues[7]);
        assertDecimalEquals(gmma.getValue(GuppyMultipleMovingAverage.Period.FORTY), expectedValues[8]);
        assertDecimalEquals(gmma.getValue(GuppyMultipleMovingAverage.Period.FORTYFIVE), expectedValues[9]);
        assertDecimalEquals(gmma.getValue(GuppyMultipleMovingAverage.Period.FIFTY), expectedValues[10]);
        assertDecimalEquals(gmma.getValue(GuppyMultipleMovingAverage.Period.SIXTY), expectedValues[11]);
    }
}
