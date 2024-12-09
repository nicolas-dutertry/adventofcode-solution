package com.dutertry.adventofcode.year2024;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Day9_2 {
    private static class AdventFile {
        public long position;
        public long id;
        public long size;

        public AdventFile(long position, long id, long size) {
            this.position = position;
            this.id = id;
            this.size = size;
        }

        public long getChecksum() {
            return id * (size*position + (size)*(size-1)/2);
        }
    }

    private static class FreeSpace {
        public long position;
        public long size;

        FreeSpace(long position, long size) {
            this.position = position;
            this.size = size;
        }
    }

    public static void main(String[] args) {
        try {
            String line = AdventUtils.getString(9);

            List<AdventFile> adventFiles = new LinkedList<>();
            List<FreeSpace> freeSpaces = new LinkedList<>();

            int fileId = 0;
            int pos = 0;
            for(int i = 0; i < line.length(); i++) {
                int val = Integer.parseInt(String.valueOf(line.charAt(i)));
                if(i % 2 == 0) {
                    adventFiles.add(new AdventFile(pos, fileId, val));
                    fileId++;
                } else {
                    if(val > 0) {
                        freeSpaces.add(new FreeSpace(pos, val));
                    }
                }
                pos += val;
            }

            long total = 0;
            for(int i = adventFiles.size() - 1; i >= 0; i--) {
                AdventFile adventFile = adventFiles.get(i);
                Iterator<FreeSpace> iterator = freeSpaces.iterator();
                while (iterator.hasNext()) {
                    FreeSpace freeSpace = iterator.next();
                    if(freeSpace.position > adventFile.position) {
                        break;
                    }
                    if(freeSpace.size >= adventFile.size) {
                        adventFile.position = freeSpace.position;
                        freeSpace.position += adventFile.size;
                        freeSpace.size -= adventFile.size;
                        if(freeSpace.size <= 0) {
                            iterator.remove();
                        }
                        break;
                    }
                }
                total += adventFile.getChecksum();
            }

            System.out.println(total);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
