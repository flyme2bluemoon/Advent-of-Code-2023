package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Advent {
    public static final Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

    public static ArrayList<String> readInput() {
        ArrayList<String> input = new ArrayList<>();

        while (in.hasNext()) {
            input.add(in.nextLine());
        }

        return input;
    }

    public static char[][] strArrayListToString(ArrayList<String> arrayList) {
        char[][] arr = new char[arrayList.size()][];
        for (int i = 0; i < arrayList.size(); i++) {
            arr[i] = arrayList.get(i).toCharArray();
        }

        return arr;
    }

    public static char[][] readInputCharArray() {
        ArrayList<String> input = readInput();

        return strArrayListToString(input);
    }
}
