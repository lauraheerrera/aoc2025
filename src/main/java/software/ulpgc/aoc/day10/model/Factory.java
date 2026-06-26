package software.ulpgc.aoc.day10.model;

import java.util.List;

public record Factory(List<? extends Machine> machines) {
    public long totalMinPresses() {
        return machines.stream()
            .mapToLong(Machine::minPresses)
            .sum();
    }
}
