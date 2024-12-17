package com.dutertry.adventofcode.year2024;

import java.io.IOException;
import java.util.List;

public class Day17_1 {
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

    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(17);
            long registerA = Long.parseLong(lines.get(0).split(": ")[1]);
            long registerB = Long.parseLong(lines.get(1).split(": ")[1]);
            long registerC = Long.parseLong(lines.get(2).split(": ")[1]);
            String program = lines.get(4).split(": ")[1];


            List<Operation> operations = List.of(
                Day17_1::adv,
                Day17_1::bxl,
                Day17_1::bst,
                Day17_1::jnz,
                Day17_1::bxc,
                Day17_1::out,
                Day17_1::bdv,
                Day17_1::cdv);

            State state = new State(registerA, registerB, registerC, 0,
                    program);


            while(!state.isOver()) {
                int op = state.getOperation();
                int input = state.getInput();

                Operation operation = operations.get(op);
                long currentPointer = state.pointer;
                operation.operate(input, state);

                if(state.pointer == currentPointer) {
                    state.pointer += 2;
                }
            }

            System.out.println(state.output.toString());
        } catch(IOException e) {
            e.printStackTrace();
        }
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

    private static void adv(long input, State state) {
        state.registerA = state.registerA >> combo(input, state);
    }

    private static void bxl(long input, State state) {
        state.registerB = state.registerB ^ input;
    }

    private static void bst(long input, State state) {
        state.registerB = combo(input, state) % 8;
    }

    private static void jnz(long input, State state) {
        if(state.registerA != 0) {
            state.pointer = (int)input;
        }
    }

    private static void bxc(long input, State state) {
        state.registerB = state.registerB ^ state.registerC;
    }

    private static void out(long input, State state) {
        state.appendOutput(combo(input, state) % 8);
    }

    private static void bdv(long input, State state) {
        state.registerB = state.registerA >> combo(input, state);
    }

    private static void cdv(long input, State state) {
        state.registerC = state.registerA >> combo(input, state);
    }
}
