package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class AdventUtils {

    public static BufferedReader getBufferedReader(int day) {
        return new BufferedReader(new InputStreamReader(AdventUtils.class.getResourceAsStream("input" + day + ".txt"), StandardCharsets.UTF_8));
    }

}
