package software.ulpgc.aoc.day02.model;

import software.ulpgc.aoc.day02.a.model.Id;

import java.util.stream.LongStream;

public class IdRange {
    private final long start;
    private final long end;

    public IdRange(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long sumInvalidIDs() {
        return LongStream.rangeClosed(start, end)
                .mapToObj(Id::create)
                .filter(Id::isInvalid)
                .mapToLong(Id::id)
                .sum();
    }
}