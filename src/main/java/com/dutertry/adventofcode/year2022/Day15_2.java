package com.dutertry.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15_2 {
    private static final Pattern PATTERN = Pattern.compile("Sensor at x=([\\-0-9]+), y=([\\-0-9]+): closest beacon is at x=([\\-0-9]+), y=([\\-0-9]+)");

    private static record Sensor(Point position, Point beaconPosition, int distance) {}

    private static record Interval(int min, int max) {}

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(15)) {
            Set<Sensor> sensors = new HashSet<>();
            String line;
            while((line = br.readLine()) != null) {
                Matcher matcher = PATTERN.matcher(line);
                if (matcher.find()) {
                    int xs = Integer.parseInt(matcher.group(1));
                    int ys = Integer.parseInt(matcher.group(2));
                    int xb = Integer.parseInt(matcher.group(3));
                    int yb = Integer.parseInt(matcher.group(4));

                    int distance = Math.abs(xs - xb) + Math.abs(ys - yb);

                    sensors.add(new Sensor(new Point(xs, ys), new Point(xb, yb), distance));
                } else {
                    throw new RuntimeException("Invalid input: " + line);
                }
            }

            Interval lineInterval = new Interval(0, 4_000_000);
            for (int y = 0; y < 4_000_000; y++) {
                Set<Interval> intervals = new HashSet<>();
                intervals.add(lineInterval);

                for (Sensor sensor : sensors) {
                    Set<Interval> newIntervals = new HashSet<>();
                    int ydist = Math.abs(sensor.position.y() - y);
                    int remaining = sensor.distance() - ydist;
                    if (remaining >= 0) {
                        Interval forbiddenInterval = new Interval(sensor.position.x() - remaining, sensor.position.x() + remaining);
                        for (Interval interval : intervals) {
                            newIntervals.addAll(remove(interval, forbiddenInterval));
                        }
                        intervals = newIntervals;
                        if(intervals.isEmpty()) {
                            break;
                        }
                    }
                }

                if(!intervals.isEmpty()) {
                    long x = intervals.iterator().next().min();
                    long frequency = x * 4000000L + y;
                    System.out.println(frequency);
                    break;
                }
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<Interval> remove(Interval i1, Interval i2) {
        if(i1.min() > i2.max() || i1.max() < i2.min()) {
            return Collections.singleton(i1);
        } else if(i1.min() < i2.min() && i1.max() > i2.max()) {
            return Set.of(new Interval(i1.min(), i2.min()-1), new Interval(i2.max()+1, i1.max()));
        } else if(i1.min() < i2.min()) {
            return Set.of(new Interval(i1.min(), i2.min()-1));
        } else if(i1.max() > i2.max()) {
            return Set.of(new Interval(i2.max()+1, i1.max()));
        } else {
            return Collections.emptySet();
        }
    }

}
