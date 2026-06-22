package software.ulpgc.aoc.day05.model;

public record ID(long value) implements Comparable<ID> {
    @Override
    public int compareTo(ID other) {
        return Long.compare(this.value, other.value);
    }
}