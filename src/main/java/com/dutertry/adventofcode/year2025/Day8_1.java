package com.dutertry.adventofcode.year2025;

import com.dutertry.adventofcode.Pair;
import com.dutertry.adventofcode.Point3D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Day8_1 {
    private record Connection(Pair<Point3D> pair, long distance) {}

    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(8);
            List<Point3D> points = new LinkedList<>();
            for(String line : lines) {
                String[] parts = line.split(",");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                int z = Integer.parseInt(parts[2]);
                Point3D p = new Point3D(x, y, z);
                points.add(p);
            }

            List<Connection> connections = getSortedConnections(points);

            Set<Set<Point3D>> circuits = new HashSet<>();
            for(Point3D point : points) {
                Set<Point3D> circuit = new HashSet<>();
                circuit.add(point);
                circuits.add(circuit);
            }

            for(int i = 0; i < 1000; i++) {
                Connection connection = connections.get(i);
                Pair<Point3D> pair = connection.pair;

                Set<Point3D> circuit1 = null;
                Set<Point3D> circuit2 = null;
                for(Set<Point3D> circuit : circuits) {
                    if(circuit1 == null && circuit.contains(pair.getFirst())) {
                        circuit1 = circuit;
                    }
                    if(circuit2 == null && circuit.contains(pair.getSecond())) {
                        circuit2 = circuit;
                    }
                    if(circuit1 != null && circuit2 != null) {
                        break;
                    }
                }
                if(circuit1 == circuit2) {
                    continue;
                }

                circuits.remove(circuit1);
                circuits.remove(circuit2);

                circuit1.addAll(circuit2);
                circuits.add(circuit1);
            }

            List<Set<Point3D>> sortedCircuits = new LinkedList<>(circuits);
            sortedCircuits.sort((c1, c2) -> Integer.compare(c2.size(), c1.size()));

            long size1 = sortedCircuits.get(0).size();
            long size2 = sortedCircuits.get(1).size();
            long size3 = sortedCircuits.get(2).size();
            long total = size1 * size2 * size3;
            System.out.println(total);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Connection> getSortedConnections(List<Point3D> points) {
        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Point3D p1 = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                Point3D p2 = points.get(j);
                Pair<Point3D> pair = new Pair<>(p1, p2);
                long distance = getDistance(pair);
                connections.add(new Connection(pair, distance));
            }
        }
        connections.sort((c1, c2) -> Long.compare(c1.distance, c2.distance));

        return connections;
    }

    private static long getDistance(Pair<Point3D> pair) {
        Point3D p1 = pair.getFirst();
        Point3D p2 = pair.getSecond();
        long dx = p1.x() - p2.x();
        long dy = p1.y() - p2.y();
        long dz = p1.z() - p2.z();
        return dx * dx + dy * dy + dz * dz;
    }
}
