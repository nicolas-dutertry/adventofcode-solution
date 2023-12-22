package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;

public class Day22_1 {
    
    private static class Brick {
        public final int minx;
        public final int miny;
        public int minz;
        public final int maxx;
        public final int maxy;
        public int maxz;
        
        public Brick(int minx, int miny, int minz, int maxx, int maxy, int maxz) {
            Validate.isTrue(minx <= maxx);
            Validate.isTrue(miny <= maxy);
            Validate.isTrue(minz <= maxz);
            this.minx = minx;
            this.miny = miny;
            this.minz = minz;
            this.maxx = maxx;
            this.maxy = maxy;
            this.maxz = maxz;
        }
        
        public void updateMinz(int minz) {
            this.maxz = this.maxz - this.minz + minz;
            this.minz = minz;
        }
        
        public boolean supports(Brick brick) {
            if(maxz != brick.minz -1) {
                return false;
            }
            
            return intersect(minx, maxx, brick.minx, brick.maxx) && intersect(miny, maxy, brick.miny, brick.maxy);
        }

        @Override
        public int hashCode() {
            return Objects.hash(maxx, maxy, maxz, minx, miny, minz);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Brick other = (Brick) obj;
            return maxx == other.maxx && maxy == other.maxy && maxz == other.maxz && minx == other.minx
                    && miny == other.miny && minz == other.minz;
        }
    }
    
    private static final Pattern PATTERN = Pattern.compile("([0-9]+),([0-9]+),([0-9]+)~([0-9]+),([0-9]+),([0-9]+)");
    
    public static void main(String[] args) {
        try {
            List<Brick> bricks = new LinkedList<>();
            List<String> lines = AdventUtils.getLines(22, false);
            int topz = 0;
            for (String line : lines) {
                Matcher matcher = PATTERN.matcher(line);
                if(!matcher.matches()) {
                    throw new RuntimeException("Oups");
                }
                int minx = Integer.parseInt(matcher.group(1));
                int miny = Integer.parseInt(matcher.group(2));
                int minz = Integer.parseInt(matcher.group(3));
                int maxx = Integer.parseInt(matcher.group(4));
                int maxy = Integer.parseInt(matcher.group(5));
                int maxz = Integer.parseInt(matcher.group(6));
                bricks.add(new Brick(minx, miny, minz, maxx, maxy, maxz));
                if(maxz > topz) {
                    topz = maxz;
                }
            }
            
            Map<Integer, List<Brick>> bricksByLevel = new HashMap<>();
            for(int z = 1; z <= topz; z++) {
                bricksByLevel.put(z, new LinkedList<>());
            }
            for (Brick brick : bricks) {
                bricksByLevel.get(brick.maxz).add(brick);
            }
            
            for(int z = 2; z <= topz; z++) {
                List<Brick> levelBricks = bricksByLevel.get(z);
                Iterator<Brick> it = levelBricks.iterator();
                while(it.hasNext()) {
                    Brick brick = it.next();
                    updateLowerLevel(brick, bricksByLevel);
                    if(z != brick.maxz) {
                        it.remove();
                        bricksByLevel.get(brick.maxz).add(brick);
                    }
                }
            }
            
            Map<Brick, Set<Brick>> supportedBricks = new HashMap<>();
            Map<Brick, Set<Brick>> underBricks = new HashMap<>();
            for (Brick brick : bricks) {
                supportedBricks.put(brick, new HashSet<>());
                underBricks.put(brick, new HashSet<>());
            }
            for (Brick brick : bricks) {
                for (Brick brick2 : bricks) {
                    if(brick.supports(brick2)) {
                        supportedBricks.get(brick).add(brick2);
                        underBricks.get(brick2).add(brick);
                    }
                }
            }
            
            int count = 0;
            for (Brick brick : bricks) {
                Set<Brick> supporteds = supportedBricks.get(brick);
                boolean ok = true;
                for (Brick supported : supporteds) {
                    if(underBricks.get(supported).size() < 2) {
                        ok = false;
                        break;
                    }
                }
                if(ok) {
                    count++;
                }
            }
            System.out.println(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static boolean intersect(int min1, int max1, int min2, int max2) {
        return max2 >= min1 && min2 <= max1;
    }
    
    private static void updateLowerLevel(Brick brick, Map<Integer, List<Brick>> bricksByLevel) {
        int startLevel = brick.minz-1;
        for(int level = startLevel; level >= 1; level--) {
            List<Brick> levelBricks = bricksByLevel.get(level);
            for (Brick levelBrick : levelBricks) {
                if(levelBrick.supports(brick)) {
                    return;
                }
            }
            brick.updateMinz(brick.minz-1);
        }
    }
}
