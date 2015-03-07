package au.net.woodberry.ta.toolbox.indicators.trend;

import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class RateOfReturnIndicator extends CachedIndicator<TADecimal> {

    private final Indicator<? extends TADecimal> priceIndicator;
    private final Indicator<? extends TADecimal> referenceIndicator;

    public RateOfReturnIndicator(Indicator<? extends TADecimal> priceIndicator) {
        this(priceIndicator, priceIndicator);
    }
    
    /**
     * Perform a rate of return calculation
     *
     * @param priceIndicator An indicator based on the price information
     * @param referenceIndicator Any indicator, ideally one which smooths the price information such as a Moving average, Average True Range, Linear regression, from which 
     *                           the calculation is performed.
     */
    public RateOfReturnIndicator(Indicator<? extends TADecimal> priceIndicator, Indicator<? extends TADecimal> referenceIndicator) {
        if (priceIndicator == null) {
            throw new IllegalArgumentException("Supplied Indicator (price) is invalid: NULL");
        }
        if (referenceIndicator == null) {
            throw new IllegalArgumentException("Supplied Indicator (reference) is invalid: NULL");
        }
        this.priceIndicator = priceIndicator;
        this.referenceIndicator = referenceIndicator;
    }

    @Override
    public TADecimal calculate(int index) {
        TADecimal current = referenceIndicator.getValue(index);
        TADecimal first = referenceIndicator.getValue(0);
        TADecimal price = priceIndicator.getValue(index);
        return current.minus(first).dividedBy(price).multipliedBy(TADecimal.HUNDRED);
    }
}
