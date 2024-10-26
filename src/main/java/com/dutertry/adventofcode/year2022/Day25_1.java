package com.dutertry.adventofcode.year2022;

import java.io.IOException;
import java.util.List;

public class Day25_1 {

    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(25);
            long sum = 0;
            for(String line : lines) {
                long value = parseSnafu(line);
                String s = toSnafuString(value);
                sum += value;
            }
            System.out.println(toSnafuString(sum));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static long parseSnafu(String line) {
        long snafu = 0;
        int power = 0;
        for(int i = line.length()-1; i >= 0; i--) {
            long value;
            char c = line.charAt(i);
            if(c == '-') {
                value = -1;
            } else if(c == '=') {
                value = -2;
            } else {
                value = Integer.parseInt(String.valueOf(c));
            }

            snafu += value * power(5, (int) power);
            power++;
        }
        return snafu;
    }

    private static long power(long l, int power) {
        if(power == 0) {
            return 1;
        }
        long result = 1;
        for(int i = 0; i < power; i++) {
            result *= l;
        }
        return result;
    }

    private static String toSnafuString(long snafu) {
        String s = Long.toString(snafu, 5);

        StringBuilder sb = new StringBuilder();
        int retain = 0;
        for(int i = s.length()-1; i >= 0; i--) {
            int value = Integer.parseInt(String.valueOf(s.charAt(i)));
            value += retain;
            retain = 0;
            if(value == 5) {
                sb.insert(0, 0);
                retain = 1;
            } else if(value == 4) {
                sb.insert(0, '-');
                retain = 1;
            } else if(value == 3) {
                sb.insert(0, '=');
                retain = 1;
            } else {
                sb.insert(0, value);
            }
        }
        if(retain == 1) {
            sb.insert(0, 1);
        }

        return sb.toString();
    }
}
