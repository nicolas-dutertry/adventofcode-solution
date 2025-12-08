package com.dutertry.adventofcode;

import java.util.Objects;
import java.util.Set;

public class Pair<T> {
    private final Set<T> values;

    public Pair(T value1, T value2) {
        this.values = Set.of(value1, value2);
    }

    public T getFirst() {
        return values.iterator().next();
    }

    public T getSecond() {
        return values.stream().skip(1).findFirst().get();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?> pair = (Pair<?>) o;
        return Objects.equals(values, pair.values);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(values);
    }

    public String toString() {
        return "Pair{" + values + "}";
    }
}
