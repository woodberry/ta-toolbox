package au.net.woodberry.ta.toolbox.indicators.directional.pivotpoints;

import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public abstract class PivotPoint extends CachedIndicator<TADecimal> {

    private TADecimal value; // <- this is the pivot point value
    private TADecimal resistanceOne;
    private TADecimal resistanceTwo;
    private TADecimal supportOne;
    private TADecimal supportTwo;

    public TADecimal getValue() {
        return value;
    }

    public void setValue(TADecimal value) {
        this.value = value;
    }

    public TADecimal getResistanceOne() {
        return resistanceOne;
    }

    public void setResistanceOne(TADecimal resistanceOne) {
        this.resistanceOne = resistanceOne;
    }

    public TADecimal getResistanceTwo() {
        return resistanceTwo;
    }

    public void setResistanceTwo(TADecimal resistanceTwo) {
        this.resistanceTwo = resistanceTwo;
    }

    public TADecimal getSupportOne() {
        return supportOne;
    }

    public TADecimal getSupportTwo() {
        return supportTwo;
    }

    public void setSupportOne(TADecimal supportOne) {
        this.supportOne = supportOne;
    }

    public void setSupportTwo(TADecimal supportTwo) {
        this.supportTwo = supportTwo;
    }
}
