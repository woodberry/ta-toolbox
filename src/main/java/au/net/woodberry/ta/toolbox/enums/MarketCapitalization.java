package au.net.woodberry.ta.toolbox.enums;

import eu.verdelhan.ta4j.TADecimal;

public enum MarketCapitalization {

    // Definitions obtained from http://www.investopedia.com/terms/m/marketcapitalization.asp
    LARGE_CAP,
    MID_CAP,
    SMALL_CAP,
    UNKNOWN;
    
    private static final TADecimal LARGE_CAP_MINIMUM = TADecimal.valueOf(10000000000.0);
    private static final TADecimal MID_CAP_MINIMUM = TADecimal.valueOf(2000000000.0);
    
    public static MarketCapitalization getCapitalization(TADecimal value) {
        if (value == null || value.isEqual(TADecimal.ZERO)) {
            return UNKNOWN;
        } else if (value.isGreaterThanOrEqual(LARGE_CAP_MINIMUM)) {
            return LARGE_CAP;
        } else if (value.isGreaterThanOrEqual(MID_CAP_MINIMUM)) {
            return MID_CAP;
        } else {
            return SMALL_CAP;
        }
    }
}
