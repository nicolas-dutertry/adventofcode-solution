package com.dutertry.adventofcode.year2015;

import java.io.BufferedReader;
import java.io.IOException;

public class Day8_1 {
    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(8)) {
            String line;
            int total = 0;
            while((line = br.readLine()) != null) {
                total += line.trim().length() - countUnescaped(line.trim());
            }
            System.out.println(total);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
    
    private static int countUnescaped(String s) {
        int count = 0;
        for(int i = 1; i < s.length()-1; i++) {
            char c = s.charAt(i);
            count++;
            if(c == '\\') {
                char c1 = s.charAt(i+1);
                if(c1 == 'x') {
                    i+=3;
                } else {
                    i++;
                }
            }
        }
        return count;
    }
}
