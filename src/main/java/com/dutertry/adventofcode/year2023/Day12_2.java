package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class Day12_2 {
    public static class State {
        public final int from;
        public final List<Integer> subBlocks;
        
        public State(int from, List<Integer> subBlocks) {
            this.from = from;
            this.subBlocks = subBlocks;
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, subBlocks);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            State other = (State) obj;
            return from == other.from && Objects.equals(subBlocks, other.subBlocks);
        }
    }
    
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(12);
            long total = 0;
            for (String line : lines) {
                String[] array = StringUtils.split(line);
                
                List<Integer> b1 = Arrays.asList(StringUtils.split(array[1], ","))
                        .stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                List<Integer> blocks = new ArrayList<>();
                for(int i = 0; i < 5; i++) {
                    blocks.addAll(b1);
                }
                
                String s = array[0];
                for(int i = 0; i < 4; i++) {
                    s += "?" + array[0];
                }
                
                long count = countSolutions(blocks, s, 0, new HashMap<>());
                total += count;                
            }
            System.out.println(total);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static long countSolutions(List<Integer> blocks, String s, int offset, Map<State, Long> stateMap) {
        long count = 0;
        int blockSize = blocks.get(0);
        List<Integer> subBlocks = blocks.subList(1, blocks.size());
        List<Integer> positions = getPossiblePositions(blockSize, s, offset, subBlocks);
        if(blocks.size() == 1) {
            for (Integer position : positions) {
                int i = StringUtils.indexOf(s, '#', position+blockSize);
                if(i == -1) {
                    count++;
                }
            }
        } else {
            for (Integer position : positions) {
                int from = position+blockSize+1;
                while(from < s.length() && s.charAt(from) == '.') {
                    from++;
                }
                
                State key = new State(from, subBlocks);
                Long c = stateMap.get(key);
                if(c == null) {
                    c = countSolutions(subBlocks, s, from, stateMap);
                    stateMap.put(key, c);
                }
                
                count += c;
            }
        }
        return count;
    }
    
    private static List<Integer> getPossiblePositions(int blockSize, String s, int offset, List<Integer> subBlocks) {
        List<Integer> positions = new LinkedList<>();
        for(int i = offset; i <= s.length()-blockSize; i++) {
            char firstChar = s.charAt(i);
            boolean ok = true;
            for(int j = i; j < i+blockSize; j++) {
                char c = s.charAt(j);
                if(c == '.') {
                    ok = false;
                    break;
                }
            }
            if(ok && (i == 0 || s.charAt(i-1) != '#') && (i+blockSize == s.length() || s.charAt(i+blockSize) != '#')) {
                positions.add(i);
            }
            if(firstChar == '#') {
                break;
            }
        }
        return positions;
    }

}
