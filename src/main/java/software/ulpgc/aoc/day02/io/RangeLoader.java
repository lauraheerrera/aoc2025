package software.ulpgc.aoc.day02.io;

import software.ulpgc.aoc.day02.model.IdRange;
import software.ulpgc.aoc.day02.model.InvalidatableId;

import java.io.IOException;
import java.util.List;

public interface RangeLoader<T extends InvalidatableId> {
    List<IdRange<T>> load() throws IOException;
}