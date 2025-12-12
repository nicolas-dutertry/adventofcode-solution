package com.dutertry.adventofcode.year2025;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day12_1 {
    private record Region(int width, int height, int[] presentCounts) {}

    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(12);
            boolean isPresent = true;
            List<Integer> presentSizes = new ArrayList<>();
            List<Region> regions = new ArrayList<>();
            int currentPresentSize = 0;
            for(String line : lines) {
                line = line.trim();
                if(isPresent) {
                    if(StringUtils.isEmpty(line)) {
                        presentSizes.add(currentPresentSize);
                        currentPresentSize = 0;
                        continue;
                    } else if(line.contains("x")) {
                        isPresent = false;
                    } else if(line.contains(":")) {
                        // ignore
                        continue;
                    } else {
                        int size = 0;
                        for(int i = 0; i < line.length(); i++) {
                            if(line.charAt(i) == '#') {
                                size++;
                            }
                        }
                        currentPresentSize += size;
                        continue;
                    }
                }

                String[] parts = StringUtils.split(line, ":");
                String sizeString = parts[0].trim();
                String presentsString = parts[1].trim();
                String[] sizeParts = StringUtils.split(sizeString, "x");
                int width = Integer.parseInt(sizeParts[0].trim());
                int height = Integer.parseInt(sizeParts[1].trim());
                String[] presentsParts = StringUtils.split(presentsString, " ");
                List<Integer> presentCounts = new ArrayList<>();
                for(String presentPart : presentsParts) {
                    presentCounts.add(Integer.parseInt(presentPart.trim()));
                }
                int[] presentCountsArray = presentCounts.stream().mapToInt(Integer::intValue).toArray();
                regions.add(new Region(width, height, presentCountsArray));
            }

            int count = 0;
            for(Region region : regions) {
                int remainingSpace = region.width * region.height;
                for(int i = 0; i < presentSizes.size(); i++) {
                    int presentSize = presentSizes.get(i);
                    int presentCount = region.presentCounts[i];
                    remainingSpace -= presentSize*presentCount;
                }
                if(remainingSpace >= 0) {
                    count++;
                }
            }
            System.out.println(count);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static int sizeOfPresent(boolean[][] present) {
        int size = 0;
        for(int y = 0; y < present.length; y++) {
            for(int x = 0; x < present[0].length; x++) {
                if(present[y][x]) {
                    size++;
                }
            }
        }
        return size;
    }


}