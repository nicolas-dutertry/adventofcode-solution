package com.dutertry.adventofcode.year2024;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day22_2 {
    private static record Sequence(long c1, long c2, long c3, long c4) {};

    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(22);
            Set<Sequence> allSequences = new HashSet<>();
            List<Map<Sequence, Long>> buyers = new LinkedList<>();
            for (String line : lines) {
                long l = Long.parseLong(line);
                List<Long> secretNumbers = getAllSecretNumbers(l);
                List<Long> prices = new ArrayList<>();
                for (Long secretNumber : secretNumbers) {
                    prices.add(secretNumber % 10);
                }

                List<Long> changes = new ArrayList<>();
                for(int i = 1; i < prices.size(); i++) {
                    long previousValue = prices.get(i-1);
                    long values = prices.get(i);
                    changes.add(values-previousValue);
                }

                Map<Sequence, Long> seqToBanana = new HashMap<>();
                buyers.add(seqToBanana);

                for(int i = 3; i < changes.size(); i++) {
                    long c1 = changes.get(i-3);
                    long c2 = changes.get(i-2);
                    long c3 = changes.get(i-1);
                    long c4 = changes.get(i);
                    Sequence sequence = new Sequence(c1, c2, c3, c4);
                    if(!seqToBanana.containsKey(sequence)) {
                        seqToBanana.put(sequence, prices.get(i+1));
                        allSequences.add(sequence);
                    }
                }
            }

            long maxbananas = 0;
            for(Sequence sequence : allSequences) {
                long bananas = 0;
                for(Map<Sequence, Long> buyer : buyers) {
                    Long b = buyer.get(sequence);
                    if(b != null) {
                        bananas += b;
                    }
                }
                maxbananas = Math.max(maxbananas, bananas);
            }

            System.out.println(maxbananas);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Long> getAllSecretNumbers(long secretNumber) {
        List<Long> secretNumbers = new ArrayList<>();
        secretNumbers.add(secretNumber);
        for(int i = 0; i < 2000; i++) {
            secretNumber = getNextSecret(secretNumber);
            secretNumbers.add(secretNumber);
        }
        return secretNumbers;
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
