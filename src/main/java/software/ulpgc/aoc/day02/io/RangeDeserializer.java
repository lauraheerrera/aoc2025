package software.ulpgc.aoc.day02.io;

import software.ulpgc.aoc.day02.model.IdRange;
import software.ulpgc.aoc.day02.model.InvalidatableId;

import java.util.List;
import java.util.function.LongFunction;

public interface RangeDeserializer<T extends InvalidatableId> {
    List<IdRange<T>> deserialize(String input, LongFunction<T> idFactory);
}