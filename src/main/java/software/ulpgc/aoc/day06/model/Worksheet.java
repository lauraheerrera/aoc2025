package software.ulpgc.aoc.day06.model;

import software.ulpgc.aoc.common.io.Deserializer;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record Worksheet(List<String> lines) {
    public enum View {
        ROWS, COLUMNS_R2L
    }

    public Worksheet {
        if (lines == null || lines.isEmpty() || lines.stream().allMatch(String::isBlank)) {
            throw new IllegalArgumentException("Worksheet lines cannot be null, empty or blank");
        }
        lines = List.copyOf(lines);
    }

    public List<Operation> parse(Deserializer<Operation> deserializer) {
        return splitIntoBlocks().stream()
                .map(block -> deserializer.deserialize(block.text()))
                .toList();
    }

    private List<ProblemBlock> splitIntoBlocks() {
        return Pattern.compile("#+").matcher(contentMap()).results()
                .map(mr -> new ProblemBlock(extractBlockLines(mr.start(), mr.end())))
                .toList();
    }

    private String contentMap() {
        return IntStream.range(0, maxWidth())
                .mapToObj(x -> isColumnEmpty(x) ? " " : "#")
                .collect(Collectors.joining());
    }

    private List<String> extractBlockLines(int start, int end) {
        return lines.stream()
                .map(line -> start >= line.length() ? "" : line.substring(start, Math.min(end, line.length())))
                .toList();
    }

    private int maxWidth() {
        return lines.stream().mapToInt(String::length).max().orElse(0);
    }

    private boolean isColumnEmpty(int x) {
        return lines.stream().allMatch(line -> x >= line.length() || Character.isWhitespace(line.charAt(x)));
    }
}
