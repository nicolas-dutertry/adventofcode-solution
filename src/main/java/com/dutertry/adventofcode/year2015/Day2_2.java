package com.dutertry.adventofcode.year2015;

import java.io.BufferedReader;
import java.io.IOException;

public class Day2_2 {
	public static void main(String[] args) {
		try(BufferedReader br = AdventUtils.getBufferedReader(2)) {
        	String line;
        	int total = 0;
        	while((line = br.readLine()) != null) {
        		String[] array = line.split("x");
        		int l = Integer.parseInt(array[0]);
        		int h = Integer.parseInt(array[1]);
        		int w = Integer.parseInt(array[2]);
        		int minPerimeter = 2*(l+h);
        		if(2*(l+w) < minPerimeter) {
        			minPerimeter = 2*(l+w);
        		}
        		if(2*(h+w) < minPerimeter) {
        			minPerimeter = 2*(h+w);
        		}
        		total += minPerimeter + l*h*w;
        	}
        	System.out.println(total);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
