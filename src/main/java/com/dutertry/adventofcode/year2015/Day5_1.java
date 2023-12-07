package com.dutertry.adventofcode.year2015;

import java.io.BufferedReader;
import java.io.IOException;

public class Day5_1 {

	public static void main(String[] args) {
		try(BufferedReader br = AdventUtils.getBufferedReader(5)) {
        	String line;
        	int total = 0;
        	while((line = br.readLine()) != null) {
        		char previousChar = '-';
        		int vowels = 0;
        		boolean twice = false;
        		boolean error = false;
        		for(int i = 0; i < line.length(); i++) {
        			char c = line.charAt(i);
        			if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
        				vowels++;
        			}
        			if(c == previousChar) {
        				twice = true;
        			}
        			String s = String.valueOf(previousChar) + c;
        			if(s.equals("ab") || s.equals("cd") || s.equals("pq") || s.equals("xy")) {
        				error = true;
        				break;
        			}
        			previousChar = c;
        		}
        		if(!error && twice && vowels > 2) {
        			total++;
        		}
        	}
        	System.out.println(total);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
