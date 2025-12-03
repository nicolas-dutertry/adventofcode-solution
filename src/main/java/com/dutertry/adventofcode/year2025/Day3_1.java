package com.dutertry.adventofcode.year2025;

import java.io.BufferedReader;
import java.io.IOException;

public class Day3_1 {

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(3)) {
            String line;
            int total = 0;
            while((line = br.readLine()) != null ) {
                int maxFirst = 0;
                int firstPos = 0;
                for(int i = 0; i < line.length()-1; i++) {
                    String c = line.substring(i, i+1);
                    int val = Integer.parseInt(c);
                    if(val > maxFirst) {
                        maxFirst = val;
                        firstPos = i;
                    }
                }

                int maxSecond = 0;
                for(int i = firstPos+1; i < line.length(); i++) {
                    String c = line.substring(i, i+1);
                    int val = Integer.parseInt(c);
                    if(val > maxSecond) {
                        maxSecond = val;
                    }
                }

                int result = maxFirst*10 + maxSecond;
                total += result;
            }
            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
