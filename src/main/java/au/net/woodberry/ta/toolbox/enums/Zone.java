package au.net.woodberry.ta.toolbox.enums;

public enum Zone {
    
    SELL(true),
    BUY_HOLD(false),
    PROFIT_TAKE_HOLD(false),
    PROFIT_TAKE(true);
    
    private final boolean mandatory;

    Zone(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public boolean isMandatory() {
        return mandatory;
    }
}
