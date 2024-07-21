package io.shift.statistic;

import java.math.BigDecimal;
import java.math.MathContext;

public class FloatStatistic extends Statistic<BigDecimal> {

    @Override
    protected BigDecimal parseValue(String line) {
        return new BigDecimal(line);
    }

    @Override
    protected BigDecimal add(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    @Override
    protected BigDecimal calculateAverageValue() {
        return allSum.divide(new BigDecimal(countOfTokens), new MathContext(5));
    }
}
