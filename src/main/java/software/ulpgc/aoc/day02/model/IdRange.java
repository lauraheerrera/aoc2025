package software.ulpgc.aoc.day02.model;

import java.util.stream.LongStream;

import java.util.function.LongFunction;

public class IdRange<T extends InvalidatableId> {
    private final long start;
    private final long end;
    private final LongFunction<T> idFactory;

    public IdRange(long start, long end, LongFunction<T> idFactory) {
        this.start = start;
        this.end = end;
        this.idFactory = idFactory;
    }

    public long sumInvalidIDs() {
        return LongStream.rangeClosed(start, end)
                .mapToObj(idFactory)
                .filter(InvalidatableId::isInvalid)
                .mapToLong(InvalidatableId::id)
                .sum();
    }
}
