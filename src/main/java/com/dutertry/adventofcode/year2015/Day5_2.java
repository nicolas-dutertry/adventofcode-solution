package com.dutertry.adventofcode.year2015;

import java.io.BufferedReader;
import java.io.IOException;

public class Day5_2 {

	public static void main(String[] args) {
		try(BufferedReader br = AdventUtils.getBufferedReader(5)) {
        	String line;
        	int total = 0;
        	while((line = br.readLine()) != null) {
        		char prevPrevChar = '-';
        		char prevChar = '-';
        		boolean twice = false;
        		boolean repeat = false;
        		for(int i = 0; i < line.length(); i++) {
        			char c = line.charAt(i);
        			if(c == prevPrevChar) {
        			    repeat = true;
        			}
        			String pair = String.valueOf(prevChar) + c;
        			if(i > 2 && line.substring(0, i-1).indexOf(pair) >= 0) {
        			    twice = true;
        			}
        			
        			if(repeat && twice) {
        			    break;
        			}
        			
        			prevPrevChar = prevChar;
        			prevChar = c;
        		}
        		if(repeat && twice) {
        			total++;
        		}
        	}
        	System.out.println(total);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
