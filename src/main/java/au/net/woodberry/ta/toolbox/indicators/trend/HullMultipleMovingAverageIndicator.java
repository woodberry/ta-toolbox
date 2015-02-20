package au.net.woodberry.ta.toolbox.indicators.trend;

import au.net.woodberry.ta.toolbox.enums.Period;
import au.net.woodberry.ta.toolbox.object.MultipleMovingAverage;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

/**
 * This GMMA indicator uses a different set of averages, which are typically used for longer periods
 * e.g. weekly, monthly data etc. 
 */
public class HullMultipleMovingAverageIndicator extends CachedIndicator<MultipleMovingAverage> {

    private final EMAIndicator ema3;
    private final EMAIndicator ema5;
    private final EMAIndicator ema7;
    private final EMAIndicator ema9;
    private final EMAIndicator ema11;
    private final EMAIndicator ema13;
    private final EMAIndicator ema21;
    private final EMAIndicator ema24;
    private final EMAIndicator ema27;
    private final EMAIndicator ema30;
    private final EMAIndicator ema33;
    private final EMAIndicator ema36;

    // Short term moving averages ... 3,5,7,9,11,13 periods
    // Long term moving averages ... 21,24,27,30,33,36 periods

    public HullMultipleMovingAverageIndicator(Indicator<? extends TADecimal> indicator) {
        
        if (indicator == null) {
            throw new IllegalArgumentException("Supplied Indicator is invalid: NULL");
        }
        // Short term moving averages
        this.ema3 = new EMAIndicator(indicator, Period.THREE.getTimeFrame());
        this.ema5 = new EMAIndicator(indicator, Period.FIVE.getTimeFrame());
        this.ema7 = new EMAIndicator(indicator, Period.SEVEN.getTimeFrame());
        this.ema9 = new EMAIndicator(indicator, Period.NINE.getTimeFrame());
        this.ema11 = new EMAIndicator(indicator, Period.ELEVEN.getTimeFrame());
        this.ema13 = new EMAIndicator(indicator, Period.THIRTEEN.getTimeFrame());

        // Long term moving averages
        this.ema21 = new EMAIndicator(indicator, Period.TWENTYONE.getTimeFrame());
        this.ema24 = new EMAIndicator(indicator, Period.TWENTYFOUR.getTimeFrame());
        this.ema27 = new EMAIndicator(indicator, Period.TWENTYSEVEN.getTimeFrame());
        this.ema30 = new EMAIndicator(indicator, Period.THIRTY.getTimeFrame());
        this.ema33 = new EMAIndicator(indicator, Period.THIRTYTHREE.getTimeFrame());
        this.ema36 = new EMAIndicator(indicator, Period.THIRTYSIX.getTimeFrame());
    }
    
    @Override
    protected MultipleMovingAverage calculate(int index) {
        
        MultipleMovingAverage gmma = new MultipleMovingAverage();

        // Short term moving averages
        if (index > Period.THREE.getTimeFrame() - 1)       gmma.setValue(Period.THREE, ema3.getValue(index));
        if (index > Period.FIVE.getTimeFrame() - 1)        gmma.setValue(Period.FIVE, ema5.getValue(index));
        if (index > Period.SEVEN.getTimeFrame() - 1)       gmma.setValue(Period.SEVEN, ema7.getValue(index));
        if (index > Period.NINE.getTimeFrame() - 1)        gmma.setValue(Period.NINE, ema9.getValue(index));
        if (index > Period.ELEVEN.getTimeFrame() - 1)      gmma.setValue(Period.ELEVEN, ema11.getValue(index));
        if (index > Period.THIRTEEN.getTimeFrame() - 1)    gmma.setValue(Period.THIRTEEN, ema13.getValue(index));

        // Long term moving averages
        if (index > Period.TWENTYONE.getTimeFrame() - 1)   gmma.setValue(Period.TWENTYONE, ema21.getValue(index));
        if (index > Period.TWENTYFOUR.getTimeFrame() - 1)  gmma.setValue(Period.TWENTYFOUR, ema24.getValue(index));
        if (index > Period.TWENTYSEVEN.getTimeFrame() - 1) gmma.setValue(Period.TWENTYSEVEN, ema27.getValue(index));
        if (index > Period.THIRTY.getTimeFrame() - 1)      gmma.setValue(Period.THIRTY, ema30.getValue(index));
        if (index > Period.THIRTYTHREE.getTimeFrame() - 1) gmma.setValue(Period.THIRTYTHREE, ema33.getValue(index));
        if (index > Period.THIRTYSIX.getTimeFrame() - 1)   gmma.setValue(Period.THIRTYSIX, ema36.getValue(index));

        return gmma;
    }
}
