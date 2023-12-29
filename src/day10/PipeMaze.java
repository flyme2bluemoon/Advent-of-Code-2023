package day10;

import utils.Advent;

import java.util.ArrayList;
import java.util.HashSet;

public class PipeMaze {
    static class Maze {
        char[][] map;
        Pair start;
        int cycleLength;
        HashSet<Pair> cyclePath = new HashSet<>();

        public Maze(ArrayList<String> input) {
            // parse input
            this.map = new char[input.size()][];
            for (int i = 0; i < input.size(); i++) {
                map[i] = input.get(i).toCharArray();
            }

            // set start
            this.findStart();

            // find cycle
            this.findCycle();
        }

        private void findStart() {
            for (int i = 0; i < this.map.length; i++) {
                for (int j = 0; j < this.map[i].length; j++) {
                    if (map[i][j] == 'S') {
                        this.start = new Pair(i, j);
                        return;
                    }
                }
            }
        }

        private boolean connectedUp(Pair p) {
            return map[p.i][p.j] == '|' || map[p.i][p.j] == 'L' || map[p.i][p.j] == 'J' || map[p.i][p.j] == 'S';
        }

        private boolean connectedDown(Pair p) {
            return map[p.i][p.j] == '|' || map[p.i][p.j] == '7' || map[p.i][p.j] == 'F' || map[p.i][p.j] == 'S';
        }

        private boolean connectedLeft(Pair p) {
            return map[p.i][p.j] == '-' || map[p.i][p.j] == '7' || map[p.i][p.j] == 'J' || map[p.i][p.j] == 'S';
        }

        private boolean connectedRight(Pair p) {
            return map[p.i][p.j] == '-' || map[p.i][p.j] == 'L' || map[p.i][p.j] == 'F' || map[p.i][p.j] == 'S';
        }

        private boolean isConnected(Pair a, Pair b) {
            if (a.i == b.i) { // check adjacent horizontal
                return (b.j - a.j == 1 && connectedRight(a) && connectedLeft(b))
                        || (a.j - b.j == 1 && connectedRight(b) && connectedLeft(a));
            } else if (a.j == b.j) { // check adjacent vertical
                return (b.i - a.i == 1 && connectedDown(a) && connectedUp(b))
                        || (a.i - b.i == 1 && connectedDown(b) && connectedUp(a));
            }
            return false;
        }

        private ArrayList<Pair> getNeighbours(Pair p) {
            ArrayList<Pair> neighbours = new ArrayList<>();

            for (int k = p.i-1; k <= p.i+1; k += 2) {
                Pair neighbour = new Pair(k, p.j);
                if (k >= 0 && k < this.map.length && isConnected(p, neighbour)) neighbours.add(neighbour);
            }

            for (int k = p.j-1; k <= p.j+1; k += 2) {
                Pair neighbour = new Pair(p.i, k);
                if (k >= 0 && k < this.map[p.i].length && isConnected(p, neighbour)) neighbours.add(neighbour);
            }

            return neighbours;
        }

        private void findCycle() {
            Pair prev = new Pair(-1, -1);
            Pair cur = start;

            this.cycleLength = 0;

            do {
                this.cyclePath.add(cur);
                ArrayList<Pair> neighbours = getNeighbours(cur);
                for (Pair neighbour : neighbours) {
                    if (!prev.equals(neighbour)) {
                        prev = cur;
                        cur = neighbour;
                        this.cycleLength++;
                        break;
                    }
                }
            } while (!cur.equals(start));
        }

        int countEnclosedTiles() {
            int count = 0;
            for (int i = 0; i < this.map.length; i++) {
                for (int j = 0; j < this.map[i].length; j++) {
                    Pair cur = new Pair(i, j);
                    if (!this.cyclePath.contains(cur)) {
                        // check if tile is enclosed
                        int upCount = 0;
                        int downCount = 0;
                        for (int k = j+1; k < this.map[i].length; k++) {
                            Pair check = new Pair(i, k);
                            if (this.cyclePath.contains(check)) {
                                if (map[i][k] != 'S') {
                                    if (connectedUp(check)) upCount++;
                                    if (connectedDown(check)) downCount++;
                                } else {
                                    if (i > 0 && isConnected(check, new Pair(i-1, k))) upCount++;
                                    if (i < this.map.length - 1 && isConnected(check, new Pair(i+1, k))) downCount++;
                                }
                            }
                        }
                        if (Math.min(upCount, downCount) % 2 != 0) count++;
                    }
                }
            }
            return count;
        }

        static class Pair {
            int i, j;

            public Pair(int i, int j) {
                this.i = i;
                this.j = j;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Pair pair)) return false;

                if (i != pair.i) return false;
                return j == pair.j;
            }

            @Override
            public int hashCode() {
                int result = i;
                result = 31 * result + j;
                return result;
            }

            @Override
            public String toString() {
                return "(" + i + ", " + j + ')';
            }
        }
    }

    public static void main(String[] args) {
        Maze maze = new Maze(Advent.readInput());
        System.out.println(maze.cycleLength / 2);
        System.out.println(maze.countEnclosedTiles());
    }
}
