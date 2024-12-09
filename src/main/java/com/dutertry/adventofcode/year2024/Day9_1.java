package com.dutertry.adventofcode.year2024;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day9_1 {

    public static void main(String[] args) {
        try {
            String line = AdventUtils.getString(9);
            List<Object> disk = new ArrayList<>();
            List<Integer> freePositions = new ArrayList<>();
            long fileId = 0;
            int pos = 0;
            for(int i = 0; i < line.length(); i++) {
                int val = Integer.parseInt(String.valueOf(line.charAt(i)));
                if(i % 2 == 0) {
                    for(int j = 0; j < val; j++) {
                        disk.add(fileId);
                        pos++;
                    }
                    fileId++;
                } else {
                    for(int j = 0; j < val; j++) {
                        disk.add(".");
                        freePositions.add(pos);
                        pos++;
                    }
                }
            }

            int freePosIndex = 0;
            for(int i = disk.size() - 1; i >= 0; i--) {
                Object val = disk.get(i);
                if(!val.equals(".")) {
                    int free = freePositions.get(freePosIndex);
                    freePosIndex++;
                    if(free >= i) {
                        break;
                    }

                    disk.set(free, disk.get(i));
                    disk.set(i, ".");
                }
            }

            long total = 0;
            for (int i = 0; i < disk.size(); i++) {
                if(disk.get(i).equals(".")) {
                    continue;
                }
                total += (Long)disk.get(i) * i;
            }

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
