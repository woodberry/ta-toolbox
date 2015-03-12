package au.net.woodberry.ta.toolbox.indicators.breakout;

import au.net.woodberry.ta.toolbox.enums.Sentiment;
import au.net.woodberry.ta.toolbox.object.NR7Breakout;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class NR7BreakoutIndicator extends CachedIndicator<NR7Breakout> {

    private final Tick nr7;
    private final TimeSeries timeSeries;
    private final Integer nr7Index;
    private final int firstIndex;

    public NR7BreakoutIndicator(TimeSeries timeSeries, Tick nr7) {
        if (timeSeries == null) {
            throw new IllegalArgumentException("Supplied TimeSeries is invalid: NULL");
        }
        if (nr7 == null) {
            throw new IllegalArgumentException("Supplied Tick is invalid: NULL");
        }
        this.nr7Index = findTickPosition(timeSeries, nr7);
        if (nr7Index == null) {
            throw new IllegalArgumentException("Supplied Tick is invalid: Tick not found within the TimeSeries");
        }
        if (nr7Index == timeSeries.getEnd()) {
            throw new IllegalArgumentException("Supplied Tick is invalid: Cannot be the last tick within the TimeSeries");
        }
        this.nr7 = nr7;
        this.firstIndex = nr7Index + 1; // First possible breakout occurs after the nr7 tick
        this.timeSeries = timeSeries;
    }

    private static Integer findTickPosition(TimeSeries timeSeries, Tick tick) {
        for (int i = timeSeries.getBegin(); i <= timeSeries.getEnd(); i++) {
            if (timeSeries.getTick(i).getEndTime().isEqual(tick.getEndTime())) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected NR7Breakout calculate(int index) {
        if (index <= nr7Index) {
            return null;
        }
        for (int i = firstIndex; i <= index; i++) {
            int periods = i - nr7Index;
            Tick tick = timeSeries.getTick(i);
            if (tick.getClosePrice().isGreaterThan(nr7.getMaxPrice())) { // Return the first bullish signal found
                return new NR7Breakout(nr7, tick, Sentiment.BULLISH, periods);
            }
            if (tick.getClosePrice().isLessThan(nr7.getMinPrice())) { // Return the first bearish signal found
                return new NR7Breakout(nr7, tick, Sentiment.BEARISH, periods);
            }
        }
        return null;
    }
}
