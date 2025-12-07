package software.ulpgc.aoc.day02.io;

import software.ulpgc.aoc.day02.model.IdRange;
import software.ulpgc.aoc.day02.model.InvalidatableId;

import java.util.function.LongFunction;

public class TxtRangeDeserializer<T extends InvalidatableId> implements RangeDeserializer<T> {

    @Override
    public IdRange<T> deserialize(String input, LongFunction<T> idFactory) {
        return toRange(input.trim(), idFactory);
    }

    private IdRange<T> toRange(String range, LongFunction<T> idFactory) {
        String[] parts = range.split("-");
        return new IdRange<>(toLong(parts[0]), toLong(parts[1]), idFactory);
    }

    private long toLong(String number) {
        return Long.parseLong(number.trim());
    }
}