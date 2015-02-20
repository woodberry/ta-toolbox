package au.net.woodberry.ta.toolbox.indicators.trend;

import au.net.woodberry.ta.toolbox.enums.Period;
import au.net.woodberry.ta.toolbox.object.MultipleMovingAverage;
import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class GuppyMultipleMovingAverageIndicator extends CachedIndicator<MultipleMovingAverage> {

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
    public MultipleMovingAverage calculate(int index) {
        MultipleMovingAverage gmma = new MultipleMovingAverage();

        // Short term moving averages
        if (index > Period.THREE.getTimeFrame() - 1)      gmma.setValue(Period.THREE, ema3.getValue(index));
        if (index > Period.FIVE.getTimeFrame() - 1)       gmma.setValue(Period.FIVE, ema5.getValue(index));
        if (index > Period.EIGHT.getTimeFrame() - 1)      gmma.setValue(Period.EIGHT, ema8.getValue(index));
        if (index > Period.TEN.getTimeFrame() - 1)        gmma.setValue(Period.TEN, ema10.getValue(index));
        if (index > Period.TWELVE.getTimeFrame() - 1)     gmma.setValue(Period.TWELVE, ema12.getValue(index));
        if (index > Period.FIFTEEN.getTimeFrame() - 1)    gmma.setValue(Period.FIFTEEN, ema15.getValue(index));

        // Long term moving averages
        if (index > Period.THIRTY.getTimeFrame() - 1)     gmma.setValue(Period.THIRTY, ema30.getValue(index));
        if (index > Period.THIRTYFIVE.getTimeFrame() - 1) gmma.setValue(Period.THIRTYFIVE, ema35.getValue(index));
        if (index > Period.FORTY.getTimeFrame() - 1)      gmma.setValue(Period.FORTY, ema40.getValue(index));
        if (index > Period.FORTYFIVE.getTimeFrame() - 1)  gmma.setValue(Period.FORTYFIVE, ema45.getValue(index));
        if (index > Period.FIFTY.getTimeFrame() - 1)      gmma.setValue(Period.FIFTY, ema50.getValue(index));
        if (index > Period.SIXTY.getTimeFrame() - 1)      gmma.setValue(Period.SIXTY, ema60.getValue(index));

        return gmma;
    }
}
