package com.dutertry.adventofcode.year2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day18_2 {
    private static final char UP = 'U';
    private static final char DOWN = 'D';
    private static final char RIGHT = 'R';
    private static final char LEFT = 'L';
    
    private static Pattern PATTERN = Pattern.compile("([A-Z]) ([0-9]+) \\(#([0-9a-f]{5})([0-3])\\)");
    
    
    private static class Point {
        public final int x;
        public final int y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public Point next(char direction, int step) {
            int nextx = x;
            int nexty = y;
            switch (direction) {
            case UP:
                nexty-=step;
                break;
            case DOWN:
                nexty+=step;
                break;
            case LEFT:
                nextx-=step;
                break;
            case RIGHT:
                nextx+=step;
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + direction);
            }
            return new Point(nextx, nexty);
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
            Point other = (Point) obj;
            return x == other.x && y == other.y;
        }
    }
    
    private static class Line {
        public final Point p1;
        public final Point p2;
        
        public Line(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
        
        public boolean isHorizontal() {
            return p1.y == p2.y;
        }
    }
    
    private static class Rectangle {
        public final Point topleft;
        public final Point bottomdown;
        public final boolean inside;
        
        public Rectangle(Point topleft, Point bottomdown, boolean inside) {
            this.topleft = topleft;
            this.bottomdown = bottomdown;
            this.inside = inside;
        }
    }
    
    public static void main(String[] args) {
        try {
            List<String> lines = AdventUtils.getLines(18, false);
            Set<Line> edgeLines = new HashSet<>();
            Point currentPoint = new Point(0,0);
            for (String line : lines) {
                Matcher matcher = PATTERN.matcher(line.trim());
                if(matcher.matches()) {
                    int d = Integer.parseInt(matcher.group(4));
                    char direction;
                    switch(d) {
                    case 0:
                        direction = RIGHT;
                        break;
                    case 1:
                        direction = DOWN;
                        break;
                    case 2:
                        direction = LEFT;
                        break;
                    case 3:
                        direction = UP;
                        break;
                    default:
                        throw new RuntimeException("Oups");
                    }
                    int step = Integer.parseInt(matcher.group(3), 16);
                    
                    Point p1 = currentPoint;
                    Point p2 = currentPoint.next(direction, step);
                    currentPoint = p2;
                    edgeLines.add(new Line(p1, p2));
                } else {
                    throw new RuntimeException("Oups");
                }
            }
            
            Set<Integer> horizontalCuts = new TreeSet<>();
            Set<Integer> verticalCuts = new TreeSet<>();
            
            for (Line line : edgeLines) {
                if(line.isHorizontal()) {
                    horizontalCuts.add(line.p1.y);
                } else {
                    verticalCuts.add(line.p1.x);
                }
            }
            
            List<Integer> horizontalCutList = new ArrayList<>(horizontalCuts);
            List<Integer> verticalCutList = new ArrayList<>(verticalCuts);
            
            
            List<List<Rectangle>> rectangleTable = new ArrayList<>();
            for (int i = 0; i < verticalCutList.size()-1; i++) {
                List<Rectangle> rectangles = new ArrayList<>();
                rectangleTable.add(rectangles);
                int x1 = verticalCutList.get(i);
                int x2 = verticalCutList.get(i+1);
                for(int j = 0; j < horizontalCutList.size()-1; j++) {
                    int y1 = horizontalCutList.get(j);
                    int y2 = horizontalCutList.get(j+1);
                    double insidex = x1+0.5;
                    double insidey = y1+0.5;
                    int intersections = 0;
                    for (Line line : edgeLines) {
                        if(line.isHorizontal() && line.p1.y < insidey) {
                            if(line.p1.x < insidex && line.p2.x > insidex) {
                                intersections++;
                            } else if(line.p1.x > insidex && line.p2.x < insidex) {
                                intersections++;
                            }
                        }
                    }
                    boolean inside = false;
                    if(intersections % 2 == 1) {
                        inside = true;
                    }
                    rectangles.add(new Rectangle(new Point(x1, y1), new Point(x2, y2), inside));
                }
            }
            
            long totalarea = 0;
            
            for (int i = 0; i < rectangleTable.size(); i++) {
                List<Rectangle> rectangles = rectangleTable.get(i);
                for(int j = 0; j < rectangles.size(); j++) {
                    Rectangle rectangle = rectangles.get(j);
                    if(!rectangle.inside) {
                        continue;
                    }
                    long l1 = rectangle.bottomdown.x-rectangle.topleft.x+1;
                    long l2 = rectangle.bottomdown.y-rectangle.topleft.y+1;
                    long alreadyCounted = 0;
                    boolean noLeftOrUp = false;
                    if(j > 0) {
                        Rectangle upRectangle = rectangles.get(j-1);
                        if(upRectangle.inside) {
                            l2--;
                            noLeftOrUp = true;
                        }
                    }
                    if(i > 0) {
                        Rectangle leftRectangle = rectangleTable.get(i-1).get(j);
                        if(leftRectangle.inside) {
                            l1--;
                            noLeftOrUp = true;
                        } else if(j < rectangles.size()-1) {
                            Rectangle downleftRectangle = rectangleTable.get(i-1).get(j+1);
                            if(downleftRectangle.inside) {
                                alreadyCounted++;
                            }
                        }
                    }
                    if( i > 0 && j > 0 && !noLeftOrUp) {
                        Rectangle upleftRectangle = rectangleTable.get(i-1).get(j-1);
                        if(upleftRectangle.inside) {
                            alreadyCounted++;
                        }
                    }
                        
                    long area = l1*l2-alreadyCounted;
                    totalarea += area;
                }
            }
            
            System.out.println(totalarea);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
