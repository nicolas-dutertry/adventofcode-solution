package com.dutertry.adventofcode.year2023;

public class Day6_1 {

    public static void main(String[] args) {
        int[] times = new int[] {61, 70, 90, 66};
        int[] distances = new int[] {643, 1184, 1362, 1041};
        
        long total = 1;
        for(int i = 0; i < times.length; i++) {
            long T = times[i];
            long d = distances[i];
            double H1 = (T - Math.sqrt(Math.pow(T, 2) - 4*d))/2;
            double H2 = (T + Math.sqrt(Math.pow(T, 2) - 4*d))/2;
            
            double a1 = Math.ceil(H1);
            if(a1 == H1) {
                a1 = a1 + 1;
            }
            double a2 = Math.floor(H2);
            if(a2 == H2) {
                a2 = a2 - 1;
            }
            
            total = total * ((long)(a2-a1+1));
        }
        System.out.println(total);
        
    }

}
