package com.dutertry.adventofcode.year2015;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Day23_1 {
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(23);
            int index = 0;
            Map<String, Integer> register = new HashMap<>();
            register.put("a", 0);
            register.put("b", 0);
            while(index < lines.size()) {
                String line = lines.get(index);
                index = exec(index, line, register);
            }
            System.out.println(register.get("b"));
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private static int exec(int index, String line, Map<String, Integer> register) {
        String[] array = StringUtils.split(line);
        switch(array[0]) {
            case "hlf":
                register.put(array[1], register.get(array[1]) / 2);
                return index+1;
            case "tpl":
                register.put(array[1], register.get(array[1]) * 3);
                return index+1;
            case "inc":
                register.put(array[1], register.get(array[1]) + 1);
                return index+1;
            case "jmp":
                return index + Integer.parseInt(array[1]);
            case "jie": {
                String var = StringUtils.substringBefore(array[1], ",");
                int val = register.get(var);
                if(val % 2 == 0) {
                    return index + Integer.parseInt(array[2]);
                } else {
                    return index+1;
                }
            }
            case "jio": {
                String var = StringUtils.substringBefore(array[1], ",");
                int val = register.get(var);
                if(val == 1) {
                    return index + Integer.parseInt(array[2]);
                } else {
                    return index+1;
                }
            }
            default:
                throw new RuntimeException("Oups");
        }
    }
    
}
