package com.dutertry.adventofcode.year2015;

public class Day10_2 {
    public static void main(String[] args) {
        String s = "1321131112";
        for (int i = 0; i < 50; i++) {
            s = say(s);
        }
        System.out.println(s.length());
	}
    
    private static String say(String s) {
        StringBuilder result = new StringBuilder();
        char current = '-';
        int currentCount = 0;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(i == 0) {
                current = c;
                currentCount = 1;
            } else {
                if(c == current) {
                    currentCount++;
                } else {
                    result.append(currentCount);
                    result.append(current);
                    current = c;
                    currentCount = 1;
                }
            }
        }
        result.append(currentCount);
        result.append(current);
        return result.toString();
    }
}
