package software.ulpgc.aoc.day05.io;

import software.ulpgc.aoc.day05.model.ID;

public interface IDDeserializer {
    ID deserialize(String line);
}
