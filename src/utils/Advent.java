package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Advent {
    private static final Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

    public static ArrayList<String> readInput() {
        ArrayList<String> input = new ArrayList<>();

        while (in.hasNext()) {
            input.add(in.nextLine());
        }

        return input;
    }
}
