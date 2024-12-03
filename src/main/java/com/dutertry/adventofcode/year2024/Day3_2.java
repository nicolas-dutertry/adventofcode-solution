package com.dutertry.adventofcode.year2024;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3_2 {
    private static final Pattern PATTERN = Pattern.compile(
            "mul\\(([0-9]{1,3}),([0-9]{1,3})\\)");

    public static void main(String[] args) {
        try {
            long total = 0;
            String input = AdventUtils.getString(3);

            int doIndex = 0;
            List<String> enabledChunks = new LinkedList<>();
            while(true) {
                int dontIndex = input.indexOf("don't()", doIndex);
                if(dontIndex == -1) {
                    enabledChunks.add(input.substring(doIndex));
                    break;
                }

                enabledChunks.add(input.substring(doIndex, dontIndex));
                doIndex = input.indexOf("do()", dontIndex);
                if(doIndex == -1) {
                    break;
                }
            }

            for (String enabledChunk : enabledChunks) {
                Matcher matcher = PATTERN.matcher(enabledChunk);

                while (matcher.find()) {
                    int start = matcher.start();
                    long left = Long.parseLong(matcher.group(1));
                    long right = Long.parseLong(matcher.group(2));
                    total += left * right;
                }
            }
            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
