package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Day21_2 {
    private static final long MAX_STEPS = 26501365;
    private static int SIZE = 0;
    
    private static class Point {
        public final int x;
        public final int y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Point other = (Point) obj;
            return x == other.x && y == other.y;
        }
        
        public Set<Point> getNeighbors() {
            Set<Point> neighbors = new HashSet<>();
            neighbors.add(new Point(x-1, y));
            neighbors.add(new Point(x+1, y));
            neighbors.add(new Point(x, y-1));
            neighbors.add(new Point(x, y+1));
            return neighbors;
        }
    }
    
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(21);
            SIZE = lines.size();
            
            Point startPoint = null;
            for (int y = 0; y < lines.size(); y++) {
                String line = lines.get(y);
                for(int x = 0; x < line.length(); x++) {
                    char c = line.charAt(x);
                    if(c == 'S') {
                        startPoint = new Point(x, y);
                    }
                }
            }
            
            Set<Point> tocheck = new HashSet<>();
            Set<Point> evenPoints = new HashSet<>();
            Set<Point> oddPoints = new HashSet<>();
            tocheck.add(startPoint);
            evenPoints.add(startPoint);
            
            int metaStepCount = 2;
            int stepCount = SIZE*metaStepCount+SIZE/2;
            for(int step = 1; step <= stepCount; step++) {
                Set<Point> nextcheck = new HashSet<>();
                for (Point point : tocheck) {
                    Set<Point> neighbors = point.getNeighbors();
                    for (Point neighbor : neighbors) {
                        char c = getChar(neighbor, lines);
                        if(c == '#') {
                            continue;
                        }
                        if(step % 2 == 1) {
                            if(oddPoints.contains(neighbor)) {
                                continue;
                            } else {
                                oddPoints.add(neighbor);
                            }
                        } else {
                            if(evenPoints.contains(neighbor)) {
                                continue;
                            } else {
                                evenPoints.add(neighbor);
                            }
                        }
                        nextcheck.add(neighbor);
                    }
                }
                tocheck = nextcheck;
            }
            
            Set<Point> points = (stepCount % 2 == 0 ? evenPoints : oddPoints);
            Map<Point, Integer> countMap = new HashMap<>();
            for(int metay = -metaStepCount; metay <= metaStepCount; metay++) {
                for (int y = 0; y < SIZE; y++) {
                    int realy = y + metay*SIZE;
                    for(int metax = -metaStepCount; metax <= metaStepCount; metax++) {
                        int count = 0;
                        for(int x = 0; x < SIZE; x++) {
                            int realx = x + metax*SIZE;
                            if(points.contains(new Point(realx, realy))) {
                                count++;
                            }
                        }
                        Point metaPoint = new Point(metax, metay);
                        Integer c = countMap.get(metaPoint);
                        if(c == null) {
                            c = 0;
                        }
                        c += count;
                        countMap.put(metaPoint, c);
                    }
                }
            }
            
            long maxMetaSteps = MAX_STEPS / SIZE;
            long result =
                    maxMetaSteps*(countMap.get(new Point(-2, -1)) + countMap.get(new Point(-2, 1)) + countMap.get(new Point(2, -1)) + countMap.get(new Point(2, 1))) +
                    (maxMetaSteps-1)*(countMap.get(new Point(-1, -1)) + countMap.get(new Point(-1, 1)) + countMap.get(new Point(1, -1)) + countMap.get(new Point(1, 1))) +
                    countMap.get(new Point(0, -2)) + countMap.get(new Point(0, 2)) + countMap.get(new Point(-2, 0)) + countMap.get(new Point(2, 0)) +
                    countMap.get(new Point(1, 0))*maxMetaSteps*maxMetaSteps +
                    countMap.get(new Point(0, 0))*(maxMetaSteps-1)*(maxMetaSteps-1);
            System.out.println(result);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static char getChar(Point point, List<String> lines) {
        String line = lines.get(mod(point.y, SIZE));
        return line.charAt(mod(point.x, SIZE));
    }
    

    
    public static int mod(int i, int max) {
        int mod = i % max;
        if(mod < 0) {
            mod += max;
        }
        return mod;
    }
}
