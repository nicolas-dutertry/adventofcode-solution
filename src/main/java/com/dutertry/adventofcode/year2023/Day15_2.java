package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Day15_2 {
    private static class Lens {
        public final String label;
        public final int focal;
        
        public Lens(String label, int focal) {
            this.label = label;
            this.focal = focal;
        }
    }
    
    public static void main(String[] args) {
        try {
            String s = AdventUtils.getString(15, false);
            String[] array = StringUtils.split(s, ",");
            List<List<Lens>> boxes = new ArrayList<>(256);
            for(int i = 0; i < 256; i++) {
                boxes.add(new ArrayList<>());
            }
            
            for (String step : array) {
                if(step.contains("=")) {
                    String label = StringUtils.substringBefore(step, "=");
                    int focal = Integer.parseInt(StringUtils.substringAfter(step, "="));
                    int boxIdx = getHash(label);
                    boolean found = false;
                    List<Lens> box = boxes.get(boxIdx);
                    for(int i = 0; i < box.size(); i++) {
                        Lens lens = box.get(i);
                        if(lens.label.equals(label)) {
                            box.set(i, new Lens(label, focal));
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        box.add(new Lens(label, focal));
                    }
                    
                } else {
                    String label = StringUtils.substringBefore(step, "-");
                    int boxIdx = getHash(label);
                    List<Lens> box = boxes.get(boxIdx);
                    Iterator<Lens> it = box.iterator();
                    while(it.hasNext()) {
                        Lens lens = it.next();
                        if(lens.label.equals(label)) {
                            it.remove();
                            break;
                        }
                    }
                }
            }
            
            long totalPower = 0;
            for(int i = 0; i < boxes.size(); i++) {
                List<Lens> box = boxes.get(i);
                for (int j = 0; j < box.size(); j++) {
                    Lens lens = box.get(j);
                    long power = (1+i)*(j+1)*lens.focal;
                    totalPower += power;
                }
            }
            System.out.println(totalPower);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static int getHash(String s) {
        int result = 0;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            result += (int)c;
            result = result*17;
            result = result % 256;
        }
        return result;
    }
}
