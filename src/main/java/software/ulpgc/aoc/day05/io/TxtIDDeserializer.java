package software.ulpgc.aoc.day05.io;

import software.ulpgc.aoc.day05.model.ID;

public class TxtIDDeserializer implements IDDeserializer {
    @Override
    public ID deserialize(String line) {
        return new ID(Long.parseLong(line.trim()));
    }
}
