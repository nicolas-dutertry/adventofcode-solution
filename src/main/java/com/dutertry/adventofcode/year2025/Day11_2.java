package com.dutertry.adventofcode.year2025;

import com.dutertry.adventofcode.PathFinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11_2 {
    private record State(String device, boolean fft, boolean dac) {}

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

            State startState = new State("svr", false, false);
            State endState = new State("out", true, true);
            long count = PathFinder.countPaths(startState, endState, state -> {
                String device = state.device;
                List<String> outputs = deviceMap.getOrDefault(device, Collections.emptyList());
                List<State> nextStates = new ArrayList<>();
                for(String output : outputs) {
                    boolean fft = state.fft;
                    boolean dac = state.dac;
                    if(output.equals("fft")) {
                        fft = true;
                    } else if(output.equals("dac")) {
                        dac = true;
                    }
                    nextStates.add(new State(output, fft, dac));
                }
                return nextStates;
            });

            System.out.println(count);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
