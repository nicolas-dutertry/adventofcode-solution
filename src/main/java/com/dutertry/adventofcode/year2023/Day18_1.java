package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18_1 {
    private static final char UP = 'U';
    private static final char DOWN = 'D';
    private static final char RIGHT = 'R';
    private static final char LEFT = 'L';
    
    private static Pattern PATTERN = Pattern.compile("([A-Z]) ([0-9]+) \\(#[0-9a-z]+\\)");
    
    
    private static class Point {
        public final int x;
        public final int y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public Point next(char direction) {
            int nextx = x;
            int nexty = y;
            switch (direction) {
            case UP:
                nexty--;
                break;
            case DOWN:
                nexty++;
                break;
            case LEFT:
                nextx--;
                break;
            case RIGHT:
                nextx++;
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + direction);
            }
            return new Point(nextx, nexty);
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
    }
    
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(18, false);
            Set<Point> edgePoints = new HashSet<>();
            Point currentPoint = new Point(0,0);
            edgePoints.add(currentPoint);
            
            for (String line : lines) {
                Matcher matcher = PATTERN.matcher(line.trim());
                if(matcher.matches()) {
                    char direction = matcher.group(1).charAt(0);
                    int step = Integer.parseInt(matcher.group(2));
                    for(int i = 0; i < step; i++) {
                        currentPoint = currentPoint.next(direction);
                        edgePoints.add(currentPoint);
                    }
                } else {
                    throw new RuntimeException("Oups");
                }
            }
            
            int minx = Integer.MAX_VALUE;
            int maxx = Integer.MIN_VALUE;
            int miny = Integer.MAX_VALUE;
            int maxy = Integer.MIN_VALUE;
            
            for (Point point : edgePoints) {
                if(point.x < minx) {
                    minx = point.x; 
                }
                if(point.x > maxx) {
                    maxx = point.x; 
                }
                if(point.y < miny) {
                    miny = point.y; 
                }
                if(point.y > maxy) {
                    maxy = point.y; 
                }
            }
            
            Set<Point> insidePoints = new HashSet<>();
            Set<Point> outsidePoints = new HashSet<>();
            
            for(int x = minx; x <= maxx; x++) {
                for(int y = miny; y <= maxy; y++) {
                    Point point = new Point(x, y);
                    if(edgePoints.contains(point) || insidePoints.contains(point) || outsidePoints.contains(point)) {
                        continue;
                    }
                    Set<Point> accessibles = findAllAccessibleNeighbors(point, edgePoints, minx, maxx, miny, maxy);
                    boolean outside = false;
                    for (Point accessible : accessibles) {
                        if(accessible.x == minx || accessible.x == maxx || accessible.y == miny || accessible.y == maxy) {
                            outside = true;
                            break;
                        }
                    }
                    if(outside) {
                        outsidePoints.addAll(accessibles);
                    } else {
                        insidePoints.addAll(accessibles);
                    }
                }
            }
            
            int total = edgePoints.size() + insidePoints.size();
            System.out.println(total);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static Set<Point> findAllAccessibleNeighbors(Point point, Set<Point> edgePoints, int minx, int maxx, int miny, int maxy) {
        Set<Point> all = new HashSet<>();
        all.add(point);
        
        Set<Point> checked = new HashSet<>();
        Set<Point> tocheck = new HashSet<>();
        tocheck.add(point);
        
        while(!tocheck.isEmpty()) {
            Set<Point> nextCheck = new HashSet<>();
            for (Point p : tocheck) {
                Set<Point> directNeighbors = findDirectNeighbors(p, edgePoints, minx, maxx, miny, maxy);
                all.addAll(directNeighbors);
                for (Point neighbor : directNeighbors) {
                    if(!checked.contains(neighbor)) {
                        nextCheck.add(neighbor);
                    }
                }
                checked.add(p);
            }
            tocheck = nextCheck;
        }
        return all;
    }
    
    private static Set<Point> findDirectNeighbors(Point point, Set<Point> edgePoints, int minx, int maxx, int miny, int maxy) {
        Set<Point> neighbors = new HashSet<>();
        Point leftPoint = point.next(LEFT);
        Point rightPoint = point.next(RIGHT);
        Point upPoint = point.next(UP);
        Point downPoint = point.next(DOWN);
        
        if(leftPoint.x >= minx && leftPoint.x <= maxx && leftPoint.y >= miny && leftPoint.y <= maxy && !edgePoints.contains(leftPoint)) {
            neighbors.add(leftPoint);
        }
        if(rightPoint.x >= minx && rightPoint.x <= maxx && rightPoint.y >= miny && rightPoint.y <= maxy && !edgePoints.contains(rightPoint)) {
            neighbors.add(rightPoint);
        }
        if(upPoint.x >= minx && upPoint.x <= maxx && upPoint.y >= miny && upPoint.y <= maxy && !edgePoints.contains(upPoint)) {
            neighbors.add(upPoint);
        }
        if(downPoint.x >= minx && downPoint.x <= maxx && downPoint.y >= miny && downPoint.y <= maxy && !edgePoints.contains(downPoint)) {
            neighbors.add(downPoint);
        }
        return neighbors;
    }
}
