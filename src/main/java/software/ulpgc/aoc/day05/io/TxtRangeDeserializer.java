package software.ulpgc.aoc.day05.io;

import software.ulpgc.aoc.day05.model.Range;

public class TxtRangeDeserializer implements RangeDeserializer {
    private static final String SEPARATOR = "-";

    @Override
    public Range deserialize(String line) {
        String[] parts = line.split(SEPARATOR);
        return new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }
}
