package au.net.woodberry.ta.toolbox.enums;

import eu.verdelhan.ta4j.TADecimal;

public enum MarketCapitalization {

    // Definitions obtained from http://www.investopedia.com/articles/basics/03/031703.asp
    MEGA_CAP,
    LARGE_CAP,
    MID_CAP,
    SMALL_CAP,
    MICRO_CAP,
    NANO_CAP,
    UNKNOWN;
    
    private static final TADecimal MEGA_CAP_MINIMUM = TADecimal.valueOf(200000000000.0); // 200 Billion
    private static final TADecimal LARGE_CAP_MINIMUM = TADecimal.valueOf(10000000000.0); // 10 Billion
    private static final TADecimal MID_CAP_MINIMUM = TADecimal.valueOf(2000000000.0);    // 2 Billion
    private static final TADecimal SMALL_CAP_MINIMUM = TADecimal.valueOf(300000000.0);   // 300 Million
    private static final TADecimal MICRO_CAP_MINIMUM = TADecimal.valueOf(50000000.0);    // 50 million
    
    public static MarketCapitalization valueOf(TADecimal sharesOutstanding) {
        if (sharesOutstanding == null || sharesOutstanding.isEqual(TADecimal.ZERO)) {
            return UNKNOWN;
        } else if (sharesOutstanding.isGreaterThanOrEqual(MEGA_CAP_MINIMUM)) {
            return MEGA_CAP;
        } else if (sharesOutstanding.isGreaterThanOrEqual(LARGE_CAP_MINIMUM)) {
            return LARGE_CAP;
        } else if (sharesOutstanding.isGreaterThanOrEqual(MID_CAP_MINIMUM)) {
            return MID_CAP;
        } else if (sharesOutstanding.isGreaterThanOrEqual(SMALL_CAP_MINIMUM)) {
            return SMALL_CAP;
        } else if (sharesOutstanding.isGreaterThanOrEqual(MICRO_CAP_MINIMUM)) {
            return MICRO_CAP;
        } else {
            return NANO_CAP;
        }
    }
}
