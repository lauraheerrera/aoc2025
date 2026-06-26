package software.ulpgc.aoc.day06.model;

import java.util.List;

public record ProblemBlock(List<String> lines) {
    public ProblemBlock {
        if (lines == null || lines.isEmpty() || lines.stream().allMatch(String::isBlank)) {
            throw new IllegalArgumentException("Block cannot be null or empty");
        }
        lines = List.copyOf(lines);
    }

    public String text() {
        return String.join("\n", lines);
    }
}
