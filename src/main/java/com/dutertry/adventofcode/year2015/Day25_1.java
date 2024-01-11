package com.dutertry.adventofcode.year2015;

public class Day25_1 {
    private static final int ROW = 2947;
    private static final int COLUMN = 3029;
    
    public static void main(String[] args) {
        int row = 1;
        int column = 1;
        int diag = 1;
        long value = 20151125;
        while(true) {
            if(row == 1) {
                diag++;
                row = diag;
                column = 1;
            } else {
                row--;
                column++;
            }
            value = (value * 252533) % 33554393;
            
            if(row == ROW & column == COLUMN) {
                System.out.println(value);
                break;
            }
        }
    }
}
