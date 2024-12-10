package com.dutertry.adventofcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AdventMapTest {
    public static final List<String> LINES = List.of(
            "ABCDE",
            "FGHIJ",
            "KLMNO",
            "PQRST"
    );

    public static final AdventMap MAP = new AdventMap(LINES);

    @Test
    public void testGetXSize() {
        Assert.assertEquals(5, MAP.getXSize());
    }

    @Test
    public void testGetYSize() {
        Assert.assertEquals(4, MAP.getYSize());
    }

    @Test
    public void testIsPointInMap() {
        Assert.assertTrue(MAP.isPointInMap(new Point(0, 0)));
        Assert.assertTrue(MAP.isPointInMap(new Point(4, 3)));
        Assert.assertFalse(MAP.isPointInMap(new Point(5, 3)));
        Assert.assertFalse(MAP.isPointInMap(new Point(4, 4)));
        Assert.assertFalse(MAP.isPointInMap(new Point(-1, 0)));
        Assert.assertFalse(MAP.isPointInMap(new Point(0, -1)));
    }

    @Test
    public void testGetChar() {
        Assert.assertEquals('A', MAP.getChar(0, 0).charValue());
        Assert.assertEquals('E', MAP.getChar(4, 0).charValue());
        Assert.assertEquals('P', MAP.getChar(0, 3).charValue());
        Assert.assertEquals('T', MAP.getChar(4, 3).charValue());
        Assert.assertNull(MAP.getChar(5, 3));
        Assert.assertNull(MAP.getChar(4, 4));
        Assert.assertNull(MAP.getChar(-1, 0));
        Assert.assertNull(MAP.getChar(0, -1));
    }

    @Test
    public void testFind() {
        Assert.assertEquals(new Point(0, 0), MAP.find('A'));
        Assert.assertEquals(new Point(4, 0), MAP.find('E'));
        Assert.assertEquals(new Point(0, 3), MAP.find('P'));
        Assert.assertEquals(new Point(4, 3), MAP.find('T'));
        Assert.assertNull(MAP.find('Z'));
    }

    @Test
    public void testStream() {
        List<Point> points = MAP.stream().toList();
        Assert.assertEquals(20, points.size());
        Assert.assertEquals(new Point(0, 0), points.get(0));
        Assert.assertEquals(new Point(4, 3), points.get(19));
    }

    @Test
    public void testRotateRight() {
        AdventMap rotated = MAP.rotateRight();
        Assert.assertEquals(4, rotated.getXSize());
        Assert.assertEquals(5, rotated.getYSize());
        Assert.assertEquals('P', rotated.getChar(0, 0).charValue());
        Assert.assertEquals('K', rotated.getChar(1, 0).charValue());
        Assert.assertEquals('T', rotated.getChar(0, 4).charValue());
        Assert.assertEquals('A', rotated.getChar(3, 0).charValue());
        Assert.assertEquals('G', rotated.getChar(2, 1).charValue());
    }

    @Test
    public void testRotateLeft() {
        AdventMap rotated = MAP.rotateLeft();
        Assert.assertEquals(4, rotated.getXSize());
        Assert.assertEquals(5, rotated.getYSize());
        Assert.assertEquals('E', rotated.getChar(0, 0).charValue());
        Assert.assertEquals('T', rotated.getChar(3, 0).charValue());
        Assert.assertEquals('A', rotated.getChar(0, 4).charValue());
        Assert.assertEquals('N', rotated.getChar(2, 1).charValue());
    }
}
