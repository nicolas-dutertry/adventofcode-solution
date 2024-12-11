package com.dutertry.adventofcode.year2024;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day11_2 {
    private static final int MAX = 75;

    public static void main(String[] args) {
        try {
            String line  = AdventUtils.getString(11);
            List<String> array = Arrays.asList(StringUtils.split(line));
            Map<String, Map<Integer, Long>> cache = buildCache();
            long count = 0;
            for(int i = 0; i < MAX; i++) {
                int remaining = MAX - i;
                List<String> newArray = new LinkedList<>();
                for (String s : array)  {
                    int size = s.length();
                    if (size == 1) {
                        Map<Integer, Long> cachedMap = cache.get(s);
                        if (cachedMap != null && cachedMap.containsKey(remaining)) {
                            count += cachedMap.get(remaining);
                            continue;
                        }
                    }

                    if(s.equals("0")) {
                        newArray.add("1");
                    } else if(size % 2 == 0) {
                        String s1 = s.substring(0, size / 2);
                        String s2 = s.substring(size / 2);
                        newArray.add(s1);
                        newArray.add(String.valueOf(Long.parseLong(s2)));
                    } else {
                        long val = Long.parseLong(s);
                        newArray.add(String.valueOf(val * 2024L));
                    }
                }
                array = newArray;
            }
            System.out.println(array.size() + count);

        } catch(IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
    }

    private static Map<String, Map<Integer, Long>> buildCache() {
        Map<String, Map<Integer, Long>> cache = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            Map<Integer, Long> map = cache.computeIfAbsent(String.valueOf(i), k -> new HashMap<>());
            map.put(0, 1L);
        }

        for(int limit = 1; limit <= MAX; limit++) {
            for (int i = 0; i < 10; i++) {
                fillCache(i, limit, cache);
            }
        }
        return cache;
    }

    private static void fillCache(int digit, int limit, Map<String, Map<Integer, Long>> cache) {
        Map<Integer, Long> map = cache.get(String.valueOf(digit));

        List<String> array = new LinkedList<>();
        array.add(String.valueOf(digit));
        long count = 0;
        for(int j = 0; j < limit; j++) {
            List<String> newArray = new LinkedList<>();
            int remaining = limit - j;
            for (String s : array) {
                int size = s.length();
                if (size == 1) {
                    Map<Integer, Long> map1 = cache.get(s);
                    if (map1 != null && map1.containsKey(remaining)) {
                        count += map1.get(remaining);
                        continue;
                    }
                }

                if (s.equals("0")) {
                    newArray.add("1");
                } else if (size % 2 == 0) {
                    String s1 = s.substring(0, size / 2);
                    String s2 = s.substring(size / 2);
                    newArray.add(s1);
                    newArray.add(String.valueOf(Long.parseLong(s2)));
                } else {
                    long val = Long.parseLong(s);
                    newArray.add(String.valueOf(val * 2024L));
                }
            }
            array = newArray;
        }
        map.put(limit, array.size() + count);
    }

}
