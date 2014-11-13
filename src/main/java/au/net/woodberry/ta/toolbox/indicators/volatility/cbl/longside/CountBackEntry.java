package au.net.woodberry.ta.toolbox.indicators.volatility.cbl.longside;

import eu.verdelhan.ta4j.indicators.CachedIndicator;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;
import java.util.List;

public class CountBackEntry extends CachedIndicator<TADecimal> {

    private final List<TADecimal> cblValues;
    private final TimeSeries data;

    public CountBackEntry(TimeSeries data, List<TADecimal> cblValues) {
        if (data == null) {
            throw new IllegalArgumentException("Supplied input TimeServices is invalid: NULL");
        }
        if (cblValues == null) {
            throw new IllegalArgumentException("Supplied input List<TADecimal> is invalid: NULL");
        }
        if (data.getSize() != cblValues.size()) {
            throw new IllegalArgumentException("Supplied input is invalid: Size mismatch between TimeSeries (" + data.getSize()
                    + ") and List<TADecimal> (" + cblValues.size() + ")");
        }
        this.data = data;
        this.cblValues = cblValues;
    }

    @Override
    protected TADecimal calculate(int i) {
        if (i == 0) {
            return null;
        }
        final int previous = i-1;
        TADecimal prevCloseTickPrice = data.getTick(previous).getClosePrice();
        TADecimal cblValue = cblValues.get(i);
        TADecimal entry = null;
        if (cblValue != null && prevCloseTickPrice.isGreaterThan(cblValue)) {
            entry = data.getTick(i).getClosePrice();
        }
        return entry;
    }
}
