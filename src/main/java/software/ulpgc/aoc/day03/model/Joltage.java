package software.ulpgc.aoc.day03.model;

public record Joltage(long value) {
    public static final Joltage ZERO = new Joltage(0);

    public Joltage add(Joltage other) {
        return new Joltage(this.value + other.value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}