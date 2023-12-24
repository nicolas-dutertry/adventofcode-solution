package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Day23_2 {
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
    
    private static class Path {
        public final Set<Point> pointSet = new HashSet<>();
        public final List<Point> pointList = new LinkedList<>();
        public Point lastAccessPoint;
        
        public void addPoint(Point point) {
            pointSet.add(point);
            pointList.add(point);
            lastAccessPoint = point;
        }
        
        public Path copy() {
            Path newPath = new Path();
            newPath.lastAccessPoint = lastAccessPoint;
            newPath.pointSet.addAll(pointList);
            newPath.pointList.addAll(pointList);
            return newPath;
        }
    }
    
    private static class Tunnel {
        public final Point access1;
        public final Point end1;
        public final Point access2;
        public final Point end2;
        public final Set<Point> points = new HashSet<>();
        
        public Tunnel(Point access1, Point end1, Point access2, Point end2) {
            this.access1 = access1;
            this.end1 = end1;
            this.access2 = access2;
            this.end2 = end2;
        }
        
        public void addPoints(Set<Point> points) {
            this.points.addAll(points);
        }
        
        public int getSize() {
            return points.size();
        }
        
        public boolean hasAccess(Point point) {
            return point.equals(access1) || point.equals(access2);
        }
        
        public Point getOtherAccess(Point point) {
            if(point.equals(access1)) {
                return access2;
            } else if(point.equals(access2)) {
                return access1;
            }
            throw new RuntimeException("Oups");
        }

        @Override
        public int hashCode() {
            return Objects.hash(access1, access2, end1, end2);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Tunnel other = (Tunnel) obj;
            return Objects.equals(access1, other.access1) && Objects.equals(access2, other.access2)
                    && Objects.equals(end1, other.end1) && Objects.equals(end2, other.end2);
        }
    }
    
    private static class DeadEnd {
        public final Point access;
        public final Point start;
        public final Set<Point> points = new HashSet<>();
        
        public DeadEnd(Point access, Point start) {
            this.access = access;
            this.start = start;
        }
        
        public void addPoints(Set<Point> points) {
            this.points.addAll(points);
        }

        @Override
        public int hashCode() {
            return Objects.hash(access, start);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            DeadEnd other = (DeadEnd) obj;
            return Objects.equals(access, other.access) && Objects.equals(start, other.start);
        }
    }
    
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(23, false);
            SIZE = lines.size();
            
            Set<Point> inTunnel = new HashSet<>();
            Set<Tunnel> tunnels = new HashSet<>();
            Set<DeadEnd> deadEnds = new HashSet<>();
            for(int y = 0; y < SIZE; y++) {
                for(int x = 0; x < SIZE; x++) {
                    Point point = new Point(x, y);
                    if(getChar(point, lines) == '#') {
                        continue;
                    }
                    if(inTunnel.contains(point)) {
                        continue;
                    }
                    
                    Set<Point> neighbors = getValidNeighbor(point, lines);
                    if(neighbors.size() != 2) {
                        continue;
                    }
                    
                    inTunnel.add(point);
                    Iterator<Point> it = neighbors.iterator();
                    
                    Set<Point> leftPoints = new HashSet<>();
                    leftPoints.add(point);
                    Point leftAccess = it.next();
                    Point leftEnd = point;
                    while(true) {
                        Set<Point> nextNeighbors = getValidNeighbor(leftAccess, lines);
                        nextNeighbors.remove(leftEnd);
                        if(nextNeighbors.size() == 1) {
                            leftEnd = leftAccess;
                            leftAccess = nextNeighbors.iterator().next();
                            leftPoints.add(leftEnd);
                            inTunnel.add(leftEnd);
                        } else if(nextNeighbors.size() > 1) {
                            break;
                        } else {
                            leftEnd = leftAccess;
                            leftAccess = null;
                            leftPoints.add(leftEnd);
                            inTunnel.add(leftEnd);
                            break;
                        }
                    }
                    
                    Set<Point> rightPoints = new HashSet<>();
                    rightPoints.add(point);
                    Point rightAccess = it.next();
                    Point rightEnd = point;
                    while(true) {
                        Set<Point> nextNeighbors = getValidNeighbor(rightAccess, lines);
                        nextNeighbors.remove(rightEnd);
                        if(nextNeighbors.size() == 1) {
                            rightEnd = rightAccess;
                            rightAccess = nextNeighbors.iterator().next();
                            rightPoints.add(rightEnd);
                            inTunnel.add(rightEnd);
                        } else if(nextNeighbors.size() > 1) {
                            break;
                        } else {
                            rightEnd = rightAccess;
                            rightAccess = null;
                            rightPoints.add(rightEnd);
                            inTunnel.add(rightEnd);
                            break;
                        }
                    }
                    
                    if(leftAccess != null && rightAccess != null) {
                        Tunnel tunnel = new Tunnel(leftAccess, leftEnd, rightAccess, rightEnd);
                        tunnel.addPoints(leftPoints);
                        tunnel.addPoints(rightPoints);
                        tunnels.add(tunnel);
                    } else if(leftAccess != null) {
                        DeadEnd deadEnd = new DeadEnd(leftAccess, leftEnd);
                        deadEnd.addPoints(leftPoints);
                        deadEnd.addPoints(rightPoints);
                        deadEnds.add(deadEnd);
                    } else if(rightAccess != null) {
                        DeadEnd deadEnd = new DeadEnd(rightAccess, rightEnd);
                        deadEnd.addPoints(rightPoints);
                        deadEnd.addPoints(leftPoints);
                        deadEnds.add(deadEnd);
                    } else {
                        throw new RuntimeException("Oups");
                    }
                }
            }
            
            DeadEnd startDeadEnd = null;
            DeadEnd endDeadEnd = null;
            for (DeadEnd deadEnd : deadEnds) {
                if(deadEnd.points.contains(new Point(1,  0))) {
                    startDeadEnd = deadEnd;
                } else {
                    endDeadEnd = deadEnd;
                }
            }
            
            Point startPoint = startDeadEnd.access;
            Point endPoint = endDeadEnd.access;
            
            Set<Point> accessPoints = new HashSet<>();
            for (Tunnel tunnel : tunnels) {
                accessPoints.add(tunnel.access1);
                accessPoints.add(tunnel.access2);
            }
            
            Map<Point, Set<Point>> accessConnectionMap = new HashMap<>();
            
            for (Point accessPoint : accessPoints) {
                Set<Tunnel> accessTunnels = getTunnelsByAccess(accessPoint, tunnels);
                Set<Point> points = new HashSet<>();
                for (Tunnel tunnel : accessTunnels) {
                    Point otherAccess = tunnel.getOtherAccess(accessPoint);
                    points.add(otherAccess);
                }
                accessConnectionMap.put(accessPoint, points);
            }
            
            Path startpath = new Path();
            startpath.addPoint(startPoint);
            
            List<Path> paths = getPaths(startpath, accessConnectionMap, endPoint);
            
            int max = 0;
            for (Path path : paths) {
                int size = 0;
                for(int i = 0; i < path.pointList.size()-1; i++) {
                    Point access1 = path.pointList.get(i);
                    Point access2 = path.pointList.get(i+1);
                    Tunnel tunnel = getTunnel(tunnels, access1, access2);
                    size += 1 + tunnel.getSize();
                }
                if(size > max) {
                    max = size;
                }
            }
            max = max + startDeadEnd.points.size() + endDeadEnd.points.size();
            System.out.println(max);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static List<Path> getPaths(Path path, Map<Point, Set<Point>> accessConnectionMap, Point endPoint) {
        List<Path> newPaths = new LinkedList<>();
        
        Set<Point> forbidden = new HashSet<>(path.pointSet);
        Set<Point> neighbors = accessConnectionMap.get(path.lastAccessPoint);
        for (Point neighbor : neighbors) {
            if(path.pointSet.contains(neighbor)) {
                continue;
            }
            if(neighbor.equals(endPoint)) {
                Path newPath = path.copy();
                newPath.addPoint(neighbor);
                newPaths.add(newPath);
                continue;
            }
            if(!getAccessiblePoints(neighbor, accessConnectionMap, forbidden).contains(endPoint)) {
                continue;
            }
            Path newPath = path.copy();
            newPath.addPoint(neighbor);
            newPaths.addAll(getPaths(newPath, accessConnectionMap, endPoint));
        }
        
        return newPaths;
    }
    
    private static Set<Point> getAccessiblePoints(Point accessPoint, Map<Point, Set<Point>> accessConnectionMap, Set<Point> forbidden) {
        Set<Point> done = new HashSet<>();
        done.addAll(forbidden);
        done.add(accessPoint);
        computeAccessiblePoints(accessPoint, accessConnectionMap, done);
        return done;
    }
    
    private static void computeAccessiblePoints(Point accessPoint, Map<Point, Set<Point>> accessConnectionMap, Set<Point> done) {
        Set<Point> points = accessConnectionMap.get(accessPoint);
        for (Point otherAccess : points) {
            if(!done.contains(otherAccess)) {
                done.add(otherAccess);
                computeAccessiblePoints(otherAccess, accessConnectionMap, done);
            }
        }
    }
    
    private static Set<Tunnel> getTunnelsByAccess(Point accessPoint, Set<Tunnel> tunnels) {
        Set<Tunnel> set = new HashSet<>();
        for (Tunnel tunnel : tunnels) {
            if(tunnel.hasAccess(accessPoint)) {
                set.add(tunnel);
            }
        }
        return set;
    }
    private static char getChar(Point point, List<String> lines) {
        return lines.get(point.y).charAt(point.x);
    }
    
    private static Set<Point> getValidNeighbor(Point point, List<String> lines) {
        Set<Point> neighbors = point.getNeighbors();
        Iterator<Point> it = neighbors.iterator();
        while(it.hasNext()) {
            Point neighbor = it.next();
            char c = getChar(neighbor, lines);
            if(c == '#') {
                it.remove();
            }
        }
        return neighbors;
    }
    
    private static Tunnel getTunnel(Set<Tunnel> tunnels, Point access1, Point access2) {
        for (Tunnel tunnel : tunnels) {
            if(tunnel.access1.equals(access1) && tunnel.access2.equals(access2)) {
                return tunnel;
            }
            if(tunnel.access2.equals(access1) && tunnel.access1.equals(access2)) {
                return tunnel;
            }
        }
        return null;
    }
}
