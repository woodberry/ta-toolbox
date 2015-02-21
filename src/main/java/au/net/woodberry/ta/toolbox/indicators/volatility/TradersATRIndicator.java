package au.net.woodberry.ta.toolbox.indicators.volatility;

import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class TradersATRIndicator extends CachedIndicator<TADecimal> {

    private final Indicator<? extends TADecimal> referenceIndicator;
    private final AverageTrueRangeIndicator atrIndicator;
    private final TADecimal displacementFactor;

    /**
     * The Trader's ATR indicator, as described in Guppy Trading, Essential Methods for Modern Trading - Daryl Guppy
     *
     * @param atrIndicator An Average True Range indicator
     * @param referenceIndicator A value-based indicator used for reference e.g. Closing Price
     * @param displacementFactor A displacement factor e.g. 1xATR, 2xATR etc.. always a positive value
     */
    public TradersATRIndicator(AverageTrueRangeIndicator atrIndicator, Indicator<? extends TADecimal> referenceIndicator, int displacementFactor) {
        if (atrIndicator == null) {
            throw new IllegalArgumentException("Supplied AverageTrueRangeIndicator is invalid: NULL");
        }
        if (referenceIndicator == null) {
            throw new IllegalArgumentException("Supplied Indicator is invalid: NULL");
        }
        if (displacementFactor <= 0) {
            throw new IllegalArgumentException("Supplied Displacement Factor cannot be zero or negative");
        }
        this.atrIndicator = atrIndicator;
        this.referenceIndicator = referenceIndicator;
        this.displacementFactor = TADecimal.valueOf(displacementFactor);
    }
    
    @Override
    protected TADecimal calculate(int index) {
        TADecimal atr = atrIndicator.getValue(index);
        if (atr == null) {
            return null;
        }
        TADecimal referenceValue = referenceIndicator.getValue(index);
        TADecimal currentAtr = referenceValue.minus(displacementFactor.multipliedBy(atr));
        if (currentAtr.isLessThan(TADecimal.ZERO)) {
            return TADecimal.ZERO;
        }
        TADecimal prevAtr = findHighest(index); // Can be null if it's on or less than the look back index
        if (prevAtr == null || currentAtr.isGreaterThan(prevAtr)) {
            return currentAtr;
        }
        return prevAtr;
    }

    private TADecimal findHighest(int index) {
        TADecimal highest = getValue(index - 1);
        for (int i = index - 1; i > 0; i--) {
            TADecimal current = getValue(i);
            if (current != null && current.isGreaterThan(highest)) {
                highest = getValue(i);
            }
        }
        return highest;
    }
}
