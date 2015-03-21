package au.net.woodberry.ta.toolbox.indicators.breakout;

import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class NR7Indicator extends CachedIndicator<Tick> {
    
    private static final int NR7_SIZE = 7;
    private static final int NR7_LOOK_BACK_INDEX = NR7_SIZE - 1;
    
    private final TimeSeries timeSeries;

    /**
     * Bulkowski's NR7 (Narrow Range) - based on 7 periods of data
     * 
     * The NR7 is based on the high-low price range that is the smallest of the prior six days (seven days total). 
     * When an NR7 occurs, it means that today's price is the narrowest of the seven days.
     * 
     * http://thepatternsite.com/nr7.html
     *
     * @param timeSeries A TimeSeries, containing both a max and min price
     */
    public NR7Indicator(TimeSeries timeSeries) {
        if (timeSeries == null) {
            throw new IllegalArgumentException("Supplied TimeSeries is invalid: NULL");
        }
        if (timeSeries.getSize() < NR7_SIZE) {
            throw new IllegalArgumentException("Supplied TimeSeries is invalid: Size Cannot be less than " + NR7_SIZE);
        }
        this.timeSeries = timeSeries;
    }
    
    @Override
    protected Tick calculate(int index) {
        if (index < NR7_LOOK_BACK_INDEX) {
            return null;
        }
        TADecimal currentRange = timeSeries.getTick(index).getMaxPrice().minus(timeSeries.getTick(index).getMinPrice());
        TADecimal lowestRange = null;
        for (int i = (index - NR7_LOOK_BACK_INDEX); i < index; i++) {
            TADecimal range = timeSeries.getTick(i).getMaxPrice().minus(timeSeries.getTick(i).getMinPrice());
            if (lowestRange == null || range.isLessThan(lowestRange)) {
                lowestRange = range;
            }
        }
        return currentRange.isLessThan(lowestRange) ? timeSeries.getTick(index) : null;
    }
}
