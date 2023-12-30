package day16;

import utils.Advent;
import utils.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class LavaFloor {
    enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    static class Position {
        Pair coordinate;
        Direction direction;
        char type;

        public Position(int row, int col, Direction direction, char type) {
            this.coordinate = new Pair(row, col);
            this.direction = direction;
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position position)) return false;

            if (!coordinate.equals(position.coordinate)) return false;

            if (type == '|') {
                if ((direction == Direction.RIGHT || direction == Direction.LEFT)
                        && (position.direction == Direction.RIGHT || position.direction == Direction.LEFT)) {
                    return true;
                }
            } else if (type == '-') {
                if ((direction == Direction.UP || direction == Direction.DOWN)
                        && (position.direction == Direction.UP || position.direction == Direction.DOWN)) {
                    return true;
                }
            }
            return direction == position.direction;
        }

        @Override
        public int hashCode() {
            return coordinate.hashCode();
        }
    }

    static Position getNeighbour(int row, int col, Direction direction, char[][] grid) {
        int origRow = row;
        int origCol = col;

        if (direction == Direction.RIGHT && col + 1 < grid[row].length) {
            col++;
        } else if (direction == Direction.LEFT && col - 1 >= 0) {
            col--;
        } else if (direction == Direction.DOWN && row + 1 < grid.length) {
            row++;
        } else if (direction == Direction.UP && row - 1 >= 0) {
            row--;
        }

        if (origRow != row || origCol != col) {
            return new Position(row, col, direction, grid[row][col]);
        }
        return null;
    }

    static int countEnergizedTiles(Position start, char[][] grid) {
        // perform breadth first search (BFS)
        HashSet<Position> explored = new HashSet<>();
        Queue<Position> frontier = new LinkedList<>();
        HashSet<Pair> energizedTiles = new HashSet<>();

        // Position start = new Position(0, 0, Direction.RIGHT, grid[0][0]);
        frontier.add(start);
        explored.add(start);

        while (!frontier.isEmpty()) {
            Position cur = frontier.remove();
            energizedTiles.add(cur.coordinate);

            // add neighbours to queue
            int row = cur.coordinate.i;
            int col = cur.coordinate.j;
            Direction direction = cur.direction;
            ArrayList<Position> neighbours = new ArrayList<>(2);
            switch (grid[row][col]) {
                case '.' -> neighbours.add(getNeighbour(row, col, direction, grid));
                case '/' -> {
                    switch (direction) {
                        case RIGHT -> neighbours.add(getNeighbour(row, col, Direction.UP, grid));
                        case UP -> neighbours.add(getNeighbour(row, col, Direction.RIGHT, grid));
                        case LEFT -> neighbours.add(getNeighbour(row, col, Direction.DOWN, grid));
                        case DOWN -> neighbours.add(getNeighbour(row, col, Direction.LEFT, grid));
                    }
                }
                case '\\' -> {
                    switch (direction) {
                        case RIGHT -> neighbours.add(getNeighbour(row, col, Direction.DOWN, grid));
                        case UP -> neighbours.add(getNeighbour(row, col, Direction.LEFT, grid));
                        case LEFT -> neighbours.add(getNeighbour(row, col, Direction.UP, grid));
                        case DOWN -> neighbours.add(getNeighbour(row, col, Direction.RIGHT, grid));
                    }
                }
                case '-' -> {
                    switch (direction) {
                        case LEFT, RIGHT -> neighbours.add(getNeighbour(row, col, direction, grid));
                        case UP, DOWN -> {
                            neighbours.add(getNeighbour(row, col, Direction.LEFT, grid));
                            neighbours.add(getNeighbour(row, col, Direction.RIGHT, grid));
                        }
                    }
                }
                case '|' -> {
                    switch (direction) {
                        case UP, DOWN -> neighbours.add(getNeighbour(row, col, direction, grid));
                        case LEFT, RIGHT -> {
                            neighbours.add(getNeighbour(row, col, Direction.UP, grid));
                            neighbours.add(getNeighbour(row, col, Direction.DOWN, grid));
                        }
                    }
                }
            }
            for (Position neighbour : neighbours) {
                if (neighbour != null && !explored.contains(neighbour)) {
                    explored.add(neighbour);
                    frontier.add(neighbour);
                }
            }
        }

        return energizedTiles.size();
    }

    public static void main(String[] args) {
        final char[][] grid = Advent.readInputCharArray();

        int maxEnergizedTiles = countEnergizedTiles(new Position(0, 0, Direction.RIGHT, grid[0][0]), grid);
        System.out.println(maxEnergizedTiles);

        // top row
        int energizedTiles;
        for (int i = 0; i < grid[0].length; i++) {
            energizedTiles = countEnergizedTiles(new Position(0, i, Direction.DOWN, grid[0][i]), grid);
            if (energizedTiles > maxEnergizedTiles) maxEnergizedTiles = energizedTiles;
        }
        // bottom row
        for (int i = 0; i < grid[0].length; i++) {
            energizedTiles = countEnergizedTiles(new Position(grid.length-1, i, Direction.UP, grid[grid.length-1][i]), grid);
            if (energizedTiles > maxEnergizedTiles) maxEnergizedTiles = energizedTiles;
        }
        // left col
        for (int i = 0; i < grid.length; i++) {
            energizedTiles = countEnergizedTiles(new Position(i, 0, Direction.RIGHT, grid[i][0]), grid);
            if (energizedTiles > maxEnergizedTiles) maxEnergizedTiles = energizedTiles;
        }
        // right col
        for (int i = 0; i < grid.length; i++) {
            energizedTiles = countEnergizedTiles(new Position(i, grid[0].length-1, Direction.LEFT, grid[i][grid[0].length-1]), grid);
            if (energizedTiles > maxEnergizedTiles) maxEnergizedTiles = energizedTiles;
        }

        System.out.println(maxEnergizedTiles);
    }
}
