package software.ulpgc.aoc.day06.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day06.model.Problem;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TxtMathProblemDeserializer implements Deserializer<Problem> {
    public enum View { Rows, ColumnsR2L }
    private final View view;

    public TxtMathProblemDeserializer() {
        this(View.Rows);
    }

    public TxtMathProblemDeserializer(View view) {
        this.view = view;
    }

    @Override
    public Problem deserialize(String block) {
        return new Problem(numbersOf(block), operatorOf(block));
    }

    private List<Long> numbersOf(String block) {
        return linesIn(block).stream()
                .flatMap(this::numbersIn)
                .toList();
    }

    private List<String> linesIn(String block) {
        return view == View.Rows ? rowsIn(block) : columnsIn(block);
    }

    private List<String> rowsIn(String block) {
        return Arrays.asList(block.split("\n")).subList(0, block.split("\n").length - 1);
    }

    private List<String> columnsIn(String block) {
        return IntStream.range(0, maxWidth(block.split("\n")))
                .map(i -> maxWidth(block.split("\n")) - 1 - i)
                .mapToObj(x -> digitsAt(block.split("\n"), x))
                .toList();
    }

    private int maxWidth(String[] lines) {
        return Arrays.stream(lines).mapToInt(String::length).max().orElse(0);
    }

    private String digitsAt(String[] lines, int x) {
        return IntStream.range(0, lines.length - 1)
                .mapToObj(y -> digitAt(lines[y], x))
                .collect(Collectors.joining());
    }

    private String digitAt(String line, int x) {
        return x < line.length() && Character.isDigit(line.charAt(x)) ? String.valueOf(line.charAt(x)) : "";
    }

    private Stream<Long> numbersIn(String line) {
        return Pattern.compile("\\d+").matcher(line).results()
                .map(mr -> Long.parseLong(mr.group()));
    }

    private char operatorOf(String block) {
        return block.trim().charAt(block.trim().length() - 1);
    }
}
