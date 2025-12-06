package software.ulpgc.aoc.day02.io;

import software.ulpgc.aoc.day02.model.IdRange;

import java.util.List;

public interface RangeDeserializer {
    List<IdRange> deserialize(String range);
}