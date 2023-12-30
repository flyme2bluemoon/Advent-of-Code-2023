package day15;

import utils.Advent;

import java.util.LinkedList;

public class LensLibrary {
    static class Lens {
        String label;
        int focalLength;

        public Lens(String label, int focalLength) {
            this.label = label;
            this.focalLength = focalLength;
        }
    }

    static int hash(String s) {
        int currentValue = 0;

        for (char c : s.toCharArray()) {
            currentValue += c;
            currentValue *= 17;
            currentValue = currentValue % 256;
        }

        return currentValue;
    }

    public static void main(String[] args) {
        String[] initSeq = Advent.in.nextLine().split(",");

        LinkedList<Lens>[] boxes = new LinkedList[256];
        for (int i = 0; i < boxes.length; i++) {
            boxes[i] = new LinkedList<>();
        }

        int sum = 0;

        for (String s : initSeq) {
            sum += hash(s);

            String label;
            int boxNumber, focalLength;
            if (s.charAt(s.length() - 1) == '-') {
                label = s.substring(0, s.length() - 1);
                boxNumber = hash(label);
                boxes[boxNumber].removeIf(cur -> cur.label.equals(label));
            } else if (s.charAt(s.length() - 2) == '=') {
                label = s.substring(0, s.length() - 2);
                boxNumber = hash(label);
                focalLength = s.charAt(s.length() - 1) - '0';
                boolean lensExists = false;
                for (Lens lens : boxes[boxNumber]) {
                    if (lens.label.equals(label)) {
                        lens.focalLength = focalLength;
                        lensExists = true;
                        break;
                    }
                }
                if (!lensExists) {
                    boxes[boxNumber].addLast(new Lens(label, focalLength));
                }
            } else throw new RuntimeException("undefined case");
        }
        System.out.println(sum);

        int totalFocusingPower = 0;
        for (int i = 0; i < boxes.length; i++) {
            int j = 1;
            for (Lens lens : boxes[i]) {
                totalFocusingPower += ((i+1) * j * lens.focalLength);
                j++;
            }
        }
        System.out.println(totalFocusingPower);
    }
}
