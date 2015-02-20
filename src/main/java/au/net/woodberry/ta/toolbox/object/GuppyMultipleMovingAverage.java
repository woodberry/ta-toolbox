package au.net.woodberry.ta.toolbox.object;

import au.net.woodberry.ta.toolbox.enums.Group;
import au.net.woodberry.ta.toolbox.enums.Period;
import eu.verdelhan.ta4j.TADecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuppyMultipleMovingAverage {

    private static final int MAP_SIZE = 12;

    private Map<Period, TADecimal> objectMap = new HashMap<>(MAP_SIZE);

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
     * A conveinence method that determines if this instance of the gmma is complete. A complete gmma object is one where
     * all period values are non-null which, by definition is at least 60-periods of input data
     *
     * @return Whether the gmma is complete. In most cases, only a complete gmma should be used
     */
    public boolean isComplete() {
        return  objectMap.get(Period.SIXTY) != null &&
                objectMap.get(Period.FIFTY) != null &&
                objectMap.get(Period.FORTYFIVE) != null &&
                objectMap.get(Period.FORTY) != null &&
                objectMap.get(Period.THIRTYFIVE) != null &&
                objectMap.get(Period.THIRTY) != null &&
                objectMap.get(Period.FIFTEEN) != null &&
                objectMap.get(Period.TWELVE) != null &&
                objectMap.get(Period.TEN) != null &&
                objectMap.get(Period.EIGHT) != null &&
                objectMap.get(Period.FIFTY) != null &&
                objectMap.get(Period.THREE) != null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(Period.THREE + ": " + objectMap.get(Period.THREE) + " ");
        builder.append(Period.FIVE + ": " + objectMap.get(Period.FIVE) + " ");
        builder.append(Period.EIGHT + ": " + objectMap.get(Period.EIGHT) + " ");
        builder.append(Period.TEN + ": " + objectMap.get(Period.TEN) + " ");
        builder.append(Period.TWELVE + ": " + objectMap.get(Period.TWELVE) + " ");
        builder.append(Period.FIFTEEN + ": " + objectMap.get(Period.FIFTEEN) + " ");
        builder.append(Period.THIRTY + ": " + objectMap.get(Period.THIRTY) + " ");
        builder.append(Period.THIRTYFIVE + ": " + objectMap.get(Period.THIRTYFIVE) + " ");
        builder.append(Period.FORTY + ": " + objectMap.get(Period.FORTY) + " ");
        builder.append(Period.FORTYFIVE + ": " + objectMap.get(Period.FORTYFIVE) + " ");
        builder.append(Period.FIFTY + ": " + objectMap.get(Period.FIFTY) + " ");
        builder.append(Period.SIXTY + ": " + objectMap.get(Period.SIXTY) + " ");
        return builder.toString();
    }
}
