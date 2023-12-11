package day06;

import utils.Advent;

public class WaitForIt {
    static int calculateNumOfWays(long t, long d) {
        double discriminant = Math.sqrt(t * t - 4 * d);
        int leftBound = (int) Math.floor(((-1 * t + discriminant) / (-2)) + 1);
        int rightBound = (int) Math.ceil(((-1 * t - discriminant) / (-2)) - 1);

        return (rightBound - leftBound + 1);
    }

    public static void main(String[] args) {
        String allTimes = Advent.in.nextLine().split(": +")[1];
        String[] times = allTimes.split(" +");
        String allDistances = Advent.in.nextLine().split(": +")[1];
        String[] distances = allDistances.split(" +");

        int product = 1;

        long t, d;

        for (int i = 0; i < times.length; i++) {
            t = Integer.parseInt(times[i]);
            d = Integer.parseInt(distances[i]);

            product *= calculateNumOfWays(t, d);
        }

        // part 1
        System.out.println(product);

        // part 2
        t = Long.parseLong(allTimes.replaceAll(" ", ""));
        d = Long.parseLong(allDistances.replaceAll(" ", ""));
        System.out.println(calculateNumOfWays(t, d));
    }
}
