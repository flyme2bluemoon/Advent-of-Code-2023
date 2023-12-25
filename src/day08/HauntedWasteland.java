package day08;

import utils.Advent;

import java.math.BigInteger;
import java.util.ArrayList;

public class HauntedWasteland {
    static class Node {
        String id;
        String leftId;
        String rightId;
        Node left = null;
        Node right = null;

        public Node(String input) {
            String[] idAndChildren = input.split(" = ");
            this.id = idAndChildren[0];
            String[] leftAndRight = idAndChildren[1].substring(1, idAndChildren[1].length()-1).split(", ");
            this.leftId = leftAndRight[0];
            this.rightId = leftAndRight[1];
        }

        public void connectChildNodes(ArrayList<Node> allNodes) {
            for (Node node : allNodes) {
                if (this.leftId.equals(node.id)) {
                    this.left = node;
                }
                if (this.rightId.equals(node.id)) {
                    this.right = node;
                }
            }
            assert this.left != null && this.right != null;
        }
    }

    public static void main(String[] args) {
        char[] instructions = Advent.in.nextLine().toCharArray();
        Advent.in.nextLine();

        ArrayList<String> input = Advent.readInput();
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Node> starts = new ArrayList<>();

        for (String node : input) {
            nodes.add(new Node(node));
        }

        for (Node node : nodes) {
            node.connectChildNodes(nodes);
            if (node.id.charAt(2) == 'A') starts.add(node);
        }

        ArrayList<Integer> steps = new ArrayList<>();
        for (Node start : starts) {
            int stepCount = 0;
            Node cur = start;
            while (cur.id.charAt(2) != 'Z') {
                if (instructions[stepCount % instructions.length] == 'L') {
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
                stepCount++;
            }
            steps.add(stepCount);
            if (start.id.equals("AAA")) System.out.println(steps.getLast());
        }

        BigInteger lcm = BigInteger.valueOf(steps.getFirst());

        for (int i = 1; i < steps.size(); i++) {
            BigInteger bi = BigInteger.valueOf(steps.get(i));
            BigInteger gcd = lcm.gcd(bi);
            lcm = lcm.multiply(bi).divide(gcd);
        }

        System.out.println(lcm);
    }
}
