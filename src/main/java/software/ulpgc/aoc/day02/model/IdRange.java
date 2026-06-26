package software.ulpgc.aoc.day02.model;

import java.util.stream.LongStream;

import java.util.function.LongFunction;

public record IdRange<T extends InvalidatableId>(long start, long end, LongFunction<T> idFactory) {
    public long sumInvalidIDs() {
        return LongStream.rangeClosed(start, end)
                .mapToObj(idFactory)
                .filter(InvalidatableId::isInvalid)
                .mapToLong(InvalidatableId::id)
                .sum();
    }
}
