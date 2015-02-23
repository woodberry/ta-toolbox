package au.net.woodberry.ta.toolbox.indicators.trend;

import au.net.woodberry.ta.toolbox.enums.Group;
import au.net.woodberry.ta.toolbox.enums.Period;
import au.net.woodberry.ta.toolbox.indicators.StubDataTestUtils;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import org.junit.Before;
import org.junit.Test;

import static au.net.woodberry.ta.toolbox.indicators.Assertions.assertHullMultipleMovingAverage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HullMultipleMovingAverageIndicatorTest {

    private HullMultipleMovingAverageIndicator hmmaIndicator;

    @Before
    public void before() {
        TimeSeries data = new TimeSeries(StubDataTestUtils.createTickData("/TEST_HULL_MULTIPLE_MOVING_AVERAGE_TC1.stub"));
        hmmaIndicator = new HullMultipleMovingAverageIndicator(new ClosePriceIndicator(data));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullIndicatorInput() {
        new HullMultipleMovingAverageIndicator(null);
    }

    @Test
    public void testInitialPositionValuesAreNull() {
        Double[] expectedValues = {null, null, null, null, null, null, null, null, null, null, null, null};
        assertHullMultipleMovingAverage(hmmaIndicator.getValue(0), false, expectedValues);
    }

    @Test
    public void testValueInsideShortTermGroup() {
        Double[] expectedValues = {6.0549, 6.0301, 5.994, 5.95458, 5.9163, null, null, null, null, null, null, null};
        assertHullMultipleMovingAverage(hmmaIndicator.getValue(11), false, expectedValues);
    }

    @Test
    public void testValueInsideLongTermGroup() {
        Double[] expectedValues = {6.927, 6.8046, 6.7109, 6.6374, 6.5769, 6.5248, 6.3617, 6.3122, 6.2671, 6.2261, null, null};
        assertHullMultipleMovingAverage(hmmaIndicator.getValue(30), false, expectedValues);
    }

    @Test
    public void testValueOutsideLongTermGroup() {
        Double[] expectedValues = {6.9821, 6.9786, 6.9592, 6.927, 6.888, 6.8465, 6.6826, 6.6263, 6.5735, 6.5242, 6.4782, 6.4353};
        assertHullMultipleMovingAverage(hmmaIndicator.getValue(37), true, expectedValues);
    }

    @Test
    public void testFinalValue() {
        Double[] expectedValues = {6.9742, 6.9728, 6.9489, 6.9111, 6.8677, 6.8226, 6.6518, 6.5947, 6.5415,6.4921,6.4462,6.4036};
        assertHullMultipleMovingAverage(hmmaIndicator.getValue(36), true, expectedValues);
    }

    @Test
    public void testLowestOfShortTermGroup() {
        assertEquals(Period.THIRTEEN, hmmaIndicator.getValue(Period.THIRTEEN.getTimeFrame()).lowestOf(Group.SHORTTERM));
    }

    @Test
    public void testHighestOfShortTermGroup() {
        assertEquals(Period.THREE, hmmaIndicator.getValue(Period.THIRTEEN.getTimeFrame()).highestOf(Group.SHORTTERM));
    }

    @Test
    public void testLowestOfLongTermGroup() {
        assertEquals(Period.THIRTYSIX, hmmaIndicator.getValue(Period.THIRTYSIX.getTimeFrame()).lowestOf(Group.LONGTERM));
    }

    @Test
    public void testHighestOfLongTermGroup() {
        assertEquals(Period.TWENTYONE, hmmaIndicator.getValue(Period.THIRTYSIX.getTimeFrame()).highestOf(Group.LONGTERM));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testShortestOfShortTermGroupThrowsExceptionWhenGmmaIsNotComplete() {
        assertNull(hmmaIndicator.getValue(Period.THREE.getTimeFrame() - 1).shortestOf(Group.SHORTTERM));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testLongestOfShortTermGroupThrowsExceptionWhenGmmaIsNotComplete() {
        assertNull(hmmaIndicator.getValue(Period.THREE.getTimeFrame() - 1).longestOf(Group.SHORTTERM));
    }

    @Test
    public void testShortestOfShortTermGroup() {
        assertEquals(Period.THREE, hmmaIndicator.getValue(Period.SIXTY.getTimeFrame()).shortestOf(Group.SHORTTERM));
    }

    @Test
    public void testLongestOfShortTermGroup() {
        assertEquals(Period.THIRTEEN, hmmaIndicator.getValue(Period.SIXTY.getTimeFrame()).longestOf(Group.SHORTTERM));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testShortestOfLongTermGroupThrowsExceptionWhenGmmaIsNotComplete() {
        assertNull(hmmaIndicator.getValue(Period.THREE.getTimeFrame() - 1).shortestOf(Group.LONGTERM));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testLongestOfLongTermGroupThrowsExceptionWhenGmmaIsNotComplete() {
        assertNull(hmmaIndicator.getValue(Period.THREE.getTimeFrame() - 1).longestOf(Group.LONGTERM));
    }

    @Test
    public void testShortestOfLongTermGroup() {
        assertEquals(Period.TWENTYONE, hmmaIndicator.getValue(Period.SIXTY.getTimeFrame()).shortestOf(Group.LONGTERM));
    }

    @Test
    public void testLongestOfLongTermGroup() {
        assertEquals(Period.THIRTYSIX, hmmaIndicator.getValue(Period.SIXTY.getTimeFrame()).longestOf(Group.LONGTERM));
    }
}
