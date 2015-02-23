package au.net.woodberry.ta.toolbox.indicators.trend;

import au.net.woodberry.ta.toolbox.enums.Group;
import au.net.woodberry.ta.toolbox.enums.Period;
import au.net.woodberry.ta.toolbox.indicators.StubDataTestUtils;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import org.junit.Before;
import org.junit.Test;

import static au.net.woodberry.ta.toolbox.indicators.Assertions.assertGuppyMultipleMovingAverage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GuppyMultipleMovingAverageIndicatorTest {

    private GuppyMultipleMovingAverageIndicator gmmaIndicator;

    @Before
    public void before() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_GUPPY_MULTIPLE_MOVING_AVERAGE_TC1.stub"));
        gmmaIndicator = new GuppyMultipleMovingAverageIndicator(new ClosePriceIndicator(data));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullIndicatorInput() {
        new GuppyMultipleMovingAverageIndicator(null);
    }

    @Test
    public void testInitialPositionValuesAreNull() {
        Double[] expectedValues = {null, null, null, null, null, null, null, null, null, null, null, null};
        assertGuppyMultipleMovingAverage(gmmaIndicator.getValue(0), false, expectedValues);
    }

    @Test
    public void testValueInsideShortTermGroup() {
        Double[] expectedValues = {6.0549, 6.0301, 5.9743, 5.9351, null, null, null, null, null, null, null, null};
        assertGuppyMultipleMovingAverage(gmmaIndicator.getValue(11), false, expectedValues);
    }

    @Test
    public void testValueInsideLongTermGroup() {
        Double[] expectedValues = {6.8078, 6.8039, 6.8164, 6.8203, 6.8177, 6.8029, 6.6416, 6.5813, 6.5235, 6.4691, null, null};
        assertGuppyMultipleMovingAverage(gmmaIndicator.getValue(45), false, expectedValues);
    }

    @Test
    public void testValueOutsideLongTermGroup() {
        Double[] expectedValues = {7.6664, 7.6421, 7.5915, 7.5580, 7.5260, 7.4804, 7.2757, 7.2113, 7.1485, 7.0877, 7.0292, 6.9197};
        assertGuppyMultipleMovingAverage(gmmaIndicator.getValue(70), true, expectedValues);
    }

    @Test
    public void testFinalValue() {
        Double[] expectedValues = {7.556, 7.583, 7.5847, 7.573, 7.5561, 7.5261, 7.3509, 7.2902, 7.23, 7.1709, 7.1135, 7.0046};
        assertGuppyMultipleMovingAverage(gmmaIndicator.getValue(74), true, expectedValues);
    }
    
    @Test
    public void testLowestOfShortTermGroup() {
        assertEquals(Period.FIFTEEN, gmmaIndicator.getValue(Period.THIRTY.getTimeFrame()).lowestOf(Group.SHORTTERM));
    }
    
    @Test
    public void testHighestOfShortTermGroup() {
        assertEquals(Period.THREE, gmmaIndicator.getValue(Period.THIRTY.getTimeFrame()).highestOf(Group.SHORTTERM));
    }
    
    @Test
    public void testLowestOfLongTermGroup() {
        assertEquals(Period.SIXTY, gmmaIndicator.getValue(Period.SIXTY.getTimeFrame()).lowestOf(Group.LONGTERM));
    }
    
    @Test
    public void testHighestOfLongTermGroup() {
        assertEquals(Period.THIRTY, gmmaIndicator.getValue(Period.SIXTY.getTimeFrame()).highestOf(Group.LONGTERM));
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testShortestOfShortTermGroupThrowsExceptionWhenGmmaIsNotComplete() {
        assertNull(gmmaIndicator.getValue(Period.THREE.getTimeFrame() - 1).shortestOf(Group.SHORTTERM));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testLongestOfShortTermGroupThrowsExceptionWhenGmmaIsNotComplete() {
        assertNull(gmmaIndicator.getValue(Period.THREE.getTimeFrame() - 1).longestOf(Group.SHORTTERM));
    }
    
    @Test
    public void testShortestOfShortTermGroup() {
        assertEquals(Period.THREE, gmmaIndicator.getValue(Period.SIXTY.getTimeFrame()).shortestOf(Group.SHORTTERM));
    }
    
    @Test
    public void testLongestOfShortTermGroup() {
        assertEquals(Period.FIFTEEN, gmmaIndicator.getValue(Period.SIXTY.getTimeFrame()).longestOf(Group.SHORTTERM));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testShortestOfLongTermGroupThrowsExceptionWhenGmmaIsNotComplete() {
        assertNull(gmmaIndicator.getValue(Period.THREE.getTimeFrame() - 1).shortestOf(Group.LONGTERM));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testLongestOfLongTermGroupThrowsExceptionWhenGmmaIsNotComplete() {
        assertNull(gmmaIndicator.getValue(Period.THREE.getTimeFrame() - 1).longestOf(Group.LONGTERM));
    }

    @Test
    public void testShortestOfLongTermGroup() {
        assertEquals(Period.THIRTY, gmmaIndicator.getValue(Period.SIXTY.getTimeFrame()).shortestOf(Group.LONGTERM));
    }

    @Test
    public void testLongestOfLongTermGroup() {
        assertEquals(Period.SIXTY, gmmaIndicator.getValue(Period.SIXTY.getTimeFrame()).longestOf(Group.LONGTERM));
    }
}
