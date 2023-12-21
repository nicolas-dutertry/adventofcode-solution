package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day21_1 {
    private static final int MAX_STEPS = 64;
    private static int MAXX = 0;
    private static int MAXY = 0;
    
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
            if(x > 0) {
                neighbors.add(new Point(x-1, y));
            }
            if(x < MAXX) {
                neighbors.add(new Point(x+1, y));
            }
            if(y > 0) {
                neighbors.add(new Point(x, y-1));
            }
            if(y < MAXY) {
                neighbors.add(new Point(x, y+1));
            }
            return neighbors;
        }
        
    }
    
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(21);
            MAXY = lines.size()-1;
            MAXX = lines.get(0).length()-1;
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
            
            for(int step = 1; step <= MAX_STEPS; step++) {
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
            
            System.out.println(evenPoints.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static char getChar(Point point, List<String> lines) {
        String line = lines.get(point.y);
        return line.charAt(point.x);
    }
}
