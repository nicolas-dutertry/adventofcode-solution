package com.dutertry.adventofcode.year2015;

import java.io.BufferedReader;
import java.io.IOException;

public class Day8_2 {
    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(8)) {
            String line;
            int total = 0;
            while((line = br.readLine()) != null) {
                total += countReescaped(line.trim()) - line.trim().length();
            }
            System.out.println(total);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
    
    private static int countReescaped(String s) {
        int count = 2;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            count++;
            if(c == '\\' || c == '"') {
                count++;
            }
        }
        return count;
    }
}
