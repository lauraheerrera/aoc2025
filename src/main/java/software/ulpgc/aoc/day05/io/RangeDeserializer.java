package software.ulpgc.aoc.day05.io;

import software.ulpgc.aoc.day05.model.Range;

public interface RangeDeserializer {
    Range deserialize(String line);
}
