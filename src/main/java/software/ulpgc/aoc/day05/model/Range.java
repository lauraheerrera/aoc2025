package software.ulpgc.aoc.day05.model;

public record Range(long start, long end) {
    public boolean contains(long value) {
        return value >= start && value <= end;
    }

}
