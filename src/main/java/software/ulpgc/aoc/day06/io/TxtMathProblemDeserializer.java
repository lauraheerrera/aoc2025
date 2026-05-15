package software.ulpgc.aoc.day06.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day06.model.Problem;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TxtMathProblemDeserializer implements Deserializer<Problem> {
    @Override
    public Problem deserialize(String block) {
        return new Problem(numbersOf(Arrays.asList(block.split("\n"))), operatorOf(block));
    }

    private List<Long> numbersOf(List<String> lines) {
        return lines.subList(0, lines.size() - 1).stream()
                .flatMap(this::numbersIn)
                .toList();
    }

    private Stream<Long> numbersIn(String line) {
        return Pattern.compile("\\d+").matcher(line).results()
                .map(mr -> Long.parseLong(mr.group()));
    }

    private char operatorOf(String block) {
        return block.trim().charAt(block.trim().length() - 1);
    }
}
