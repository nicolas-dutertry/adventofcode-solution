package com.dutertry.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15_1 {
    private static final Pattern PATTERN = Pattern.compile("Sensor at x=([\\-0-9]+), y=([\\-0-9]+): closest beacon is at x=([\\-0-9]+), y=([\\-0-9]+)");

    private static record Sensor(Point position, Point beaconPosition, int distance) {}

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

            int y = 2000000;
            Set<Integer> beaconPoints = new TreeSet<>();
            for(Sensor sensor : sensors) {
                if(sensor.beaconPosition().y() == y) {
                    beaconPoints.add(sensor.beaconPosition().x());
                }
            }

            Set<Integer> unreachablePoints = new TreeSet<>();
            for(Sensor sensor : sensors) {
                int ydist = Math.abs(sensor.position.y() - y);
                int remaining = sensor.distance() - ydist;

                for(int i = 0; i <= remaining; i++) {
                    int x1 = sensor.position.x() + i;
                    int x2 = sensor.position.x() - i;
                    unreachablePoints.add(x1);
                    unreachablePoints.add(x2);
                }
            }
            unreachablePoints.removeAll(beaconPoints);

            System.out.println(unreachablePoints.size());

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
