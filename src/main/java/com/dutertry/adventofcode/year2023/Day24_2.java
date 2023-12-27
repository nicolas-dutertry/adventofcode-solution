package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dutertry.adventofcode.Rational;
import com.dutertry.adventofcode.LinearSystem;

public class Day24_2 {    
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
    }
    
    private static Pattern PATTERN = Pattern.compile("([0-9]+),\\s+([0-9]+),\\s+([0-9]+)\\s+@\\s+(-?[0-9]+),\\s+(-?[0-9]+),\\s+(-?[0-9]+)");
    
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
            
            Rational[] solution = solve(hailstones.get(3), hailstones.get(4), hailstones.get(5));
            Rational result = solution[0].plus(solution[1]).plus(solution[2]);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Rational[] solve(Hailstone h1, Hailstone h2, Hailstone h3) {
        long px1 = h1.px;
        long py1 = h1.py;
        long pz1 = h1.pz;
        long vx1 = h1.vx;
        long vy1 = h1.vy;
        long vz1 = h1.vz;
        
        long px2 = h2.px;
        long py2 = h2.py;
        long pz2 = h2.pz;
        long vx2 = h2.vx;
        long vy2 = h2.vy;
        long vz2 = h2.vz;
        
        long px3 = h3.px;
        long py3 = h3.py;
        long pz3 = h3.pz;
        long vx3 = h3.vx;
        long vy3 = h3.vy;
        long vz3 = h3.vz;
        
        long[][] coefficients = {
            {vy1-vy2, vx2-vx1, 0, py2-py1, px1-px2, 0},
            {vz1-vz2, 0, vx2-vx1, pz2-pz1, 0, px1-px2},
            {0, vz1-vz2, vy2-vy1, 0, pz2-pz1, py1-py2},
            {vy1-vy3, vx3-vx1, 0, py3-py1, px1-px3, 0},
            {vz1-vz3, 0, vx3-vx1, pz3-pz1, 0, px1-px3},
            {0, vz1-vz3, vy3-vy1, 0, pz3-pz1, py1-py3}
        };

        long[] constants = {
                vy1*px1-vy2*px2-vx1*py1+vx2*py2,
                vz1*px1-vz2*px2-vx1*pz1+vx2*pz2,
                vz1*py1-vz2*py2-vy1*pz1+vy2*pz2,
                vy1*px1-vy3*px3-vx1*py1+vx3*py3,
                vz1*px1-vz3*px3-vx1*pz1+vx3*pz3,
                vz1*py1-vz3*py3-vy1*pz1+vy3*pz3};

        
        return LinearSystem.solveSystem(coefficients, constants);
    }
}
