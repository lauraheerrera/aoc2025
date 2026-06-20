package software.ulpgc.aoc.day10.model;

import java.util.List;

public record Factory(List<Machine> machines) {
    public int totalMinPresses() {
        return machines.stream()
            .mapToInt(Machine::minPresses)
            .sum();
    }
}
