package software.ulpgc.aoc.day05.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day05.model.ID;
import software.ulpgc.aoc.day05.model.Range;

public class TxtRangeDeserializer implements Deserializer<Range> {
    private static final String SEPARATOR = "-";

    @Override
    public Range deserialize(String line) {
        if (line == null || line.isBlank())
            throw new IllegalArgumentException("Line cannot be null or empty");
        String[] parts = line.split(SEPARATOR);
        if (parts.length != 2)
            throw new IllegalArgumentException("Invalid range format: " + line);
        return new Range(new ID(Long.parseLong(parts[0].trim())), new ID(Long.parseLong(parts[1].trim())));
    }
}
