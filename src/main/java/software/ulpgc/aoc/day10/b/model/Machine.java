package software.ulpgc.aoc.day10.b.model;

import software.ulpgc.aoc.day10.model.Button;
import java.util.List;

public record Machine(int[] targets, List<Button> buttons) {
}
