package day02;

import utils.Advent;

import java.util.ArrayList;

public class CubeConundrum {
    static int partOne(ArrayList<String> input) {
        int sum = 0;

        // only 12 red cubes, 13 green cubes, and 14 blue cubes
        for (String s : input) {
            String[] game = s.split(": ");
            int gameId = Integer.parseInt(game[0].replaceAll("[^0-9]", ""));
            boolean impossible = false;

            String[] sets = game[1].split("; ");
            for (String set : sets) {
                String[] colors = set.split(", ");

                for (String color : colors) {
                    String[] cubes = color.split(" ");
                    int count = Integer.parseInt(cubes[0]);
                    if (cubes[1].equals("red") && count > 12) {
                        impossible = true;
                        break;
                    } else if (cubes[1].equals("green") && count > 13) {
                        impossible = true;
                        break;
                    } else if (cubes[1].equals("blue") && count > 14) {
                        impossible = true;
                        break;
                    }
                }

                if (impossible) {
                    break;
                }
            }

            if (!impossible) {
                sum += gameId;
            }
        }

        return sum;
    }

    static int partTwo(ArrayList<String> input) {
        int sum = 0;

        for (String s : input) {
            String[] game = s.split(": ");

            int red = 0, green = 0, blue = 0;
            String[] sets = game[1].split("; ");
            for (String set : sets) {
                String[] colors = set.split(", ");

                for (String color : colors) {
                    String[] cubes = color.split(" ");
                    int count = Integer.parseInt(cubes[0]);

                    switch (cubes[1]) {
                        case "red" -> red = Math.max(red, count);
                        case "green" -> green = Math.max(green, count);
                        case "blue" -> blue = Math.max(blue, count);
                    }
                }
            }
            sum += (red * green * blue);
        }

        return sum;
    }

    public static void main(String[] args) {
        ArrayList<String> input = Advent.readInput();

        System.out.println(partOne(input));
        System.out.println(partTwo(input));
    }
}
