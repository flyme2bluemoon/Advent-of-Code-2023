package day04;

import utils.Advent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scratchcards {
    static int[] solve(ArrayList<String> input) {
        int[] answer = new int[]{0, 0};

        int[] quantities = new int[input.size()];
        Arrays.fill(quantities, 1);
        for (int i = 0; i < input.size(); i++) {
            String[] cardParts = input.get(i).split(": ");
            String[] numbers = cardParts[1].split(" \\| ");
            String[] winningNumbers = numbers[0].split(" +");
            List<String> elfNumbers = Arrays.asList(numbers[1].split(" +"));
            int matches = 0;
            for (String n : winningNumbers) {
                if (elfNumbers.contains(n)) {
                    matches++;
                }
            }

            for (int j = i + 1; j <= i + matches; j++) {
                quantities[j] += quantities[i];
            }

            answer[0] += matches > 0 ? (int) Math.pow(2, matches - 1) : 0;
        }

        answer[1] = Arrays.stream(quantities).sum();

        return answer;
    }

    public static void main(String[] args) {
        ArrayList<String> input = Advent.readInput();

        System.out.println(Arrays.toString(solve(input)));
    }
}
