package software.ulpgc.aoc.day02.io;

import software.ulpgc.aoc.day02.model.IdRange;
import software.ulpgc.aoc.day02.model.InvalidatableId;
import software.ulpgc.aoc.common.io.Deserializer;

import java.util.function.LongFunction;

public class TxtRangeDeserializer<T extends InvalidatableId>
        implements Deserializer<IdRange<T>> {

    private final LongFunction<T> idFactory;

    public TxtRangeDeserializer(LongFunction<T> idFactory) {
        this.idFactory = idFactory;
    }

    public TxtRangeDeserializer() {
        this.idFactory = null; // Or throw detailed exception if default constructor is used incorrectly
    }

    @Override
    public IdRange<T> deserialize(String input) {
        return toRange(input.trim());
    }

    private IdRange<T> toRange(String range) {
        String[] parts = range.split("-");
        return new IdRange<>(toLong(parts[0]), toLong(parts[1]), idFactory);
    }

    private long toLong(String number) {
        return Long.parseLong(number.trim());
    }
}