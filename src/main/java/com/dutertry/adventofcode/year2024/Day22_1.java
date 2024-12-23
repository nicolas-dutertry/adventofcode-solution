package com.dutertry.adventofcode.year2024;

import java.io.IOException;
import java.util.List;

public class Day22_1 {
    public static void main(String[] args) {
        try {
            long total = 0;
            List<String> lines = AdventUtils.getLines(22);
            for (String line : lines) {
                long l = Long.parseLong(line);
                total += getFinalSecretNumber(l);
            }

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static long getFinalSecretNumber(long secretNumber) {
        for(int i = 0; i < 2000; i++) {
            secretNumber = getNextSecret(secretNumber);
        }
        return secretNumber;
    }

    private static long getNextSecret(long secretNumber) {
        long res = secretNumber * 64;
        secretNumber = mix(secretNumber, res);
        secretNumber = prune(secretNumber);
        res = secretNumber / 32;
        secretNumber = mix(secretNumber, res);
        secretNumber = prune(secretNumber);
        res = secretNumber * 2048;
        secretNumber = mix(secretNumber, res);
        secretNumber = prune(secretNumber);
        return secretNumber;
    }

    private static long mix(long secretNumber, long res) {
        return res ^ secretNumber;
    }

    private static long prune(long secretNumber) {
        return secretNumber % 16777216;
    }
}
