package au.net.woodberry.ta.toolbox.indicators.directional;

import au.net.woodberry.ta.toolbox.enums.Sentiment;
import au.net.woodberry.ta.toolbox.object.ChartPattern;
import au.net.woodberry.ta.toolbox.object.NR7Breakout;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

import java.util.List;

public class ChartPatternIndicator extends CachedIndicator<ChartPattern> {
    
    // Constants, use defaults as per the recommendations
    private static final int BULLISH_SENTIMENT_THRESHOLD = 65;
    private static final int BEARISH_SENTIMENT_THRESHOLD = 35;
    
    private final List<NR7Breakout> data;
    private final TimeSeries timeSeries;
    
    public ChartPatternIndicator(TimeSeries timeSeries, List<NR7Breakout> data) {
        this.timeSeries = timeSeries;
        this.data = data;
    }
    
    @Override
    protected ChartPattern calculate(int index) {
        Tick tick = timeSeries.getTick(index);
        int bullish = 0, bearish = 0;
        for (NR7Breakout breakout : data) { // go through each breakout data, determine bullish / bearish for the specified day only
            if (breakout.getBreakout().getEndTime().isEqual(tick.getEndTime())) {
                if (breakout.getSentiment().equals(Sentiment.BULLISH)) {
                    bullish++;
                }
                if (breakout.getSentiment().equals(Sentiment.BEARISH)) {
                    bearish++;
                }
            }
        }
        TADecimal numerator = TADecimal.HUNDRED.multipliedBy(TADecimal.valueOf(bullish));
        TADecimal denominator = TADecimal.valueOf(bullish).plus(TADecimal.valueOf(bearish));
        if (!denominator.isEqual(TADecimal.ZERO)) {
            TADecimal ratio = numerator.dividedBy(denominator);
            Sentiment sentiment = ratio.isGreaterThan(TADecimal.valueOf(BULLISH_SENTIMENT_THRESHOLD)) ? Sentiment.BULLISH :
                                  ratio.isLessThan(TADecimal.valueOf(BEARISH_SENTIMENT_THRESHOLD)) ? Sentiment.BEARISH :
                                  null;
            return new ChartPattern(ratio, sentiment);
        }
        return null;
    }
}
