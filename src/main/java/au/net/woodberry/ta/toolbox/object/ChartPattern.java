package au.net.woodberry.ta.toolbox.object;

import au.net.woodberry.ta.toolbox.enums.Sentiment;
import eu.verdelhan.ta4j.TADecimal;

public class ChartPattern {

    private final TADecimal ratio;
    private final Sentiment sentiment;

    public ChartPattern(TADecimal ratio, Sentiment sentiment) {
        this.ratio = ratio;
        this.sentiment = sentiment;
    }

    public TADecimal getRatio() {
        return ratio;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }
}
