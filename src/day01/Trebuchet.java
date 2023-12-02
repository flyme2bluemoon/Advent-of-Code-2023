package day01;

import utils.Advent;

import java.util.ArrayList;
import java.util.HashMap;

public class Trebuchet {
    static int partOne(ArrayList<String> inp) {
        int sum = 0;
        String numbersOnly;

        for (String line : inp) {
            numbersOnly = line.replaceAll("[^0-9]", "");
            sum += (numbersOnly.charAt(0) - '0') * 10 + (numbersOnly.charAt(numbersOnly.length() - 1) - '0');
        }

        return sum;
    }

    static int partTwo(ArrayList<String> inp) {
        int sum = 0;

        HashMap<String, Integer> numbers = new HashMap<>();
        numbers.put("one", 1);
        numbers.put("two", 2);
        numbers.put("three", 3);
        numbers.put("four", 4);
        numbers.put("five", 5);
        numbers.put("six", 6);
        numbers.put("seven", 7);
        numbers.put("eight", 8);
        numbers.put("nine", 9);
        numbers.put("1", 1);
        numbers.put("2", 2);
        numbers.put("3", 3);
        numbers.put("4", 4);
        numbers.put("5", 5);
        numbers.put("6", 6);
        numbers.put("7", 7);
        numbers.put("8", 8);
        numbers.put("9", 9);

        for (String line : inp) {
            int firstIndex = Integer.MAX_VALUE;
            int lastIndex = -1;
            int firstValue = 0;
            int lastValue = 0;

            for (String number : numbers.keySet()) {
                if (line.contains(number)) {
                    if (line.indexOf(number) < firstIndex) {
                        firstIndex = line.indexOf(number);
                        firstValue = numbers.get(number);
                    }
                    if (line.lastIndexOf(number) > lastIndex) {
                        lastIndex = line.lastIndexOf(number);
                        lastValue = numbers.get(number);
                    }
                }
            }

            sum += firstValue * 10 + lastValue;
        }

        return sum;
    }

    public static void main(String[] args) {
        ArrayList<String> input = Advent.readInput();

        System.out.println(partOne(input));
        System.out.println(partTwo(input));
    }
}
