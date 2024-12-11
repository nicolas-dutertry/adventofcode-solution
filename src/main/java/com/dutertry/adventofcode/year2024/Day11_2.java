package com.dutertry.adventofcode.year2024;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day11_2 {
    private static record CacheKey(String stone, int iterationCount) {}

    private static final int MAX = 75;

    public static void main(String[] args) {
        try {
            String line  = AdventUtils.getString(11);
            String[] array = StringUtils.split(line);
            Map<CacheKey, Long> cache = new HashMap<>();

            long total = 0;
            for (String stone : array) {
                total += count(stone, MAX, cache);
            }
            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static long count(String stone, int steps, Map<CacheKey, Long> cache) {
        Long cached = cache.get(new CacheKey(stone, steps));
        if (cached != null) {
            return cached;
        }

        long result;
        if(steps == 0) {
            result = 1;
        } else {
            int size = stone.length();
            if (stone.equals("0")) {
                result = count("1", steps - 1, cache);
            } else if (size % 2 == 0) {
                String s1 = stone.substring(0, size / 2);
                String s2 = stone.substring(size / 2);
                s2 = String.valueOf(Long.parseLong(s2));
                result = count(s1, steps - 1, cache) + count(s2, steps - 1, cache);
            } else {
                long val = Long.parseLong(stone);
                result = count(String.valueOf(val * 2024L), steps - 1, cache);
            }
        }
        cache.put(new CacheKey(stone, steps), result);
        return result;
    }
}
