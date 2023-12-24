package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day23_1 {
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
            if(x > 0) {
                neighbors.add(new Point(x-1, y));
            }
            if(x < SIZE-1) {
                neighbors.add(new Point(x+1, y));
            }
            if(y > 0) {
                neighbors.add(new Point(x, y-1));
            }
            if(y < SIZE-1) {
                neighbors.add(new Point(x, y+1));
            }
            return neighbors;
        }
    }
    
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(23, false);
            SIZE = lines.size();
            Point startPoint = new Point(1, 0);
            Point endPoint = new Point(SIZE-2, SIZE-1);
            
            List<Point> start = new LinkedList<>();
            start.add(startPoint);
            
            List<List<Point>> paths = getPaths(start, lines, endPoint);
            int max = 0;
            for (List<Point> path : paths) {
                if(path.size()-1 > max) {
                    max = path.size()-1;
                }
            }
            System.out.println(max);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static List<List<Point>> getPaths(List<Point> path, List<String> lines, Point endPoint) {
        List<List<Point>> paths = new LinkedList<>();
        
        Point lastPoint = path.get(path.size()-1);
        char c = getChar(lastPoint, lines);
        if(c == '.') {
            Set<Point> neighbors = lastPoint.getNeighbors();
            for (Point neighbor : neighbors) {
                char nc = getChar(neighbor, lines);
                if(nc != '#' && !path.contains(neighbor)) {
                    if(!path.contains(neighbor)) {
                        List<Point> newPath = new LinkedList<>(path);
                        newPath.add(neighbor);
                        if(neighbor.equals(endPoint)) {
                            paths.add(newPath);
                        } else {
                            paths.addAll(getPaths(newPath, lines, endPoint));
                        }
                    }
                }
            }
        } else {
            Point nextPoint;
            if(c == '>') {
                nextPoint = new Point(lastPoint.x+1, lastPoint.y);
            } else if(c == '<') {
                nextPoint = new Point(lastPoint.x-1, lastPoint.y);
            } else if(c == '^') {
                nextPoint = new Point(lastPoint.x, lastPoint.y-1);
            } else if(c == 'v') {
                nextPoint = new Point(lastPoint.x, lastPoint.y+1);
            } else {
                throw new RuntimeException("Oups");
            }
            if(!path.contains(nextPoint)) {
                List<Point> newPath = new LinkedList<>(path);
                newPath.add(nextPoint);
                if(nextPoint.equals(endPoint)) {
                    paths.add(newPath);
                } else {
                    paths.addAll(getPaths(newPath, lines, endPoint));
                }
            }
            
        }
        return paths;
    }
    
    private static char getChar(Point point, List<String> lines) {
        return lines.get(point.y).charAt(point.x);
    }
}
