package day05;

import utils.Advent;

import java.util.ArrayList;
import java.util.Arrays;

public class SeedFertilizer {
    static class Map {
        ArrayList<Mapping> mappings = new ArrayList<>();

        public Map() {
            Advent.in.nextLine();
            for (String s = Advent.in.nextLine(); !s.isEmpty(); s = Advent.in.nextLine()) {
                this.addMapping(s);
                if (!Advent.in.hasNextLine()) break;
            }
        }

        public void addMapping(String mapping) {
            String[] triplet = mapping.split(" ");
            mappings.add(new Mapping(Long.parseLong(triplet[0]), Long.parseLong(triplet[1]), Long.parseLong(triplet[2])));
        }

        public long calculateMapping(long src) {
            for (Mapping mapping : mappings) {
                long diff = src - mapping.srcRange;
                if (diff >= 0 && diff < mapping.rangeLength) {
                    return mapping.destRange + diff;
                }
            }

            return src;
        }

        public ArrayList<long[]> calculateMapping(long[] src) {
            long start = src[0];
            long range = src[1];

            // range using start, length
            ArrayList<long[]> result = new ArrayList<>();

            long end = start + range;

            // range using [start, end)
            ArrayList<long[]> mappedRanges = new ArrayList<>();
            // add dummy head
            mappedRanges.add(new long[]{start, 0});

            for (Mapping mapping : mappings) {
                long mappingEnd = mapping.srcRange + mapping.rangeLength;
                if (!(end <= mapping.srcRange || start >= mappingEnd)) {
                    long[] curRange = new long[]{Math.max(start, mapping.srcRange), Math.min(end, mappingEnd)};

                    // marks as mapped
                    mappedRanges.add(curRange);

                    // do mapping
                    result.add(new long[]{mapping.destRange + (curRange[0] - mapping.srcRange), curRange[1] - curRange[0]});
                }
            }

            // add dummy tail
            mappedRanges.add(new long[]{end, 0});

            mappedRanges.sort((o1, o2) -> Long.compare(o1[0], o2[0]));

            // fill in the holes
            for (int i = 0; i < mappedRanges.size() - 1; i++) {
                long curStart = mappedRanges.get(i)[0] + mappedRanges.get(i)[1];
                long curRange = mappedRanges.get(i+1)[0] - curStart;
                if (curRange > 0) {
                    result.add(new long[]{curStart, curRange});
                }
            }

            return result;
        }

        static class Mapping {
            long destRange;
            long srcRange;
            long rangeLength;

            public Mapping(long destRange, long srcRange, long rangeLength) {
                this.destRange = destRange;
                this.srcRange = srcRange;
                this.rangeLength = rangeLength;
            }
        }
    }

    static long[] solve() {
        String[] seedsStr = Advent.in.nextLine().split(": ")[1].split(" ");
        long[] seeds1 = new long[seedsStr.length];
        ArrayList<long[]> seeds2 = new ArrayList<>();

        for (int i = 0; i < seedsStr.length; i += 2) {
            seeds1[i] = Long.parseLong(seedsStr[i]);
            seeds1[i + 1] = Long.parseLong(seedsStr[i + 1]);

            seeds2.add(new long[]{Long.parseLong(seedsStr[i]), Long.parseLong(seedsStr[i+1])});
        }
        Advent.in.nextLine();

        for (int i = 0; i < 7; i++) {
            Map map = new Map();

            for (int j = 0; j < seeds1.length; j++) {
                seeds1[j] = map.calculateMapping(seeds1[j]);
            }

            ArrayList<long[]> newSeeds = new ArrayList<>();

            for (long[] seedRange : seeds2) {
                newSeeds.addAll(map.calculateMapping(seedRange));
            }

            seeds2 = newSeeds;
        }

        long[] minLocation = new long[]{Long.MAX_VALUE, Long.MAX_VALUE};

        for (long seed : seeds1) {
            if (seed < minLocation[0]) {
                minLocation[0] = seed;
            }
        }

        for (long[] seed : seeds2) {
            if (seed[0] < minLocation[1]) {
                minLocation[1] = seed[0];
            }
        }

        return minLocation;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solve()));
    }
}
