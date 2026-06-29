package software.ulpgc.aoc.day01.model;

import java.util.stream.IntStream;

public class DialCalculator {

    private final DialStatus status;

    private DialCalculator(DialStatus status) {
        this.status = status;
    }

    public static DialCalculator of(DialStatus status) {
        return new DialCalculator(status);
    }

    public int countEndingInZero() {
        return (int) IntStream.range(0, status.size())
                .map(status::next)
                .filter(p -> p == 0)
                .count();
    }

    public int countCrossingZero() {
        return IntStream.range(0, status.size())
                .map(status::crossZeroAt)
                .sum();
    }
}