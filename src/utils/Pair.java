package utils;

public class Pair {
    public int i, j;

    public Pair(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair pair)) return false;

        if (i != pair.i) return false;
        return j == pair.j;
    }

    @Override
    public int hashCode() {
        int result = i;
        result = 31 * result + j;
        return result;
    }

    @Override
    public String toString() {
        return "(" + i + ", " + j + ')';
    }
}
