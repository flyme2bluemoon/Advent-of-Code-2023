package day13;

import utils.Advent;

import java.util.ArrayList;

public class PointOfIncidence {
    static int compareColumns(char[][] pattern, int col1, int col2) {
        int differences = 0;
        for (int i = 0; i < pattern.length; i++) {
            if (pattern[i][col1] != pattern[i][col2]) differences++;
        }
        return differences;
    }

    static boolean checkVerticalSymmetry(char[][] pattern, int col, int smudges) {
        int left = col;
        int right = col + 1;

        int smudgesFound = 0;

        while (left >= 0 && right < pattern[0].length) {
            smudgesFound += compareColumns(pattern, left, right);
            left--;
            right++;
        }

        return smudges == smudgesFound;
    }

    static int findVerticalReflection(char[][] pattern, int smudges) {
        for (int i = 0; i < pattern[0].length - 1; i++) {
            if (checkVerticalSymmetry(pattern, i, smudges)) return i+1;
        }
        return -1;
    }

    static int compareRows(char[][] pattern, int row1, int row2) {
        int differences = 0;
        for (int i = 0; i < pattern[row1].length; i++) {
            if (pattern[row1][i] != pattern[row2][i]) differences++;
        }
        return differences;
    }

    static boolean checkHorizontalSymmetry(char[][] pattern, int row, int smudges) {
        int top = row;
        int bottom = row + 1;

        int smudgesFound = 0;

        while (top >= 0 && bottom < pattern.length) {
            smudgesFound += compareRows(pattern, top, bottom);
            top--;
            bottom++;
        }

        return smudges == smudgesFound;
    }

    static int findHorizontalReflection(char[][] pattern, int smudges) {
        for (int i = 0; i < pattern.length - 1; i++) {
            if (checkHorizontalSymmetry(pattern, i, smudges)) return i+1;
        }
        return -1;
    }

    public static void main(String[] args) {
        ArrayList<char[][]> patterns = new ArrayList<>();

        // read one pattern
        while (Advent.in.hasNext()) {
            ArrayList<String> tmpPattern = new ArrayList<>();
            while (Advent.in.hasNext()) {
                String line = Advent.in.nextLine();
                if (line.isEmpty()) break;
                tmpPattern.add(line);
            }
            patterns.add(Advent.strArrayListToString(tmpPattern));
        }

        for (int smudges = 0; smudges <= 1; smudges++) {
            int sum = 0;
            for (char[][] pattern : patterns) {
                int vReflection = findVerticalReflection(pattern, smudges);
                if (vReflection != -1) sum += vReflection;
                int hReflection = findHorizontalReflection(pattern, smudges);
                if (hReflection != -1) sum += hReflection * 100;
            }
            System.out.println(sum);
        }
    }
}
