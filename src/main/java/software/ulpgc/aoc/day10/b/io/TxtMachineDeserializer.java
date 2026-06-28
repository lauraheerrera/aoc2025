package software.ulpgc.aoc.day10.b.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day10.b.model.Machine;
import software.ulpgc.aoc.day10.model.Button;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class TxtMachineDeserializer implements Deserializer<Machine> {

    private static final Pattern TARGET = Pattern.compile("\\{([^}]*)\\}");
    private static final Pattern BUTTON = Pattern.compile("\\(([^)]*)\\)");

    @Override
    public Machine deserialize(String line) {
        if (line == null || line.isBlank())
            throw new IllegalArgumentException("Line cannot be null or empty");

        return new Machine(parseTargets(line), parseButtons(line));
    }

    private int[] parseTargets(String line) {
        return numbers(
                TARGET.matcher(line)
                        .results()
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Missing or invalid target joltage curly brackets format: " + line))
                        .group(1));
    }

    private List<Button> parseButtons(String line) {
        return BUTTON.matcher(line)
                .results()
                .map(r -> Button.from(r.group(1)))
                .toList();
    }

    private int[] numbers(String text) {
        return text.isBlank() ? new int[0]
                : Arrays.stream(text.split(","))
                        .map(String::trim)
                        .mapToInt(Integer::parseInt)
                        .toArray();
    }
}
