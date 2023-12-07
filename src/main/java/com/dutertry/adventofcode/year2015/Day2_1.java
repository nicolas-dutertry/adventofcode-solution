package com.dutertry.adventofcode.year2015;

import java.io.BufferedReader;
import java.io.IOException;

public class Day2_1 {
	public static void main(String[] args) {
		try(BufferedReader br = AdventUtils.getBufferedReader(2)) {
        	String line;
        	int total = 0;
        	while((line = br.readLine()) != null) {
        		String[] array = line.split("x");
        		int l = Integer.parseInt(array[0]);
        		int h = Integer.parseInt(array[1]);
        		int w = Integer.parseInt(array[2]);
        		int minArea = l*h;
        		if(l*w < minArea) {
        			minArea = l*w;
        		}
        		if(h*w < minArea) {
        			minArea = h*w;
        		}
        		total += minArea + 2*l*h + 2*l*w + 2*h*w;
        	}
        	System.out.println(total);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
