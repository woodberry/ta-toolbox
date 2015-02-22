package au.net.woodberry.ta.toolbox.indicators.trackers;

import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.CachedIndicator;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.indicators.simple.ConstantIndicator;
import eu.verdelhan.ta4j.indicators.trackers.WMAIndicator;

public class HMAIndicator extends CachedIndicator<TADecimal> {

    private final WMAIndicator wma1;
    private final WMAIndicator wma2;

    /**
     * The Hull Moving Average 
     * (Author: Alan Hull)
     *  
     * Formula: 
     * ﻿Hull = WMA( 2*WMA(C,int(Period/2))- WMA(C,Period),int(sqrt(Period)));
     *  
     * Reference
     * http://www.traderslog.com/hullmovingaverage/
     */
    public HMAIndicator(TimeSeries timeSeries, int timeFrame) {
        if (timeSeries == null) {
            throw new IllegalArgumentException("Supplied TimeSeries is invalid: NULL");
        }
        if (timeFrame < 2) { // Require at least 2 periods, otherwise WMA becomes unhappy.
            throw new IllegalArgumentException("Supplied timeFrame is invalid: Must not be less than 2");
        }
        this.wma1 = new WMAIndicator(new ClosePriceIndicator(timeSeries), timeFrame);
        this.wma2 = new WMAIndicator(new ClosePriceIndicator(timeSeries), timeFrame/2);
    }

    @Override
    protected TADecimal calculate(int index) {
        ConstantIndicator<TADecimal> constantIndicator = new ConstantIndicator<>(TADecimal.TWO.multipliedBy(wma2.getValue(index)).minus(wma1.getValue(index)));
        WMAIndicator wmaIndicator = new WMAIndicator(constantIndicator, Double.valueOf(Math.sqrt(index)).intValue());
        return wmaIndicator.getValue(index);
    }
}
