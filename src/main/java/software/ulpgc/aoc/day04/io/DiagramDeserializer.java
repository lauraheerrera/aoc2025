package software.ulpgc.aoc.day04.io;

import software.ulpgc.aoc.day04.model.DiagramLine;

public interface DiagramDeserializer {
    DiagramLine deserialize(String line);
}
