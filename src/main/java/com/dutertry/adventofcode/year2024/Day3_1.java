package com.dutertry.adventofcode.year2024;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3_1 {
    private static final Pattern PATTERN = Pattern.compile(
            "mul\\(([0-9]{1,3}),([0-9]{1,3})\\)");

    public static void main(String[] args) {
        try {
            long total = 0;
            String input = AdventUtils.getString(3);
            Matcher matcher = PATTERN.matcher(input);

            while (matcher.find()) {
                long left = Long.parseLong(matcher.group(1));
                long right = Long.parseLong(matcher.group(2));
                total += left * right;
            }
            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
