package com.dutertry.adventofcode.year2025;

import com.dutertry.adventofcode.PathFinder;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10_1 {
    private record Light(int index) {
        public void toggle(char[] chars) {
                chars[index] = chars[index] == '#' ? '.' : '#';
            }
    }

    private record Button(List<Integer> lightIndexes) {}

    private record Machine(String finalState, List<Light> lights, List<Button> buttons) {
        public String pressButton(int buttonIndex, String state) {
            char[] chars = state.toCharArray();
            Button button = buttons.get(buttonIndex);
            for(int lightIndex : button.lightIndexes) {
                lights.get(lightIndex).toggle(chars);
            }
            return new String(chars);
        }
    }

    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(10);
            List<Machine> machines = new ArrayList<>();
            for(String line : lines) {
                String[] parts = StringUtils.split(line, " ");
                String finalState = parts[0].substring(1, parts[0].length() - 1);

                List<Light> lights = new ArrayList<>();
                for(int i = 0; i < finalState.length(); i++) {
                    lights.add(new Light(i));
                }
                List<Button> buttons = new ArrayList<>();
                for(int i = 1; i < parts.length-1; i++) {
                    String buttonPart = parts[i].substring(1, parts[i].length() - 1);
                    List<Integer> lightIndexes = new ArrayList<>();
                    for(String indexStr : StringUtils.split(buttonPart, ",")) {
                        lightIndexes.add(Integer.parseInt(indexStr));
                    }
                    buttons.add(new Button(lightIndexes));
                }
                machines.add(new Machine(finalState, lights, buttons));
            }

            long total = 0;
            for(Machine machine : machines) {
                long best = findBest(machine);
                total += best;
            }
            System.out.println(total);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static long findBest(Machine machine) {
        String startState = ".".repeat(machine.lights.size());

        PathFinder.WeightedPath<String> bestPath = PathFinder.findAnyBestPath(
                startState,
                machine.finalState(),
                state -> {
                    Map<String, Long> nextPaths = new HashMap<>();
                    for(int i = 0; i < machine.buttons.size(); i++) {
                        String newState = machine.pressButton(i, state);
                        nextPaths.put(newState, 1L);
                    }
                    return nextPaths;
                }
        );
        return bestPath.weight();
    }
}
