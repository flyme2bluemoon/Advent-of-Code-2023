package day03;

import utils.Advent;

import java.util.ArrayList;
import java.util.LinkedList;

public class GearRatios {
    static boolean checkAdj(ArrayList<String> input, int row, int col) {
        for (int i = Math.max(row - 1, 0); i <= Math.min(row + 1, input.size() - 1); i++) {
            for (int j = Math.max(col - 1, 0); j <= Math.min(col + 1, input.get(row).length() - 1); j++) {
                char c = input.get(i).charAt(j);
                if (!Character.isDigit(c) && c != '.') return true;
            }
        }
        return false;
    }

    static int partOne(ArrayList<String> input) {
        int sum = 0;

        for (int i = 0; i < input.size(); i++) {
            int partNumber = 0;
            boolean adjSymbol = false;
            for (int j = 0; j < input.get(i).length(); j++) {
                char c = input.get(i).charAt(j);
                if (!Character.isDigit(c) && partNumber != 0) {
                    if (adjSymbol) sum += partNumber;
                    partNumber = 0;
                    adjSymbol = false;
                } else if (Character.isDigit(c)) {
                    partNumber *= 10;
                    partNumber += c - '0';
                    adjSymbol = adjSymbol || checkAdj(input, i, j);
                }
            }
            if (adjSymbol) sum += partNumber;
        }

        return sum;
    }

    static int getEntireNumber(ArrayList<String> input, int row, int col) {
        char c = input.get(row).charAt(col);
        if (!Character.isDigit(c)) return -1;

        LinkedList<Integer> digits = new LinkedList<>();
        digits.addLast(c - '0');

        for (int i = col + 1; i < input.get(row).length(); i++) {
            c = input.get(row).charAt(i);
            if (Character.isDigit(c)) digits.addLast(c - '0');
            else break;
        }
        for (int i = col - 1; i >= 0; i--) {
            c = input.get(row).charAt(i);
            if (Character.isDigit(c)) digits.addFirst(c - '0');
            else break;
        }

        int num = 0;
        for (int d : digits) {
            num *= 10;
            num += d;
        }

        return num;
    }

    // direction: 0 - along row / 1 - along col
    static ArrayList<Integer> getMultipliers(ArrayList<String> input, int row, int col, int direction) {
        ArrayList<Integer> multipliers = new ArrayList<>();

        int[] iRange = direction == 0 ? new int[]{row} : new int[]{row - 1, row + 1};
        int[] jRange = direction == 0 ? new int[]{col - 1, col + 1} : new int[]{col};

        for (int i : iRange) {
            for (int j : jRange) {
                if (i >= 0 && i < input.size() && j >= 0 && j < input.get(i).length()) {
                    int n = getEntireNumber(input, i, j);
                    if (n != -1) {
                        multipliers.add(n);
                    } else if (direction == 1) {
                        multipliers.addAll(getMultipliers(input, i, j, 0));
                    }
                }
            }
        }

        return multipliers;
    }

    static int partTwo(ArrayList<String> input) {
        int sum = 0;

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                char c = input.get(i).charAt(j);
                if (c == '*') {
                    ArrayList<Integer> multipliers = getMultipliers(input, i, j, 0);
                    multipliers.addAll(getMultipliers(input, i, j, 1));
                    if (multipliers.size() == 2) {
                        sum += multipliers.get(0) * multipliers.get(1);
                    }
                }
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        ArrayList<String> input = Advent.readInput();

        System.out.println(partOne(input));
        System.out.println(partTwo(input));
    }
}
