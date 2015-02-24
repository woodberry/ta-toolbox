package au.net.woodberry.ta.toolbox.object;

import au.net.woodberry.ta.toolbox.enums.Zone;
import eu.verdelhan.ta4j.TADecimal;

public class Range {

    private final TADecimal upperDeviation;
    private final TADecimal centralCord;
    private final TADecimal lowerDeviation;
    private final Zone zone;

    public Range(TADecimal upperDeviation, TADecimal centralCord, TADecimal lowerDeviation, Zone zone) {
        this.upperDeviation = upperDeviation;
        this.centralCord = centralCord;
        this.lowerDeviation = lowerDeviation;
        this.zone = zone;
    }

    public TADecimal getUpperDeviation() {
        return upperDeviation;
    }

    public TADecimal getCentralCord() {
        return centralCord;
    }

    public TADecimal getLowerDeviation() {
        return lowerDeviation;
    }

    public Zone getZone() {
        return zone;
    }
}
