package au.net.woodberry.ta.toolbox.object;

import au.net.woodberry.ta.toolbox.enums.Sentiment;
import eu.verdelhan.ta4j.Tick;

public class NR7Breakout {
    
    private final Sentiment sentiment;
    private final Tick nr7;
    private final Tick breakout;

    /**
     * *
     * @param nr7 The tick at which the nr7 pattern has formed and the pattern ends
     * @param breakout The tick at which a breakout is signalled
     * @param sentiment The sentiment of the breakout
     */
    public NR7Breakout(Tick nr7, Tick breakout, Sentiment sentiment) {
        this.nr7 = nr7;
        this.breakout = breakout;
        this.sentiment = sentiment;
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

    @Override
    public String toString() {
        return "NR7 tick: [Date: " + nr7.getEndTime() + "] " +
               "Breakout Tick: [Date:" + breakout.getEndTime() + "] " +
               "Sentiment: " + sentiment;
    }
}
