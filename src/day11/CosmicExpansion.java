package day11;

import utils.Advent;
import utils.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.IntStream;

public class CosmicExpansion {
    public static void main(String[] args) {
        char[][] map = Advent.readInputCharArray();

        HashSet<Integer> emptyRows = new HashSet<>(IntStream.range(0, map.length).boxed().toList());
        HashSet<Integer> emptyCols = new HashSet<>(IntStream.range(0, map[0].length).boxed().toList());

        ArrayList<Pair> galaxies = new ArrayList<>();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '#') {
                    emptyRows.remove(i);
                    emptyCols.remove(j);
                    galaxies.add(new Pair(i, j));
                }
            }
        }

        int sum = 0;
        long sum2 = 0;

        HashSet<Integer> tmp;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                Pair start = galaxies.get(i);
                Pair end = galaxies.get(j);

                tmp = new HashSet<>(emptyRows);
                tmp.retainAll(IntStream.range(Math.min(start.i, end.i), Math.max(start.i, end.i)).boxed().toList());
                int extraRows = tmp.size();

                tmp = new HashSet<>(emptyCols);
                tmp.retainAll(IntStream.range(Math.min(start.j, end.j), Math.max(start.j, end.j)).boxed().toList());
                int extraCols = tmp.size();

                int distance = Math.abs(end.i - start.i) + Math.abs(end.j - start.j);

                sum += (distance + extraRows + extraCols);
                sum2 += (distance + extraRows * 999999L + extraCols * 999999L);
            }
        }

        System.out.println(sum);
        System.out.println(sum2);
    }
}
