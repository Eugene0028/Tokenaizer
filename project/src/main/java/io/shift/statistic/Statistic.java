package io.shift.statistic;

import java.math.BigInteger;

public abstract class Statistic<T extends Comparable<T>> {
    protected BigInteger countOfTokens = BigInteger.ZERO;
    protected T minValue;
    protected T maxValue;
    protected T allSum;

    protected void increment() {
        countOfTokens = countOfTokens.add(BigInteger.ONE);
    }

    protected abstract T parseValue(String line);

    protected abstract T add(T a, T b);

    protected abstract T calculateAverageValue();

    public void smallProcess(){
        increment();
    }

    public void process(String line) {

        increment();

        T current = parseValue(line);

        if (minValue == null || minValue.compareTo(current) > 0) {
            minValue = current;
        }

        if (maxValue == null || maxValue.compareTo(current) < 0) {
            maxValue = current;
        }

        if (allSum == null) {
            allSum = parseValue("0");
        }

        allSum = add(allSum, current);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(this.getClass().getSimpleName() + "{");
        result.append("countOfTokens=").append(countOfTokens);
        if (minValue != null && maxValue != null && allSum != null) {
            result.append(", minValue=").append(minValue);
            result.append(", maxValue=").append(maxValue);
            result.append(", allSum=").append(allSum);
            result.append(", averageValue=").append(calculateAverageValue());
        }

        result.append('}');
        return result.toString();
    }
}
