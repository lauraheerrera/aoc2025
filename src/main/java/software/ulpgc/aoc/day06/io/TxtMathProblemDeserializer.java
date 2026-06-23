package software.ulpgc.aoc.day06.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day06.model.Operand;
import software.ulpgc.aoc.day06.model.Operator;
import software.ulpgc.aoc.day06.model.Problem;
import software.ulpgc.aoc.day06.model.Worksheet;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TxtMathProblemDeserializer implements Deserializer<Problem> {
    private final Worksheet.View view;

    public TxtMathProblemDeserializer(Worksheet.View view) {
        this.view = view;
    }

    @Override
    public Problem deserialize(String blockText) {
        List<String> lines = blockText.lines().toList();
        return new Problem(extractOperands(lines), extractOperator(lines));
    }

    private Operator extractOperator(List<String> lines) {
        return Operator.from(lines.get(lines.size() - 1).trim().charAt(0));
    }

    private List<Operand> extractOperands(List<String> lines) {
        return view == Worksheet.View.ROWS ? extractOperandsByRow(lines) : extractOperandsByColumn(lines);
    }

    private List<Operand> extractOperandsByRow(List<String> lines) {
        return lines.subList(0, lines.size() - 1).stream()
                .flatMap(line -> parseNumbers(line).stream())
                .map(Operand::new)
                .toList();
    }

    private List<Operand> extractOperandsByColumn(List<String> lines) {
        int maxWidth = lines.stream().mapToInt(String::length).max().orElse(0);
        return IntStream.range(0, maxWidth)
                .map(x -> maxWidth - 1 - x)
                .mapToObj(x -> columnString(lines, x))
                .flatMap(colStr -> parseNumbers(colStr).stream())
                .map(Operand::new)
                .toList();
    }

    private String columnString(List<String> lines, int x) {
        return IntStream.range(0, lines.size() - 1)
                .mapToObj(y -> charAt(lines.get(y), x))
                .collect(Collectors.joining());
    }

    private String charAt(String line, int x) {
        return x < line.length() && Character.isDigit(line.charAt(x)) ? String.valueOf(line.charAt(x)) : "";
    }

    private List<Long> parseNumbers(String s) {
        return Pattern.compile("\\d+").matcher(s).results()
                .map(mr -> Long.parseLong(mr.group()))
                .toList();
    }
}
