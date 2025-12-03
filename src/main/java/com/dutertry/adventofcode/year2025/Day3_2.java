package com.dutertry.adventofcode.year2025;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day3_2 {
    private static class DigitInfo {
        public final int value;
        public final int position;

        public DigitInfo(int value, int position) {
            this.value = value;
            this.position = position;
        }

        public String toString() {
            return "Digit: value=" + value + ", position=" + position;
        }
    }

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(3)) {
            String line;
            int size = 12;
            long total = 0;
            while((line = br.readLine()) != null ) {
                List<DigitInfo> digits = new ArrayList<DigitInfo>();
                DigitInfo firstDigit = findMaxDigit(line, 0, line.length()-(size-1));
                digits.add(firstDigit);
                for(int i = 1; i < size; i++) {
                    DigitInfo previousDigit = digits.get(i-1);
                    DigitInfo digitInfo = findMaxDigit(line, previousDigit.position+1, line.length()-(size-1)+i);
                    digits.add(digitInfo);
                }

                long result = 0;
                for(int i = 0; i < digits.size(); i++) {
                    result = result * 10L + digits.get(i).value;
                }
                total += result;
            }
            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static DigitInfo findMaxDigit(String line, int startIndex, int endIndex) {
        int maxValue = -1;
        int maxPosition = -1;
        for(int i = startIndex; i < endIndex; i++) {
            String c = line.substring(i, i+1);
            int val = Integer.parseInt(c);
            if(val > maxValue) {
                maxValue = val;
                maxPosition = i;
            }
        }
        return new DigitInfo(maxValue, maxPosition);
    }

}
