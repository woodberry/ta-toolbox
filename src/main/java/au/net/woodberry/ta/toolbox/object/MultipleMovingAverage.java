package au.net.woodberry.ta.toolbox.object;

import au.net.woodberry.ta.toolbox.enums.Group;
import au.net.woodberry.ta.toolbox.enums.Period;
import eu.verdelhan.ta4j.TADecimal;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MultipleMovingAverage {

    private static final int MAP_SIZE = 12;

    private Map<Period, TADecimal> objectMap = new LinkedHashMap<>(MAP_SIZE);

    public void setValue(Period period, TADecimal value) {
        objectMap.put(period, value);
    }

    /**
     * @param period A single period for the required value
     * @return A value for the specified period
     */
    public TADecimal getValue(Period period) {
        if (period == null) {
            throw new IllegalArgumentException("Supplied Period is invalid: NULL");
        }
        return objectMap.get(period);
    }

    /**
     * Determines the lowest value from a given group
     * @param group - The group to determine the lowest value from
     * @return The lowest period for the supplied group or null, if none could be determined
     */
    public Period lowestOf(Group group) {
        Period lowest = null;
        TADecimal lowestValue = null;
        for (Period period : objectMap.keySet()) {
            if (period.getGroup().equals(group)) {
                if (lowest == null || objectMap.get(period).isLessThan(lowestValue)) {
                    lowest = period;
                    lowestValue = objectMap.get(period);
                }
            }
        }
        return lowest;
    }

    /**
     * Determines the highest value from a given group
     * @param group - The group to determine the highest value from
     * @return The highest period for the supplied group or null, if none could be determined
     */
    public Period highestOf(Group group) {
        Period highest = null;
        TADecimal highestValue = null;
        for (Period period : objectMap.keySet()) {
            if (period.getGroup().equals(group)) {
                if (highest == null || objectMap.get(period).isGreaterThan(highestValue)) {
                    highest = period;
                    highestValue = objectMap.get(period);
                }
            }
        }
        return highest;
    }

    /**
     * @return All period values
     */
    public List<TADecimal> getValues() {
        List<TADecimal> values = new ArrayList<>();
        for (Period period : objectMap.keySet()) {
            values.add(getValue(period));
        }
        return values;
    }

    /**
     * @param group
     * @return A list of values within the specified group
     */
    public List<TADecimal> getValues(Group group) {
        List<TADecimal> values = new ArrayList<>();
        for (Period period : objectMap.keySet()) {
            if (period.getGroup().equals(group)) {
                values.add(getValue(period));
            }
        }
        return values;
    }

    /**
     * A conveinence method that determines if this instance of the mma is complete. A complete mma object is one where
     * all period values are non-null which, by definition is at least n-samples of input data, where n is the largest period
     *
     * @return Whether the mma is complete. In most cases, only a complete mma should be used
     */
    public boolean isComplete() {
        // Ensure values defined for a period are not null and that the total map size is expected
        for (Period period : objectMap.keySet()) {
            if (objectMap.get(period) == null) {
                return false;
            }
        }
        return objectMap.size() == MAP_SIZE;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Period period : objectMap.keySet()) {
            builder.append(period + ": " + objectMap.get(period) + " ");
        }
        return builder.toString();
    }
}
