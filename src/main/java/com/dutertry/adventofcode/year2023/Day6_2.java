package com.dutertry.adventofcode.year2023;

public class Day6_2 {

    public static void main(String[] args) {
        long T = 61709066L;
        long d = 643118413621041L;
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
        
        long total = (long)(a2-a1+1);
        System.out.println(total);
        
    }

}
