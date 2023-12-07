package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Day5_2 {
    private static class SeedRange {
        public long start;
        public long length;

        public SeedRange(long start, long length) {
            this.start = start;
            this.length = length;
        }
    }

    private static class Range {
        public long destStart;
        public long sourceStart;
        public long length;

        public Range(long destStart, long sourceStart, long length) {
            this.destStart = destStart;
            this.sourceStart = sourceStart;
            this.length = length;
        }

        public void transform(List<SeedRange> seedRanges, List<SeedRange> transformedRanges,
                List<SeedRange> todoRanges) {
            for (SeedRange seedRange : seedRanges) {
                long seedStart = seedRange.start;
                long seedEnd = seedStart + seedRange.length - 1;
                long start = sourceStart;
                long end = sourceStart + length - 1;

                if (seedStart > end || seedEnd < start) {
                    todoRanges.add(new SeedRange(seedStart, seedEnd - seedStart + 1));
                } else if (seedStart < start && seedEnd > end) {
                    todoRanges.add(new SeedRange(seedStart, start - seedStart));
                    todoRanges.add(new SeedRange(end + 1, seedEnd - end));
                    transformedRanges.add(new SeedRange(destStart, length));
                } else if (seedStart < start && seedEnd <= end) {
                    todoRanges.add(new SeedRange(seedStart, start - seedStart));
                    long newLength = seedEnd - start + 1;
                    transformedRanges.add(new SeedRange(destStart, newLength));
                } else if (seedStart >= start && seedEnd > end) {
                    todoRanges.add(new SeedRange(end + 1, seedEnd - end));
                    long newLength = end - seedStart + 1;
                    transformedRanges.add(new SeedRange(destStart + seedStart - start, newLength));
                } else {
                    transformedRanges.add(new SeedRange(destStart + seedStart - start, seedRange.length));
                }
            }
        }
    }

    public static void main(String[] args) {
        try (BufferedReader br = AdventUtils.getBufferedReader(5)) {

            List<SeedRange> seedRanges = null;
            List<List<Range>> allRanges = new LinkedList<>();
            List<Range> currentRanges = null;

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().equals("")) {
                    continue;
                }

                if (seedRanges == null) {
                    seedRanges = new LinkedList<>();
                    String[] array = StringUtils.split(line);
                    for (int i = 1; i < array.length; i += 2) {
                        long seedStart = Long.parseLong(array[i].trim());
                        long length = Long.parseLong(array[i + 1].trim());
                        seedRanges.add(new SeedRange(seedStart, length));
                    }
                } else {
                    if (line.contains("map")) {
                        currentRanges = new LinkedList<>();
                        allRanges.add(currentRanges);
                    } else {
                        String[] array = StringUtils.split(line);
                        currentRanges.add(new Range(Long.parseLong(array[0].trim()), Long.parseLong(array[1].trim()),
                                Long.parseLong(array[2].trim())));
                    }
                }
            }

            List<SeedRange> sourceRanges = seedRanges;
            for (List<Range> ranges : allRanges) {
                List<SeedRange> tranformedRanges = new LinkedList<>();
                List<SeedRange> todoRanges = new LinkedList<>();
                for (Range range : ranges) {
                    range.transform(sourceRanges, tranformedRanges, todoRanges);
                    sourceRanges = todoRanges;
                    todoRanges = new LinkedList<>();
                }
                tranformedRanges.addAll(sourceRanges);
                sourceRanges = tranformedRanges;
            }

            long minLocation = Long.MAX_VALUE;
            for (SeedRange sourceRange : sourceRanges) {
                if (sourceRange.start < minLocation) {
                    minLocation = sourceRange.start;
                }
            }
            System.out.println(minLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
