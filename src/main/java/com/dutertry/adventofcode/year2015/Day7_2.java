package com.dutertry.adventofcode.year2015;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Day7_2 {
    public static void main(String[] args) {
	    try {
        	List<String> lines = AdventUtils.getLines(7);
	        int a = getAValue(lines);
	        
	        List<String> lines2 = AdventUtils.getLines(7);
	        for (int i = 0; i < lines2.size(); i++) {
                String line = lines2.get(i);
                if(line.endsWith("-> b")) {
                    lines2.set(i, a + " -> b");
                    break;
                }
            }
	        System.out.println(getAValue(lines2));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
    
    private static int getAValue(List<String> lines) {
        Map<String, Integer> valueMap = new HashMap<>();
        while(true) {
            Iterator<String> it = lines.iterator();
            while(it.hasNext()) {
                String line = it.next();
                int i = line.indexOf("->");
                String var = line.substring(i+2).trim();
                String expression = line.substring(0, i).trim();
                int value = -1;
                
                if(!expression.contains(" ")) {
                    if(canEvaluate(expression, valueMap)) {
                        value = getValue(expression, valueMap);
                    }
                } else if(expression.startsWith("NOT")) {
                    String s = expression.substring(3).trim();
                    if(canEvaluate(s, valueMap)) {
                        int x = getValue(s, valueMap);
                        value = not(x);
                    }
                } else {
                    String[] array = StringUtils.split(expression);
                    String sx = array[0].trim();
                    String operator = array[1].trim();
                    String sy = array[2].trim();
                    
                    if(canEvaluate(sx, valueMap) && canEvaluate(sy, valueMap)) {
                        int x = getValue(array[0].trim(), valueMap);
                        int y = getValue(array[2].trim(), valueMap);
                        
                        switch(operator) {
                        case "AND":
                            value = and(x, y);
                            break;
                        case "OR":
                            value = or(x, y);
                            break;
                        case "LSHIFT":
                            value = lshift(x, y);
                            break;
                        case "RSHIFT":
                            value = rshift(x, y);
                            break;
                        default:
                            throw new RuntimeException("Oups: " + expression);                      
                        }
                    }
                }
                
                if(value != -1) {
                    if(var.equals("a")) {
                        return value;
                    }
                    valueMap.put(var, value);
                    it.remove();
                }
            }
        }
    }
	
	private static boolean canEvaluate(String expression, Map<String, Integer> valueMap) {
        if(StringUtils.isNumeric(expression)) {
            return true;
        } else {
            return valueMap.containsKey(expression);
        }
    }
	
	private static int getValue(String expression, Map<String, Integer> valueMap) {
	    if(StringUtils.isNumeric(expression)) {
	        return Integer.parseInt(expression);
	    } else {
	        return valueMap.get(expression);
	    }
	}
	
	private static int and(int x, int y) {
        return 65535 & (x & y);
    }
    
    private static int or(int x, int y) {
        return 65535 & (x | y);
    }
    
    private static int lshift (int x, int y) {
        return 65535 & (x << y);
    }
    
    private static int rshift (int x, int y) {
        return 65535 & (x >> y);
    }
    
    private static int not(int x) {
        return 65535 & (~x);
    }

}
