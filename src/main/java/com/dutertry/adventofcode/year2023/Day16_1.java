package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day16_1 {
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String UP = "up";
    private static final String DOWN = "down";
    
    private static class Beam {
        public final int x;
        public final int y;
        public final String direction;
        
        public Beam(int x, int y, String direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, x, y);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Beam other = (Beam) obj;
            return Objects.equals(direction, other.direction) && x == other.x && y == other.y;
        }
    }
    
    private static class Tile {
        public final int x;
        public final int y;
        
        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Tile other = (Tile) obj;
            return x == other.x && y == other.y;
        }        
        
    }
    
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(16);
            int maxx = lines.get(0).length()-1;
            int maxy = lines.size()-1;
            
            Set<Tile> energizedTiles = new HashSet<>();
            Set<Beam> beamSet = new HashSet<>();
            List<Beam> beams = new LinkedList<>();
            beams.add(new Beam(-1, 0, RIGHT));
            
            while(!beams.isEmpty()) {
                List<Beam> nextBeams = new LinkedList<>();
                for (Beam beam : beams) {
                    int nextx = beam.x;
                    int nexty = beam.y;
                    switch(beam.direction) {
                    case LEFT:
                        nextx--;
                        break;
                    case RIGHT:
                        nextx++;
                        break;
                    case UP:
                        nexty--;
                        break;
                    case DOWN:
                        nexty++;
                        break;
                    default:
                        throw new RuntimeException("Oups");
                    }
                    if(nextx > maxx || nextx < 0 || nexty > maxy || nexty < 0) {
                        continue;
                    }
                    
                    energizedTiles.add(new Tile(nextx, nexty));
                    
                    char tile = lines.get(nexty).charAt(nextx);
                    if(tile == '.') {
                        nextBeams.add(new Beam(nextx, nexty, beam.direction));
                    } else if(tile == '/') {
                        switch(beam.direction) {
                        case LEFT:
                            nextBeams.add(new Beam(nextx, nexty, DOWN));
                            break;
                        case RIGHT:
                            nextBeams.add(new Beam(nextx, nexty, UP));
                            break;
                        case UP:
                            nextBeams.add(new Beam(nextx, nexty, RIGHT));
                            break;
                        case DOWN:
                            nextBeams.add(new Beam(nextx, nexty, LEFT));
                            break;
                        default:
                            throw new RuntimeException("Oups");
                        }
                    } else if(tile == '\\') {
                        switch(beam.direction) {
                        case LEFT:
                            nextBeams.add(new Beam(nextx, nexty, UP));
                            break;
                        case RIGHT:
                            nextBeams.add(new Beam(nextx, nexty, DOWN));
                            break;
                        case UP:
                            nextBeams.add(new Beam(nextx, nexty, LEFT));
                            break;
                        case DOWN:
                            nextBeams.add(new Beam(nextx, nexty, RIGHT));
                            break;
                        default:
                            throw new RuntimeException("Oups");
                        }
                    } else if(tile == '|') {
                        switch(beam.direction) {
                        case LEFT:
                            nextBeams.add(new Beam(nextx, nexty, UP));
                            nextBeams.add(new Beam(nextx, nexty, DOWN));
                            break;
                        case RIGHT:
                            nextBeams.add(new Beam(nextx, nexty, DOWN));
                            nextBeams.add(new Beam(nextx, nexty, UP));
                            break;
                        case UP:
                            nextBeams.add(new Beam(nextx, nexty, beam.direction));
                            break;
                        case DOWN:
                            nextBeams.add(new Beam(nextx, nexty, beam.direction));
                            break;
                        default:
                            throw new RuntimeException("Oups");
                        }
                    } else if(tile == '-') {
                        switch(beam.direction) {
                        case LEFT:
                            nextBeams.add(new Beam(nextx, nexty, beam.direction));
                            break;
                        case RIGHT:
                            nextBeams.add(new Beam(nextx, nexty, beam.direction));
                            break;
                        case UP:
                            nextBeams.add(new Beam(nextx, nexty, LEFT));
                            nextBeams.add(new Beam(nextx, nexty, RIGHT));
                            break;
                        case DOWN:
                            nextBeams.add(new Beam(nextx, nexty, LEFT));
                            nextBeams.add(new Beam(nextx, nexty, RIGHT));
                            break;
                        default:
                            throw new RuntimeException("Oups");
                        }
                    }
                }
                Iterator<Beam> it = nextBeams.iterator();
                while(it.hasNext()) {
                    Beam beam = it.next();
                    if(beamSet.contains(beam)) {
                        it.remove();
                    } else {
                        beamSet.add(beam);
                    }
                }
                beams = nextBeams;
            }
            System.out.println(energizedTiles.size());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
