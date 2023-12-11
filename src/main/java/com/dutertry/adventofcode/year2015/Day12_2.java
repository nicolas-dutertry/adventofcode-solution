package com.dutertry.adventofcode.year2015;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day12_2 {
    private static class Node {
        public final Node parent;
        public final List<Node> children = new LinkedList<>();
        public int sum;
        public boolean red;
        
        public Node(Node parent) {
            this.parent = parent;
            if(parent != null) {
                parent.children.add(this);
            }
        }
        
    }
    
    public static void main(String[] args) {
        try {
            String s = AdventUtils.getInputAsString(12);
            Node root = new Node(null);
            Node currentNode = root;
            String currentNumber = "";
            
            for(int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if(c == '-' && currentNumber.length() == 0) {
                    currentNumber = "-";
                } else if(c >= '0' && c <= '9') {
                    currentNumber += c;
                } else {
                    if(!currentNumber.isEmpty() && !currentNumber.equals("-")) {
                        currentNode.sum += Integer.parseInt(currentNumber);
                    }
                    currentNumber = "";
                    
                    if(c == '{') {
                        currentNode = new Node(currentNode);
                    } else if(c == '}') {
                        currentNode = currentNode.parent;
                    } else if(i > 6 && c == '"' && s.substring(i-5, i).equals(":\"red")) {
                        currentNode.red = true;
                    }
                }
            }
            int sum = getSum(root);
            
            System.out.println(sum);
            
        } catch(IOException e) {
            e.printStackTrace();
        }
	}
    
    private static int getSum(Node node) {
        int sum = node.sum;
        for (Node child : node.children) {
            if(!child.red) {
                sum += getSum(child);
            }
        }
        return sum;
    }
    
    
}
