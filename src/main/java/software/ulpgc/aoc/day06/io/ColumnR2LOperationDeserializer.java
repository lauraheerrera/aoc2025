package software.ulpgc.aoc.day06.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day06.model.Operand;
import software.ulpgc.aoc.day06.model.Operator;
import software.ulpgc.aoc.day06.model.Operation;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ColumnR2LOperationDeserializer implements Deserializer<Operation> {

    @Override
    public Operation deserialize(String blockText) {
        List<String> lines = blockText.lines().toList();

        int maxWidth = lines.stream().mapToInt(String::length).max().orElse(0);

        List<Operand> operands = IntStream.range(0, maxWidth)
                .map(i -> maxWidth - 1 - i)
                .mapToObj(x -> column(lines, x))
                .flatMap(s -> Pattern.compile("\\d+").matcher(s).results()
                        .map(mr -> new Operand(Long.parseLong(mr.group()))))
                .toList();

        Operator operator = Operator.from(lines.get(lines.size() - 1).trim().charAt(0));

        return new Operation(operands, operator);
    }

    private String column(List<String> lines, int x) {
        return IntStream.range(0, lines.size() - 1)
                .mapToObj(y -> charAt(lines.get(y), x))
                .collect(Collectors.joining());
    }

    private String charAt(String line, int x) {
        return (x < line.length() && Character.isDigit(line.charAt(x)))
                ? String.valueOf(line.charAt(x))
                : "";
    }
}