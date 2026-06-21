package software.ulpgc.aoc.day04.model;

public record RollsCount(int value) {
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
