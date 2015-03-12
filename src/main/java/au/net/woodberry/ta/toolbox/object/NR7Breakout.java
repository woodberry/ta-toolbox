package au.net.woodberry.ta.toolbox.object;

import au.net.woodberry.ta.toolbox.enums.Sentiment;
import eu.verdelhan.ta4j.Tick;

public class NR7Breakout {
    
    private final Sentiment sentiment;
    private final Tick nr7;
    private final Tick breakout;
    private final int periods;
    
    public NR7Breakout(Tick nr7, Tick breakout, Sentiment sentiment, int periods) {
        this.nr7 = nr7;
        this.breakout = breakout;
        this.sentiment = sentiment;
        this.periods = periods;
    }
    
    public Sentiment getSentiment() {
        return sentiment;
    }

    public Tick getNr7() {
        return nr7;
    }

    public Tick getBreakout() {
        return breakout;
    }

    public int getPeriods() {
        return periods;
    }
    
    @Override
    public String toString() {
        return "NR7 tick: [Date: " + nr7.getEndTime() + ", Close: " + nr7.getClosePrice() + "] " +
               "Breakout Tick: [Date:" + breakout.getEndTime() + ", Close:" + breakout.getClosePrice() + "] " +
               "Sentiment: " + sentiment + " Periods: " + periods;
    }
}
