package au.net.woodberry.ta.toolbox.object;

import eu.verdelhan.ta4j.TADecimal;

public class PivotPoint {

    private TADecimal pivotPoint; // <- this is the pivot point pivotPoint
    private TADecimal resistanceOne;
    private TADecimal resistanceTwo;
    private TADecimal supportOne;
    private TADecimal supportTwo;

    public TADecimal getPivotPoint() {
        return pivotPoint;
    }

    public void setPivotPoint(TADecimal pivotPoint) {
        this.pivotPoint = pivotPoint;
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
