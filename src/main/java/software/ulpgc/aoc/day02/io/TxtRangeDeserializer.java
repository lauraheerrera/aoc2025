package software.ulpgc.aoc.day02.io;

import software.ulpgc.aoc.day02.model.IdRange;

import java.util.*;
import java.util.stream.Collectors;

public class TxtRangeDeserializer implements RangeDeserializer {

    @Override
    public List<IdRange> deserialize(String input) {
        return Arrays.stream(input.split(","))
                .map(this::toRange)
                .collect(Collectors.toList());
    }

    private IdRange toRange(String range) {
        String[] parts = range.split("-");
        return new IdRange(toLong(parts[0]), toLong(parts[1]));
    }

    private long toLong(String number) {
        return Long.parseLong(number.trim());
    }
}