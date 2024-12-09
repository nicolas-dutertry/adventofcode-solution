package com.dutertry.adventofcode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public record Interval(long min, long max) {
    public Interval {
        if(min > max) {
            throw new IllegalArgumentException("min must be less than or equal to max");
        }
    }

    public List<Interval> remove(Interval i2) {
        if(min > i2.max() || max < i2.min()) {
            return Collections.singletonList(this);
        } else if(min < i2.min() && max > i2.max()) {
            return List.of(new Interval(min(), i2.min()-1), new Interval(i2.max()+1, max()));
        } else if(min() < i2.min()) {
            return List.of(new Interval(min(), i2.min()-1));
        } else if(max() > i2.max()) {
            return List.of(new Interval(i2.max()+1, max()));
        } else {
            return Collections.emptyList();
        }
    }

    public List<Interval> removeAll(Collection<Interval> intervals) {
        List<Interval> mergedIntervals = mergeAll(intervals);
        List<Interval> result = new ArrayList<>();
        Interval current = this;
        for(Interval interval : mergedIntervals) {
            List<Interval> removed = current.remove(interval);
            if(removed.size() > 1) {
                result.addAll(removed.subList(0, removed.size()-1));
                current = removed.get(removed.size()-1);
            } else {
                current = removed.get(0);
            }
        }
        result.add(current);
        return result;
    }

    public Set<Interval> merge(Interval i2) {
        if(min > i2.max() || max < i2.min()) {
            return Set.of(this, i2);
        } else {
            return Set.of(new Interval(Math.min(min(), i2.min()), Math.max(max(), i2.max())));
        }
    }

    public static List<Interval> mergeAll(Collection<Interval> intervals) {
        List<Interval> sortedIntervals = new ArrayList<>(intervals);
        sortedIntervals.sort(Comparator.comparingLong(Interval::min));
        List<Interval> mergedIntervals = new ArrayList<>();
        for(int i = 0; i < sortedIntervals.size(); i++) {
            Interval current = sortedIntervals.get(i);
            for(int j = i + 1; j < sortedIntervals.size(); j++) {
                Interval next = sortedIntervals.get(j);
                if(current.max() >= next.min()) {
                    current = new Interval(current.min(), Math.max(current.max(), next.max()));
                    i = j;
                } else {
                    mergedIntervals.add(current);
                    current = null;
                    break;
                }
            }
            if(current != null) {
                mergedIntervals.add(current);
            }
        }
        return mergedIntervals;
    }

    public static void main(String[] args) {
        Interval i1 = new Interval(1, 50);
        Interval i2 = new Interval(3, 7);
        Interval i3 = new Interval(9, 10);
        Interval i4 = new Interval(11, 12);
        List<Interval> intervals = List.of(i2, i3, i4);
        System.out.println(i1.removeAll(intervals));
    }
}
