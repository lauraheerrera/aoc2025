package software.ulpgc.aoc.day06.model;

import java.util.List;

public record Problem(List<Long> numbers, char operator) {
    public long solve() {
        return numbers.stream().reduce(this::apply).orElse(0L);
    }

    private long apply(long acc, long val) {
        return operator == '+' ? acc + val : acc * val;
    }
}
