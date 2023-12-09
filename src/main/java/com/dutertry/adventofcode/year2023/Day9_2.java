package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class Day9_2 {
    public static void main(String[] args) {

        try (BufferedReader br = AdventUtils.getBufferedReader(9)) {
            String line;
            int total = 0;
            while ((line = br.readLine()) != null) {
                List<Integer> seq = Arrays.asList(StringUtils.split(line))
                        .stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                List<List<Integer>> seqList = new LinkedList<List<Integer>>();
                seqList.add(seq);
                while (true) {
                    List<Integer> nextSeq = new ArrayList<Integer>();
                    for (int i = 1; i < seq.size(); i++) {
                        nextSeq.add(seq.get(i) - seq.get(i - 1));
                    }
                    seq = nextSeq;
                    seqList.add(seq);
                    if (seq.stream().allMatch(i -> i == 0)) {
                        break;
                    }
                }

                for (int i = seqList.size() - 1; i > 0; i--) {
                    List<Integer> seqNext = seqList.get(i);
                    seq = seqList.get(i - 1);
                    int firstVal = seq.get(0) - seqNext.get(0);
                    seq.add(0, firstVal);
                }

                total += seq.get(0);
            }
            System.out.println(total);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
