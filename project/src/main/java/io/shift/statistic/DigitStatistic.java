package io.shift.statistic;

import java.math.BigInteger;

public class DigitStatistic extends Statistic<BigInteger> {

    public DigitStatistic() {
        allSum = BigInteger.ZERO;
    }

    @Override
    protected BigInteger parseValue(String line) {
        return new BigInteger(line);
    }

    @Override
    protected BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    protected BigInteger calculateAverageValue() {
        return allSum.divide(countOfTokens);
    }
}
