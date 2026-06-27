package software.ulpgc.aoc.day05.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day05.model.ID;

public class TxtIDDeserializer implements Deserializer<ID> {
    @Override
    public ID deserialize(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("Line cannot be null or empty");
        }
        return new ID(Long.parseLong(line.trim()));
    }
}
