package au.net.woodberry.ta.toolbox.indicators.breakout;

import au.net.woodberry.ta.toolbox.enums.Sentiment;
import au.net.woodberry.ta.toolbox.object.NR7Breakout;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class NR7BreakoutIndicator extends CachedIndicator<NR7Breakout> {

    private final TimeSeries timeSeries;
    private final Integer nr7Index;
    private final int firstIndex;

    /**
     * Bulkowski's NR7 (Narrow Range) - based on 7 periods of data
     *
     * The NR7 is based on the high-low price range that is the smallest of the prior six days (seven days total). 
     * When an NR7 occurs, it means that today's price is the narrowest of the seven days.
     *
     * http://thepatternsite.com/nr7.html
     *
     * @param timeSeries A TimeSeries, containing both a max and min price
     * @param nr7Index The index position where the nr7 signal exists
     */
    public NR7BreakoutIndicator(TimeSeries timeSeries, int nr7Index) {
        if (timeSeries == null) {
            throw new IllegalArgumentException("Supplied TimeSeries is invalid: NULL");
        }
        if (timeSeries.getSize() <= 2) {
            throw new IllegalArgumentException("Supplied TimeSeries is invalid: Cannot be less than size of 2");
        }
        if (nr7Index == timeSeries.getEnd()) {
            throw new IllegalArgumentException("Supplied Integer index is invalid: Cannot be the last index within the TimeSeries");
        }
        if (nr7Index > timeSeries.getEnd()) {
            throw new IllegalArgumentException("Supplied Integer index is invalid: Not within the TimeSeries");
        }
        this.nr7Index = nr7Index;
        this.firstIndex = nr7Index + 1; // First possible breakout occurs after the nr7 tick
        this.timeSeries = timeSeries;
    }

    @Override
    protected NR7Breakout calculate(int index) {
        if (index <= nr7Index) {
            return null;
        }
        for (int i = firstIndex; i <= index; i++) {
            Tick tick = timeSeries.getTick(i);
            Tick nr7Tick = timeSeries.getTick(nr7Index);
            if (tick.getClosePrice().isGreaterThan(nr7Tick.getMaxPrice())) { // Return the first bullish signal found
                return new NR7Breakout(nr7Tick, tick, Sentiment.BULLISH);
            }
            if (tick.getClosePrice().isLessThan(nr7Tick.getMinPrice())) { // Return the first bearish signal found
                return new NR7Breakout(nr7Tick, tick, Sentiment.BEARISH);
            }
        }
        return null;
    }

}
