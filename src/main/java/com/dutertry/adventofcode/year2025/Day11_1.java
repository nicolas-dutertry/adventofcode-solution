package com.dutertry.adventofcode.year2025;

import com.dutertry.adventofcode.PathFinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11_1 {

    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(11);
            Map<String, List<String>> deviceMap = new HashMap<>();
            for(String line : lines) {
                String[] parts = line.split(":");
                String device = parts[0].trim();
                String instruction = parts[1].trim();
                parts = instruction.split(" ");
                List<String> outputs = new ArrayList<>();
                for(String part : parts) {
                    outputs.add(part.trim());
                }
                deviceMap.put(device, outputs);
            }

            long count = PathFinder.countPaths("you", "out", state -> {
                return deviceMap.getOrDefault(state, List.of());
            });
            System.out.println(count);


        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
