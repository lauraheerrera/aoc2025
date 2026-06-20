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
        this.idFactory = null;
    }

    @Override
    public IdRange<T> deserialize(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }
        return toRange(input.trim());
    }

    private IdRange<T> toRange(String range) {
        String[] parts = range.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid range format: " + range);
        }
        return new IdRange<>(toLong(parts[0]), toLong(parts[1]), idFactory);
    }

    private long toLong(String number) {
        return Long.parseLong(number.trim());
    }
}