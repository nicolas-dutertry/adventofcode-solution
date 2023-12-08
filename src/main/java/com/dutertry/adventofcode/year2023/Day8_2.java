package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Day8_2 {
    private static final Pattern NODE_PATTERN = Pattern.compile("([A-Z0-9]+)\\s*=\\s*\\(([A-Z0-9]+),\s*([A-Z0-9]+)\\)");
    
    private static class Node {
        public final String left;
        public final String right;
        
        public Node(String left, String right) {
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        
        try(BufferedReader br = AdventUtils.getBufferedReader(8)) {
            String directions = null;
            Map<String, Node> nodeMap = new HashMap<>();
            String line;
            while((line = br.readLine()) != null ) {
                if(StringUtils.isBlank(line)) {
                    continue;
                }
                if(directions == null) {
                    directions = line;
                    continue;
                }
                
                Matcher matcher = NODE_PATTERN.matcher(line);
                if(matcher.matches()) {
                    String nodeName = matcher.group(1);
                    String left = matcher.group(2);
                    String right = matcher.group(3);
                    nodeMap.put(nodeName, new Node(left, right));
                } else {
                    throw new RuntimeException("Oups");
                }
            }
            
            List<String> nodeNames = new ArrayList<>();
            for (String nodeName : nodeMap.keySet()) {
                if(nodeName.endsWith("A")) {
                    nodeNames.add(nodeName);
                }
            }
            
            List<Long> steps = new LinkedList<>();
            for(int i = 0; i < nodeNames.size(); i++) {
                String nodeName = nodeNames.get(i);
                
                long step = 1;
                int directionIndex = 0;
                while(true) {
                    char direction = directions.charAt(directionIndex);
                    
                    Node node = nodeMap.get(nodeName);
                    if(direction == 'L') {
                        nodeName = node.left;
                    } else {
                        nodeName = node.right;
                    }
                    
                    if(nodeName.endsWith("Z")) {
                        break;
                    }
                    
                    step++;
                    directionIndex++;
                    if(directionIndex >= directions.length()) {
                        directionIndex = 0;
                    }
                }
                steps.add(step);
            }
            System.out.println(lcm(steps));
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    public static long lcm(List<Long> numbers) {
        long result = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {
            result = lcm(result, numbers.get(i));
        }

        return result;
    }

}
