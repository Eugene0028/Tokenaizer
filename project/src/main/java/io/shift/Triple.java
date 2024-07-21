package io.shift;


public class Triple<T, U, W>
{
    private T first;
    private U second;
    private W third;

    public Triple(T first, U second, W third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    public W getThird() {
        return third;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(U second) {
        this.second = second;
    }

    public void setThird(W third) {
        this.third = third;
    }
}
