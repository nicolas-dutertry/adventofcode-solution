package com.dutertry.adventofcode.year2015;

import java.io.IOException;

public class Day12_1 {
    public static void main(String[] args) {
        try {
            String s = AdventUtils.getInputAsString(12);
            String currentNumber = "";
            int sum = 0;
            for(int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if(c == '-' && currentNumber.length() == 0) {
                    currentNumber = "-";
                } else if(c >= '0' && c <= '9') {
                    currentNumber += c;
                } else {
                    if(!currentNumber.isEmpty() && !currentNumber.equals("-")) {
                        sum += Integer.parseInt(currentNumber);
                    }
                    currentNumber = "";
                }
            }
            System.out.println(sum);
            
        } catch(IOException e) {
            e.printStackTrace();
        }
	}
    
    
}
