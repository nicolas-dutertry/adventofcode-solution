package com.dutertry.adventofcode.year2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day17_2_bis {
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
            Day17_2_bis::adv,
            Day17_2_bis::bxl,
            Day17_2_bis::bst,
            Day17_2_bis::jnz,
            Day17_2_bis::bxc,
            Day17_2_bis::out,
            Day17_2_bis::bdv,
            Day17_2_bis::cdv);

    private static record SeriesSearchResult(long finalRegisterA, long first, List<Long> series) {}

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(17)) {
            List<String> lines = AdventUtils.getLines(17);
            long registerB = Long.parseLong(lines.get(1).split(": ")[1]);
            long registerC = Long.parseLong(lines.get(2).split(": ")[1]);
            String program = lines.get(4).split(": ")[1];

            Set<Long> lastResult = Collections.singleton(0L);
            int depth = 1;
            while(depth <= 16) {
                lastResult = search(registerB, registerC, program, lastResult, depth);
                depth++;
            }
            long result = lastResult.stream().min(Long::compareTo).get();
            System.out.println(result);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<Long> search(long registerB, long registerC, String program, Set<Long> lastResult, int depth) {
        long finalRegisterA = -1;

        int len = program.length();
        String searchString = program.substring(program.length() + 1 - 2*depth);

        Set<Long> aValues = new HashSet<>();
        for(int i = 0; i < 8; i++) {
            for (long lastA : lastResult) {
                long a = lastA*8 + i;
                aValues.add(a);
            }
        }

        Set<Long> result = new HashSet<>();
        for(long registerA : aValues) {
            boolean foundStart = false;

            State state = new State(registerA, registerB, registerC, 0,
                    program);

            while(!state.isOver()) {
                int op = state.getOperation();
                int input = state.getInput();

                Operation operation = OPERATIONS.get(op);
                long currentPointer = state.pointer;
                operation.operate(input, state);

                if(state.pointer == currentPointer) {
                    state.pointer += 2;
                }
            }

            if(state.output.toString().equals(searchString)) {
                result.add(registerA);
            }
        }

        return result;
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
}
