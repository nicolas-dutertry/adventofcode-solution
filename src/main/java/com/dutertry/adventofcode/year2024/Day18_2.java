package com.dutertry.adventofcode.year2024;

import com.dutertry.adventofcode.AdventMap;
import com.dutertry.adventofcode.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day18_2 {
    private static final int MAX = 70;

    public static void main(String[] args) {
        try(BufferedReader br = AdventUtils.getBufferedReader(18)) {
            long total = 0;

            List<Point> bytes = new LinkedList<>();
            String line;
            while((line = br.readLine()) != null ) {
                String[] array = line.split(",");
                int x = Integer.parseInt(array[0]);
                int y = Integer.parseInt(array[1]);
                bytes.add(new Point(x, y));
            }

            List<String> lines = new ArrayList<>();
            for(int i = 0; i <= MAX; i++) {
                lines.add(".".repeat(MAX + 1));
            }

            int bytesCount = 1024;
            List<Point> lastPath = null;
            while(true) {
                if(lastPath != null && !lastPath.contains(bytes.get(bytesCount-1))) {
                    bytesCount++;
                    continue;
                }

                AdventMap map = new AdventMap(lines);

                for (int i = 0; i < bytesCount; i++) {
                    Point p = bytes.get(i);
                    map.setChar(p, '#');
                }

                Point startPoint = new Point(0, 0);
                Point endPoint = new Point(MAX, MAX);

                lastPath = map.getBestPath(startPoint, endPoint);
                if(lastPath == null) {
                    Point p = bytes.get(bytesCount-1);
                    System.out.println(p.x() + "," + p.y());
                    return;
                }
                bytesCount++;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
