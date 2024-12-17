package com.dutertry.adventofcode.year2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day17_2 {
    private static class State {
        public long registerA;
        public long registerB;
        public long registerC;
        public long pointer;
        public String program;
        public StringBuilder output;

        public State(long registerA, long registerB, long registerC, long pointer, String program) {
            this.registerA = registerA;
            this.registerB = registerB;
            this.registerC = registerC;
            this.pointer = pointer;
            this.program = program;
            this.output = new StringBuilder();
        }

        public int getOperation() {
            return Integer.parseInt(String.valueOf(program.charAt((int)(2*pointer))));
        }

        public int getInput() {
            return Integer.parseInt(String.valueOf(program.charAt((int)(2*pointer + 2))));
        }

        public void appendOutput(long value) {
            if(!output.isEmpty()) {
                output.append(",");
            }
            output.append(value);
        }

        public boolean isOver() {
            return (2*pointer) >= program.length();
        }
    }

    private interface Operation {
        public void operate(long input, State state);
    }

    private static final List<Operation> OPERATIONS = List.of(
            Day17_2::adv,
            Day17_2::bxl,
            Day17_2::bst,
            Day17_2::jnz,
            Day17_2::bxc,
            Day17_2::out,
            Day17_2::bdv,
            Day17_2::cdv);

    private static record SeriesSearchResult(long finalRegisterA, long first, List<Long> series) {}

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(17)) {
            List<String> lines = AdventUtils.getLines(17);
            long registerB = Long.parseLong(lines.get(1).split(": ")[1]);
            long registerC = Long.parseLong(lines.get(2).split(": ")[1]);
            String program = lines.get(4).split(": ")[1];

            SeriesSearchResult searchResult = new SeriesSearchResult(-1, 1, null);
            int depth = 1;
            while(searchResult.finalRegisterA == -1) {
                searchResult = searchSeries(registerB, registerC, program, searchResult, depth);
                depth++;
            }
            System.out.println(searchResult.finalRegisterA);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static SeriesSearchResult searchSeries(long registerB, long registerC, String program, SeriesSearchResult lastResult, int depth) {
        long finalRegisterA = -1;

        String searchString = program.substring(0, depth * 2 - 1);

        long registerA = lastResult.first;
        int index = 0;
        long lastA = 0L;
        int maxsize = 300;
        List<Long> series = null;
        List<Long> foundStartList = new LinkedList<>();
        main: while(true) {
            boolean foundStart = false;

            State state = new State(registerA, registerB, registerC, 0,
                    program);

            while (!state.isOver()) {
                int op = state.getOperation();
                int input = state.getInput();

                Operation operation = OPERATIONS.get(op);
                long currentPointer = state.pointer;
                operation.operate(input, state);

                if(state.pointer == currentPointer) {
                    state.pointer += 2;
                }

                String outputStr = state.output.toString();
                if(!outputStr.isEmpty()) {
                    if (outputStr.equals(state.program)) {
                        finalRegisterA = registerA;
                        break main;
                    }

                    if(!foundStart && outputStr.startsWith(searchString)) {
                        foundStart = true;
                        long diff = registerA - lastA;
                        foundStartList.add(diff);
                        lastA = registerA;
                    }

                    if (!state.program.startsWith(outputStr)) {
                        break;
                    }
                }
            }

            if(foundStartList.size() >= maxsize) {
                series = identifySeries(foundStartList.subList(1, foundStartList.size()));
                if(series != null) {
                    break;
                }
                maxsize = maxsize * 2;
            }

            if(lastResult.series == null) {
                registerA++;
            } else {
                registerA += lastResult.series.get(index % lastResult.series.size());
            }
            index++;
        }

        return new SeriesSearchResult(finalRegisterA, foundStartList.get(0), series);
    }

    private static long combo(long i, State state) {
        if(i >= 0 && i <= 3) {
            return i;
        }
        if(i == 4) {
            return state.registerA;
        }
        if(i == 5) {
            return state.registerB;
        }
        if(i == 6) {
            return state.registerC;
        }
        throw new IllegalArgumentException("Invalid value for i: " + i);
    }

    // 0
    private static void adv(long input, State state) {
        state.registerA = state.registerA >> combo(input, state);
    }

    // 1
    private static void bxl(long input, State state) {
        state.registerB = state.registerB ^ input;
    }

    // 2
    private static void bst(long input, State state) {
        state.registerB = combo(input, state) % 8;
    }

    // 3
    private static void jnz(long input, State state) {
        if(state.registerA != 0) {
            state.pointer = (int)input;
        }
    }

    // 4
    private static void bxc(long input, State state) {
        state.registerB = state.registerB ^ state.registerC;
    }

    // 5
    private static void out(long input, State state) {
        state.appendOutput(combo(input, state) % 8);
    }

    // 6
    private static void bdv(long input, State state) {
        state.registerB = state.registerA >> combo(input, state);
    }

    // 7
    private static void cdv(long input, State state) {
        state.registerC = state.registerA >> combo(input, state);
    }

    private static List<Long> identifySeries(List<Long> list) {
        for(int size = 1; size < list.size() / 10; size++) {
            List<Long> series = list.subList(0, size);
            int n = 1;
            boolean found = true;
            while((n + 1) * size <= list.size()) {
                List<Long> series2 = list.subList(n * size, (n + 1) * size);
                if (!series.equals(series2)) {
                    found = false;
                    break;
                }
                n++;
            }

            if(found) {
                return series;
            }
        }

        return null;
    }
}
