package day14;

import utils.Advent;

import java.util.ArrayList;

public class ParabolicReflectorDish {
    static void tilt(char[] column) {
        int lowerBound = 0;
        for (int i = 0; i < column.length; i++) {
            if (column[i] == '#') {
                lowerBound = i+1;
            } else if (column[i] == 'O') {
                column[i] = '.';
                column[lowerBound] = 'O';
                lowerBound++;
            }
        }
    }

    static int calculateLoad(char[][] dish) {
        int totalLoad = 0;
        for (int i = 0; i < dish.length; i++) {
            for (int j = 0; j < dish[0].length; j++) {
                if (dish[i][j] == 'O') totalLoad += (dish.length - i);
            }
        }
        return totalLoad;
    }

    static String dishToString(char[][] dish) {
        StringBuilder s = new StringBuilder();
        for (char[] row : dish) {
            s.append(row);
        }
        return s.toString();
    }

    public static void main(String[] args) {
        char[][] dish = Advent.readInputCharArray();

        ArrayList<String> prevDishes = new ArrayList<>();
        ArrayList<Integer> prevDishLoads = new ArrayList<>();

        for (int cycle = 1; cycle <= 1_000_000_000; cycle++) {
            // tilt north
            for (int i = 0; i < dish[0].length; i++) {
                char[] col = new char[dish.length];
                for (int j = 0; j < dish.length; j++) {
                    col[j] = dish[j][i];
                }
                tilt(col);
                for (int j = 0; j < dish.length; j++) {
                    dish[j][i] = col[j];
                }
            }
            if (cycle == 1) {
                System.out.println(calculateLoad(dish));
            }
            // tilt west
            for (int i = 0; i < dish.length; i++) {
                char[] row = new char[dish[i].length];
                for (int j = 0; j < dish[i].length; j++) {
                    row[j] = dish[i][j];
                }
                tilt(row);
                for (int j = 0; j < dish[i].length; j++) {
                    dish[i][j] = row[j];
                }
            }
            // tilt south
            for (int i = 0; i < dish[0].length; i++) {
                char[] col = new char[dish.length];
                for (int j = 0; j < dish.length; j++) {
                    col[j] = dish[dish.length - j - 1][i];
                }
                tilt(col);
                for (int j = 0; j < dish.length; j++) {
                    dish[j][i] = col[dish.length - j - 1];
                }
            }
            // tilt east
            for (int i = 0; i < dish.length; i++) {
                char[] row = new char[dish[i].length];
                for (int j = 0; j < dish[i].length; j++) {
                    row[j] = dish[i][dish[i].length - j - 1];
                }
                tilt(row);
                for (int j = 0; j < dish[i].length; j++) {
                    dish[i][j] = row[dish[i].length - j - 1];
                }
            }

            String dishAsStr = dishToString(dish);
            if (prevDishes.contains(dishAsStr)) {
                int cycleStart = prevDishes.indexOf(dishAsStr);
                int cycleLength = prevDishes.size() - cycleStart;
                int offset = (1_000_000_000 - cycleStart) % cycleLength;
                System.out.println(prevDishLoads.get(cycleStart + offset - 1));
                break;
            } else {
                prevDishes.add(dishAsStr);
                prevDishLoads.add(calculateLoad(dish));
            }
        }
    }
}
