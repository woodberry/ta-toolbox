package au.net.woodberry.ta.toolbox.indicators.statistics;

import eu.verdelhan.ta4j.Indicator;
import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

public class LinearRegressionIndicator extends CachedIndicator<TADecimal> {

    private final Indicator<? extends TADecimal> indicator;
    private final int lastIndex;
    private TADecimal beta0;
    private TADecimal beta1;

    /**
     * A port of the Linear Regression code from:
     * http://introcs.cs.princeton.edu/java/97data/LinearRegression.java.html
     *
     * @param indicator An indicator containing the time series which is used for the regression calculation
     * @param lastIndex The last index value of the time series data
     */
    public LinearRegressionIndicator(Indicator<? extends TADecimal> indicator, int lastIndex) {
        this.indicator = indicator;
        this.lastIndex = lastIndex;
        calculateRegressionLine();
    }
    
    private void calculateRegressionLine() {
        TADecimal sumX = TADecimal.ZERO, sumY = TADecimal.ZERO;
        for (int i = 0; i <= lastIndex; i++) {
            TADecimal xI = TADecimal.valueOf(i+1);
            TADecimal yI = indicator.getValue(i);
            sumX = sumX.plus(xI);
            sumY = sumY.plus(yI);
        }

        TADecimal n = TADecimal.valueOf(lastIndex + 1);
        TADecimal xBar = sumX.dividedBy(n);
        TADecimal yBar = sumY.dividedBy(n);

        TADecimal xxBar = TADecimal.ZERO, xyBar = TADecimal.ZERO;
        for (int i = 0; i <= lastIndex; i++) {
            TADecimal xI = TADecimal.valueOf(i+1);
            TADecimal yI = indicator.getValue(i);
            xxBar = xxBar.plus(xI.minus(xBar).multipliedBy(xI.minus(xBar)));
            xyBar = xyBar.plus(xI.minus(xBar).multipliedBy(yI.minus(yBar)));
        }

        this.beta1 = xyBar.dividedBy(xxBar);
        this.beta0 = yBar.minus(beta1.multipliedBy(xBar));
    }
    
    @Override
    public TADecimal calculate(int index) {
        TADecimal x = TADecimal.valueOf(index + 1);
        return x.multipliedBy(beta1).plus(beta0);

    }
}
