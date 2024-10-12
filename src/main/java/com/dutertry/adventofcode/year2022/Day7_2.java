package com.dutertry.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Day7_2 {
    public static void main(String[] args) {

        Map<Path, Map<String, Integer>> fileMap = new HashMap<>();
        Map<Path, Set<Path>> pathMap = new HashMap<>();
        Set<Path> allPaths = new TreeSet<>();
        Path currentPath = Path.of("/");
        try(BufferedReader br = AdventUtils.getBufferedReader(7)) {
            String line;
            while((line = br.readLine()) != null) {
                if(line.startsWith("$ cd ")) {
                    String name = line.substring(5);
                    if(name.equals("..")) {
                        currentPath = currentPath.getParent();
                    } else if(name.equals("/")) {
                        currentPath = Path.of("/");
                    } else {
                        currentPath = currentPath.resolve(name);
                    }
                    allPaths.add(currentPath);
                } else if(!line.startsWith("$")) {
                    if(line.startsWith("dir")) {
                        Path path = currentPath.resolve(line.substring(4));
                        Set<Path> subPaths = pathMap.computeIfAbsent(currentPath, k -> new HashSet<>());
                        subPaths.add(path);
                    } else {
                        int i = line.indexOf(" ");
                        int size = Integer.parseInt(line.substring(0, i));
                        String name = line.substring(i+1);
                        Map<String, Integer> files = fileMap.computeIfAbsent(currentPath, k -> new HashMap<>());
                        files.put(name, size);
                    }
                }
            }

            int totalSize = getSize(Path.of("/"), fileMap, pathMap);
            int overspace = totalSize - 40_000_000;
            int min = Integer.MAX_VALUE;
            for (Path path : allPaths) {
                int size = getSize(path, fileMap, pathMap);
                if(size >= overspace && size < min) {
                    min = size;
                }
            }
            System.out.println(min);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static int getSize(Path path, Map<Path, Map<String, Integer>> fileMap, Map<Path, Set<Path>> pathMap) {
        int size = 0;
        Map<String, Integer> files = fileMap.get(path);
        if(files != null) {
            for(Integer i : files.values()) {
                size += i;
            }
        }
        Set<Path> subPaths = pathMap.get(path);
        if(subPaths != null) {
            for(Path p : subPaths) {
                size += getSize(p, fileMap, pathMap);
            }
        }
        return size;
    }
}
