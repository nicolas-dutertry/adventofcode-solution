package com.dutertry.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18_2 {
    private static final Pattern PATTERN = Pattern.compile("([0-9]+),([0-9]+),([0-9]+)");
    private static final List<Integer> DIRECTIONS = List.of(-1, 1);
    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(18)) {
            Set<Point3D> cubes = new HashSet<>();
            String line;
            while((line = br.readLine()) != null) {
                Matcher matcher = PATTERN.matcher(line);
                if (matcher.find()) {
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    int z = Integer.parseInt(matcher.group(3));
                    Point3D cube = new Point3D(x, y, z);
                    cubes.add(cube);
                } else {
                    throw new RuntimeException("Invalid input: " + line);
                }
            }

            Point3D startAirCube = new Point3D(0, 0, 0);
            Set<Point3D> airCubes = new HashSet<>();
            airCubes.add(startAirCube);
            Set<Point3D> airCubesToScan = new HashSet<>();
            airCubesToScan.add(startAirCube);
            int faces = 0;
            while(!airCubesToScan.isEmpty()) {
                Set<Point3D> unknownAirCubes = new HashSet<>();
                for(Point3D airCubeToScan : airCubesToScan) {
                    faces += scanAirCubes(airCubeToScan, cubes, airCubes, unknownAirCubes);
                }
                airCubes.addAll(unknownAirCubes);
                airCubesToScan = unknownAirCubes;
            }
            System.out.println(faces);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static int scanAirCubes(Point3D airCube, Set<Point3D> cubes, Set<Point3D> airCubes, Set<Point3D> unknownAirCubes) {
        int faces = 0;
        List<Point3D> adjacents = List.of(
                new Point3D(airCube.x() - 1, airCube.y(), airCube.z()),
                new Point3D(airCube.x() + 1, airCube.y(), airCube.z()),
                new Point3D(airCube.x(), airCube.y() - 1, airCube.z()),
                new Point3D(airCube.x(), airCube.y() + 1, airCube.z()),
                new Point3D(airCube.x(), airCube.y(), airCube.z() - 1),
                new Point3D(airCube.x(), airCube.y(), airCube.z() + 1)
        );
        for(Point3D adjacent : adjacents) {
            if(adjacent.x() < -1 || adjacent.y() < -1 || adjacent.z() < -1 || adjacent.x() > 21 || adjacent.y() > 21 || adjacent.z() > 21) {
                continue;
            }
            if(!airCubes.contains(adjacent)) {
                if (cubes.contains(adjacent)) {
                    faces++;
                } else {
                    unknownAirCubes.add(adjacent);
                }
            }
        }
        return faces;
    }
}
