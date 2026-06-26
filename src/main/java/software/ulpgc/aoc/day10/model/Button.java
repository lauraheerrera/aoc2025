package software.ulpgc.aoc.day10.model;

import java.util.Arrays;
import java.util.List;

public record Button(List<Integer> targets) {
    public static Button from(String text) {
        return new Button(
                text.isBlank() ? List.of()
                        : Arrays.stream(text.split(","))
                                .map(String::trim)
                                .map(Integer::parseInt)
                                .toList()
        );
    }
}
