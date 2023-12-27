package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day24_1 {    
    private static class Hailstone {
        public final long px;
        public final long py;
        public final long pz;
        public final long vx;
        public final long vy;
        public final long vz;
        
        public Hailstone(long px, long py, long pz, long vx, long vy, long vz) {
            this.px = px;
            this.py = py;
            this.pz = pz;
            this.vx = vx;
            this.vy = vy;
            this.vz = vz;
        }

        @Override
        public int hashCode() {
            return Objects.hash(px, py, pz, vx, vy, vz);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Hailstone other = (Hailstone) obj;
            return px == other.px && py == other.py && pz == other.pz && vx == other.vx && vy == other.vy
                    && vz == other.vz;
        }

        @Override
        public String toString() {
            return px + ", " + py + ", " + pz + " @ " + vx + ", " + vy + ", " + vz;
        }
        
    }
    
    public static class Point {
        private final double x;
        private final double y;
        
        public Point(double x, double y) {
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
            return Double.doubleToLongBits(x) == Double.doubleToLongBits(other.x)
                    && Double.doubleToLongBits(y) == Double.doubleToLongBits(other.y);
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
        
    }
    
    private static class LinearFunction {
        private final double a;
        private final double b;
        
        public LinearFunction(double a, double b) {
            this.a = a;
            this.b = b;
        }
    }
    
    private static Pattern PATTERN = Pattern.compile("([0-9]+),\\s+([0-9]+),\\s+([0-9]+)\\s+@\\s+(-?[0-9]+),\\s+(-?[0-9]+),\\s+(-?[0-9]+)");
    private static final long TEST_MIN = 200000000000000L;
    private static final long TEST_MAX = 400000000000000L;
    
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(24, false);
            List<Hailstone> hailstones = new ArrayList<>();
            for (String line : lines) {
                Matcher matcher = PATTERN.matcher(line);
                if(!matcher.matches()) {
                    throw new RuntimeException("Oups");
                }
                
                long px = Long.parseLong(matcher.group(1));
                long py = Long.parseLong(matcher.group(2));
                long pz = Long.parseLong(matcher.group(3));
                long vx = Long.parseLong(matcher.group(4));
                long vy = Long.parseLong(matcher.group(5));
                long vz = Long.parseLong(matcher.group(6));
                hailstones.add(new Hailstone(px, py, pz, vx, vy, vz));
            }
            
            int count = 0;
            for(int i = 0; i < hailstones.size()-1; i++) {
                for(int j = i+1; j < hailstones.size(); j++) {
                    Hailstone h1 = hailstones.get(i);
                    Hailstone h2 = hailstones.get(j);
                    LinearFunction l1 = getLinearFunction(h1);
                    LinearFunction l2 = getLinearFunction(h2);
                    Point intersection = intersection(l1, l2);
                    //System.out.println(h1);
                    //System.out.println(h2);
                    //System.out.println(intersection);
                    if(intersection != null && intersection.x >= TEST_MIN && intersection.x <= TEST_MAX && intersection.y >= TEST_MIN && intersection.y <= TEST_MAX && !isPast(intersection, h1) && !isPast(intersection, h2)) {
                        count++;
                        //System.out.println("OK");
                    } else {
                        //System.out.println("KO");
                    }
                }
            }

            System.out.println(count);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static LinearFunction getLinearFunction(Hailstone hail) {
        double a = ((double)hail.vy) / ((double)hail.vx);
        double b = hail.py - a * hail.px;
        return new LinearFunction(a, b);
    }
    
    private static Point intersection(LinearFunction l1, LinearFunction l2) {
        if(l1.a == l2.a) {
            return null;
        }
        double x = (l2.b-l1.b) / (l1.a-l2.a);
        double y = l1.a * x + l1.b;
        return new Point(x, y);
    }
    
    private static boolean isPast(Point point, Hailstone hailstone) {
        if(hailstone.vx > 0) {
            return point.x < hailstone.px;
        } else {
            return point.x > hailstone.px;
        }
    }
}
