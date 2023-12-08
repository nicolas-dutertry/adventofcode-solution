package com.dutertry.adventofcode.year2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Day8_1 {
    private static final Pattern NODE_PATTERN = Pattern.compile("([A-Z]+)\\s*=\\s*\\(([A-Z]+),\s*([A-Z]+)\\)");
    
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
            
            String nodeName = "AAA";
            int step = 1;
            int directionIndex = 0;
            while(true) {
                char direction = directions.charAt(directionIndex);
                Node node = nodeMap.get(nodeName);
                if(direction == 'L') {
                    nodeName = node.left;
                } else {
                    nodeName = node.right;
                }
                if(nodeName.equals("ZZZ")) {
                    break;
                }
                
                step++;
                directionIndex++;
                if(directionIndex >= directions.length()) {
                    directionIndex = 0;
                }                
            }
            
            System.out.println(step);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
        

}
