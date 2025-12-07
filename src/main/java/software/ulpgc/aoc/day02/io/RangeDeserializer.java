package software.ulpgc.aoc.day02.io;

import software.ulpgc.aoc.day02.model.IdRange;
import software.ulpgc.aoc.day02.model.InvalidatableId;

import java.util.function.LongFunction;

public interface RangeDeserializer<T extends InvalidatableId> {
    IdRange<T> deserialize(String input, LongFunction<T> idFactory);
}