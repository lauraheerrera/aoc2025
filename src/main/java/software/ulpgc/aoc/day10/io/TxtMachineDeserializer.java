package software.ulpgc.aoc.day10.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day10.model.Machine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TxtMachineDeserializer implements Deserializer<Machine> {
    @Override
    public Machine deserialize(String line) {
        return new Machine(
            parseTarget(line),
            parseButtons(line)
        );
    }

    private long parseTarget(String line) {
        return parseTargetMask(line.substring(line.indexOf('[') + 1, line.indexOf(']')));
    }

    private long parseTargetMask(String diagram) {
        return IntStream.range(0, diagram.length())
            .filter(i -> diagram.charAt(i) == '#')
            .mapToLong(i -> 1L << i)
            .sum();
    }

    private List<Long> parseButtons(String line) {
        return Arrays.stream(line.split(" "))
            .filter(s -> s.startsWith("(") && s.endsWith(")"))
            .map(this::parseButtonMask)
            .collect(Collectors.toList());
    }

    private long parseButtonMask(String button) {
        return Arrays.stream(button.substring(1, button.length() - 1).split(","))
            .mapToLong(Long::parseLong)
            .map(i -> 1L << i)
            .sum();
    }
}
