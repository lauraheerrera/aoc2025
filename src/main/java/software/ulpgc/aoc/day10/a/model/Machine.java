package software.ulpgc.aoc.day10.a.model;

import software.ulpgc.aoc.day10.model.Button;
import java.util.List;

public record Machine(long targetMask, List<Button> buttons) implements software.ulpgc.aoc.day10.model.Machine {
}