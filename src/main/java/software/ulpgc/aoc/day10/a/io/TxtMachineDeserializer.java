package software.ulpgc.aoc.day10.a.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day10.a.model.Machine;
import software.ulpgc.aoc.day10.model.Button;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class TxtMachineDeserializer implements Deserializer<Machine> {

    private static final Pattern TARGET = Pattern.compile("\\[([.#]+)]");
    private static final Pattern BUTTON = Pattern.compile("\\(([^)]*)\\)");

    @Override
    public Machine deserialize(String line) {
        if (line == null || line.isBlank())
            throw new IllegalArgumentException("Line cannot be null or empty");

        return new Machine(parseTarget(line), parseButtons(line));
    }

    private long parseTarget(String line) {
        return mask(
                TARGET.matcher(line)
                        .results()
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Missing or invalid target diagram bracket format: " + line))
                        .group(1));
    }

    private List<Button> parseButtons(String line) {
        return BUTTON.matcher(line)
                .results()
                .map(r -> Button.from(r.group(1)))
                .toList();
    }

    private long mask(String diagram) {
        return IntStream.range(0, diagram.length())
                .filter(i -> diagram.charAt(i) == '#')
                .mapToLong(i -> 1L << i)
                .sum();
    }
}