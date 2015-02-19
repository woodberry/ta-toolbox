package au.net.woodberry.ta.toolbox.indicators.volume;

import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class MarketCapitalizationIndicator extends CachedIndicator<TADecimal> {

    private final TimeSeries series;

    public MarketCapitalizationIndicator(TimeSeries series) {
        this.series = series;
    }
    
    @Override
    protected TADecimal calculate(int index) {
        Tick tick = series.getTick(index);
        return tick.getClosePrice().multipliedBy(tick.getVolume());
    }
}
