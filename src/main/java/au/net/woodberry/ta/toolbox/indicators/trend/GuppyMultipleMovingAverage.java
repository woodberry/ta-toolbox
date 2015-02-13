package au.net.woodberry.ta.toolbox.indicators.trend;

import au.net.woodberry.ta.toolbox.enums.Group;
import eu.verdelhan.ta4j.indicators.CachedIndicator;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.TADecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuppyMultipleMovingAverage extends CachedIndicator<GuppyMultipleMovingAverage.Object> {

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

    // use the EMA for calculation
    public GuppyMultipleMovingAverage(Indicator<? extends TADecimal> indicator) {
        
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
    protected GuppyMultipleMovingAverage.Object calculate(int i) {
        GuppyMultipleMovingAverage.Object object = new GuppyMultipleMovingAverage.Object();

        // Short term moving averages
        object.setValue(Period.THREE, ema3.getValue(i));
        object.setValue(Period.FIVE, ema5.getValue(i));
        object.setValue(Period.EIGHT, ema8.getValue(i));
        object.setValue(Period.TEN, ema10.getValue(i));
        object.setValue(Period.TWELVE, ema12.getValue(i));
        object.setValue(Period.FIFTEEN, ema15.getValue(i));

        // Long term moving averages
        object.setValue(Period.THIRTY, ema30.getValue(i));
        object.setValue(Period.THIRTYFIVE, ema35.getValue(i));
        object.setValue(Period.FORTY, ema40.getValue(i));
        object.setValue(Period.FORTYFIVE, ema45.getValue(i));
        object.setValue(Period.FIFTY, ema50.getValue(i));
        object.setValue(Period.SIXTY, ema60.getValue(i));

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

        private Map<Period, TADecimal> objectMap = new HashMap<>(12);

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
