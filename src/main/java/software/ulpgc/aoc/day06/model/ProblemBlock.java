package software.ulpgc.aoc.day06.model;

import java.util.List;

public class ProblemBlock {
    private final List<String> lines;

    public ProblemBlock(List<String> lines) {
        if (lines == null || lines.isEmpty() || lines.stream().allMatch(String::isBlank)) {
            throw new IllegalArgumentException("Block cannot be null or empty");
        }
        this.lines = List.copyOf(lines);
    }

    public String text() {
        return String.join("\n", lines);
    }
}
