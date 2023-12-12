package com.dutertry.adventofcode.year2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class Day13_1 {
    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(13)) {
            String line;
            Set<String> hosts = new HashSet<>();
            Map<String, Integer> scores = new HashMap<>();
            while((line = br.readLine()) != null) {
                String[] array = StringUtils.split(line);
                String host1 = array[0];
                boolean lose = array[2].equals("lose");
                int score = Integer.parseInt(array[3]);
                if(lose) {
                    score = -score;
                }
                String host2 = StringUtils.substringBefore(array[10], ".");
                
                hosts.add(host1);
                hosts.add(host2);
                scores.putIfAbsent(host1 + "," + host2, score);
            }            
            
            Collection<List<String>> permutations = CollectionUtils.permutations(hosts);
            int maxScore = 0;
            for (List<String> perm : permutations) {
                
                int score = 0;
                for(int i = 0; i < perm.size(); i++) {
                    String host = perm.get(i);
                    int left = i-1;
                    if(i == 0) {
                        left = perm.size()-1;
                    }
                    String leftHost = perm.get(left);
                    int right = i+1;
                    if(right == perm.size()) {
                        right = 0;
                    }
                    String rightHost = perm.get(right);
                    
                    score += scores.get(host + "," + leftHost) + scores.get(host + "," + rightHost);
                }
                if(score > maxScore) {
                    maxScore = score;
                }
            }
            
            System.out.println(maxScore);
            
        } catch(IOException e) {
            e.printStackTrace();
        }
	}
    
    
}
