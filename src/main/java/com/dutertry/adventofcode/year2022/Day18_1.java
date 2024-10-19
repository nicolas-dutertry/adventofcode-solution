package com.dutertry.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18_1 {
    private static final Pattern PATTERN = Pattern.compile("([0-9]+),([0-9]+),([0-9]+)");

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(18)) {
            List<Point3D> cubes = new LinkedList<>();
            Map<Point3D, Integer> freeFaces = new HashMap<>();
            String line;
            while((line = br.readLine()) != null) {
                Matcher matcher = PATTERN.matcher(line);
                if (matcher.find()) {
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    int z = Integer.parseInt(matcher.group(3));
                    Point3D cube = new Point3D(x, y, z);
                    cubes.add(cube);
                    freeFaces.put(cube, 6);
                } else {
                    throw new RuntimeException("Invalid input: " + line);
                }
            }

            for(int i = 0; i< cubes.size(); i++) {
                Point3D cube1 = cubes.get(i);
                for(int j = i+1; j < cubes.size(); j++) {
                    Point3D cube2 = cubes.get(j);
                    if(haveCommonFace(cube1, cube2)) {
                        freeFaces.put(cube1, freeFaces.get(cube1) - 1);
                        freeFaces.put(cube2, freeFaces.get(cube2) - 1);
                    }
                }
            }
            System.out.println(freeFaces.values().stream().mapToInt(i -> i).sum());

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean haveCommonFace(Point3D p1, Point3D p2) {
        return p1.x() == p2.x() && p1.y() == p2.y() && Math.abs(p1.z() - p2.z()) == 1
                || p1.x() == p2.x() && p1.z() == p2.z() && Math.abs(p1.y() - p2.y()) == 1
                || p1.y() == p2.y() && p1.z() == p2.z() && Math.abs(p1.x() - p2.x()) == 1;
    }
}
