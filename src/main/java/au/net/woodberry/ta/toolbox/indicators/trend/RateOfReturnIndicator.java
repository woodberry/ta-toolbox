package au.net.woodberry.ta.toolbox.indicators.trend;

import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class RateOfReturnIndicator extends CachedIndicator<TADecimal> {

    private final Indicator<? extends TADecimal> priceIndicator;
    private final Indicator<? extends TADecimal> referenceIndicator;
    private final int referencePoint;
    
    public RateOfReturnIndicator(Indicator<? extends TADecimal> priceIndicator) {
        this(priceIndicator, priceIndicator, 0);
    }
    
    public RateOfReturnIndicator(Indicator<? extends TADecimal> priceIndicator, Indicator<? extends TADecimal> referenceIndicator) {
        this(priceIndicator, referenceIndicator, 0);
    }

    /**
     * Perform a rate of return calculation
     *
     * @param priceIndicator An indicator based on the price information
     * @param referenceIndicator Any indicator, ideally one which smooths the price information such as a Moving average, Average True Range, Linear regression
     * @param referencePoint A reference point within the time series, default is the start index
     */
    public RateOfReturnIndicator(Indicator<? extends TADecimal> priceIndicator, Indicator<? extends TADecimal> referenceIndicator, int referencePoint) {
        if (priceIndicator == null) {
            throw new IllegalArgumentException("Supplied Indicator (price) is invalid: NULL");
        }
        if (referenceIndicator == null) {
            throw new IllegalArgumentException("Supplied Indicator (reference) is invalid: NULL");
        }
        this.priceIndicator = priceIndicator;
        this.referenceIndicator = referenceIndicator;
        this.referencePoint = referencePoint;
    }
    
    private TADecimal multiplier(int index) {
        return (priceIndicator.getValue(index).minus(TADecimal.valueOf(referencePoint))).dividedBy(priceIndicator.getValue(index)).abs();
    }
    
    @Override
    public TADecimal calculate(int index) {
        TADecimal currentValue = referenceIndicator.getValue(index);
        TADecimal referencePoint = referenceIndicator.getValue(this.referencePoint);
        TADecimal value = priceIndicator.getValue(index);
        TADecimal multiplier = multiplier(index);
        return (currentValue.minus(referencePoint)).dividedBy(value).multipliedBy(multiplier).multipliedBy(TADecimal.HUNDRED);
    }
}
