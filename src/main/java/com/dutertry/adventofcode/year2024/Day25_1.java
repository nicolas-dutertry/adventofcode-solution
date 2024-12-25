package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.AdventMap;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day25_1 {
    private record Lock(List<Integer> columns) {}
    private record Key(List<Integer> columns) {}

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(25)) {
            long total = 0;

            String line;
            List<AdventMap> maps = new ArrayList<>();
            List<String> currentLines = new LinkedList<>();
            while((line = br.readLine()) != null ) {
                if(StringUtils.isEmpty(line)) {
                    maps.add(new AdventMap(currentLines));
                    currentLines = new LinkedList<>();
                } else {
                    currentLines.add(line);
                }
            }
            if(!currentLines.isEmpty()) {
                maps.add(new AdventMap(currentLines));
            }

            List<Lock> locks = new ArrayList<>();
            List<Key> keys = new ArrayList<>();

            for(AdventMap map : maps) {
                if(map.getChar(0, 0) == '#') {
                    Lock lock = new Lock(getColumns(map));
                    locks.add(lock);
                } else {
                    Key key = new Key(getColumns(map));
                    keys.add(key);
                }
            }

            for(Lock lock : locks) {
                for(Key key : keys) {
                    if(fit(key, lock)) {
                        total++;
                    }
                }
            }

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> getColumns(AdventMap map) {
        List<Integer> columns = new ArrayList<>();
        for(int x = 0; x < map.getXSize(); x++) {
            int count = 0;
            for(int y = 0; y < map.getYSize(); y++) {
                if(map.getChar(x, y) == '#') {
                    count++;
                }
            }
            columns.add(count);
        }
        return columns;
    }

    private static boolean fit(Key key, Lock lock) {
        for(int i = 0; i < key.columns().size(); i++) {
            int keyValue = key.columns().get(i);
            int lockValue = lock.columns().get(i);
            if(keyValue + lockValue > 7) {
                return false;
            }
        }
        return true;
    }
}
