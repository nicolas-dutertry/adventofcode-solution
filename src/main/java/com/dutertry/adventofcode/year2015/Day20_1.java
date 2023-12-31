package com.dutertry.adventofcode.year2015;

public class Day20_1 {
private static final int PRESENT_COUNT = 29_000_000;
    
    public static void main(String[] args) {
        int maxhouse = PRESENT_COUNT/10 + 2;
        int[] presents = new int[maxhouse];
        
        for(int elf = 1; elf < maxhouse; elf++) {
            int elfpresents = elf*10;
            int step = 1;
            int house = elf*step;
            while(house <= maxhouse - 1) {
                presents[house] += elfpresents;
                step++;
                house = elf*step;
            }
            if(presents[elf] >= PRESENT_COUNT) {
                System.out.println(elf);
                break;
            }
        }
    }    
}
