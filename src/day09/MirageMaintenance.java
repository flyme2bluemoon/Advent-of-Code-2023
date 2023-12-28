package day09;

import utils.Advent;

public class MirageMaintenance {
    static int getNext(int[] sequence) {
        int[] differences = new int[sequence.length - 1];
        boolean allZero = true;
        for (int i = 0; i < differences.length; i++) {
            differences[i] = sequence[i+1] - sequence[i];
            if (differences[i] != 0) allZero = false;
        }

        if (allZero) return sequence[0];
        else return sequence[sequence.length - 1] + getNext(differences);
    }

    static int[] reverse(int[] original) {
        int[] reversed = new int[original.length];
        for (int i = 0; i < original.length; i++) {
            reversed[i] = original[original.length - i - 1];
        }
        return reversed;
    }

    public static void main(String[] args) {
        int sum = 0;
        int sum2 = 0;

        while (Advent.in.hasNext()) {
            String[] sequenceStr = Advent.in.nextLine().split(" ");
            int[] sequence = new int[sequenceStr.length];
            for (int i = 0; i < sequenceStr.length; i++) {
                sequence[i] = Integer.parseInt(sequenceStr[i]);
            }
            sum += getNext(sequence);
            sum2 += getNext(reverse(sequence));
        }

        System.out.println(sum);
        System.out.println(sum2);
    }
}
