package io.shift.statistic;

public class StringStatistic extends Statistic<String> {
    private Integer minLength = null;
    private Integer maxLength = null;

    @Override
    protected String parseValue(String line) {
        return line;
    }

    @Override
    protected String add(String a, String b) {
        return null; // This method is not applicable for strings
    }

    @Override
    protected String calculateAverageValue() {
        return null; // This method is not applicable for strings
    }

    @Override
    public void process(String line) {
        increment();
        int length = line.length();
        if (minLength == null || length < minLength) {
            minLength = length;
            minValue = line;
        }
        if (maxLength == null || length > maxLength) {
            maxLength = length;
            maxValue = line;
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(this.getClass().getSimpleName() + "{");
        result.append("countOfTokens=").append(countOfTokens);
        if (minLength != null) {
            result.append(", minLength=").append(minLength);
            result.append(", maxLength=").append(maxLength);
        }
        result.append('}');
        return result.toString();
    }
}
