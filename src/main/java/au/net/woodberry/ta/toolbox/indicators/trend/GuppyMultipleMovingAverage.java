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

        /* Short term MA's */
        this.ema3 = new EMAIndicator(indicator, Period.THREE.getTimeFrame());
        this.ema5 = new EMAIndicator(indicator, Period.FIVE.getTimeFrame());
        this.ema8 = new EMAIndicator(indicator, Period.EIGHT.getTimeFrame());
        this.ema10 = new EMAIndicator(indicator, Period.TEN.getTimeFrame());
        this.ema12 = new EMAIndicator(indicator, Period.TWELVE.getTimeFrame());
        this.ema15 = new EMAIndicator(indicator, Period.FIFTEEN.getTimeFrame());

        /* Long term MA's */
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

        /* Short term MA's */
        object.setValue(Period.THREE, ema3.getValue(i));
        object.setValue(Period.FIVE, ema5.getValue(i));
        object.setValue(Period.EIGHT, ema8.getValue(i));
        object.setValue(Period.TEN, ema10.getValue(i));
        object.setValue(Period.TWELVE, ema12.getValue(i));
        object.setValue(Period.FIFTEEN, ema15.getValue(i));

        /* Long term MA's */
        object.setValue(Period.THIRTY, ema30.getValue(i));
        object.setValue(Period.THIRTYFIVE, ema35.getValue(i));
        object.setValue(Period.FORTY, ema40.getValue(i));
        object.setValue(Period.FORTYFIVE, ema45.getValue(i));
        object.setValue(Period.FIFTY, ema50.getValue(i));
        object.setValue(Period.SIXTY, ema60.getValue(i));

        return object;
    }

    public enum Period {

        /* Short term MA's */
        THREE(3, Group.SHORTTERM),
        FIVE(5, Group.SHORTTERM),
        EIGHT(8, Group.SHORTTERM),
        TEN(10, Group.SHORTTERM),
        TWELVE(12, Group.SHORTTERM),
        FIFTEEN(15, Group.SHORTTERM),

        /* Long term MA's */
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

    /**
     * Object abstraction of the GMMA underlying data store,
     * so that consumers of the indicator do not have to work directly with the map
     */
    public class Object {

        private Map<Period, TADecimal> objectMap = new HashMap<>(12);

        public void setValue(Period period, TADecimal value) {
            objectMap.put(period, value);
        }

        /**
         *
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
         *
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
         *
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
    }
}
