package au.net.woodberry.ta.toolbox.indicators.trend;

import au.net.woodberry.ta.toolbox.enums.Group;
import eu.verdelhan.ta4j.indicators.CachedIndicator;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.TADecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuppyMultipleMovingAverageIndicator extends CachedIndicator<GuppyMultipleMovingAverageIndicator.Object> {

    private final EMAIndicator ema3;
    private final EMAIndicator ema5;
    private final EMAIndicator ema8;
    private final EMAIndicator ema10;
    private final EMAIndicator ema12;
    private final EMAIndicator ema15;
    private final EMAIndicator ema30;
    private final EMAIndicator ema35;
    private final EMAIndicator ema40;
    private final EMAIndicator ema45;
    private final EMAIndicator ema50;
    private final EMAIndicator ema60;
    
    // Short term moving averages ... 3,5,8,10,12,15 periods
    // Long term moving averages ... 30,35,40,45,50,60 periods

    public GuppyMultipleMovingAverageIndicator(Indicator<? extends TADecimal> indicator) {
        
        if (indicator == null) {
            throw new IllegalArgumentException("Supplied Indicator is invalid: NULL");
        }
        // Short term moving averages
        this.ema3 = new EMAIndicator(indicator, Period.THREE.getTimeFrame());
        this.ema5 = new EMAIndicator(indicator, Period.FIVE.getTimeFrame());
        this.ema8 = new EMAIndicator(indicator, Period.EIGHT.getTimeFrame());
        this.ema10 = new EMAIndicator(indicator, Period.TEN.getTimeFrame());
        this.ema12 = new EMAIndicator(indicator, Period.TWELVE.getTimeFrame());
        this.ema15 = new EMAIndicator(indicator, Period.FIFTEEN.getTimeFrame());

        // Long term moving averages
        this.ema30 = new EMAIndicator(indicator, Period.THIRTY.getTimeFrame());
        this.ema35 = new EMAIndicator(indicator, Period.THIRTYFIVE.getTimeFrame());
        this.ema40 = new EMAIndicator(indicator, Period.FORTY.getTimeFrame());
        this.ema45 = new EMAIndicator(indicator, Period.FORTYFIVE.getTimeFrame());
        this.ema50 = new EMAIndicator(indicator, Period.FIFTY.getTimeFrame());
        this.ema60 = new EMAIndicator(indicator, Period.SIXTY.getTimeFrame());
    }

    @Override
    public GuppyMultipleMovingAverageIndicator.Object calculate(int i) {
        GuppyMultipleMovingAverageIndicator.Object object = new GuppyMultipleMovingAverageIndicator.Object();

        // Short term moving averages
        if (i > Period.THREE.getTimeFrame() - 1)      object.setValue(Period.THREE, ema3.getValue(i));
        if (i > Period.FIVE.getTimeFrame() - 1)       object.setValue(Period.FIVE, ema5.getValue(i));
        if (i > Period.EIGHT.getTimeFrame() - 1)      object.setValue(Period.EIGHT, ema8.getValue(i));
        if (i > Period.TEN.getTimeFrame() - 1)        object.setValue(Period.TEN, ema10.getValue(i));
        if (i > Period.TWELVE.getTimeFrame() - 1)     object.setValue(Period.TWELVE, ema12.getValue(i));
        if (i > Period.FIFTEEN.getTimeFrame() - 1)    object.setValue(Period.FIFTEEN, ema15.getValue(i));

        // Long term moving averages
        if (i > Period.THIRTY.getTimeFrame() - 1)     object.setValue(Period.THIRTY, ema30.getValue(i));
        if (i > Period.THIRTYFIVE.getTimeFrame() - 1) object.setValue(Period.THIRTYFIVE, ema35.getValue(i));
        if (i > Period.FORTY.getTimeFrame() - 1)      object.setValue(Period.FORTY, ema40.getValue(i));
        if (i > Period.FORTYFIVE.getTimeFrame() - 1)  object.setValue(Period.FORTYFIVE, ema45.getValue(i));
        if (i > Period.FIFTY.getTimeFrame() - 1)      object.setValue(Period.FIFTY, ema50.getValue(i));
        if (i > Period.SIXTY.getTimeFrame() - 1)      object.setValue(Period.SIXTY, ema60.getValue(i));

        return object;
    }
    
    public enum Period {

        // Short term moving averages
        THREE(3, Group.SHORTTERM),
        FIVE(5, Group.SHORTTERM),
        EIGHT(8, Group.SHORTTERM),
        TEN(10, Group.SHORTTERM),
        TWELVE(12, Group.SHORTTERM),
        FIFTEEN(15, Group.SHORTTERM),

        // Long term moving averages
        THIRTY(30, Group.LONGTERM),
        THIRTYFIVE(35, Group.LONGTERM),
        FORTY(40, Group.LONGTERM),
        FORTYFIVE(45, Group.LONGTERM),
        FIFTY(50, Group.LONGTERM),
        SIXTY(60, Group.LONGTERM);

        private final int timeFrame;
        private final Group group;

        Period(int timeFrame, Group group) {
            this.timeFrame = timeFrame;
            this.group = group;
        }

        public Group getGroup() {
            return group;
        }

        public int getTimeFrame() {
            return timeFrame;
        }
    }

    public class Object {
        
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
}
