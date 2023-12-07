package com.dutertry.adventofcode.year2015;

import java.io.IOException;

public class Day1_2 {

	public static void main(String[] args) {
		try {
			String input = AdventUtils.getInputAsString(1);
			
			int floor = 0;
			for(int i = 0; i < input.length(); i++) {
				char c = input.charAt(i);
				if(c == '(') {
					floor++;
				} else if(c == ')') {
					floor--;
				}
				
				if(floor == -1) {
					System.out.println(i+1);
					break;
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
